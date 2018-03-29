package com.zhangqin.framework.web.gpe.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.common.enums.ResponseCodeEnum;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.gpe.GpeRealm;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;

/**
 * 重置用户设置Handler
 * 
 * @author zhangqin
 *
 */
public class RestoreUserColumnSettingMethodHandler extends AbstractGpeMethodHandler<ResponseData<Boolean>> {

	public RestoreUserColumnSettingMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping,
			String[] paths) {
		super(annotation, mapping, paths);
	}

	@Override
	public ResponseData<Boolean> handler(HttpServletRequest request, HttpServletResponse response) {
		// GPE数据获取及持久化接口
		GpeRealm realm = SpringContextUtils.getBean(GpeRealm.class);

		// 获取当前标记方法的信息作为key
		String key = getMethod().toGenericString();

		// 重置用户列设置
		boolean success = realm.restoreUserColumnSetting(key);
		if (success) {
			return ResponseData.builder(null, ResponseCodeEnum.SUCCESS);
		} else {
			return ResponseData.builder(null, ResponseCodeEnum.FAIL);
		}
	}

}
