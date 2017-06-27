package com.springboot.mongo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mongodb.morphia.Morphia;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.springboot.mongo.common.PageModel;
import com.springboot.mongo.dao.OrdersDao;
import com.springboot.mongo.entity.Orders;

/** 
 * 订单实现 
 * @author zhengcy 
 * 
 */  
@Repository("ordersDao")  
public class OrdersDaoImpl implements OrdersDao {  
    @Resource  
    private MongoTemplate mongoTemplate;  
    private Morphia  morphia;  
    
    public OrdersDaoImpl(){  
       morphia= new Morphia();  
       morphia.map(Orders.class);  
    }  
  
    public void insert(Orders object, String collectionName) {  
        mongoTemplate.insert(object, collectionName);  
          
    }  
      
      
    public void save(Orders object, String collectionName) {  
        mongoTemplate.save(object, collectionName);  
          
    }  
      
    public void insertAll(List<Orders> objects) {  
        mongoTemplate.insertAll(objects);  
          
    }


	public Orders findOne(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Orders> findAll(Map<String, Object> params,
			String collectionName) {
		// TODO Auto-generated method stub
		return null;
	}


	public void update(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		
	}


	public void createCollection(String collectionName) {
		// TODO Auto-generated method stub
		
	}


	public void remove(Map<String, Object> params, String collectionName) {
		// TODO Auto-generated method stub
		
	}


	public Orders findOne(Query query, String collectionName) {
		return mongoTemplate.findOne(query, Orders.class, collectionName);  
	}


	public List<Orders> find(Query query, String collectionName) {
		return mongoTemplate.find(query, Orders.class, collectionName);  
	}


	public List<Orders> find(BasicQuery query, String collectionName) {
		// TODO Auto-generated method stub
		return mongoTemplate.find(query, Orders.class, collectionName);  
	}


	public void update(Query query, Update update, String collectionName) {
		mongoTemplate.upsert(query,update, collectionName); 
	}


	public WriteResult updateFirst(Query query, Update update,String collectionName) {
		return mongoTemplate.updateFirst(query,update, collectionName); 
	}


/*	public PageModel<Orders> getOrders(PageModel<Orders> page,DBObject queryObject, String collectionName) {
		Query query=new BasicQuery(queryObject);  
		   //查询总数  
		   int count=(int) mongoTemplate.count(query,Orders.class);  
		   page.setRowCount(count);  
		    
		   //排序  
		     query.with(new Sort(Direction.ASC, "onumber"));  
		     query.skip(page.getSkip()).limit(page.getPageSize());  
		   List<Orders>datas=mongoTemplate.find(query,Orders.class);  
		   page.setDatas(datas);  
		   return page;  
	}*/


	public void remove(String field, String value, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where(field).is(value)),Orders.class,collectionName);  
	}

	public PageModel<Orders> getOrders(PageModel<Orders> page,DBObject queryObject, String collectionName) {
		 DBObject filterDBObject=new BasicDBObject();  
	      filterDBObject.put("_id", 0);  
	      filterDBObject.put("cname",1);  
	      filterDBObject.put("onumber",1);  
	         
	      DBCursor dbCursor=mongoTemplate.getCollection(collectionName).find(queryObject,filterDBObject);  
	       
	      //排序  
	      DBObject sortDBObject=new BasicDBObject();  
	      sortDBObject.put("onumber",1);  
	      dbCursor.sort(sortDBObject);  
	      //分页查询  
	      dbCursor.skip(page.getSkip()).limit(page.getPageSize());  
	       
	      //总数  
	      int count=dbCursor.count();  
	      //循环指针  
	      List<Orders> datas=new ArrayList<Orders>();  
	      while (dbCursor.hasNext()) {  
	        datas.add(morphia.fromDBObject(Orders.class, dbCursor.next()));  
	      }  
	       
	      page.setRowCount(count);  
	      page.setDatas(datas);  
	      return page;  
	}  
}  