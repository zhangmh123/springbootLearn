package com.springboot.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("ServletContext contextInitialized");
    }

    public void contextDestroyed(ServletContextEvent arg0) {
         System.out.println("ServletContex contextDestroyed");
    }


}