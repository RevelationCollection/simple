package com.test.web;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock implements Runnable{
    private Lock lock;
    public TestLock(ReentrantLock lock){

        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("get lock before");
        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        lock.lock();
        System.out.println("get lock after");
//        doSomething();
//        lock.unlock();
//        System.out.println("lock unlock");
    }

    private void doSomething(){
        System.out.println("get tow lock before");
        lock.lock();
        System.out.println("get tow lock after");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            System.out.println("error");
            e.printStackTrace();
        }
        lock.unlock();
        System.out.println("tow lock unlock");
    }

}
