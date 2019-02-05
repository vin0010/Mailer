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

import com.mailer.model.Mail;
import com.mailer.producer.KafkaMessageProducer;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * @author Vinoth.Gopu
 *
 * REST Controller class to receive mail request. 
 */
@Api("End point to send mails asynchronously")
@RestController
public class MailController {

	private Logger logger = LoggerFactory.getLogger(MailController.class);
	@Autowired
	private KafkaMessageProducer messageProducer;

	/**
	 * @param message Mail pojo
	 * @return acknowledgement that the request for mail has been accepeted
	 */
	@ApiOperation(value = "Send mails",response = String.class, consumes="JSON", tags="mail")
    @ApiResponses( 
		value = {
				@ApiResponse(code = 202, message = "Send Email request accepted"),
		}
	)
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String send(@RequestBody Mail message) {
		messageProducer.sendMessage(message);
		logger.info("Received mail details in rest call");
		System.out.println("Received message in rest:" + message);
		return "Request Accepted!";
	}
}