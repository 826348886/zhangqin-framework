package com.zhangqin.framework.web.gpe.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.bean.result.PrintColumn;
import com.zhangqin.framework.web.gpe.bean.result.PrintHeader;
import com.zhangqin.framework.web.gpe.enums.UseFor;

/**
 * GPE打印工具类
 * @author zhangqin
 *
 */
public class GpePrintUtils {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GpePrintUtils.class);

	/**
	 * 
	 * <p>
	 * Title: 私有构造函数
	 * </p>
	 * <p>
	 * Description: 成员方法均为静态，禁止new对象
	 * </p>
	 */
	private GpePrintUtils() {
	}
	
	/**
	 * 获取打印表头信息
	 * @param clazz
	 * @return
	 */
	public static PrintHeader getPrintHeader(Class<?> clazz) {
		logger.debug("进入GpePrintUtils->getPrintHeader(),clazz:{}.", clazz);
		
		// 打印表头对象
		PrintHeader header = new PrintHeader();
		
		// 获取GPE对象
		GpeBean gpe = GpeUtils.getGpeBean(clazz, UseFor.PRINT);
		
		// 将解析的字段转为二维集合（二维集合支持表头行列合）
		List<List<GpeFieldBean>> d2List = GpeUtils.convertToTwoDimensionalList(gpe.getFields());
		
		// GPE列对象转打印列对象
		List<List<PrintColumn>> print2dList = Lists.newArrayList();
		d2List.forEach(d1list->{
			List<PrintColumn> print1dList = Lists.newArrayList();
			d1list.forEach(field->{
				// 对象转换
				PrintColumn column = BeanMapper.map(field, PrintColumn.class);
				// 宽度
				column.setWidth(field.getPwidth());
				// 对齐方式
				column.setAlign(field.getAlign().name().toLowerCase());
				// 加入到一维列表
				print1dList.add(column);
			});
			print2dList.add(print1dList);
		});
		
		header.setColumns(print2dList);
		return header;
	}
}
