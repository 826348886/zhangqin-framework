package com.zhangqin.framework.web.importer;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.web.multipart.MultipartFile;

import com.zhangqin.framework.web.common.utils.ExcelUtils;

public class ExcelImporter<T> {
	
	
	/**
	 * 校验成功的数据
	 */
	private List<T> successDatas = Lists.newArrayList();
	
	/**
	 * 校验失败的数据
	 */
	private List<T> errorDatas;
	
	private AbstractImportValidator<T> validator;
	
	public ExcelImporter(MultipartFile file,AbstractImportValidator<T> validator) {
		
	}
	
	
	
	public void importExcel(MultipartFile file,Class<T> javaClass) {
		// 读取Excel
		List<T> list = ExcelUtils.readExcel(file,javaClass);
		for (T t : list) {
			String result = validator.checkOneRecord(t);
			if(StringUtils.isBlank(result)) {
				errorDatas.add(t);
			}else {
				successDatas.add(t);
			}
		}
	}
	
	public List<T> getSuccessDatas() {
		return successDatas;
	}

	public void setSuccessDatas(List<T> successDatas) {
		this.successDatas = successDatas;
	}

	public List<T> getErrorDatas() {
		return errorDatas;
	}

	public void setErrorDatas(List<T> errorDatas) {
		this.errorDatas = errorDatas;
	}
}
