package com.zhangqin.framework.web.gpe.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * 打印表头
 * 
 * @author zhangqin
 *
 */
public class PrintHeader implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1776659740754546951L;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 普通列二维集合
	 */
	private List<List<PrintColumn>> columns;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<List<PrintColumn>> getColumns() {
		return columns;
	}

	public void setColumns(List<List<PrintColumn>> columns) {
		this.columns = columns;
	}

}
