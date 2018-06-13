package com.zhangqin.framework.gpe.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 高级查询字段注解
 * @author kun
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NbsField {
	/**
	 * 字段标题
	 * @return
	 */
	String title();
	
	/**
	 * 列名，数据库字段名
	 * @return
	 */
	String column() default "";
	
	/**
	 * 表别名
	 * @return
	 */
	String tableAlias() default "";
}
