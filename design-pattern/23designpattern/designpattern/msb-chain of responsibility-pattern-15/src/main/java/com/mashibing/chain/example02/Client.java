package com.mashibing.chain.example02;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author spikeCong
 * @date 2022/10/16
 **/
public class Client {

    public static void main(String[] args) throws ParseException {

        AuthController controller = new AuthController();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2022-11-06 00:00:00");

        //模拟审核请求及审批操作
        AuthInfo info1 = controller.doAuth("研发小周", "100001000010000", date);
        System.out.println("当前审核状态: " + info1.getInfo());

        AuthService.auth("1000013","100001000010000");
        System.out.println("三级审批负责人审批完成,审批人: 王工");

        System.out.println("=================================================");

        AuthInfo info2 = controller.doAuth("研发小周", "100001000010000", date);
        System.out.println("当前审核状态: " + info2.getInfo());

        AuthService.auth("1000012","100001000010000");
        System.out.println("二级审批负责人审批完成,审批人: 周经理");


        System.out.println("=================================================");

        AuthInfo info3 = controller.doAuth("研发小周", "100001000010000", date);
        System.out.println("当前审核状态: " + info3.getInfo());

        AuthService.auth("1000012","100001000010000");
        System.out.println("一级审批负责人审批完成,审批人: 罗总");
    }
}
