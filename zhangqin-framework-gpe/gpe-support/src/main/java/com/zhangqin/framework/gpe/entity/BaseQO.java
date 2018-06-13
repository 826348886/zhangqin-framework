package com.zhangqin.framework.gpe.entity;

import java.io.Serializable;

import org.dozer.Mapping;

/**
 * 查询基类
 * 
 * @author zhangqin
 *
 */
public class BaseQO implements Serializable {
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
	 * 高级查询规则映射列表
	 */
	private NbsRuleMappingList nbsRules = new NbsRuleMappingList();

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

	public NbsRuleMappingList getNbsRules() {
		return nbsRules;
	}

	public void setNbsRules(NbsRuleMappingList nbsRules) {
		this.nbsRules = nbsRules;
	}

}
