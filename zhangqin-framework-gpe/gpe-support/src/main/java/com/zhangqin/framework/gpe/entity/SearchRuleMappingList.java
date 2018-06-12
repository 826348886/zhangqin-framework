package com.zhangqin.framework.gpe.entity;

import java.util.ArrayList;

import com.zhangqin.framework.common.enums.CompareOperator;

/**
 * 查询规则映射列表
 * 
 * @author kun
 *
 */
public class SearchRuleMappingList extends ArrayList<SearchRuleMapping> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -785502838839161848L;

	public SearchRuleMappingList add(String field, CompareOperator rule) {
		SearchRuleMapping mapping = new SearchRuleMapping(field, rule);
		super.add(mapping);
		return this;
	}
	
}
