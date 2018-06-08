package com.zhangqin.framework.web.importer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

import com.zhangqin.framework.web.importer.ExcelImportTemplate;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface ExcelImport {
	/**
	 * 需解析的实体类Class
	 * 
	 * @return
	 */
	Class<? extends ExcelImportTemplate> [] javaClass();
	
	/**
	 * 标题
	 * @return
	 */
	String title();
}
