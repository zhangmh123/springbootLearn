package com.springboot.rabbitmq;

import java.math.BigDecimal;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.springboot.Application;
@Component
@Qualifier("asyncMessageListener")
public class AsyncMessageListener implements ChannelAwareMessageListener {  
    
    @Autowired  
    private MessagePropertiesConverter messagePropertiesConverter;  
   
    @Autowired  
    private RabbitTemplate rabbitTemplate;  
   
    public void onMessage(Message message, Channel channel) throws Exception  
    {  
        MessageProperties messageProperties = message.getMessageProperties();  
        AMQP.BasicProperties rabbitMQProperties =  
                messagePropertiesConverter.fromMessageProperties(messageProperties, "UTF-8");  
   
         String messageContent = new String(message.getBody(), "UTF-8");  
   
         System.out.println("The received message is:" + messageContent);  
   
        String consumerTag = messageProperties.getConsumerTag();  
        String result = "no data";  
        if(!StringUtils.isEmpty(messageContent)){
        	int number = Integer.parseInt(messageContent);  
              result = String.valueOf(factorial(number));  
        }
      
   
        String replyMessageContent = consumerTag + " have received the message and received message is "+ messageContent +" and result is "+result;
        Thread.sleep(1000);  
   
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
                		Application.replyExchangeName, Application.asyncReplyRoutingKey);  
   
        MessageProperties replyMessageProperties =  
                messagePropertiesConverter.toMessageProperties(replyRabbitMQProps,  
                        replyEnvelope,"UTF-8");  
   
        Message replyMessage = MessageBuilder.withBody(replyMessageContent.getBytes())  
                .andProperties(replyMessageProperties)  
                .build();  
   
        rabbitTemplate.send(Application.replyExchangeName,Application.asyncReplyRoutingKey, replyMessage);  
        channel.basicAck(messageProperties.getDeliveryTag(), false);  
    }  
    
    public static long factorial(int n){//简单的循环计算的阶乘
    	if(n==1||n==0){     
    	      return 1;    
    	    }else{     
    	      return factorial(n-1)*n;   
    	    }  
   }
}  
