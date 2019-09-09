package com.chao.cloud.micro.gateway.oauth2;

import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

@Configuration
@Data
@ConfigurationProperties(prefix = "chao.cloud.oauth2")
public class OAuth2Properties implements InitializingBean {
	/**
	 * 过滤的路径
	 */
	public static final String[] PERMIT_URL = { //
			OAuth2Constant.OAUTH_TOKEN_URL, //
			"/**/health/core", //
			"/**/login/**" };

	private Set<String> antMatchers = CollUtil.newHashSet();

	@Override
	public void afterPropertiesSet() throws Exception {
		// 添加默认匹配
		for (String url : PERMIT_URL) {
			antMatchers.add(url);//
		}
	}

}
