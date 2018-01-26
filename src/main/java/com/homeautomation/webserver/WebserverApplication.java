package com.homeautomation.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebserverApplication {

	@Autowired
	private NodeRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(WebserverApplication.class, args);
	}

	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		repository.save(new Node("bedroom1", "on"));
		repository.save(new Node("bedroom2", "off"));
		repository.save(new Node("garagedoor", "closed"));

	}
}
