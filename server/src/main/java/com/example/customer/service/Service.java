package com.example.customer.service;

import com.example.customer.message.interfaces.Message;

public interface Service<T> {

	String getServiceName();

	void setServiceName(final String p0);

	Message<?> serviceSingle(final Message<T> p0) throws Exception;
}
