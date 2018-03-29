package com.zhangqin.framework.web.gpe.bean.result;

import java.util.List;

/**
 * 用户设置
 * 
 * @author zhangqin
 *
 */
public class UserColumnSetting {
	/**
	 * 表头标题
	 */
	private String title;
	
	/**
	 * 用户设置明细
	 */
	private List<UserColumnSettingField> fields;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<UserColumnSettingField> getFields() {
		return fields;
	}

	public void setFields(List<UserColumnSettingField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "UserSettingResult [title=" + title + ", fields=" + fields + "]";
	}
	
}
