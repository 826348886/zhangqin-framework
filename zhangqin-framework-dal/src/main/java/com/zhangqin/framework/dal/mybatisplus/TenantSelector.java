package com.zhangqin.framework.dal.mybatisplus;

/**
 * 租户选择器
 * 
 * @author zhangqin
 *
 */
public class TenantSelector {
	/**
	 * 本地租户存储
	 */
	private static final ThreadLocal<String> TENANT_THREAD_LOCAL = new ThreadLocal<String>();

	/**
	 * 设置租户ID（或Schema）
	 * 
	 * @param tenantId
	 */
	public static void setTenantId(String tenantId) {
		TENANT_THREAD_LOCAL.set(tenantId);
	}

	/**
	 * 获取租户ID（或Schema）
	 * 
	 * @return
	 */
	public static String getTenantId() {
		return TENANT_THREAD_LOCAL.get();
	}

	/**
	 * 移除租户ID（或Schema）
	 */
	public static void remove() {
		TENANT_THREAD_LOCAL.remove();
	}
}
