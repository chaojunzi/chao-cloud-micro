package com.chao.cloud.micro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ChaoCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaoCloudGatewayApplication.class, args);
	}

}
