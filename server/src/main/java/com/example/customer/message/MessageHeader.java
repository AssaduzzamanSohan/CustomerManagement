package com.example.customer.message;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.customer.message.interfaces.Header;

public class MessageHeader implements Header {
	private static Logger log = LogManager.getLogger(MessageHeader.class);
	private static volatile IdGenerator idGenerator;
	private final Map<String, Object> header;

	public MessageHeader(final Map<String, Object> m) {
		this.header = ((m != null) ? new HashMap<String, Object>(m) : new HashMap<String, Object>());
		if (this.header.get(Header.ID) == null) {
			if (MessageHeader.idGenerator == null) {
				this.header.put(Header.ID, UUID.randomUUID());
			} else {
				this.header.put(Header.ID, MessageHeader.idGenerator.generateId());
			}
		}
		this.header.put(Header.UTC_TIMESTAMP, Instant.now().getEpochSecond());
		this.header.put(Header.TZ_NAME, ZoneId.systemDefault().getId());
	}

	public UUID getId() {
		return this.get(Header.ID, UUID.class);
	}

	public Long getTimestamp() {
		return this.get(Header.TIMESTAMP, Long.class);
	}

	public String getTzName() {
		return this.get(Header.TZ_NAME, String.class);
	}

	public Integer getPriority() {
		return this.get(Header.PRIORITY, Integer.class);
	}

	public Long getExpirationDate() {
		return this.get(Header.EXPIRATION_DATE, Long.class);
	}

	public Object getCorrelationId() {
		return this.get(Header.CORRELATION_ID);
	}

	public Integer getSequenceNumber() {
		final Integer n = this.get(Header.SEQUENCE_NUMBER, Integer.class);
		return (n != null) ? n : 0;
	}

	public Integer getSequenceSize() {
		final Integer n = this.get(Header.SEQUENCE_SIZE, Integer.class);
		return (n != null) ? n : 0;
	}

	public String getContentType() {
		return this.get(Header.CONTENT_TYPE, String.class);
	}

	public String getSource() {
		return this.get(Header.SOURCE, String.class);
	}

	public String getClientSource() {
		return this.get(Header.CLIENT_SOURCE, String.class);
	}

	public String getDestination() {
		return this.get(Header.DESTINATION, String.class);
	}

	public Integer getLocationId() {
		return this.get(Header.LOCATION_ID, Double.class).intValue();
	}

	public Integer getUserId() {
		return this.get(Header.USER_ID, Double.class).intValue();
	}

	public String getUserName() {
		return this.get(Header.USER_NAME, String.class);
	}

	public String getActionType() {
		return this.get(Header.ACTION_TYPE, String.class);
	}

	public String getServiceType() {
		return this.get(Header.SERVICE_TYPE, String.class);
	}

	public Integer getClientId() {
		return this.get(Header.CLIENT_ID, Double.class).intValue();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(final Object obj, final Class<T> obj2) {
		final Object value = this.header.get(obj);
		if (value == null) {
			return null;
		}
		if (!obj2.isAssignableFrom(value.getClass())) {
			throw new IllegalArgumentException("Incorrect type specified for header '" + obj + "'. Expected [" + obj2
					+ "] but actual type is [" + value.getClass() + "]");
		}
		log.trace("Fecthed Message Header...{} / {}", obj, value);
		return (T) value;
	}

	public void update(final String key, final Object value) {
		this.header.put(key, value);
	}

	@Override
	public int hashCode() {
		return this.header.hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		return this == o || (o != null && o instanceof MessageHeader && this.header.equals(((MessageHeader) o).header));
	}

	@Override
	public String toString() {
		return this.header.toString();
	}

	@Override
	public boolean containsKey(final Object o) {
		return this.header.containsKey(o);
	}

	@Override
	public boolean containsValue(final Object o) {
		return this.header.containsValue(o);
	}

	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		return Collections.unmodifiableSet((Set<? extends Map.Entry<String, Object>>) this.header.entrySet());
	}

	@Override
	public Object get(final Object o) {
		return this.header.get(o);
	}

	@Override
	public boolean isEmpty() {
		return this.header.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return Collections.unmodifiableSet((Set<? extends String>) this.header.keySet());
	}

	@Override
	public int size() {
		return this.header.size();
	}

	@Override
	public Collection<Object> values() {
		return Collections.unmodifiableCollection((Collection<?>) this.header.values());
	}

	@Override
	public Object put(final String s, final Object o) {
		throw new UnsupportedOperationException("MessageHeaders are immutable.");
	}

	@Override
	public void putAll(final Map<? extends String, ?> map) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object remove(final Object o) {
		throw new UnsupportedOperationException("MessageHeaders are immutable.");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("MessageHeaders are immutable.");
	}

	public interface IdGenerator {
		UUID generateId();
	}
}
