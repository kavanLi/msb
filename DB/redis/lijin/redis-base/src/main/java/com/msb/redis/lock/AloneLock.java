package com.msb.redis.lock;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class AloneLock implements Lock {
    AtomicReference<Thread> owner = new AtomicReference<>();
    //队列--存放哪些没有抢到锁的线程
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();
    //实现加锁
    @Override
    public void lock() {
        while (!owner.compareAndSet(null,Thread.currentThread())){
            waiters.add(Thread.currentThread());
            LockSupport.park();//让当前线程阻塞
            waiters.remove(Thread.currentThread());//解锁了，就需要把线程从等待列表中删除
        }
    }
    //实现解锁
    @Override
    public void unlock() {
        if(owner.compareAndSet(Thread.currentThread(),null)){
            for (Object  object:waiters.toArray()){
                Thread  next= (Thread) object;
                LockSupport.unpark(next);
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }



    @Override
    public Condition newCondition() {
        return null;
    }
}

