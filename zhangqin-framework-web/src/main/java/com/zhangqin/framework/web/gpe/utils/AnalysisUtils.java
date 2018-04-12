package com.zhangqin.framework.web.gpe.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.annotation.AnnotatedElementUtils;

import com.zhangqin.framework.web.gpe.GpeCacheManager;
import com.zhangqin.framework.web.gpe.GpeDefaultPropertyStrategy;
import com.zhangqin.framework.web.gpe.GpePropertyStrategy;
import com.zhangqin.framework.web.gpe.annotation.GpeField;
import com.zhangqin.framework.web.gpe.annotation.GpeHeader;
import com.zhangqin.framework.web.gpe.annotation.GpeProperty;
import com.zhangqin.framework.web.gpe.bean.GpeGlobalPropertyBean;
import com.zhangqin.framework.web.gpe.bean.analysis.GpeFieldAnalysis;
import com.zhangqin.framework.web.gpe.bean.analysis.GpeHeaderAnalysis;

/**
 * 解析注解工具类
 * 
 * @author zhangqin
 *
 */
public class AnalysisUtils {

	/**
	 * 
	 * <p>
	 * Title: 私有构造函数
	 * </p>
	 * <p>
	 * Description: 成员方法均为静态，禁止new对象
	 * </p>
	 */
	private AnalysisUtils() {
	}

	/**
	 * 解析出类的所有原始属性
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> analysisOriginalFields(Class<?> clazz,boolean all) {
		// 获取子类和父类的所有Field
		List<Field> fieldList = new ArrayList<Field>();
		Class<?> tempClass = clazz;
		while (null != tempClass) {
			// 获取tempClass的所有Field
			for (Field field : tempClass.getDeclaredFields()) {
				boolean exists = fieldList.parallelStream().filter(f -> {
					return f.getName().equals(field.getName());
				}).count() > 0;
				// 是否标记@GpeField注解
				if (!all && !AnnotatedElementUtils.hasAnnotation(field, GpeField.class)) {
					continue;
				}
				
				if (!exists && !field.getName().equals("serialVersionUID")) {
					field.setAccessible(true);
					fieldList.add(field);
				}
			}
			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}
		return fieldList;
	}

	/**
	 * 
	 * @Description: 解析默认属性
	 * @param clazz
	 * @return PropertyBean
	 * @author zhangq
	 * @date 2017年9月25日
	 */
	public static GpeGlobalPropertyBean analysisProperty(Class<?> clazz) {
		// 属性配置器Class
		Class<? extends GpePropertyStrategy> propertyClass = null;

		// 是否标记@GpeProperty注解
		if (!AnnotatedElementUtils.hasAnnotation(clazz, GpeProperty.class)) {
			propertyClass = GpeDefaultPropertyStrategy.class;
		} else {
			// 获取注解
			GpeProperty annotation = clazz.getAnnotation(GpeProperty.class);
			propertyClass = annotation.property();
		}

		return GpeCacheManager.getProperty(propertyClass);
	}

	/**
	 * 
	 * @Description: 解析表头
	 * @param clazz
	 * @return HeaderBean
	 * @author zhangqin
	 * @date 2017年9月22日
	 */
	public static GpeHeaderAnalysis analysisHeader(Class<?> clazz) {
		// 是否标记@GpeHeader注解
		if (!AnnotatedElementUtils.hasAnnotation(clazz, GpeHeader.class)) {
			return new GpeHeaderAnalysis();
		}

		// 获取注解
		GpeHeader annotation = clazz.getAnnotation(GpeHeader.class);

		// 新建表头实体类
		GpeHeaderAnalysis bean = new GpeHeaderAnalysis();
		// 表头标题
		bean.setTitle(annotation.title());
		// 网格是否显示标题
		bean.setGshow(annotation.gshow());
		// 是否打印标题
		bean.setPshow(annotation.pshow());
		// 是否导出标题
		bean.setEshow(annotation.eshow());
		// 是否解析所有的字段
		bean.setAll(annotation.all());
		return bean;
	}
	
	/**
	 * 
	 * @Description: 解析字段列表
	 * @param clazz
	 * @return List<FieldBean>  
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static List<GpeFieldAnalysis> analysisFieldList(Class<?> clazz) {
		// 所有字段列表
		List<GpeFieldAnalysis> beanList = new ArrayList<GpeFieldAnalysis>();

		// 获取子类和父类的所有Field
		List<Field> fieldList = analysisOriginalFields(clazz,true);

		// 遍历所有字段，解析注解
		fieldList.stream().forEach(field->{
			GpeFieldAnalysis bean = analysisField(field);
			if (null != bean) {
				beanList.add(bean);
			}
		});
		
		return beanList;
	}

	/**
	 * 
	 * @Description: 解析单个字段
	 * @param field
	 * @return FieldBean
	 * @author zhangqin
	 * @date 2017年9月22日
	 */
	public static GpeFieldAnalysis analysisField(Field field) {
		// 是否标记@GpeHeader注解
		if (!AnnotatedElementUtils.hasAnnotation(field, GpeField.class)) {
			return null;
		}

		// 获取注解
		GpeField annotation = field.getAnnotation(GpeField.class);

		// 新建字段实体类
		GpeFieldAnalysis bean = new GpeFieldAnalysis();
		// 字段名称
		bean.setField(field.getName());
		// 字段标题
		bean.setTitle(annotation.title());
		// 是否必须字段
		bean.setMust(annotation.must().convertToBoolean());
		// 是否隐藏
		bean.setHidden(annotation.hidden().convertToBoolean());
		// 水平对齐方式
		bean.setAlign(annotation.align());
		// 网格是否显示
		bean.setGshow(annotation.gshow().convertToBoolean());
		// 是否打印
		bean.setPshow(annotation.pshow().convertToBoolean());
		// 是否导出
		bean.setEshow(annotation.eshow().convertToBoolean());
		// 网格宽度
		bean.setGwidth(annotation.gwidth());
		// 打印宽度
		bean.setPwidth(annotation.pwidth());
		// 导出宽度
		bean.setEwidth(annotation.ewidth());
		// 网格格式化
		bean.setGformat(annotation.gformat());
		// 打印格式化
		bean.setPformat(annotation.pformat());
		// 导出格式化
		bean.setEformat(annotation.eformat());
		// 是否支持排序
		bean.setSortable(annotation.sortable().convertToBoolean());
		// 字段显示顺序
		bean.setSort(annotation.sort());
		// 列合并标题
		bean.setCtitle(annotation.ctitle());
		// 是否冻结列
		bean.setFrozen(annotation.frozen());
		// 扩展坞集合
		if (null != annotation.docks() && annotation.docks().length > 0) {
			bean.setDocks(Arrays.asList(annotation.docks()));
		}
		// 是否存需要计算合计
		bean.setSum(annotation.sum());
		return bean;
	}
}
