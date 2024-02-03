package com.msb.redis.lock.rdl;
/**
 *
 *类说明：Redis的key-value结构
 */
public class LockItem {
    private final String key;
    private final String value;

    public LockItem(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
