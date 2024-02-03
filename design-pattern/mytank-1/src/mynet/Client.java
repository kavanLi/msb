package mynet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
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

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        System.out.println(Thread.currentThread().getName());

        System.out.println(socketChannel);
        //socketChannel.pipeline().addLast();
    }
}

