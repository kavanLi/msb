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
public class YchServiceOrderInfoEnumConverter implements Converter <String> {
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
        String fieldName = contentProperty.getField().getName();
        log.info("Field: {}, Value: {}", fieldName, value);

        WriteCellData<String> writeCellData = new WriteCellData<>("");

        switch (fieldName) {
            case "certType":
                if ("1".equals(value)) {
                    writeCellData = new WriteCellData<>("身份证");
                } else if ("2".equals(value)) {
                    writeCellData = new WriteCellData<>("护照");
                } else if ("3".equals(value)) {
                    writeCellData = new WriteCellData<>("军官证");
                } else if ("4".equals(value)) {
                    writeCellData = new WriteCellData<>("回乡证");
                } else if ("5".equals(value)) {
                    writeCellData = new WriteCellData<>("台胞证");
                } else if ("6".equals(value)) {
                    writeCellData = new WriteCellData<>("警官证");
                } else if ("7".equals(value)) {
                    writeCellData = new WriteCellData<>("士兵证");
                } else if ("8".equals(value)) {
                    writeCellData = new WriteCellData<>("户口簿");
                } else if ("9".equals(value)) {
                    writeCellData = new WriteCellData<>("港澳居民来往内地通行证");
                } else if ("10".equals(value)) {
                    writeCellData = new WriteCellData<>("临时身份证");
                } else if ("11".equals(value)) {
                    writeCellData = new WriteCellData<>("外国人居留证");
                } else if ("12".equals(value)) {
                    writeCellData = new WriteCellData<>("港澳台居民居住证");
                } else if ("99".equals(value)) {
                    writeCellData = new WriteCellData<>("其它证件");
                } else {
                    writeCellData = new WriteCellData<>("未知证件类型");
                }
                break;
            case "customerType":
                writeCellData = new WriteCellData<>(value.equals("1") ? "是" : "否");
                break;
            case "collectionFlag":
                if (value.equals("STANDARD")) {
                    writeCellData = new WriteCellData<>("标准收款");
                }
                if (value.equals("ITEM")) {
                    writeCellData = new WriteCellData<>("款项收款");
                }
                break;
            case "status":
                if (value.equals("0")) {
                    writeCellData = new WriteCellData<>("未支付");
                }
                if (value.equals("1")) {
                    writeCellData = new WriteCellData<>("部分支付");
                }
                if (value.equals("2")) {
                    writeCellData = new WriteCellData<>("完成");
                }
                break;
            case "settleStatus":
                if (value.equals("0")) {
                    writeCellData = new WriteCellData<>("未结算");
                }
                if (value.equals("1")) {
                    writeCellData = new WriteCellData<>("结算中");
                }
                if (value.equals("2")) {
                    writeCellData = new WriteCellData<>("结算成功");
                }
                if (value.equals("3")) {
                    writeCellData = new WriteCellData<>("结算失败");
                }
                break;
            case "isRealName":
                if (value.equals("COR")) {
                    writeCellData = new WriteCellData<>("单位");
                }
                if (value.equals("IND")) {
                    writeCellData = new WriteCellData<>("个人");
                }
                break;
            default:
                writeCellData = new WriteCellData<>(value.toString());
                break;
        }

        return writeCellData;
    }
}