package com.qf.rabbitmq.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qf.rabbitmq.entity.Company;
import com.qf.rabbitmq.entity.Employee;
import com.qf.rabbitmq.service.SendMessageService;

@RestController  
public class SendMessageController  
{  
    @Autowired  
    private SendMessageService sendMessageService;  
   
    @RequestMapping(value = "/caculate/{number}", method = RequestMethod.GET)  
    @ResponseBody  
    public String sendMessage(@PathVariable String number)  
    {  
    	String result =  
                sendMessageService.sendMessage(number);  
         return "Factorial(" + number + ") = " + result;  
    }  
    
    @RequestMapping(value = "/sendMessage/{message}", method = RequestMethod.GET) 
    @ResponseBody  
    public String sendAsyncMessage(@PathVariable String message)  
    {  
       sendMessageService.sendAsyncMessage(message);  
       
       return message;
    }  
    
    @RequestMapping(value = "/sendAsyncMessage", method = RequestMethod.POST)  
    public void sendAsyncMessage2(@RequestBody String message)  
    {  
       sendMessageService.sendAsyncMessage(message);  
    } 
    
    @RequestMapping(value = "/sendMessages", method = RequestMethod.GET)  
    @ResponseBody 
    public void sendMessages()  
    {  
       List<String> messageList = new ArrayList<String>();  
      
       for(int i = 0;i<30;i++){
    	   String msg = "Message";
    	   messageList.add(msg+"-"+i);
       }       
       sendMessageService.sendBatchMessage(messageList);  
    }   
    
    @RequestMapping(value = "/sendCompanyMessage", method = RequestMethod.GET)  
    @ResponseBody 
    public void sendCompanyMessage()  
    {  
       Company company = new Company();  
       company.setCompanyName("百度");
       company.setEstablishDate("2017-06-19");
       company.setId(1);
       sendMessageService.sendObjectMessage(company);  
    }  
       
    @RequestMapping(value = "/sendEmployeeMessage", method = RequestMethod.GET)  
    public void sendEmployeeMessage()  
    {  
        Employee employee = new Employee();  
        employee.setEmployeeDate("2017-06-19");
        employee.setEmployeeId("20160601");
        employee.setEmployeeName("张三");
        sendMessageService.sendObjectMessage(employee);  
    }  
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)  
    @ResponseBody  
    public String hello()  
    {  
         return "hello world!";  
    }  
    
    @RequestMapping(value = "/sendNumber", method = RequestMethod.POST)  
    @ResponseBody  
    public String send(@RequestBody String number)  
    {  
         return "I have received you number " + number;  
    }  
}  