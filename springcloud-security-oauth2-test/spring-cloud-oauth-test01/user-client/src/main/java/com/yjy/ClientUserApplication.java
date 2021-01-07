package com.yjy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClientUserApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClientUserApplication.class, args);
	}
}
