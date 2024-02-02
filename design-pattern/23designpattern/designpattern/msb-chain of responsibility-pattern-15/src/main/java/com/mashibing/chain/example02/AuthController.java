package com.mashibing.chain.example02;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 审核申请接口
 * @author spikeCong
 * @date 2022/10/16
 **/
public class AuthController {

    /**
     * 审核方法
     * @param name     申请人姓名
     * @param orderId  申请单ID
     * @param authDate 申请时间
     * @return: AuthInfo
     */
    public AuthInfo doAuth(String name, String orderId, Date authDate) throws ParseException {

        //三级审批
        Date date = null;

        //查询是否存在审核信息,虚拟三级审核人ID: 1000013
        date = AuthService.queryAuthInfo("1000013", orderId);
        if(date == null){
            return new AuthInfo("0001","单号: " + orderId ,
                    "状态: 等待三级审批负责人进行审批");
        }


        /**
         * 二级审批
         *  查询是否存在审核信息,虚拟二级审核人ID: 1000012
         *  二级审核人审核申请单的时间范围为: 11-01 ~ 11-10
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(authDate.after(sdf.parse("2022-10-31 00:00:00")) && authDate.before(sdf.parse("2022-11-11 00:00:00")) ){
            //条件成立,查询二级审核信息
            date = AuthService.queryAuthInfo("1000012",orderId);

            if(date == null){
                return new AuthInfo("0001","单号: " + orderId ,
                        "状态: 等待二级审批负责人进行审批");
            }
        }

        /**
         * 一级审批
         *  查询是否存在审核信息,虚拟二级审核人ID: 1000012
         *  二级审核人审核申请单的时间范围为: 11-11 ~ 11-31
         */
        if(authDate.after(sdf.parse("2022-11-10 00:00:00")) && authDate.before(sdf.parse("2022-11-31 00:00:00")) ){
            //条件成立,查询二级审核信息
            date = AuthService.queryAuthInfo("1000011",orderId);

            if(date == null){
                return new AuthInfo("0001","单号: " + orderId ,
                        "状态: 等待一级审批负责人进行审批");
            }
        }

        return new AuthInfo("0001","单号: " + orderId ,"申请人: " + name," 状态:审批完成!");
    }

}
