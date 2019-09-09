package com.chao.cloud.micro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.chao.cloud.micro.api.annotation.EnableFeignAPI;
import com.chao.cloud.micro.api.sentinel.EnableSentinelWebFlux;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignAPI // feign
@EnableSentinelWebFlux
public class ChaoCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaoCloudGatewayApplication.class, args);
	}

}
