package com.example.demo;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.demo.service.AsyncService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author: kavanLi-R7000
 * @create: 2023-12-27 10:52
 * To change this template use File | Settings | File and Code Templates.
 */

@SpringBootTest
public class AlertTest {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private AsyncService asyncService;

    @Test
    public void testAsyncThreadName() {
        asyncService.processCompletableFuture("123");
    }

    /**
     * 发送文本邮件
     */
    @Test
    public void testEmailAlert1() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("763527350@qq.com");
        message.setTo("763527350@qq.com");
        message.setSubject("告警通知");
        message.setText("系统发生异常,详情请查看日志");
        mailSender.send(message);
        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(123);
        }
    }

    /**
     * 发送HTML格式邮件
     */
    @Test
    public void testEmailAlert2() {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("763527350@qq.com");
            helper.setTo("763527350@qq.com");
            helper.setSubject("告警通知");
            helper.setText("<h1>系统发生异常</h1><p>详情请查看日志</p>", true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(message);
    }



}
