package qg.fangrui.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import qg.fangrui.boot.dao.CustomerRepository;
import qg.fangrui.boot.model.Customer;
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {
	private static final Logger logger = LoggerFactory.getLogger(MongodbTest.class);
	@Autowired
	CustomerRepository customerRepository;

	@Test
	public void mongodbIdTest() {
		Customer customer=new Customer("lxdxil","dd");
        customer=customerRepository.save(customer);
        logger.info( "mongodbId:"+customer.getId());
	}
	
	@Test
	public void mongodbFindTest() {
		Customer customer=customerRepository.findByFirstName("lxdxil");
        logger.info( "mongodbId:"+customer.getId()+"lastName:"+customer.getLastName());
	}
	
}
