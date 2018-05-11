package com.zhangqin.framework.gpe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhangqin.framework.gpe.GpeDefaultPropertyStrategy;
import com.zhangqin.framework.gpe.GpePropertyStrategy;

/**
 * 
 * ClassName: GpeProperty
 * 
 * @Description: 属性注解
 * @author zhangq
 * @date 2017年9月21日
 *
 *       =================================================================================================
 *       Task ID Date Author Description
 *       ----------------+----------------+-------------------+-------------------------------------------
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface GpeProperty {

	/**
	 * 
	 * @Description: 使用哪个默认属性类
	 * @return Class<? extends GpePropertyStrategy>[]
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	Class<? extends GpePropertyStrategy> property() default GpeDefaultPropertyStrategy.class;
}
