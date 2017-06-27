package com.springboot.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SendMsgRunner implements CommandLineRunner {

    @Autowired 
    private Send send;

    public void run(String... args) throws Exception {
        System.out.println("Sending message start...");
        send.sendMsg("Hello world!");
    }

}
