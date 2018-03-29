package com.zhangqin.framework.web.gpe.bean.result;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 用户设置明细
 * 
 * @author zhangqin
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserColumnSettingField implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6593923599459538020L;
	/**
	 * 字段名称
	 */
	private String field;
	/**
	 * 字段标题
	 */
	private String title;
	/**
	 * 是否必须
	 */
	private boolean must;
	/**
	 * 是否显示
	 */
	private boolean gshow;
	/**
	 * 是否打印
	 */
	private boolean pshow;
	/**
	 * 是否导出
	 */
	private boolean eshow;
	/**
	 * 宽度
	 */
	private int width;

	/**
	 * 排序
	 */
	private int sort;
	/**
	 * 是否冻结列
	 */
	private boolean frozen;
	/**
	 * 列合并标题
	 */
	private String ctitle;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isMust() {
		return must;
	}

	public void setMust(boolean must) {
		this.must = must;
	}

	public boolean isGshow() {
		return gshow;
	}

	public void setGshow(boolean gshow) {
		this.gshow = gshow;
	}

	public boolean isPshow() {
		return pshow;
	}

	public void setPshow(boolean pshow) {
		this.pshow = pshow;
	}

	public boolean isEshow() {
		return eshow;
	}

	public void setEshow(boolean eshow) {
		this.eshow = eshow;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public String getCtitle() {
		return ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	@Override
	public String toString() {
		return "UserSettingFieldResult [field=" + field + ", title=" + title + ", must=" + must + ", gshow=" + gshow
				+ ", pshow=" + pshow + ", eshow=" + eshow + ", width=" + width + ", sort=" + sort
				+ ", frozen=" + frozen + ", ctitle=" + ctitle + "]";
	}

}
