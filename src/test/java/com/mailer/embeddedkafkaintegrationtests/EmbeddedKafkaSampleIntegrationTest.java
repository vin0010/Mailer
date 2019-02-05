package com.mailer.embeddedkafkaintegrationtests;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.mailer.consumer.KafkaMailConsumer;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class EmbeddedKafkaSampleIntegrationTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedKafkaSampleIntegrationTest.class);

	private static String RECEIVER_TOPIC = "receiver.t";

	@Autowired
	private KafkaMailConsumer receiver;

	private KafkaTemplate<String, String> template;

	@ClassRule
	public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, RECEIVER_TOPIC);

	@Before
	public void setUp() throws Exception {
		// set up the Kafka producer properties
		Map<String, Object> senderProperties = KafkaTestUtils
				.senderProps(embeddedKafka.getEmbeddedKafka().getBrokersAsString());

		// create a Kafka producer factory
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<String, String>(
				senderProperties);

		// create a Kafka template
		template = new KafkaTemplate<>(producerFactory);
		// set the default topic to send to
		template.setDefaultTopic(RECEIVER_TOPIC);

	}

	@Ignore
	@Test
	public void testReceive() throws Exception {
		// send the message
		String greeting = "This message needs to be delivered!";
		template.sendDefault(greeting);
		LOGGER.debug("test-sender sent message='{}'", greeting);

		receiver.latch.await(1000, TimeUnit.MILLISECONDS);
		// check that the message was received
		assertThat(receiver.latch.getCount()).isEqualTo(3);
	}
}