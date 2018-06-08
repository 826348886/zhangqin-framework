package com.zhangqin.framework.web.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * GpeMethodHandler接口
 * 
 * @author zhangqin
 *
 */
public interface MethodHandler<T> {
	/**
	 * 方法handler
	 * 
	 * @param args
	 * @return
	 */
	T handler(HttpServletRequest request, HttpServletResponse response);
}
