package com.mailer;


import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class AsyncMailer {

	public static void main(String[] args) {
		SpringApplication.run(AsyncMailer.class, args);
		//TODO delete starter
//		Starter starter = new Starter(context);
//		starter.start();
	}
	
	@PostConstruct
    public void init(){
        System.out.println("coming inside post construct started");
    }
}
