package com.msb.dubbo.provider.filters;


import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.Map;
@Slf4j
@Activate(group = "provider")
public class ProviderContextFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        // ServerAttachment接收客户端传递过来的采纳数
        Map<String, Object> serverAttachments = RpcContext.getServerAttachment().getObjectAttachments();
        log.info("ContextService serverAttachents:{}",serverAttachments);

        String xid = (String)serverAttachments.get("XID");
        log.info("获取传递的数据XID:{}",xid);
        // 执行下一个逻辑
        Result result = invoker.invoke(invocation);
        return result;
    }
}
