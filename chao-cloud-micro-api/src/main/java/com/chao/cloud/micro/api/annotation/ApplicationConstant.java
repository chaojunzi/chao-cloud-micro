package com.chao.cloud.micro.api.annotation;

/**
 * 微服务常量
 * @author 薛超
 * @since 2019年8月23日
 * @version 1.0.7
 */
public interface ApplicationConstant {

	String CHAO_CLOUD_PREFIX = "chao-cloud-";// 系统前缀
	String CHAO_CLOUD_GATEWAY = CHAO_CLOUD_PREFIX + "gateway";// 网关服务
	String CHAO_CLOUD_PROVIDER = CHAO_CLOUD_PREFIX + "provider";// 服务提供者
	String CHAO_CLOUD_CONSUMER = CHAO_CLOUD_PREFIX + "consumer";// 服务消费者

}
