package com.zhangqin.framework.web.importer;

import com.zhangqin.framework.web.importer.annotation.ExcelCell;

/**
 * Excel导入模版
 * 
 * @author kun
 *
 */
public class ExcelImportTemplate {
	/**
	 * 错误信息
	 */
	@ExcelCell(title = "错误信息")
	private String errorMsg;

	/**
	 * 行号
	 */
	private Integer rowNo;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Integer getRowNo() {
		return rowNo;
	}

	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}

}
