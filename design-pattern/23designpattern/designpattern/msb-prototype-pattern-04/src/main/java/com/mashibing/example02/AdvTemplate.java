package com.mashibing.example02;

/**
 * 广告模板
 * @author spikeCong
 * @date 2022/9/21
 **/
public class AdvTemplate {

    //广告信名称
    private String advSubject = "xx银行本月还款达标,可抽iPhone 13等好礼!";

    //广告信内容
    private String advContext = "达标用户请在2022年3月1日到2022年3月30日参与抽奖......";

    public String getAdvSubject() {
        return advSubject;
    }

    public void setAdvSubject(String advSubject) {
        this.advSubject = advSubject;
    }

    public String getAdvContext() {
        return advContext;
    }

    public void setAdvContext(String advContext) {
        this.advContext = advContext;
    }
}
