package com.zhangqin.framework.web.gpe.bean;

import java.io.Serializable;

import com.zhangqin.framework.web.gpe.enums.TextAlign;

/**
 * 字段属性配置Bean
 * 
 * @author zhangqin
 *
 */
public class GpeFieldPropertyBean implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3608552618694576734L;

	/**
	 * 字段名
	 */
	private String field;

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 是否隐藏
	 */
	private boolean hidden;

	/**
	 * 文本对齐方式
	 */
	private TextAlign align;

	/**
	 * 网格是否显示
	 */
	private Boolean gshow;

	/**
	 * 打印是否显示
	 */
	private Boolean pshow;

	/**
	 * 导出是否显示
	 */
	private Boolean eshow;

	/**
	 * 网格格式化
	 */
	private String gformat;

	/**
	 * 打印格式化
	 */
	private String pformat;

	/**
	 * 导出格式化
	 */
	private String eformat;

	/**
	 * 宽度
	 */
	private Integer width;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public TextAlign getAlign() {
		return align;
	}

	public void setAlign(TextAlign align) {
		this.align = align;
	}

	public Boolean getGshow() {
		return gshow;
	}

	public void setGshow(Boolean gshow) {
		this.gshow = gshow;
	}

	public Boolean getPshow() {
		return pshow;
	}

	public void setPshow(Boolean pshow) {
		this.pshow = pshow;
	}

	public Boolean getEshow() {
		return eshow;
	}

	public void setEshow(Boolean eshow) {
		this.eshow = eshow;
	}

	public String getGformat() {
		return gformat;
	}

	public void setGformat(String gformat) {
		this.gformat = gformat;
	}

	public String getPformat() {
		return pformat;
	}

	public void setPformat(String pformat) {
		this.pformat = pformat;
	}

	public String getEformat() {
		return eformat;
	}

	public void setEformat(String eformat) {
		this.eformat = eformat;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Override
	public String toString() {
		return "GpeFieldPropertyBean [field=" + field + ", title=" + title + ", hidden=" + hidden + ", align=" + align
				+ ", gshow=" + gshow + ", pshow=" + pshow + ", eshow=" + eshow + ", gformat=" + gformat + ", pformat="
				+ pformat + ", eformat=" + eformat + ", width=" + width + "]";
	}

}
