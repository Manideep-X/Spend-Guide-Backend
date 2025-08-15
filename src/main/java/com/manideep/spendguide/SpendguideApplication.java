package com.manideep.spendguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SpendguideApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpendguideApplication.class, args);
	}

}
