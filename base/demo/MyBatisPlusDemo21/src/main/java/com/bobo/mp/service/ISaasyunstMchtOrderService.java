package com.bobo.mp.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bobo.mp.domain.pojo.SaasyunstMchtOrder;

public interface ISaasyunstMchtOrderService extends IService <SaasyunstMchtOrder> {

    //YchDailyFeeSumResp dailyFeeFlowSummary(DailySettleFeeQueryReq req, YchDailyFeeSumResp resp);
    //
    //YchTransResponse <PageResponse<YchDailyFeeQueryResp>> transList(DailySettleFeeQueryReq req);
    //
    //PageResponse <YchDailyFeeQueryResp> queryPage4DailySettleFee(DailySettleFeeQueryReq req);
    //
    //PageResponse <MchtOrderQueryResp> queryPage(MchtOrderQueryReq req);
    //
    ///**
    // * 分账信息查询
    // *
    // * @param bizOrderNo 商户订单号
    // * @return 商户订单生成时的分账信息
    // */
    //List <MchtOrderAllocationResponse> queryAllocation(String bizOrderNo);
    //
    ///**
    // * 查询商户订单
    // *
    // * @param bizOrderNo 商户订单号
    // * @return *
    // */
    //SaasyunstMchtOrder selectByBizOrderNo(String bizOrderNo);
    //
    ///**
    // * 查询商户订单
    // *
    // * @param applicationId      应用编号
    // * @param agentPayBizOrderNo 托付商户订单号
    // * @return *
    // */
    //SaasyunstMchtOrder selectByAgentPayBizOrderNo(String applicationId, String agentPayBizOrderNo);
    //
    ///**
    // * 查询商户订单
    // *
    // * @param applicationId 应用编号
    // * @param bizOrderNo    商户订单号
    // * @return *
    // */
    //SaasyunstMchtOrder selectByBizOrderNo(String applicationId, String bizOrderNo);
    //
    ///**
    // * 同步订单状态
    // *
    // * @param bizOrderNo 商户订单号
    // * @return 订单状态
    // */
    //Map <String, Object> synOrderStatus(String bizOrderNo);
}
