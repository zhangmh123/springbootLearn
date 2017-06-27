package com.neusoft.spring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.spring.util.SpringUtil;


@RestController
@RequestMapping("/application")
public class TestApplicationController {
    
    @RequestMapping("/test1")
    public Object testSpringUtil1() {
        return SpringUtil.getBean("testDemo");
    }
    
}
