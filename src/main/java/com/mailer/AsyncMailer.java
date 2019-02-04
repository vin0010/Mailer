package com.mailer;


import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan("com.mailer.producer.MessageProducer")
public class AsyncMailer {

	public static void main(String[] args) {
		SpringApplication.run(AsyncMailer.class, args);
	}
	
	@PostConstruct
    public void init(){
        System.out.println("Application started succefully!!");
    }
}
