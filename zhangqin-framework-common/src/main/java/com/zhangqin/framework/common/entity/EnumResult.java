package com.zhangqin.framework.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * ClassName: EnumResult
 * 
 * @Description: 枚举转换对象
 * @author zhangqin
 * @date 2017年12月23日
 *
 *       =================================================================================================
 *       Task ID Date Author Description
 *       ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class EnumResult<T> implements Serializable {

	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -2833579622731198250L;

	private T selected;

	private List<EnumOption<T>> options;

	public T getSelected() {
		return selected;
	}

	public void setSelected(T selected) {
		this.selected = selected;
	}

	public List<EnumOption<T>> getOptions() {
		return options;
	}

	public void setOptions(List<EnumOption<T>> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "EnumResult [selected=" + selected + ", options=" + options + "]";
	}
}