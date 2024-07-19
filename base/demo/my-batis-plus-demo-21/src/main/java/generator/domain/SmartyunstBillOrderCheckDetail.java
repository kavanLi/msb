package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 支付流对账明细表
 * @TableName smartyunst_bill_order_check_detail
 */
@TableName(value ="smartyunst_bill_order_check_detail")
@Data
public class SmartyunstBillOrderCheckDetail implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用号
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * 应用名称
     */
    @TableField(value = "application_name")
    private String applicationName;

    /**
     * 门店id
     */
    @TableField(value = "store_id")
    private String storeId;

    /**
     * 门店名称
     */
    @TableField(value = "store_name")
    private String storeName;

    /**
     * 分公司编号
     */
    @TableField(value = "branch_id")
    private String branchId;

    /**
     * 分公司名称
     */
    @TableField(value = "branch_name")
    private String branchName;

    /**
     * 收款方会员编号
     */
    @TableField(value = "reciever_id")
    private String recieverId;

    /**
     * 收款方会员名称
     */
    @TableField(value = "reciever_name")
    private String recieverName;

    /**
     * 对账批次
     */
    @TableField(value = "check_batch_no")
    private String checkBatchNo;

    /**
     * 对账日期
     */
    @TableField(value = "check_date")
    private LocalDateTime checkDate;

    /**
     * 对账状态 0-未开始 1-完成对账 2-对账失败
     */
    @TableField(value = "recon_status")
    private Integer reconStatus;

    /**
     * 对账操作人
     */
    @TableField(value = "recon_operator")
    private String reconOperator;

    /**
     * 对账结果
     */
    @TableField(value = "check_result")
    private Integer checkResult;

    /**
     * 对账完成类型 0-自动完成 1-人工触发
     */
    @TableField(value = "run_type")
    private Integer runType;

    /**
     * 账单号/单据号
     */
    @TableField(value = "service_order_no")
    private String serviceOrderNo;

    /**
     * 商户订单号/流水号
     */
    @TableField(value = "biz_order_no")
    private String bizOrderNo;

    /**
     * 商户账单总金额（分）
     */
    @TableField(value = "bill_total_amount")
    private Long billTotalAmount;

    /**
     * 商户账单已付总金额（分）
     */
    @TableField(value = "bill_paid_amount")
    private Long billPaidAmount;

    /**
     * 订单交易金额（分）
     */
    @TableField(value = "order_amount")
    private Long orderAmount;

    /**
     * 订单渠道手续费（分）
     */
    @TableField(value = "order_channel_fee")
    private Long orderChannelFee;

    /**
     * 支付方式 1-H5收银台 2-微信JS 3-支付宝JS
     */
    @TableField(value = "pay_mode")
    private String payMode;

    /**
     * 付款场景 1-订单收款码 2-远程支付 3-门店码牌 4-POS收款
     */
    @TableField(value = "pay_scene")
    private String payScene;

    /**
     * 订单生成时间
     */
    @TableField(value = "order_generate_time")
    private LocalDateTime orderGenerateTime;

    /**
     * 订单完成时间
     */
    @TableField(value = "order_finish_time")
    private LocalDateTime orderFinishTime;

    /**
     * 订单支付时间
     */
    @TableField(value = "order_pay_time")
    private LocalDateTime orderPayTime;

    /**
     * 账单订单日期
     */
    @TableField(value = "bill_order_time")
    private LocalDateTime billOrderTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 更新人
     */
    @TableField(value = "modifier")
    private String modifier;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}