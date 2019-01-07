package com.systems.community.carpooling.southpoolsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableJpaRepositories
@ComponentScan(basePackages = { "com.systems.community.carpooling.southpoolsecurity" })
@EntityScan(basePackages = { "com.systems.community.carpooling.southpoolsecurity.entities" })
public class SouthpoolsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SouthpoolsecurityApplication.class, args);
	}

}

