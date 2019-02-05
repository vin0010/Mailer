package com.mailer.producer;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 * 
 * Kafka message producer which is used by the controller explicitly to send all incoming requests to topics.
 * 
 */
public class KafkaMailProducer {

	private Logger LOGGER = LoggerFactory.getLogger(KafkaMailProducer.class);
			
	@Autowired
	private KafkaTemplate<String, Mail> messageKafkaTemplate;

	@Value(value = "${kafka.mail.topic.name}")
	private String topicName;

	/**
	 * @param message
	 */
	public void sendMessage(Mail message) {
		ListenableFuture<SendResult<String, Mail>> future = messageKafkaTemplate.send(topicName, new Random().nextLong() + "", message);
		future.addCallback(new ListenableFutureCallback<SendResult<String, Mail>>() {

			@Override
			public void onSuccess(SendResult<String, Mail> result) {
				LOGGER.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				LOGGER.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}
}