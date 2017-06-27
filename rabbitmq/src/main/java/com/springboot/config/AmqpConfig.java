package com.springboot.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;
  
/**  
 * Qmqp Rabbitmq  
 *   
 * http://docs.spring.io/spring-amqp/docs/1.4.5.RELEASE/reference/html/  
 *   
 * @author lkl  
 * @version $Id: AmqpConfig.java, v 0.1 2015��11��01�� ����2:05:37 lkl Exp $  
 */  
  
@Configuration  
public class AmqpConfig {  
  
    public static final String EXCHANGE   = "spring-boot-exchange";  
    public static final String ROUTINGKEY = "spring-boot-routingKey";  
  
    @Bean  
    public ConnectionFactory connectionFactory() {  
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setAddresses("172.29.50.151:5672");  
        connectionFactory.setUsername("guest");  
        connectionFactory.setPassword("guest");  
        connectionFactory.setVirtualHost("/");  
        connectionFactory.setPublisherConfirms(true); //����Ҫ����  
        return connectionFactory;  
    }  
  
    @Bean  
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
    //������prototype����  
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        return template;  
    }  
  
    /**  
     * �������������  
     * 1. ���ý���������  
     * 2. �����а󶨵�������  
     *   
     *   
        FanoutExchange: ����Ϣ�ַ������еİ󶨶��У���routingkey�ĸ���  
        HeadersExchange ��ͨ���������key-valueƥ��  
        DirectExchange:����routingkey�ַ���ָ������  
        TopicExchange:��ؼ���ƥ��  
     */  
    @Bean  
    public DirectExchange defaultExchange() {  
        return new DirectExchange(EXCHANGE);  
    }  
  
    @Bean  
    public Queue queue() {  
        return new Queue("spring-boot-queue", true); //���г־�  
  
    }  
  
    @Bean  
    public Binding binding() {  
        return BindingBuilder.bind(queue()).to(defaultExchange()).with(AmqpConfig.ROUTINGKEY);  
    }  
    /**
     * Required for executing adminstration functions against an AMQP Broker
     */
 /*   @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }*/
   
 /*  @Bean
   public RabbitAdmin rabbitAdmin(@Qualifier("connectionFactory")ConnectionFactory connectionFactory){
       return new RabbitAdmin(connectionFactory);
   }*/
    @Bean  
    public SimpleMessageListenerContainer messageContainer() {  
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());  
        container.setQueues(queue());  
        container.setExposeListenerChannel(true);  
        container.setMaxConcurrentConsumers(1);  
        container.setConcurrentConsumers(1);  
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //����ȷ��ģʽ�ֹ�ȷ��  
        container.setMessageListener(new ChannelAwareMessageListener() {  
  
            public void onMessage(Message message, Channel channel) throws Exception {  
                byte[] body = message.getBody();  
                System.out.println("receive msg : " + new String(body));  
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //ȷ����Ϣ�ɹ�����  
            }  
        });  
        return container;  
    }  
  
}  