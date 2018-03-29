package com.zhangqin.framework.web.gpe.bean.result;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 列对象
 * @author zhangqin
 *
 */
@JsonInclude(Include.NON_DEFAULT)
public class ColumnResult implements Serializable{

	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -54028396028258663L;

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

	/**
	 * 是否支持排序
	 */
	private boolean sortable;

	/**
	 * 是否隐藏
	 */
	private boolean hidden;

	/**
	 * 格式化
	 */
	private String format;

	/**
	 * 扩展坞集合
	 */
	private List<String> docks;

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

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public List<String> getDocks() {
		return docks;
	}

	public void setDocks(List<String> docks) {
		this.docks = docks;
	}

	@Override
	public String toString() {
		return "ColumnResult [title=" + title + ", field=" + field + ", width=" + width + ", rowspan=" + rowspan
				+ ", colspan=" + colspan + ", align=" + align + ", sortable=" + sortable + ", hidden=" + hidden
				+ ", format=" + format + ", docks=" + docks + "]";
	}

}
