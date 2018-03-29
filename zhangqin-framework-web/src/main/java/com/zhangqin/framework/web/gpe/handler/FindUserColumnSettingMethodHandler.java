package com.zhangqin.framework.web.gpe.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.common.enums.ResponseCodeEnum;
import com.zhangqin.framework.web.gpe.GpeCacheManager;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.bean.result.UserColumnSetting;
import com.zhangqin.framework.web.gpe.utils.GpeUserSettingUtils;

/**
 * 获取用户列设置
 * @author zhangqin
 *
 */
public class FindUserColumnSettingMethodHandler extends AbstractGpeMethodHandler<ResponseData<UserColumnSetting>> {

	public FindUserColumnSettingMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, String[] paths) {
		super(annotation, mapping, paths);
	}

	@ResponseBody
	@Override
	public ResponseData<UserColumnSetting> handler(HttpServletRequest request, HttpServletResponse response) {
		GpeCacheManager.setMethodGenericInfo(getMethod().toGenericString());
		UserColumnSetting setting = GpeUserSettingUtils.getUserSettingResult(getAnnotation().viewObject(), null);
		return new ResponseData<UserColumnSetting>(ResponseCodeEnum.SUCCESS, setting);
	}

}
