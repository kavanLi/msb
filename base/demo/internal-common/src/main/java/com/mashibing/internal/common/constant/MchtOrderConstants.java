package com.mashibing.internal.common.constant;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class MchtOrderConstants {

    public static String MCHT_ORDER_SCENE = "1-码牌交易 2-款项收款 3-消费 4-代收 5-充值 6-提现 7-商城 8-账单模式";

    public static List<String> CALC_ORDER_SCENE=Arrays.asList("2","3","4");

    public enum MchtOrderScene {

        SCENE_ONE("1", "码牌交易"),
        SCENE_TWO("2", "款项收款"),
        SCENE_THREE("3", "消费"),
        SCENE_FOUR("4", "代收"),
        SCENE_FIVE("5", "充值"),
        SCENE_SIX("6", "提现"),
        SCENE_SEVEN("7", "商城"),
        SCENE_EIGHT("8", "账单模式");

        private final String code;
        private final String desc;

        MchtOrderScene(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static MchtOrderScene of(String code) {
            for (MchtOrderScene item : MchtOrderScene.values()) {
                if (Objects.equals(item.code, code)) {
                    return item;
                }
            }
            throw new IllegalArgumentException("Invalid order item code: " + code);
        }

        public static Boolean exists(String code) {
            for (MchtOrderScene item : MchtOrderScene.values()) {
                if (Objects.equals(item.code, code)) {
                    return true;
                }
            }
            //throw new SmartyunstException(ServerRespCode.ERROR_PARAM, MchtOrderConstants.MCHT_ORDER_SCENE + "参数不合法！");
            return false;
        }
    }


    /**
     * 云商通标准接口响应状态
     */
    public enum YunstResultStatus{
        SUCCESS("OK","成功"),FAIL("error","失败"),PENDING("pending","进行中");

        private final String key;
        private final String value;

        YunstResultStatus(String key, String value) {
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
     * 云商通支付状态
     */
    public enum YunstPayStatus{
        SUCCESS("success","成功"),FAIL("fail","失败"),PENDING("pending","进行中");

        private final String key;
        private final String value;

        YunstPayStatus(String key, String value) {
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
     * 商品订单状态
     */
    public static final List<String> MchtOrderStatusList = Arrays.asList("1", "3", "4", "5", "6", "8", "99");
    public enum MchtOrderStatus{
        UNPAY("1", "未支付"), TRADEFAIL("3","交易失败"),
        TRADESUS("4","交易成功"), TRADESUSANDREFUND("5","交易成功-发生退款"), CLOSED("6","关闭"),
        TRADESUSRETICKET("8","交易成功-发生退票"), INPROGRESS("99", "进行中");

        private final String key;
        private final String value;

        MchtOrderStatus(String key, String value){
            this.key = key;
            this.value = value;
        }

        public static String getValueByKey(String key){
            for (MchtOrderStatus mchtOrderStatus: MchtOrderStatus.values()) {
                if (mchtOrderStatus.getKey().equals(key)) {
                    return mchtOrderStatus.getValue();
                }
            }
            return "";
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     *
     */
    public static final List<String> MchtOrderReceiveTypeList = Arrays.asList("1", "2");
    public enum MchtOrderReceiveType{
        IMMEDIATE("1","即时到账"), GUARANTEE("2", "担保交易");

        private final String key;
        private final String value;

        MchtOrderReceiveType(String key, String value){
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

    public static final List<String> PayModeList = Arrays.asList("1", "2", "3", "4");
    /**
     * 支付方式
     */
    public enum PayMode{
        H5_CASHIER_VSP("1","H5收银台"), WECHAT_PUBLIC("2","微信JS"), ALIPAY_SERVICE("3","支付宝JS"),
        WECHATPAY_MINIPROGRAM_CASHIER_VSP("4","微信小程序收银台支付"), ORDER_VSPPAY("5","收银宝POS");
        private final String key;
        private final String value;
        PayMode(String key, String value){
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

    public static final List<String> PayChannelList = Arrays.asList("1");
    /**
     * 支付渠道
     */
    public enum PayChannel{
        YST2D("1","云商通二代");
        private final String key;
        private final String value;
        PayChannel(String key, String value){
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

    public static final List<String> PosOrderTypeList = Arrays.asList("01", "02", "03", "04", "05", "06");
    /**
     * POS订单类型
     */
    public enum PosOrderType{
        TYPE01("01","款项收款"), TYPE02("02","消费"), TYPE03("03","代收"),
        TYPE04("04","订单"), TYPE05("05","H5"), TYPE06("06","退款");
        private final String key;
        private final String value;
        PosOrderType(String key, String value){
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
}
