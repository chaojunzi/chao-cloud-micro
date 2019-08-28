package com.chao.cloud.micro.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.cloud.micro.provider.dal.entity.XcOrder;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * 
 * @author 薛超
 * @since 2019年8月26日
 * @version 1.0.7
 */
public interface OrderService extends IService<XcOrder> {

	Snowflake SNOW_ID = IdUtil.createSnowflake(0, 0);

	/**
	 * 生成订单
	 * @param userId
	 * @return
	 */
	Integer create(Integer userId);

}
