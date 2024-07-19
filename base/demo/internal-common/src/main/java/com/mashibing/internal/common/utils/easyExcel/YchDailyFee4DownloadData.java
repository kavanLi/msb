package com.mashibing.internal.common.utils.easyExcel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.mashibing.internal.common.utils.easyExcel.converter.YchDailyFee4SceneConverter;
import com.mashibing.internal.common.utils.easyExcel.converter.YchDailyFee4StatusConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
@Accessors(chain=true)
public class YchDailyFee4DownloadData {

    /**
     * 忽略这个字段
     */
    /**主键*/
    @ExcelIgnore
    private Long id;

    /**收款方会员编号*/
    @ExcelProperty("收款方会员编号")
    private String recieverId;

    /**收款方会员名称*/
    @ExcelProperty("收款方会员名称")
    private String recieverName;
    @ExcelProperty("手续费承担方会员编号")
    private String paymentReceiverId;
    @ExcelProperty("手续费承担方员名称")
    private String paymentReceiverName;

    /**手续费承担方信息 */
    @ExcelProperty("手续费承担方信息")
    private String feeIdsStr;

    /**交易场景 1码牌交易 2消费 4提现*/
    @ExcelProperty(value = "交易场景", converter = YchDailyFee4SceneConverter.class)
    private String scene;

    /**商户订单号*/
    @ExcelProperty("商户订单号")
    private String bizOrderNo;


    /**订单状态  1-未支付 3-交易失败 4-交易成功 5-交易成功发生退款 6-关闭 99-进行中 8-交易成功发生退票*/
    @ExcelProperty(value = "订单状态", converter = YchDailyFee4StatusConverter.class)
    private String status;

    /**订单金额  分*/
    @ExcelProperty("订单金额（分）")
    private Long amount;

    /**手续费  分 */
    @ExcelProperty("手续费（分）")
    private Long fee;

    /**渠道手续费  分 */
    @ExcelProperty("渠道手续费（分）")
    private Long channelFee;

    /**订单生成时间 */
    @ExcelProperty("订单生成时间")
    private Date orderGenerateTime;



}
