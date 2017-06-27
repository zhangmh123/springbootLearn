package com.springframework.security.support;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
//@Component 
public class CustomUsernamePasswordAuthenticationFilter  extends UsernamePasswordAuthenticationFilter{
	 private static final Logger log = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
	 @Autowired  
	 private AuthenticationManager authenticationManager;  
	 public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	        logger.debug("setAuthenticationManager(AuthenticationManager) - start"); //$NON-NLS-1$
	        super.setAuthenticationManager(authenticationManager);
	        logger.debug("setAuthenticationManager(AuthenticationManager) - end"); //$NON-NLS-1$
	    }
	 public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if ( !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();
        log.info("username:"+username+",password:"+password);
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		log.info("authRequest"+authRequest);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
	 
	 @Override
	    protected void successfulAuthentication(HttpServletRequest request,
	        HttpServletResponse response,
	        FilterChain chain,
	        Authentication authResult) throws IOException, ServletException {
	        logger.debug("successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication) - start"); //$NON-NLS-1$
	        super.successfulAuthentication(request, response, chain, authResult);
	        logger.debug(new StringBuffer("登录成功！用户是:").append(authResult.getName()));
	        request.getSession().setAttribute("user", authResult.getName());
	        logger.debug("successfulAuthentication(HttpServletRequest, HttpServletResponse, FilterChain, Authentication) - end"); //$NON-NLS-1$
	    }
}
