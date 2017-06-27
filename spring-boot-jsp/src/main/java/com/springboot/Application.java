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
		 * Banner.Mode.OFF:�ر�;
		 * 
		 * Banner.Mode.CONSOLE:����̨�����Ĭ�Ϸ�ʽ;
		 * 
		 * Banner.Mode.LOG:��־�����ʽ;
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
		// // �����ļ���С���� ,���ˣ�ҳ����׳��쳣��Ϣ����ʱ�����Ҫ�����쳣��Ϣ�Ĵ�����;
		factory.setMaxFileSize("1MB"); // KB,MB
		// / �������ϴ������ܴ�С
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
