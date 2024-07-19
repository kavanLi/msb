package com.mashibing.internal.common.utils.easyExcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

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

    /**
     * 写入调用
     */
    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("Field: {}, Value: {}", fieldName, value);

        WriteCellData<String> writeCellData;

        switch (fieldName) {
            case "registryFlag":
                writeCellData = new WriteCellData<>(value ? "登记" : "未登记");
                break;
            case "isRealName":
                writeCellData = new WriteCellData<>(value ? "实名" : "不实名");
                break;
            case "isReleased":
                writeCellData = new WriteCellData<>(value ? "已发布" : "未发布");
                break;
            case "isRefund":
                writeCellData = new WriteCellData<>(value ? "可退款" : "不可退款");
                break;
            default:
                writeCellData = new WriteCellData<>(value.toString());
                break;
        }

        return writeCellData;
    }
}