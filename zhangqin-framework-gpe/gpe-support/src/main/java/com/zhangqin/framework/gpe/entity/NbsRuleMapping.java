package com.zhangqin.framework.gpe.entity;

import java.io.Serializable;

import com.zhangqin.framework.common.enums.CompareOperator;

/**
 * 高级查询规则映射
 * 
 * @author kun
 *
 */
public class NbsRuleMapping implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6669114414301575532L;

	/**
	 * 构造函数
	 */
	public NbsRuleMapping() {

	}

	/**
	 * 构造函数
	 * 
	 * @param field
	 * @param rule
	 */
	public NbsRuleMapping(String property, CompareOperator operator) {
		this.property = property;
		this.operator = operator;
	}

	/**
	 * 属性名，实体类字段名
	 */
	private String property;

	/**
	 * 运算符
	 */
	private CompareOperator operator;

	/**
	 * 列名，数据库字段名
	 */
	private String column;

	/**
	 * 表别名
	 */
	private String tableAlias;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public CompareOperator getOperator() {
		return operator;
	}

	public void setOperator(CompareOperator operator) {
		this.operator = operator;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	@Override
	public String toString() {
		return "SearchRuleMapping [property=" + property + ", operator=" + operator + ", column=" + column
				+ ", tableAlias=" + tableAlias + "]";
	}

}
