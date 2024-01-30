package com.example.demo.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.OperationLogAnnotation;
import com.example.demo.domain.User;
import com.example.demo.domain.UserDaddy;
import com.example.demo.service.AsyncService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author: kavanLi
 * @create: 2019-01-30 10:52
 * To change this template use File | Settings | File and Code Templates.
 */
@Component
@Aspect
@Slf4j
public class OperationLogAspect {

    @Resource
    private AsyncService asyncService;

    //@Before("execution(* com.example.demo.controller..*.*(..))")
    //public void beforeMethod(JoinPoint jp) {
    //    System.out.println("Before invoked");
    //}
    //
    //@After("execution(* com.example.demo.controller..*.*(..))")
    //public void beforeMethodRpt(JoinPoint jp) {
    //    System.out.println("After invoked");
    //}
    //
    //@AfterReturning("execution(* com.example.demo.controller..*.*(..))")
    //public void afterMethod(JoinPoint jp) {
    //    System.out.println("AfterReturning invoked");
    //}
    //
    //@AfterThrowing("execution(* com.example.demo.controller..*.*(..))")
    //public void afterMethodRpt(JoinPoint jp) {
    //    System.out.println("AfterThrowing invoked");
    //}

    @Around("execution(* com.example.demo.controller..*.*(..))")
    public Object methodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //asyncService.processUpdateAndLog("666");
        System.out.println("aroundA invoked before---------------------------------------");
        saveOperationLog(proceedingJoinPoint);
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("aroundB invoked after---------------------------------------");
        return proceed;
    }

    private void saveOperationLog(ProceedingJoinPoint jp) {
        try {
            MethodSignature methodSignature = (MethodSignature) jp.getSignature();
            Method method1 = methodSignature.getMethod();
            OperationLogAnnotation annotation = method1.getAnnotation(OperationLogAnnotation.class);
            if (annotation == null ||
                    !annotation.saveLog()) {
                return;
            }
            // 获取方法参数名
            String[] parameterNames = methodSignature.getParameterNames();

            // 获取方法参数值
            Object[] args = jp.getArgs();
            // 获取请求的路径
            String[] value = jp.getTarget().getClass().getAnnotation(RequestMapping.class).value();
            String requestPath = value[0];
            // 获取目标方法
            Method targetMethod = Arrays.stream(jp.getTarget().getClass().getDeclaredMethods()).filter(method -> method.getName().equals(jp.getSignature().getName()) && method.isAnnotationPresent(GetMapping.class)).findFirst().orElse(null);


            if (targetMethod != null) {
                // 获取方法上的 GetMapping 注解
                GetMapping getMappingAnnotation = targetMethod.getAnnotation(GetMapping.class);

                // 获取注解上的值
                String[] values = getMappingAnnotation.value();

                // 这里可以处理 values，即 @GetMapping 注解上的参数
                System.out.println("GetMapping values: " + Arrays.toString(values));
            }
            // 可以进一步获取其他信息，如方法名、参数等

            // -========================================== Start ==========================================
            //-url:/ybftransfer/bankFront/transfer
            // -HTTP Method:POST
            //-HTTP Headers:user-agent:Apifox/1.0.0 (https://apifox.com),content-type:application/json,accept:*/*,host:localhost:8020,accept-encoding:gzip, deflate, br,connection:keep-alive,content-length:427,
            // -Class Method:com.allinpay.ybf.transfer.controller.BankFrontController.transfer
            // -IP :0:0:0:0:0:0:0:1
            // -Request Param Args:{}
            // -Request Body Args:{
            //    "INFO": {
            //        "TRX_CODE": "103003",
            //                "VERSION": "v2.0",
            //                "DATA_TYPE": "2",
            //                "LEVEL": "5",
            //                "ORGID": "9808",
            //                "SYSID": "1550368314845093890",
            //                "SUBMIT_TIME": "20190514123000",
            //                "REQ_SN": "104002-9807-001",
            //                "SIGNED_MSG": "测试"
            //    },
            //    "BODY": {
            //        "SRC_BATCHID": "20231117101302001",
            //                "SRC_SUBMIT_TIME": "20231117101302"
            //    }
            //}
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            log.info("========================================== Start ==========================================");
            //打印请求参数相关日志
            // 打印请求 url
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            int clientPort = attr.getRequest().getLocalPort();
            log.info("url:{},port {}, client port {}", request.getRequestURI(), request.getServerPort(), clientPort);
            // 打印 Http method
            log.info("HTTP Method:{}", request.getMethod());
            log.info("HTTP Headers:{}", getHeaders(request.getHeaderNames(), request));
            // 打印调用 controller 的全路径以及执行方法
            log.info("Class Method:{}.{}", jp.getSignature().getDeclaringTypeName(), jp.getSignature().getName());
            // 打印请求的 IP
            log.info("IP :{}", request.getRemoteAddr());
            // 打印请求入参
            log.info("Request Param Args:{}", getParameterMap(request.getParameterMap()));
            log.info("Request Body Args:{}", args);
            User user1 = new User();
            //user1.setEmail(String.valueOf(args[0]));
            // 如果是传JsonObject
            // 获取方法参数
            // 遍历参数，找到 User 类型的参数
            for (Object arg : args) {
                if (arg instanceof User) {
                    User user = (User) arg;
                    log.info("Received User object: {}", user);
                    // 可以在这里进行相关的操作
                }
            }

            //如果是传JsonStr
            //String string = String.valueOf(args[0]);
            //com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(string);



            ApiOperation apiOperation = method1.getDeclaredAnnotation(ApiOperation.class);
            String value1 = apiOperation.value(); //获取value值

            System.out.println("Request Path: " + requestPath);
            OperationLogAnnotation ann = method1.getAnnotation(OperationLogAnnotation.class);
            if (!ann.saveLog()) {
                return;
            }

            // 确保数组不为空且至少有一个元素
            if (args != null && args.length > 0) {
                Object arg = args[0];
                // 使用 arg 进行后续的操作
            } else {
                // 处理数组为空的情况，可以抛出异常、打印日志等
                System.out.println("数组为空或长度为0");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //throw new SystemException(ResultCode.ERR_SERVER, e);
        }
    }

    //处理请求头
    private static String getHeaders(Enumeration <String> headers, HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement();
            String headerVal = request.getHeader(headerName);
            stringBuilder.append(headerName + ":" + headerVal + ",");
        }
        return stringBuilder.toString();
    }

    public static Map getParameterMap(Map <String, String[]> map) {
        Map result = new HashMap();
        for (Map.Entry <String, String[]> entry : map.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }

}
