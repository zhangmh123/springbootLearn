package com.springframework.security.entity;

import javax.validation.constraints.NotNull;  
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;  
  
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;  
import org.hibernate.validator.constraints.Range;  

import com.springframework.security.constraint.Memo;
public class AnimalForm {  
  
    private long id;  
      
    @NotBlank(message="动物名: 不能为空")    
    private String oname;  
      
    @Pattern(regexp="[1-9]{1,3}", message="数量X: 必须为正整数，并且0<X<1000") 
    private String  ocount;  
      
    @Memo(message = "备注不能为空，且只能填写\"圈养\"，或者\"散养\"")  
    private String memo;  
  
    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	public String  getOcount() {
		return ocount;
	}

	public void setOcount(String  ocount) {
		this.ocount = ocount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}    
      
  
      
}  