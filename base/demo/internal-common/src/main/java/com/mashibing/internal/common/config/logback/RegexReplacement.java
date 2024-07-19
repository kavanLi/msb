package com.mashibing.internal.common.config.logback;

import java.util.regex.Pattern;

/**
 * @author Joyce Huang
 */
public class RegexReplacement {

    /** 脱敏匹配正则 */
    private Pattern regex;
    /** 替换正则 */
    private String replacement;

    public String format(final String msg) {
        return regex.matcher(msg).replaceAll(replacement);
    }

    public Pattern getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = Pattern.compile(regex);
    }

    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
    }
}
