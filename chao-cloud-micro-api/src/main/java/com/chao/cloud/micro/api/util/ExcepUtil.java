package com.chao.cloud.micro.api.util;

import java.io.OutputStream;

import com.chao.cloud.common.entity.Response;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;

/**
 * 异常工具类
 * @author 薛超
 * @since 2019年9月6日
 * @version 1.0.7
 */
public final class ExcepUtil {
	/**
	 * 输出一个response对象
	 * @param out 目标
	 * @param response 输出对象
	 */
	public static void write(OutputStream out, Response<?> response) {
		IoUtil.writeUtf8(out, true, JSONUtil.toJsonStr(response));
	}

}
