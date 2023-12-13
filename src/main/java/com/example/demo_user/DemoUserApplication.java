package com.example.demo_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class DemoUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoUserApplication.class, args);
	}

}
