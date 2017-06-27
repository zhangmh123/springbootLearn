package com.springboot.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownSignalException;

/** * 连接管理 * */  
//@Component
public class ConnectionManage {  
  
    private volatile Connection connection;  
  
    public ConnectionManage(String rmqServerIP, int rmqServerPort,String username,String password)  
            throws IOException, Exception {  
        ConnectionFactory cf = new ConnectionFactory();  
        //System.out.println("ip:"+rmqServerIP+",port:"+rmqServerPort+",username:"+username+",password:"+password);
        cf.setHost(rmqServerIP);  
        //cf.setHost("172.29.50.151");  
        cf.setPort(rmqServerPort);  
        cf.setUsername(username);
        cf.setPassword(password);
        connection = cf.newConnection();  
    }  
  
    @SuppressWarnings("finally")  
    public Channel createChannel() {  
        Channel channel = null;  
        try {  
            channel = connection.createChannel();  
        } catch (ShutdownSignalException e1) {  
        } catch (IOException e) {  
        }  
        return channel;  
    }  
  
    public void shutdown() throws IOException {  
        if (connection != null)  
            connection.close();  
    }  
}    
