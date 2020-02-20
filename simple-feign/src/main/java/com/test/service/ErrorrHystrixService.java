package com.test.service;

import org.springframework.stereotype.Service;

@Service
public class ErrorrHystrixService implements HystrixScheduleService{

    @Override
    public String syHiFromClient(String name) {
        return "sorry "+name;
    }
}
