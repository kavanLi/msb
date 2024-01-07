package com.sean.io.test.rpc.transport;


import com.sean.io.test.util.SerDerUtil;
import com.sean.io.test.rpc.protocol.MyContent;
import com.sean.io.test.rpc.protocol.Myheader;
import com.sean.io.test.rpc.ResponseMappingCallback;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.sean.io.test.rpc.protocol.Myheader.createHeader;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 18:08
 */
public class ClientFactory{

    int poolSize = 5;
    NioEventLoopGroup clientWorker;
    //TODO 现在是对着一个service的多条连接进行随机选择，如果同一个service有多个provider呢？端到端的io优化在这里；还有一个维度是端到端的选择优化上
    Random rand = new Random();

    private ClientFactory(){}
    private static final ClientFactory factory;
    static {
        factory = new ClientFactory();
    }
    public static ClientFactory getFactory(){
        return factory;
    }

    public static CompletableFuture transport(MyContent content){
        //3，连接池：：取得连接
        byte[] msgBody = SerDerUtil.ser(content);
        Myheader header = createHeader(msgBody);
        byte[] msgHeader = SerDerUtil.ser(header);
        System.out.println("header size:"+msgHeader.length);
        NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("localhost", 9090));
        //获取连接过程中： 开始-创建，过程-直接取
        //4，发送--> 走IO  out -->走Netty（event 驱动）

        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(msgHeader.length + msgBody.length);
        long id = header.getRequestID();
        CompletableFuture res = new CompletableFuture<>();
        ResponseMappingCallback.addCallBack(id, res);
        byteBuf.writeBytes(msgHeader);
        byteBuf.writeBytes(msgBody);
        clientChannel.writeAndFlush(byteBuf);
        return res;

    }


    //一个consumer 可以连接很多的provider，每一个provider都有自己的pool  K,V

    ConcurrentHashMap<InetSocketAddress, ClientPool> outboxs = new ConcurrentHashMap<>();

    public  NioSocketChannel getClient(InetSocketAddress address){

        //TODO 在并发下，要控制锁的资源粒度
        ClientPool clientPool = outboxs.get(address);
        if(clientPool ==  null){
            synchronized(outboxs){
                if(clientPool ==  null){
                    outboxs.putIfAbsent(address,new ClientPool(poolSize)); //每provider 一clientpool
                    clientPool =  outboxs.get(address);
                }
            }
        }

        int i = rand.nextInt(poolSize);

        if( clientPool.clients[i] != null && clientPool.clients[i].isActive()){
            return clientPool.clients[i];
        }else{
            synchronized (clientPool.lock[i]){
                if( clientPool.clients[i] == null || !clientPool.clients[i].isActive()){
                    return clientPool.clients[i] = create(address);
                }
            }
        }
        return clientPool.clients[i];
    }

    private NioSocketChannel create(InetSocketAddress address){

        //基于 netty 的客户端创建方式
        clientWorker = new NioEventLoopGroup(1);
        Bootstrap bs = new Bootstrap();
        ChannelFuture connect = bs.group(clientWorker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerDecode());
                        p.addLast(new ClientResponses());  //解决给谁的？？  requestID..
                    }
                }).connect(address);
        try {
            NioSocketChannel client = (NioSocketChannel)connect.sync().channel();
            return client;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;


    }


}
