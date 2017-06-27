package com.springboot.test;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties(prefix="test")
@PropertySource("classpath:test.properties")
public class Test2Settings {
    private String t1;
    private String t2;
    public String getT1() {
        return t1;
    }
    public void setT1(String t1) {
        this.t1 = t1;
    }
    public String getT2() {
        return t2;
    }
    public void setT2(String t2) {
        this.t2 = t2;
    }
    @Override
    public String toString() {
        return "Test2Settings [t1=" + t1 + ", t2=" + t2 + "]";
    }
    
}