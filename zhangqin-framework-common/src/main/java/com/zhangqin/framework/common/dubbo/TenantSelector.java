package com.zhangqin.framework.common.dubbo;

/**
 * 租户选择器
 * @author zhangqin
 *
 */
public class TenantSelector {

	/**
	 * 本地租户存储
	 */
	private static final ThreadLocal<String> LOCAL_TENANT = new ThreadLocal<>();

	/**
	 * 设置租户ID
	 * @param tenantId
	 */
	public static void setTenantId(String tenantId) {
		LOCAL_TENANT.set(tenantId);
	}

	/**
	 * 获取租户ID
	 * @return
	 */
	public static String getTenantId() {
		return LOCAL_TENANT.get();
	}

	/**
	 * 移除租户ID
	 */
	public static void remove() {
		LOCAL_TENANT.remove();
	}
}
