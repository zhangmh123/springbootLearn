package com.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.springboot.rabbitmq.RmqConsumer;



@EnableRabbit
@SpringBootApplication
public class Application {

    public final static String queueName = "testQueue";
    public final static String exchangeName = "topicExchange";
    public static final String ROUTINGKEY = "testQueue-routingKey";  
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
        connectionFactory.setPublisherConfirms(true); //����Ҫ����  
        return connectionFactory;  
    } 
    @Bean
    public RabbitAdmin rabbitAdmin(@Qualifier("connectionFactory")ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
    
    @Bean  
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        return template;  
    } 
    
    @Bean
    SimpleMessageConverter simpleMessageConverter() {
        return new SimpleMessageConverter();
    }
    @Bean
    Queue queue() {
        return new Queue(queueName,true,false,false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName,true,false);
    }

    @Bean  
    public Binding binding() {  
        //return BindingBuilder.bind(queue()).to(exchange()).with(ROUTINGKEY); 
    	Map<String,Object> param=new HashMap<String, Object>();  
    	return new Binding(queueName,Binding.DestinationType.QUEUE,exchangeName,ROUTINGKEY,param);
    } 

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter,@Qualifier("consumerTagStrategy")ConsumerTagStrategy consumerTagStrategy) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setConsumerTagStrategy(consumerTagStrategy);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RmqConsumer rmqConsumer) {
    	MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(rmqConsumer, "rmqProducerMessage"); 
    	listenerAdapter.setMessageConverter(simpleMessageConverter());
    	return listenerAdapter;
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(Application.class, args);
    }

}