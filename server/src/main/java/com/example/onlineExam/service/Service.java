package com.example.onlineExam.service;

import com.example.customer.model.RequestMessage;

public interface Service<T> {

	String getServiceName();

	void setServiceName(final String p0);

	String serviceSingle(final RequestMessage msg);
}
