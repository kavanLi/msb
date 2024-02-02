package com.mashibing.observer.example02;

import java.util.Date;

/**
 * 开奖结果封装类
 * @author spikeCong
 * @date 2022/10/12
 **/
public class LotteryResult {

    private String uId; //用户id

    private String msg; //摇号信息

    private Date dateTime; //业务时间

    public LotteryResult(String uId, String msg, Date dateTime) {
        this.uId = uId;
        this.msg = msg;
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "LotteryResult{" +
                "uId='" + uId + '\'' +
                ", msg='" + msg + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
