package com.chao.cloud.micro.consumer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chao.cloud.micro.consumer.dal.entity.XcTbl;

/**
 * 
 * @author 薛超
 * @since 2019年8月26日
 * @version 1.0.7
 */
public interface TblService extends IService<XcTbl> {
	/**
	 * 业务测试
	 */
	Integer test(Integer userId);

}
