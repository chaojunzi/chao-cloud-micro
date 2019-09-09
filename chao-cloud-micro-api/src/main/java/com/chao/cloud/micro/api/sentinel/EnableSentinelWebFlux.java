package com.chao.cloud.micro.api.sentinel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * Sentinel alibaba 网关限流 限流
 * @author 薛超
 * @since 2019年9月6日
 * @version 1.0.5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SentinelWebFluxConfig.class)
public @interface EnableSentinelWebFlux {

}
