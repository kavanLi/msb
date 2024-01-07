package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: kavanLi
 * @create: 2019-08-01 15:53
 * To change this template use File | Settings | File and Code Templates.
 */
public final class RegexUtils {

    /* fields -------------------------------------------------------------- */

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
     * 多个字符在一个字符串中交集的regex
     * Multiple words in any order using regex
     * https://stackoverflow.com/questions/1177081/multiple-words-in-any-order-using-regex
     * https://www.baeldung.com/string-contains-multiple-words
     * https://stackoverflow.com/questions/4389644/regex-to-match-string-containing-two-names-in-any-order
     * @param str
     * @return
     */
    public static String multipleWordInStringRegExp(String... str) {
        if (str.length <= 0) {
            return "";
        }

        String regExp = "";

        for (String s : str) {
            regExp += "(?=.*" + makeQueryStringToRegExp(s) + ")";
        }

        return regExp;
    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
