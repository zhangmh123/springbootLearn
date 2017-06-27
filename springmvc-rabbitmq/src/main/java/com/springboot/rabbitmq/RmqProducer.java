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
 * 生产着 
 * 
 */  
@Component
public class RmqProducer implements InitializingBean,DisposableBean  
{  
	@Value("${spring.rabbitmq.host}")  
    private String rmqServerIP;//ip地址  
	@Value("${spring.rabbitmq.port}") 
    private int rmqServerPort;//端口    
	@Value("${spring.rabbitmq.username}")
	private String username;
	@Value("${spring.rabbitmq.password}")
	private String password;
	@Value("${spring.rabbitmq.threadPoolNum}") 
	private int threadPoolNum;//线程数  
    public static final String exchangeType = "topic"; //类型  
    public static final String  exchange = "testExchange";//交换器     
    private ConnectionManage connectManage;  
    private Channel channel;        
    private ConcurrentHashMap<Thread, Channel> channelManager;
    private ExecutorService threadPool;
      
    /** 
     * 初始化 
     */  
    public void afterPropertiesSet() throws Exception   
    {  
        //创建连接管理器  
        connectManage=new ConnectionManage(rmqServerIP,rmqServerPort,username,password);  
        boolean durable=true;//是否持久化  
        boolean autoDelete=false;//是否自动删除  
        //Channel channel=connectManage.createChannel();  
        channel=connectManage.createChannel();  
        channel.exchangeDeclare(exchange, exchangeType, durable,autoDelete,null);  
          
    }  
      
      
      
      
      
      
    /** 
     * 发送信息 
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
    	            channelManager.put(thread, channel);//创建线程和channel对应起来  
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
