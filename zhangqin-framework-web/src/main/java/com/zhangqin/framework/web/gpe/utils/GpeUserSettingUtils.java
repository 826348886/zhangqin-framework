package com.zhangqin.framework.web.gpe.utils;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.result.UserColumnSettingField;
import com.zhangqin.framework.web.gpe.bean.result.UserColumnSetting;

/**
 * 用户自定义设置工具类
 * @author zhangqin
 *
 */
public class GpeUserSettingUtils {
	
	/**
	 * 
	 * <p>Title: 私有构造函数</p> 
	 * <p>Description: 成员方法均为静态，禁止new对象</p>
	 */
	private GpeUserSettingUtils() {
	}
	
	public static UserColumnSetting getUserSettingResult(Class<?> clazz, Set<String> forbidSet) {
		// 获取Gpe对象
		GpeBean gpe = GpeUtils.getGpeBean(clazz, forbidSet);
		
		// 用户设置
		UserColumnSetting setting = new UserColumnSetting();
		BeanMapper.copySkipNullAndEmpty(gpe, setting);
		
		// 用户设置明细
		List<UserColumnSettingField> fields = Lists.newArrayList();
		gpe.getFields().forEach(action->{
			UserColumnSettingField field = BeanMapper.map(action, UserColumnSettingField.class);
			field.setWidth(action.getGwidth());
			fields.add(field);
		});
		setting.setFields(fields);
		
		return setting;
	}
	
	
}
