package com.mashibing.framework.utils;

/**
 * @author spikeCong
 * @date 2022/10/28
 **/
public class SpringUtils {
    private SpringUtils() {
    }

    public static String getSetterMethod(String fieldName){
        //fieldName = courseDao
        String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return methodName;
    }
}
