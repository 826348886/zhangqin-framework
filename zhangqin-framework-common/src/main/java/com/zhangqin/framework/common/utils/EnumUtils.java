package com.zhangqin.framework.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.zhangqin.framework.common.annotation.Selected;
import com.zhangqin.framework.common.entity.EnumOption;
import com.zhangqin.framework.common.entity.EnumResult;
import com.zhangqin.framework.common.enums.BaseEnum;

public class EnumUtils {

	/**
	 * 
	 * @Description: 获取枚举结果对象，入参需实现BaseEnumInterface接口
	 * @param clazz
	 * @return EnumResult
	 * @author zhangqin
	 * @date 2017年4月26日
	 */
	public static <T> EnumResult<T> getEnumResult(Class<? extends BaseEnum<? extends Enum<?>, T>> clazz) {
		EnumResult<T> result = new EnumResult<T>();
		try {
			// 枚举值列表
			Method method = clazz.getMethod("values");

			@SuppressWarnings("unchecked")
			BaseEnum<? extends Enum<?>, T>[] inter = (BaseEnum<? extends Enum<?>, T>[]) method.invoke(null);

			// 所有枚举项
			List<EnumOption<T>> options = Arrays.asList(inter).parallelStream()
					.map(enu -> new EnumOption<T>(enu.getDesc(), enu.getValue())).collect(Collectors.toList());
			
			// 默认字段
			Field field = Arrays.asList(clazz.getDeclaredFields()).parallelStream()
					.filter(f -> null != f.getAnnotation(Selected.class)).findFirst()
					.orElse(clazz.getDeclaredFields()[0]);

			// 默认值
			T selected = Arrays.asList(inter).parallelStream().filter(enu -> enu.toString().equals(field.getName()))
					.findFirst().orElse(inter[0]).getValue();

			// 设置所有枚举值
			result.setOptions(options);
			// 设置默认值
			result.setSelected(selected);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 根据枚举的class和value获取枚举对象
	 * @param clazz
	 * @param value
	 * @return
	 */
	public static <T> BaseEnum<? extends Enum<?>, T> getEnumObjT(Class<? extends BaseEnum<? extends Enum<?>, T>> clazz,T value) {
		try {
			// 枚举值列表
			Method method = clazz.getMethod("values");
	
			@SuppressWarnings("unchecked")
			BaseEnum<? extends Enum<?>, T>[] inter = (BaseEnum<? extends Enum<?>, T>[]) method.invoke(null);
			
			return Arrays.asList(inter).parallelStream().filter(enu -> {
				return enu.getValue().equals(value);
			}).findFirst().orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 根据枚举的class和value获取枚举对象
	 * @param clazz
	 * @param value
	 * @return
	 */
	public static BaseEnum<? extends Enum<?>, ?> getEnumObj(Class<? extends BaseEnum<? extends Enum<?>, ?>> clazz,Object value) {
		try {
			// 枚举值列表
			Method method = clazz.getMethod("values");
	
			@SuppressWarnings("unchecked")
			BaseEnum<? extends Enum<?>, ?>[] inter = (BaseEnum<? extends Enum<?>, ?>[]) method.invoke(null);
			
			return Arrays.asList(inter).parallelStream().filter(enu -> {
				return enu.getValue().equals(value);
			}).findFirst().orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @Description: 获取枚举描述
	 * @param clazz
	 * @param value
	 * @return String
	 * @author zhangqin
	 * @date 2017年12月26日
	 */
	public static String getEnumDesc(Class<? extends BaseEnum<? extends Enum<?>, ?>> clazz, Object value) {
		// 枚举值列表
		try {
			Method method = clazz.getMethod("values");
			@SuppressWarnings("unchecked")
			BaseEnum<? extends Enum<?>, ?>[] inter = (BaseEnum<? extends Enum<?>, ?>[]) method.invoke(null);

			return Arrays.asList(inter).parallelStream().filter(enu -> enu.getValue().equals(value))
					.map(enu -> enu.getDesc()).findFirst().orElse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
