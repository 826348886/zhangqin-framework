package com.zhangqin.framework.web.importer;

import java.io.Serializable;
import java.util.List;

public class ImportResult<T> implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8321007890793730484L;
	/**
	 * 成功数量
	 */
	private Integer successCount;
	/**
	 * 失败数量
	 */
	private Integer failCount;
	/**
	 * 成功数据
	 */
	private List<T> successData;
	/**
	 * 是否存在失败数据
	 */
	private boolean hasFailData;

	/**
	 * 失败票据，凭此票据10分钟内可下载失败Excel
	 */
	private String failTicket;
	
	/**
	 * 构造函数
	 */
	public ImportResult() {
		
	}
	
	/**
	 * 构造函数
	 * @param successCount
	 * @param failCount
	 * @param successData
	 * @param hasFailData
	 * @param failTicket
	 */
	public ImportResult(Integer successCount,Integer failCount,List<T> successData,boolean hasFailData,String failTicket) {
		this.successCount = successCount;
		this.failCount = failCount;
		this.successData = successData;
		this.hasFailData = hasFailData;
		this.failTicket = failTicket;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	public List<T> getSuccessData() {
		return successData;
	}

	public void setSuccessData(List<T> successData) {
		this.successData = successData;
	}

	public boolean isHasFailData() {
		return hasFailData;
	}

	public void setHasFailData(boolean hasFailData) {
		this.hasFailData = hasFailData;
	}

	public String getFailTicket() {
		return failTicket;
	}

	public void setFailTicket(String failTicket) {
		this.failTicket = failTicket;
	}

}
