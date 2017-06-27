package com.springboot.mongo.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.springboot.mongo.common.PageModel;
import com.springboot.mongo.dao.UserDao;
import com.springboot.mongo.entity.Orders;
import com.springboot.mongo.entity.User;

@Repository("userDaoImpl")  
public class UserDaoImpl implements UserDao {  
      
    @Resource  
    private MongoTemplate mongoTemplate;  
  
    public void insert(User object,String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
    }  
  
    public User findOne(Map<String,Object> params,String collectionName) {  
         return mongoTemplate.findOne(new Query(Criteria.where("id").is(params.get("id"))), User.class,collectionName);    
    }  
  
    public List<User> findAll(Map<String,Object> params,String collectionName) {  
        List<User> result = mongoTemplate.find(new Query(Criteria.where("age").lt(params.get("maxAge"))), User.class,collectionName);  
        return result;  
    }  
  
    public void update(Map<String,Object> params,String collectionName) {  
        mongoTemplate.upsert(new Query(Criteria.where("id").is(params.get("id"))), new Update().set("name", params.get("name")), User.class,collectionName);  
    }  
  
    public void createCollection(String name) {  
        mongoTemplate.createCollection(name);  
    }  
  
  
    public void remove(Map<String, Object> params,String collectionName) {  
        mongoTemplate.remove(new Query(Criteria.where("id").is(params.get("id"))),User.class,collectionName);  
    }

	public User findOne(Query query, String collectionName) {
		return mongoTemplate.findOne(query, User.class, collectionName);  
	}

	public void insertAll(List<User> object) {
		mongoTemplate.insertAll(object);  
	}

	public List<User> find(Query query, String collectionName) {
		return mongoTemplate.find(query, User.class, collectionName);  
	}

	public List<User> find(BasicQuery query, String collectionName) {
		return mongoTemplate.find(query, User.class, collectionName);  
	}

	public void update(Query query, Update update, String collectionName) {
		mongoTemplate.upsert(query,update, collectionName); 
	}

	public WriteResult updateFirst(Query query, Update update,String collectionName) {
		return mongoTemplate.updateFirst(query,update, collectionName); 
	}

	public PageModel<Orders> getOrders(PageModel<Orders> page,
			DBObject queryObject, String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String field, String value, String collectionName) {
		// TODO Auto-generated method stub
		
	}

}
