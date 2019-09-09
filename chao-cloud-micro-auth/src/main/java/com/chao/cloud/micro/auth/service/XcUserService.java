package com.chao.cloud.micro.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.cloud.micro.auth.dal.entity.XcUser;

/**
 * @author 薛超
 * @since 2019-09-06
 * @version 1.0.7
 */
public interface XcUserService extends UserDetailsService, IService<XcUser> {

}
