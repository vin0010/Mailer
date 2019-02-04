package com.mailer.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;

import com.mailer.domain.Mail;
import com.mailer.service.HttpDownloadUtility;
import com.mailer.service.MailService;
public class MessageConsumer {

	@Autowired
	private MailService notificationService;
	
    @Value(value = "${kafka.mail.groupid}")
    private String groupId;
	
    //can be used in case multiple listeners work together
    public java.util.concurrent.CountDownLatch latch = new java.util.concurrent.CountDownLatch(3);

	@KafkaListener(topics = "${kafka.mail.topic.name}", groupId = "${kafka.mail.groupid}", containerFactory = "mailKafkaListenerContainerFactory")
	public void listenGroupFoo(String mail) throws Exception {
		System.out.println("-------Received Messasge in consumer group " + groupId + ": " + mail);
		System.out.println("About to send mail");
		latch.countDown();
		// Attachment service seperated from mail service to make sure attachment is not getting downloaded more than once in case of retry
//		notificationService.sendNotification(mail, HttpDownloadUtility.downloadFile(mail.getUri().toString()));
	}
}