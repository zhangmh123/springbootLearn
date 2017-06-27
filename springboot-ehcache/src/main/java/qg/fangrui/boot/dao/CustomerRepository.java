package qg.fangrui.boot.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import qg.fangrui.boot.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}