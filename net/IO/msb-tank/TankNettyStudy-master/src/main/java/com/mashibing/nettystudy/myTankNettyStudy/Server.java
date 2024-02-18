package com.mashibing.nettystudy.myTankNettyStudy;


import java.util.concurrent.CyclicBarrier;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {

    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void main(String[] args) {
        //serverStart();
    }

    public void serverStart() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workrtGroup = new NioEventLoopGroup(2);


        /**
         * 在 Netty 中，建立连接和后续客户端与服务端的数据传输是通过两个不同的通道进行处理的。这两个通道分别是 NioServerSocketChannel 和 SocketChannel。
         *
         * NioServerSocketChannel：
         *
         * 用于服务端的监听通道，负责接收客户端的连接请求。
         * 服务端启动时创建，只负责监听连接请求，不直接处理具体的数据传输。
         * 一旦有客户端连接请求到达，它会创建一个 SocketChannel 表示与客户端的具体连接，并将这个 SocketChannel 传递给服务端的业务逻辑进行处理。
         * SocketChannel：
         *
         * 用于表示客户端与服务端建立的连接通道，负责具体的数据传输。
         * 在服务端接受到客户端连接请求后创建。
         * 这个通道负责处理连接上的读写操作，它是具体进行数据传输的通道。
         * 对应于客户端的每个连接，都有一个独立的 SocketChannel 实例。
         * 通过这种方式，Netty 实现了一种异步、事件驱动的模型。NioServerSocketChannel 用于监听连接请求，一旦有连接到达，会创建相应的 SocketChannel 用于后续的数据传输。这种分离的设计可以更好地处理多个客户端的并发连接和数据传输。在业务逻辑中，你通常会在 NioServerSocketChannel 的 handler 方法中配置服务端的全局处理逻辑，而在 SocketChannel 的 childHandler 方法中配置每个连接的具体处理逻辑。
         */
        try {
            ServerBootstrap b = new ServerBootstrap();
            ChannelFuture f = b.group(bossGroup, workrtGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer <SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pl = ch.pipeline();
                            pl.addLast(new TankMsgDecoder())
                            .addLast(new ServerChildHandler());
                        }
                    })
                    .bind(8888).sync();


            System.out.println("server started");
            ServerFrame.INSTANCE.updateServerMsg("server started");
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            workrtGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}

class ServerChildHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Server.clients.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channelRead");
        try {
            TankMsg tm = (TankMsg)msg;

            System.out.println(tm);
        } finally {
            ReferenceCountUtil.release(msg);
        }

        //ByteBuf buf = null;
        //try {
        //
        //    buf = (ByteBuf) msg;
        //    byte[] bytes = new byte[buf.readableBytes()];
        //    buf.getBytes(buf.readerIndex(), bytes);
        //    String s = new String(bytes);
        //    System.out.println(s);
        //
        //    ServerFrame.INSTANCE.updateClientMsg(s);
        //
        //
        //    if (s.equals("_bye_")) {
        //        System.out.println("客户端要求推出");
        //        ServerFrame.INSTANCE.updateServerMsg("客户端要求推出");
        //        Server.clients.remove(ctx.channel());
        //        ctx.close();
        //    } else {
        //        Server.clients.writeAndFlush(msg);
        //    }
        //
        //    //System.out.println(buf);
        //    //System.out.println(buf.refCnt());
        //} catch (Exception e) {
        //    throw new RuntimeException(e);
        //} finally {
        //    //if(buf != null && buf) ReferenceCountUtil.release(buf);
        //    //System.out.println(buf.refCnt());
        //}
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        //删除出现异常的客户端channle，并关闭连接
        Server.clients.remove(ctx.channel());
        ctx.close();
    }

}


