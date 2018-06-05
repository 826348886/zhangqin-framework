package com.zhangqin.framework.web.importer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.zhangqin.framework.web.common.utils.ExcelUtils;

public class ExcelImporter<T extends ExcelImporterError> {
	
	@SuppressWarnings("unchecked")
	public ExcelImporter() {
		javaClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return;
	}
	
	/**
	 * 校验成功的数据
	 */
	private List<T> successDatas;
	
	/**
	 * 校验失败的数据
	 */
	private List<T> errorDatas;
	
	/**
	 * 实体类的Class
	 */
	@SuppressWarnings("unused")
	private Class<T> javaClass;
	
	public void importData(MultipartFile file) {
		Field[] fields = this.javaClass.getDeclaredFields();
		
		List<Map<String, Object>> list = ExcelUtils.readExcel(file);
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
