package com.chao.cloud.micro.api.provider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.extra.feign.annotation.FeignFallback;
import com.chao.cloud.micro.api.annotation.ApplicationConstant;

/**
 * 服务提供者
 * @author 薛超
 * @since 2019年8月23日
 * @version 1.0.7
 */
@FeignClient(ApplicationConstant.CHAO_CLOUD_PROVIDER)
public interface ProviderFeignClient {

	/**
	 * 创建订单
	 * @param userId
	 * @return
	 */
	@FeignFallback
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	Response<Integer> create(@RequestParam("userId") Integer userId);
}
