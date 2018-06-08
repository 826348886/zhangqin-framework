package com.zhangqin.framework.web.importer.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.web.importer.annotation.ExcelImport;

/**
 * 注册方法链
 * 
 * @author kun
 *
 */
public class ExcelImportMethodHandlerChainConfig {
	/**
	 * 构建处理链
	 * @param annotation
	 * @param mapping
	 * @param method
	 * @return
	 */
	public static List<AbstractImportMethodHandler<?>> buildHandlerChain(ExcelImport annotation,
			RequestMappingInfo mapping, Method method) {
		List<AbstractImportMethodHandler<?>> chainList = Arrays.asList(new AbstractImportMethodHandler[] {
			// 注册template方法
			new ExportExcelTemplateMethodHandler(annotation, mapping, "template"),
			// 注册error方法
			new ExportErrorExcelMethodHandler(annotation, mapping, "error")
		});
		return chainList;
	}
}
