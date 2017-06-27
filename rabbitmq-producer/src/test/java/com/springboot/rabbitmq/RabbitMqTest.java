package com.springboot.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.Application;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {
	@Autowired
	private RmqProducer rmqProducer;
	@Test  
	public void test() throws IOException  
	{  
	  
	        String exchange=Application.exchangeName;////交换器  
	        String routeKey=Application.ROUTINGKEY;//队列  
	        String methodName="test";//调用的方法  
            //参数  
            Map<String,Object> param=new HashMap<String, Object>();  
            param.put("name","张三");  
              
            RabbitMessage  msg=new RabbitMessage(exchange,routeKey, methodName, param);  
            rmqProducer.sendMessage(msg);
	        
	          
	} 
}
