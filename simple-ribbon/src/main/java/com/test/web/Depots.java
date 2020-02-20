package com.test.web;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Depots {
    private int size;
    private int capacity;
    private Lock lock;
    private Condition fullCondition;

    private Condition emptyCondition;

    public Depots(int capacity){
        this.capacity = capacity;
        lock = new ReentrantLock();
        fullCondition = lock.newCondition();
        emptyCondition = lock.newCondition();
    }

    public void produce(int num){
        String threadName = Thread.currentThread().getName();
        System.out.println("threadName:"+threadName+",lock");
        lock.lock();
        System.out.println("threadName:"+threadName+",get lock");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int limit = num;
        try {
            while (limit >0){
                while (size>=capacity){
                    System.out.println("threadName:"+threadName+",fullCondition before await");
                    fullCondition.await();
                    System.out.println("threadName:"+threadName+",fullCondition after await");
                }
                int inc = (limit + size ) > capacity ? capacity-size : limit;
                limit -=inc;
                size +=inc;
                System.out.println("threadName:"+threadName+",product ,size:"+size+",capacity:"+capacity+",num:"+inc);
                emptyCondition.signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public void consume(int num){
        String threadName = Thread.currentThread().getName();
        System.out.println("threadName:"+threadName+",lock");
        lock.lock();
        System.out.println("threadName:"+threadName+",get lock");
        try {
            while (num>0){
                while (size<=0){
                    System.out.println("threadName:"+threadName+",emptyCondition before await");
                    emptyCondition.await();
                    System.out.println("threadName:"+threadName+",emptyCondition after await");
                }
                int inc = size>num ? num : size;
                num -=inc;
                size-=inc;
                System.out.println("threadName:"+threadName+",consume ,size:"+size+",capacity:"+capacity+",num:"+inc);
                fullCondition.signal();

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}
class Consumers{
    private Depots depot;
    private String name;

    public Consumers(Depots depot,String name){
        this.depot = depot;
        this.name = name;
    }

    public void consume(int num){
        Runnable run = ()->{
            depot.consume(num);
        };
        new Thread(run,"consumer-"+name).start();
    }
}

class Producers{
    private Depots depot;
    private String name;

    public Producers(Depots depot,String name){
        this.depot = depot;
        this.name = name;
    }

    public void producer(int num){
        Runnable run = ()->{
            depot.produce(num);
        };
        new Thread(run,"producer-"+name).start();
    }
}
