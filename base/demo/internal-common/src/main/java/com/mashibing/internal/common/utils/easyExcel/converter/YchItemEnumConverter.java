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
     * 写入调用
     */
    @Override
    public WriteCellData <?> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                                GlobalConfiguration globalConfiguration) {
        String fieldName = contentProperty.getField().getName();
        log.info("Field: {}, Value: {}", fieldName, value);

        WriteCellData<String> writeCellData;

        switch (fieldName) {
            //case "registryFlag":
            //    writeCellData = new WriteCellData<>(ItemManagementConstants.ItemTier.of(value).getDesc());
            //    break;
            //case "itemTier":
            //    // 科目层级，1-科目类别 2-科目明细
            //    writeCellData = new WriteCellData<>(ItemManagementConstants.ItemTier.of(value).getDesc());
            //    break;
            //case "itemScene":
            //    // 应用场景，1-POS款项 2-H5款项
            //    writeCellData = new WriteCellData<>(ItemManagementConstants.ItemScene.of(value).getDesc());
            //    break;
            //case "status":
            //    // 启用状态，1-启用 0-禁用
            //    writeCellData = new WriteCellData<>(ItemManagementConstants.ItemStatus.of(value).getDesc());
            //    break;
            default:
                writeCellData = new WriteCellData<>(value.toString());
                break;
        }

        return writeCellData;
    }
}