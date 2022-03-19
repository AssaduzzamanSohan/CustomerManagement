package com.example.customer.message.processor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.example.customer.message.interfaces.MessageProcessor;

public abstract class AbstractMessageProcessor implements MessageProcessor {
	private static Set<String> multiMessageSet;
	private Map<String, Class<?>> classMap;

	public AbstractMessageProcessor() {
		this.classMap = new LinkedHashMap<String, Class<?>>(0);
	}

	public static final Set<String> getMultiMessageSet() {
		return AbstractMessageProcessor.multiMessageSet;
	}

	public boolean isMultiMessage(final String s) {
		return AbstractMessageProcessor.multiMessageSet.contains(s);
	}

	@Override
	public Map<String, Class<?>> getClassMap() {
		return this.classMap;
	}

	@Override
	public void setClassMap(final Map<String, Class<?>> classMap2) {
		this.classMap = classMap2;
	}

	@Override
	public void addToClassMap(final String s, final Class<?> p1) {
		this.classMap.put(s, p1);
	}
}
