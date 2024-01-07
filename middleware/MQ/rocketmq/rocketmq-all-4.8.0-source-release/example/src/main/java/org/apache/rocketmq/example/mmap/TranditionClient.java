package org.apache.rocketmq.example.mmap;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * 传统io读写+网络发送：客户端 4次copy
 */
public class TranditionClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",8081);
        String fileName = "C://Users//Administrator//Desktop//lijin.jpg";
        //创建输⼊流对象
        InputStream inputStream = new FileInputStream(fileName);
        //创建Socket的输出流 -> dataOutputStream
        DataOutputStream dataOutputStream = new
                DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[1024];
        long readCount = 0;
        long total=0;
        long startTime = System.currentTimeMillis(); //进行数据拷贝发送的开始时间
        //TODO 这里要发生2次copy（1次 DMA、一次 CPU）
        while ((readCount=inputStream.read(buffer))>=0){
            total+=readCount;
            //TODO 网络发送(1次 DMA、一次 CPU)：这里要发生2次copy
            dataOutputStream.write(buffer);
        }
        long endTime = System.currentTimeMillis();//进行数据拷贝发送的结束时间
        System.out.println("发送总字节数："+total+"，耗时："+(endTime-startTime)+" ms");
        //释放资源
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
