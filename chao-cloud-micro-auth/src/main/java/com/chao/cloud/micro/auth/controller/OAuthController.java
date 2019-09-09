package com.chao.cloud.micro.auth.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.micro.auth.config.MappingAuthProperties;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;

/**
 *  用户信息控制器
 */
@RequestMapping("/oauth")
@RestController
@Validated
public class OAuthController {

	@Autowired
	private MappingAuthProperties mappingAuthProperties;

	/**
	 * 获取授权用户的信息
	 * @param user 当前用户
	 * @return 授权信息
	 */
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}

	@RequestMapping("/error")
	public Response<String> user(HttpServletRequest request) {
		JSON json = JSONUtil.parse(request.getAttribute("error"));
		if (json != null) {
			return Response.error(json.getByPath("message", String.class));
		}
		return Response.error();
	}

	/**
	 * 身份鉴定
	 * // 请自行开发接口(此为案例)
	 * @param user
	 * @param path 请求路径
	 * @return
	 */
	@RequestMapping("/authentication")
	public Response<String> authentication(OAuth2Authentication user, @NotBlank String path) {
		path = path.replace("/", "@");
		// 校验用户
		StaticLog.info("[user={},authorities={}]", user.getName(), user.getAuthorities());
		// 根据path获取权限
		Map<String, String> mappingAuth = mappingAuthProperties.getMappingAuth();
		if (!mappingAuth.containsKey(path)) {
			return Response.ok(user.getName());
		}
		if (CollUtil.isEmpty(user.getAuthorities())) {
			return Response.error("无访问权限:" + mappingAuth.get(path));
		}
		List<String> authList = user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
		if (authList.contains(mappingAuth.get(path))) {// 匹配成功
			return Response.ok(user.getName());
		}
		return Response.error("无访问权限:" + mappingAuth.get(path));
	}
}