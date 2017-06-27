package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.test.Test1Settings;
import com.springboot.test.Test2Settings;


@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private Test1Settings test1Settings;
    
    @Autowired
    private Test2Settings test2Settings;
    
    @RequestMapping("/testProperties")
    public String testProperties() {
        System.out.println(test1Settings);
        System.out.println(test2Settings);
        return "ok";
    }
}
