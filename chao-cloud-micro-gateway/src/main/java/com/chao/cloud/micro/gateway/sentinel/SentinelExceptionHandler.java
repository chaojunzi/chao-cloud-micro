package com.chao.cloud.micro.gateway.sentinel;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.chao.cloud.common.entity.Response;

import cn.hutool.json.JSONUtil;
import reactor.core.publisher.Mono;

/**
 * 限流异常处理
 * @author 薛超
 * @since 2019年8月30日
 * @version 1.0.7
 */
public class SentinelExceptionHandler implements WebExceptionHandler {

	private final String errorMsg = "限流了";

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		// This exception handler only handles rejection by Sentinel.
		if (!BlockException.isBlockException(ex)) {
			return Mono.error(ex);
		}
		return handleBlockedRequest(exchange, ex).flatMap(response -> writeResponse(response, exchange));
	}

	private Mono<ServerResponse> handleBlockedRequest(ServerWebExchange exchange, Throwable throwable) {
		return GatewayCallbackManager.getBlockHandler().handleRequest(exchange, throwable);
	}

	private Mono<Void> writeResponse(ServerResponse response, ServerWebExchange exchange) {
		ServerHttpResponse serverHttpResponse = exchange.getResponse();
		serverHttpResponse.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
		DataBuffer buffer = serverHttpResponse.bufferFactory()
				.wrap(JSONUtil.toJsonStr(Response.error(errorMsg)).getBytes());
		return serverHttpResponse.writeWith(Mono.just(buffer));
	}

}
