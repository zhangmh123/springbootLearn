package com.springboot.rabbitmq;

import org.springframework.stereotype.Component;

@Component
public class RmqConsumer    
{  
    public void rmqProducerMessage(Object object){  
          
        RabbitMessage rabbitMessage=(RabbitMessage) object;  
          
        System.out.println(rabbitMessage.getExchange());  
        System.out.println(rabbitMessage.getRouteKey());  
        System.out.println(rabbitMessage.getParams()[0].toString());  
    }  
              
      
}
