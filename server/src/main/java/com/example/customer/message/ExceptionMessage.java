package com.example.customer.message;

import java.util.Map;

import com.example.customer.message.enums.MessageContentType;
import com.example.customer.message.interfaces.Header;

public class ExceptionMessage extends GenericMessage<String> {
	public ExceptionMessage(final Throwable t) {
		super(t.toString());
		this.getHeader().update(Header.CONTENT_TYPE, MessageContentType.EXCEPTION.toString());
	}

	public ExceptionMessage(final Throwable t, final Map<String, Object> map) {
		super(t.toString(), map);
		this.getHeader().update(Header.CONTENT_TYPE, MessageContentType.EXCEPTION.toString());
	}
}
