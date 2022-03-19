package com.example.customer.message.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.example.customer.message.GenericMessage;
import com.example.customer.message.MessageBuilder;
import com.example.customer.message.interfaces.Header;
import com.example.customer.message.interfaces.Message;
import com.example.customer.message.interfaces.MessageConstants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonJsonMessageProcessor extends AbstractMessageProcessor {

	static Gson gson;
	static {
		gson = new Gson();
	}

	private static final String REQUEST_SAMPLE = "{\r\n" + "\"header\": {\r\n"
			+ "\"source\": \"\", // from where this request is coming\r\n"
			+ "\"clientSource\": { // client information, style not fixed yet\r\n" + "\"id\": \"\", // client id\r\n"
			+ "\"uniqueId\": \"\", \r\n" + "\"alias\": [\r\n" + "\"\",\r\n" + "\"\"\r\n" + "]\r\n" + "},\r\n"
			+ "\"serviceType\": \"\", // which type of service need to perform \r\n"
			+ "\"objectType\": \"\", // which type of object is there\r\n"
			+ "\"contentType\": \"\", // which type of content is there. Simply Model class name\r\n"
			+ "\"actionType\": \"\", // which type of action need to perform\r\n"
			+ "\"userId\": 1, // userID of requested person \r\n"
			+ "\"reference\": \"\", // this request reference, use to catch response\r\n"
			+ "\"appName\": \"\", // project name\r\n" + "\"envId\": 1, // \r\n" + "\"senderId\": 1, // \r\n"
			+ "\"id\": \"\", // id of this request, unique id\r\n" + "\"sequenceNumber\": 5, // \r\n"
			+ "\"clientUid\": \"\"// client unique id \r\n" + "},\r\n" + "\"payload\": [\r\n" + "{\r\n"
			+ "\"userModKey\": 1, // must need to provide\r\n" + "\"Id\": 1// other req parm\r\n" + "},\r\n" + "{\r\n"
			+ "\"userModKey\": 1, // must need to provide\r\n" + "\"Id\": 1 \r\n" + "}\r\n" + "]\r\n" + "}";

	private static final String RESPONSE_SAMPLE = "{\n\"header\": {}, // same as request header except contentType\n"
			+ "\"payload\": [\n" + "{\n" + "\"header\": {}, // same as request header except contentType\n"
			+ "\"payload\": [] // response status here\n" + "},\n" + "{\n"
			+ "\"header\": {}, // same as request header except source and destination \n"
			+ "\"payload\": [] // all response data\n" + "}\n" + "]\n" + "}";

	public String getRequestSample() {
		return REQUEST_SAMPLE;
	}

	public String getResponseSample() {
		return RESPONSE_SAMPLE;
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public Message<?> processMessage(final String s) throws Exception {

		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(s).getAsJsonObject();

		final Map map = (Map) gson.fromJson(jsonObject.get(MessageConstants.MSG_HEADER).toString(),
				(Class) LinkedHashMap.class);

		JsonArray jsonArray = jsonObject.getAsJsonArray(MessageConstants.MSG_PAYLOAD);
		if (jsonArray == null) {
			jsonArray = jsonObject.getAsJsonArray(MessageConstants.MSG_PAYLOAD_X);
		}

		List<Object> objectList = new ArrayList<>();
		for (JsonElement it : jsonArray) {
			String rawData = it.getAsJsonObject().toString();
			objectList.add(gson.fromJson(rawData, getClassMap().get(map.get(Header.CONTENT_TYPE))));
		}

		return new GenericMessage(objectList, map);
	}

	public <T> Message<T> createResponseMessage(final Message<?> message, final T t, final Map<String, Object> map) {

		for (Entry<String, Object> entry : map.entrySet()) {
			message.getHeader().update(entry.getKey(), entry.getValue());
		}

		return MessageBuilder.fromMessage(t, message.getHeader()).build();
	}

	public static <T> T fromJson(final String payload, final Class<T> clazz) {
		return gson.fromJson(payload, clazz);
	}

	@Override
	public String toJson(final Message<?> message) {
		return gson.toJson(message);
	}

	@Override
	public String toJson(final Object o) {
		return gson.toJson(o);
	}
}
