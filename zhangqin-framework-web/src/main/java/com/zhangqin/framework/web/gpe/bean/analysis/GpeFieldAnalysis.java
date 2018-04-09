package com.zhangqin.framework.web.gpe.bean.analysis;

import java.io.Serializable;
import java.util.List;

import com.zhangqin.framework.web.gpe.enums.DockType;
import com.zhangqin.framework.web.gpe.enums.TextAlign;

/**
 * @GpeField注解解析类
 * @author zhangqin
 *
 */
public class GpeFieldAnalysis implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6192448315156164385L;

	/**
	 * 字段名称
	 */
	private String field;

	/**
	 * 字段标题
	 */
	private String title;

	/**
	 * 是否必须
	 */
	private Boolean must;

	/**
	 * 是否隐藏
	 */
	private Boolean hidden;

	/**
	 * 水平对齐方式
	 */
	private TextAlign align;

	/**
	 * 网格是否显示
	 */
	private Boolean gshow;

	/**
	 * 是否打印
	 */
	private Boolean pshow;

	/**
	 * 是否导出
	 */
	private Boolean eshow;

	/**
	 * 列表宽度
	 */
	private int gwidth;

	/**
	 * 打印宽度
	 */
	private int pwidth;

	/**
	 * 导出宽度
	 */
	private int ewidth;

	/**
	 * 列表格式化
	 */
	private String gformat;

	/**
	 * 打印格式化
	 */
	private String pformat;

	/**
	 * 打印格式化
	 */
	private String eformat;

	/**
	 * 是否支持排序
	 */
	private Boolean sortable;

	/**
	 * 字段显示顺序
	 */
	private int sort;

	/**
	 * 
	 * 列合并标题</br>
	 * 相同ctitle且排列连续的列，自动进行colspan。</br>
	 * 当存在有colspan时，没有colspan则自动进行rowspan。
	 */
	private String ctitle;

	/**
	 * 是否冻结列
	 */
	private boolean frozen;

	/**
	 * 扩展坞集合
	 */
	private List<DockType> docks;

	/**
	 * 是否冻结列
	 */
	private boolean sum;

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

	public Boolean getMust() {
		return must;
	}

	public void setMust(Boolean must) {
		this.must = must;
	}

	public Boolean getHidden() {
		return hidden;
	}

	public void setHidden(Boolean hidden) {
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

	public int getGwidth() {
		return gwidth;
	}

	public void setGwidth(int gwidth) {
		this.gwidth = gwidth;
	}

	public int getPwidth() {
		return pwidth;
	}

	public void setPwidth(int pwidth) {
		this.pwidth = pwidth;
	}

	public int getEwidth() {
		return ewidth;
	}

	public void setEwidth(int ewidth) {
		this.ewidth = ewidth;
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

	public Boolean getSortable() {
		return sortable;
	}

	public void setSortable(Boolean sortable) {
		this.sortable = sortable;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCtitle() {
		return ctitle;
	}

	public void setCtitle(String ctitle) {
		this.ctitle = ctitle;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public List<DockType> getDocks() {
		return docks;
	}

	public void setDocks(List<DockType> docks) {
		this.docks = docks;
	}

	public boolean isSum() {
		return sum;
	}

	public void setSum(boolean sum) {
		this.sum = sum;
	}

}
