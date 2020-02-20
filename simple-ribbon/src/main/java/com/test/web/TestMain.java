package com.test.web;

import java.util.concurrent.locks.ReentrantLock;

public class TestMain {
    public static void main(String[] args) {
    ReentrantLock lock = new ReentrantLock();
    TestLock testLock = new TestLock(lock);
//        TestLock testLock2 = new TestLock(lock);
//
    Thread thread = new Thread(testLock, "test-1");
    thread.start();
    try {
        thread.wait();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
//        new Thread(testLock2,"test-2").start();


}

    private static void testAbq(){
        Depots depot = new Depots(500);
        new Producers(depot,"1").producer(500);
        new Producers(depot,"2").producer(200);
        new Consumers(depot,"3").consume(500);
        new Consumers(depot,"4").consume(200);

//        Depot depot = new Depot(500);
//        new Producer(depot).produce(500);
//        new Producer(depot).produce(200);
//        new Consumer(depot).consume(500);
//        new Consumer(depot).consume(200);
    }
}
