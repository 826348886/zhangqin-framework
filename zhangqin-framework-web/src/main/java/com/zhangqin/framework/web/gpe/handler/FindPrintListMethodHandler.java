package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.gpe.ParameterRequestWrapper;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.enums.UseFor;
import com.zhangqin.framework.web.gpe.handler.processor.HandlerProcessor;

/**
 * findPrintList方法Handler
 * 
 * @author zhangqin
 *
 */
public class FindPrintListMethodHandler extends AbstractGpeMethodHandler<List<Map<String, Object>>> {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(FindPrintListMethodHandler.class);

	/**
	 * 默认按多少条数据切割请求数据
	 */
	private static final Integer DEFAULT_SPLIT_ROWS = 1000;

	/**
	 * 构造函数
	 * 
	 * @param annotation
	 * @param mapping
	 * @param proxyMethod
	 * @param paths
	 */
	public FindPrintListMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, Method proxyMethod,
			String... paths) {
		super(annotation, mapping, proxyMethod, paths);
	}

	@Override
	@ResponseBody
	public List<Map<String, Object>> handler(HttpServletRequest request, HttpServletResponse response) {
		// 分页参数
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");

		// 组织handler和adapter
		Class<?> targetClass = getProxyMethod().getDeclaringClass();
		Object controller = SpringContextUtils.getBean(targetClass);
		ServletInvocableHandlerMethod handler = new ServletInvocableHandlerMethod(controller, getProxyMethod());
		GpeRequestMappingHandlerAdapter adapter = SpringContextUtils.getBean(GpeRequestMappingHandlerAdapter.class);

		List<Object> list;
		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			// 导出当前页
			list = printCurrentPage(request, response, handler, adapter);
		} else {
			// 导出所有页
			list = printAllPage(request, response, handler, adapter);
		}

		// 无数据时返回
		if(CollectionUtils.isEmpty(list)) {
			return Lists.newArrayList();
		}
		
		return HandlerProcessor.process(getAnnotation().viewObject(), list, UseFor.PRINT);
	}

	/**
	 * 导出全部页
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param adapter
	 */
	private List<Object> printAllPage(HttpServletRequest request, HttpServletResponse response,
			ServletInvocableHandlerMethod handler, GpeRequestMappingHandlerAdapter adapter) {
		try {
			List<Object> list = Lists.newArrayList();
			// 当前页，从第一页开始
			Integer currPage = 1;
			// 循环请求每一页数据
			while (true) {
				HashMap<String, String[]> paramMap = new HashMap<String, String[]>(request.getParameterMap());
				paramMap.put("rows", new String[] { DEFAULT_SPLIT_ROWS.toString() });
				paramMap.put("page", new String[] { currPage.toString() });
				currPage++;

				HttpServletRequest req = (HttpServletRequest) request;
				ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(req, paramMap);
				request = requestWrapper;

				PageInfo<?> page = invoke(request, response, handler, adapter);
				list.addAll(page.getList());

				if (!page.isHasNextPage()) {
					break;
				}
			}
			return list;
		} catch (Exception e) {
			logger.error("打印所有页失败，{}", e);
		}
		return null;
	}

	/**
	 * 导出当前页
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param adapter
	 */
	@SuppressWarnings("unchecked")
	private List<Object> printCurrentPage(HttpServletRequest request, HttpServletResponse response,
			ServletInvocableHandlerMethod handler, GpeRequestMappingHandlerAdapter adapter) {
		try {
			PageInfo<?> page = invoke(request, response, handler, adapter);
			return (List<Object>) page.getList();
		} catch (Exception e) {
			logger.error("打印当前页失败，{}", e);
		}
		return null;
	}

	/**
	 * 调用请求
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param adapter
	 * @return
	 * @throws Exception
	 */
	private PageInfo<?> invoke(HttpServletRequest request, HttpServletResponse response,
			ServletInvocableHandlerMethod handler, GpeRequestMappingHandlerAdapter adapter) throws Exception {
		PageInfo<?> pageInfo = (PageInfo<?>) adapter.invokeForRequest(request, response, handler);
		return pageInfo;
	}

}
