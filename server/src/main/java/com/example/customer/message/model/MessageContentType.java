package com.example.customer.message.model;

public enum MessageContentType {
	SIMPLE("SIMPLE"), MULTI("MULTI"), STATUS("STATUS"), EXCEPTION("EXCEPTION");

	private final String messageContentType;

	private MessageContentType(String messageContentType) {
		this.messageContentType = messageContentType;
	}

	@Override
	public String toString() {
		return this.messageContentType;
	}
}
