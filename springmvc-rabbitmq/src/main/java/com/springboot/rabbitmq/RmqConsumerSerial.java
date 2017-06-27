package com.springboot.rabbitmq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
@Component
public class RmqConsumerSerial implements InitializingBean,DisposableBean {
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
    public static final String  queueName = "testQueue";//交换器   
    public static final String  routeKey = "testQueue";//交换器  
    private ConnectionManage connectManage;  
    //private Channel channel;        
    private HashMap<Thread, Channel> channelManager;
    private ExecutorService threadPool;
    public void afterPropertiesSet() throws Exception   
    {  
        start();  
    }  
      
    public void destroy() throws Exception   
    {  
    	connectManage.shutdown();  
    }  
      
    public void start() throws Exception  
    {  
        connectManage=new ConnectionManage(rmqServerIP,rmqServerPort,username,password);      
                  
        //向rmq创建exchange，queue  
        boolean durable=true,exclusive=false,autoDelete=false;  
        Channel channel=connectManage.createChannel();  
        channel.exchangeDeclare(exchange, exchangeType, durable,autoDelete,null);  
        channel.queueDeclare(queueName, durable, exclusive, autoDelete, null);  
        channel.queueBind(queueName, exchange, routeKey);  
        channel.close();  
                  
        //启动线程池  
        channelManager=new HashMap<Thread, Channel>();  
        threadPool=Executors.newFixedThreadPool(threadPoolNum, new ThreadFactory(){  
            public Thread newThread(Runnable r)   
            {  
                Thread thread=new Thread(r);  
                try {  
                    Channel channel = connectManage.createChannel();  
                    if(channel!=null)  
                    {  
                        channelManager.put(thread, channel);  
                        channel.basicQos(1);  
                    }  
                } catch (IOException e) {  
                    System.out.println(e.getMessage());                      
                }  
                return thread;  
            }             
        });  
  
        for(int i=0;i<threadPoolNum;i++)  
            threadPool.execute(getRunable());  
    }  
      
    protected  Runnable getRunable(){  
          
        return new Runnable() {  
            public void run()   
            {  
                Thread thread=Thread.currentThread();  
                final Channel channel=channelManager.get(thread);  
                boolean autoAck=false;  
                DefaultConsumer consumer =  new DefaultConsumer(channel) {  
                     public void handleDelivery(String consumerTag,  
                                                Envelope envelope,  
                                                AMQP.BasicProperties properties,  
                                                byte[] body)  
                         throws IOException  
                         {  
                            long deliveryTag = envelope.getDeliveryTag();  
                            boolean suc=false;  
                            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(body));                              
                            try {  
                                Object obj=ois.readObject();  
                                RabbitMessage rmqMsg = RabbitMessage.class.cast(obj);         
                                Object[] objs=rmqMsg.getParams();  
                                System.out.println("rmqMsg.getParams()=="+rmqMsg.getParams()[0].toString());  
                                suc=true;  
  
                            } catch (Exception e) {  
                            }              
                            if(suc)  
                                channel.basicAck(deliveryTag, false);  
                            else  
                                channel.basicNack(deliveryTag, false,true);  
                         }  
                     };           
                try {  
                    channel.basicConsume(queueName, autoAck, consumer);               
                }catch (IOException e) {  
                    System.out.println(e.getMessage());  
                }   
            }  
        };  
    }  } 