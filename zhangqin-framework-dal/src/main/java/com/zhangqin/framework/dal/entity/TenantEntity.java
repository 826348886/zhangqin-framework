package com.zhangqin.framework.dal.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 多租户实体类基类 用于使用tenant_id字段方式实现多租户的场景，利用数据库schame方式实现的多租户的场景不适用
 * 
 * @author zhangqin
 *
 */
@TableName("sys_tenant")
public class TenantEntity extends BaseEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8812715063331648141L;

	/**
	 * 租户ID
	 */
	@TableField("tenant_id")
	private String tenantId;

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@Override
	public String toString() {
		return super.toString() + ",TenantEntity [tenantId=" + tenantId + "]";
	}

}
