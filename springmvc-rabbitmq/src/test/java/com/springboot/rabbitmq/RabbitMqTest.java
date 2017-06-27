package com.springboot.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {
	@Autowired
	private RmqProducer rmqProducer;
	@Test  
	public void test() throws IOException  
	{  
	  
	        String exchange="testExchange";////交换器  
	        String routeKey="testQueue";//队列  
	       
	        //参数  
	        for(int i =0;i < 100000;i++){
	        	Map<String,Object> param=new HashMap<String, Object>();  
		        param.put("data"+i,"hello"+i);  
		          
		        RabbitMessage  msg=new RabbitMessage(exchange,routeKey, param);  
		        //发送消息  
		        rmqProducer.sendMessage(msg);  
	        }
	        
	          
	} 
}
