package com.test.web;

import com.test.service.HystrixHelloService;
import com.test.service.TestConsumer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    TestConsumer helloService;

    @Resource
    private HystrixHelloService hystrixHelloService;

    @GetMapping(value = "/hi")
    public String hi(@RequestParam String name) {
        return helloService.hiService(name);
    }

    @RequestMapping("/hystrix/hi")
    public String hiHystrix(String name){
        return hystrixHelloService.hiService(name);
    }
}
