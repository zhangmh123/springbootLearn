package com.neusoft.junit.test;

import java.io.Serializable;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.springboot.Application;
import com.springboot.bean.User;
import com.springboot.config.ApplicationConfig;
import com.springboot.service.HelloService;

////SpringJUnit支持，由此引入Spring-Test框架支持！

@RunWith(SpringJUnit4ClassRunner.class)
// //指定我们SpringBoot工程的启动类
@SpringBootTest(classes=Application.class)
// /由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class HelloServiceTest {

	@Resource
	private HelloService helloService;
	//@Resource
	//private RedisConnectionFactory connectionFactory;
	
	@Before
	public void before() {
		System.out.println("在每个测试方法前执行！");

	}

	//@Test
	public void testGetName() {
		System.out.println("测试方法1开始执行！");
		Assert.assertEquals("hello", helloService.getName());

	}
	//@Test
	public void testGetName2() {
		System.out.println("测试方法2开始执行！");
		Assert.assertEquals("hello", helloService.getName());

	}

    @Test  
    public void testJdkSerialiable() {  
    	RedisConnectionFactory connectionFactory = new JedisConnectionFactory();  
    	((JedisConnectionFactory) connectionFactory).setHostName("172.29.50.32");  
    	((JedisConnectionFactory) connectionFactory).setPort(6379);  
        RedisTemplate<String, Serializable> redis = new RedisTemplate<String, Serializable>();  
        redis.setConnectionFactory(connectionFactory);  
        redis.setKeySerializer(ApplicationConfig.StringSerializer.INSTANCE);  
        redis.setValueSerializer(new JdkSerializationRedisSerializer());  
        redis.afterPropertiesSet();  
      
        ValueOperations<String, Serializable> ops = redis.opsForValue();  
      
        User user1 = new User();  
        user1.setName("user1");  
        user1.setPassword("123");  
      
        String key1 = "users/user1";  
        User user11 = null;  
      
        long begin = System.currentTimeMillis();  
        for (int i = 0; i < 100; i++) {  
            ops.set(key1,user1);  
            user11 = (User)ops.get(key1);  
        }  
        long time = System.currentTimeMillis() - begin;  
        System.out.println("jdk time:"+time);  
        Assert.assertEquals("user1", user11.getName());
        //assertThat(user11.getName(),is("user1"));  
    }  

	@After
	public void after() {
		System.out.println("在每个测试方法后执行！");

	}
}