package com.chao.cloud.micro.consumer.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.chao.cloud.common.base.BaseHttpServlet;
import com.chao.cloud.common.entity.Response;
import com.chao.cloud.common.exception.ErrorUtil;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;

/**
 * 限流控制
 * @author 薛超
 * @since 2019年8月30日
 * @version 1.0.7
 */
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.sentinel")
@Data
public class SentinelConfig implements InitializingBean {

	private List<FlowRule> flowRules = new ArrayList<>();// 流控
	private List<DegradeRule> degradeRules = new ArrayList<>();// 降级

	@Override
	public void afterPropertiesSet() throws Exception {
		// 流控-加载规则
		FlowRuleManager.loadRules(null);// 清空
		FlowRuleManager.loadRules(flowRules);
		// 降级-加载规则
		DegradeRuleManager.loadRules(null);// 清空
		DegradeRuleManager.loadRules(degradeRules);
		// 异常返回
		WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());

	}

	public class CustomUrlBlockHandler implements UrlBlockHandler, BaseHttpServlet {

		@Override
		public void blocked(HttpServletRequest request, HttpServletResponse response, BlockException ex)
				throws IOException {
			// 返回限流
			boolean ajax = requestIsAjax(request);
			if (!ajax) {
				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				String result = JSONUtil
						.toJsonStr(Response.error(StrUtil.format("[{}:请求达到上限]", ex.getRule().getResource())));
				IoUtil.writeUtf8(response.getOutputStream(), true, result);
				return;
			}
			ErrorUtil.writeErrorHtml(response, "public/error/403.html");
		}
	}
}