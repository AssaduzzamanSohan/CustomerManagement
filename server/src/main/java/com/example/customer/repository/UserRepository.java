package com.example.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.customer.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserKey(Long userKey);

	User findByLoginNameAndPassword(String loginName, String password);
}
