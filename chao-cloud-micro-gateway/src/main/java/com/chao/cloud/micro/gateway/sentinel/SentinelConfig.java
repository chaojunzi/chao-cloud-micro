package com.chao.cloud.micro.gateway.sentinel;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
import com.alibaba.csp.sentinel.log.LogBase;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.Data;

/**
 * 
 * @author 薛超
 * @since 2019年8月30日
 * @version 1.0.7
 */
@Configuration
@ConfigurationProperties(prefix = "chao.cloud.sentinel")
@Data
public class SentinelConfig implements InitializingBean {

	private Set<GatewayFlowRule> rules = new HashSet<>();

	private String logDir;

	@PostConstruct
	public void initLog() {
		// 加载日志路径
		if (StrUtil.isBlank(logDir)) {
			// 临时目录
			logDir = SystemUtil.getUserInfo().getTempDir();
		}
		System.setProperty(LogBase.LOG_DIR, logDir);
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public SentinelExceptionHandler sentinelExceptionHandler() {
		return new SentinelExceptionHandler();
	}

	@Bean
	@Order(-1)
	public GlobalFilter sentinelGatewayFilter() {
		return new SentinelGatewayFilter();
	}

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