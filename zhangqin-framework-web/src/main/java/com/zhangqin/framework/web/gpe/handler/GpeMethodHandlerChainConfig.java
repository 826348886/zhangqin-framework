package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;

/**
 * 注册方法链
 * @author zhangqin
 *
 */
public class GpeMethodHandlerChainConfig {
	public static List<AbstractGpeMethodHandler<?>> buildHandlerChain(GpeRequestMapping annotation, RequestMappingInfo mapping,Method method) {

		List<AbstractGpeMethodHandler<?>> chainList = Arrays.asList(new AbstractGpeMethodHandler[] {
			// 注册findGridResult方法
			new FindGridResultMethodHandler(annotation, mapping, method, "gpe/findGridResult"),
			// 注册findListPage方法替代原来的查询分页方法
			new FindListPageMethodHandler(annotation, mapping, method, "gpe/findListPage"),

			// 注册findColSetting方法
			new FindUserColumnSettingMethodHandler(annotation, mapping, method, "gpe/findColumnSetting"),
			// 注册saveColumnSetting方法
			new SaveUserColumnSettingMethodHandler(annotation, mapping, method, "gpe/saveColumnSetting"),
			// 注册restoreUserColumnSetting方法
			new RestoreUserColumnSettingMethodHandler(annotation, mapping, method, "gpe/restoreColumnSetting"),
			
			// 注册findPrintHeader方法，查询打印表头
			new FindPrintHeaderMethodHandler(annotation, mapping, method, "gpe/findPrintHeader"),
			// 注册findPrintHeader方法，查询打印列表数据
			new FindPrintListMethodHandler(annotation, mapping, method, "gpe/findPrintList"),
			
			// 注册export方法
			new ExportMethodHandler(annotation, mapping, new String[] { "gpe/export" }, method)
			
			// 查询自定义查询列表 gpe/findAdvancedSearchList
			// 查询高级查询字段列表  gpe/findAdvancedSearchFieldList
			
			// 保存自定义查询条件 gpe/saveAdvancedSearch
			// 删除自定义查询条件 gpe/deleteAdvancedSearch
		});
		return chainList;
	}
}
