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
import com.zhangqin.framework.web.gpe.bean.result.PrintHeader;
import com.zhangqin.framework.web.gpe.utils.GpePrintUtils;

/**
 * findPrintHeader方法Handler
 * 
 * @author zhangqin
 *
 */
public class FindPrintHeaderMethodHandler extends AbstractGpeMethodHandler<ResponseData<PrintHeader>> {

	/**
	 * 构造函数
	 * 
	 * @param annotation
	 * @param mapping
	 * @param proxyMethod
	 * @param paths
	 */
	public FindPrintHeaderMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, Method proxyMethod,
			String... paths) {
		super(annotation, mapping, proxyMethod, paths);
	}

	@Override
	@ResponseBody
	public ResponseData<PrintHeader> handler(HttpServletRequest request, HttpServletResponse response) {
		// 设置代理方法的基本信息，用于唯一定位当前功能路径
		GpeCacheManager.setMethodGenericInfo(getProxyMethod().toGenericString());

		// 获取打印表头信息
		PrintHeader header = GpePrintUtils.getPrintHeader(getAnnotation().viewObject());

		// 返回结果
		return new ResponseData<PrintHeader>(ResponseCodeEnum.SUCCESS, header);
	}

}
