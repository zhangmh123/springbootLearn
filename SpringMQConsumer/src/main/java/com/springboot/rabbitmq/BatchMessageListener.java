package com.springboot.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.springboot.Application;
@Component
@Qualifier("batchMessageListener")
public class BatchMessageListener implements ChannelAwareMessageListener  
{  
    private Logger logger = LoggerFactory.getLogger(BatchMessageListener.class);  
    @Autowired  
    private MessagePropertiesConverter messagePropertiesConverter; 
    
    @Autowired  
    private RabbitTemplate rabbitTemplate;  
    
    public void onMessage(Message message, Channel channel) throws Exception  
    {  
      MessageProperties messageProperties = message.getMessageProperties();  
      String consumerTag = messageProperties.getConsumerTag();  
      AMQP.BasicProperties rabbitMQProperties =  
                messagePropertiesConverter.fromMessageProperties(messageProperties, "UTF-8");  
   
      String messageContent = new String(message.getBody(), "UTF-8");  
      String correlationId = rabbitMQProperties.getCorrelationId();  
      //打印接收到的批量消息内容和correlationId，用于检验是否使用第一条消息的correaltionId.  
      logger.info("The received batch message is:" + messageContent);  
      logger.info("The correlation id is:" + correlationId);  
      String replyMessageContent = consumerTag + " have received the message and received message is "+ messageContent ;
      AMQP.BasicProperties replyRabbitMQProps =  
              new AMQP.BasicProperties("text/plain",  
                      "UTF-8",  
                      null,  
                      2,  
                      0, rabbitMQProperties.getCorrelationId(), null, null,  
                      null, null, null, null,  
                      consumerTag, null);  
      //创建返回消息的信封头  
      Envelope replyEnvelope =  
              new Envelope(messageProperties.getDeliveryTag(), true,  
              		Application.replyExchangeName, Application.REPLY_BATCH_MESSAGE_KEY);  
 
      MessageProperties replyMessageProperties =  
              messagePropertiesConverter.toMessageProperties(replyRabbitMQProps,  
                      replyEnvelope,"UTF-8");  
 
      Message replyMessage = MessageBuilder.withBody(replyMessageContent.getBytes())  
              .andProperties(replyMessageProperties)  
              .build();  
 
      rabbitTemplate.send(Application.replyExchangeName,Application.REPLY_BATCH_MESSAGE_KEY, replyMessage);  
      channel.basicAck(messageProperties.getDeliveryTag(), false);  
   }   
}    
