package com.spapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.spapi" })
@EnableJpaRepositories("com.spapi")
public class SpRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpRestApiApplication.class, args);
	}
}
