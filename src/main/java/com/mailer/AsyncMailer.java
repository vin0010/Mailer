package com.mailer;


import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsyncMailer {

	public static void main(String[] args) {
		SpringApplication.run(AsyncMailer.class, args);
	}
	
	@PostConstruct
    public void init(){
        System.out.println("Application started succefully!!");
    }
}
