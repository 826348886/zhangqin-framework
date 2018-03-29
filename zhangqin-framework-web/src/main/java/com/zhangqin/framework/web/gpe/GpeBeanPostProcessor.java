package com.zhangqin.framework.web.gpe;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.config.BeanExpressionResolver;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.Ordered;

public class GpeBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware {

	/**
	 * BeanFactory
	 */
	@SuppressWarnings("unused")
	private BeanFactory beanFactory;
	/**
	 * BeanExpressionContext
	 */
	@SuppressWarnings("unused")
	private BeanExpressionContext expressionContext;
	/**
	 * BeanExpressionResolver
	 */
	@SuppressWarnings("unused")
	private BeanExpressionResolver resolver = new StandardBeanExpressionResolver();

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
		if (beanFactory instanceof ConfigurableListableBeanFactory) {
			this.resolver = ((ConfigurableListableBeanFactory) beanFactory).getBeanExpressionResolver();
			this.expressionContext = new BeanExpressionContext((ConfigurableListableBeanFactory) beanFactory, null);
		}
	}

	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		
		return null;
	}

}
