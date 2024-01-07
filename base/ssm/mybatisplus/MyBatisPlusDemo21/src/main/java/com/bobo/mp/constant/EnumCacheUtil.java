package com.bobo.mp.constant;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Guava Cache的使用
 */
public class EnumCacheUtil {
    private static final Cache<String, Map<Long, String>> cache = CacheBuilder.newBuilder().build();

    public static Map<Long, String> getAllEnumList() {
        try {
            return cache.get("enumList", () -> loadEnumList());
        } catch (ExecutionException e) {
            // 处理获取异常
            return null;
        }
    }

    private static Map<Long, String> loadEnumList() {
        Map<Long, String> enumList = createEnumList();
        cache.put("enumList", enumList);
        return enumList;
    }

    // 这里是你的原始方法，创建 enumList
    private static Map<Long, String> createEnumList() {
        Map<Long, String> map = new HashMap <>();
        map.put(1L, "银行管理");
        map.put(2L, "通联通管理");
        map.put(6L, "自主管理");
        map.put(7L, "电子账户管理");
        map.put(8L, "云直通");
        return map;
    }
}
