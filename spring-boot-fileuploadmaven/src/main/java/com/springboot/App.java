package com.springboot;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value="com.springboot")
public class App 
{
    @Bean 
    public MultipartConfigElement multipartConfigElement() { 
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// �����ļ���С���� ,���ˣ�ҳ����׳��쳣��Ϣ����ʱ�����Ҫ�����쳣��Ϣ�Ĵ�����;
        factory.setMaxFileSize("1MB"); //KB,MB
        /// �������ϴ������ܴ�С
        factory.setMaxRequestSize("10MB"); 
        //Sets the directory location wherefiles will be stored.
        //factory.setLocation("upload");
        return factory.createMultipartConfig(); 
    } 

    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
