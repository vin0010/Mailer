package com.mailer.producer;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.mailer.domain.Message;
import com.mailer.service.NotificationService;

import org.springframework.kafka.support.KafkaHeaders;
@Qualifier
public class MessageConsumer {

	@Autowired
	private NotificationService notificationService;
	
	private CountDownLatch latch = new CountDownLatch(3);

	private CountDownLatch partitionLatch = new CountDownLatch(2);

	private CountDownLatch filterLatch = new CountDownLatch(2);

	private CountDownLatch greetingLatch = new CountDownLatch(1);

	@KafkaListener(topics = "${message.topic.name}", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
	public void listenGroupFoo(Message message) throws Exception {
		System.out.println("-------Received Messasge in group 'foo': " + message);
		System.out.println("About to send mail");
		notificationService.sendNotification(message);
		latch.countDown();
	}

	/*@KafkaListener(topics = "${message.topic.name}", groupId = "bar", containerFactory = "barKafkaListenerContainerFactory")
	public void listenGroupBar(String message) {
		System.out.println("-----Received Messasge in group 'bar': " + message);
		latch.countDown();
	}

	@KafkaListener(topics = "${message.topic.name}", containerFactory = "headersKafkaListenerContainerFactory")
	public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		System.out.println("----Received Messasge: " + message + " from partition: " + partition);
		latch.countDown();
	}

	@KafkaListener(topicPartitions = @TopicPartition(topic = "${partitioned.topic.name}", partitions = { "0", "3" }))
	public void listenToParition(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
		System.out.println("---Received Message: " + message + " from partition: " + partition);
		this.partitionLatch.countDown();
	}

	@KafkaListener(topics = "${filtered.topic.name}", containerFactory = "filterKafkaListenerContainerFactory")
	public void listenWithFilter(String message) {
		System.out.println("--Recieved Message in filtered listener: " + message);
		this.filterLatch.countDown();
	}
	//greetingKafkaListenerContainerFactory
	//greetingKafkaListenerContainerFactory
	@KafkaListener(topics = "${greeting.topic.name}", containerFactory = "greetingKafkaListenerContainerFactory")
	public void greetingListener(Message message) {
		System.out.println("-Recieved message message in kafka : " + message);
		this.greetingLatch.countDown();
	}*/

}