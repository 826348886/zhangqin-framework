package com.zhangqin.framework.web.gpe.enums;

import com.zhangqin.framework.common.enums.BaseEnum;

/**
 * 用途枚举
 * @author zhangqin
 *
 */
public enum UseFor implements BaseEnum<UseFor, Integer> {
	/**
	 * 全部，不做任何过滤
	 */
	ALL(0, "全部"),
	/**
	 * 网格
	 */
	GRID(1, "网格"),
	/**
	 * 导出
	 */
	EXPORT(2, "导出"),
	/**
	 * 打印
	 */
	PRINT(3, "打印"),
	/**
	 * 列设置
	 */
	SETTING(4, "列设置");

	/**
	 * 
	 * <p>
	 * Title: 构造方法
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param value
	 * @param desc
	 */
	UseFor(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * 枚举编号
	 */
	private Integer value;
	/**
	 * 枚举描述
	 */
	private String desc;

	@Override
	public Integer getValue() {
		return value;
	}

	@Override
	public String getDesc() {
		return desc;
	}

}
