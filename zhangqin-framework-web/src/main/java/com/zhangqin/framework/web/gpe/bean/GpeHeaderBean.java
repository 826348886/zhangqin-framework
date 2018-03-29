package com.zhangqin.framework.web.gpe.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: GpeHeaderBean 
 * @Description: 解析 @GpeHeader 注解对应的实体类对象
 * @author zhangqin
 * @date 2017年12月15日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class GpeHeaderBean implements Serializable {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -73100968575688170L;
	/**
	 * 表头标题
	 */
	private String title;
	/**
	 * 网格是否显示标题
	 */
	private boolean gshow;
	/**
	 * 是否打印标题
	 */
	private boolean pshow;
	/**
	 * 是否导出标题
	 */
	private boolean eshow;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isGshow() {
		return gshow;
	}

	public void setGshow(boolean gshow) {
		this.gshow = gshow;
	}

	public boolean isPshow() {
		return pshow;
	}

	public void setPshow(boolean pshow) {
		this.pshow = pshow;
	}

	public boolean isEshow() {
		return eshow;
	}

	public void setEshow(boolean eshow) {
		this.eshow = eshow;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GpeHeaderBean [title=" + title + ", gshow=" + gshow + ", pshow="
				+ pshow + ", eshow=" + eshow + "]";
	}
}