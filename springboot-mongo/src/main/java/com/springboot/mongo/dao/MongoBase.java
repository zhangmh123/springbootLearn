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
    //添加  
    public void insert(T object,String collectionName);    
    //根据条件查找  
    public T findOne(Map<String,Object> params,String collectionName);    
    //查找所有  
    public List<T> findAll(Map<String,Object> params,String collectionName);    
    //修改  
    public void update(Map<String,Object> params,String collectionName);   
    //创建集合  
    public void createCollection(String collectionName);  
    //根据条件删除  
    public void remove(Map<String,Object> params,String collectionName);  
    //根据条件删除  
    public void remove(String field,String value,String collectionName);  
    
  //根据条件查询  
    public T findOne(Query query,String collectionName); 
  //批量添加  
    public void insertAll(List<T> object);   
    
    public List<T> find(Query query,String collectionName);  
    
    public List<T> find(BasicQuery query, String collectionName) ;
    
    public void update(Query query,Update update,String collectionName);   
      
    public WriteResult updateFirst(Query query,Update update,String collectionName); 
    
    public PageModel<Orders>getOrders(PageModel<Orders> page, DBObject queryObject,String collectionName);
}  
