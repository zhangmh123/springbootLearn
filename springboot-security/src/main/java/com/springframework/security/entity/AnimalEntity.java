package com.springframework.security.entity;

public class AnimalEntity {  
    
    //���ݿ��Զ����ɵ�id  
    private Long id;  
      
    //��������  
    private String name;  
      
    //��������  
    private Integer count;  
      
    //��ע  
    private String memo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}  
  
        //getters and setters  
    
    
}  
