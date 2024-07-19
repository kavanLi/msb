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
public class YchReconEnumConverter implements Converter <Integer> {
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
    public WriteCellData <?> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("Field: {}, Value: {}", fieldName, value);

        WriteCellData<String> writeCellData;

        switch (fieldName) {
            case "reconStatus":
                //// 0-未开始 1-系统对平 2-人工对平 3-对账失败
                //if (0 == NumberUtil.compare(CommConstants.ReconStatus.of(value).getCode(), 1) ||
                //        0 == NumberUtil.compare(CommConstants.ReconStatus.of(value).getCode(), 2)) {
                //    writeCellData = new WriteCellData<>("无差异");
                //} else {
                //    writeCellData = new WriteCellData<>("有差异");
                //}
                //break;
            default:
                writeCellData = new WriteCellData<>(value.toString());
                break;
        }

        return writeCellData;
    }
}