package com.zhangqin.framework.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import com.google.common.collect.Lists;

/**
 * 反射工具类
 * 
 * @author kun
 *
 */
public class ReflectUtils {

	/**
	 * 获取所有javaClass的所有包含指定注解字段，含父类。
	 * 
	 * @param javaClass
	 * @param annotationType
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> javaClass, Class<? extends Annotation> annotationType) {
		List<Field> list = getFieldList(javaClass);
		Iterator<Field> iterator = list.iterator();
		while (iterator.hasNext()) {
			Field field = iterator.next();
			// 是否存在指定注解
			if (!AnnotatedElementUtils.hasAnnotation(field, annotationType)) {
				iterator.remove();
			}
		}
		return list;
	}

	/**
	 * 获取所有javaClass的所有字段，含父类。
	 * 
	 * <p>
	 * 1.不包含serialVersionUID字段
	 * </p>
	 * <p>
	 * 2.父类字段在前，子类字段在后
	 * </p>
	 * <p>
	 * 3.子类与父类都存在的字段，子类覆盖父类</>
	 * 
	 * @param javaClass
	 * @return
	 */
	public static List<Field> getFieldList(Class<?> javaClass) {
		// 用于存储所有标记ExcelCell注解的字段
		List<Field> fieldList = Lists.newArrayList();

		// 获取所有标记ExcelCell注解的字段
		Class<?> tempClass = javaClass;
		while (null != tempClass) {
			List<Field> tempList = Lists.newArrayList();

			// 获取tempClass的所有Field
			for (Field field : tempClass.getDeclaredFields()) {

				// 是否已经存在
				boolean exists = fieldList.parallelStream().filter(f -> {
					return f.getName().equals(field.getName());
				}).count() > 0;

				// 不存在且字段名不是serialVersionUID，则添加到list
				if (!exists && !field.getName().equals("serialVersionUID")) {
					field.setAccessible(true);
					tempList.add(field);
				}
			}

			// 每次插到最前面
			if (CollectionUtils.isNotEmpty(tempList)) {
				fieldList.addAll(0, tempList);
			}

			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}

		return fieldList;
	}
}
