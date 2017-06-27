package com.neusoft.spring.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.neusoft.spring.model.Demo;


@Mapper
public interface DemoDao{
    
    @Insert("insert into t_demo(name) "+
            "values(#{name})")
    int save(Demo  demo);
}