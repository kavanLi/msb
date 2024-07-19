package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 业务流对账结果 0-有差异1-无差异 表
 * @TableName smartyunst_bill_payment_check
 */
@TableName(value ="smartyunst_bill_payment_check")
@Data
public class SmartyunstBillPaymentCheck implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 集团编号
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * 集团名称
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
     * 科目款项编号
     */
    @TableField(value = "payment_id")
    private String paymentId;

    /**
     * 科目款项名称
     */
    @TableField(value = "payment_name")
    private String paymentName;

    /**
     * 收款方会员编号 款项信息里面的分账方
     */
    @TableField(value = "reciever_id")
    private String recieverId;

    /**
     * 收款方会员名称 款项信息里面的分账方
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
     * 对账完成类型 0-自动完成 1-人工触发
     */
    @TableField(value = "run_type")
    private Integer runType;

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

    /**
     * 款项总交易笔数
     */
    @TableField(value = "item_trans_count")
    private Long itemTransCount;

    /**
     * 款项成功交易笔数 判断条件is_receiver为是
     */
    @TableField(value = "item_success_trans_count")
    private Long itemSuccessTransCount;

    /**
     * 款项交易场景-款项收款笔数
     */
    @TableField(value = "item_payment_collection_count")
    private Long itemPaymentCollectionCount;

    /**
     * 款项交易场景-消费笔数
     */
    @TableField(value = "item_consume_count")
    private Long itemConsumeCount;

    /**
     * 款项交易场景-代收笔数
     */
    @TableField(value = "item_agent_collention_count")
    private Long itemAgentCollentionCount;

    /**
     * 款项交易场景-账单模式笔数
     */
    @TableField(value = "item_bill_mode_count")
    private Long itemBillModeCount;

    /**
     * 款项交易场景-其他笔数
     */
    @TableField(value = "item_other_count")
    private Long itemOtherCount;

    /**
     * 款项交易总金额（分）
     */
    @TableField(value = "item_total_amount")
    private Long itemTotalAmount;

    /**
     * 款项交易成功支付总金额（分） 判断条件is_receiver为是
     */
    @TableField(value = "item_success_paid_amount")
    private Long itemSuccessPaidAmount;

    /**
     * 款项交易场景-款项收款金额（分）
     */
    @TableField(value = "item_payment_collection_amount")
    private Long itemPaymentCollectionAmount;

    /**
     * 款项交易场景-消费收款金额（分）
     */
    @TableField(value = "item_consume_amount")
    private Long itemConsumeAmount;

    /**
     * 款项交易场景-代收收款金额（分）
     */
    @TableField(value = "item_agent_collention_amount")
    private Long itemAgentCollentionAmount;

    /**
     * 款项交易场景-账单模式收款金额（分）
     */
    @TableField(value = "item_bill_mode_amount")
    private Long itemBillModeAmount;

    /**
     * 款项交易场景-其他收款金额（分）
     */
    @TableField(value = "item_other_amount")
    private Long itemOtherAmount;

    /**
     * 款项总应代付手续费（分）
     */
    @TableField(value = "item_total_fee")
    private Long itemTotalFee;

    /**
     * 款项总已代付手续费（分）
     */
    @TableField(value = "item_success_split_fee")
    private Long itemSuccessSplitFee;

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
     * 对账结果 0-有差异1-无差异 
     */
    @TableField(value = "check_result")
    private Integer checkResult;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}