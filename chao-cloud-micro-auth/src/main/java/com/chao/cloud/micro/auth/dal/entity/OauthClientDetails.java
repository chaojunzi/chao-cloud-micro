package com.chao.cloud.micro.auth.dal.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 薛超
 * @since 2019-09-06
 * @version 1.0.7
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class OauthClientDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clientId;

	private String clientSecret;

	private String authorities;

	private String authorizedGrantTypes;

	private String scope;

	private String resourceIds;

	private String webServerRedirectUri;

	private Integer accessTokenValidity;

	private Integer refreshTokenValidity;

	private String additionalInformation;

	private String autoapprove;

}
