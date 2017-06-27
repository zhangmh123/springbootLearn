package com.springboot.rabbitmq;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.config.AmqpConfig;
  
/**  
 * ��Ϣ������  
 *   
 * @author liaokailin  
 * @version $Id: Send.java, v 0.1 2015��11��01�� ����4:22:25 liaokailin Exp $  
 */  
@Component  
public class Send implements RabbitTemplate.ConfirmCallback {  
  
    private RabbitTemplate rabbitTemplate;  
  
    /**  
     * ���췽��ע��  
     */  
    @Autowired  
    public Send(RabbitTemplate rabbitTemplate) {  
        this.rabbitTemplate = rabbitTemplate;  
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate���Ϊ�����Ļ����ǻص�����������õ�����  
    }  
  
    public void sendMsg(String content) {  
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());  
        rabbitTemplate.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.ROUTINGKEY, content, correlationId);  
    }  
  
    /**  
     * �ص�  
     */  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
        System.out.println(" �ص�id:" + correlationData);  
        if (ack) {  
            System.out.println("��Ϣ�ɹ�����");  
        } else {  
            System.out.println("��Ϣ����ʧ��:" + cause);  
        }  
    }  
  
}  