package com.springframework.security.support;

import java.util.Collection;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.springframework.security.MainApplication;

@Service  
public class CustomAccessDecisionManager implements AccessDecisionManager {  
	 private static final Logger log = LoggerFactory.getLogger(CustomAccessDecisionManager.class);    
    public void decide( Authentication authentication, Object object,   
            Collection<ConfigAttribute> configAttributes)   
        throws AccessDeniedException, InsufficientAuthenticationException{  
    	log.info("CustomAccessDecisionManager===========================begin");  
    	log.info("configAttributes==========================="+configAttributes);  
        if( configAttributes == null ) {  
            return ;  
        }  
          
        Iterator<ConfigAttribute> ite = configAttributes.iterator();  
          
        while( ite.hasNext()){  
            ConfigAttribute ca = ite.next();  
            String needAuth = ((SecurityConfig)ca).getAttribute();  
            log.info("访问资源应该具有的权限:"+needAuth);   
        //<span style="color:#33cc00;">   //ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。</span>  
            log.info("用户具有的权限:"+authentication.getAuthorities());  
            for( GrantedAuthority ga: authentication.getAuthorities()){  
                  
                if(needAuth.trim().equals(ga.getAuthority().trim())){  
  
                    return;  
                }  
                  
            }  
              
        }  
        log.info("权限不足");    
        throw new AccessDeniedException("权限不足");  
          
    }  
      
    public boolean supports( ConfigAttribute attribute ){  
        return true;//都要设为true</span>  
  
    }  
      
    public boolean supports(Class<?> clazz){  
        return true;//都要设为true</span>  
    }  
      
  
}  