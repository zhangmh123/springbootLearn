package com.neusoft.spring.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.spring.model.Demo;
import com.neusoft.spring.service.DemoService;


@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    /**

     * 测试保存数据方法.

     * @return

     */

    @RequestMapping("/save")
    public String save(){
        Demo d = new Demo();
        d.setName("Baby");
        demoService.save(d);//保存数据.
        return "ok.DemoController.save";

    }
    
    @RequestMapping("/getById")

    public Demo getById(long id){

       return demoService.getById(id);

    }
}
