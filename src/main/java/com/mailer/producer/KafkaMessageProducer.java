package com.mailer.producer;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.stereotype.Component;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.util.concurrent.ListenableFuture;
//import org.springframework.util.concurrent.ListenableFutureCallback;

import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 * 
 * Kafka message producer which is used by the controller explicitly to send all incoming requests to topics.
 * 
 */
public class KafkaMessageProducer {

//	@Autowired
//	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Mail> messageKafkaTemplate;

	@Value(value = "${kafka.mail.topic.name}")
	private String topicName;

	/*public void sendMessage(String message) {

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				System.out.println(
						"Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
			}

			@Override
			public void onFailure(Throwable ex) {
				System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
			}
		});
	}*/

	/**
	 * @param message
	 */
	public void sendMessage(Mail message) {
		messageKafkaTemplate.send(topicName, new Random().nextLong() + "", message);
	}
}