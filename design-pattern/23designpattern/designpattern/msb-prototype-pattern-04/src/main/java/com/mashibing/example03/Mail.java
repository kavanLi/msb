package com.mashibing.example03;

import com.mashibing.example02.AdvTemplate;

/**
 * @author spikeCong
 * @date 2022/11/30
 **/
public class Mail implements Cloneable {

    private String receiver; //收件人

    private String subject; //邮件名称

    private String appellation; //称呼

    private String context;  //邮件内容

    private String tail; //邮件尾部 "xx版权所有"

    public Mail(AdvTemplate advTemplate) {
        this.subject = advTemplate.getAdvSubject();
        this.context = advTemplate.getAdvContext();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAppellation() {
        return appellation;
    }

    public void setAppellation(String appellation) {
        this.appellation = appellation;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    @Override
    protected Mail clone() {
        Mail mail = null;

        try {
            return  (Mail)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
