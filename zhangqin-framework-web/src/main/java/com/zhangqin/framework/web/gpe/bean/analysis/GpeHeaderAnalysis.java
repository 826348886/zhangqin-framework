package com.zhangqin.framework.web.gpe.bean.analysis;

/**
 * @GpeHeader注解解析类
 * @author zhangqin
 *
 */
public class GpeHeaderAnalysis {
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

	/**
	 * 是否解析所有的字段
	 */
	private boolean all;

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

	public boolean isAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

}
