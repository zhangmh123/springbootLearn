package com.qf.rabbitmq.entity;

public class Employee implements java.io.Serializable  
{  
        /**
	 * 
	 */
	private static final long serialVersionUID = -6625666652070863444L;
		private String employeeId;  
        private String employeeName;  
        private String employeeDate;  
   
    public String getEmployeeId() {  
        return employeeId;  
    }  
   
    public void setEmployeeId(String employeeId) {  
        this.employeeId = employeeId;  
    }  
   
    public String getEmployeeName() {  
        return employeeName;  
    }  
   
    public void setEmployeeName(String employeeName) {  
        this.employeeName = employeeName;  
    }  
   
    public String getEmployeeDate() {  
        return employeeDate;  
    }  
   
    public void setEmployeeDate(String employeeDate) {  
        this.employeeDate = employeeDate;  
    }  
   
    public String toString()  
    {  
        return "EmployeeId=" + getEmployeeId() + ",EmployeeName=" + getEmployeeName() + ",EmployeeDate=" + getEmployeeDate();  
    }  
}  
