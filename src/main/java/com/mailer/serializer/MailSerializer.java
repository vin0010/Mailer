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
 * Serializer for Mail pojo
 */
public class MailSerializer implements Serializer<Mail> {

	/* (non-Javadoc)
	 * @see org.apache.kafka.common.serialization.Serializer#configure(java.util.Map, boolean)
	 */
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.apache.kafka.common.serialization.Serializer#serialize(java.lang.String, java.lang.Object)
	 */
	@Override
	public byte[] serialize(String topic, Mail data) {
		ObjectMapper mapper = new ObjectMapper();
		byte[] mail=null;
		try {
			mail = mapper.writeValueAsString(data).getBytes();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mail;
	}

	/* (non-Javadoc)
	 * @see org.apache.kafka.common.serialization.Serializer#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

}
