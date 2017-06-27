package com.qf.rabbitmq.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import com.qf.rabbitmq.AppConstants;
import com.qf.rabbitmq.util.ApplicationContextUtil;
import com.rabbitmq.client.AMQP;

public class HandlerMQSenderThread  implements Runnable  
{  
    private Object message;  
   
    public HandlerMQSenderThread(Object message)  
    {  
        this.message = message;  
    }  
   
    public void run()  
    {  
        RabbitTemplate rabbitTemplate =  
                    (RabbitTemplate)ApplicationContextUtil.getBean("rabbitTemplate"); 
        MessagePropertiesConverter messagePropertiesConverter =  
                (MessagePropertiesConverter) ApplicationContextUtil.getBean("messagePropertiesConverter");  
        String correlationId = UUID.randomUUID().toString(); 
        Date sendTime = new Date();  
        AMQP.BasicProperties props =  
                    new AMQP.BasicProperties(MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT,  
                            "UTF-8",  
                            null,  
                            2,  
                            0, correlationId, AppConstants.SEND_EXCHANGE_NAME, null,  
                            null, sendTime, null, null,  
                            "SpringProducer", null);  
        MessageProperties sendMessageProperties =  
                messagePropertiesConverter.toMessageProperties(props, null,"UTF-8");  
        sendMessageProperties.setReceivedExchange(AppConstants.REPLY_EXCHANGE_NAME);  
        sendMessageProperties.setReceivedRoutingKey(AppConstants.REPLY_MESSAGE_KEY);  
        sendMessageProperties.setRedelivered(true);  
        Message sendMessage = MessageBuilder.withBody(toByteArray(message))  
                    .andProperties(sendMessageProperties)  
                    .build();  
        rabbitTemplate.send(AppConstants.SEND_EXCHANGE_NAME, AppConstants.SEND_HANDLER_MESSAGE_KEY, sendMessage);              
   }  
   
   public byte[] toByteArray (Object obj) {  
        byte[] bytes = null;  
        ByteArrayOutputStream bos = new ByteArrayOutputStream();  
        try {  
            ObjectOutputStream oos = new ObjectOutputStream(bos);  
            oos.writeObject(obj);  
            oos.flush();  
            bytes = bos.toByteArray ();  
            oos.close();  
            bos.close();  
        } catch (IOException ex) {  
            ex.printStackTrace();  
        }  
        return bytes;  
    }  
}     