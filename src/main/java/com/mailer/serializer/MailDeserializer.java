/**
 * 
 */
package com.mailer.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 *
 */
public class MailDeserializer implements Deserializer<Mail> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public Mail deserialize(String topic, byte[] data) {
		Mail mail = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			mail = mapper.readValue(data, Mail.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mail;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
