package com.qf.rabbitmq;

import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
@Component
@Qualifier("replyConsumerTagStrategy")
public class ReplyConsumerTagStrategy implements ConsumerTagStrategy  
{  
    public String createConsumerTag(String queue) {  
       // String consumerName = "Consumer1";  
    	String consumerName = "Consumer_" + Thread.currentThread().getName();  
        return consumerName + "_" + queue;  
    }  
}  