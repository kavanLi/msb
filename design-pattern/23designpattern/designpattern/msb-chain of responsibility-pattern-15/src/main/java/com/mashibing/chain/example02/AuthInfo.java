package com.mashibing.chain.example02;

/**
 * 封装审核信息
 * @author spikeCong
 * @date 2022/10/16
 **/
public class AuthInfo {

    private String code; //状态码

    private String info = ""; //审核相关信息

    public AuthInfo(String code, String... infos) {
        this.code = code;
        for (String str : infos) {
            this.info = info.concat(str+ " ");
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "code='" + code + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
