package com.neusoft.spring.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neusoft.spring.model.User;
import com.neusoft.spring.service.UserService;
import com.neusoft.spring.util.CommonUtil;


/**
 * 
 * @ClassName UserController
 * @author abel
 * @date 2016年11月10日
 */
@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过spring data jpa 调用方法 api :localhost:8099/users/byname?username=xxx
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/byname", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> getUser(HttpServletRequest request) {
        Map<String, Object> map = CommonUtil.getParameterMap(request);
        String username = (String) map.get("username");
        return new ResponseEntity<User>(userService.getUserByName(username), HttpStatus.OK);
    }
}
