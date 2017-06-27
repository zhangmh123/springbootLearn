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

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
@Component
@Qualifier("caculateListener")
public class CaculateListener implements ChannelAwareMessageListener{
	 @Autowired  
	    private MessagePropertiesConverter messagePropertiesConverter;  
	   
	    @Autowired  
	    private RabbitTemplate rabbitTemplate;  
	   
	    public void onMessage(Message message, Channel channel) throws Exception   
	    {  
	        MessageProperties messageProperties = message.getMessageProperties();  
	        AMQP.BasicProperties rabbitMQProperties =  
	                messagePropertiesConverter.fromMessageProperties(messageProperties, "UTF-8");  
	        String numberContent = null;  
	        numberContent = new String(message.getBody(),"UTF-8");  
	        System.out.println("The received number is:" + numberContent);  
	        String consumerTag = messageProperties.getConsumerTag();  
	        //int number = Integer.parseInt(numberContent);  
	        BigDecimal number=new BigDecimal(numberContent);
	        String result = factorial(number).toString();  
	        // System.out.println("计算结果为result="+result);
	        AMQP.BasicProperties replyRabbitMQProps =  
	                new AMQP.BasicProperties("text/plain",  
	                        "UTF-8",  
	                        null,  
	                        2,  
	                        0, rabbitMQProperties.getCorrelationId(), null, null,  
	                        null, null, null, null,  
	                        consumerTag, null);  
	        Envelope replyEnvelope =  
	                new Envelope(messageProperties.getDeliveryTag(), true,  
	                        "springReplyMessageExchange", "springMessageReply");  
	   
	        MessageProperties replyMessageProperties =  
	                messagePropertiesConverter.toMessageProperties(replyRabbitMQProps,  
	                        replyEnvelope,"UTF-8");  
	   
	        Message replyMessage = MessageBuilder.withBody(result.getBytes())  
	                .andProperties(replyMessageProperties)  
	                .build();  
	   
	        rabbitTemplate.send("springReplyMessageExchange","springMessageReply", replyMessage);  
	        channel.basicAck(messageProperties.getDeliveryTag(), false);  
	    }  
	    
	    public static BigDecimal factorial(BigDecimal n){//简单的循环计算的阶乘
	    	 BigDecimal bd1 = new BigDecimal(1);//BigDecimal类型的1  
	         BigDecimal bd2 = new BigDecimal(2);//BigDecimal类型的2</span><span>  
	         BigDecimal result = bd1;//结果集，初值取1  
	         while(n.compareTo(bd1) > 0){//参数大于1，进入循环  
	             result = result.multiply(n.multiply(n.subtract(bd1)));//实现result*（n*（n-1））  
	             n = n.subtract(bd2);//n-2后继续  
	         }  
	         return result;   
	    }
}
