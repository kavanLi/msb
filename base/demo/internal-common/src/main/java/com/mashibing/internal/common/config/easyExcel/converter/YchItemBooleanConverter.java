package com.mashibing.internal.common.config.easyExcel.converter;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.mashibing.internal.common.constant.ItemManagementConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-23 14:33
 * To change this template use File | Settings | File and Code Templates.
 */

/**
 * 自定义转换器
 */
@Slf4j
public class YchItemBooleanConverter implements Converter <Boolean> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }


    private final String YES = ItemManagementConstants.BooleanStatus.TRUE.getDesc();
    private final String NO = ItemManagementConstants.BooleanStatus.FALSE.getDesc();

    /**
     * 读取调用
     */
    @Override
    public Boolean convertToJavaData(ReadCellData <?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Field field = contentProperty.getField();
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        String[] excelPropertyValues = excelProperty.value();

        // 这里假设只有一个值，可以根据需要处理多个值的情况
        String fieldName = field.getName();
        String fieldConvertName = excelPropertyValues.length > 0 ? excelPropertyValues[0] : "";
        String value = cellData.getStringValue();
        log.info("read from excel, field: {}, Value: {}", fieldConvertName, value);

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        if (Stream.of(YES).anyMatch(e -> e.equals(value))) {
            return true;
        }
        if (Stream.of(NO).anyMatch(e -> e.equals(value))) {
            return false;
        }
        throw new IllegalArgumentException("表格" + fieldConvertName + "列中存在非法的导入参数：" + value);
    }

    /**
     * 写入调用
     */
    @Override
    public WriteCellData <?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("write to excel, Field: {}, Value: {}", fieldName, value);

        WriteCellData <String> writeCellData;

        switch (fieldName) {
            case "registryFlag":
                writeCellData = new WriteCellData <>(value ? YES : NO);
                break;
            case "isRealName":
                writeCellData = new WriteCellData <>(value ? YES : NO);
                break;
            case "isReleased":
                writeCellData = new WriteCellData <>(value ? YES : NO);
                break;
            case "isRefund":
                writeCellData = new WriteCellData <>(value ? YES : NO);
                break;
            case "isAllowAmount":
                writeCellData = new WriteCellData <>(value ? YES : NO);
                break;
            default:
                writeCellData = new WriteCellData <>(value.toString());
                break;
        }

        return writeCellData;
    }
}