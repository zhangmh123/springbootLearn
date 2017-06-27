package com.springboot.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Created by zggdczfr on 2016/9/29.
 * Spring Boot主类
 * //@EnableScheduling   //�?启定时任�?
 * //@EnableAsync        //�?启异步任�?
 * //@EnableCaching      //�?启缓�?
 */
@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}