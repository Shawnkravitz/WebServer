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

	public static void main(String[] args) {
		SpringApplication.run(WebserverApplication.class, args);
	}

	public void run(String... args) throws Exception {

	}
}
