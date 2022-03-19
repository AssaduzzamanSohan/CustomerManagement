package com.example.onlineExam.service;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Data;

@Data
public abstract class AbstractService<T> implements Service<T> {
	private static Logger log = LogManager.getLogger(AbstractService.class);

	private String serviceName;
	private Map<String, Service<?>> serviceMap;

	public AbstractService() {
		this(null);
	}

	public AbstractService(final String serviceName) {
		this.setServiceName(serviceName);
	}

	@Override
	public void setServiceName(String serviceName) {
		if (serviceName == null) {
			serviceName = this.getClass().getSimpleName();
			log.warn("***** [{}] No Service Name provided, defaulting serviceName to {} *****",
					this.getClass().getSimpleName(), serviceName);
		}
		this.serviceName = serviceName;
	}

	@Override
	public String getServiceName() {
		return this.serviceName;
	}

	public Service<?> getServiceByName(final String serviceName) throws Exception {
		if (!this.serviceMap.containsKey(serviceName)) {
			throw new Exception("Unknow Service " + serviceName);
		}
		return this.serviceMap.get(serviceName);
	}
}