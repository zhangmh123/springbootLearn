package com.springboot.mongo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.springboot.mongo.common.PageModel;
import com.springboot.mongo.entity.Orders;

public interface MongoBase<T> {  
    //���  
    public void insert(T object,String collectionName);    
    //������������  
    public T findOne(Map<String,Object> params,String collectionName);    
    //��������  
    public List<T> findAll(Map<String,Object> params,String collectionName);    
    //�޸�  
    public void update(Map<String,Object> params,String collectionName);   
    //��������  
    public void createCollection(String collectionName);  
    //��������ɾ��  
    public void remove(Map<String,Object> params,String collectionName);  
    //��������ɾ��  
    public void remove(String field,String value,String collectionName);  
    
  //����������ѯ  
    public T findOne(Query query,String collectionName); 
  //�������  
    public void insertAll(List<T> object);   
    
    public List<T> find(Query query,String collectionName);  
    
    public List<T> find(BasicQuery query, String collectionName) ;
    
    public void update(Query query,Update update,String collectionName);   
      
    public WriteResult updateFirst(Query query,Update update,String collectionName); 
    
    public PageModel<Orders>getOrders(PageModel<Orders> page, DBObject queryObject,String collectionName);
}  
