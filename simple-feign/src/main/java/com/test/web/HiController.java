package com.test.web;

import com.test.service.SchedualServiceHi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HiController {
    @Autowired
    SchedualServiceHi schedualServiceHi;

    @RequestMapping("/hi")
    public String sayHi(String name){
        return schedualServiceHi.testClient(name);
    }
    @RequestMapping("/sayHystriHi")
    public String sayHystriHi(String name){
        return schedualServiceHi.testClient(name);
    }
}
