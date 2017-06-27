package com.springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyEnvironmentAware implements EnvironmentAware {

    @Value("${spring.datasource.url}")
    private String url;

    public void setEnvironment(Environment env) {
        //打印注入的属性信息.
        System.out.println(url);
        //通过 environment 获取到系统属性.
        System.out.println(env.getProperty("JAVA_HOME"));
        System.out.println(System.getenv("JAVA_HOME"));
         //通过 environment 同样能获取到application.properties配置的属性.
        System.out.println(env.getProperty("spring.datasource.url"));
        //获取到前缀是"spring.datasource." 的属性列表值.
        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        System.out.println("spring.datasource.url="+relaxedPropertyResolver.getProperty("url"));
        System.out.println("spring.datasource.driverClassName="+relaxedPropertyResolver.getProperty("driverClassName"));
    }

}