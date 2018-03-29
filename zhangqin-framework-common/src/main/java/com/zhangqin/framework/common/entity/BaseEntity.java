package com.zhangqin.framework.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * ClassName: BaseEntity 
 * @Description: 实体类基类
 * @author zhangqin
 * @date 2018年1月13日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class BaseEntity implements Serializable {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 5229658478458390243L;
	/**
	 * id
	 */
	private String id;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUserId;
	/**
	 * 最后修改时间
	 */
	private Date updateTime;
	/**
	 * 最后修改人
	 */
	private String updateUserId;
	/**
	 * 是否删除
	 */
	private Boolean isDelete;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseEntity [id=" + id + ", createTime=" + createTime + ", createUserId=" + createUserId
				+ ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", isDelete=" + isDelete + "]";
	}
}
