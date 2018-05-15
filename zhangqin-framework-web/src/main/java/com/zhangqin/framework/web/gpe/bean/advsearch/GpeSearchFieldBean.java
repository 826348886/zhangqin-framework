package com.zhangqin.framework.web.gpe.bean.advsearch;

import java.io.Serializable;
import java.util.List;

/**
 * 高级查询字段实体类
 * 
 * @author zhangqin
 *
 */
public class GpeSearchFieldBean implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7981260335432387877L;

	/**
	 * 字段标题
	 */
	private String title;

	/**
	 * 字段名
	 */
	private String field;

	/**
	 * 关系运算符
	 */
	private List<String> operators;

	/**
	 * 选择器
	 */
	private String selector;

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

	public List<String> getOperators() {
		return operators;
	}

	public void setOperators(List<String> operators) {
		this.operators = operators;
	}

	public String getSelector() {
		return selector;
	}

	public void setSelector(String selector) {
		this.selector = selector;
	}

}
