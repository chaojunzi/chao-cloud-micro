package com.chao.cloud.micro.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.cloud.micro.auth.dal.entity.XcUser;
import com.chao.cloud.micro.auth.dal.mapper.XcUserMapper;
import com.chao.cloud.micro.auth.service.XcUserService;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author 薛超
 * @since 2019-09-06
 * @version 1.0.7
 */
@Service
public class XcUserServiceImpl extends ServiceImpl<XcUserMapper, XcUser> implements XcUserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		XcUser user = this.baseMapper.selectOne(Wrappers.<XcUser>lambdaQuery().eq(XcUser::getUsername, username));
		if (BeanUtil.isEmpty(user)) {
			throw new UsernameNotFoundException("无效的用户 null");
		}
		// 请自行配置
		return User.withUsername(username)//
				.password(passwordEncoder.encode(user.getPassword()))//
				.build();
	}

}
