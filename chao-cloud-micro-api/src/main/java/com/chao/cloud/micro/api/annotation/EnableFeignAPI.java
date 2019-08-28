package com.chao.cloud.micro.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import com.chao.cloud.common.extra.feign.annotation.EnableFeign;
import com.chao.cloud.micro.api.provider.ProviderFeignClient;

/**
 * feign 接口
 * @author 薛超
 * @since 2019年8月23日
 * @version 1.0.7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableFeign
@ComponentScan(basePackages = { "com.chao.cloud.micro.api" })
// 每增加一个手动加入-为了方便使用
@EnableFeignClients(clients = { //
		ProviderFeignClient.class, // 提供
})
public @interface EnableFeignAPI {

}
