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
    //http://localhost:8080/login ������ȷ���û������� ����ѡ��remember-me ���½�ɹ���ת�� indexҳ��   
    //�ٴη���indexҳ�������¼ֱ�ӷ���  
    //����http://localhost:8080/home �����أ�ֱ�ӷ��ʣ�  
    //����http://localhost:8080/hello ��Ҫ��¼��֤���Ҿ߱� ��ADMIN��Ȩ��hasAuthority("ADMIN")�ſ��Է���  
    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http  
        .addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)//����ȷ��λ����������Զ���Ĺ�����  
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
//ָ�����������ʹ�õļ�����ΪpasswordEncoder()  
//��Ҫ��������ܺ�д�����ݿ�   
       // auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());  
//��ɾ��ƾ�ݣ��Ա��ס�û�  
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