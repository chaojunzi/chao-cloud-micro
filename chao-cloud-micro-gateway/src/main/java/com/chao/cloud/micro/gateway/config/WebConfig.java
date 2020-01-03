package com.chao.cloud.micro.gateway.config;

import java.util.List;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.codec.ServerCodecConfigurer;

import com.chao.cloud.common.web.controller.HealthController;

import cn.hutool.extra.spring.SpringUtil;

@Configuration
public class WebConfig {

	@Bean
	public HealthController healthController() {
		return new HealthController();
	}

	@Bean
	public SpringUtil SpringUtil(ApplicationContext applicationContext) {
		SpringUtil util = new SpringUtil();
		util.setApplicationContext(applicationContext);
		return util;
	}

	@Bean
	@Order(-2)
	public GlobalExceptionHandler globalErrorWebExceptionHandler(SpringUtil springContext,
			ErrorAttributes errorAttributes, ResourceProperties resourceProperties)
			throws NoSuchFieldException, SecurityException {
		ServerCodecConfigurer serverCodecConfigurer = SpringUtil.getBean(ServerCodecConfigurer.class);
		List<HttpMessageReader<?>> readers = serverCodecConfigurer.getReaders();
		List<HttpMessageWriter<?>> writers = serverCodecConfigurer.getWriters();
		GlobalExceptionHandler handler = new GlobalExceptionHandler(//
				errorAttributes, //
				resourceProperties, //
				SpringUtil.getApplicationContext());
		handler.setMessageReaders(readers);
		handler.setMessageWriters(writers);
		return handler;
	}

}
