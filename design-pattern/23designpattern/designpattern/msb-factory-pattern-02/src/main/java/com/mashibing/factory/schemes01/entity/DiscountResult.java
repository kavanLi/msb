package com.mashibing.factory.schemes01.entity;

/**
 * 打折券响应信息封装实体类
 * @author spikeCong
 * @date 2022/9/8
 **/
public class DiscountResult {

    private String status; //状态码

    private String message; //信息


    public DiscountResult(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
