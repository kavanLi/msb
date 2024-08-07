package com.mashibing.internal.common.config.easyExcel.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.mashibing.internal.common.config.easyExcel.annotation.DropdownOptions;
import com.mashibing.internal.common.config.easyExcel.setting.SheetTableData;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class DropdownWriteHandler extends AbstractSheetWriteHandler {



    private final Map <String, List <DropdownOptionsWrapper>> optionMap = new HashMap <>();
    private final Map <String, Integer> rowCountMap = new HashMap <>();
    private final Integer defaultRowCount = 256;

    public DropdownWriteHandler(List <SheetTableData> sheetTableDataList) {
        initializeDropdownOptions(sheetTableDataList);
    }

    private static class DropdownOptionsWrapper {
        private final String[] values;
        private final boolean check;
        private final Integer columnIndex;

        public DropdownOptionsWrapper(String[] values, Integer columnIndex,boolean check) {
            this.values = values;
            this.columnIndex = columnIndex;
            this.check = check;
        }

        public String[] getValues() {
            return values;
        }

        public Integer getColumnIndex() {
            return columnIndex;
        }

        public boolean isCheck() {
            return check;
        }
    }

    private void initializeDropdownOptions(List <SheetTableData> sheetTableDataList) {
        for (SheetTableData tableData : sheetTableDataList) {
            Class <?> clazz = tableData.getClazz();
            String clazzName = clazz.getName();
            List <?> dataList = tableData.getDataList();
            int rowCount = dataList.size(); // +1 to include header row
            //int rowCount = dataList.size() + 1; // +1 to include header row
            rowCountMap.put(clazzName, rowCount);

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(DropdownOptions.class)) {
                    DropdownOptions annotation = field.getAnnotation(DropdownOptions.class);
                    int columnIndex = getColumnIndex(field);

                    // ------------- 检查 enumClass 注解值，如果不是 DefaultEnumClass，则通过反射获取枚举类的所有枚举实例，并提取其描述。如果是 DefaultEnumClass，则直接使用注解的 value 属性。解析枚举类时出现异常时，返回 value 注解值。 -------------
                    String[] values;
                    try {
                        values = getEnumDescriptions(annotation.enumClass());
                    } catch (Exception e) {
                        values = annotation.value();
                    }
                    if (columnIndex >= 0) {
                        DropdownOptionsWrapper optionWrapper = new DropdownOptionsWrapper(values, columnIndex, annotation.check());
                        optionMap.computeIfAbsent(clazzName, k -> new ArrayList<>()).add(optionWrapper);
                    }
                }
            }
        }

        // ------------- 如果只有一个表格且行数小于默认行数则单元格下拉框增加到默认行数 -------------
        if (1 == rowCountMap.size()) {
            for (Map.Entry <String, Integer> entry : rowCountMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                if (defaultRowCount > value) {
                    rowCountMap.put(key, defaultRowCount);
                }
            }
        }
    }

    /**
     * 解析枚举值
     */
    private String[] getEnumDescriptions(Class<? extends Enum<?>> enumClass) throws Exception {
        if (enumClass == DropdownOptions.DefaultEnumClass.class) {
            throw new IllegalArgumentException("DefaultEnumClass does not have enum constants");
        }

        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        return Arrays.stream(enumConstants)
                .map(enumConstant -> {
                    try {
                        Method getDescMethod = enumConstant.getClass().getMethod("getDesc");
                        return (String) getDescMethod.invoke(enumConstant);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to get enum description", e);
                    }
                })
                .toArray(String[]::new);
    }

    private int getColumnIndex(Field field) {
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        if (excelProperty != null) {
            String[] value = excelProperty.value();
            int order = excelProperty.order();
            if (value.length > 0 && order >= 0) {
                return order; // 自定义实现，根据实际情况映射列索引
            }
        }
        return -1; // 无效索引
    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        XSSFSheet sheet = (XSSFSheet) writeSheetHolder.getSheet();
        XSSFDataValidationHelper helper = new XSSFDataValidationHelper(sheet);

        Integer firstRow = 1;
        for (Map.Entry <String, List <DropdownOptionsWrapper>> entry : optionMap.entrySet()) {
            String clazzName = entry.getKey();
            List <DropdownOptionsWrapper> dropdownOptionsWrapperList = entry.getValue();
            int rowCount = rowCountMap.getOrDefault(clazzName, defaultRowCount); // 默认行数为100
            for (DropdownOptionsWrapper dropdownOptions : dropdownOptionsWrapperList) {
                int columnIndex = dropdownOptions.getColumnIndex();
                String[] dropdownValues = dropdownOptions.getValues();
                boolean check = dropdownOptions.isCheck();

                XSSFDataValidationConstraint constraint = (XSSFDataValidationConstraint) helper.createExplicitListConstraint(dropdownValues);
                CellRangeAddressList addressList = new CellRangeAddressList(firstRow, rowCount, columnIndex, columnIndex);
                XSSFDataValidation dataValidation = (XSSFDataValidation) helper.createValidation(constraint, addressList);

                // 设置下拉箭头的显示与否
                dataValidation.setSuppressDropDownArrow(true);

                // 根据 check 属性设置是否需要错误提示框
                dataValidation.setShowErrorBox(check);

                sheet.addValidationData(dataValidation);
            }
            firstRow += rowCount + 1; // +1 to exclude header row
        }
    }

}
