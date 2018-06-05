package com.zhangqin.framework.web.importer;

/**
 * 校验器
 * @author kun
 *
 */
public interface ImportCheck<T> {
	String checkOneRecord(T t);
}
