package com.zhangqin.framework.web.gpe.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import com.zhangqin.framework.common.entity.ResponseData;
import com.zhangqin.framework.common.enums.ResponseCodeEnum;
import com.zhangqin.framework.common.utils.JsonMapper;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.gpe.GpeRealm;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.bean.result.UserColumnSetting;

/**
 * 保存用户列设置Handler
 * @author zhangqin
 *
 */
public class SaveUserColumnSettingMethodHandler extends AbstractGpeMethodHandler<ResponseData<Boolean>> {

	public SaveUserColumnSettingMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, String[] paths) {
		super(annotation, mapping, paths);
	}

	@Override
	public ResponseData<Boolean> handler(HttpServletRequest request, HttpServletResponse response) {
		// GPE数据获取及持久化接口
		GpeRealm realm = SpringContextUtils.getBean(GpeRealm.class);
		
		// 获取当前标记方法的信息作为key
		String key = getMethod().toGenericString();
		
		// 获取列设置
		String value = request.getAttribute("").toString();
		UserColumnSetting setting = JsonMapper.fromJson(value, UserColumnSetting.class);
		
		// 保存用户列设置
		boolean success = realm.saveUserColumnSetting(key, setting);
		if(success) {
			return ResponseData.builder(null, ResponseCodeEnum.SUCCESS);
		}else {
			return ResponseData.builder(null, ResponseCodeEnum.FAIL);
		}
	}

}
