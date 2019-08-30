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

import com.chao.cloud.common.core.SpringContextUtil;
import com.chao.cloud.common.web.HealthController;

@Configuration
public class WebConfig {

	@Bean
	public HealthController healthController() {
		return new HealthController();
	}

	@Bean
	public SpringContextUtil springContextUtil(ApplicationContext applicationContext) {
		SpringContextUtil util = new SpringContextUtil();
		util.setApplicationContext(applicationContext);
		return util;
	}

	@Bean
	@Order(-2)
	public GlobalExceptionHandler globalErrorWebExceptionHandler(SpringContextUtil springContext,
			ErrorAttributes errorAttributes, ResourceProperties resourceProperties)
			throws NoSuchFieldException, SecurityException {
		ServerCodecConfigurer serverCodecConfigurer = SpringContextUtil.getBean(ServerCodecConfigurer.class);
		List<HttpMessageReader<?>> readers = serverCodecConfigurer.getReaders();
		List<HttpMessageWriter<?>> writers = serverCodecConfigurer.getWriters();
		GlobalExceptionHandler handler = new GlobalExceptionHandler(//
				errorAttributes, //
				resourceProperties, //
				SpringContextUtil.getApplicationContext());
		handler.setMessageReaders(readers);
		handler.setMessageWriters(writers);
		return handler;
	}

}
