/**
 * 
 */
package com.mailer.serializer;

import java.util.Map;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 *
 */
public class MailSerializer implements Serializer<Mail> {

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] serialize(String topic, Mail data) {
		ObjectMapper mapper = new ObjectMapper();
		byte[] mail=null;
		try {
			mail = mapper.writeValueAsString(data).getBytes();
		}catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return mail;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
