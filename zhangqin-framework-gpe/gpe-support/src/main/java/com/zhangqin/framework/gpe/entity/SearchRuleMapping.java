package com.zhangqin.framework.gpe.entity;

import java.io.Serializable;

import com.zhangqin.framework.common.enums.CompareOperator;
import com.zhangqin.framework.common.utils.StringUtils;

/**
 * 查询规则映射
 * 
 * @author kun
 *
 */
public class SearchRuleMapping implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6669114414301575532L;

	/**
	 * 构造函数
	 */
	public SearchRuleMapping() {

	}

	/**
	 * 构造函数
	 * 
	 * @param field
	 * @param rule
	 */
	public SearchRuleMapping(String field, CompareOperator rule) {
		this.field = field;
		this.rule = rule;
		this.fieldUnderline = StringUtils.camelToUnderline(field);
	}

	/**
	 * 字段
	 */
	private String field;

	/**
	 * 驼峰转下划线字段
	 */
	private String fieldUnderline;

	/**
	 * 运算符
	 */
	private CompareOperator rule;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getFieldUnderline() {
		return fieldUnderline;
	}

	public void setFieldUnderline(String fieldUnderline) {
		this.fieldUnderline = fieldUnderline;
	}

	public CompareOperator getRule() {
		return rule;
	}

	public void setRule(CompareOperator rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "RuleMapping [field=" + field + ", fieldUnderline=" + fieldUnderline + ", rule=" + rule + "]";
	}

}
