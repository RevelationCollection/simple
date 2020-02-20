package com.test.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value="hystrixScheduleService" ,fallback = ErrorrHystrixService.class)
public interface HystrixScheduleService {

    @RequestMapping("/hi")
    String syHiFromClient(String name);
}
