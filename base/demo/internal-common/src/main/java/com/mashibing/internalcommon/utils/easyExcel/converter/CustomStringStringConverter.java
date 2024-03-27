package com.mashibing.internalcommon.utils.easyExcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-23 14:33
 * To change this template use File | Settings | File and Code Templates.
 */
/**
 * 自定义转换器
 */
public class CustomStringStringConverter implements Converter <String> {
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
    public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        return "自定义：" + cellData.getStringValue();
    }
    /**
     * 写入调用
     */
    @Override
    public WriteCellData <?> convertToExcelData(String value, ExcelContentProperty contentProperty,
                                                GlobalConfiguration globalConfiguration) {
        return new WriteCellData ("自定义：" + value);
    }
}