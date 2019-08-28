package com.chao.cloud.micro.gateway.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.chao.cloud.common.constant.ResultCodeEnum;
import com.chao.cloud.common.entity.Response;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler {

	public GlobalErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
			ApplicationContext applicationContext) {
		super(errorAttributes, resourceProperties, applicationContext);
	}

	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
	}

	/**
	 * 错误处理
	 * @param request
	 * @return
	 */
	private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

		Map<String, Object> body = getErrorAttributes(request, false);
		// 错误打印
		Throwable error = getError(request);
		if (!"/favicon.ico".equals(request.path())) {
			log.error("[gateway:访问异常=>{}]", error);
			log.error("[error-param:{}]", request.queryParams());
			log.error("[error-path:{}]", request.path());
			log.error("[error-uri:{}]", request.uri());
			log.error("[error-body:{}]", body);
		}
		// 返回
		Response<Object> response = Response.result(ResultCodeEnum.CODE_500.code(), body.get("error").toString());
		response.setBody(error.getMessage());
		return ServerResponse.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(BodyInserters.fromObject(response));
	}

}