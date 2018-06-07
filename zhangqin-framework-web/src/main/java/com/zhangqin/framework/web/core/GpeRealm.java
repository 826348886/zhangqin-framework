package com.zhangqin.framework.web.core;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.zhangqin.framework.web.gpe.bean.result.UserColumnSetting;

/**
 * GPE数据获取及持久化接口
 * @author zhangqin
 *
 */
public interface GpeRealm {
	/**
	 * 获取用户列设置对象
	 * @param key 功能唯一标识
	 * @return
	 */
	UserColumnSetting getUserColumnSetting(String key);
	
	/**
	 * 
	 * @param key 功能唯一标识
	 * @param setting 用户列设置对象 
	 * @return
	 */
	boolean saveUserColumnSetting(String key, UserColumnSetting setting);
	
	/**
	 * 重置用户设置信息
	 * @param userId 用户ID
	 * @param key 功能唯一标识
	 * @return
	 */
	boolean restoreUserColumnSetting(String key);
	
	/**
	 * 获取没有权限访问的字段
	 * @param userId
	 * @return
	 */
	Set<String> getForbidFields(String userId);
	
	String getStringFromRedis(String key);
	
	void setStringToRedis(String key, String Json, long timeout, TimeUnit unit);
}
