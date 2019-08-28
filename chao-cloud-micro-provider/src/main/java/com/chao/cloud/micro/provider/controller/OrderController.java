package com.chao.cloud.micro.provider.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.micro.provider.service.OrderService;

@RestController
@Validated
public class OrderController {
	@Autowired
	private OrderService orderService;

	/**
	 * 生成一个订单
	 * @param name
	 * @return
	 */
	@RequestMapping("/create")
	public Response<Integer> create(@NotNull Integer userId, HttpServletRequest request) {
		Integer orderId = orderService.create(userId);
		return Response.ok(orderId);
	}
}