package com.springboot.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.springboot.bean.DemoInfo;
import com.springboot.repository.DemoInfoRepository;
import com.springboot.service.DemoInfoService;
import com.springboot.util.RedisUtil;


@Service

public class DemoInfoServiceImpl implements DemoInfoService{

      

       @Resource

       private DemoInfoRepository demoInfoRepository;

      

      // @Resource

     //  private RedisTemplate<String,String> redisTemplate;
       @Resource
       private RedisUtil redisUtil;

      

       public void test(){

            //  ValueOperations<String,String>valueOperations = redisTemplate.opsForValue();

             // valueOperations.set("mykey4", "random1="+Math.random());

            //  System.out.println(valueOperations.get("mykey4"));

       }

      

      //keyGenerator="myKeyGenerator"
      // @Cacheable(value="demoInfo") //缓存,这里没有指定key.
       public DemoInfo findById(long id) {

              System.err.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);
              return demoInfoRepository.findById(id);
             // return demoInfoRepository.findOne(id);

       }

      

       //@CacheEvict(value="demoInfo", allEntries = true)
       //@CachePut(value="demoInfo")  
       public void deleteFromCache(long id) {
    	      //demoInfoRepository.delete(id);
    	   		demoInfoRepository.deleteById(id);
              System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");

       }


	public void save(DemoInfo demo) {
		// TODO Auto-generated method stub
		demoInfoRepository.save(demo);
	}


	//@CachePut(value="demoInfo")  
	public DemoInfo update(DemoInfo demo) {
		return demoInfoRepository.save(demo);
	}

}