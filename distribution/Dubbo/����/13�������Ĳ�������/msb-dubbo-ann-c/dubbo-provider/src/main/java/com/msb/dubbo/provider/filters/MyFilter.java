package com.msb.dubbo.provider.filters;


import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
// 这里group是定义消费者还是提供者
@Slf4j
@Activate(group = "provider")
public class MyFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // 业务处理
        URL url = invoker.getUrl();

        Class<?> anInterface = invoker.getInterface();
        String simpleName = anInterface.getSimpleName();
        String serviceName = invocation.getServiceName();
        String methodName = invocation.getMethodName();

        log.info("url:{}",url);
        log.info("simpleName:{}",simpleName);
        log.info("serviceName:{}",serviceName);
        log.info("methodName:{}",methodName);
        // 调用下一级
        Result result = invoker.invoke(invocation);
        return result;
    }
}
