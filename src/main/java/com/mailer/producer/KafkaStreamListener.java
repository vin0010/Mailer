package com.mailer.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaStreamListener {
	@Bean("app1StreamTopology")
	public KStream<String, String> startProcessing(@Qualifier("app1StreamBuilder") StreamsBuilder builder) {

		final KStream<String, String> toSquare = builder.stream("baeldung",
				Consumed.with(Serdes.String(), Serdes.String()));
		toSquare.map((key, value) -> { // do something with each msg, square the values in our case
			System.out.println(key+"-----stream------"+value);
			return KeyValue.pair(key, value);
		}).to("squared", Produced.with(Serdes.String(), Serdes.String())); // send downstream to another topic
		System.out.println("Success fulll");
		return toSquare;
	}

}