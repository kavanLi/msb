package com.mashibing.internal.common.config.easyExcel.converter;

import java.lang.reflect.Field;

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
public class YchItemEnumConverter implements Converter <Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 读取调用
     */
    @Override
    public Integer convertToJavaData(ReadCellData <?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
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

        switch (value) {
            case "启用":
                return ItemManagementConstants.ItemStatus.ITEM_STATUS_ENABLE.getCode();
            case "禁用":
                return ItemManagementConstants.ItemStatus.ITEM_STATUS_DISABLE.getCode();
            case "POS款项":
                return ItemManagementConstants.ItemScene.ITEM_SCENE_POS.getCode();
            case "H5款项":
                return ItemManagementConstants.ItemScene.ITEM_SCENE_H5.getCode();
            default:
                throw new IllegalArgumentException("表格" + fieldConvertName + "列中存在非法的导入参数：" + value);
        }
    }

    /**
     * 写入调用
     */
    @Override
    public WriteCellData <?> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                                GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("write to excel, Field: {}, Value: {}", fieldName, value);

        WriteCellData<String> writeCellData;

        switch (fieldName) {
            case "registryFlag":
                writeCellData = new WriteCellData<>(ItemManagementConstants.ItemTier.of(value).getDesc());
                break;
            case "itemTier":
                // 科目层级，1-科目类别 2-科目明细
                writeCellData = new WriteCellData<>(ItemManagementConstants.ItemTier.of(value).getDesc());
                break;
            case "itemScene":
                // 应用场景，1-POS款项 2-H5款项
                writeCellData = new WriteCellData<>(ItemManagementConstants.ItemScene.of(value).getDesc());
                break;
            case "status":
                // 启用状态，1-启用 0-禁用
                writeCellData = new WriteCellData<>(ItemManagementConstants.ItemStatus.of(value).getDesc());
                break;
            default:
                writeCellData = new WriteCellData<>(value.toString());
                break;
        }

        return writeCellData;
    }
}