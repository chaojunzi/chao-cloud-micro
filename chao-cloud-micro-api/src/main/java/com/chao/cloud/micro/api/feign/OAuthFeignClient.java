package com.chao.cloud.micro.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.extra.feign.annotation.FeignFallback;
import com.chao.cloud.micro.api.annotation.ApplicationConstant;

/**
 * 授权
 * @author 薛超
 * @since 2019年9月6日
 * @version 1.0.7
 */
@FeignClient(ApplicationConstant.CHAO_CLOUD_AUTH)
public interface OAuthFeignClient {

	/**
	 * 身份鉴定
	 * @param access_token
	 * @return
	 */
	@FeignFallback
	@RequestMapping(value = "/oauth/authentication", method = RequestMethod.GET)
	Response<String> authentication(@RequestParam("access_token") String access_token,
			@RequestParam("path") String path);

	@FeignFallback
	@RequestMapping(value = "/health/core", method = RequestMethod.GET)
	Response<String> health();
}
