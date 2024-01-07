package com.sean.io.test.rpc.transport;

import com.sean.io.test.util.Packmsg;
import com.sean.io.test.util.SerDerUtil;
import com.sean.io.test.rpc.protocol.MyContent;
import com.sean.io.test.rpc.protocol.Myheader;
import com.sean.io.test.rpc.Dispatcher;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 19:06
 */
public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    Dispatcher dis;

    public ServerRequestHandler(Dispatcher dis) {
        this.dis=dis;
    }

    //provider:
    //思考下解决方法？
    //
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Packmsg requestPkg = (Packmsg) msg;

        String ioThreadName = Thread.currentThread().getName();

        ctx.executor().execute(new Runnable() {
//        ctx.executor().parent().next().execute(new Runnable() {

            @Override
            public void run() {

                String serviceName = requestPkg.getContent().getName();
                String method = requestPkg.getContent().getMethodName();
                Object c = dis.get(serviceName);
                Class<?> clazz = c.getClass();
                Object res = null;
                try {


                    Method m = clazz.getMethod(method, requestPkg.getContent().getParameterTypes());
                    res = m.invoke(c, requestPkg.getContent().getArgs());


                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


//                String execThreadName = Thread.currentThread().getName();
                MyContent content = new MyContent();
//                String s = "io thread: " + ioThreadName + " exec thread: " + execThreadName + " from args:" + requestPkg.content.getArgs()[0];
                content.setRes((String)res);
                byte[] contentByte = SerDerUtil.ser(content);

                Myheader resHeader = new Myheader();
                resHeader.setRequestID(requestPkg.getHeader().getRequestID());
                resHeader.setFlag(0x14141424);
                resHeader.setDataLen(contentByte.length);
                byte[] headerByte = SerDerUtil.ser(resHeader);
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);

                byteBuf.writeBytes(headerByte);
                byteBuf.writeBytes(contentByte);
                ctx.writeAndFlush(byteBuf);
            }
        });




    }

}

