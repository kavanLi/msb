package com.mashibing.internal.common.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import ru.lanwen.verbalregex.VerbalExpression;

/**
 * @author: kavanLi
 * @create: 2019-08-01 15:53
 * To change this template use File | Settings | File and Code Templates.
 */
public final class RegexUtils {

    /* fields -------------------------------------------------------------- */

    /**
     * 字符串中有非数字
     */
    public static Pattern NUMBER_PATTERN = Pattern.compile("[^0-9]");

    /**
     * 字符串包含数字
     */
    public static Pattern CONTAIN_NUMBER_PATTERN = Pattern.compile("(.*?)\\d(.*?)");


    private RegexUtils() {
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    /**
     * 转义正则特殊字符 （$()*+.[]?\^{}
     * \\需要第一个替换，否则replace方法替换时会有逻辑bug
     *
     * @param str
     * @return
     */
    public static String makeQueryStringToRegExp(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        return str.replace("\\", "\\\\").replace("*", "\\*")
                .replace("+", "\\+").replace("|", "\\|")
                .replace("{", "\\{").replace("}", "\\}")
                .replace("(", "\\(").replace(")", "\\)")
                .replace("^", "\\^").replace("$", "\\$")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("?", "\\?").replace(",", "\\,")
                .replace(".", "\\.").replace("&", "\\&");
    }

    /**
     * 多个字符在一个字符串中的regex 交集
     * Multiple words in any order using regex
     * https://stackoverflow.com/questions/1177081/multiple-words-in-any-order-using-regex
     * https://www.baeldung.com/string-contains-multiple-words
     * https://stackoverflow.com/questions/4389644/regex-to-match-string-containing-two-names-in-any-order
     *
     * @param str
     * @return
     */
    public static String multipleWordAndInStringRegExp(String... str) {
        if (str.length <= 0) {
            return "";
        }

        String regExp = "";

        for (String s : str) {
            regExp += "(?=.*" + makeQueryStringToRegExp(s) + ")";
        }

        return regExp;
    }

    /**
     * 多个字符在一个字符串中的regex 并集
     * Multiple words in any order using regex
     *
     * @param str
     * @return
     */
    public static String multipleWordOrInStringRegExp(String... str) {
        if (str.length <= 0) {
            return "";
        }
        VerbalExpression regex = VerbalExpression.regex().oneOf(str).build();

        return regex.toString();
    }

    /**
     * 验证由数字和26个英文字母组成的字符串 和 - 组成
     * https://www.cnblogs.com/halao/p/7662425.html
     *
     * @return
     */
    public static String anyLettersAndNumbers() {
        String regExp = "^[A-Za-z0-9-]+$";
        return regExp;
    }

    /**
     * 不包含字符串
     *
     * @return
     */
    public static String notContainString(String string) {
        String regExp = "^(?!.*" + makeQueryStringToRegExp(string) + ")";
        return regExp;
    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
