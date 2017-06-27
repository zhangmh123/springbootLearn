package com.springboot.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
import com.springboot.mongo.common.PageModel;
import com.springboot.mongo.dao.OrdersDao;
import com.springboot.mongo.dao.UserDao;
import com.springboot.mongo.entity.Item;
import com.springboot.mongo.entity.Orders;
import com.springboot.mongo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {
	private static final Logger logger = LoggerFactory
			.getLogger(MongodbTest.class);
	@Autowired
	private UserDao userDaoImpl;

	@Autowired
	private OrdersDao ordersDao;
	
	 @Resource  
	 private MongoTemplate mongoTemplate;  

	// @Test
	public void testAdd() {

		// 添加一百个user
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setId("" + i);
			user.setAge(i);
			user.setName("zcy" + i);
			user.setPassword("zcy" + i);
			userDaoImpl.insert(user, "users");
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("maxAge", 50);
		List<User> list = userDaoImpl.findAll(params, "users");
		logger.debug("user.count()==" + list.size());
	}

	// @Test
	public void testUdate() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "1");
		User user = userDaoImpl.findOne(params, "users");
		System.out.println("user.name===" + user.getName());
		System.out.println("=============update==================");
		params.put("name", "hello");
		userDaoImpl.update(params, "users");
		user = userDaoImpl.findOne(params, "users");
		System.out.println("user.name===" + user.getName());
	}

	// @Test
	public void testRemove() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", "2");
		userDaoImpl.remove(params, "users");
		User user = userDaoImpl.findOne(params, "users");
		logger.debug("user==" + user);
	}

	// 测试Save方法添加
	// @Test
	public void testSave() throws ParseException {
		SimpleDateFormat form = new SimpleDateFormat("yyyy-mm-dd");
		// 订单
		Orders order = new Orders();
		order.setOnumber("001");
		order.setDate(form.parse("2015-07-25"));
		order.setCname("zcy");
		// 订单详情
		List<Item> items = new ArrayList<Item>();
		Item item1 = new Item();
		item1.setPnumber("p001");
		item1.setPrice(4.0);
		item1.setQuantity(5);
		items.add(item1);
		Item item2 = new Item();
		item2.setPnumber("p002");
		item2.setPrice(8.0);
		item2.setQuantity(6);
		items.add(item2);
		order.setItems(items);
		ordersDao.insert(order, "orders");
	}

	// @Test
	public void testInsertAll() throws ParseException {
		List<Orders> orders = new ArrayList<Orders>();
		for (int i = 15; i <= 100; i++) {
			SimpleDateFormat form = new SimpleDateFormat("yyyy-mm-dd");
			// 订单
			Orders order = new Orders();
			order.setOnumber("00" + i);
			order.setDate(form.parse("2017-06-09"));
			order.setCname("zcy");
			// 订单详情
			List<Item> items = new ArrayList<Item>();
			Item item1 = new Item();
			item1.setPnumber("p00" + i);
			item1.setPrice(4.0 + i);
			item1.setQuantity(5 + i);
			items.add(item1);
			Item item2 = new Item();
			item2.setPnumber("p00" + (i + 1));
			item2.setPrice(8.0 + i);
			item2.setQuantity(6 + i);
			items.add(item2);
			order.setItems(items);
			orders.add(order);
		}
		ordersDao.insertAll(orders);
	}

	// @Test
	public void testFindOne()  {

		Query query = new Query(Criteria.where("onumber").is("002"));
		Orders order = ordersDao.findOne(query, "orders");
		System.out.println(JSONObject.fromObject(order));

	}

	//@Test
	public void testFindbyQuery()  {
		//Query query = new Query(Criteria.where("onumber").is("002").and("cname").is("zcy2"));
		Query query = new Query(Criteria.where("onumber").is("001"));
		List<Orders> orders = ordersDao.find(query, "orders");
		logger.debug("json:"+JSONArray.fromObject(orders));
	}
	//@Test
    public void testFindbyBasicQuery() 
    {  
        
       BasicDBList basicDBList=new BasicDBList();  
        
       basicDBList.add(new BasicDBObject("onumber","002"));  
       basicDBList.add(new BasicDBObject("cname","zcy1"));  
       DBObject obj = new BasicDBObject();  
       obj.put("$or", basicDBList);  
       Query query=new BasicQuery(obj);  
       List<Orders>orders=ordersDao.find(query,"orders");  
       logger.debug("json:"+JSONArray.fromObject(orders));  
    }  
	
	//@Test
    public void testFindbyQueryBuilder()  
    {  
        
		 QueryBuilder queryBuilder= new QueryBuilder(); 

         queryBuilder.or(new BasicDBObject("onumber","002"),new BasicDBObject("cname","zcy1")); 

        Query query=new BasicQuery(queryBuilder.get()); 
       List<Orders>orders=ordersDao.find(query,"orders");  
       logger.debug("json:"+JSONArray.fromObject(orders));  
    } 
	
	//@Test
    public void testFindbyQueryBuilderAndFields()  
    {  
        
		 QueryBuilder queryBuilder= new QueryBuilder(); 

         queryBuilder.or(new BasicDBObject("onumber","002"),new BasicDBObject("cname","zcy1")); 
         BasicDBObject fieldsObject=new BasicDBObject();  
         fieldsObject.put("onumber", 1);  
         fieldsObject.put("cname", 1);  
        Query query=new BasicQuery(queryBuilder.get(),fieldsObject); 
       List<Orders>orders=ordersDao.find(query,"orders");  
       logger.debug("json:"+JSONArray.fromObject(orders));  
    } 
	
	//@Test
    public void testUpsert()  
    {  
	   Query query = new Query(Criteria.where("onumber").is("002"));
	   Update update = new Update();
	   update.set("cname", "zcy");
       ordersDao.update(query, update, "orders");  
    } 
	//@Test
    public void testUpdateFirst ()  
    {  
	   Query query = new Query(Criteria.where("cname").is("zcy"));
	   Update update = new Update();
	   update.set("date", "2017-06-09");
	   WriteResult result = ordersDao.updateFirst(query, update, "orders");  
	   logger.debug("result:"+result);
    } 
	//@Test
    public void testBasicUpdate ()  
    {  
		BasicDBObject basicDBObject=new BasicDBObject();  
		basicDBObject.put("$set", new BasicDBObject("date","2015-08-09"));  
		Update update=new BasicUpdate(basicDBObject);  
		WriteResult result =  mongoTemplate.updateFirst(new Query(Criteria.where("cname").is("zcy")), update, "orders"); 
	    logger.debug("result:"+result);
    } 
    //@Test
    public void testUpdateMuti()  
    {  
		BasicDBObject basicDBObject=new BasicDBObject();  
		basicDBObject.put("$set", new BasicDBObject("cname","zcy"));  
		Update update=new BasicUpdate(basicDBObject);  
		WriteResult result =  mongoTemplate.updateMulti(new Query(Criteria.where("cname").ne("zcy")), update, "orders"); 
	    logger.debug("result:"+result);
    } 
    //@Test   
   	public void testRemoveOrders() 
   	{  
   	 ordersDao.remove("onumber","002", "orders");  
   	}  
    //@Test  
    public void testList() throws ParseException  
    {  
      PageModel<Orders> page=new PageModel<Orders>();  
      page.setPageNo(2);  
      page=ordersDao.getOrders(page, new BasicDBObject("cname","zcy"),"orders");  
      System.out.println("总数："+page.getRowCount());  
      System.out.println("返回条数："+page.getDatas().size());  
      System.out.println(JSONArray.fromObject(page.getDatas()));  
    }  
    //@Test  
    public void getAggregation() {  
        Set<String> onumberSet=new HashSet<String>();  
        onumberSet.add("001");  
        onumberSet.add("002");  
        onumberSet.add("003");  
        //过滤条件  
        DBObject queryObject=new BasicDBObject("onumber", new BasicDBObject("$in",onumberSet));  
        DBObject queryMatch=new BasicDBObject("$match",queryObject);  
        //展开数组  
        DBObject queryUnwind=new BasicDBObject("$unwind","$items");  
        //分组统计  
        DBObject groupObject=new BasicDBObject("_id",new BasicDBObject("ino","$items.ino"));  
        groupObject.put("total", new BasicDBObject("$sum","$items.quantity"));  
        DBObject  queryGroup=new BasicDBObject("$group",groupObject);  
        //过滤条件  
        DBObject finalizeMatch=new BasicDBObject("$match",new BasicDBObject("total",new BasicDBObject("$gt",1)));  
      
        AggregationOutput  output=mongoTemplate.getCollection("orders").aggregate(queryMatch,queryUnwind,queryGroup,finalizeMatch);  
        for (Iterator<DBObject> iterator = output.results().iterator(); iterator.hasNext();) {  
            DBObject obj =iterator.next();  
            System.out.println(obj.toString());  
        }  
       } 
    @Test  
    public void getAggregation2() {  
        Set<String> onumberSet=new HashSet<String>();  
        onumberSet.add("001");  
        onumberSet.add("002");  
        onumberSet.add("003");  
        Aggregation agg = Aggregation.newAggregation(  
                Aggregation.match(Criteria.where("onumber").in(onumberSet)),  
                Aggregation.unwind("items"),  
                Aggregation.group("items.ino").sum("items.quantity").as("total"),  
                Aggregation.match(Criteria.where("total").gt(1).and("_id").ne(null))  
        );  
          
        AggregationResults<BasicDBObject> outputType=mongoTemplate.aggregate(agg,"orders", BasicDBObject.class);  
        for (Iterator<BasicDBObject> iterator = outputType.iterator(); iterator.hasNext();) {  
            DBObject obj =iterator.next();  
            System.out.println(obj.toString());  
        }  
   
    }
}