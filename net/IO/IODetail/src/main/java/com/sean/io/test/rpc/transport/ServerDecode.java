package com.sean.io.test.rpc.transport;

import com.sean.io.test.util.Config;
import com.sean.io.test.util.Packmsg;
import com.sean.io.test.rpc.protocol.MyContent;
import com.sean.io.test.rpc.protocol.Myheader;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * @author: 马士兵教育
 * @create: 2020-08-16 18:22
 */
public class ServerDecode extends ByteToMessageDecoder {

    //父类里一定有channelread{  前老的拼buf  decode（）；剩余留存 ;对out遍历 } -> bytebuf
    //因为你偷懒，自己能不能实现！
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws Exception {

        while(buf.readableBytes() >= Config.headerSize) {
            byte[] bytes = new byte[Config.headerSize];
            buf.getBytes(buf.readerIndex(),bytes);  //从哪里读取，读多少，但是readindex不变
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            ObjectInputStream oin = new ObjectInputStream(in);
            Myheader header = (Myheader) oin.readObject();


            //DECODE在2个方向都使用
            //通信的协议
            if(buf.readableBytes() >= header.getDataLen()){
                //处理指针
                buf.readBytes(Config.headerSize);  //移动指针到body开始的位置
                byte[] data = new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);

                if(header.getFlag() == 0x14141414){
                    MyContent content = (MyContent) doin.readObject();
                    out.add(new Packmsg(header,content));

                }else if(header.getFlag() == 0x14141424){
                    MyContent content = (MyContent) doin.readObject();
                    out.add(new Packmsg(header,content));
                }


            }else{
                break;
            }


        }

    }
}
