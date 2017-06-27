package com.springframework.security.support;

import java.io.IOException;  
import java.util.Set;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;  
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;  

import com.springframework.security.entity.SysUser;
  
  
public class LoginSuccessHandler extends  SavedRequestAwareAuthenticationSuccessHandler {  
	 private static final Logger log = LoggerFactory.getLogger(LoginSuccessHandler.class);
	@Override    
    public void onAuthenticationSuccess(HttpServletRequest request,    
            HttpServletResponse response, Authentication authentication) throws IOException,    
            ServletException {    
        //�����Ȩ��ɵõ��û���Ϣ   ��ʹ��SUserService�������ݿ����  
        SysUser userDetails = (SysUser)authentication.getPrincipal();    
       /* Set<SysRole> roles = userDetails.getSysRoles();*/  
        //�����¼��ʾ��Ϣ    
        log.info("�û� " + userDetails.getName() + " ��¼�ɹ���");    
        
        log.info("�û� ���룺" + userDetails.getPassword() + "��");    
          
        log.info("IP :"+getIpAddress(request));  
                
        super.onAuthenticationSuccess(request, response, authentication);    
    }    
      
    public String getIpAddress(HttpServletRequest request){      
        String ip = request.getHeader("x-forwarded-for");      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("Proxy-Client-IP");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("WL-Proxy-Client-IP");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("HTTP_CLIENT_IP");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");      
        }      
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {      
            ip = request.getRemoteAddr();      
        }      
        return ip;      
    }    
}  
