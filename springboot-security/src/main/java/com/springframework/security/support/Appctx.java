package com.springframework.security.support;

import org.springframework.context.ApplicationContext;  

public class Appctx {  
    public static ApplicationContext ctx=null;   
    public static Object getObject(String string){  
        return ctx.getBean(string);  
    }  
}  