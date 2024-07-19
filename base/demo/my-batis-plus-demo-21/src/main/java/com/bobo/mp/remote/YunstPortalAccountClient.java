package com.bobo.mp.remote;

import com.mashibing.internal.common.domain.request.BusinessOrderListRequest;
import com.mashibing.internal.common.domain.response.CommonResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


//@FeignClient(name = "foobar", url = "${yunst3.mcht}")
public interface YunstPortalAccountClient {


    @RequestMapping(value = "/mcht/payment/getBusinessOrder", method = RequestMethod.POST)
    CommonResponse <T> getBusinessOrder(BusinessOrderListRequest req);

    /**
     * 可以使用 @RequestParam 注解将参数 req 转换为 application/x-www-form-urlencoded 类型的参数。
     */
    @RequestMapping(value = "/mcht/payment/getBusinessOrder", method = RequestMethod.POST)
    CommonResponse <T> getBusinessOrder(@RequestParam("applicationId") String applicationId,
                                        @RequestParam("orderNo") String orderNo,
                                        @RequestParam("bizOrderNo") String bizOrderNo);




}

/**
 * 使用 Feign 的 RequestInterceptor: Feign 的 RequestInterceptor 来拦截请求并转换参数。
 */
//class FeignRequestInterceptor implements RequestInterceptor {
//
//    @Override
//    public void apply(RequestTemplate template) {
//        // 将参数 req 转换为 application/x-www-form-urlencoded 类型的参数
//        Map<String, String> params = new HashMap<>();
//        params.put("merchantNo", req.getMerchantNo());
//        params.put("orderNo", req.getOrderNo());
//        params.put("orderType", req.getOrderType());
//        template.body(params);
//    }
//
//}

/**
 * 使用 Feign 的 Encoder: Feign 的 Encoder 来转换请求体。
 */
//class FeignEncoder implements Encoder {
//
//    @Override
//    public String encode(Object object) throws EncodingException {
//        // 将参数 req 转换为 application/x-www-form-urlencoded 类型的参数
//        Map<String, String> params = new HashMap<>();
//        params.put("merchantNo", req.getMerchantNo());
//        params.put("orderNo", req.getOrderNo());
//        params.put("orderType", req.getOrderType());
//        return URLEncodedUtils.format(params, "UTF-8");
//    }
//
//}