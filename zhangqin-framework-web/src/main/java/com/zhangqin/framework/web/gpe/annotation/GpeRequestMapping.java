package com.zhangqin.framework.web.gpe.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * GPE方法注解
 * 
 * @author zhangqin
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface GpeRequestMapping {
	/**
	 * 同@RequestMapping
	 * 
	 * @return
	 */
	String[] value() default {};

	/**
	 * 同@RequestMapping
	 * 
	 * @return
	 */
	RequestMethod[] method() default {};

	/**
	 * 需解析的实体类Class
	 * 
	 * @return
	 */
	Class<?> viewObject();
}
