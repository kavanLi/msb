package com.mashibing.chain.example02;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 模拟审核服务
 * @author spikeCong
 * @date 2022/10/16
 **/
public class AuthService {

    //审核信息容器 key:审批人Id+审批单Id , value:审批时间
    private static Map<String, Date> authMap = new HashMap<>();


    /**
     * 审核方法
     * @param uId     审核人id
     * @param orderId  审核单id
     */
    public static void auth(String uId , String orderId){
        System.out.println("进入审批流程,审批人ID: " + uId);
        authMap.put(uId.concat(orderId),new Date());
    }

    /**
     * 查询审核结果
     * @param uId
     * @param orderId
     * @return: java.util.Date
     */
    public static Date queryAuthInfo(String uId , String orderId){
        return authMap.get(uId.concat(orderId));
    }
}
