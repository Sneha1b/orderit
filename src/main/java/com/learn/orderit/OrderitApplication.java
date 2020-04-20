package com.learn.orderit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OrderitApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderitApplication.class, args);
	}

}
