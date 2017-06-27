package com.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.support.DefaultMessagePropertiesConverter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.util.backoff.FixedBackOff;




@EnableRabbit
@SpringBootApplication
public class Application {

     public final static String queueName = "springMessageQueue";
     public final static String exchangeName = "springMessageExchange";
     public static final String routingKey = "springMessage";  
	 public final static String replyQueueName = "springReplyMessageQueue";
	 public final static String replyExchangeName = "springReplyMessageExchange";
	 public static final String replyRoutingKey = "springReplyMessage";  
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;
    
    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;
    @Bean  
    public ConnectionFactory connectionFactory() {  
    	System.out.println("ip:"+host+",port:"+port+",username:"+username+",password:"+password);
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setAddresses(host+":"+port);  
        connectionFactory.setUsername(username);  
        connectionFactory.setPassword(password);  
        connectionFactory.setVirtualHost(virtualHost);  
        connectionFactory.setCacheMode(CacheMode.CONNECTION);
        connectionFactory.setConnectionCacheSize(10);
        connectionFactory.setConnectionLimit(10);
        return connectionFactory;  
    } 
    @Bean
    public RabbitAdmin rabbitAdmin(){
    	RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory());
    	rabbitAdmin.setAutoStartup(true);
    	return rabbitAdmin;
    }
    
    @Bean  
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        return template;  
    } 
    //RabbitMQ消息转化器，用于将RabbitMQ消息转换为AMQP消息，我们这里使用基本的Message Converter 
    @Bean
    public SimpleMessageConverter simpleMessageConverter() {
        return new SimpleMessageConverter();
    }
    @Bean
    public DefaultMessagePropertiesConverter propertiesConverter(){
    	return new DefaultMessagePropertiesConverter();
    }
    @Bean
    public Queue queue() {
    	Queue queue = new Queue(queueName,true,false,false);
    	//定义AMQP Queue创建所需的RabbitAdmin对象
    	queue.setAdminsThatShouldDeclare(rabbitAdmin());
    	//判断是否需要在连接RabbitMQ后创建Queue 
    	queue.setShouldDeclare(true);
    	return queue;
    }

    @Bean
    public DirectExchange exchange() {
    	DirectExchange directExchange = new DirectExchange(exchangeName,true,false);
    	directExchange.setAdminsThatShouldDeclare(rabbitAdmin());
    	directExchange.setShouldDeclare(true);
    	return directExchange;
    }

    @Bean  
    public Binding binding() {  
    	Map<String,Object> param=new HashMap<String, Object>();  
    	return new Binding(queueName,Binding.DestinationType.QUEUE,exchangeName,routingKey,param);
    } 
    
    @Bean
    public Queue replyQueue() {
    	Queue replyQueue = new Queue(replyQueueName,true,false,false);
    	//定义AMQP Queue创建所需的RabbitAdmin对象
    	replyQueue.setAdminsThatShouldDeclare(rabbitAdmin());
    	//判断是否需要在连接RabbitMQ后创建Queue 
    	replyQueue.setShouldDeclare(true);
    	return replyQueue;
    }

    @Bean
    public DirectExchange replyExchange() {
    	DirectExchange replyExchange = new DirectExchange(replyExchangeName,true,false);
    	replyExchange.setAdminsThatShouldDeclare(rabbitAdmin());
    	replyExchange.setShouldDeclare(true);
    	return replyExchange;
    }

    @Bean  
    public Binding replyBinding() {  
    	Map<String,Object> param=new HashMap<String, Object>();  
    	return new Binding(replyQueueName,Binding.DestinationType.QUEUE,replyExchangeName,replyRoutingKey,param);
    } 
    
    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
    		@Qualifier("rabbitMQConsumer")ChannelAwareMessageListener rabbitMQConsumer,@Qualifier("consumerTagStrategy")ConsumerTagStrategy consumerTagStrategy) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName,replyQueueName);
        container.setMessageListener(rabbitMQConsumer);        
        container.setConsumerTagStrategy(consumerTagStrategy);
        container.setMessageConverter(simpleMessageConverter());
        //消费者并发数设置
        container.setConcurrentConsumers(5);
        container.setMaxConcurrentConsumers(5);
        //消费者消息预取数设置
        container.setPrefetchCount(5);
        //设置消息手动/自动确认模式
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //当执行RabbitMQ重连时，Message Listener Container也会对Consumer进行重新恢复，
        //它的恢复间隔是由recoveryBackOff属性决定的
        container.setRecoveryBackOff(fixedBackOff());
        return container;
    }
    @Bean
    public FixedBackOff fixedBackOff(){
    	return new FixedBackOff(60000,100);
    }
//    @Bean
//    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapter,@Qualifier("consumerTagStrategy")ConsumerTagStrategy consumerTagStrategy) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
//        container.setConsumerTagStrategy(consumerTagStrategy);
//        return container;
//    }

  /*  @Bean
    MessageListenerAdapter listenerAdapter(RmqConsumer rmqConsumer) {
    	MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(rmqConsumer, "rmqProducerMessage"); 
    	listenerAdapter.setMessageConverter(simpleMessageConverter());
    	return listenerAdapter;
    }*/

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}