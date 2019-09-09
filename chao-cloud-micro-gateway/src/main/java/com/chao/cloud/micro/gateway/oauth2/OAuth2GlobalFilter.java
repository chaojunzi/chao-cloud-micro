package com.chao.cloud.micro.gateway.oauth2;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.micro.api.feign.OAuthFeignClient;
import com.chao.cloud.micro.api.util.PathUtil;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 全局拦截器，作用所有的微服务
 * 若想使用oauth2进行接口权限控制 请解开下方注释 @Component
 * @author 薛超
 * @since 2019年9月6日
 * @version 1.0.7
 */
// @Component
@Slf4j
public class OAuth2GlobalFilter implements GlobalFilter, Ordered {

	@Autowired
	private OAuthFeignClient authFeignClient;
	@Autowired
	private OAuth2Properties oauth2Properties;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// 过滤路径
		ServerHttpRequest request = exchange.getRequest();
		String path = FileUtil.normalize(request.getPath().value());
		// 不拦截
		if (this.isPermit(path)) {
			return chain.filter(exchange);
		}
		// 判断请求头是否含有参数
		try {
			String access_token = getAccessToken(request);
			String body = authFeignClient.authentication(access_token, path).getBody();
			log.info("[authentication body={},access_token={}, path={}]", body, access_token, path);
			return chain.filter(exchange);
		} catch (Exception e) {
			// 返回错误信息
			ServerHttpResponse response = exchange.getResponse();
			DataBuffer buffer = response.bufferFactory()
					.wrap(JSONUtil.toJsonStr(Response.error(e.getMessage())).getBytes());
			response.setStatusCode(HttpStatus.OK);
			response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
			return response.writeWith(Mono.just(buffer));
		}
	}

	private String getAccessToken(ServerHttpRequest request) {
		String auth = request.getHeaders().getFirst("Authorization");
		if (StrUtil.isNotBlank(auth) && auth.startsWith("Bearer")) {
			return StrUtil.removePrefix(auth, "Bearer ");
		}
		auth = request.getQueryParams().getFirst("access_token");
		Assert.notBlank(auth, "access_token not empty");
		return auth;
	}

	/**
	 * 向下执行
	 * @param path 请求路径
	 * @return 匹配结果
	 */
	boolean isPermit(String path) {
		Set<String> antMatchers = oauth2Properties.getAntMatchers();
		for (String match : antMatchers) {
			if (PathUtil.isMatch(match, path)) {
				return true;
			}
		}
		return false;
	};

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}
}