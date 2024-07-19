package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 会员认证信息表
 * @TableName smartyunst_member_auth
 */
@TableName(value ="smartyunst_member_auth")
@Data
public class SmartyunstMemberAuth implements Serializable {
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 应用（该会员归属的应用ID）
     */
    @TableField(value = "application_id")
    private String applicationId;

    /**
     * 外部业务系统名称
     */
    @TableField(value = "biz_user_id")
    private String bizUserId;

    /**
     * UUID
     */
    @TableField(value = "userid")
    private String userid;

    /**
     * 实名时间
     */
    @TableField(value = "real_name_time")
    private LocalDateTime realNameTime;

    /**
     * 证件类型 身份证	1 护照	2 军官证	3 回乡证	4 台湾通行证	5 警官证	6 士兵证	7 户口簿	8 港澳居民来往内地通行证	9 临时身份证	10 外国人居留证	11 港澳台居民居住证	12 其它证件	99
     */
    @TableField(value = "cer_type")
    private Long cerType;

    /**
     * 证件号码
     */
    @TableField(value = "identity_card_no")
    private String identityCardNo;

    /**
     * 证件号(加密)
     */
    @TableField(value = "identity_card_no_encrypt")
    private String identityCardNoEncrypt;

    /**
     * 证件有效开始日期
     */
    @TableField(value = "identity_begin_date")
    private String identityBeginDate;

    /**
     * 证件有效截止日期
     */
    @TableField(value = "identity_end_date")
    private String identityEndDate;

    /**
     * 证件认证时间
     */
    @TableField(value = "identity_auth_date")
    private LocalDateTime identityAuthDate;

    /**
     * 手机号认证时间
     */
    @TableField(value = "phone_auth_date")
    private LocalDateTime phoneAuthDate;

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
     * 绑卡方式
     */
    @TableField(value = "bind_type")
    private Integer bindType;

    /**
     * 手机号码
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 人脸认证时间
     */
    @TableField(value = "face_check_time")
    private LocalDateTime faceCheckTime;

    /**
     * 更新人
     */
    @TableField(value = "update_user_name")
    private String updateUserName;

    /**
     * 人脸认证总次数
     */
    @TableField(value = "face_identify_count")
    private Long faceIdentifyCount;

    /**
     * 人脸认证失败次数
     */
    @TableField(value = "face_identify_fail_count")
    private Long faceIdentifyFailCount;

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