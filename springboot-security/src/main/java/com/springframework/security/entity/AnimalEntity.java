package com.springframework.security.entity;

public class AnimalEntity {  
    
    //数据库自动生成的id  
    private Long id;  
      
    //动物名称  
    private String name;  
      
    //动物数量  
    private Integer count;  
      
    //备注  
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
