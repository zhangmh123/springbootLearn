package com.springframework.security.dao;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.jdbc.core.BeanPropertyRowMapper;  
import org.springframework.jdbc.core.JdbcTemplate;  
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;  

import com.springframework.security.entity.AnimalEntity;
  
  
@Repository  
public class AnimalDAO {  
  
    @Autowired  
    private JdbcTemplate jdbcTemplate;  
      
    //��animals���������������  
    public List<AnimalEntity> getAll() {  
        String sql = "SELECT id, name, count, memo FROM animals";  
        List<AnimalEntity> list = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<AnimalEntity>(AnimalEntity.class));  
        return list;  
    }  
      
    //����һ�����ݵ�animals��  
    public int insertOne(AnimalEntity entity){  
        int cnt = this.jdbcTemplate.update("INSERT INTO animals(name, count, memo) VALUES(?, ?, ?)",   
                entity.getName(), entity.getCount(), entity.getMemo());  
        return cnt;  
    }  
}  