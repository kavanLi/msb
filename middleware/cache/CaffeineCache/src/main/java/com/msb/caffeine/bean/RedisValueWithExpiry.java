package com.msb.caffeine.bean;

/**
 * @author: kavanLi-R7000
 * @create: 2024-05-27 17:37
 * To change this template use File | Settings | File and Code Templates.
 */
public class RedisValueWithExpiry<T> {
    private T value;
    private long expiryTime;

    public RedisValueWithExpiry(T value, long expiryTime) {
        this.value = value;
        this.expiryTime = expiryTime;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }
}
