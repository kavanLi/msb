package org.apache.rocketmq.example.mmap;

import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * RocketMQ中的MMAP
 */
public class MmapCopy
{

    public static String path = "F:\\mmap";
    public static void main( String[] args ) throws Exception
    {
        File file1 = new File(path, "lijin");
        FileChannel fileChannel = new RandomAccessFile(file1, "rw").getChannel();//拿到能够操作文件的通道
        // map方法，底层会调用操作系统mmap函数，实现零拷贝  从0 开始，映射（内存即磁盘、磁盘即内存） 1024个byte
        MappedByteBuffer mmap = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0,  1024);
        //内存 （应用层）
        mmap.put("lijin2222".getBytes());//数据写入
        mmap.flip();//刷盘  数据从内存 同步到磁盘
        byte[] bb = new byte[5];
        mmap.get(bb,0,5); //数据读取
        System.out.println(new String(bb));
        unmap(mmap);
    }
    //最终释放会去调用，显示调用System.gc()方法，还是GC
    private static void unmap(MappedByteBuffer bb) {
        Cleaner cl = ((DirectBuffer)bb).cleaner();
        if (cl != null)
            cl.clean();
    }
}
