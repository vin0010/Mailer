package com.mailer.consumer;

import java.util.HashMap;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.mailer.domain.Mail;

@EnableKafka
@Configuration
public class KafkaMailConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    
    @Value(value = "${kafka.mail.groupid}")
    private String groupId;

    @Bean
    public KafkaMailConsumer messageListener() {
    	return new KafkaMailConsumer();
    }

    public ConsumerFactory<String, Mail> getConsumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Mail.class));
    }

    
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Mail> mailKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Mail> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getConsumerFactory(groupId));
        return factory;
    }
}
