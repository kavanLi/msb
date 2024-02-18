package com.mashibing.nettystudy.myTankNettyStudy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Client {

    private Channel channel = null;

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }

    public void send(String msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buf);
    }

    public void closeConnect() {
        this.send("_bye_");
    }


    public void connect() {
        //线程池
        EventLoopGroup group = new NioEventLoopGroup(1);


        try {
            Bootstrap b = new Bootstrap();
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);

            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        System.out.println("not connected");
                    } else {
                        System.out.println("connected");
                        // initialize the channel
                        channel = future.channel();
                    }
                }
            });

            f.sync();
            System.out.println("...");

            f.channel().closeFuture().sync();

            System.out.println("已经退出");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

}

class ClientChannelInitializer extends ChannelInitializer <SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TankMsgEncoder())
                .addLast(new ClientHandler());
    }
}

class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = null;

        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            String msgAccepted = new String(bytes);
            System.out.println(msgAccepted);
            ClientFrame.INSTANCE.updateText(msgAccepted + "1");

            //System.out.println(buf);
            //System.out.println(buf.refCnt());

        } finally {
            if (buf != null) {
                ReferenceCountUtil.release(buf);
            }
            //System.out.println(buf.refCnt());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //ByteBuf buf = Unpooled.copiedBuffer("hello".getBytes());
        //ctx.writeAndFlush(buf);
        ctx.writeAndFlush(new TankMsg(5, 8));
    }
}
