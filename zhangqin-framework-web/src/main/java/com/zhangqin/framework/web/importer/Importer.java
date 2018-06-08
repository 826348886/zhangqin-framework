package com.zhangqin.framework.web.importer;

/**
 * 校验器
 * @author kun
 *
 */
public interface Importer<IN extends ExcelImportTemplate,OUT> {
	String checkOneRecord(IN data);
	
	/**
	 * 转换为输出对象
	 * @param list
	 * @return
	 */
	OUT convertOneRecord(IN data);
}
