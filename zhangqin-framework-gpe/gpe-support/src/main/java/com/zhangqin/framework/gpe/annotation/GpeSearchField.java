package com.zhangqin.framework.gpe.annotation;

/**
 * 
 * @author zhangqin
 *
 */
public @interface GpeSearchField {
	/**
	 * 字段标题
	 * @return
	 */
	String title() default "";
	
	/**
	 * 选择器
	 * @return
	 */
	String selector() default "";
}
