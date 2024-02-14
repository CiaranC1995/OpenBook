package com.openBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.openBook.repository")
public class OpenBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenBookApplication.class, args);
	}

}
