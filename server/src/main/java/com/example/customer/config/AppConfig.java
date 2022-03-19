package com.example.customer.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.customer.message.processor.GsonJsonMessageProcessor;
import com.example.customer.model.Customer;
import com.example.customer.model.User;
import com.example.customer.service.CustomerService;
import com.example.customer.service.ServiceCoordinator;
import com.example.customer.service.ServiceMap;
import com.example.customer.service.UserService;

@Configuration
public class AppConfig {

	@Bean
	ServiceMap serviceMap() {
		ServiceMap ob = new ServiceMap();
		ob.addService(userService());
		ob.addService(customerService());
		return ob;
	}

	@Bean
	GsonJsonMessageProcessor jsonJsonMessageProcessor() {
		GsonJsonMessageProcessor gsn = new GsonJsonMessageProcessor();
		Map<String, Class<?>> classMap = new LinkedHashMap<>();
		classMap.put(User.class.getSimpleName(), User.class);
		classMap.put(Customer.class.getSimpleName(), Customer.class);

		gsn.setClassMap(classMap);
		return gsn;
	}

	@Bean
	ServiceCoordinator serviceCoordinator() {
		ServiceCoordinator sc = new ServiceCoordinator();
		sc.setServiceMap(serviceMap());
		return sc;
	}

	@Bean
	UserService userService() {
		UserService service = new UserService();
		return service;
	}

	@Bean
	CustomerService customerService() {
		CustomerService service = new CustomerService();
		return service;
	}

}
