package com.springboot.mongo.entity;

/** 
 * ��Ʒ������ 
 * @author zhengcy 
 * 
 */  
public class Item {  
      
    //����  
    private Integer quantity;  
    //����  
    private Double price;  
    //��Ʒ����  
    private String pnumber;  
    public Integer getQuantity() {  
        return quantity;  
    }  
    public void setQuantity(Integer quantity) {  
        this.quantity = quantity;  
    }  
    public Double getPrice() {  
        return price;  
    }  
    public void setPrice(Double price) {  
        this.price = price;  
    }  
    public String getPnumber() {  
        return pnumber;  
    }  
    public void setPnumber(String pnumber) {  
        this.pnumber = pnumber;  
    }  
}  
