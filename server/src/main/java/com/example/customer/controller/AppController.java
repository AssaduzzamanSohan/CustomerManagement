package com.example.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.message.MessageHeader;
import com.example.customer.message.Status;
import com.example.customer.message.enums.MessageContentType;
import com.example.customer.message.enums.StatusType;
import com.example.customer.message.interfaces.Message;
import com.example.customer.message.processor.GsonJsonMessageProcessor;
import com.example.customer.service.ServiceCoordinator;
import com.google.gson.Gson;

@RestController
@CrossOrigin("*")
public class AppController {
	private static Logger log = LogManager.getLogger(AppController.class);

	static Gson gson;
	static {
		gson = new Gson();
	}

	@Autowired
	ServiceCoordinator serviceCoordinator;

	@Autowired
	private GsonJsonMessageProcessor messageProcessor;

	@RequestMapping(value = "/jsonRequest", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String handleJsonRequest(@RequestBody String json) {

		Message<?> request = null, dataMsg = null, response = null;
		MessageHeader requestHeaders = null;
		String responseString = null, errorString = null, serviceName = null;

		Map<String, Object> statusMsgHeader = new HashMap<String, Object>();

		statusMsgHeader.put(MessageHeader.CONTENT_TYPE, MessageContentType.STATUS);

		try {
			request = messageProcessor.processMessage(json);

			log.debug("Recieved Request {}", json);

			if (request != null && request.getHeader().getContentType() != MessageContentType.EXCEPTION.toString()) {
				requestHeaders = request.getHeader();

				log.debug("Source [{}] Destination [{}]", requestHeaders.getSource(), serviceName);
				// validating request
				validateRequest(requestHeaders, request);
				// sending message to service coordinator
				dataMsg = serviceCoordinator.service(request);

				if (dataMsg == null) {

					String errorMsg = "No response received from service -> " + serviceName;
					log.error(errorMsg);
					List<Status> statusList = new ArrayList<Status>();
					statusList.add(new Status(StatusType.ERROR, errorMsg));

					response = messageProcessor.createResponseMessage(request, statusMsgHeader, statusMsgHeader);

				} else {

					List<Status> statusMsgList = new ArrayList<Status>();
					statusMsgList.add(new Status(StatusType.OK));
					Message<List<Status>> statusMsg = messageProcessor.createResponseMessage(request, statusMsgList,
							statusMsgHeader);

					List<Message<?>> msgBody = new ArrayList<Message<?>>();
					msgBody.add(statusMsg);
					msgBody.add(dataMsg);

					Map<String, Object> finalMsgHeader = new HashMap<String, Object>();
					finalMsgHeader.put(MessageHeader.CONTENT_TYPE, MessageContentType.MULTI);
					response = messageProcessor.createResponseMessage(request, msgBody, finalMsgHeader);
				}
			}
		} catch (Exception ex) {
			log.error("error with request {}", ex);

			if (ex.getLocalizedMessage().contains("Error:")) {
				if (ex.getCause().getLocalizedMessage().contains("["))
					errorString = ex.getCause().getLocalizedMessage().replace("[", "");
				if (ex.getCause().getLocalizedMessage().contains("@"))
					errorString = ex.getCause().getLocalizedMessage().replace("@", "");
				if (ex.getCause().getLocalizedMessage().contains("]"))
					errorString = ex.getCause().getLocalizedMessage().replace("]", "");
				if (ex.getCause().getLocalizedMessage().contains("Int"))
					errorString = ex.getCause().getLocalizedMessage().replace("Int", "");
			} else if (ex.getLocalizedMessage().contains("Error:")
					&& !ex.getCause().getLocalizedMessage().contains("["))
				errorString = ex.getCause().getLocalizedMessage();
			else {
				errorString = ex.getLocalizedMessage();
			}

			List<Status> statusList = new ArrayList<Status>();
			statusList.add(new Status(StatusType.ERROR, errorString));

			response = messageProcessor.createResponseMessage(request, statusList, statusMsgHeader);
		}

		responseString = messageProcessor.toJson(response);

		log.debug("Sending Response {}", responseString);

		return responseString;
	}

	private void validateRequest(MessageHeader requestHeaders, Message<?> msg) throws Exception {
		StringBuffer sb = new StringBuffer();

		if (requestHeaders.getServiceType() == null) {
			sb.append("Missing ServiceType");
		}

		if (requestHeaders.getActionType() == null) {
			sb.append("Missing ActionType");
		}

		if (sb.length() > 0) {
			throw new Exception(sb.toString());
		}

	}

	@RequestMapping(value = "/test/api", method = RequestMethod.GET)
	public String testApi() {
		return "API is working.";
	}

	@RequestMapping(value = "/api/request/format", method = RequestMethod.GET)
	public String getApiRequestFormat() {
		return messageProcessor.getRequestSample();
	}

	@RequestMapping(value = "/api/response/format", method = RequestMethod.GET)
	public String getApiResponseFormat() {
		return messageProcessor.getRequestSample();
	}

}
