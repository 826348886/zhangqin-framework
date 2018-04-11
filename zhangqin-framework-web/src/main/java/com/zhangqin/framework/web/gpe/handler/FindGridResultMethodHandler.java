package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.common.enums.ResponseCodeEnum;
import com.zhangqin.framework.web.gpe.GpeCacheManager;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.bean.result.GridResult;
import com.zhangqin.framework.web.gpe.utils.GpeGridUtils;

/**
 * findGridResult方法Handler
 * 
 * @author zhangqin
 *
 */
public class FindGridResultMethodHandler extends AbstractGpeMethodHandler<ResponseData<GridResult>> {

	/**
	 * 构造函数
	 * 
	 * @param annotation
	 * @param mapping
	 * @param proxyMethod
	 * @param paths
	 */
	public FindGridResultMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, Method proxyMethod,
			String... paths) {
		super(annotation, mapping, proxyMethod, paths);
	}

	/**
	 * 方法Handler
	 */
	@Override
	@ResponseBody
	public ResponseData<GridResult> handler(HttpServletRequest request, HttpServletResponse response) {
		GpeCacheManager.setMethodGenericInfo(getProxyMethod().toGenericString());
		GridResult result = GpeGridUtils.getGridResult(getAnnotation().viewObject());
		return new ResponseData<GridResult>(ResponseCodeEnum.SUCCESS, result);
	}
}
