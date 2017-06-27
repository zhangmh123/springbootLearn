package com.springframework.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@PropertySource(value = { "classpath:application.properties" })
public class MyConfiguration {
	 @Autowired
	 private Environment environment;
	@Bean
	public DataSource datasource() {
		org.springframework.jdbc.datasource.DriverManagerDataSource dataSource = new org.springframework.jdbc.datasource.DriverManagerDataSource();
		 dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
	     dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
	     dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
	     dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		return dataSource;
	}

}
