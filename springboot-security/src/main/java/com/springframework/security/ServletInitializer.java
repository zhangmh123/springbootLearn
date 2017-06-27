package com.springframework.security;

import javax.servlet.FilterRegistration;  
import javax.servlet.ServletContext;  
import javax.servlet.ServletException;  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;  
import org.springframework.boot.web.support.SpringBootServletInitializer;  
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;  
  
public class ServletInitializer extends SpringBootServletInitializer {  
	private static final Logger log = LoggerFactory.getLogger(ServletInitializer.class);  
    @Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
    	 log.info("SpringApplicationBuilder begin run ");   
    	 return application.sources(MainApplication.class);  
    }  
      
    @Override  
    public void onStartup(ServletContext servletContext)  
    throws ServletException {  
    	log.info("ServletInitializer..........................onStartup");  
     FilterRegistration.Dynamic openEntityManagerInViewFilter = servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class);  
         openEntityManagerInViewFilter.setInitParameter("entityManagerFactoryBeanName","entityManagerFactory");  
         openEntityManagerInViewFilter.addMappingForUrlPatterns(null, false, "/*");  
    super.onStartup(servletContext);  
    }  
}  