package com.springboot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	// �� application.properties �ж�ȡ���ã���ȡ����Ĭ��ֵΪHelloAngel

	@Value("${application.hello : Hello Angel}")
	private String hello;

	@RequestMapping("/helloJsp")
	public String helloJsp(Map<String, Object> map) {

		System.out.println("HelloController.helloJsp().hello=" + hello);

		map.put("hello", hello);

		return "helloJsp";

	}

}
