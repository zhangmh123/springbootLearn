package com.springboot.service;

import com.springboot.bean.DemoInfo;


public interface DemoInfoService {
     public DemoInfo findById(long id);
     public void deleteFromCache(long id);
     void test();
     void save(DemoInfo demo);
     DemoInfo update(DemoInfo demo);
}