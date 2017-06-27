package com.springframework.security.service.impl;

import java.util.List;  

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Service;  

import com.springframework.security.dao.AnimalDAO;
import com.springframework.security.entity.AnimalEntity;
import com.springframework.security.service.AnimalService;
  
  
@Service  
public class AnimalServiceImpl implements AnimalService {  
      
    @Autowired  
    private AnimalDAO dao;  
  
    public List<AnimalEntity> getAllAnimals() {  
        return dao.getAll();  
    }  
  
    public int insertOne(AnimalEntity entity) {  
        return dao.insertOne(entity);  
    }  
  
}  