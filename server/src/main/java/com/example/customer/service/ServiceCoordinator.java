package com.example.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.example.customer.message.MessageBuilder;
import com.example.customer.message.MessageHeader;
import com.example.customer.message.enums.MessageContentType;
import com.example.customer.message.interfaces.Message;

import lombok.Data;

/**
 * @author Assaduzzaman Sohan
 */
@Data
public class ServiceCoordinator {
	private static Logger log = LogManager.getLogger(ServiceCoordinator.class);

	@Value("${app.name}")
	private static String appName;

	@Value("${app.server.name}")
	private static String appServerName;

	private ServiceMap serviceMap;
	private static final String PRIVATE_ADDRESS_LIST = "privateAddressList";
	private static final String SERVICE_POSTFIX = "Service";

	/**
	 * This is the Service Coordinator
	 * 
	 * @param msg
	 * @param source
	 * @param destination
	 * @return Message
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Message<?> service(Message<?> msg) throws Exception {

		List<Message> payloadList = null;
		List<Message> msgList = new ArrayList<>();
		Message msgRet = null;
		MessageHeader header = msg.getHeader();

		try {

			if (msg.getHeader().getContentType().equals(MessageContentType.MULTI_MESSAGE.toString())) {

				payloadList = (List<Message>) msg.getPayload();

				if (!payloadList.isEmpty()) {

					for (Message payload : payloadList) {
						msgRet = handleMessege(payload);
						msgList.add(msgRet);
					}
				}
				msg = MessageBuilder.withPayload(msgList).copyHeaders(header)
						.setHeader(MessageHeader.SOURCE, appServerName)
						.setHeader(MessageHeader.CONTENT_TYPE, msg.getHeader().getContentType())
						.setHeader(MessageHeader.ACTION_TYPE, msg.getHeader().getActionType())
						.setHeader(MessageHeader.DESTINATION, appServerName).build();
			} else if (msg.getHeader().get(PRIVATE_ADDRESS_LIST) != null
					&& msg.getHeader().get(PRIVATE_ADDRESS_LIST).equals(PRIVATE_ADDRESS_LIST)) {
				msg = handleMessege(msg);
				msg = MessageBuilder.withPayload(msg.getPayload()).copyHeaders(header)
						.setHeader(MessageHeader.SOURCE, appServerName)
						.setHeader(MessageHeader.CONTENT_TYPE, msg.getHeader().getContentType())
						.setHeader(MessageHeader.ACTION_TYPE, msg.getHeader().getActionType())
						.setHeader(PRIVATE_ADDRESS_LIST, msg.getHeader().get(PRIVATE_ADDRESS_LIST).toString())
						.setHeader(MessageHeader.DESTINATION, appServerName).build();
			} else {

				msg = handleMessege(msg);
				msg = MessageBuilder.withPayload(msg.getPayload()).copyHeaders(header)
						.setHeader(MessageHeader.SOURCE, appServerName)
						.setHeader(MessageHeader.CONTENT_TYPE, msg.getHeader().getContentType())
						.setHeader(MessageHeader.ACTION_TYPE, msg.getHeader().getActionType())
						.setHeader(MessageHeader.DESTINATION, appServerName).build();
			}

		} catch (Exception ex) {
			log.error("Error {}", ex);
			throw ex;
		}

		return msg;
	}

	/**
	 * This method lookup for requested service
	 * 
	 * @param Message
	 * @return Message
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Message handleMessege(Message msg) throws Exception {

		String serviceName = msg.getHeader().getServiceType();
		Service serviceHandler;

		try {

			serviceHandler = serviceMap.getServiceByName(serviceName + SERVICE_POSTFIX);
			log.info("Service Lookup {} -> {} ", serviceName, serviceHandler.getServiceName());

			if (serviceHandler != null) {

				msg = serviceHandler.serviceSingle(msg);
			}

		} catch (Exception ex) {
			log.error("Error {}", ex);
			throw ex;
		}

		return msg;
	}
}
