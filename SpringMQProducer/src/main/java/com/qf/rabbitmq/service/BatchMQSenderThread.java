package com.qf.rabbitmq.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.BatchingRabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;

import com.qf.rabbitmq.AppConstants;
import com.qf.rabbitmq.util.ApplicationContextUtil;
import com.rabbitmq.client.AMQP;

public class BatchMQSenderThread  implements Runnable  
{  
    private Logger logger = LoggerFactory.getLogger(BatchMQSenderThread.class);  
   
    private List<String> messageList;  
   
    public BatchMQSenderThread(List<String> messageList)  
    {  
        this.messageList = messageList;  
    }  
   
    public void run()  
    {  
    	MessagePropertiesConverter messagePropertiesConverter = (MessagePropertiesConverter) ApplicationContextUtil
				.getBean("messagePropertiesConverter");
        BatchingRabbitTemplate rabbitTemplate =  
                (BatchingRabbitTemplate) ApplicationContextUtil.getBean("batchRabbitTemplate");  
        for(String message:messageList)  
        {  
            Date sendTime = new Date();  
            String correlationId = UUID.randomUUID().toString();  
   
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
            sendMessageProperties.setReceivedRoutingKey(AppConstants.REPLY_BATCH_MESSAGE_KEY);  
            sendMessageProperties.setRedelivered(true);  
   
            Message sendMessage = MessageBuilder.withBody(message.getBytes())  
                    .andProperties(sendMessageProperties)  
                    .build();  
   
            logger.info("Send message '" + message + "' to batch");  
            logger.info("The message's correlation id is:" + correlationId);  
   
            rabbitTemplate.send(AppConstants.SEND_EXCHANGE_NAME, AppConstants.SEND_BATCH_MESSAGE_KEY, sendMessage, null);
            //rabbitTemplate.sendAndReceive(AppConstants.SEND_EXCHANGE_NAME, AppConstants.SEND_BATCH_MESSAGE_KEY, sendMessage, null);
        }  
}  
}    
