package com.example.customer.message.interfaces;

import java.util.Map;

public interface Header extends Map<String, Object> {
	public static final String ID = "id";
	public static final String CORRELATION_ID = "correlationId";
	public static final String TIMESTAMP = "timestamp";
	public static final String UTC_TIMESTAMP = "utcTimestamp";
	public static final String TZ_NAME = "tzName";
	public static final String PRIORITY = "priority";
	public static final String EXPIRATION_DATE = "expirationDate";
	public static final String SEQUENCE_SIZE = "sequenceSize";
	public static final String SEQUENCE_NUMBER = "sequenceNumber";
	public static final String CONTENT_TYPE = "contentType";
	public static final String SERVICE_TYPE = "serviceType";
	public static final String SOURCE = "source";
	public static final String DESTINATION = "destination";
	public static final String ACTION_TYPE = "actionType";
	public static final String LOCATION_ID = "locationId";
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	public static final String CLIENT_SOURCE = "clientSource";
	public static final String CLIENT_ID = "clientId";
}
