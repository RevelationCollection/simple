package com.test.web;

import com.test.service.RedissonService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestWeb {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedissonService redissonService;

    @RequestMapping("test")
    public String test(){
        return "Hello";
    }

    @RequestMapping("testRedis")
    public String testRedis(String key,String value){
        RBucket<Object> bucket = this.redissonClient.getBucket(key);
        System.out.println("value:"+bucket.get());
        bucket.set(value);
        return "ok";
    }

    @RequestMapping("testLock")
    public String testlock(){
        redissonService.testRlock();
        return "ok";
    }
}
