package com.mashibing.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
/**
 * @author
 * 传统io读写+网络发送：客户端 4次copy
 */
public class TranditionClient {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",8081);
        String fileName = "C:\\Users\\lijin\\Desktop\\lijin.jpg";
        //创建输⼊流对象
        InputStream inputStream = new FileInputStream(fileName);
        //创建输出流
        DataOutputStream dataOutputStream = new
                DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[1024];
        long readCount = 0;
        long total=0;
        long startTime = System.currentTimeMillis();
        //TODO 这里要发生2次copy
        while ((readCount=inputStream.read(buffer))>=0){
            total+=readCount;
            //TODO 网络发送(文件的写入 也是一样)：这里要发生2次copy
            dataOutputStream.write(buffer);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("发送总字节数："+total+"，耗时："+(endTime-startTime)+" ms");
        //释放资源
        dataOutputStream.close();
        socket.close();
        inputStream.close();
    }
}
