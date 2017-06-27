package com.springboot.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.qf.rabbitmq.entity.Company;
import com.qf.rabbitmq.entity.Employee;


@Component  
@RabbitListener(queues="springRabbitHandlerQueue")  
public class RabbitHandlerConsumer  
{  
    private Logger logger = LoggerFactory.getLogger(RabbitHandlerConsumer.class);  
   
    @RabbitHandler  
    public void processCompanyMessage(Company message)  
    {  
        logger.info("Received the message having Company type.");  
        logger.info("The message content is:" + message.toString());  
    }  
   
    @RabbitHandler  
    public void processEmployeeMessage(Employee message)  
    {  
        logger.info("Received the message having Employee type.");  
        logger.info("The message content is:" + message.toString());  
    }  
}  
