package com.hello;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CC {
	@Value("${service.title}")
	private String title;
	public String say(){
		return "okkkkkkkkkk"+title;
	}
}
