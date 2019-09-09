package com.chao.cloud.micro.auth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.micro.api.util.ExcepUtil;

/**
 * 资源服务配置
 * @EnableResourceServer 启用资源服务
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.accessDeniedHandler(new AccessDeniedHandler() {// 错误输出
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
					throws IOException, ServletException {
				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				ExcepUtil.write(response.getOutputStream(), Response.error(e.getCause().getMessage()));
			}
		}).authenticationEntryPoint(new AuthenticationEntryPoint() {
			@Override
			public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
					throws IOException, ServletException {// 错误输出
				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				ExcepUtil.write(response.getOutputStream(), Response.error(e.getCause().getMessage()));
			}
		});

	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.requestMatcher(new RequestMatcher() {// 定义OAuth2请求匹配器
			@Override
			public boolean matches(HttpServletRequest request) {
				String auth = request.getHeader("Authorization");
				// 判断来源请求是否包含oauth2授权信息,这里授权信息来源可能是头部的Authorization值以Bearer开头,或者是请求参数中包含access_token参数,满足其中一个则匹配成功
				boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
				boolean haveAccessToken = request.getParameter("access_token") != null;
				return haveOauth2Token || haveAccessToken;
			}
		}).authorizeRequests()//
				.antMatchers(HttpMethod.OPTIONS).permitAll()//
				.anyRequest().authenticated();
	}

}