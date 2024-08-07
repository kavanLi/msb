package com.mashibing.internal.common.config.easyExcel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.mashibing.internal.common.config.easyExcel.converter.LongFormatConverter;
import com.mashibing.internal.common.config.easyExcel.converter.YchDailyFee4SceneConverter;
import com.mashibing.internal.common.config.easyExcel.converter.YchDailyFee4StatusConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @Accessors(chain = true) 读excel不能使用这个注解，否则会解析失败
 * https://blog.51cto.com/YangPC/5483482
 */


@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
public class YchDailyFee4DownloadData {

    /**
     * 忽略这个字段
     */
    /**
     * 主键
     */
    @ExcelIgnore
    private Long id;

    @ExcelProperty("集团名称")
    private String applicationName;

    @ExcelProperty("资金去向方")
    private String recieverId;

    @ExcelProperty("资金去向方")
    private String recieverName;

    @ExcelProperty(value = "交易状态", converter = YchDailyFee4StatusConverter.class)
    private String status;

    @ExcelProperty(value = "交易类型", converter = YchDailyFee4SceneConverter.class)
    private String scene;

    @ExcelProperty(value = "交易金额（元）", converter = LongFormatConverter.class)
    private Long amount;

    @ExcelProperty(value = "渠道手续费（元）")
    private String feeIdsStr;

    @ExcelProperty(value = "渠道手续费（元）")
    @ExcelIgnore
    private Long channelFee;

    @ExcelProperty("交易时间")
    private Date orderGenerateTime;

    @ExcelProperty("手续费承担方")
    private String paymentReceiverId;
    @ExcelProperty("手续费承担方")
    private String paymentReceiverName;

    @ExcelProperty("交易订单号")
    private String bizOrderNo;


}
