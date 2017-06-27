package com.springframework.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.springframework.security.support.CustomUserDetailsService;
import com.springframework.security.support.CustomUsernamePasswordAuthenticationFilter;
import com.springframework.security.support.LoginSuccessHandler;
import com.springframework.security.support.MyFilterSecurityInterceptor;
  
@Configuration  
@EnableWebSecurity  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  
      
    @Autowired  
    private MyFilterSecurityInterceptor mySecurityFilter;  
      
   // @Autowired
   // CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter;
  
    
    @Autowired  
    private CustomUserDetailsService customUserDetailsService;  
    
    @Autowired  
    @Qualifier("datasource")
    private DataSource dataSource;
      
    @Override  
    public AuthenticationManager authenticationManagerBean() throws Exception {  
       
    return super.authenticationManagerBean();  
       
    }  
    //http://localhost:8080/login 输入正确的用户名密码 并且选中remember-me 则登陆成功，转到 index页面   
    //再次访问index页面无需登录直接访问  
    //访问http://localhost:8080/home 不拦截，直接访问，  
    //访问http://localhost:8080/hello 需要登录验证后，且具备 “ADMIN”权限hasAuthority("ADMIN")才可以访问  
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http  
        .addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)//在正确的位置添加我们自定义的过滤器  
        .authorizeRequests()  
        .antMatchers("/home").permitAll()  
        .anyRequest().authenticated()  
        //.antMatchers("/hello").hasAuthority("ADMIN")  
        //.antMatchers("/hello").hasRole("ADMIN") 
        .and()  
        .formLogin()  
        .loginPage("/login")      
        .permitAll()  
        .successHandler(loginSuccessHandler())//code3  
        .and()  
        .logout()  
        .logoutSuccessUrl("/home")  
        .permitAll()  
        .invalidateHttpSession(true)  
        .and()  
        .rememberMe().rememberMeParameter("remember-me").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(86400)
        .and().csrf()
        .and().exceptionHandling().accessDeniedPage("/Access_Denied"); 
    }  
    @Override  
        public void configure(WebSecurity web) throws Exception {  
            super.configure(web);  
    }  
    @Autowired  
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {     
//指定密码加密所使用的加密器为passwordEncoder()  
//需要将密码加密后写入数据库   
       // auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());  
//不删除凭据，以便记住用户  
        auth.eraseCredentials(false);   
    	auth.userDetailsService(customUserDetailsService);

		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		//daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoderMd5());
		daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
		
		//ReflectionSaltSource saltSource = new ReflectionSaltSource();
	    //saltSource.setUserPropertyToUse("username");
	    daoAuthenticationProvider.setSaltSource(reflectionSaltSource());
		auth.authenticationProvider(daoAuthenticationProvider);
    }  
     
    @Bean("saltSource")     
    public ReflectionSaltSource reflectionSaltSource() {  
    	ReflectionSaltSource saltSource = new ReflectionSaltSource();
	    saltSource.setUserPropertyToUse("username");
        return saltSource;  
    }  
    
    // Code5----------------------------------------------  
    @Bean  
    public BCryptPasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder(4);  
    }  
    
    @Bean  
    public Md5PasswordEncoder passwordEncoderMd5() {  
    	Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        return md5PasswordEncoder;  
    }  
  
    // Code3----------------------------------------------  
    @Bean  
    public LoginSuccessHandler loginSuccessHandler(){  
        return new LoginSuccessHandler();  
    }  
    
    // Code4----------------------------------------------  
  /*  @Bean  
    public JdbcTokenRepositoryImpl tokenRepository(){  
    	JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
    	jdbcTokenRepositoryImpl.setDataSource(datasource);
        return jdbcTokenRepositoryImpl;  
    } */ 
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }
    @Bean
    public JdbcTemplate jdbcTemplate(){  
    	JdbcTemplate jdbcTemplate = new JdbcTemplate();
    	jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;  
    }  
//    @Bean
//    public CustomUsernamePasswordAuthenticationFilter usernamePasswordSubdomainAuthenticationFilter() {
//        System.out.println(this.authenticationManager);
//        CustomUsernamePasswordAuthenticationFilter filer = new CustomUsernamePasswordAuthenticationFilter();
//        filer.setAuthenticationManager(authenticationManager);
//        return filer;
//    }
   
} 