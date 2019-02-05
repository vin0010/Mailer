/**
 * 
 */
package com.mailer.serializer;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 * Test class for Mail serializer
 */
public class MailSerializerTest {
	/**
	 * Test method for {@link com.mailer.serializer.MailSerializer#serialize(java.lang.String, com.mailer.model.Mail)}.
	 */
	@Test
	public final void testSerialize() {
		try(MailSerializer mailSerializer = new MailSerializer()){
			String fromId = "vinoth@localhost.com";
			Mail mail = new Mail();
			mail.setFrom(fromId);
			byte[] data = mailSerializer.serialize("", mail);
			assertTrue(new String(data).contains(fromId));
		}
	}
}
