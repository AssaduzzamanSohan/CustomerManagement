package com.example.customer.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.customer.message.interfaces.Header;
import com.example.customer.message.interfaces.Message;

public final class MessageBuilder<T> {
	private Map<String, Object> header = new HashMap<String, Object>();
	private final T payload;

	public MessageBuilder(final T payload, final Map<String, Object> header) {
		this.payload = payload;
		this.header = header != null ? header : new HashMap<String, Object>();
	}

	public static <T> MessageBuilder<T> fromMessage(final T t, final MessageHeader messageHeader) {
		return new MessageBuilder<T>(t, messageHeader);
	}

	public static <T> MessageBuilder<T> fromMessage(final T t, final Map<String, Object> header) {
		return new MessageBuilder<T>(t, header);
	}

	public static <T> MessageBuilder<T> withPayload(final T t) {
		return new MessageBuilder<T>(t, null);
	}

	public MessageBuilder<T> setHeader(final String s, final Object o) {
		this.header.put(s, o);
		return this;
	}

	public MessageBuilder<T> setHeaderIfAbsent(final String s, final Object o) {
		return this.doSetHeaderIfAbsent(s, o);
	}

	public MessageBuilder<T> doSetHeaderIfAbsent(final String s, final Object o) {
		if (this.header.get(s) == null) {
			this.setHeader(s, o);
		}
		return this;
	}

	public MessageBuilder<T> removeHeaders(final String... array) {
		for (String it : array) {
			removeHeader(it);
		}
		return this;
	}

	public MessageBuilder<T> removeHeader(final String s) {
		this.header.remove(s);
		return this;
	}

	public MessageBuilder<T> copyHeaders(final Map<String, ?> map) {
		this.header = new HashMap<String, Object>(map);
		return this;
	}

	public MessageBuilder<T> copyHeadersIfAbsent(final Map<String, ?> map) {
		return this.copyHeaders(map);
	}

	public MessageBuilder<T> setExpirationDate(final Long n) {
		return this.setHeader(Header.EXPIRATION_DATE, n);
	}

	public MessageBuilder<T> setExpirationDate(final Date date) {
		return this.setHeader(Header.EXPIRATION_DATE, date);
	}

	public MessageBuilder<T> setCorrelationId(final Object o) {
		return this.setHeader(Header.CORRELATION_ID, o);
	}

	public MessageBuilder<T> setSequenceNumber(final Integer n) {
		return this.setHeader(Header.SEQUENCE_NUMBER, n);
	}

	public MessageBuilder<T> setPriority(final Integer n) {
		return this.setHeader(Header.PRIORITY, n);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Message<T> build() {
		return new GenericMessage(this.payload, this.header);
	}
}
