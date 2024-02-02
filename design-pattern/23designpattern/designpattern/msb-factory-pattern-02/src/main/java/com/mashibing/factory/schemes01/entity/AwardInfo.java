package com.mashibing.factory.schemes01.entity;

import java.util.Map;

/**
 * 获奖信息实体类
 * @author spikeCong
 * @date 2022/9/8
 **/
public class AwardInfo {

    private String uid; //用户id

    private Integer awardTypes; //奖品类型: 1 打折券 ,2 优酷会员 , 3 小礼品

    private String awardNumber; //奖品编号

    private Map<String,String> extMap; //额外信息

    public AwardInfo() {
    }

    @Override
    public String toString() {
        return "AwardInfo{" +
                "uid='" + uid + '\'' +
                ", awardTypes=" + awardTypes +
                ", awardNumber='" + awardNumber + '\'' +
                ", extMap=" + extMap +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getAwardTypes() {
        return awardTypes;
    }

    public void setAwardTypes(Integer awardTypes) {
        this.awardTypes = awardTypes;
    }

    public String getAwardNumber() {
        return awardNumber;
    }

    public void setAwardNumber(String awardNumber) {
        this.awardNumber = awardNumber;
    }

    public Map<String, String> getExtMap() {
        return extMap;
    }

    public void setExtMap(Map<String, String> extMap) {
        this.extMap = extMap;
    }
}
