package com.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.dataflow.server.EnableDataFlowServer;

@EnableDataFlowServer
@EnableAutoConfiguration
@SpringBootApplication
public class LocalDataflowServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalDataflowServerApplication.class, args);
	}
}
