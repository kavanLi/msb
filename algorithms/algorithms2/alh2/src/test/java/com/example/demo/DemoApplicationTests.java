package com.example.demo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.example.demo.b.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    //public static Object lock = new Object();
    public static int i = 100;
    public static Lock lock = new ReentrantLock(true);
    //public static Condition condition = lock.newCondition();

    static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75f, true);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return super.size() > capacity;
        }
    }

    public static void main(String[] args) {
    }

    private static void showBit(int num) {
        for (int i = 31; i >= 0; i--) {
            System.out.print((num & (1 << i)) == 0 ? "0" : "1");
        }
        System.out.println("\n" + (~(Integer.MIN_VALUE + 1) + 1));
    }

    @Test
    void contextLoads() {
        Stack <Integer> stack = new Stack <>();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        stack.pop();
        stack.pop();
        stack.pop();


    }

    @Test
    void test1() {
    }

    @Test
    void test2() {
    }

    @Test
    void test3() {
    }

    @Test
    void test4() {
    }

    @Test
    void test5() {
    }

    @Test
    void test6() {
    }

    @Test
    void test7() {
    }
}
