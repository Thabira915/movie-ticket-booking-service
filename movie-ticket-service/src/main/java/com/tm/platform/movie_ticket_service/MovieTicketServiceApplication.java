package com.tm.platform.movie_ticket_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.tm.platform")
@EntityScan(basePackages = "com.tm.platform")
@EnableJpaRepositories(basePackages = "com.tm.platform.repository.mysql")
@EnableMongoRepositories(basePackages = "com.tm.platform.repository.mongodb")
public class MovieTicketServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieTicketServiceApplication.class, args);
	}

}
