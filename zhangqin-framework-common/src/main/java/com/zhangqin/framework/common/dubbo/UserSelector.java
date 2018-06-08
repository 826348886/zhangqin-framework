package com.zhangqin.framework.common.dubbo;

/**
 * 用户选择器
 * @author zhangqin
 *
 */
public class UserSelector {

	/**
	 * 本地用户存储
	 */
	private static final ThreadLocal<String> LOCAL_USER = new ThreadLocal<>();

	/**
	 * 设置用户ID
	 * @param tenantId
	 */
	public static void setUserId(String userId) {
		LOCAL_USER.set(userId);
	}

	/**
	 * 获取用户ID
	 * @return
	 */
	public static String getUserId() {
		return LOCAL_USER.get();
	}

	/**
	 * 移除用户ID
	 */
	public static void remove() {
		LOCAL_USER.remove();
	}
}
