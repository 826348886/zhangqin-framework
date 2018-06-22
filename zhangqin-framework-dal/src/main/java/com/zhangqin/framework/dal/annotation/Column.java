package com.zhangqin.framework.dal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列注解
 * @author zhangqin
 *
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
	/**
	 * 数据库列名
	 * @return
	 */
	String value();
}
