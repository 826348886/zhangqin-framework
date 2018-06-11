package com.zhangqin.framework.common.entity;

import java.util.ArrayList;

import com.zhangqin.framework.common.enums.CompareOperator;

public class RuleMappingList extends ArrayList<RuleMapping> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -785502838839161848L;

    public RuleMappingList add(String field, CompareOperator rule) {
		RuleMapping mapping = new RuleMapping(field, rule);
        super.add(mapping);
        return this;
    }
}
