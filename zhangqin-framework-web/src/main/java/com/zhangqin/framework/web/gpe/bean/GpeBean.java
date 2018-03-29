package com.zhangqin.framework.web.gpe.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * ClassName: GpeBean 
 * @Description: 注解对应的实体类对象
 * @author zhangqin
 * @date 2017年12月18日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class GpeBean implements Serializable {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = -8486464987577732831L;
	/**
	 * 属性注解对应的实体类对象
	 */
	private GpeGlobalPropertyBean property;
	/**
	 * 表头注解对应的实体类对象
	 */
	private GpeHeaderBean header;
	/**
	 * 字段注解对应的实体类对象
	 */
	private List<GpeFieldBean> fields;

	public GpeGlobalPropertyBean getProperty() {
		return property;
	}

	public void setProperty(GpeGlobalPropertyBean property) {
		this.property = property;
	}

	public GpeHeaderBean getHeader() {
		return header;
	}

	public void setHeader(GpeHeaderBean header) {
		this.header = header;
	}

	public List<GpeFieldBean> getFields() {
		return fields;
	}

	public void setFields(List<GpeFieldBean> fields) {
		this.fields = fields;
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GpeBean [property=" + property + ", header=" + header
				+ ", fields=" + fields + "]";
	}
}