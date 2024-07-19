package com.mashibing.internal.common.utils.easyExcel;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.mashibing.internal.common.utils.easyExcel.converter.YchMchtOrderEnumConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@EqualsAndHashCode
@ColumnWidth(25)
@Accessors(chain=true)
public class YchMchtOrderDownloadData {

    /**
     * 忽略这个字段
     */
    /**主键*/
    @ExcelIgnore
    private Long id;

    /**商户订单号*/
    @ExcelProperty("商户订单号")
    private String bizOrderNo;

    /**云商通订单号*/
    @ExcelProperty("云商通订单号")
    private String orderNo;

    /**付款人会员编号*/
    @ExcelProperty("付款方信息")
    private String payerId;

    /**付款人会员名称*/
    @ExcelProperty("付款方信息")
    private String payerName;

    /**收款方会员编号*/
    @ExcelProperty("收款门店信息")
    private String recieverId;

    /**收款方会员名称*/
    @ExcelProperty("收款门店信息")
    private String recieverName;

    /**交易状态  1-未支付 / 2-进行中 / 3-交易失败 / 4-交易完成 / 5-交易完成-发生退款 / 0-关闭 */
    @ExcelProperty(value = "交易状态", converter = YchMchtOrderEnumConverter.class)
    private String status;

    /**订单金额  分*/
    @ExcelProperty("交易金额（分）")
    private Long amount;

    ///**手续费  分 */
    //@ExcelProperty("消费手续费")
    //private Long fee;

    /**渠道手续费/手续费承担金额（分） */
    @ExcelProperty("渠道手续费（分）")
    private Long channelFee;

    /**是否已收款 */
    @ExcelProperty(value = "是否已收款", converter = YchMchtOrderEnumConverter.class)
    private String isReceived;

    /**订单生成时间 */
    @ExcelProperty("订单生成时间")
    private Date orderGenerateTime;

    /**订单完成时间 */
    @ExcelProperty("订单完成时间")
    private Date orderFinishTime;

    /**订单支付时间 */
    @ExcelProperty("订单支付时间")
    private Date orderPayTime;

    ///**退款金额  分 */
    //@ExcelProperty("退款金额")
    //private Long refundAmount;
    //
    ///**退款时间 */
    //@ExcelProperty("退款时间")
    //private Date refundTime;
    //
    ///**退款商户订单号 */
    //@ExcelProperty("退款商户订单号")
    //private String refundBizOrderNo;
    //
    ///**退款云商通订单号 */
    //@ExcelProperty("退款支付订单号")
    //private String refundOrderNo;

    ///**预计资金到账时间 */
    // @ExcelProperty("预计资金到账时间")
    //private Date receivedEstimateTime;
    //
    ///**实际资金到账时间 */
    // @ExcelProperty("实际资金到账时间")
    //private Date receivedActualTime;
    //
    ///**托付商户订单号 */
    // @ExcelProperty("托付商户订单号")
    //private String agentPayBizOrderNo;

    /**备注 */
     @ExcelProperty("备注")
    private String remark;



}
