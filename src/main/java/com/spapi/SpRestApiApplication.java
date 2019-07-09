package com.spapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.spapi" })
@EnableJpaRepositories("com.spapi")
public class SpRestApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SpRestApiApplication.class, args);
	}
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpRestApiApplication.class);
    }
}
