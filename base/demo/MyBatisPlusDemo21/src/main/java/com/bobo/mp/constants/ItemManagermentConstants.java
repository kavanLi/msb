package com.bobo.mp.constants;


import java.util.Objects;

public class ItemManagermentConstants {

    public static String YCH_ITEM_TIER = "1-科目类别 2-科目明细，科目层级";
    public static String YCH_ITEM_SCENE = "1-POS款项 2-H5款项，应用场景";

    public static String YCH_ITEM_STATUS = "1-启用 0-禁用，启用状态";

    public static String MCHT_ORDER_SCENE = "1-码牌交易 2-消费 3-款项";

    public enum MchtOrderScene {

        SCENE_ONE("1", "码牌交易"),
        SCENE_TWO("2", "消费"),
        SCENE_THREE("3", "款项");

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

        public static ItemManagermentConstants.MchtOrderScene of(String code) {
            for (ItemManagermentConstants.MchtOrderScene item : ItemManagermentConstants.MchtOrderScene.values()) {
                if (Objects.equals(item.code, code)) {
                    return item;
                }
            }
            throw new IllegalArgumentException("Invalid order item code: " + code);
        }

        public static Boolean exists(String code) {
            for (ItemManagermentConstants.MchtOrderScene item : ItemManagermentConstants.MchtOrderScene.values()) {
                if (Objects.equals(item.code, code)) {
                    return true;
                }
            }
            return false;
            //throw new SaasYunstException(ServerRespCode.ERROR_PARAM, ItemManagermentConstants.MCHT_ORDER_SCENE + "参数不合法！");
        }
    }

    /**
     * 1-启用 0-禁用，启用状态
     */
    public enum ItemStatus {

        ITEM_STATUS_ENABLE(1, "启用"),
        ITEM_STATUS_DISABLE(0, "禁用");

        private final int code;
        private final String desc;

        ItemStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static ItemStatus of(int code) {
            for (ItemStatus status : ItemStatus.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (ItemStatus status : ItemStatus.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            //throw new SaasYunstException(ServerRespCode.ERROR_PARAM, ItemManagermentConstants.YCH_ITEM_STATUS + "参数不合法！");
            return false;
        }
    }

    /**
     * 1-POS款项 2-H5款项，应用场景
     */
    public enum ItemScene {

        ITEM_SCENE_POS(1, "POS款项"),
        ITEM_SCENE_H5(2, "H5款项");

        private final int code;
        private final String desc;

        ItemScene(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static ItemScene of(int code) {
            for (ItemScene scene : ItemScene.values()) {
                if (scene.code == code) {
                    return scene;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (ItemScene scene : ItemScene.values()) {
                if (scene.code == code) {
                    return true;
                }
            }
            //throw new SaasYunstException(ServerRespCode.ERROR_PARAM, ItemManagermentConstants.YCH_ITEM_SCENE + "参数不合法！");
            return false;
        }
    }

    /**
     * 科目层级 1-科目类别 2-科目明细
     */
    public enum ItemTier {

        ITEM_CATEGORY(1, "科目类别"),
        ITEM_DETAIL(2, "科目明细");

        private final int code;
        private final String desc;

        ItemTier(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static ItemTier of(int code) {
            for (ItemTier status : ItemTier.values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid order status code: " + code);
        }

        public static Boolean exists(int code) {
            for (ItemTier status : ItemTier.values()) {
                if (status.code == code) {
                    return true;
                }
            }
            //throw new SaasYunstException(ServerRespCode.ERROR_PARAM, ItemManagermentConstants.YCH_ITEM_TIER + "参数不合法！");
            return false;
        }
    }

}
