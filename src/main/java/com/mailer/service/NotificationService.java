package com.mailer.service;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;

import com.mailer.domain.Message;
import com.sun.mail.smtp.SMTPAddressFailedException;

@Service
@EnableRetry
public class NotificationService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	@Retryable(
			value = { Exception.class }, 
			maxAttemptsExpression = "#{${mailer.retry.maxAttempts}}", backoff = @Backoff(delayExpression = "#{${mailer.retry.backOffDelay}}"))
	public void sendNotification(Message mail){
        System.out.println("Sending email...");
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
        	MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        	File file = HttpDownloadUtility.downloadFile(mail.getUri().toString());
        	mailMessage.addAttachment(file.getName(), file);
        	mailMessage.setTo(mail.getTo());
        	mailMessage.setFrom(mail.getFrom());
			mailMessage.setSubject(mail.getSubject());
			mailMessage.setText(mail.getBody());
		} catch (Exception e) {
			System.out.println("Going to rety!");
			e.printStackTrace();
		}
		javaMailSender.send(message);
		//how to get acknowlwdgement that it failed/succeeded
		System.out.println("Email Sent!");
	}
}