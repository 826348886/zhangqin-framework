package com.zhangqin.framework.gpe.enums;

import com.zhangqin.framework.common.enums.BaseEnum;

/**
 * 
 * ClassName: BoolValue 
 * @Description: 布尔值枚举
 * @author zhangqin
 * @date 2018年1月15日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public enum BoolValue implements BaseEnum<TextAlign, Boolean> {
	/**
	 * false
	 */
	FALSE(false, "否"),
	/**
	 * true
	 */
	TRUE(true, "true"),
	/**
	 * null
	 */
	NULL(null, "未设置");
	/**
	 * 
	 * <p>Title: 构造方法</p> 
	 * <p>Description: </p> 
	 * @param value
	 * @param desc
	 */
	BoolValue(Boolean value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * 枚举编号
	 */
	private Boolean value;
	/**
	 * 枚举描述
	 */
	private String desc;

	/**
	 * (non-Javadoc)
	 * @see com.zhangqin.framework.common.enums.BaseEnum#code()
	 */
	@Override
	public Boolean getValue() {
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
	
	/**
	 * 
	 * @Description: 枚举转换为Boolean对象
	 * @return Boolean  
	 * @author zhangqin
	 * @date 2017年9月27日
	 */
	public Boolean convertToBoolean() {
		Boolean bool = null;
		switch (this.name().toUpperCase()) {
			case "FALSE":
				bool = false;
				break;
			case "TRUE":
				bool = true;
				break;
			case "NULL":
				bool = null;
				break;
			default:
				bool = null;
				break;
		}
		return bool;
	}
}
