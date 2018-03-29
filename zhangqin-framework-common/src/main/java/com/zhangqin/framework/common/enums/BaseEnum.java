package com.zhangqin.framework.common.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * ClassName: BaseEnum
 * 
 * @Description: 枚举基类接口
 * @author zhangqin
 * @date 2017年12月23日
 *
 * =================================================================================================
 * Task ID Date Author Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
@JsonSerialize(using = BaseEnumSerializer.class)
public interface BaseEnum<E extends Enum<?>, T> {
	/**
	 * 
	 * @Description: 枚举编码
	 * @return String
	 * @author zhangqin
	 * @date 2017年12月23日
	 */
	T getValue();

	/**
	 * 
	 * @Description: 枚举描述
	 * @return String
	 * @author zhangqin
	 * @date 2017年12月23日
	 */
	String getDesc();
}
