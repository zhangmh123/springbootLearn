package com.springframework.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;  
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;  
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;  
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
  
@Configuration  
@EnableWebMvc
@ComponentScan(basePackages = "com.springframework.security")
public class MvcConfig extends WebMvcConfigurerAdapter {  
//  ����ҳ��ı����ʽ������
  private static final String CONTENTTYPE = "text/html; charset=UTF-8";
    @Override  
    public void addViewControllers(ViewControllerRegistry registry) {  
        registry.addViewController("/home").setViewName("home");  
        registry.addViewController("/").setViewName("home");  
        registry.addViewController("/hello").setViewName("hello");  
        registry.addViewController("/login").setViewName("login");  
        registry.addViewController("/hello3").setViewName("hello3");  
        registry.addViewController("/hello2").setViewName("hello2");  
        registry.addViewController("/hello1").setViewName("hello1");  
        registry.addViewController("/user/hello4").setViewName("user/hello4");  
        registry.addViewController("/user/hello").setViewName("user/hello");  
        registry.addViewController("/welcome").setViewName("welcome"); 
        //registry.addViewController("/zoo/zoolist").setViewName("zoo/zoolist");  
    }  
    //Thymeleaf�������
    @Bean
    public TemplateResolver templateResolver(){
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
    //     ȥ������
        resolver.setCacheable(false);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.setTemplateResolver(templateResolver());
        return springTemplateEngine;
    }

    /**
     * ģ�����������
     * @return
     */
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setContentType(CONTENTTYPE);
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        return viewResolver;
    }
}

