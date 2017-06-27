package com.springboot.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.bean.DemoInfo;
import com.springboot.service.DemoInfoService;
import com.springboot.util.RedisUtil;

/**
 * 
 * ≤‚ ‘¿‡.
 * 
 * @author Angel(QQ:412887952)
 * 
 * @version v.0.1
 */

@Controller
public class DemoInfoController {

	@Autowired
	DemoInfoService demoInfoService;
	
    @Resource
    private RedisUtil redisUtil;

	@RequestMapping("/test")
	public @ResponseBody
	String test() {

		DemoInfo loaded = demoInfoService.findById(11);

		System.out.println("loaded=" + loaded);

		DemoInfo cached = demoInfoService.findById(11);

		System.out.println("cached=" + cached);

		loaded = demoInfoService.findById(12);

		System.out.println("loaded2=" + loaded);

		return "ok";

	}

	@RequestMapping("/delete/{id}")
	public @ResponseBody
	String delete(@PathVariable long id) {
		System.out.println(id);
		demoInfoService.deleteFromCache(id);

		return "ok";

	}

	@RequestMapping("/test1")
	public @ResponseBody
	String test1() {

		demoInfoService.test();

		System.out.println("DemoInfoController.test1()");

		return "ok";

	}
	
	@RequestMapping("/save")
	public @ResponseBody String save() {
		DemoInfo demo = new DemoInfo();
		demo.setName("aaaaaa");
		demo.setPwd("aaaaaa");
		demoInfoService.save(demo);
		return "ok";
	}

	@RequestMapping("/update")
	public @ResponseBody String update() {
		DemoInfo demo = demoInfoService.findById(11);;
		demo.setName("cccccc");
		//demo.setPwd("bbbcc");
		demoInfoService.update(demo);
		return "ok";
	}
}