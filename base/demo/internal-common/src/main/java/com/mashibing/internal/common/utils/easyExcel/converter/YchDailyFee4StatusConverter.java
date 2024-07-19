package com.mashibing.internal.common.utils.easyExcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.mashibing.internal.common.constant.MchtOrderConstants;
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
        log.info("---------------" + value);
        /**订单状态  1-未支付 3-交易失败 4-交易成功 5-交易成功发生退款 6-关闭 99-进行中 8-交易成功发生退票*/
        return new WriteCellData(MchtOrderConstants.MchtOrderScene.of(value).getDesc());
    }
}