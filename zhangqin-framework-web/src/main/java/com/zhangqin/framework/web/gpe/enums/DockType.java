package com.zhangqin.framework.web.gpe.enums;

import com.zhangqin.framework.common.enums.BaseEnum;

/**
 * 
 * ClassName: ExtendType 
 * @Description: 扩展坞类型
 * @author zhangqin
 * @date 2017年11月3日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public enum DockType implements BaseEnum<DockType, String> {
	/**
	 * 扩展1
	 */
	DOCK_1("dock_1", "扩展1"),
	/**
	 * 扩展2
	 */
	DOCK_2("dock_2", "扩展2"),
	/**
	 * 扩展3
	 */
	DOCK_3("dock_3", "扩展1"),
	/**
	 * EasyUI formatter扩展
	 * 被该值标记的字段，生成网格时，额外增加formatter方法
	 */
	EASYUI_FORMATTER("easyui_formatter", "EasyUI formatter扩展");
	/**
	 * 
	 * <p>Title: 构造方法</p> 
	 * <p>Description: </p> 
	 * @param value
	 * @param desc
	 */
	DockType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * 枚举编号
	 */
	private String value;
	/**
	 * 枚举描述
	 */
	private String desc;

	/**
	 * (non-Javadoc)
	 * @see com.zhangqin.framework.common.enums.BaseEnum#code()
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * (non-Javadoc)
	 * @see com.zhangqin.framework.common.enums.BaseEnum#desc()
	 */
	@Override
	public String getDesc() {
		return desc;
	}
}
