package com.msb.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatServerHandler extends SimpleChannelInboundHandler {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private SimpleDateFormat sdf = new SimpleDateFormat();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端：" + channel.remoteAddress() + " "
                + sdf.format(new Date()) + "上线" + ",请注意接收消息\n" );
        channelGroup.add(channel);
        System.out.println("客户端 " + channel.remoteAddress() + "上线\n" );
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if(ch != channel){
                ch.writeAndFlush("客户单 " + channel.remoteAddress() + "发送数据为：" + msg);
            }else{
                ch.writeAndFlush("自己发送数据为：" + msg);
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("客户端端已经下线 " + channel.remoteAddress() + sdf.format(new Date()));
        System.out.println("客户端已将下线 " + channel.remoteAddress() + sdf.format(new Date()));
        System.out.println("在线人数：" + channelGroup.size());
    }
}
