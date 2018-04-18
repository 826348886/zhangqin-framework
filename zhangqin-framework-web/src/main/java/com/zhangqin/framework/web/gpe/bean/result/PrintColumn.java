package com.zhangqin.framework.web.gpe.bean.result;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 打印列
 * @author zhangqin
 *
 */
@JsonInclude(Include.NON_DEFAULT)
public class PrintColumn implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1301101896652338635L;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 字段名
	 */
	private String field;

	/**
	 * 宽度
	 */
	private int width;

	/**
	 * 行合并
	 */
	private int rowspan;

	/**
	 * 列合并
	 */
	private int colspan;

	/**
	 * 	对齐
	 */
	private String align;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
	
}
