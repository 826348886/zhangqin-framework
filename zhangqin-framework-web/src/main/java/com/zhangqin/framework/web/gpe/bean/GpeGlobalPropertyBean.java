package com.zhangqin.framework.web.gpe.bean;

import java.io.Serializable;

import com.zhangqin.framework.web.gpe.enums.TextAlign;

/**
 * GPE全局属性配置Bean
 * 
 * @author zhangqin
 *
 */
public class GpeGlobalPropertyBean implements Serializable {

	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 4309913076657668558L;

	/**
	 * 是否显示
	 */
	private boolean gshow = true;

	/**
	 * 是否打印
	 */
	private boolean pshow = true;

	/**
	 * 是否导出
	 */
	private boolean eshow = true;

	/**
	 * 文本水平对齐方式
	 */
	private TextAlign textAlign = TextAlign.LEFT;

	/**
	 * 数字水平对齐方式
	 */
	private TextAlign numericAlign = TextAlign.RIGHT;

	/**
	 * 日期水平对齐方式
	 */
	private TextAlign dateAlign = TextAlign.LEFT;

	/**
	 * 网格浮点格式化，一般对应BigDecimal类型
	 */
	private String gDecimalFormat = "00.00";

	/**
	 * 打印浮点格式化，一般对应BigDecimal类型
	 */
	private String pDecimalFormat = "00.00";

	/**
	 * 导出浮点格式化，一般对应BigDecimal类型
	 */
	private String eDecimalFormat = "00.0000";

	/**
	 * 网格日期时间格式化
	 */
	private String gDateFormat = "yyyy-MM-dd";

	/**
	 * 打印日期时间格式化
	 */
	private String pDateFormat = "yyyy-MM-dd";

	/**
	 * 导出日期时间格式化
	 */
	private String eDateFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 默认宽度
	 */
	private int width = 100;

	/**
	 * 是否支持排序
	 */
	private boolean sortable = true;

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

	public TextAlign getTextAlign() {
		return textAlign;
	}

	public void setTextAlign(TextAlign textAlign) {
		this.textAlign = textAlign;
	}

	public TextAlign getNumericAlign() {
		return numericAlign;
	}

	public void setNumericAlign(TextAlign numericAlign) {
		this.numericAlign = numericAlign;
	}

	public TextAlign getDateAlign() {
		return dateAlign;
	}

	public void setDateAlign(TextAlign dateAlign) {
		this.dateAlign = dateAlign;
	}

	public String getgDecimalFormat() {
		return gDecimalFormat;
	}

	public void setgDecimalFormat(String gDecimalFormat) {
		this.gDecimalFormat = gDecimalFormat;
	}

	public String getpDecimalFormat() {
		return pDecimalFormat;
	}

	public void setpDecimalFormat(String pDecimalFormat) {
		this.pDecimalFormat = pDecimalFormat;
	}

	public String geteDecimalFormat() {
		return eDecimalFormat;
	}

	public void seteDecimalFormat(String eDecimalFormat) {
		this.eDecimalFormat = eDecimalFormat;
	}

	public String getgDateFormat() {
		return gDateFormat;
	}

	public void setgDateFormat(String gDateFormat) {
		this.gDateFormat = gDateFormat;
	}

	public String getpDateFormat() {
		return pDateFormat;
	}

	public void setpDateFormat(String pDateFormat) {
		this.pDateFormat = pDateFormat;
	}

	public String geteDateFormat() {
		return eDateFormat;
	}

	public void seteDateFormat(String eDateFormat) {
		this.eDateFormat = eDateFormat;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

}
