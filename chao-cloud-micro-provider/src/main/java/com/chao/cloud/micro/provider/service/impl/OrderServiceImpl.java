package com.chao.cloud.micro.provider.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chao.cloud.micro.provider.dal.entity.XcOrder;
import com.chao.cloud.micro.provider.dal.mapper.XcOrderMapper;
import com.chao.cloud.micro.provider.service.OrderService;

/**
 * 
 * @author 薛超
 * @since 2019年8月26日
 * @version 1.0.7
 */
@Service
public class OrderServiceImpl extends ServiceImpl<XcOrderMapper, XcOrder> implements OrderService {

	@Override
	public Integer create(Integer userId) {
		XcOrder order = new XcOrder();
		order.setOrderSn(SNOW_ID.nextIdStr()).setUserId(userId);
		this.save(order);
		return order.getId();
	}

}
