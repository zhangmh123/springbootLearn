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

////SpringJUnit支持，由此引入Spring-Test框架支持！

@RunWith(SpringJUnit4ClassRunner.class)
// //指定我们SpringBoot工程的启动类
@SpringBootTest
// /由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class HelloServiceTest {

	@Resource
	private HelloService helloService;
	
	@Before
	public void before() {
		System.out.println("在每个测试方法前执行！");

	}

	@Test
	public void testGetName() {
		System.out.println("测试方法1开始执行！");
		Assert.assertEquals("hello", helloService.getName());

	}
	@Test
	@Ignore("not ready yet") 
	public void testGetName2() {
		System.out.println("测试方法2开始执行！");
		Assert.assertEquals("hello", helloService.getName());

	}
	@After
	public void after() {
		System.out.println("在每个测试方法后执行！");

	}
}