package com.zhangqin.framework.web.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 继承RequestMappingHandlerAdapter
 * @author zhangqin
 *
 */
public class RequestMappingHandlerAdapterPlus extends RequestMappingHandlerAdapter {

	private HandlerMethodArgumentResolverComposite argumentResolvers;
	private HandlerMethodReturnValueHandlerComposite returnValueHandlers;
	private ParameterNameDiscoverer parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
	private Boolean ignoreDefaultModelOnRedirect = false;

	/**
	 * 请求指定的方法
	 * @param request
	 * @param response
	 * @param handlerMethod
	 * @return
	 * @throws Exception
	 */
	public Object invokeForRequest(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod) throws Exception {

		// 初始适配器参数
		initAdapterParams();

		ServletWebRequest webRequest = new ServletWebRequest(request, response);
		WebDataBinderFactory binderFactory = getDataBinderFactory(handlerMethod);
		ModelFactory modelFactory = getModelFactory(handlerMethod, binderFactory);

		ServletInvocableHandlerMethod invocableMethod = createInvocableHandlerMethod(handlerMethod);
		invocableMethod.setHandlerMethodArgumentResolvers(this.argumentResolvers);
		invocableMethod.setHandlerMethodReturnValueHandlers(this.returnValueHandlers);
		invocableMethod.setDataBinderFactory(binderFactory);
		invocableMethod.setParameterNameDiscoverer(this.parameterNameDiscoverer);

		ModelAndViewContainer mavContainer = new ModelAndViewContainer();
		mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
		modelFactory.initModel(webRequest, mavContainer, invocableMethod);
		mavContainer.setIgnoreDefaultModelOnRedirect(this.ignoreDefaultModelOnRedirect);

		return invocableMethod.invokeForRequest(webRequest, mavContainer);
	}

	/**
	 * 初始适配器参数
	 */
	public void initAdapterParams() {
		// 初始化 argumentResolvers
		if (null == argumentResolvers) {
			Field argumentResolversField = ReflectionUtils.findField(this.getClass().getSuperclass(),
					"argumentResolvers");
			argumentResolversField.setAccessible(true);
			argumentResolvers = (HandlerMethodArgumentResolverComposite) ReflectionUtils
					.getField(argumentResolversField, this);
		}

		// 初始化 argumentResolvers
		if (null != returnValueHandlers) {
			Field returnValueHandlersField = ReflectionUtils.findField(this.getClass().getSuperclass(),
					"returnValueHandlers");
			returnValueHandlersField.setAccessible(true);
			returnValueHandlers = (HandlerMethodReturnValueHandlerComposite) ReflectionUtils
					.getField(returnValueHandlersField, this);
		}

		// 初始化 parameterNameDiscoverer
		if (null == parameterNameDiscoverer) {
			Field parameterNameDiscovererField = ReflectionUtils.findField(this.getClass().getSuperclass(),
					"parameterNameDiscoverer");
			parameterNameDiscovererField.setAccessible(true);
			parameterNameDiscoverer = (ParameterNameDiscoverer) ReflectionUtils.getField(parameterNameDiscovererField,
					this);
		}

		// 初始化 ignoreDefaultModelOnRedirect
		if (null == ignoreDefaultModelOnRedirect) {
			Field ignoreDefaultModelOnRedirectField = ReflectionUtils.findField(this.getClass().getSuperclass(),
					"ignoreDefaultModelOnRedirect");
			ignoreDefaultModelOnRedirectField.setAccessible(true);
			ignoreDefaultModelOnRedirect = (Boolean) ReflectionUtils.getField(ignoreDefaultModelOnRedirectField, this);
		}
	}

	/**
	 * 调用父类的私有getDataBinderFactory方法
	 * @param handlerMethod
	 * @return
	 */
	private WebDataBinderFactory getDataBinderFactory(HandlerMethod handlerMethod) {
		Method method = ReflectionUtils.findMethod(this.getClass().getSuperclass(), "getDataBinderFactory",
				HandlerMethod.class);
		method.setAccessible(true);
		return (WebDataBinderFactory) ReflectionUtils.invokeMethod(method, this, handlerMethod);
	}

	/**
	 * 调用父类的私有getModelFactory方法
	 * @param handlerMethod
	 * @param binderFactory
	 * @return
	 */
	private ModelFactory getModelFactory(HandlerMethod handlerMethod, WebDataBinderFactory binderFactory) {
		Method method = ReflectionUtils.findMethod(this.getClass().getSuperclass(), "getModelFactory",
				HandlerMethod.class, WebDataBinderFactory.class);
		method.setAccessible(true);
		return (ModelFactory) ReflectionUtils.invokeMethod(method, this, handlerMethod, binderFactory);
	}
}
