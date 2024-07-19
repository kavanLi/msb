package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员基础信息表
 * @TableName ums_member
 */
@TableName(value ="ums_member")
@Data
public class UmsMember implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用编号
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * 微信授权登录ID
     */
    @TableField(value = "open_id")
    private String openId;

    /**
     * 会员编号
     */
    @TableField(value = "biz_user_id")
    private String bizUserId;

    /**
     * 手机号码/联系电话
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 头像
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 国家
     */
    @TableField(value = "country")
    private String country;

    /**
     * 省
     */
    @TableField(value = "province")
    private String province;

    /**
     * 地区
     */
    @TableField(value = "area")
    private String area;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    private String nickname;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    private Date birthday;

    /**
     * 所在城市
     */
    @TableField(value = "city")
    private String city;

    /**
     * 个性签名
     */
    @TableField(value = "personalized_signature")
    private String personalizedSignature;

    /**
     * 职业
     */
    @TableField(value = "job")
    private String job;

    /**
     * 国籍：中国
     */
    @TableField(value = "nationality")
    private String nationality;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 性别：0->未知；1->男；2->女
     */
    @TableField(value = "gender")
    private Integer gender;

    /**
     * 会员等级编号
     */
    @TableField(value = "member_level_id")
    private Long memberLevelId;

    /**
     * 用户来源
     */
    @TableField(value = "source_type")
    private Integer sourceType;

    /**
     * 帐号启用状态:0->禁用；1->启用
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 积分（后续抽离）
     */
    @TableField(value = "integration")
    private Integer integration;

    /**
     * 成长值（后续抽离）
     */
    @TableField(value = "growth")
    private Integer growth;

    /**
     * 剩余抽奖次数（后续抽离）
     */
    @TableField(value = "luckey_count")
    private Integer luckeyCount;

    /**
     * 历史积分数量（后续抽离）
     */
    @TableField(value = "history_integration")
    private Integer historyIntegration;

    /**
     * 注册时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 是否设置安全问题
     */
    @TableField(value = "is_security_issue")
    private Integer isSecurityIssue;

    /**
     * 是否设置扩展信息
     */
    @TableField(value = "is_set_extend_info")
    private Integer isSetExtendInfo;

    /**
     * 是否实名认证
     */
    @TableField(value = "is_identity_checked")
    private Integer isIdentityChecked;

    /**
     * 手机号码是否验证
     */
    @TableField(value = "is_phone_checked")
    private Integer isPhoneChecked;

    /**
     * 是否人脸认证
     */
    @TableField(value = "is_face_check")
    private Integer isFaceCheck;

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