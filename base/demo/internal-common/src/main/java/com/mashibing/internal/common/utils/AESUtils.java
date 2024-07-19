package com.mashibing.internal.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * java使用AES加密解密 AES-128-ECB加密
 * 与mysql数据库aes加密算法通用
 * 数据库aes加密解密
 *    -- 加密
 *    SELECT to_base64(AES_ENCRYPT('www.gowhere.so','tainshucrm!@_+$='));
 *    -- 解密
 *    SELECT AES_DECRYPT(from_base64('Oa1NPBSarXrPH8wqSRhh3g=='),'tainshucrm!@_+$=');
 * @author haoshaobo
 *
 */
@Slf4j
public class AESUtils {

    public static final String KEY = "tainshucrm!@_+$=";

    /**
     * 这个方法根据 key 的长度来决定掩码的策略：
     *
     * 长度 ≤ 32 位：显示前 3 位和后 3 位，中间部分用 “****” 替代。
     * 长度 > 32 且 ≤ 64 位：显示前 4 位和后 4 位，中间部分用 “****” 替代。
     * 长度 > 64 且 ≤ 128 位：显示前 5 位和后 5 位，中间部分用 “****” 替代。
     * 长度 > 128 位：显示前 6 位和后 6 位，中间部分用 “****” 替代。
     * 通过这种方式，可以在记录日志时显示部分密钥信息以便识别，而不会暴露太多的敏感信息。
     * @param key
     * @return
     */
    public static String maskSecretKey(String key) {
        if (key == null) {
            return "****";
        }
        int length = key.length();

        if (length <= 32) {
            return key.substring(0, 3) + "****" + key.substring(length - 3, length);
        } else if (length <= 64) {
            return key.substring(0, 4) + "****" + key.substring(length - 4, length);
        } else if (length <= 128) {
            return key.substring(0, 5) + "****" + key.substring(length - 5, length);
        } else {
            return key.substring(0, 6) + "****" + key.substring(length - 6, length);
        }
    }

    // 加密
    public static String Encrypt(String sSrc) throws Exception {

        byte[] raw = KEY.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }
 
    // 解密
    public static String Decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                log.info(e.toString());
                return null;
            }
        } catch (Exception ex) {
            log.info(ex.toString());
            return null;
        }
    }
 
    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "jkl;POIU1234++==";
        // 需要加密的字串
        String cSrc = "18220876966";
        log.info(cSrc);
        // 加密
        String enString = AESUtils.Encrypt(cSrc);
        log.info("加密后的字串是：" + enString);
 
        // 解密
        String DeString = AESUtils.Decrypt(enString);
        log.info("解密后的字串是：" + DeString);
    }
}