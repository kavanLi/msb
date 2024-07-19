package com.mashibing.internal.common.utils.easyExcel.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @author: kavanLi-R7000
 * @create: 2024-02-23 14:33
 * To change this template use File | Settings | File and Code Templates.
 */

/**
 * 自定义转换器
 */
@Slf4j
public class LongFormatConverter implements Converter <Long> {
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
    public WriteCellData <?> convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("Field: {}, Value: {}", fieldName, value);

        WriteCellData<String> writeCellData;

        switch (fieldName) {
            case "billTotalAmount":
                writeCellData = getLong2YuanWriteCellData(value);
                break;
            case "billTotalPaidAmount":
                writeCellData = getLong2YuanWriteCellData(value);
                break;
            case "billAmount":
                writeCellData = getLong2YuanWriteCellData(value);
                break;
            case "billPaidAmount":
                writeCellData = getLong2YuanWriteCellData(value);
                break;
            default:
                writeCellData = getLong2YuanWriteCellData(value);
                break;
        }

        return writeCellData;
    }

    @NotNull
    private static WriteCellData <String> getLong2YuanWriteCellData(Long value) {
        WriteCellData <String> writeCellData;
        if (value instanceof Long) {
            Long longValue = (Long) value;
            // 将分转换为元
            writeCellData = new WriteCellData<>(BigDecimal.valueOf(longValue).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        } else {
            writeCellData = new WriteCellData<>(value.toString());
        }

        return writeCellData;
    }
}