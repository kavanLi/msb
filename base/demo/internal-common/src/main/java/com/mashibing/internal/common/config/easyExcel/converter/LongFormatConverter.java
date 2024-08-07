package com.mashibing.internal.common.config.easyExcel.converter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
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
     * 读取调用
     */
    @Override
    public Long convertToJavaData(ReadCellData <?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        Field field = contentProperty.getField();
        ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
        String[] excelPropertyValues = excelProperty.value();

        // 这里假设只有一个值，可以根据需要处理多个值的情况
        String fieldName = field.getName();
        String fieldConvertName = excelPropertyValues.length > 0 ? excelPropertyValues[0] : "";
        BigDecimal value = null;

        try {
            value = cellData.getNumberValue();
            log.info("read from excel, field: {}, Value: {}", fieldConvertName, value);

            if (Objects.isNull(value)) {
                return null;
            }
            // 转换为分
            BigDecimal fen = value.multiply(new BigDecimal(100));
            // 检查是否溢出 long 类型范围
            if (fen.compareTo(new BigDecimal(Long.MAX_VALUE)) > 0 || fen.compareTo(new BigDecimal(Long.MIN_VALUE)) < 0) {
                throw new IllegalArgumentException("表格" + fieldConvertName + "列中存在超出范围的参数：" + value);
            }
            return fen.longValue();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("表格" + fieldConvertName + "列中存在非法的导入参数：" + value);
        }
    }

    /**
     * 写入调用
     */
    @Override
    public WriteCellData <?> convertToExcelData(Long value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("write to excel, Field: {}, Value: {}", fieldName, value);

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