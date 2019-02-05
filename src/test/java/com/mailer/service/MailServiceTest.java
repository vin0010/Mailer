/**
 * 
 */
package com.mailer.service;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.mailer.model.Mail;

/**
 * @author Vinoth.Gopu
 *
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MailServiceTest {

	@Mock
	private MailService mailService;
	
	/**
	 * Test method for {@link com.mailer.service.MailService#sendNotification(com.mailer.model.Mail, java.io.File)}.
	 */
	@Test
	public final void testSendNotification() {
		Mockito.when(
				mailService.sendNotification(Mockito.any(Mail.class), Mockito.any(File.class))
				).thenReturn("Request Accepted!");
		
		String response = mailService.sendNotification(new Mail(), new File("test.txt"));
		assertEquals(response,"Request Accepted!");
	}

}
