package com.mashibing.smsgateway.netty4;

import com.mashibing.smsgateway.netty4.entity.CmppMessageHeader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码
 */
public class CMPPEncoder extends MessageToByteEncoder<Object> {

    public CMPPEncoder(){
        super(false);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof byte[]){
                out.writeBytes((byte[])msg);
            }else if(msg instanceof Integer){
                out.writeInt((Integer)msg);
            }else if(msg instanceof Byte){
                out.writeByte((Byte)msg);
            }else if(msg instanceof Long){
                out.writeLong((Long)msg);
            }else if(msg instanceof String){
                out.writeBytes(((String)msg).getBytes("UTF-16BE"));
            }else if (msg instanceof Character){
                out.writeChar((Character)msg);
            }else if (msg instanceof CmppMessageHeader){
                CmppMessageHeader c=(CmppMessageHeader)msg;
                out.writeBytes(c.toByteArray());
        }
    }
}
