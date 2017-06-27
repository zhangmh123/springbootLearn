package com.neusoft.spring.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

//@Configuration
//@EnableScheduling
public class SchedulingConfig {
    
    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void testSchedule() {
          System.out.println(">>>>>>>>> SchedulingConfig.scheduler()");
    }
}
