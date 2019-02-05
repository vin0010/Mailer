package com.mailer.springmail;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author Vinoth.Gopu
 * 
 * Configuration class for spring-mail
 */
@Configuration
public class MailConfig {
	@Value(value = "${spring.mail.username}")
	private String username;
	
	@Value(value = "${spring.mail.password}")
	private String password;
	
	@Value(value = "${spring.mail.port}")
	private Integer port;
	
	@Value(value = "${spring.mail.host}")
	private String host;
	
	/**
	 * @return mail sender
	 */
	@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        return mailSender;
    }
}
