package com.msb.dubbo.consumer.filters;

import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
@Activate(group = "consumer")
public class ConsumerContextFilter  implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getClientAttachment()
                .setAttachment("XID","1111111111111111");

        Result  result = null;
        try{
            // 执行下一个逻辑
            result = invoker.invoke(invocation);
        }finally {
            // 清理
            RpcContext.getClientAttachment().clearAttachments();
        }

        return result;
    }
}
