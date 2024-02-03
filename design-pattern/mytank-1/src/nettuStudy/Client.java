package nettuStudy;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
    public static void main(String[] args) {
        // 线程池
        EventLoopGroup group = new NioEventLoopGroup(1);

        Bootstrap b = new Bootstrap();
        Bootstrap b1 = new Bootstrap();

        try {
            ChannelFuture channelFuture = b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);

            //ChannelFuture channelFuture1 = b1.group(group)
            //        .channel(NioSocketChannel.class)
            //        .handler(new ClientChannelInitializer())
            //        .connect("localhost", 18888);

            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName());
                    if (!channelFuture.isSuccess()) {
                        System.out.println("not connected");
                    } else {
                        System.out.println("connected");
                    }
                }
            }).sync();

            //channelFuture1.addListener(new ChannelFutureListener() {
            //    @Override
            //    public void operationComplete(ChannelFuture channelFuture) throws Exception {
            //        System.out.println(Thread.currentThread().getName());
            //        if (!channelFuture.isSuccess()) {
            //            System.out.println("not connected");
            //        } else {
            //            System.out.println("connected");
            //        }
            //    }
            //});

            //channelFuture.sync();
            //channelFuture1.sync();

            System.out.println("...");

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 由于 group.shutdownGracefully() 的执行，主线程调用了 group 的 shutdownGracefully() 方法，这个方法通常会等待线程池中的任务执行完成。这也就是主线程最终等待的原因。
            //如果在 operationComplete 方法中没有阻塞的操作，主线程会立即执行 group.shutdownGracefully()，而不会等待异步操作完成。在异步编程中，我们通常不建议在异步回调中执行阻塞的操作，以免影响整个系统的性能。
            System.out.println("shutdownGracefully before");
            group.shutdownGracefully();
            System.out.println("shutdownGracefully after");
        }
    }
}

class ClientChannelInitializer extends ChannelInitializer <SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println(Thread.currentThread().getName());
        System.out.println(socketChannel);
        socketChannel.pipeline().addLast(new ClientChildHandler());
    }
}


class ClientChildHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName());
        // channel 第一次连上可用，写出一个字符串 ByteBuf使用 Direct Memory，但是跳过了java垃圾回收机制，需要主动释放堆外内存，不过writeAndFlush()这个方法自动释放。
        //在使用堆外内存时需要格外小心，确保正确释放内存，以防止内存泄漏。在 Java 中，堆外内存的生命周期不受 Java 垃圾回收器的管理，需要手动释放。此外，Java 提供的 ByteBuffer 在不再使用时，会由垃圾回收器自动释放底层的堆外内存。
        ByteBuf buf = (Unpooled.copiedBuffer("hello".getBytes()));
        ctx.writeAndFlush(buf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(Thread.currentThread().getName());
        ByteBuf buf = null;

        try {
            buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.getBytes(buf.readerIndex(), bytes);
            System.out.println(new String(bytes));
            //ctx.writeAndFlush(msg);

            //System.out.println(buf);
            //System.out.println(buf.refCnt());
        } finally {
            //if (buf != null) {
            //    ReferenceCountUtil.release(buf);
            //}
            //System.out.println(buf.refCnt());
        }
    }
}

