package com.qf.rabbitmq.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import com.qf.rabbitmq.AppConstants;
import com.qf.rabbitmq.util.ApplicationContextUtil;
import com.rabbitmq.client.AMQP;

public class  MQSenderSupplier implements Supplier<String> {  
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String message;  
   
    public MQSenderSupplier(String message)  
    {  
        this.message = message;  
    }  
   
    public String get()  
    {  
        Date sendTime = new Date();  
        String correlationId = UUID.randomUUID().toString();  
   
        MessagePropertiesConverter messagePropertiesConverter =  
                (MessagePropertiesConverter) ApplicationContextUtil.getBean("messagePropertiesConverter");  
   
        RabbitTemplate rabbitTemplate =  
                 (RabbitTemplate)ApplicationContextUtil.getBean("rabbitTemplate");  
   
        AMQP.BasicProperties props =  
                new AMQP.BasicProperties("text/plain",  
                        "UTF-8",  
                        null,  
                        2,  
                        0, correlationId, AppConstants.REPLY_EXCHANGE_NAME, null,  
                        null, sendTime, null, null,  
                        "SpringProducer", null);  
   
        MessageProperties sendMessageProperties =  
                messagePropertiesConverter.toMessageProperties(props, null,"UTF-8");  
        sendMessageProperties.setReceivedExchange(AppConstants.REPLY_EXCHANGE_NAME);  
        sendMessageProperties.setReceivedRoutingKey(AppConstants.REPLY_MESSAGE_KEY);  
        sendMessageProperties.setRedelivered(true);  
   
        Message sendMessage = MessageBuilder.withBody(message.getBytes())  
                .andProperties(sendMessageProperties)  
                .build();  
   
        Message replyMessage =  
                rabbitTemplate.sendAndReceive(AppConstants.SEND_EXCHANGE_NAME,  
                        AppConstants.SEND_MESSAGE_KEY, sendMessage);  
        logger.info("Send the caculate number to consumer");  
        String replyMessageContent = null;  
        try {  
            replyMessageContent = new String(replyMessage.getBody(),"UTF-8");  
            logger.info("The reply message is:" + replyMessageContent);  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }     
        logger.info("The following operation");  
        return replyMessageContent;  
    }  
}                