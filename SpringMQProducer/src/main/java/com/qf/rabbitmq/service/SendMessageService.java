package com.qf.rabbitmq.service;

import java.util.List;

public interface SendMessageService  
{  
    String sendMessage(String message);  
    
    void sendAsyncMessage(String message);  
    
    void sendBatchMessage(List<String> messages);  
    
    void sendObjectMessage(Object message);  
} 
