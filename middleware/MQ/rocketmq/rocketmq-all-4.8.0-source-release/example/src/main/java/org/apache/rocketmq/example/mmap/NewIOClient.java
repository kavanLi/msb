package org.apache.rocketmq.example.mmap;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 采用零拷贝读写客户端(2次拷贝 Kafka中的sendfile)
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception {
        //socket套接字
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8081));
        socketChannel.configureBlocking(true);
        //文件
        String fileName = "C://Users//Administrator//Desktop//lijin.jpg";
        //FileChannel 文件读写、映射和操作的通道
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();


        //上面的没有涉及到数据IO的处理
        long startTime = System.currentTimeMillis();
        //transferTo⽅法⽤到了零拷⻉，底层是sendfile，这里只需要发生2次copy（DMA的COPY）
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        long endTime = System.currentTimeMillis();
        System.out.println("发送总字节数："+transferCount+"耗时："+(endTime-startTime)+" ms");
        //释放资源
        fileChannel.close();
        socketChannel.close();
    }
}