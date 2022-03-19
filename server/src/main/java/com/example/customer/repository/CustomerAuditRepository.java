package com.example.customer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.CustomerAudit;

public interface CustomerAuditRepository extends JpaRepository<CustomerAudit, Long> {

	List<CustomerAudit> findByCustomerKey(Long customerKey);

	List<CustomerAudit> findByEmail(String email);

	List<CustomerAudit> findByName(String name);

	List<CustomerAudit> findByPhone(String phone);

	List<CustomerAudit> findByLoginNameAndPassword(String loginName, String password);

	List<CustomerAudit> findByLoginName(String loginName);

}
