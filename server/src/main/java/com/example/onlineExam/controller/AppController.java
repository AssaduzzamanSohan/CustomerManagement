package com.example.onlineExam.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.customer.model.RequestMessage;
import com.example.customer.model.RequestMessageHeader;
import com.example.onlineExam.service.ServiceCoordinator;
import com.example.onlineExam.utils.MyGson;

@RestController
@CrossOrigin("*")
public class AppController {
	private static Logger log = LogManager.getLogger(AppController.class);

	@Autowired
	ServiceCoordinator serviceCoordinator;

	@PostMapping(value = "/jsonRequest", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String handleJsonRequest(@RequestBody String json) {
		String responseString = "";
		RequestMessage requestMessage = null;
		try {
			requestMessage = MyGson.gson.fromJson(json, RequestMessage.class);
			validateRequest(requestMessage.getHeader());

			if (requestMessage.getPayload() != null) {
				// sending message to service coordinator
				responseString = serviceCoordinator.handleMessege(requestMessage);

				if (responseString == null || responseString.isEmpty()) {
					responseString = "No response received from service -> " + requestMessage.getHeader().getContentType() + "Service";
				}
			}
		}
		catch (Exception ex) {
			log.error("error with request {}", ex);
			responseString = ex.getLocalizedMessage();
		}
		requestMessage.setPayload(responseString);
		responseString = MyGson.gson.toJson(requestMessage);
		log.debug("Sending Response {}", responseString);

		return responseString;
	}

	private void validateRequest(RequestMessageHeader requestHeader) throws Exception {
		StringBuffer sb = new StringBuffer();

		if (requestHeader.getContentType() == null) {
			sb.append("Missing ServiceType");
		}

		if (requestHeader.getActionType() == null) {
			sb.append("Missing ActionType");
		}

		if (sb.length() > 0) {
			throw new Exception(sb.toString());
		}

	}

	@GetMapping(value = "ping")
	public String getMethodName() {
		log.info("Ping");
		log.error("Pong");
		return "Pong";
	}

}
