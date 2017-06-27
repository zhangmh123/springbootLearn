package com.springframework.security.service;

import java.util.List;  

import com.springframework.security.entity.AnimalEntity;

  
public interface AnimalService {  
  
    public List<AnimalEntity> getAllAnimals();  
      
    public int insertOne(AnimalEntity entity);  
}