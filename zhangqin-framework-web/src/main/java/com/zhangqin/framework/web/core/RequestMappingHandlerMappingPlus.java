package com.zhangqin.framework.web.core;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.handler.AbstractGpeMethodHandler;
import com.zhangqin.framework.web.gpe.handler.GpeMethodHandlerChainConfig;
import com.zhangqin.framework.web.importer.annotation.ExcelImport;
import com.zhangqin.framework.web.importer.handler.AbstractImportMethodHandler;
import com.zhangqin.framework.web.importer.handler.ExcelImportMethodHandlerChainConfig;

/**
 * RequestMappingHandlerMapping 扩展
 * 
 * @author kun
 *
 */
public class RequestMappingHandlerMappingPlus extends RequestMappingHandlerMapping {

	/*
	 * 
	 * 重写AbstractHandlerMethodMapping的detectHandlerMethods方法，增加注册GPE方法处理
	 * 
	 * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#
	 * detectHandlerMethods(java.lang.Object)
	 */
	@Override
	protected void detectHandlerMethods(Object handler) {
		Class<?> handlerType = (handler instanceof String ? getApplicationContext().getType((String) handler)
				: handler.getClass());
		final Class<?> userType = ClassUtils.getUserClass(handlerType);

		Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(userType,
				new MethodIntrospector.MetadataLookup<RequestMappingInfo>() {
					@Override
					public RequestMappingInfo inspect(Method method) {
						try {
							return getMappingForMethod(method, userType);
						} catch (Throwable ex) {
							throw new IllegalStateException(
									"Invalid mapping on handler class [" + userType.getName() + "]: " + method, ex);
						}
					}
				});

		if (logger.isDebugEnabled()) {
			logger.debug(methods.size() + " request handler methods found on " + userType + ": " + methods);
		}
		for (Map.Entry<Method, RequestMappingInfo> entry : methods.entrySet()) {
			Method invocableMethod = AopUtils.selectInvocableMethod(entry.getKey(), userType);
			RequestMappingInfo mapping = entry.getValue();
			registerHandlerMethod(handler, invocableMethod, mapping);
			// 注册GPE方法
			registerGpeHandlerMethods(invocableMethod, mapping);
			// 注册Excel导入方法
			registerExcelImportHandlerMethods(invocableMethod, mapping);
		}
		// super.detectHandlerMethods(handler);
	}

	/**
	 * 注册GPE方法
	 * 
	 * @param method
	 * @param mapping
	 */
	protected void registerGpeHandlerMethods(Method method, RequestMappingInfo mapping) {
		// 是否标记有GpeRequestMapping注解
		if (mapping == null || !AnnotatedElementUtils.hasAnnotation(method, GpeRequestMapping.class)) {
			return;
		}

		// 获取GpeRequestMapping注解
		GpeRequestMapping annotation = method.getAnnotation(GpeRequestMapping.class);

		List<AbstractGpeMethodHandler<?>> handlerList = GpeMethodHandlerChainConfig.buildHandlerChain(annotation,
				mapping, method);
		handlerList.forEach(action -> {
			registerHandlerMethod(action, action.getMethod(), action.getMapping());
		});
	}

	/**
	 * 注册Excel导出方法
	 * 
	 * @param method
	 * @param mapping
	 */
	protected void registerExcelImportHandlerMethods(Method method, RequestMappingInfo mapping) {
		// 是否标记有ExcelImport注解
		if (mapping == null || !AnnotatedElementUtils.hasAnnotation(method, ExcelImport.class)) {
			return;
		}

		// 获取GpeRequestMapping注解
		ExcelImport annotation = method.getAnnotation(ExcelImport.class);
		
		List<AbstractImportMethodHandler<?>> handlerList = ExcelImportMethodHandlerChainConfig
				.buildHandlerChain(annotation, mapping, method);
		handlerList.forEach(action -> {
			registerHandlerMethod(action, action.getMethod(), action.getMapping());
		});
	}

}
