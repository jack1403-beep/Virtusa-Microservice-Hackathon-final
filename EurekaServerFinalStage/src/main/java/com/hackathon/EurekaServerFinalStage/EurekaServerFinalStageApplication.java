package com.hackathon.EurekaServerFinalStage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerFinalStageApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerFinalStageApplication.class, args);
	}

}
