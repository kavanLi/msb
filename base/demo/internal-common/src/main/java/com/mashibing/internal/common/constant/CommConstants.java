package com.mashibing.internal.common.constant;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CommConstants {

    /**
     * 渠道
     */
    public enum ChannelType {

        WECOM("1", "企业微信"), WECHAT_MINI_PROGRAM("2", "微信小程序"), ALIPAY__MINI_PROGRAM("3", "支付宝小程序"),
        OFFICIAL_ACCOUNT("4", "公众号"),VIDEO_ID("5", "视频号"),TIKTOK("6", "抖音");
        private final String key;
        private final String value;


        ChannelType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 对账完成类型
     */
    public static String RUN_TYPE = "0-自动完成 1-人工触发";

    public enum RunType {

        AUTO(0, "自动完成"),
        MANUAL(1, "人工触发"),
        ;

        private final int code;
        private final String desc;

        RunType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static RunType of(int code) {
            for (RunType status : RunType.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (RunType status : RunType.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            return false;

            //throw new SmartyunstException(ServerRespCode.ERROR_PARAM, CommConstants.RUN_TYPE + "参数不合法！");
        }
    }

    /**
     * 对账状态
     */
    public static String RECON_STATUS = "0-未开始 1-系统对平 2-人工对平 3-对账失败";

    public enum ReconStatus {

        NOT_START(0, "未开始"),
        SUCCESS(1, "系统对平"),
        MANUAL_SUCCESS(2, "人工对平"),
        FAIL(3, "对账失败"),
        ;

        private final int code;
        private final String desc;

        ReconStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static ReconStatus of(int code) {
            for (ReconStatus status : ReconStatus.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (ReconStatus status : ReconStatus.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            return false;
            //throw new SmartyunstException(ServerRespCode.ERROR_PARAM, CommConstants.RECON_STATUS + "参数不合法！");
        }
    }

    /**
     * 对账结果
     */
    public static String CHECK_RESULT = "0-有差异 1-无差异 ";

    public enum CheckResult {

        DIFF(0, "有差异"),
        SAME(1, "无差异"),
        ;

        private final int code;
        private final String desc;

        CheckResult(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static CheckResult of(int code) {
            for (CheckResult status : CheckResult.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (CheckResult status : CheckResult.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            return false;

            //throw new SmartyunstException(ServerRespCode.ERROR_PARAM, CommConstants.CHECK_RESULT + "参数不合法！");
        }
    }
    public static String GENDER_STATUS = "性别：0->未知；1->男；2->女";

    public enum GenderStatus {

        UNKNOWN(0, "未知"),
        MALE(1, "男"),
        FEMALE(2, "女"),
        ;

        private final int code;
        private final String desc;

        GenderStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static GenderStatus of(int code) {
            for (GenderStatus status : GenderStatus.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (GenderStatus status : GenderStatus.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            return false;

            //throw new SmartyunstException(ServerRespCode.ERROR_PARAM, CommConstants.GENDER_STATUS + "参数不合法！");
        }
    }


    public static String REGISTER_MODE_STATUS = "注册方式：0->会员注册 1->微信授权注册 2->手机验证码注册";

    public enum RegisterMode {

        REGISTER_MODE_0(0, "会员注册"),
        REGISTER_MODE_1(1, "微信授权注册"),
        REGISTER_MODE_2(2, "手机验证码注册"),
        ;

        private final int code;
        private final String desc;

        RegisterMode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static RegisterMode of(int code) {
            for (RegisterMode status : RegisterMode.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (RegisterMode status : RegisterMode.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            return false;
            //throw new SmartyunstException(ServerRespCode.ERROR_PARAM, CommConstants.REGISTER_MODE_STATUS + "参数不合法！");
        }
    }

    /**
     * 云商通二代订单状态
     */
    public enum Yst2OrderStatus {
        PENDING("0", "进行中"), SUCCESS("1", "交易成功"), FAIL("2", "交易失败");

        private final String key;
        private final String value;

        Yst2OrderStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 会员类型
     */
    public enum MemberType {
        PERSONAL(3L, "个人会员"), COMPANY(2L, "企业会员");

        private final Long key;
        private final String value;

        MemberType(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 企业审核状态
     */
    public enum CompanyValidStatus {
        STANDBY(1L, "待审核"), SUCCESS(2L, "审核成功"), FAIL(3L, "审核失败");

        private final Long key;

        private final String value;

        CompanyValidStatus(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 会员状态
     */
    public enum MemberStatus {
        VALID(1L, "有效"), FAIL(3L, "审核失败"), LOCKED(5L, "已锁定"), STANDBY(7L, "待审核"),
        BLACKLIST(9L, "黑名单"), RISK_BLACKLIST(11L, "风控黑名单"), LOGOUT(13L, "注销"),
        BANK_AUDITING(15L, "银行审核中"), BANK_AUDIT_FAIL(17L, "银行审核失败");

        private final Long key;
        private final String value;

        MemberStatus(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 员工状态
     */
    public enum EmployeeStatus {
        NORMAL("1", "正常"), FREEZE("2", "冻结"), DELETED("3", "已删除");

        private final String key;
        private final String value;

        EmployeeStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 员工开放查询的状态
     */
    public static final List <String> EMPLOYEE_STATUS_QUERY_LIST = Arrays.asList("1", "2");


    public enum MemberCategory {
        PAYER(1L, "付款方"), RECEIVER(2L, "收款方"), DIVIDER(3L, "分账方");

        private final Long key;
        private final String value;

        MemberCategory(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum AgreementType {
        RECEIVER(2L, "收款方"), DIVIDER(1L, "分账方");

        private final Long key;
        private final String value;

        AgreementType(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Source {
        MOBILE(1L, "mobile"), PC(2L, "PC");

        private final Long key;
        private final String value;

        Source(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }

    public final static String dateTime = "yyyy-MM-dd HH:mm:ss";


    public enum PayStatus {
        SUCCESS("1", "success"), PENDING("2", "pending"), FAIL("3", "fail");


        private final String key;

        private final String value;

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        PayStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public enum SettleType {
        AUTO(1, "自动结算"), MANUAL(2, "手动结算");

        private final Integer key;
        private final String value;

        SettleType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SettleStatus {
        DELETE("0", "删除"), WORKFUL("1", "有效");

        private final String key;
        private final String value;

        SettleStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum OrderCategory {
        AGENT_COLLECT("agentCollect", "代收"), AGENT_PAY("agentPay", "代付"), BILL_AGENT_PAY("billAgentPay", "账单代付"),
        REFUND("refund", "退款"), WITHDRAW("withdraw", "提现"), SETTLE_WITHDRAW("settleWithdraw", "自动结算提现");

        private final String key;
        private final String value;

        OrderCategory(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum AccountType {
        TO_B(1L, "对公"), TO_C(0L, "对私");

        AccountType(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Long key;

        private final String value;

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum AppState {
        ONLINE(0L, "启用"), FORBIDDEN(1L, "禁止"), NOTACTIVATED(2L, "待激活");

        AppState(Long key, String value) {
            this.key = key;
            this.value = value;
        }

        private final Long key;

        private final String value;

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    //暂不使用
    public enum SplitAgreementSignStatus {
        NO_SIGN("0", "未签约"), SIGN("1", "签约中"), SIGN_SUCCESS("2", "签约成功"), SIGN_FAIL("3", "签约失败");

        private final String key;

        private final String value;

        SplitAgreementSignStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum SplitAgreementSignStatusYunst2 {
        NO_SIGN("0", "未签约"), SIGN_SUCCESS("1", "签约成功"), SIGN_FAIL("2", "签约失败"), SIGN("3", "签约中");

        private final String key;

        private final String value;

        SplitAgreementSignStatusYunst2(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum AreaCompanyStatus {
        INVALID(0, "已删除"), VALID(1, "生效");

        private final Integer key;

        private final String value;

        AreaCompanyStatus(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum YchMemberBindStatus {
        BIND(1, "已绑定"), UNBIND(0, "未绑定");

        private final Integer key;

        private final String value;

        YchMemberBindStatus(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    public enum WithdrawLimitSwitch {
        CLOSE("0", "关闭"), OPEN("1", "开启");

        private final String key;

        private final String value;

        WithdrawLimitSwitch(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum YchPlatSettleConfigModule {
        WITHDRAW_LIMIT(1, "提现限制"), AMOUNT_SETTLE_CONFIG(2, "门店结算配置"), FEE_SETTLE_CONFIG(3, "门店手续费结算配置");

        private final Integer key;

        private final String value;

        YchPlatSettleConfigModule(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    //云商通绑定手机验证码类型 6-解绑 9-绑定
    public static List <Long> VerificationCodeType = Arrays.asList(6L, 9L);
    public static Long VerificationCodeType_unbindPhone = 6L;
    public static Long VerificationCodeType_bindPhone = 9L;
    public static String YCH_DEFAULT_PASSWORD = "1234qwer";


    public enum MchtOrderReceive {

        NO("0", "未收款"), YES("1", "已收款");

        private final String key;

        private final String value;

        MchtOrderReceive(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 云商通二代会员枚举映射
     * saasKey：行业组件（可视化）会员状态枚举  key：云商通二代状态枚举
     */
    public enum MemberStatusYun2 {
        VALID(1L, "有效", 1L), FAIL(3L, "审核失败", 2L), STANDBY(7L, "待审核", 0L),
        BLACKLIST(9L, "黑名单", 4L), RISK_BLACKLIST(11L, "风控黑名单", 3L), LOGOUT(13L, "注销", 5L),
        BANK_AUDITING(15L, "银行审核中", 6L), BANK_AUDIT_FAIL(17L, "银行审核失败", 7L);

        private final Long key;
        private final String value;
        private final Long saasKey;

        MemberStatusYun2(Long saasKey, String value, Long key) {
            this.key = key;
            this.value = value;
            this.saasKey = saasKey;
        }

        public Long getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public Long getSaasKey() {
            return saasKey;
        }

        public static Long getSaasKeyByKey(Long key) {
            for (MemberStatusYun2 status : MemberStatusYun2.values()) {
                if (status.getKey().equals(key)) {
                    return status.getSaasKey();
                }
            }
            return null;
        }
    }


    /**
     * 协议类型
     */
    public enum ProtocolType {

        WithdrawAgreement(1, "账户提现协议"), PayeeAgreement(2, "收款协议"), SepWithdrawAgreement(3, "分账协议");
        private final Integer key;
        private final String value;


        ProtocolType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 用户大类
     */
    public enum EmployeeType {
        APP_EMPLOYEE(1, "应用用户"), ORG_EMPLOYEE(2, "分公司用户");

        private final Integer key;

        private final String value;

        EmployeeType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }

    /**
     * 分账类型
     */
    public enum AgreementSplitType {

        Percent("1", "比例"), FixAmount("2", "固定金额");
        private final String key;
        private final String value;


        AgreementSplitType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 分公司/事业部类型
     */
    public enum BranchCorpType {

        Merchant(1, "分公司"), Department(2, "事业部"), TopManager(3, "总部运维");
        private final Integer key;
        private final String value;


        BranchCorpType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 登录方式
     */
    public enum LoginType {

        Merchant(1, "用户名"), Department(2, "手机号");

        private final Integer key;
        private final String value;


        LoginType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public enum Merchant_Tag {

        API("1", "集团API版"), SAAS("0", "集团saas版");

        private final String key;
        private final String value;


        Merchant_Tag(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


    /**
     * 应用渠道
     */
    public enum ApplicationChannelType {

        Yun2("1", "云商通2.0渠道参数"), PayMode("2", "纯支付版本参数"), Wechat("3", "微信小程序参数");
        private final String key;
        private final String value;


        ApplicationChannelType(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static Boolean judgeChannelType(String channelType){
            for(ApplicationChannelType type : ApplicationChannelType.values()){
                if(type.key.equals(channelType)){
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }
    }


    public enum MCC_PARAM{
        P7531("7531", "车体维修店"),
        P7534("7534", "轮胎翻新、维修店"),
        P7535("7535", "汽车喷漆店"),
        P5541E("5541E", "充电桩"),
        P5521("5521", "二手车经销商"),
        P5561("5561", "拖车销售商"),
        P5571("5571", "摩托车商店"),
        P5598("5598", "雪车商"),
        P5599("5599", "未列入其他代码的机动车、飞行器、农业机械经销商"),
        P4582("4582", "机场、停机坪、飞机停靠与维修服务"),
        P5532("5532", "汽车轮胎经销商"),
        P5533("5533", "汽车零配件商店"),
        P7523("7523", "停车场"),
        P7512("7512", "汽车出租"),
        P7513("7513", "卡车、拖车与农机出租"),
        P7519("7519", "房车和娱乐车辆出租"),
        P7538("7538", "机动车维修保养"),
        P7542("7542", "洗车服务"),
        P7549("7549", "拖车服务"),
        P5013("5013", "机动车、农机零配件批发"),
        P5511("5511", "汽车经销商"),
        P5592("5592", "房车销售商"),
        P5541("5541", "加油、加气、充电站");

        private final String key;
        private final String dec;

        MCC_PARAM(String key, String dec) {
            this.key = key;
            this.dec = dec;
        }
        public String getKey() {
            return key;
        }
        public static String getMccNameById(String mccId){
            for(MCC_PARAM mccParam : MCC_PARAM.values()){
                if(mccParam.key.equals(mccId)){
                    return mccParam.dec;
                }
            }
            return mccId;
        }
    }

    public enum MERCHANT_PRODUCT_TAG_TYPE{
        OfficialDefinition("1", "官方定义标签"),
        Customize("2", "自定义型标签");


        private final String key;
        private final String dec;

        MERCHANT_PRODUCT_TAG_TYPE(String key, String dec) {
            this.key = key;
            this.dec = dec;
        }
        public String getKey() {
            return key;
        }
    }

    /**
     * 云商通二代协议签约状态
     */
    public enum Yst2AgreeSignStatus {

        SUCCESS("1", "签约成功"), FAIL("2", "签约失败"), WAITING_AUDIT("3", "签约中"), WAITING_UPLOAD("0","未上传");
        private final String key;
        private final String value;


        Yst2AgreeSignStatus(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    public final static List <Long> RISKROLE_RECEIVER = Arrays.asList(1L, 3L);
    public final static List <Long> RISKROLE_SPLIT = Arrays.asList(2L, 4L);
    public final static Integer RISK_ROLE_TYPE_RECEIVER = 1;
    public final static Integer RISK_ROLE_TYPE_SPLIT = 2;

    //前端所需判断登录用户权限字段 16-总部 17-事业部 18-分公司
    public final static Long TOP_MANAGE_ROOT = 16L;
    public final static Long DEPARTMENT_ROOT = 17L;
    public final static Long BRANCH_ROOT = 18L;


    public final static List <Long> INDUSTRY_CATEGORY_RECEIVER = Arrays.asList(2L, 11L, 13L);
    public final static List <Long> INDUSTRY_CATEGORY_SPLIT = Arrays.asList(3L, 12L);
    public final static String NULL_STRING = "NULL";
}
