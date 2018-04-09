package com.zhangqin.framework.web.gpe.bean;

import java.io.Serializable;

import com.zhangqin.framework.web.gpe.bean.analysis.GpeFieldAnalysis;

/**
 * 
 * ClassName: GpeFieldBean
 * 
 * @Description: Gpe字段实体类
 * @author zhangqin
 * @date 2017年12月14日
 *
 *       =================================================================================================
 *       Task ID Date Author Description
 *       ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class GpeFieldBean extends GpeFieldAnalysis implements Serializable, Comparable<GpeFieldBean> {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 2895632231269171295L;

	/**
	 * 行合并数
	 */
	private int rowspan;
	/**
	 * 列合并数
	 */
	private int colspan;

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}

	public int getColspan() {
		return colspan;
	}

	public void setColspan(int colspan) {
		this.colspan = colspan;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GpeFieldBean bean) {
		int sort = bean.getSort();
		if (getSort() > sort) {
			return 1;
		} else if (sort == getSort()) {
			return 0;
		} else {
			return -1;
		}
	}
}