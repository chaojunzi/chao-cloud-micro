package com.chao.cloud.micro.consumer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.cloud.micro.api.provider.ProviderFeignClient;
import com.chao.cloud.micro.consumer.dal.entity.XcTbl;
import com.chao.cloud.micro.consumer.dal.mapper.XcTblMapper;
import com.chao.cloud.micro.consumer.service.TblService;

import cn.hutool.core.lang.Assert;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * 
 * @author 薛超
 * @since 2019年8月26日
 * @version 1.0.7
 */
@Service

public class TblServiceImpl extends ServiceImpl<XcTblMapper, XcTbl> implements TblService {
	@Autowired
	private ProviderFeignClient providerFeignClient;

	@Override
	@GlobalTransactional // 全局事务
	@Transactional
	public Integer test(Integer userId) {
		// 增加一条业务数据
		XcTbl tbl = new XcTbl();
		tbl.setUserId(userId);
		tbl.setMoney(100);
		this.save(tbl);
		Integer body = providerFeignClient.create(userId).getBody();// 生成订单
		Assert.state(body < 10);
		return body;
	}

}
