package com.springboot.servlet;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MyHttpSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent arg0) {
         System.out.println("MyHttpSessionListener sessionCreated");
    }

    public void sessionDestroyed(HttpSessionEvent arg0) {
        System.out.println("MyHttpSessionListener sessionDestroyed");
    }

}
