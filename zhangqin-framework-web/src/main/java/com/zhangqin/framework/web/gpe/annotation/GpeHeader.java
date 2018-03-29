package com.zhangqin.framework.web.gpe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * ClassName: GpeHeader 
 * @Description: 表头注解
 * @author zhangqin
 * @date 2017年9月22日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface GpeHeader {
	/**
	 * 
	 * @Description: 表头标题
	 * @return String  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	String title() default "";

	/**
	 * 
	 * @Description: 网格是否显示标题
	 * @return boolean  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	boolean gshow() default true;

	/**
	 * 
	 * @Description: 是否打印标题
	 * @return boolean  
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	boolean pshow() default true;

	/**
	 * 
	 * @Description: 是否导出标题
	 * @return boolean 
	 * @author zhangqin
	 * @date 2017年9月21日
	 */
	boolean eshow() default true;
}