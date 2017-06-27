package com.springboot.test;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.springboot.service.HelloService;

////SpringJUnit֧�֣��ɴ�����Spring-Test���֧�֣�

@RunWith(SpringJUnit4ClassRunner.class)
// //ָ������SpringBoot���̵�������
@SpringBootTest
// /������Web��Ŀ��Junit��Ҫģ��ServletContext�����������Ҫ�����ǵĲ��������@WebAppConfiguration��
@WebAppConfiguration
public class HelloServiceTest {

	@Resource
	private HelloService helloService;
	
	@Before
	public void before() {
		System.out.println("��ÿ�����Է���ǰִ�У�");

	}

	@Test
	public void testGetName() {
		System.out.println("���Է���1��ʼִ�У�");
		Assert.assertEquals("hello", helloService.getName());

	}
	@Test
	@Ignore("not ready yet") 
	public void testGetName2() {
		System.out.println("���Է���2��ʼִ�У�");
		Assert.assertEquals("hello", helloService.getName());

	}
	@After
	public void after() {
		System.out.println("��ÿ�����Է�����ִ�У�");

	}
}