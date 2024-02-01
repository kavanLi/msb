package com.mashibing.strategy.example02;

/**
 * 回执信息
 * @author spikeCong
 * @date 2022/10/13
 **/
public class Receipt {

    private String message; //回执内容

    private String type; //回执类型: MT1101、MT2101、MT4101、MT8104

    public Receipt() {
    }

    public Receipt(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
