package com.zhangqin.framework.common.html.elements.select;

import java.io.Serializable;

/**
 * HTML Select元素的Option标签
 * @author zhangqin
 *
 * @param <T>
 */
public class OptionElement<T> implements Serializable {

	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -7633329801624623208L;

	/**
	 * 
	 * <p>Title: 构造函数</p>
	 * <p>Description: </p>
	 */
	public OptionElement() {

	}

	/**
	 * 
	 * <p>Title: 构造函数</p> 
	 * <p>Description: </p> 
	 * @param text
	 * @param value
	 */
	public OptionElement(String text, T value) {
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