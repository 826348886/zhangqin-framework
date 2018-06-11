package com.zhangqin.framework.common.entity;

import java.io.Serializable;

import com.zhangqin.framework.common.enums.CompareOperator;

/**
 * 规则映射
 * 
 * @author kun
 *
 */
public class RuleMapping implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6669114414301575532L;
	
	/**
	 * 构造函数
	 */
	public RuleMapping() {
		
	}
	
	/**
	 * 构造函数
	 * @param field
	 * @param rule
	 */
	public RuleMapping(String field, CompareOperator rule) {
		this.field = field;
		this.rule = rule;
	}
	
	/**
	 * 字段
	 */
	private String field;
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

	public CompareOperator getRule() {
		return rule;
	}

	public void setRule(CompareOperator rule) {
		this.rule = rule;
	}

	@Override
	public String toString() {
		return "RuleMapping [field=" + field + ", rule=" + rule + "]";
	}

}
