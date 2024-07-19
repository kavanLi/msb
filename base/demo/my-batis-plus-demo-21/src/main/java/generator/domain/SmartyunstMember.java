package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员表

 * @TableName smartyunst_member
 */
@TableName(value ="smartyunst_member")
@Data
public class SmartyunstMember implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 云商通会员uuid
     */
    @TableField(value = "user_id")
    private String userId;

    /**
     * 商户用户编号
     */
    @TableField(value = "biz_user_id")
    private String bizUserId;

    /**
     * 会员类型
     */
    @TableField(value = "member_type")
    private Long memberType;

    /**
     * 开放平台应用号
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * 会员状态
     */
    @TableField(value = "member_status")
    private Long memberStatus;

    /**
     * 会员名称
     */
    @TableField(value = "member_name")
    private String memberName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    private Long createBy;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    private Long updateBy;

    /**
     * 会员种类 1-付款方 2-收款方 3-分账方
     */
    @TableField(value = "category")
    private Long category;

    /**
     * 开户方式
     */
    @TableField(value = "open_type")
    private Integer openType;

    /**
     * 会员角色
     */
    @TableField(value = "member_role_id")
    private Long memberRoleId;

    /**
     * 是否绑定收银宝 0-未绑定 1-绑定
     */
    @TableField(value = "is_bind_syb")
    private Integer isBindSyb;

    /**
     * 是否默认提现卡 0-未设置 1-已设置
     */
    @TableField(value = "is_default_withdraw_card")
    private Integer isDefaultWithdrawCard;

    /**
     * 掩码卡号
     */
    @TableField(value = "masked_card_no")
    private String maskedCardNo;

    /**
     * 加密类型
     */
    @TableField(value = "encrypt_type")
    private Integer encryptType;

    /**
     * 所属行业
     */
    @TableField(value = "belong")
    private String belong;

    /**
     * 会员子账号
     */
    @TableField(value = "sub_acct_no")
    private String subAcctNo;

    /**
     * 账户集编号
     */
    @TableField(value = "account_set_no")
    private String accountSetNo;

    /**
     * 默认收银宝终端号
     */
    @TableField(value = "default_syb_term_id")
    private String defaultSybTermId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}