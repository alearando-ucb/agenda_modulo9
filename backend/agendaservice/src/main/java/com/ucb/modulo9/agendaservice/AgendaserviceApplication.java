package com.ucb.modulo9.agendaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AgendaserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgendaserviceApplication.class, args);
	}

}
