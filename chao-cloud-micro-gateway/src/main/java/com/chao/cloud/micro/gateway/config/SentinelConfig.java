package com.chao.cloud.micro.gateway.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;

/**
 * 
 * @author 薛超
 * @since 2019年8月30日
 * @version 1.0.7
 */
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.sentinel")
@Data
public class SentinelConfig implements InitializingBean {

	private Set<GatewayFlowRule> rules = new HashSet<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		// 清除
		loadGatewayManager(null, null);
		// api规则
		Set<ApiDefinition> definitions = new HashSet<>();
		// 提取 rules 规则
		rules.forEach(r -> {
			definitions.add(new ApiDefinition()//
					.setApiName(r.getResource())// api 名称
					.setPredicateItems(CollUtil.newHashSet(new ApiPathPredicateItem()//
							.setPattern(r.getResource())// 资源路径
							.setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX))));
		});
		loadGatewayManager(definitions, rules);
	}

	private void loadGatewayManager(Set<ApiDefinition> definitions, Set<GatewayFlowRule> rules) throws Exception {
		GatewayApiDefinitionManager.loadApiDefinitions(definitions);
		GatewayRuleManager.loadRules(rules);
	}

}