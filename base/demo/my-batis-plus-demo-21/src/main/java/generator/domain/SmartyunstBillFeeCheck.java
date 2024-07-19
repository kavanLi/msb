package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 订单流对账结果表
 * @TableName smartyunst_bill_fee_check
 */
@TableName(value ="smartyunst_bill_fee_check")
@Data
public class SmartyunstBillFeeCheck implements Serializable {
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
     * 商户账单总交易笔数
     */
    @TableField(value = "bill_trans_count")
    private Long billTransCount;

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
     * 账单所属订单总交易笔数
     */
    @TableField(value = "order_trans_count")
    private Long orderTransCount;

    /**
     * 商户账单所属订单应收总金额（分）
     */
    @TableField(value = "order_total_amount")
    private Long orderTotalAmount;

    /**
     * 商户账单所属订单已收总金额（分）
     */
    @TableField(value = "order_total_paid_amount")
    private Long orderTotalPaidAmount;

    /**
     * 商户账单所属订单总渠道手续费（分）
     */
    @TableField(value = "order_total_channel_fee")
    private Long orderTotalChannelFee;

    /**
     * 商户账单所属订单总退款金额（分）
     */
    @TableField(value = "order_total_refund_amount")
    private Long orderTotalRefundAmount;

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