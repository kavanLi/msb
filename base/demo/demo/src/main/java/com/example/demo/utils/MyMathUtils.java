package com.example.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: kavanLi
 * @create: 2019-08-01 15:53
 * To change this template use File | Settings | File and Code Templates.
 */
@Slf4j
public final class MyMathUtils {

    /* fields -------------------------------------------------------------- */

    private MyMathUtils() {
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    /**
     * 验证由数字和26个英文字母组成的字符串 和 - 组成
     * https://www.cnblogs.com/halao/p/7662425.html
     *
     * @return
     */
    public static String anyLettersAndNumbers() {
        String regExp = "^[A-Za-z0-9-]+$";
        return regExp;
    }

    /**
     * 验证由数字字符串
     * https://www.cnblogs.com/halao/p/7662425.html
     *
     * @return
     */
    public static String anyNumbers() {
        String regExp = "^[0-9]+$";
        return regExp;
    }

    /**
     * 验证由数字或小数字符串
     * https://www.cnblogs.com/halao/p/7662425.html
     *
     * @return
     */
    public static String anyDecimalOrNumeric() {
        String regExp = "^[0-9]\\d*(\\.\\d+)?$";
        return regExp;
    }

    /**
     * 计算整数除法 相除 百分比 百分号 保留小数点 精度 2种方法
     *
     * @param dividend 除数
     * @param divisor 被除数
     * @param precision 精度 如为2 即百分比保留的小数位数为2位 例如 92.01%
     * @return
     */
    public static String calcDivisionResultToPercent(String dividend, String divisor, int precision) {
        if (Integer.valueOf(divisor) == 0) {
            return "N/A";
        }
        String ratio = "";
        try {
            NumberFormat percent = NumberFormat.getPercentInstance();
            percent.setMaximumFractionDigits(precision);

            BigDecimal bigDecimalDividend = new BigDecimal(dividend);
            BigDecimal bigDecimalDivisor = new BigDecimal(divisor);
            double doubleValue = bigDecimalDividend.divide(bigDecimalDivisor, precision + 2, RoundingMode.HALF_UP).doubleValue();
            //格式化为百分比字符串（自带百分号）
            ratio = percent.format(doubleValue);
        } catch (ArithmeticException e) {
            e.printStackTrace();
            log.error("Nothing to worry.\n"
                    + "We are just printing the stack trace.\n"
                    + "ArithmeticException is handled. But take care of the variable \"c\"");
            ratio = "N/A";
        } finally {
            return ratio;
        }
    }


    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
