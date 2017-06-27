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
        //��ӡע���������Ϣ.
        System.out.println(url);
        //ͨ�� environment ��ȡ��ϵͳ����.
        System.out.println(env.getProperty("JAVA_HOME"));
        System.out.println(System.getenv("JAVA_HOME"));
         //ͨ�� environment ͬ���ܻ�ȡ��application.properties���õ�����.
        System.out.println(env.getProperty("spring.datasource.url"));
        //��ȡ��ǰ׺��"spring.datasource." �������б�ֵ.
        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
        System.out.println("spring.datasource.url="+relaxedPropertyResolver.getProperty("url"));
        System.out.println("spring.datasource.driverClassName="+relaxedPropertyResolver.getProperty("driverClassName"));
    }

}