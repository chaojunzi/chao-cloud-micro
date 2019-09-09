package com.chao.cloud.micro.gateway.oauth2;

import com.chao.cloud.micro.api.annotation.ApplicationConstant;

import cn.hutool.core.util.StrUtil;

public interface OAuth2Constant {

	/**
	 * 默认登录URL
	 * 通过appid secret 获取token
	 */
	String OAUTH_TOKEN_URL = StrUtil.format("/{}/oauth/token", ApplicationConstant.CHAO_CLOUD_AUTH);

	/**
	 * 获取用户路径
	 */
	String OAUTH_USER_URL = StrUtil.format("/{}/oauth/user", ApplicationConstant.CHAO_CLOUD_AUTH);

	/**
	 * grant_type
	 */
	String REFRESH_TOKEN = "refresh_token";

}
