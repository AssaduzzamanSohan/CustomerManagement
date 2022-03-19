package com.example.customer.message.enums;

public enum MessageContentType {
	SIMPLE("SIMPLE"), MULTI("MULTI"), MULTI_MESSAGE("MULTI_MESSAGE"), STATUS("STATUS"), EXCEPTION("EXCEPTION");

	private final String messageContentType;

	private MessageContentType(String messageContentType) {
		this.messageContentType = messageContentType;
	}

	@Override
	public String toString() {
		return this.messageContentType;
	}
}
