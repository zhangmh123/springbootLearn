package com.neusoft.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import com.neusoft.spring.MainApplication;
  
public class ServletInitializer extends SpringBootServletInitializer {  
	private static final Logger log = LoggerFactory.getLogger(ServletInitializer.class);  
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
    	 log.info("SpringApplicationBuilder begin run ");   
    	 return application.sources(MainApplication.class);  
    }  
      
}  