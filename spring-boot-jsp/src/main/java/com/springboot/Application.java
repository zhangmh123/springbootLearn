package com.springboot;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// @EnableConfigurationProperties({Test2Settings.class})
// @ServletComponentScan("com.springboot.servlet")
@ComponentScan("com.springboot")
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {

		// SpringApplication.run(Application.class, args);
		SpringApplication application = new SpringApplication(Application.class);

		/*
		 * 
		 * Banner.Mode.OFF:关闭;
		 * 
		 * Banner.Mode.CONSOLE:控制台输出，默认方式;
		 * 
		 * Banner.Mode.LOG:日志输出方式;
		 */

		application.setBannerMode(org.springframework.boot.Banner.Mode.CONSOLE);

		application.run(args);

	}

	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {

		return application.sources(Application.class);

	}

	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// // 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
		factory.setMaxFileSize("1MB"); // KB,MB
		// / 设置总上传数据总大小
		factory.setMaxRequestSize("10MB");
		// Sets the directory location wherefiles will be stored.
		 factory.setLocation("upload");
		return factory.createMultipartConfig();
	}

	// @Bean
	// public ServletRegistrationBean myServlet1() {
	//
	// return new ServletRegistrationBean(new MyServlet1(), "/myServlet/*");
	// }
}
