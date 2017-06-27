package qg.fangrui.boot.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import qg.fangrui.boot.aop.Token;

/**
 * Created by zggdczfr on 2017/1/26.
 */
@Controller
public class URLController {

    @Token(save = true)
    @RequestMapping("/savetoken")
    @ResponseBody
    public String getToken(HttpServletRequest request, HttpServletResponse response){
        return (String) request.getSession().getAttribute("token");
    }

    @Token(remove = true)
    @RequestMapping("/removetoken")
    @ResponseBody
    public String removeToken(HttpServletRequest request, HttpServletResponse response){
        return "success";
    }
}