package com.chao.cloud.micro.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.chao.cloud.common.extra.mybatis.annotation.EnableMybatisPlus;
import com.chao.cloud.common.extra.tx.annotation.EnableTxSeata;
import com.chao.cloud.common.web.annotation.EnableGlobalException;
import com.chao.cloud.common.web.annotation.EnableWeb;

@SpringBootApplication
@EnableDiscoveryClient // 微服务注册配置中心
@EnableWeb // web服务
@EnableGlobalException // 全局异常处理
@EnableMybatisPlus // 数据库服务
@EnableTxSeata // 分布式事务
public class ChaoCloudProviderApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ChaoCloudProviderApplication.class, args);

	}
}
