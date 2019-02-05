package com.mailer.embeddedkafkatests;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import com.mailer.consumer.KafkaMailConsumer;
import com.mailer.model.Mail;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext
public class SpringKafkaReceiverTest {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(SpringKafkaReceiverTest.class);

  private static String RECEIVER_TOPIC = "receiver.t";

  @Autowired
  private KafkaMailConsumer receiver;

  private KafkaTemplate<String, Mail> template;

  @Autowired
  private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

  @ClassRule
  public static EmbeddedKafkaRule embeddedKafka =
      new EmbeddedKafkaRule(1, true, RECEIVER_TOPIC);

  @Before
  public void setUp() throws Exception {
    // set up the Kafka producer properties
    Map<String, Object> senderProperties =
        KafkaTestUtils.senderProps(
            embeddedKafka.getEmbeddedKafka().getBrokersAsString());

    // create a Kafka producer factory
    ProducerFactory<String, Mail> producerFactory =
        new DefaultKafkaProducerFactory<String, Mail>(
            senderProperties);

    // create a Kafka template
    template = new KafkaTemplate<String, Mail>(producerFactory);
    // set the default topic to send to
    template.setDefaultTopic(RECEIVER_TOPIC);

    // wait until the partitions are assigned
    for (MessageListenerContainer messageListenerContainer : kafkaListenerEndpointRegistry
        .getListenerContainers()) {
      ContainerTestUtils.waitForAssignment(messageListenerContainer,
          embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
    }
  }

  @Test
  public void testReceive() throws Exception {
    Mail mail = new Mail();
    template.sendDefault(mail);
    LOGGER.debug("test-sender sent message='{}'", mail);

    receiver.latch.await(10000, TimeUnit.MILLISECONDS);
    assertThat(receiver.latch.getCount()).isEqualTo(0);
  }
}