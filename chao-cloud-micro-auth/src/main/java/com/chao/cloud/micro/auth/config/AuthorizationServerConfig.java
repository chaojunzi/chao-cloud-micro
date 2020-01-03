package com.chao.cloud.micro.auth.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.alibaba.druid.pool.DruidDataSource;
import com.chao.cloud.micro.auth.service.XcUserService;

/**
 * http://localhost:8003/oauth/token?grant_type=password&client_id=client_1&username=user&client_secret=123456&scope=all&password=1
 * 
 * @author 薛超
 * @since 2019年9月4日
 * @version 1.0.7
 */
// 配置授权中心信息
@Configuration
@EnableAuthorizationServer // 开启认证授权中心
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter implements InitializingBean {

	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private XcUserService userDetailsService;

	private AuthenticationManager authenticationManager;

	@Autowired
	private DruidDataSource dataSource;
	@Autowired
	private TokenStore tokenStore;

	@Bean
	public TokenStore tokenStore(DruidDataSource dataSource) {
		return new JdbcTokenStore(dataSource); /// 使用Jdbctoken store
	}

	// 添加商户信息
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.tokenStore(tokenStore)//
				.authenticationManager(authenticationManager)//
				.userDetailsService(userDetailsService)// 必须设置
				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		// 允许表单认证
		oauthServer.allowFormAuthenticationForClients();
		// 允许check_token访问
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.authenticationManager = new AuthenticationManager() {
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return authenticationProvider.authenticate(authentication);
			}
		};
	}

}
