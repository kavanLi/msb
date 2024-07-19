package com.mashibing.internal.common.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: kavanLi
 * @create: 2019-06-14 14:00
 * To change this template use File | Settings | File and Code Templates.
 */
@Data
@Slf4j
public final class CountCharacterUtils {
    /* fields -------------------------------------------------------------- */
    /**中文字符 */
    private int chCharacter = 0;

    /**英文字符 */
    private int enCharacter = 0;

    /**空格 */
    private int spaceCharacter = 0;

    /**数字 */
    private int numberCharacter = 0;

    /**其他字符 */
    private int otherCharacter = 0;

    private CountCharacterUtils() {

    }

    /***
     * 统计字符串中中文，英文，数字，空格等字符个数
     * @param str 需要统计的字符串
     */
    public static CountCharacterUtils count(String str) {
        CountCharacterUtils countCharacterUtils = new CountCharacterUtils();
        if (null == str || str.equals("")) {
            System.out.println("字符串为空");
            return countCharacterUtils;
        }

        for (int i = 0; i < str.length(); i++) {
            char tmp = str.charAt(i);
            if ((tmp >= 'A' && tmp <= 'Z') || (tmp >= 'a' && tmp <= 'z')) {
                countCharacterUtils.enCharacter ++;
            } else if ((tmp >= '0') && (tmp <= '9')) {
                countCharacterUtils.numberCharacter ++;
            } else if (tmp ==' ') {
                countCharacterUtils.spaceCharacter ++;
            } else if (isChinese(tmp)) {
                countCharacterUtils.chCharacter ++;
            } else {
                countCharacterUtils.otherCharacter ++;
            }
        }
        //log.info("字数裁剪后, 统计字符串中中文，英文，数字，空格等字符个数的字符串结果为:");
        //log.info("中文字符有:" + countCharacterUtils.chCharacter);
        //log.info("英文字符有:" + countCharacterUtils.enCharacter);
        //log.info("数字有:" + countCharacterUtils.numberCharacter);
        //log.info("空格有:" + countCharacterUtils.spaceCharacter);
        //log.info("其他字符有:" + countCharacterUtils.otherCharacter);
        return countCharacterUtils;
    }

    public static int totalChars(CountCharacterUtils countCharacterUtils) {
        return ((countCharacterUtils.chCharacter * 2) + countCharacterUtils.enCharacter + countCharacterUtils.numberCharacter + countCharacterUtils.spaceCharacter + countCharacterUtils.otherCharacter);
    }

    public static int totalCharsExcludeSpaceCharacter(CountCharacterUtils countCharacterUtils) {
        return ((countCharacterUtils.chCharacter * 2) + countCharacterUtils.enCharacter + countCharacterUtils.numberCharacter + countCharacterUtils.otherCharacter);
    }
    /***
     * 判断字符是否为中文
     * @param ch 需要判断的字符
     * @return 中文返回true，非中文返回false
     */
    private static boolean isChinese(char ch) {
        //获取此字符的UniCodeBlock
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(ch);
        //  GENERAL_PUNCTUATION 判断中文的“号
        //  CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        //  HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            //System.out.println(ch + " 是中文");
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String str = "，";
        CountCharacterUtils count = CountCharacterUtils.count(str);
        int totalChars = CountCharacterUtils.totalChars(count);
        System.out.println();
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
