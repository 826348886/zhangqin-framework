package com.zhangqin.framework.common.enums;

/**
 * 比较运算符
 * 
 * @author kun
 *
 */
public enum CompareOperator implements BaseEnum<CompareOperator, String> {
	/**
	 * 等于
	 */
	EQ("EQ", "等于"),
	/**
	 * 不等于
	 */
	NE("NE", "不等于"),
	/**
	 * 大于
	 */
	GT("GT", "大于"),
	/**
	 * 大于或等于
	 */
	GE("GE", "大于或等于"),
	/**
	 * 小于
	 */
	LT("LT", "小于"),
	/**
	 * 小于或等于
	 */
	LE("LE", "小于或等于"),
	/**
	 * 包含
	 */
	LK("LK", "包含"),
	/**
	 * 不包含
	 */
	NL("NL", "不包含");

	/**
	 * 枚举编号
	 */
	private String value;
	/**
	 * 枚举描述
	 */
	private String desc;

	/**
	 * 构造函数
	 * 
	 * @param value
	 * @param desc
	 */
	CompareOperator(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getDesc() {
		return desc;
	}

}
