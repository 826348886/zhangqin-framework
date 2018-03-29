package com.zhangqin.framework.web.common.auth;

import java.io.Serializable;

/**
 * 用户基本信息
 * 
 * @author zhangqin
 *
 */
public class UserInfo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6134614621090403630L;
	/**
	 * 用户ID
	 */
	private String id;
	/**
	 * 登录账号
	 */
	private String loginName;
	/**
	 * 用户姓名
	 */
	private String userName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "UserInfo [id=" + id + ", loginName=" + loginName + ", userName=" + userName + "]";
	}

}
