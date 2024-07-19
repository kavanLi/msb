package com.mashibing.internal.common.utils.easyExcel;

import java.time.LocalDateTime;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mashibing.internal.common.utils.easyExcel.converter.LongFormatConverter;
import com.mashibing.internal.common.utils.easyExcel.converter.YchReconEnumConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
@Accessors(chain=true)
public class YchBillFlowReconDetailDownloadData {

    /**
     * 忽略这个字段
     */
    /**主键*/
    @ExcelIgnore
    private Long id;

    /**
     * 平台编号
     */
    @ExcelProperty("平台编号")
    private String applicationId;
    /**
     * 平台名称
     */
    @ExcelProperty("平台名称")
    private String applicationName;
    /**
     * 门店编号
     */
    @ExcelProperty("门店编号")
    private String storeId;
    /**
     * 门店名称
     */
    @ExcelProperty("门店名称")
    private String storeName;
    /**
     * 对账批次号
     */
    @ExcelProperty("对账批次号")
    private String checkBatchNo;

    /**
     * 账单号/单据号
     */
    @ExcelProperty("单据号")
    private String serviceOrderNo;

    /**
     * 账单总笔数
     */
    //@ExcelProperty("账单总笔数")
    //private Long billTransCount;

    /**
     * 价格，单位：分
     */
    @ExcelProperty(value = "账单总金额（元）", converter = LongFormatConverter.class)
    private Long billAmount;

    /**
     * 价格，单位：分
     */
    @ExcelProperty(value = "账单已付总金额（元）", converter = LongFormatConverter.class)
    private Long billPaidAmount;

    /**
     * 账单日期
     */
    @ExcelProperty("账单日期")
    private Date billOrderTime;

    /**
     * 对账状态
     */
    @ExcelProperty(value = "对账状态", converter = YchReconEnumConverter.class)
    private Integer reconStatus;

    /**
     * 对账日期
     */
    @ExcelProperty("对账日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkDate;

    /**
     * 对账操作人
     */
    @ExcelProperty("对账操作人")
    private String reconOperator;



}
