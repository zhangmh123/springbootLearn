package com.springboot.rabbitmq;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
@Component
public class RmqProducer  
{  
      
    @Resource  
    private RabbitTemplate rabbitTemplate;  
      
    /** 
     * ������Ϣ 
     * @param msg 
     */  
    public void sendMessage(RabbitMessage  msg)  
    {  
        try {  
            System.out.println(rabbitTemplate.getConnectionFactory().getHost());  
            System.out.println(rabbitTemplate.getConnectionFactory().getPort());  
            //������Ϣ  
            rabbitTemplate.convertAndSend(msg.getExchange(), msg.getRouteKey(), msg);  
           
        } catch (Exception e) {  
        	System.out.println("����ʧ�ܣ�"+e);
        }  
          
          
    }  
      
}