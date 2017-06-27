package com.springboot.rabbitmq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/** * 消息 * */  
public class RabbitMessage implements Serializable {  
    private static final long serialVersionUID = -6487839157908352120L;  
    private Class<?>[] paramTypes;// 参数类型  
    private String exchange;// 交换器  
    private Object[] params;  
    private String routeKey;// 路由key  
  
    public RabbitMessage() {  
    }  
  
    public RabbitMessage(String exchange, String routeKey, Object... params) {  
        this.params = params;  
        this.exchange = exchange;  
        this.routeKey = routeKey;  
    }  
  
    @SuppressWarnings("rawtypes")  
    public RabbitMessage(String exchange, String routeKey, String methodName,  
            Object... params) {  
        this.params = params;  
        this.exchange = exchange;  
        this.routeKey = routeKey;  
        int len = params.length;  
        Class[] clazzArray = new Class[len];  
        for (int i = 0; i < len; i++)  
            clazzArray[i] = params[i].getClass();  
        this.paramTypes = clazzArray;  
    }  
  
    public byte[] getSerialBytes() {  
        byte[] res = new byte[0];  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        ObjectOutputStream oos;  
        try {  
            oos = new ObjectOutputStream(baos);  
            oos.writeObject(this);  
            oos.close();  
            res = baos.toByteArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return res;  
    }  
  
    public String getRouteKey() {  
        return routeKey;  
    }  
  
    public String getExchange() {  
        return exchange;  
    }  
  
    public void setExchange(String exchange) {  
        this.exchange = exchange;  
    }  
  
    public void setRouteKey(String routeKey) {  
        this.routeKey = routeKey;  
    }  
  
    public Class<?>[] getParamTypes() {  
        return paramTypes;  
    }  
  
    public Object[] getParams() {  
        return params;  
    }  
}  
