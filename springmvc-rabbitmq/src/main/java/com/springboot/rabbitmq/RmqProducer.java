package com.springboot.rabbitmq;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/** 
 * ������ 
 * 
 */  
@Component
public class RmqProducer implements InitializingBean,DisposableBean  
{  
	@Value("${spring.rabbitmq.host}")  
    private String rmqServerIP;//ip��ַ  
	@Value("${spring.rabbitmq.port}") 
    private int rmqServerPort;//�˿�    
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.threadPoolNum}") 
	private int threadPoolNum;//�߳���  
    public static final String exchangeType = "topic"; //����  
    public static final String  exchange = "testExchange";//������     
    private ConnectionManage connectManage;  
    private Channel channel;        
    private ConcurrentHashMap<Thread, Channel> channelManager;
    private ExecutorService threadPool;
      
    /** 
     * ��ʼ�� 
     */  
    public void afterPropertiesSet() throws Exception   
    {  
        //�������ӹ�����  
        connectManage=new ConnectionManage(rmqServerIP,rmqServerPort,username,password);  
        boolean durable=true;//�Ƿ�־û�  
        boolean autoDelete=false;//�Ƿ��Զ�ɾ��  
        //Channel channel=connectManage.createChannel();  
        channel=connectManage.createChannel();  
        channel.exchangeDeclare(exchange, exchangeType, durable,autoDelete,null);  
          
    }  
      
      
      
      
      
      
    /** 
     * ������Ϣ 
     * @param msg 
     * @throws IOException 
     */  
    public void sendMessage(final RabbitMessage  msg) throws IOException  
    {  
          
          
        //channel.basicPublish(msg.getExchange(),msg.getRouteKey(),MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getSerialBytes());  
    	channelManager=new ConcurrentHashMap<Thread, Channel>();  
    	threadPool=Executors.newFixedThreadPool(threadPoolNum, new ThreadFactory(){  
    	    public Thread newThread(Runnable r)   
    	    {  
    	        Thread thread=new Thread(r);  
    	        Channel channel = connectManage.createChannel();  
    	        if(channel!=null)  
    	            channelManager.put(thread, channel);//�����̺߳�channel��Ӧ����  
    	        return thread;  
    	    }             
    	}
    	
    	);  
    	Runnable runnable=new Runnable() {  
    	    public void run()   
    	    {  
    	        Thread thread=Thread.currentThread();  
    	        Channel channel=channelManager.get(thread);  
    	        if(channel!=null){
    	        	 channelManager.put(thread, channel);  
    	        } else{
    	        	channel = connectManage.createChannel();  
    	        }     	           
    	        try { 
    	        	//System.out.println("channel=="+channel);
    	            channel.basicPublish(msg.getExchange(),msg.getRouteKey(),MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getSerialBytes());  
    	        } catch (IOException e) {  
    	            e.printStackTrace();  
    	        }  
    	    }  
    	};  
    	  
    	threadPool.execute(runnable);  
    }  
  
    /** 
     *  
     * @throws Exception 
     */  
    public void destroy() throws Exception   
    {  
        connectManage.shutdown();  
    }  
  
      
}  
