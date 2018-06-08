package com.zhangqin.framework.web.importer;

import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.zhangqin.framework.common.utils.JsonMapper;
import com.zhangqin.framework.web.common.utils.ExcelUtils;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.core.GpeRealm;

/**
 * 导入校验器
 * 
 * @author kun
 *
 * @param <T>
 */
public abstract class AbstractExcelImport<IN extends ExcelImportTemplate, OUT> {

	/**
	 * 输出数据
	 */
	private List<OUT> outputDataList = Lists.newArrayList();

	/**
	 * 导入失败数据
	 */
	private List<IN> inputErrorDataList = Lists.newArrayList();
	
	/**
	 * 校验所有数据
	 * @param inputList
	 */
	public abstract void checkAllRecord(List<IN> inputList);
	
	/**
	 * 转换为输出对象
	 * 
	 * @param list
	 * @return
	 */
	public abstract List<OUT> convertAllRecord(List<IN> inputList);

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
	public ImportResult<OUT> importExcel(MultipartFile file) {
		// 读取
		List<IN> inputList = ExcelUtils.readExcel(file, getInputJavaClass());
		
		// 校验
		checkAllRecord(inputList);
		
		// 去除校验不通过的
		clearErrorData(inputList);
		
		// 转化对象
		List<OUT> outputDataList = convertAllRecord(inputList);

		if (inputErrorDataList.size() > 0) {
			// 保存10分钟，用户每次导入都保存一个错误文件
			String ticket = UUID.randomUUID().toString();
			// GPE数据获取及持久化接口
			GpeRealm realm = SpringContextUtils.getBean(GpeRealm.class);
			realm.setStringToRedis("IMPORT:" + ticket, JsonMapper.toJson(inputErrorDataList), 10, TimeUnit.MINUTES);

			return new ImportResult<>(outputDataList.size(), inputErrorDataList.size(), outputDataList, true, ticket);
		} else {
			return new ImportResult<>(outputDataList.size(), 0, outputDataList, false, null);
		}

	}
	
	private void clearErrorData(List<IN> inputList) {
		Iterator<IN> iterator  = inputList.iterator();
		while (iterator.hasNext()) {
			IN record = iterator.next();
			if (StringUtils.isNotEmpty(record.getErrorMsg())) {
				inputErrorDataList.add(record);
			} else {
				iterator.remove();
			}
		}
	}

	public List<IN> getInputErrorDataList() {
		return inputErrorDataList;
	}

	public List<OUT> getOutputDataList() {
		return outputDataList;
	}

}
