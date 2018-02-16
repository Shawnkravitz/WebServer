package com.homeautomation.webserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableAutoConfiguration
@Component
public class WebserverApplication implements CommandLineRunner {

	@Autowired
	private NodeRepository repository;

	@Autowired
	private UserRepository userRepository;

	// main program
	public static void main(String[] args) {
		SpringApplication.run(WebserverApplication.class, args);
	}

	public void run(String... args) throws Exception {


		// delete all users and nodes
		this.repository.deleteAll();
		this.userRepository.deleteAll();

		// save a couple of customers
		this.repository.save(new Node("bedroom1", "true", "bedroom light"));
		this.repository.save(new Node("bedroom2", "false", "second bedroom"));
		this.repository.save(new Node("garagedoor", "true", "garage door"));


		// save a few example usernames and passwords
		this.userRepository.save(new User("JimHalpert", "iheartpam"));
		this.userRepository.save(new User("MichaelScott", "thatswhatshesaid"));
		this.userRepository.save(new User("DwightSchrute", "beets"));
	}
}
