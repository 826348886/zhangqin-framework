package com.zhangqin.framework.common.entity;

import java.io.Serializable;

/**
 * 
 * ClassName: EnumOption 
 * @Description: 枚举项
 * @author zhangqin
 * @date 2017年12月23日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class EnumOption<T> implements Serializable {

	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -7633329801624623208L;

	/**
	 * 
	 * <p>Title: 构造函数</p>
	 * <p>Description: </p>
	 */
	public EnumOption() {

	}

	/**
	 * 
	 * <p>Title: 构造函数</p> 
	 * <p>Description: </p> 
	 * @param text
	 * @param value
	 */
	public EnumOption(String text, T value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * 文本
	 */
	private String text;

	/**
	 * 值
	 */
	private T value;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "EnumOption [text=" + text + ", value=" + value + "]";
	}

}