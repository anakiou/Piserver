package com.anakiou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PiserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiserverApplication.class, args);
	}
}
