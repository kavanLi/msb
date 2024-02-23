package com.example.demo.utils;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.support.CronSequenceGenerator;

/**
 * @author: kavanLi
 * @create: 2019-06-27 13:34
 * To change this template use File | Settings | File and Code Templates.
 */
public class SpringCronResolveUtil {
    /* fields -------------------------------------------------------------- */


    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */
    /**
     * 功能描述: 根据当前时间计算并返回下次执行时间
     * @param cron 表达式字符串,包含6个以空格分开的字符
     * @return long
     * @throws
     */
    public static long nextExecutionTime(String cron){
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        Date lastTime = new Date();
        Date nexDate = cronSequenceGenerator.next(lastTime);
        return nexDate.getTime();

    }

    /**
     * 功能描述: 根据最后一次执行时间计算并返回下次执行时间
     * @param cron 表达式字符串，一定要是包含6个以空格分离的字符
     * @param lastTime 最近的执行时间
     * @return long
     * @throws
     */
    public static long nextExecutionTime(String cron,long lastTime) {
        Date date = new Date(lastTime);
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);
        Date nexDate = cronSequenceGenerator.next(date);
        return nexDate.getTime();
    }

    public static void main(String[] args) {
        String cron = "0/10 * * * * ? ";
        System.out.println("当前时间:" + System.currentTimeMillis());
        System.out.println("下一次时间:" + DateFormatUtils.format(nextExecutionTime(cron), "yyyy-MM-dd HH:mm:ss"));
        System.out.println("下一次时间:" + DateFormatUtils.format(nextExecutionTime("0 33 23 * * ?"), "yyyy-MM-dd HH:mm:ss"));
        System.out.println("下一次时间:" + DateFormatUtils.format(nextExecutionTime("0 33 23 * * ?"), "yyyy-MM-dd HH:mm:ss"));
    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
