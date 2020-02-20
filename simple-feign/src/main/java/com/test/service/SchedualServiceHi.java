package com.test.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-hi")
public interface SchedualServiceHi {

    @RequestMapping("hi")
    String testClient(@RequestParam("name") String name);
}
