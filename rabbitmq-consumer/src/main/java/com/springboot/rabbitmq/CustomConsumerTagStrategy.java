package com.springboot.rabbitmq;

import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component
@Qualifier("consumerTagStrategy")
public class CustomConsumerTagStrategy implements ConsumerTagStrategy  
{  
    public String createConsumerTag(String queue) {  
        String consumerName = "Consumer1";  
        return consumerName + "_" + queue;  
    }  
}  