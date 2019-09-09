package com.chao.cloud.micro.auth.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.cloud.micro.auth.dal.entity.OauthClientDetails;
import com.chao.cloud.micro.auth.dal.mapper.OauthClientDetailsMapper;
import com.chao.cloud.micro.auth.service.OauthClientDetailsService;

/**
 * @author 薛超
 * @since 2019-09-06
 * @version 1.0.7
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
		implements OauthClientDetailsService {

}
