package com.zhangqin.framework.gpe.enums;

import com.zhangqin.framework.common.enums.BaseEnum;

/**
 * 
 * ClassName: TextAlign 
 * @Description: 对齐方式枚举
 * @author zhangqin
 * @date 2018年1月15日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public enum TextAlign implements BaseEnum<TextAlign, String> {
	/**
	 * 左对齐
	 */
	LEFT("left", "左对齐"),
	/**
	 * 居中对齐
	 */
	CENTER("center", "居中对齐"),
	/**
	 * 右对齐
	 */
	RIGHT("right", "右对齐"),
	/**
	 * 未设置，使用默认
	 */
	NULL("null", "未设置");
	/**
	 * 
	 * <p>Title: 构造方法</p> 
	 * <p>Description: </p> 
	 * @param value
	 * @param desc
	 */
	TextAlign(String value, String desc) {
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
