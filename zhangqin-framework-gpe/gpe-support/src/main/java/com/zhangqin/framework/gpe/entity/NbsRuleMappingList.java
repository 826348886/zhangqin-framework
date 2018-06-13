package com.zhangqin.framework.gpe.entity;

import java.util.ArrayList;

import com.zhangqin.framework.common.enums.CompareOperator;

/**
 * 高级查询规则映射列表
 * 
 * @author kun
 *
 */
public class NbsRuleMappingList extends ArrayList<NbsRuleMapping> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -785502838839161848L;

	public NbsRuleMappingList add(String field, CompareOperator rule) {
		NbsRuleMapping mapping = new NbsRuleMapping(field, rule);
		super.add(mapping);
		return this;
	}
	
}
