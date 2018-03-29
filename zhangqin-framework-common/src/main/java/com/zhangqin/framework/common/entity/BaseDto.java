package com.zhangqin.framework.common.entity;

/**
 * 
 * ClassName: BaseDto 
 * @Description: DTO基类
 * @author zhangqin
 * @date 2018年1月13日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class BaseDto extends BaseEntity {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 2997654673274632929L;
	/**
	 * 创建人
	 */
	private String createUserName;
	/**
	 * 修改人
	 */
	private String updateUserName;

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	/**
	 * (non-Javadoc)
	 * @see com.zhangqin.framework.common.entity.BaseEntity#toString()
	 */
	@Override
	public String toString() {
		return "BaseDto [createUserName=" + createUserName + ", updateUserName=" + updateUserName + "]";
	}
}