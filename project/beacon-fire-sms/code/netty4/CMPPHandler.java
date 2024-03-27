package com.mashibing.smsgateway.netty4;


import com.mashibing.smsgateway.netty4.entity.CmppDeliver;
import com.mashibing.smsgateway.netty4.entity.CmppSubmitResp;
import com.mashibing.smsgateway.netty4.utils.MsgUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 主要业务 handler,运营商响应信息
 */
public class CMPPHandler extends SimpleChannelInboundHandler {
    private final static Logger log = LoggerFactory.getLogger(CMPPHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext context, Object msg) throws Exception {

        if (msg instanceof CmppSubmitResp){
            CmppSubmitResp resp=(CmppSubmitResp)msg;
            log.info("-------------接收到短信提交应答-------------");
            log.info("----自增id："+resp.getSequenceId());
            log.info("----状态："+ resp.getResult());
            log.info("----第一次响应："+resp.getMsgId());
        }

        if (msg instanceof CmppDeliver){
            CmppDeliver resp=(CmppDeliver)msg;
            // 是否为状态报告 0：非状态报告1：状态报告
            if (resp.getRegistered_Delivery() == 1) {
                // 如果是状态报告的话
                log.info("-------------状态报告---------------");
                log.info("----第二次响应："+resp.getMsg_Id_DELIVRD());
                log.info("----手机号："+resp.getDest_terminal_Id());
                log.info("----状态："+resp.getStat());
            } else {
                //用户回复会打印在这里
                log.info(""+ MsgUtils.bytesToLong(resp.getMsg_Id()));
                log.info(resp.getSrc_terminal_Id());
                log.info(resp.getMsg_Content());
            }
        }
    }

}
