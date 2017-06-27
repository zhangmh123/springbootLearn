package com.springframework.security;

import java.io.IOException;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Set;  
  
import javax.annotation.PostConstruct;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.boot.SpringApplication;  
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;  
import org.springframework.boot.autoconfigure.SpringBootApplication;  
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;  
import org.springframework.web.method.HandlerMethod;  
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;  
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;  

import com.springframework.security.entity.SysUser;
import com.springframework.security.service.SResourceService;
import com.springframework.security.service.UserService;
import com.springframework.security.support.Appctx;
import com.springframework.security.support.CustomUserDetailsService;
import com.springframework.security.support.MyFilterSecurityInterceptor;
import com.springframework.security.support.SecurityUser;


  
@SpringBootApplication  
@EnableAutoConfiguration//×¢Òâ  
public class MainApplication{  
      
    @Autowired  
    private SResourceService sresourceService;  
      
    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);  
    @PostConstruct  
     public void initApplication() throws IOException {  
         log.info("Running with Spring profile(s) : {}");   
    }  
       
    public static void main(String[] args) {  
        //SpringApplication.run(MainApplication.class, args);  
        SpringApplication app=new SpringApplication(MainApplication.class);       
        Appctx.ctx=app.run(args);  
        UserService suserService = (UserService) Appctx.ctx.getBean("suserService"); 
        SysUser su= suserService.findByName("test"); 
       // System.out.println("ÃÜÂë"+su.getPassword()); 
       // System.out.println("Ãû×Ö"+su.getName()); 
        UserDetailsService userDetailsService =  (CustomUserDetailsService) Appctx.ctx.getBean("customUserDetailsService"); 
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        System.out.println("ÃÜÂë"+userDetails.getPassword()); 
        System.out.println("Ãû×Ö"+userDetails.getUsername()); 
        SaltSource saltSource = ( SaltSource)Appctx.ctx.getBean("saltSource");
        PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        su.setPassword(passwordEncoder.encodePassword(su.getPassword(), saltSource.getSalt(userDetails)));
        //BCryptPasswordEncoder bc=new BCryptPasswordEncoder(4);//½«ÃÜÂë¼ÓÃÜ 
        //su.setPassword(bc.encode(su.getPassword())); 
        System.out.println("ÃÜÂë"+su.getPassword()); 
        suserService.update(su);
}  
}   