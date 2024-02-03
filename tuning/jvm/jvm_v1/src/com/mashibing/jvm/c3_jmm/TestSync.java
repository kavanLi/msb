package com.mashibing.jvm.c3_jmm;

public class TestSync {
    synchronized void m() {

    }

    void n() {
        synchronized (this) {

        }
    }

    // 根据CPU缓存行大小定义数组长度
    static final int ARRAY_SIZE = 1024 * 1024;

    static long[][] buffers = new long[3][ARRAY_SIZE];

    static void wcBufferWrite() {
        long start = System.nanoTime();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            // 连续写入三个缓冲区的相同位置
            buffers[0][i] = i;
            buffers[1][i] = i;
            buffers[2][i] = i;
        }
        System.out.println(System.nanoTime() - start);
    }

    static void normalWrite() {
        long start = System.nanoTime();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            // 访问第一个缓冲区
            buffers[0][i] = i;
        }

        for (int i = 0; i < ARRAY_SIZE; i++) {
            // 访问第二个缓冲区
            buffers[1][i] = i;
        }

        for (int i = 0; i < ARRAY_SIZE; i++) {
            // 访问第三个缓冲区
            buffers[2][i] = i;
        }
        System.out.println(System.nanoTime() - start);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 11; i++) {
            normalWrite();
            wcBufferWrite();
            System.out.println("------------------------------");
        }

    }
}
