package com.mashibing.internalcommon.utils.easyExcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
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
public class YchDailyFee4StatusConverter implements Converter <String> {
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
    public WriteCellData <?> convertToExcelData(String value, ExcelContentProperty contentProperty,
                                                GlobalConfiguration globalConfiguration) {
        System.out.println("---------------" + value);
        /**订单状态  1-未支付 3-交易失败 4-交易成功 5-交易成功发生退款 6-关闭 99-进行中 8-交易成功发生退票*/
        switch (value) {
            case "1":
                value = "未支付";
                break;
            case "3":
                value = "交易失败";
                break;
            case "4":
                value = "交易成功";
                break;
            case "5":
                value = "交易成功发生退款";
                break;
            case "6":
                value = "进行中";
                break;
            case "99":
                value = "提现";
                break;
            case "8":
                value = "交易成功发生退票";
                break;
            default:
                break;
        }
        return new WriteCellData(value);
    }
}