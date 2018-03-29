package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;

/**
 * GpeMethodHandler抽象类
 * 
 * @author zhangqin
 *
 */
public abstract class AbstractGpeMethodHandler<T> implements GpeMethodHandler<T> {
	/**
	 * registerHandlerMethod方法中的method参数
	 * 
	 * @see org.springframework.web.servlet.handler.AbstractHandlerMethodMapping#
	 *      registerHandlerMethod(Object handler, Method method, T mapping)
	 */
	private Method method;
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
	private GpeRequestMapping annotation;

	/**
	 * 构造函数
	 * 
	 * @param annotation
	 * @param mapping
	 * @param paths
	 */
	public AbstractGpeMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, String... paths) {
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

	public void setMethod(Method method) {
		this.method = method;
	}

	public RequestMappingInfo getMapping() {
		return mapping;
	}

	public void setMapping(RequestMappingInfo mapping) {
		this.mapping = mapping;
	}

	public GpeRequestMapping getAnnotation() {
		return annotation;
	}

	public void setAnnotation(GpeRequestMapping annotation) {
		this.annotation = annotation;
	}
	
	public Class<?> getViewObject(){
		return annotation.viewObject();
	}

}
