package com.springboot.rabbitmq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageListener;
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
@Component
@Qualifier("rabbitMQConsumer")
public class RabbitMQConsumer implements ChannelAwareMessageListener 
//设置消息自动确认模式时实现MessageListener接口，手动确认模式时实现ChannelAwareMessageListener
//public class RabbitMQConsumer implements MessageListener  
{  
    @Autowired  
    private MessagePropertiesConverter messagePropertiesConverter;
    @Autowired  
    private RabbitTemplate rabbitTemplate;  

	public void onMessage(Message message, Channel channel) throws Exception {
		try   
        {  
             //spring-amqp Message对象中的Message Properties属性  
             MessageProperties messageProperties = message.getMessageProperties();               
             //使用Message Converter将spring-amqp Message对象中的Message Properties属性  
             //转换为RabbitMQ 的Message Properties对象  
             AMQP.BasicProperties rabbitMQProperties =  
                    messagePropertiesConverter.fromMessageProperties(messageProperties, "UTF-8");               
             System.out.println("The message's correlationId is:" + rabbitMQProperties.getCorrelationId());  
             String messageContent = null;  
             messageContent = new String(message.getBody(),"UTF-8");  
             System.out.println("The message content is:" + messageContent);  
             String consumerTag = messageProperties.getConsumerTag(); 
             String replyMessageContent = consumerTag + " have received the message '" + messageContent + "'";  
             
             Thread.sleep(60000);  
             //创建返回消息的RabbitMQ Message Properties  
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
                               "springReplyMessageExchange", "springReplyMessage");  
          
           //创建返回消息的spring-amqp Message Properties属性  
           MessageProperties replyMessageProperties =  
                    messagePropertiesConverter.toMessageProperties(replyRabbitMQProps,   
                               replyEnvelope,"UTF-8");  
          
           //构建返回消息(spring-amqp消息)  
           Message replyMessage = MessageBuilder.withBody(replyMessageContent.getBytes())  
                                                .andProperties(replyMessageProperties)  
                                                .build();  
             rabbitTemplate.send("springReplyMessageExchange","springReplyMessage", replyMessage); 
             //channel.basicPublish(rabbitMQProperties.getReplyTo(), "springReplyMessage", rabbitMQProperties, replyMessageContent.getBytes());
             //Channel.basicAck方法对消息进行手动确认
             channel.basicAck(messageProperties.getDeliveryTag(), false);  
        }  
        catch (IOException e) {  
            e.printStackTrace();  
        }  
		
	}  
   
//    public void onMessage(Message message)  
//    {  
//        try   
//        {  
//             //spring-amqp Message对象中的Message Properties属性  
//             MessageProperties messageProperties = message.getMessageProperties();  
//             //使用Message Converter将spring-amqp Message对象中的Message Properties属性  
//             //转换为RabbitMQ 的Message Properties对象  
//             AMQP.BasicProperties rabbitMQProperties =  
//                messagePropertiesConverter.fromMessageProperties(messageProperties, "UTF-8");               
//             System.out.println("The message's correlationId is:" + rabbitMQProperties.getCorrelationId());  
//             String messageContent = null;  
//             messageContent = new String(message.getBody(),"UTF-8");  
//             System.out.println("The message content is:" + messageContent);  
//             String consumerTag = messageProperties.getConsumerTag();  
//             String replyMessageContent = consumerTag+" have received the message '" + messageContent + "'";  
//             //创建返回消息的RabbitMQ Message Properties  
//             AMQP.BasicProperties replyRabbitMQProps =  
//                      new AMQP.BasicProperties("text/plain",  
//                                    "UTF-8",  
//                                     null,  
//                                     2,  
//                                     0, rabbitMQProperties.getCorrelationId(), null, null,  
//                                     null, null, null, null,  
//                                     consumerTag, null);  
//             //创建返回消息的信封头  
//             Envelope replyEnvelope =  
//                      new Envelope(messageProperties.getDeliveryTag(), true,   
//                                 "springReplyMessageExchange", "springReplyMessage");  
//            
//             //创建返回消息的spring-amqp Message Properties属性  
//             MessageProperties replyMessageProperties =  
//                      messagePropertiesConverter.toMessageProperties(replyRabbitMQProps,   
//                                 replyEnvelope,"UTF-8");  
//            
//             //构建返回消息(spring-amqp消息)  
//             Message replyMessage = MessageBuilder.withBody(replyMessageContent.getBytes())  
//                                                  .andProperties(replyMessageProperties)  
//                                                  .build();  
//            
//             rabbitTemplate.send("springReplyMessageExchange","springReplyMessage", replyMessage); 
//        }   
//        catch (UnsupportedEncodingException e) {  
//            e.printStackTrace();  
//        }  
//    }  
}  
