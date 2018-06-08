package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.core.RequestMappingHandlerAdapterPlus;
import com.zhangqin.framework.web.gpe.ParameterRequestWrapper;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.utils.GpeExportUtils;

/**
 * export方法Handler
 * 
 * @author zhangqin
 *
 */
public class ExportMethodHandler extends AbstractGpeMethodHandler<ResponseData<Boolean>> {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ExportMethodHandler.class);

	/**
	 * 目标方法
	 */
	private Method targetMethod;

	/**
	 * 默认按多少条数据切割请求数据
	 */
	private static final Integer DEFAULT_SPLIT_ROWS = 1000;

	/**
	 * 构造方法
	 * 
	 * @param annotation
	 * @param mapping
	 * @param paths
	 * @param targetMethod
	 */
	public ExportMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, String[] paths,
			Method targetMethod) {
		super(annotation, mapping, paths);
		this.targetMethod = targetMethod;
	}

	@Override
	@ResponseBody
	public ResponseData<Boolean> handler(HttpServletRequest request, HttpServletResponse response) {
		// 分页参数
		String page = request.getParameter("page");
		String rows = request.getParameter("rows");

		// 组织handler和adapter
		Class<?> targetClass = this.targetMethod.getDeclaringClass();
		Object controller = SpringContextUtils.getBean(targetClass);
		ServletInvocableHandlerMethod handler = new ServletInvocableHandlerMethod(controller, targetMethod);
		RequestMappingHandlerAdapterPlus adapter = SpringContextUtils.getBean(RequestMappingHandlerAdapterPlus.class);

		if (StringUtils.isNotBlank(page) && StringUtils.isNotBlank(rows)) {
			// 导出当前页
			exportCurrentPage(request, response, handler, adapter);
		} else {
			// 导出所有页
			exportAllPage(request, response, handler, adapter);
		}

		return null;
	}

	/**
	 * 导出全部页
	 * @param request
	 * @param response
	 * @param handler
	 * @param adapter
	 */
	private void exportAllPage(HttpServletRequest request, HttpServletResponse response,
			ServletInvocableHandlerMethod handler, RequestMappingHandlerAdapterPlus adapter) {
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
			GpeExportUtils.export(getViewObject(), list, response);
		} catch (Exception e) {
			logger.error("导出所有页失败，{}", e);
		}
	}

	/**
	 * 导出当前页
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param adapter
	 */
	private void exportCurrentPage(HttpServletRequest request, HttpServletResponse response,
			ServletInvocableHandlerMethod handler, RequestMappingHandlerAdapterPlus adapter) {
		try {
			PageInfo<?> page = invoke(request, response, handler, adapter);
			GpeExportUtils.export(getViewObject(), page.getList(), response);
		} catch (Exception e) {
			logger.error("导出当前页失败，{}", e);
		}
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
			ServletInvocableHandlerMethod handler, RequestMappingHandlerAdapterPlus adapter) throws Exception {
		PageInfo<?> pageInfo = (PageInfo<?>) adapter.invokeForRequest(request, response, handler);
		return pageInfo;
	}

}
