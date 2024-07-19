package com.mashibing.internal.common.constant;

import lombok.Getter;

/**
 * 【强制】错误码为字符串类型，共 5 位，分成两个部分：错误产生来源+四位数字编号。
 * 说明：错误产生来源分为 A/B/C，A 表示错误来源于用户，比如参数错误，用户安装版本过低，用户支付超时等问题；
 * B 表示错误来源于当前系统，往往是业务逻辑出错，或程序健壮性差等问题；C 表示错误来源于第三方服务，比如 CDN
 * 服务出错，消息投递超时等问题；四位数字编号从 0001 到 9999，大类之间的步长间距预留 100
 */
@Getter
public enum ErrorCodeEnum {

    /**
     * 成功
     */
    SUCCESS("00000","一切 ok"),

    /**
     * 失败
     */

    FAIL("00001","未知失败"),
    FAIL_A("A0001","调用端错误/用户端错误"),
    FAIL_B("B0001","被调用端错误/系统执行出错 "),
    FAIL_C("C0001","调用第三方服务出错"),

    /**
     * 统一验证提示 1700-1799
     */
    VALIDATION_EXCEPTION("A1700","接口请求参数统一验证的错误提示"),


    /**
     * 被调用端错误/系统执行出错提示：B0100 - B0199
     */
    SM4_ENCRYPT_ERROR("B0102","SM4加密异常"),
    SM4_DECRYPT_ERROR("B0103","SM4解密异常"),
    READ_PRIVATE_KEY_ERROR("B0104","读取私钥失败"),
    READ_PUBLIC_KEY_ERROR("B0105","读取公钥失败"),



    ;

    private String code;
    private String value;

    ErrorCodeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
