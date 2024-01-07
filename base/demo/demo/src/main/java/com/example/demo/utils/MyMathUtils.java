package com.example.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @author: kavanLi
 * @create: 2019-08-01 15:53
 * To change this template use File | Settings | File and Code Templates.
 */
public final class MyMathUtils {

    /* fields -------------------------------------------------------------- */

    private MyMathUtils() {
    }

    /* constructors -------------------------------------------------------- */


    /* public methods ------------------------------------------------------ */

    /**
     * 计算整数除法 相除 百分比 百分号 保留小数点 精度 2种方法
     *
     * @param dividend 除数
     * @param divisor 被除数
     * @param precision 精度 如为2 即百分比保留的小数位数为2位 例如 92.01%
     * @return
     */
    public static String calcDivisionResultToPercent(String dividend, String divisor, int precision) {
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
            return "0.00%";
        } finally {
            return ratio;
        }
    }

    /* private methods ----------------------------------------------------- */


    /* getters/setters ----------------------------------------------------- */

}
