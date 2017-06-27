package com.qf.rabbitmq.entity;

public class Company implements java.io.Serializable  
{  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;  
    private String companyName;  
    private String establishDate;  
   
   
    public int getId() {  
        return id;  
    }  
   
    public void setId(int id) {  
        this.id = id;  
    }  
   
    public String getCompanyName() {  
        return companyName;  
    }  
   
    public void setCompanyName(String companyName) {  
        this.companyName = companyName;  
    }  
   
    public String getEstablishDate() {  
        return establishDate;  
    }  
   
    public void setEstablishDate(String establishDate) {  
        this.establishDate = establishDate;  
    }  
   
    public String toString()  
    {  
        return "companyId=" + Integer.toString(getId()) +   
        ",companyName=" + getCompanyName() + ",establishDate=" + getEstablishDate();  
    }  
}  