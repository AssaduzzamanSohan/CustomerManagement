package com.example.customer.message.interfaces;

import java.util.Map;

public interface MessageProcessor {

	<T> Message<T> processMessage(final String p0) throws Exception;

	Map<String, Class<?>> getClassMap();

	void setClassMap(final Map<String, Class<?>> p0);

	void addToClassMap(final String p0, final Class<?> p1);

	String toJson(final Message<?> p0);

	String toJson(final Object p0);
}
