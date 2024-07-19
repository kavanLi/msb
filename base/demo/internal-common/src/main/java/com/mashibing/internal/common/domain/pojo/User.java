package com.mashibing.internal.common.domain.pojo;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.baomidou.mybatisplus.annotation.*;
import com.mashibing.internal.common.constraint.ListSizeRange;
import com.mashibing.internal.common.constraint.MapSizeRange;
import lombok.Data;
import lombok.ToString;

//@TableName(value = "t_user")
@ToString
@Data
public class User {
    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long uid;
    // 如果数据库中是 user_name 实体类中是 userName这种方式MyBatisPlus会自动帮助我们完成 驼峰命名法的转换
    @TableField("name") // 表结构中字段和实体类中的属性不一致的情况下可以设置映射关系
    private String userName;
    private Integer age;

    @NotBlank(message = "email不能为空")
    private String email;

    // 0 表示没有删除 1 表示已经逻辑删除了
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;

    /**
     * 对于复杂对象类型（如 Dept），Spring 在验证时不会默认递归验证内部字段。如果您希望验证复杂对象类型内部的字段，您需要在父类中手动添加对应的验证注解。
     *
     * 在这种情况下，您可以使用 @Valid 注解来指示 Spring 在验证 User 对象时，递归验证其内部的 EnterpriseBaseInfo 对象。这样，即使 Dept 是一个引用类型，其内部字段也会得到验证。
     */
    @Valid
    private Dept dept;

    /**
     证件类型 必填
     见枚举值，支持多种证件类型 注:绑卡方式8仅支持身份证
     名称	取值	对应收银宝取值	对应收付通取值
     身份证	1	0（收银宝支持）	0
     护照	2	2（收银宝支持）	2
     军官证	3	3	3
     回乡证	4	X	X
     台胞证	5	6（收银宝支持）	6
     警官证	6	9	9
     士兵证	7	4	4
     户口簿	8	1	1
     港澳居民来往内地通行证	9	5（收银宝支持）	5
     临时身份证	10	7	7
     外国人居留证	11	8	8
     港澳台居民居住证	12	X	X
     其它证件	99	X	X

     若绑卡，则银行卡四要素信息字段必须均上送。
     */
    @NotBlank(message = "证件类型不能为空 说明：支持多种证件类型")
    @Pattern(regexp = "^(1|2|3|4|5|6|7|8|9|10|11|12|99)$", message = "证件类型必须是指定的几种值 注：绑卡方式8仅支持身份证 " +
            "护照-2 " +
            "军官证-3 " +
            "回乡证-4 " +
            "台胞证-5 " +
            "警官证-6 " +
            "士兵证-7 " +
            "户口簿-8 " +
            "港澳居民来往内地通行证-9 " +
            "临时身份证-10 " +
            "外国人居留证-11 " +
            "港澳台居民居住证-12 " +
            "其它证件-99")
    private String cerType;

    /**
     法人手机号码 必填
     */
    @NotBlank(message = "法人手机号码不能为空")
    @Pattern(regexp = "^1[3,4,5,6,7,8,9]\\d{9}$",message = "请填写正确的法人手机号码")
    private String legalPersonPhone;

    /*
     支付行号
     12位数字 账户类型=1-对公，则必填
     */
    @Pattern(regexp = "^\\d{12}$",message = "请填写12位数字的支付行号")
    private String payBankNumber;

    /*
     交易验证方式 （余额）
     */
    @Pattern(regexp = "^[0-1]$", message = "交易验证方式 （余额）类型必须是指定的几种值 注：0：无验证 1：短信验证码 默认-1：短信验证码 ")
    private Long verifyType;

    /**
     对账文件日期 必填
     格式20240228
     */
    @NotBlank(message = "对账文件日期不能为空")
    @Pattern(regexp = "(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])", message = "日期格式必须为yyyyMMdd")
    private String fileDate;

    /**
     收款人列表 必填
     4.2.9	担保消费申请收款列表
     signNum String	商户会员编号 必填
     amount	Long 金额，单位：分 必填
     最多支持10个
     */
    //@ListMaxSize(value = 10, message = "收款人列表最多只能包含 {value} 个元素")
    @ListSizeRange(min = 1, max = 10, message = "收款人列表元素数量必须在 {min} 和 {max} 之间")
    private List <Map <String, Object>> receiverList;

    /*
     支付模式
     详见api文档和demo
     */
    @MapSizeRange(min = 1, max = -1, message = "支付模式的Map集合大小必须 >= {min}")
    private Map <String, Object> payMode;

    /**
     订单金额 必填
     */
    @Min(value = 0, message = "订单金额数值必须大于等于0 说明：单位：分 订单金额=支付金额+营销金额 ")
    private Long orderAmount;

}

