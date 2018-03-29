package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;

/**
 * 处理链配置
 * @author zhangqin
 *
 */
public class MethodHandlerChainConfig {
	public static List<AbstractGpeMethodHandler<?>> buildHandlerChain(GpeRequestMapping annotation, RequestMappingInfo mapping,Method method) {

		List<AbstractGpeMethodHandler<?>> chainList = Arrays.asList(new AbstractGpeMethodHandler[] {
			// 注册findGridResult方法
			new FindGridResultMethodHandler(annotation, mapping, new String[] { "gpe/findGridResult" }),
			// 注册findListPage方法替代原来的查询分页方法
			new FindListPageMethodHandler(annotation, mapping, new String[] { "gpe/findListPage" }, method),
			
			// 注册findColSetting方法
			new FindUserColumnSettingMethodHandler(annotation, mapping, new String[] { "gpe/findColumnSetting" }),
			// 注册saveColumnSetting方法
			new SaveUserColumnSettingMethodHandler(annotation, mapping, new String[] { "gpe/saveColumnSetting" }),
			// 注册restoreUserColumnSetting方法
			new RestoreUserColumnSettingMethodHandler(annotation, mapping, new String[] { "gpe/restoreColumnSetting" })
		});
		return chainList;
	}
}
