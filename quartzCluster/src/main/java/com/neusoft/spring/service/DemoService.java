package com.neusoft.spring.service;

import com.neusoft.spring.model.Demo;


public interface DemoService {
    public void save(Demo demo);
    public Demo getById(long id) ;
}