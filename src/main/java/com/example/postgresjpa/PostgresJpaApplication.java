package com.example.postgresjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PostgresJpaApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostgresJpaApplication.class, args);
	}
}
