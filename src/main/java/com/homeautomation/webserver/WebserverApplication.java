package com.homeautomation.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class WebserverApplication {

	@Autowired
	private NodeRepository repository;

	@Autowired
	private UserRepository userRepository;

	// main program
	public static void main(String[] args) {
		SpringApplication.run(WebserverApplication.class, args);
	}

	public void run(String... args) throws Exception {

		repository.deleteAll();
		// save a couple of customers
		repository.save(new Node("bedroom1", "on"));
		repository.save(new Node("bedroom2", "off"));
		repository.save(new Node("garagedoor", "closed"));

		userRepository.deleteAll();
		// save a few example usernames and passwords
		userRepository.save(new User("JimHalpert", "iheartpam"));
		userRepository.save(new User("MichaelScott", "thatswhatshesaid"));
		userRepository.save(new User("DwightSchrute", "beets"));
	}
}
