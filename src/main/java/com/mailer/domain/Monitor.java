package com.mailer.domain;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Monitor {

    @PostConstruct
    public void init(){
        System.out.println("Monitor started");
    }
}