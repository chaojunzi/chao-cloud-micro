package com.chao.cloud.micro.auth.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * 权限映射
 * @author 薛超
 * @since 2019年9月6日
 * @version 1.0.7
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "chao.cloud.oauth2")
public class MappingAuthProperties {
	/**
	 * 路径->权限
	 */
	private Map<String, String> mappingAuth;

}
