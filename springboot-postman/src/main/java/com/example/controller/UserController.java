package com.example.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.Application;
import com.example.entity.User;
import com.example.repository.UserRepository;
import com.google.common.base.Strings;
  
/** 
 * Created by steadyjack on 2017/3/22. 
 */  
@SpringBootTest(classes = Application.class)  
@RestController
@RequestMapping("/user")  
public class UserController {  
  
    @Autowired  
    private UserRepository userRepository;  
  
    /** 
     * �û��б� 
     * @return 
     */  
    @RequestMapping("/list")  
    public List<User> listUser() {  
        List<User> userList=null;  
        try {  
            userList=userRepository.getUserList();  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ:  "+e.getMessage());  
        }  
        return userList;  
    }  
  
    /** 
     * ����id��ѯUserʵ�� 
     * @param id 
     * @return 
     */  
    @RequestMapping("/{id}")  
    public User getUserById(@PathVariable Integer id){  
        User user=null;  
        try {  
            user=userRepository.getUserById(id);  
        }catch (Exception e){  
            user=new User(1,"admin","admin@sina.com");  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return user;  
    }  
  
    /** 
     * ����userʵ�� 
     * @param user 
     * @return 
     */  
    @RequestMapping(value = "/save",method = RequestMethod.POST)  
    public int insertUser(User user){  
        int res=1;  
        try {  
            res=userRepository.saveUser(user);  
            System.out.println(user);
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
    /** 
     * ����Userʵ��-PreparedStatementSetter 
     * @param user 
     * @return 
     */  
    @RequestMapping(value = "/saveWithSafe",method = RequestMethod.POST)  
    public int insertUserWithSafe(User user){  
        int res=1;  
        try {  
            res=userRepository.saveUserWithSafe(user);  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
    /** 
     * ����userʵ��-PreparedStatementCreator��KeyHolder-����ʵ��󷵻�ʵ������� 
     * @param user 
     * @return 
     */  
    @RequestMapping(value = "/saveWithKey",method = RequestMethod.POST)  
    public int insertUserWithKey(User user){  
        int res=1;  
        try {  
            res=userRepository.saveUserWithKey(user);  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
    /** 
     * ����id����userʵ�� 
     * @param id 
     * @param request 
     * @return 
     */  
    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)  
    public int updateUserWithId(@PathVariable Integer id,HttpServletRequest request){  
        int res=1;  
        try {  
            if (id!=null && !id.equals(0)){  
                String name=request.getParameter("name");  
                String email=request.getParameter("email");  
                User updateUser=new User(id, Strings.isNullOrEmpty(name)?null:name,Strings.isNullOrEmpty(email)?null:email);  
                res=userRepository.updateUser(updateUser);  
            }  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
    /** 
     * ����idɾ��userʵ�� 
     * @param id 
     * @return 
     */  
    @RequestMapping("/delete/{id}")  
    public int deleteUserById(@PathVariable Integer id){  
        int res=1;  
        try {  
            User deleteUser=userRepository.getUserById(id);  
            res=userRepository.deleteUser(deleteUser);  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
    /** 
     * ����name��ѯ�Ƿ����ĳ��userʵ�� 
     * @param request 
     * @return 
     */  
    @RequestMapping("/isExistUser")  
    public Boolean isExistUser(HttpServletRequest request){  
        Boolean res=false;  
        try {  
            String name=request.getParameter("name");  
            User queryUser=new User();
            queryUser.setName(Strings.isNullOrEmpty(name)?null:name);
            User deleteUser=userRepository.getUserByUserName(queryUser);  
            if (deleteUser!=null){  
                res=true;  
            }  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
    /** 
     * ��ѯuserʵ������� 
     * @return 
     */  
    @RequestMapping("/total")  
    public Integer getTotal(){  
        Integer res=0;  
        try {  
            res=userRepository.getCount();  
        }catch (Exception e){  
            System.out.println("�쳣��Ϣ�� "+e.getMessage());  
        }  
        return res;  
    }  
  
}  