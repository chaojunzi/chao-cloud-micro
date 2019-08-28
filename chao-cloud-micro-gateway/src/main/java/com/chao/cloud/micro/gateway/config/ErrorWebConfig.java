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

@Configuration
public class ErrorWebConfig {

	@Bean
	public SpringContextUtil springContextUtil(ApplicationContext applicationContext) {
		SpringContextUtil util = new SpringContextUtil();
		util.setApplicationContext(applicationContext);
		return util;
	}

	@Bean
	@Order(-2)
	public GlobalErrorWebExceptionHandler globalErrorWebExceptionHandler(SpringContextUtil springContext,
			ErrorAttributes errorAttributes, ResourceProperties resourceProperties)
			throws NoSuchFieldException, SecurityException {
		ServerCodecConfigurer serverCodecConfigurer = SpringContextUtil.getBean(ServerCodecConfigurer.class);
		List<HttpMessageReader<?>> readers = serverCodecConfigurer.getReaders();
		List<HttpMessageWriter<?>> writers = serverCodecConfigurer.getWriters();
		GlobalErrorWebExceptionHandler handler = new GlobalErrorWebExceptionHandler(//
				errorAttributes, //
				resourceProperties, //
				SpringContextUtil.getApplicationContext());
		handler.setMessageReaders(readers);
		handler.setMessageWriters(writers);
		return handler;

	}

}
