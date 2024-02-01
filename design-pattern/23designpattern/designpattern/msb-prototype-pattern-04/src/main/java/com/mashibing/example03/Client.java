package com.mashibing.example03;

import com.mashibing.example02.AdvTemplate;
import com.mashibing.example03.Mail;

import java.util.Random;

/**
 * @author spikeCong
 * @date 2022/11/30
 **/
public class Client {

    //发送邮件的数量
    private static int MAX_COUNT = 6;

    //发送邮件
    public static void sendMail(Mail mail){
        System.out.println("标题: " + mail.getSubject() + "\t 收件人: " + mail.getReceiver()
                + "\t ...发送成功!");
    }

    public static void main(String[] args) {

        int i = 0;

        //定义模板
        Mail mail = new Mail(new AdvTemplate());
        mail.setTail("xxx银行版权所有");

        while(i < MAX_COUNT){
            //每封邮件不同的信息
            Mail cloneMail = mail.clone();
            cloneMail.setAppellation(" 先生 (女士)");
            Random random = new Random();
            int num = random.nextInt(999999999);
            cloneMail.setReceiver(num + "@"+"mashibing.com");

            //发送邮件
            sendMail(cloneMail);
            i++;
        }
    }
}
