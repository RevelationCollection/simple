package com.test.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedissonService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private RedissonClient redsson;

    public void testRlock(){
        String lockName = "redisson_lock";
        log.info("start .....");
        RLock lock = redsson.getLock(lockName);
//        lock.lock();
//        //do something
//        lock.unlock();
//
//        lock.lock(10, TimeUnit.SECONDS);
//
//        lock.unlock();
        try {
            boolean b = lock.tryLock(6, TimeUnit.SECONDS);
            log.info("getLock status:{}",b);
            if(b){
                log.info("get lock success");
                doSomething(lockName);
                Thread.sleep(30000);
            }else{
                log.info("get lock false");
            }
        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        log.info("end .....");
    }

    private void doSomething(String lockName){
        log.info("do start .....");
        RLock lock = redsson.getLock(lockName);
        boolean flag = lock.tryLock();
        log.info("getLock status:{}",flag);
    }
}
