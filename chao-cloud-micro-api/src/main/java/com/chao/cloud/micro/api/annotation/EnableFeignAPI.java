package com.chao.cloud.micro.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.cloud.openfeign.EnableFeignClients;

import com.chao.cloud.common.extra.feign.annotation.EnableFeign;

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
@EnableFeignClients(basePackages = "com.chao.cloud.micro.api.feign")
public @interface EnableFeignAPI {

}
