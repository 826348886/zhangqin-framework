package com.zhangqin.framework.web.gpe.handler.search;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.handler.AbstractGpeMethodHandler;

/**
 * 获取查询规则列表Handler
 * @author zhangqin
 *
 */
public class GetSearchRulesMethodHandler extends AbstractGpeMethodHandler<ResponseData<List<SearchRuleListVo>>>  {

	public GetSearchRulesMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, Method proxyMethod,
			String[] paths) {
		super(annotation, mapping, proxyMethod, paths);
	}

	@Override
	public ResponseData<List<SearchRuleListVo>> handler(HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}
	
}
