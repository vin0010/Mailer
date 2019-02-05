package com.mailer.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import com.mailer.model.Mail;
import com.mailer.service.MailService;
import com.mailer.utility.AttachmentDownloaderUtility;

/**
 * @author Vinoth.Gopu
 * 
 * Kafka consumer to poll topic and get messages if there are any
 *
 */
public class KafkaMailConsumer {

	@Autowired
	private MailService notificationService;

	@Value(value = "${kafka.mail.groupid}")
	private String groupId;

	// can be used in case multiple listeners work together
	public CountDownLatch latch = new CountDownLatch(3);

	/**
	 * Listener method to retrieve and process messages
	 * 
	 * @param mail - mail pojo
	 * @throws Exception
	 */
	@KafkaListener(topics = "${kafka.mail.topic.name}", groupId = "${kafka.mail.groupid}", containerFactory = "mailKafkaListenerContainerFactory")
	public void mailListener(Mail mail) throws Exception {
		System.out.println("Received Messasge in group " + groupId + ": " + mail);
		System.out.println("About to send mail");
		latch.countDown();
		// Attachment service seperated from mail service to make sure attachment is not
		// getting downloaded more than once in case of retry
		notificationService.sendNotification(mail, AttachmentDownloaderUtility.downloadFile(mail.getUri().toString()));
	}
}