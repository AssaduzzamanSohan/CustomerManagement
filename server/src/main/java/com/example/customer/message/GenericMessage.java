package com.example.customer.message;

import java.util.HashMap;
import java.util.Map;

import com.example.customer.message.interfaces.Message;

import lombok.Data;

@Data
public class GenericMessage<T> implements Message<T> {
	private final MessageHeader header;
	private final T payload;

	public GenericMessage(final T t) {
		this(t, null);
	}

	public GenericMessage(final T payload, final Map<String, Object> m) {
		HashMap<String, Object> hashMap;
		if (m == null) {
			hashMap = new HashMap<String, Object>();
		} else {
			hashMap = new HashMap<String, Object>(m);
		}
		this.header = new MessageHeader(hashMap);
		this.payload = payload;
	}

	public GenericMessage(final T payload, MessageHeader header) {
		this.header = header;
		this.payload = payload;
	}

	@Override
	public MessageHeader getHeader() {
		return this.header;
	}

	@Override
	public T getPayload() {
		return this.payload;
	}
}
