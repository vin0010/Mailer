package com.mailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import com.mailer.domain.Message;
import com.mailer.producer.MessageProducer;

public class Starter {

	private ConfigurableApplicationContext context;
	
	@Autowired
	private MessageProducer messageProducer;

	public Starter(ConfigurableApplicationContext context) {
		this.context = context;
	}

	public void start() {
		System.out.println("--->" + messageProducer);
		MessageProducer messageProducer1 = new MessageProducer();
		System.out.println("===>"+messageProducer1);
//		MessageListener listener = context.getBean(MessageListener.class);
		MessageProducer messageProducer = context.getBean(MessageProducer.class);
		Message m = new Message();
		messageProducer.sendMessage(m);
//		messageProducer.sendMessage("hi");

	}
}
