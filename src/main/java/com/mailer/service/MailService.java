package com.mailer.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mailer.model.Mail;

@Service
@EnableRetry
public class MailService {

	private JavaMailSender javaMailSender;
	
	@Autowired
	public MailService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
	
	@Async
	@Retryable(
			value = { Exception.class }, 
			maxAttemptsExpression = "#{${mailer.retry.maxAttempts}}", backoff = @Backoff(delayExpression = "#{${mailer.retry.backOffDelay}}"))
	public String sendNotification(Mail mail, File file){
        System.out.println("Sending email...");
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
        	MimeMessageHelper mailMessage = new MimeMessageHelper(message, true);
        	mailMessage.addAttachment(file.getName(), file);
        	mailMessage.setTo(mail.getTo());
        	mailMessage.setFrom(mail.getFrom());
			mailMessage.setSubject(mail.getSubject());
			mailMessage.setText(mail.getBody());
		} catch (Exception e) {
			System.out.println("Going to retry!");
			e.printStackTrace();
		}
		javaMailSender.send(message);
		System.out.println("Email Sent!");
		return "Request Accepted!";
		//TODO how to get acknowlwdgement that it failed/succeeded
	}
}