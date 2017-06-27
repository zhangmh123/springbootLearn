package com.qf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.qf.rabbitmq")  
@EnableAutoConfiguration(exclude = RabbitAutoConfiguration.class)  
@SpringBootApplication  
public class SpringMqProducerApplication {  
   
    public static void main(String[] args) {  
        SpringApplication.run(SpringMqProducerApplication.class, args);  
    }  
}
