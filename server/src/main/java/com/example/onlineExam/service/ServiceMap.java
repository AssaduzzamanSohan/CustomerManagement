package com.example.onlineExam.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Data;

@Data
public class ServiceMap {

	private static Logger log = LogManager.getLogger(ServiceMap.class);
	private Map<String, Service<?>> serviceMap;

	public ServiceMap() {
		this.serviceMap = new LinkedHashMap<String, Service<?>>();
	}

	public Service<?> getServiceByName(final String serviceName) throws Exception {
		if (!this.serviceMap.containsKey(serviceName)) {
			throw new Exception("Unknow Service " + serviceName);
		}
		return this.serviceMap.get(serviceName);
	}

	public void addService(final Service<?> service) {
		log.info("Added Service {}", service.getServiceName());
		this.serviceMap.put(service.getServiceName(), service);
	}

}
