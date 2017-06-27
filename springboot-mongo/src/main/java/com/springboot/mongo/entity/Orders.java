package com.springboot.mongo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Id;

/** 
 * ���� 
 * @author zhengcy 
 * 
 */  
public class Orders implements Serializable {  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
    @Id
    private String id;  
    //������  
    private String onumber;  
    //����  
    private Date date;  
    //�ͻ�����  
    private String cname;  
    //����  
    private List<Item> items;  
      
    public String getId() {  
        return id;  
    }  
    public void setId(String id) {  
        this.id = id;  
    }  
    public Date getDate() {  
        return date;  
    }  
    public void setDate(Date date) {  
        this.date = date;  
    }  
    public String getCname() {  
        return cname;  
    }  
    public void setCname(String cname) {  
        this.cname = cname;  
    }  
    public String getOnumber() {  
        return onumber;  
    }  
    public void setOnumber(String onumber) {  
        this.onumber = onumber;  
    }  
    public List<Item> getItems() {  
        return items;  
    }  
    public void setItems(List<Item> items) {  
        this.items = items;  
    }  
  
}  