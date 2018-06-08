package com.zhangqin.framework.common.qo;

import java.io.Serializable;

import org.dozer.Mapping;

import com.zhangqin.framework.common.entity.RuleMapping;

/**
 * 查询基类
 * 
 * @author zhangqin
 *
 */
public class BaseQo implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2526753001931056270L;

	/**
	 * 页码
	 */
	@Mapping("pageNum")
	private int page;

	/**
	 * 每页显示数量
	 */
	@Mapping("pageSize")
	private int rows;

	/**
	 * 规则映射列表
	 */
	private RuleMapping[] rules;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public RuleMapping[] getRules() {
		return rules;
	}

	public void setRules(RuleMapping[] rules) {
		this.rules = rules;
	}

}
