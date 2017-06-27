package com.springboot.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class ReplyConsumer extends DefaultConsumer  
{  
    public ReplyConsumer(Channel channel)  
    {  
        super(channel);  
    }  
   
    @Override  
    public void handleDelivery(String consumerTag,  
                                         Envelope envelope,  
                                         AMQP.BasicProperties properties,  
                                         byte[] body)  
            throws IOException  
    {  
        String consumerName = properties.getAppId();  
        String replyMessageContent = new String(body, "UTF-8");  
        System.out.println("The reply message's sender is:" + consumerName);  
        System.out.println("The reply message is '" + replyMessageContent + "'");  
    }  
}  
