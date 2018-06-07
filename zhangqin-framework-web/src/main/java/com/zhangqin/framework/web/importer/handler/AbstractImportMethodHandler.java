package com.zhangqin.framework.web.importer.handler;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.web.core.MethodHandler;
import com.zhangqin.framework.web.importer.annotation.ExcelImport;

/**
 * ImportMethodHandler抽象类
 * @author kun
 *
 * @param <T>
 */
public abstract class AbstractImportMethodHandler<T> implements MethodHandler<T> {
	/**
	 * registerHandlerMethod方法中的method参数
	 * 
	 * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#
	 *      registerHandlerMethod(Object handler, Method method, T mapping)
	 */
	private Method method;
	
	/**
	 * 被代理的方法
	 */
	private Method proxyMethod;
	
	/**
	 * registerHandlerMethod方法中的mapping参数
	 * 
	 * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#
	 *      registerHandlerMethod(Object handler, Method method, T mapping)
	 */
	private RequestMappingInfo mapping;

	/**
	 * GpeRequestMapping注解
	 * 
	 * @see com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping
	 */
	private ExcelImport annotation;
	
	/**
	 * 构造函数
	 * 
	 * @param annotation
	 * @param mapping
	 * @param proxyMethod
	 * @param paths
	 */
	public AbstractImportMethodHandler(ExcelImport annotation, RequestMappingInfo mapping, Method proxyMethod,
			String... paths) {
		this(annotation, mapping, paths);
		this.proxyMethod = proxyMethod;
	}

	/**
	 * 构造函数
	 * 
	 * @param annotation
	 * @param mapping
	 * @param paths
	 */
	public AbstractImportMethodHandler(ExcelImport annotation, RequestMappingInfo mapping, String... paths) {
		// method
		this.method = Arrays.asList(this.getClass().getMethods()).parallelStream().filter(m -> {
			return m.getName().equals("handler");
		}).findFirst().get();

		// mapping
		RequestMappingInfo mappingInfo = RequestMappingInfo.paths(paths).methods(RequestMethod.GET, RequestMethod.POST)
				.build();
		this.mapping = mapping.combine(mappingInfo);

		// annotation
		this.annotation = annotation;
	}

	public Method getMethod() {
		return method;
	}

	public RequestMappingInfo getMapping() {
		return mapping;
	}

	public void setMapping(RequestMappingInfo mapping) {
		this.mapping = mapping;
	}

	public ExcelImport getAnnotation() {
		return annotation;
	}

	public void setAnnotation(ExcelImport annotation) {
		this.annotation = annotation;
	}
	
	public Class<?>[] getJavaClass(){
		return annotation.javaClass();
	}

	public Method getProxyMethod() {
		return proxyMethod;
	}
}
