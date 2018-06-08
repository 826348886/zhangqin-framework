package com.zhangqin.framework.common.html.elements.select;

import java.io.Serializable;
import java.util.List;

/**
 * HTML Select元素
 * @author zhangqin
 *
 */
public class SelectElement<T> implements Serializable {

	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -2833579622731198250L;

	private T selected;

	private List<OptionElement<T>> options;

	public T getSelected() {
		return selected;
	}

	public void setSelected(T selected) {
		this.selected = selected;
	}

	public List<OptionElement<T>> getOptions() {
		return options;
	}

	public void setOptions(List<OptionElement<T>> options) {
		this.options = options;
	}

	@Override
	public String toString() {
		return "EnumResult [selected=" + selected + ", options=" + options + "]";
	}
}