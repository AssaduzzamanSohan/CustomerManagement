package com.example.customer.message;

import java.util.Map;

import com.example.customer.message.enums.MessageContentType;
import com.example.customer.message.interfaces.Header;

public class StatusMessage extends GenericMessage<Status> {
	public StatusMessage(final Status status) {
		super(status);
		this.setContentType();
	}

	public StatusMessage(final Status status, final Map<String, Object> map) {
		super(status, map);
		this.setContentType();
	}

	private final void setContentType() {
		this.getHeader().update(Header.CONTENT_TYPE, MessageContentType.STATUS);
	}
}
