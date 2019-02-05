package com.mailer.producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.mailer.model.Mail;
import com.mailer.serializer.MailSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vinoth.Gopu
 * 
 * Kafka Producer configuration class
 *
 */
@Configuration
public class KafkaMailProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    /**
     * Producer factory bean creaion
     * @return ProducerFactory bean
     */
    @Bean
    public ProducerFactory<String, Mail> mailProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MailSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    /**
     * @return KafkaMailProducer bean
     */
    @Bean
    public KafkaMailProducer messageProducer() {
        return new KafkaMailProducer();
    }
    
    /**
     * @return kafka template used by producer to access kafka topics
     */
    @Bean
    public KafkaTemplate<String, Mail> mailKafkaTemplate() {
        return new KafkaTemplate<String, Mail>(mailProducerFactory());
    }
    
}
