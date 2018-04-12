package com.zhangqin.framework.web.common.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户名-密码-验证码校验
 * 
 * @author zhangqin
 *
 */
public class TenantUsernamePasswordCaptchaToken extends UsernamePasswordToken {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3705789999489310595L;

	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 租户编号
	 */
	private String tenant;

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

}
