package com.chao.cloud.micro.consumer.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chao.cloud.common.entity.Response;
import com.chao.cloud.micro.consumer.service.TblService;

@RestController
@Validated
public class TblController {

	@Autowired
	private TblService tblService;

	@RequestMapping("/test")
	public Response<Integer> test(@NotNull Integer userId) {
		return Response.ok(tblService.test(userId));
	}

}