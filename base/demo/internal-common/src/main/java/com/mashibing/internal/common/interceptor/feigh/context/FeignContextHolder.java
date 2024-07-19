package com.mashibing.internal.common.interceptor.feigh.context;

/**
 * @author: kavanLi-R7000
 * @create: 2024-04-12 14:20
 * To change this template use File | Settings | File and Code Templates.
 */
public final class FeignContextHolder {

    private static final ThreadLocal <Long> LOCAL_TIME = new ThreadLocal <>();

    public static void setLocalTime() {
        LOCAL_TIME.set(System.currentTimeMillis());
    }

    public static Long getLocalTime() {
        Long time = LOCAL_TIME.get();
        LOCAL_TIME.remove();
        return time;
    }

}
