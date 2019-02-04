package com.mailer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mailer.domain.Message;
import com.mailer.producer.MessageProducer;

import io.swagger.annotations.Api;

@RestController
public class MailController {

	private Logger logger = LoggerFactory.getLogger(MailController.class);
	@Autowired
	private MessageProducer messageProducer;

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String send(@RequestBody Message message) {
		messageProducer.sendMessage(message);
		logger.info("Received mail details in rest call");
		System.out.println("Received message in rest:" + message);
		return "Request Accepted!";
	}
}