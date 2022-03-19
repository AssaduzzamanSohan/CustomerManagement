package com.example.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByCustomerKey(Long customerKey);

	List<Customer> findByEmail(String email);

	List<Customer> findByName(String name);

	List<Customer> findByPhone(String phone);

	List<Customer> findByLoginName(String loginName);

	Customer findByLoginNameAndPassword(String loginName, String password);

}
