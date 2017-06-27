package com.neusoft.spring.service.Impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neusoft.spring.dao.DemoDao;
import com.neusoft.spring.dao.DemoJdbcDao;
import com.neusoft.spring.model.Demo;
import com.neusoft.spring.service.DemoService;


@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;
    
    @Resource
    private DemoJdbcDao demoJdbcDao;

    public void save(Demo demo){
        demoDao.save(demo);
    }

	public Demo getById(long id) {
		return demoJdbcDao.getById(id);
	}
}