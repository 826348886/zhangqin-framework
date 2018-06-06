package com.zhangqin.framework.web.importer;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.zhangqin.framework.web.common.utils.ExcelUtils;

/**
 * 导入校验器
 * 
 * @author kun
 *
 * @param <T>
 */
public abstract class AbstractExcelImport<IN extends ExcelImportTemplate, OUT> implements Importer<IN, OUT> {
	
	/**
	 * 输出数据
	 */
	private List<OUT> outputDataList = Lists.newArrayList();
	
	/**
	 * 导入失败数据
	 */
	private List<IN> inputErrorDataList = Lists.newArrayList();
	
	/**
	 * 获取导入Class对象
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Class<IN> getInputJavaClass() {
		return (Class<IN>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**
	 * 导入Excel
	 * 
	 * @param file
	 */
	public void importExcel(MultipartFile file) {
		// 读取Excel
		List<IN> list = ExcelUtils.readExcel(file, getInputJavaClass());
		
		// 校验导入数据
		List<IN> successDatas = Lists.newArrayList();
		for (IN obj : list) {
			String result = checkOneRecord(obj);
			if (StringUtils.isBlank(result)) {
				successDatas.add(obj);
			} else {
				obj.setErrorMsg(result);
				inputErrorDataList.add(obj);
			}
		}
		
		// 转化当前数据
		successDatas.forEach(data -> {
			OUT out = convertOneRecord(data);
			if (null != out) {
				outputDataList.add(out);
			} else {
				data.setErrorMsg("数据不存在或没权限。");
				inputErrorDataList.add(data);
			}
		});
		
		// 错误数据存入redis
	}
	
	public List<IN> getInputErrorDataList() {
		return inputErrorDataList;
	}

	public List<OUT> getOutputDataList() {
		return outputDataList;
	}
	
}
