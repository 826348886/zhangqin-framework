package com.zhangqin.framework.web.gpe.bean.result;

import java.io.Serializable;
import java.util.List;

/**
 * 网格对象，包含一个普通列二维集合和冻结列二维集合
 * 
 * @author zhangqin
 *
 */
public class GridResult implements Serializable {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 2507890307665234043L;
	/**
	 * 普通列二维集合
	 */
	private List<List<ColumnResult>> columns;
	/**
	 * 冻结列二维集合
	 */
	private List<List<ColumnResult>> forzenColumns;

	public List<List<ColumnResult>> getColumns() {
		return columns;
	}

	public void setColumns(List<List<ColumnResult>> columns) {
		this.columns = columns;
	}

	public List<List<ColumnResult>> getForzenColumns() {
		return forzenColumns;
	}

	public void setForzenColumns(List<List<ColumnResult>> forzenColumns) {
		this.forzenColumns = forzenColumns;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MutilGridColumnsResult [columns=" + columns + ", forzenColumns=" + forzenColumns + "]";
	}
}
