package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 业务流对账明细表
 * @TableName smartyunst_bill_payment_check_detail
 */
@TableName(value ="smartyunst_bill_payment_check_detail")
@Data
public class SmartyunstBillPaymentCheckDetail implements Serializable {
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
     * 账单号/单据号
     */
    @TableField(value = "service_order_no")
    private String serviceOrderNo;

    /**
     * 账单号/单据号
     */
    @TableField(value = "ori_agent_collect_biz_order_no")
    private String oriAgentCollectBizOrderNo;

    /**
     * 款项价格/款项分账金额（分）
     */
    @TableField(value = "item_amount")
    private Long itemAmount;

    /**
     * 是否已收款
     */
    @TableField(value = "is_receiver")
    private Integer isReceiver;

    /**
     * 款项手续费（分）
     */
    @TableField(value = "item_fee")
    private Long itemFee;

    /**
     * 款项交易场景 2-款项收款 3-消费 4-代收 8-账单模式
     */
    @TableField(value = "item_trans_scene")
    private Integer itemTransScene;

    /**
     * 是否固定落地数据 1是固定落地数据的情况下是模板数据，不能作为参考
     */
    @TableField(value = "is_fixed")
    private Integer isFixed;

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