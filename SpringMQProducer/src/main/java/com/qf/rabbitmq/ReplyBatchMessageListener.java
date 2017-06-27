package com.qf.rabbitmq;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
@Component
public class ReplyBatchMessageListener implements ChannelAwareMessageListener{
	private Logger logger = LoggerFactory.getLogger(ReplyBatchMessageListener.class);  
	//���@RabbitListener��������Ϣ���У�Exchange��Bind��ϵ��RabbitMQ���������Ѿ�������
	//���ǿ�����@RabbitListener�в�ʹ��@QueueBinding,ֱ��ʹ��queues���ԣ���ʱonMessage����ͷ����ע������:
	//@RabbitListener(admin="rabbitAdmin", queues="springBatchReplyMessageQueue") 
	@RabbitListener(  
	        bindings = @QueueBinding(  
	        value = @Queue(value="springBatchReplyMessageQueue", durable = "true", exclusive = "false",autoDelete = "false"),  
	        exchange = @Exchange(value="springReplyMessageExchange", durable = "true"),  
	        key = "springBatchMessageReply"),  
	        admin="rabbitAdmin")  
	public void onMessage(Message message, Channel channel) throws Exception {
		 try   
	     {  
	          String messageContent = new String(message.getBody(), "UTF-8");  
	          logger.info("The reply message content is:" + messageContent);  
	     }   
	     catch (UnsupportedEncodingException e)   
	     {  
	          e.printStackTrace();  
	      }  
		
	}

}
