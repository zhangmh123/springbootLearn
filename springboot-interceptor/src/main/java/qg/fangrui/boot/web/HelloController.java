package qg.fangrui.boot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String index(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";  
    }
    @RequestMapping("/hello")
    public String error() {
        // 加入一个属性，用来在模板中读取
        //map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "/errorpage/error";  
    }
    @RequestMapping("/errorpage/error")
    public String errorpage() {
        return "/errorpage/error";  
    }
}