package com.neusoft.spring.configure;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.neusoft.spring.model.Demo;


@Configuration
public class BeanConfig {
    @Bean(name="testDemo")
    public Demo generateDemo() {
        Demo demo = new Demo();
        demo.setId(12345);
        demo.setName("test");
        return demo;
    }
}
