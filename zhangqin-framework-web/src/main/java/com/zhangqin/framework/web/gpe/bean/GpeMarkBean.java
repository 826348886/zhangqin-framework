package com.zhangqin.framework.web.gpe.bean;

import java.io.Serializable;

/**
 * 
 * ClassName: GpeMarkBean 
 * @Description: Gpe定位标记Bean
 * @author zhangqin
 * @date 2017年12月18日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class GpeMarkBean implements Serializable {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 8026391877102320160L;

	/**
	 * 
	 * <p>Title: 构造函数</p> 
	 * <p>Description: </p> 
	 * @param moudle
	 * @param function
	 * @param key
	 */
	public GpeMarkBean(String moudle, String function, String key) {
		this.moudle = moudle;
		this.function = function;
		this.key = key;
	}

	/**
	 * 模块
	 */
	private String moudle;
	/**
	 * 功能
	 */
	private String function;
	/**
	 * 键
	 */
	private String key;
	/**
	 * 值
	 */
	private String value;

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GpeMarkBean [moudle=" + moudle + ", function=" + function
				+ ", key=" + key + ", value=" + value + "]";
	}
}