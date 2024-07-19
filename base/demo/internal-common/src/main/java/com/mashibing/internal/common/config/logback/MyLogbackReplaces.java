package com.mashibing.internal.common.config.logback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Joyce Huang
 */
public class MyLogbackReplaces {

    /** 脱敏正则列表 */
    private List<RegexReplacement> replace = new ArrayList<>();

    /**
     * 添加规则
     * @param replacement replacement
     */
    public void addReplace(RegexReplacement replacement) {
        replace.add(replacement);
    }

    public List<RegexReplacement> getReplace() {
        return replace;
    }

    public void setReplace(List<RegexReplacement> replace) {
        this.replace = replace;
    }
}
