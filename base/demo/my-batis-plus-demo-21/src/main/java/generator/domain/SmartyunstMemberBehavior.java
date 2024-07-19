package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员行为表
 * @TableName smartyunst_member_behavior
 */
@TableName(value ="smartyunst_member_behavior")
@Data
public class SmartyunstMemberBehavior implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 开放平台应用号
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * UUID
     */
    @TableField(value = "userid")
    private String userid;

    /**
     * 外部业务系统名称
     */
    @TableField(value = "biz_user_id")
    private String bizUserId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * ip地址
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 登录类型：0->PC；1->android;2->ios;3->小程序
     */
    @TableField(value = "login_type")
    private Integer loginType;

    /**
     * 省份
     */
    @TableField(value = "province")
    private String province;

    /**
     * 注册时间
     */
    @TableField(value = "register_time")
    private LocalDateTime registerTime;

    /**
     * 登录失败次数
     */
    @TableField(value = "login_fail_amount")
    private Long loginFailAmount;

    /**
     * 支付失败次数
     */
    @TableField(value = "pay_fail_amount")
    private Long payFailAmount;

    /**
     * 是否设置安全问题
     */
    @TableField(value = "is_security_issue")
    private Boolean isSecurityIssue;

    /**
     * 是否设置扩展信息
     */
    @TableField(value = "is_set_extend_info")
    private Boolean isSetExtendInfo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SmartyunstMemberBehavior other = (SmartyunstMemberBehavior) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getBizUserId() == null ? other.getBizUserId() == null : this.getBizUserId().equals(other.getBizUserId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getLoginType() == null ? other.getLoginType() == null : this.getLoginType().equals(other.getLoginType()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getRegisterTime() == null ? other.getRegisterTime() == null : this.getRegisterTime().equals(other.getRegisterTime()))
            && (this.getLoginFailAmount() == null ? other.getLoginFailAmount() == null : this.getLoginFailAmount().equals(other.getLoginFailAmount()))
            && (this.getPayFailAmount() == null ? other.getPayFailAmount() == null : this.getPayFailAmount().equals(other.getPayFailAmount()))
            && (this.getIsSecurityIssue() == null ? other.getIsSecurityIssue() == null : this.getIsSecurityIssue().equals(other.getIsSecurityIssue()))
            && (this.getIsSetExtendInfo() == null ? other.getIsSetExtendInfo() == null : this.getIsSetExtendInfo().equals(other.getIsSetExtendInfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getBizUserId() == null) ? 0 : getBizUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getLoginType() == null) ? 0 : getLoginType().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getRegisterTime() == null) ? 0 : getRegisterTime().hashCode());
        result = prime * result + ((getLoginFailAmount() == null) ? 0 : getLoginFailAmount().hashCode());
        result = prime * result + ((getPayFailAmount() == null) ? 0 : getPayFailAmount().hashCode());
        result = prime * result + ((getIsSecurityIssue() == null) ? 0 : getIsSecurityIssue().hashCode());
        result = prime * result + ((getIsSetExtendInfo() == null) ? 0 : getIsSetExtendInfo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", userid=").append(userid);
        sb.append(", bizUserId=").append(bizUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", ip=").append(ip);
        sb.append(", city=").append(city);
        sb.append(", loginType=").append(loginType);
        sb.append(", province=").append(province);
        sb.append(", registerTime=").append(registerTime);
        sb.append(", loginFailAmount=").append(loginFailAmount);
        sb.append(", payFailAmount=").append(payFailAmount);
        sb.append(", isSecurityIssue=").append(isSecurityIssue);
        sb.append(", isSetExtendInfo=").append(isSetExtendInfo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}