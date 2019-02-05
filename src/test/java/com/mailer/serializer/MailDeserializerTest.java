/**
 * 
 */
package com.mailer.serializer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 * Test class for Mail deserializer
 */
public class MailDeserializerTest {

	/**
	 * Test method for {@link com.mailer.serializer.MailDeserializer#deserialize(java.lang.String, byte[])}.
	 */
	@Test
	public final void testDeserialize() {
		String mail = "{\"from\":\"vinoth@localhost.com\",\"to\":null,\"subject\":null,\"body\":null,\"uri\":null}";
		try(MailDeserializer deserializer = new MailDeserializer()){
			Mail resultMail = deserializer.deserialize("", mail.toString().getBytes());
			assertTrue(resultMail.getFrom().equals("vinoth@localhost.com"));
		}
	}
}
