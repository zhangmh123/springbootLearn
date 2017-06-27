package com.springboot.rabbitmq;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;


@RunWith(SpringRunner.class)
public class SendMsgTest {
	@SuppressWarnings("deprecation")
	//@Test  
	public void send() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException  
	{  
	    ConnectionFactory factory = new ConnectionFactory();  
	    factory.setHost("172.29.50.151");  
	    factory.setPort(5672);  
	    factory.setUsername("guest");  
	    factory.setPassword("guest");  
	    factory.setVirtualHost("test_vhost");  
	       
	    //创建与RabbitMQ服务器的TCP连接  
	    Connection connection  = factory.newConnection();  
	    Channel channel = connection.createChannel();  
	       
	    String message = "First Web RabbitMQ PRC Message";  
	       
	    String correlationId = UUID.randomUUID().toString();  
	    AMQP.BasicProperties props = new AMQP.BasicProperties  
	                        .Builder()  
	                        .correlationId(correlationId)  
	                        .build();  
	       
	    channel.basicPublish("springMessageExchange","springMessage", props, message.getBytes());  
	    
	    //生产者程序添加对返回消息队列侦听的Consumer
	    QueueingConsumer replyCustomer = new QueueingConsumer(channel);  
	    channel.basicConsume("springReplyMessageQueue",true,"Producer Reply Consumer", replyCustomer);  
	       
	    String responseMessage = null;  
	       
	    while(true)  
	    {  
	       QueueingConsumer.Delivery delivery = replyCustomer.nextDelivery();  
	       String messageCorrelationId = delivery.getProperties().getCorrelationId();  
	       System.out.println("messageCorrelationId:"+messageCorrelationId);
	       if (messageCorrelationId != null && messageCorrelationId.equals(correlationId))   
	       {  
	           responseMessage = new String(delivery.getBody());  
	           System.out.println("The reply message's correlation id is:" + messageCorrelationId);  
	           break;  
	       }  
	    }  
	    if(responseMessage != null)  
	    {  
	      System.out.println("The repsonse message is:'" + responseMessage + "'");  
	    }  
	          
	} 
	
	@Test  
	public void send2() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException  
	{ 
		Channel channel = getChannel();
	    ReplyConsumer replyCustomer = new ReplyConsumer(channel);  
	    channel.basicConsume("springReplyMessageQueue",true,"Producer Reply Consumer", replyCustomer);  
	       
	    for(int i=0; i<10; i++)  
	    {  
	       String correlationId = UUID.randomUUID().toString();  
	       String message = "Web RabbitMQ Message " + i;  
	       
	       AMQP.BasicProperties props =   
	                       new AMQP.BasicProperties  
	                            .Builder()  
	                            .contentType("text/plain")  
	                            .deliveryMode(2)  
	                            .correlationId(correlationId)  
	                            .replyTo("springReplyMessageExchange")  
	                            .build();  
	       
	       channel.basicPublish("springMessageExchange","springMessage", props, message.getBytes());  
	    }  
	}
	
	public Channel getChannel() throws IOException, TimeoutException{
		ConnectionFactory factory = new ConnectionFactory();  
	    factory.setHost("172.29.50.151");  
	    factory.setPort(5672);  
	    factory.setUsername("guest");  
	    factory.setPassword("guest");  
	    factory.setVirtualHost("test_vhost");  
	    factory.setAutomaticRecoveryEnabled(true);  
	    factory.setTopologyRecoveryEnabled(true);
	    factory.setNetworkRecoveryInterval(60000);
	    //创建与RabbitMQ服务器的TCP连接  
	    Connection connection  = factory.newConnection();  
	    Channel channel = connection.createChannel();  
	    return channel;
	}
}
