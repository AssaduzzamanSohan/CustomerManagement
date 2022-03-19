package com.example.onlineExam.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.customer.model.RequestMessage;

public class ServiceCoordinator {
	private static Logger log = LogManager.getLogger(ServiceCoordinator.class);

	private ServiceMap serviceMap;

	/**
	 * This method lookup for requested service
	 * 
	 * @param Message
	 * @return Message
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public String handleMessege(RequestMessage msg) throws Exception {
		String serviceName = msg.getHeader().getContentType();
		try {

			Service serviceHandler = serviceMap.getServiceByName(serviceName + "Service");
			log.info("Service Lookup {} -> {} ", serviceName, serviceHandler.getServiceName());

			if (serviceHandler != null) {
				return serviceHandler.serviceSingle(msg);
			}

		}
		catch (Exception ex) {
			log.error("Error {}", ex);
			return ex.getLocalizedMessage();
		}
		return "Service Not Found";
	}

	public ServiceMap getServiceMap() {
		return serviceMap;
	}

	public void setServiceMap(ServiceMap serviceMap) {
		this.serviceMap = serviceMap;
	}
}
