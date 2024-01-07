package com.mashibing.zerocopy;

import java.io.*;

public class FileSplitter {
    public static void main(String[] args) {
        String sourceFilePath = "E:\\mmap\\lijin"; // 源文件路径
        String targetDirectory = "E:\\mmap"; // 目标文件夹路径
        int chunkSize = 1024 * 1024; // 分片大小，单位为字节

        try {
            File sourceFile = new File(sourceFilePath);
            FileInputStream fis = new FileInputStream(sourceFile);

            byte[] buffer = new byte[chunkSize];
            int bytesRead;
            int chunkIndex = 1;

            while ((bytesRead = fis.read(buffer)) > 0) {
                String chunkFileName = "chunk_" + chunkIndex + ".dat"; // 分片文件名
                String chunkFilePath = targetDirectory + File.separator + chunkFileName; // 分片文件路径

                FileOutputStream fos = new FileOutputStream(chunkFilePath);
                fos.write(buffer, 0, bytesRead);
                fos.close();

                chunkIndex++;
            }

            fis.close();

            System.out.println("File split completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}