package com.mashibing.internal.common.domain.pojo;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 商品订单表
 * @author lijj
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("saasyunst_mcht_order")
public class SaasyunstMchtOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    /**主键*/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**应用号*/
    private String applicationId;

    /**应用名称*/
    private String applicationName;

    /**交易场景 1-码牌交易*/
    private String scene;

    /**商户订单号*/
    private String bizOrderNo;

    /**云商通订单号*/
    private String orderNo;

    /**付款人会员编号*/
    private String payerId;

    /**付款人会员名称*/
    private String payerName;

    /**收款方会员编号*/
    private String recieverId;

    /**收款方会员名称*/
    private String recieverName;

    /**商品编号*/
    private Long goodsId;

    /**商品名称*/
    private String goodsName;

    /**订单金额  分*/
    private Long amount;

    /**订单状态  1-未支付 / 2-进行中 / 3-交易失败 / 4-交易完成 / 5-交易完成-发生退款 / 0-关闭 */
    private String status;

    /**手续费  分 */
    private Long fee;

    /**渠道手续费  分 */
    private Long channelFee;


    /**交易到账类型  1-即时到账  2-担保交易 */
    private String receiveType;

    /**账期  天 */
    private Long receiveDay;

    /**订单生成时间 */
    private Date orderGenerateTime;

    /**订单完成时间 */
    private Date orderFinishTime;

    /**订单支付时间 */
    private Date orderPayTime;

    /**退款金额  分 */
    private Long refundAmount;

    /**退款时间 */
    private Date refundTime;

    /**退款商户订单号 */
    private String refundBizOrderNo;

    /**退款云商通订单号 */
    private String refundOrderNo;

    /**退款方式  默认D1 */
    private String refundType;

    /**退款状态 */
    private String refundStatus;

    /**是否已收款 */
    private String isReceived;

    /**预计资金到账时间 */
    private Date receivedEstimateTime;

    /**实际资金到账时间 */
    private Date receivedActualTime;

    /**托付商户订单号 */
    private String agentPayBizOrderNo;

    /**备注 */
    private String remark;

    /**分账信息 */
    private String allocation;

    /**创建时间 */
    private Date createTime;

    /**更新时间 */
    private Date updateTime;

    /**收款规则编号 */
    private Long ruleId;

    /** 订单失败原因 */
    private String failMessage;

    /**
     * 退款失败原因
     */
    private String refundFailMessage;
    /**
     * 代付失败原因
     */
    private String agentPayFailMessage;

    /**
     * 退款完成时间
     */
    private Date refundFinishTime;

    /**
     * 代付完成时间
     */
    private Date agentPayFinishTime;

    /**
     * 账期时间
     */
    private Date receiveDate;

    /**
     * 代付状态
     */
    private String agentPayStatus;

    /**
     * 同步代收、消费订单状态次数
     */
    private Integer syncStatusCount;

    /**
     * 同步退款订单状态
     */
    private Integer syncRefundStatusCount;

    /**
     * 同步代付订单状态
     */
    private Integer syncAgentPayCount;

}
