package com.zhangqin.framework.web.common.utils;

import java.io.Serializable;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

import com.zhangqin.framework.web.common.auth.UserInfo;

/**
 * 用户工具类
 * 
 * @author zhangqin
 *
 */
public class UserUtils {
	/**
	 * 系统机器人用户
	 */
	private static UserInfo robotUser = new UserInfo();

	/**
	 * 当前用户Session存储的Key
	 */
	private static final String SESSION_USER_KEY = "session_user";

	/**
	 * 初始化机器人用户
	 */
	static {
		robotUser.setId("0");
		robotUser.setLoginName("robot");
		robotUser.setUserName("系统");
	}

	/**
	 * 获取当前用户Session
	 * 
	 * @return
	 */
	public static Session getSession() {
		Session session = SecurityUtils.getSubject().getSession();
		return session;
	}

	/**
	 * 获取当前用户对象
	 * 
	 * @return user
	 */
	public static UserInfo getCurrentUser() {
		Session session = SecurityUtils.getSubject().getSession();
		if (null != session) {
			return (UserInfo) session.getAttribute(SESSION_USER_KEY);
		} else {
			return null;
		}
	}

	/**
	 * 获取当前用户相关对象
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T getCurrentUserInfo(String key) {
		Session session = SecurityUtils.getSubject().getSession();
		if (null != session) {
			return (T) session.getAttribute(key);
		} else {
			return null;
		}
	}
	
	/**
	 * 获取机器人用户
	 * @return
	 */
	public static UserInfo getRobotUser() {
		return robotUser;
	}
}
