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

////SpringJUnit֧�֣��ɴ�����Spring-Test���֧�֣�

@RunWith(SpringJUnit4ClassRunner.class)
// //ָ������SpringBoot���̵�������
@SpringBootTest(classes=Application.class)
// /������Web��Ŀ��Junit��Ҫģ��ServletContext�����������Ҫ�����ǵĲ��������@WebAppConfiguration��
@WebAppConfiguration
public class HelloServiceTest {

	@Resource
	private HelloService helloService;
	//@Resource
	//private RedisConnectionFactory connectionFactory;
	
	@Before
	public void before() {
		System.out.println("��ÿ�����Է���ǰִ�У�");

	}

	//@Test
	public void testGetName() {
		System.out.println("���Է���1��ʼִ�У�");
		Assert.assertEquals("hello", helloService.getName());

	}
	//@Test
	public void testGetName2() {
		System.out.println("���Է���2��ʼִ�У�");
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
		System.out.println("��ÿ�����Է�����ִ�У�");

	}
}