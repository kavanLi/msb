package com.example.demo.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.OperationLogAnnotation;
import com.example.demo.domain.User;
import com.example.demo.domain.model.CommonResponse;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: kavanLi
 * @create: 2020-01-06 14:07
 * To change this template use File | Settings | File and Code Templates.
 *
 * Springboot mail使用QQ邮箱错误汇总
 * https://blog.csdn.net/m0_63411853/article/details/131820588?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-131820588-blog-115234112.235^v39^pc_relevant_3m_sort_dl_base3&spm=1001.2101.3001.4242.1&utm_relevant_index=1
 *
 * gudie https://www.baeldung.com/spring-email
 */
@RestController
@RequestMapping("/alert")
public class AlertController {
    @Autowired
    private JavaMailSender mailSender;

    @GetMapping(value = "")
    public Map<String, String> test(){
        testEmailAlert1();
        System.out.println(123123123);
        Map<String, String> errors = new HashMap <>();
        errors.put("123", "1231");
        return errors;
    }

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

    public void testEmailAlert2() {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom("763526350@qq.com");
            helper.setTo("763526350@qq.com");
            helper.setSubject("告警通知");
            helper.setText("<h1>系统发生异常</h1><p>详情请查看日志</p>", true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        mailSender.send(message);
    }


}
