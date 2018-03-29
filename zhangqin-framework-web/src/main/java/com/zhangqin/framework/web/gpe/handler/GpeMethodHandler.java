package com.zhangqin.framework.web.gpe.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GpeMethodHandler接口
 * 
 * @author zhangqin
 *
 */
public interface GpeMethodHandler<T> {
	/**
	 * 方法handler
	 * 
	 * @param args
	 * @return
	 */
	T handler(HttpServletRequest request, HttpServletResponse response);
}
