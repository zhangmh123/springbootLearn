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
      
    @NotBlank(message="������: ����Ϊ��")    
    private String oname;  
      
    @Pattern(regexp="[1-9]{1,3}", message="����X: ����Ϊ������������0<X<1000") 
    private String  ocount;  
      
    @Memo(message = "��ע����Ϊ�գ���ֻ����д\"Ȧ��\"������\"ɢ��\"")  
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