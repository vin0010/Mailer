package com.mailer.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.mailer.domain.Mail;

/**
 * @author Vinoth.Gopu
 *
 */
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {

	@Mock
	private MailController mailController;

	/**
	 * Test method for
	 * {@link com.mailer.controller.MailController#send(com.mailer.domain.Mail)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendMailTest() throws Exception {

		Mockito.when(mailController.send(Mockito.any(Mail.class))).thenReturn("Request Accepted!");

		String response = mailController.send(new Mail());

		assertEquals(response, "Request Accepted!");

	}

}
