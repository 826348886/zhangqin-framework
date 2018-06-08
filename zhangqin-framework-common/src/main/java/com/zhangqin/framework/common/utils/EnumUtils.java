package com.zhangqin.framework.common.utils;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.zhangqin.framework.common.enums.BaseEnum;

public class EnumUtils {

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
				if(enu.getValue() instanceof Integer) {
					return enu.getValue().toString().equals(value.toString());
				}
				
				if(enu.getValue() instanceof Boolean) {
					return enu.getValue().toString().equals(value.toString());
				}
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
	
	public enum test implements BaseEnum<test,Integer> {
		/**
		 * 现金退款
		 */
		CASH("现金退款", 1),
		/**
		 * 原路退款
		 */
		ORIGINAL("原路退款", 2),
		/**
		 * 不退
		 */
		NO("不退", 3);

		/**
		 * 枚举显示值
		 */
		private String desc;

		/**
		 * 枚举值
		 */
		private Integer value;

		/**
		 * 
		 * <p>Title: 构造函数</p> 
		 * <p>Description: </p> 
		 * @param name
		 * @param value
		 */
		test(String desc, Integer value) {
			this.desc = desc;
			this.value = value;
		}

		/**
		 * (non-Javadoc)
		 * @see com.okdeer.newretail.common.enums.BaseEnum#desc()
		 */
		//@Override
		public String getDesc() {
			return desc;
		}

		/**
		 * (non-Javadoc)
		 * @see com.okdeer.newretail.common.enums.BaseEnum#value()
		 */
		//@Override
		public Integer getValue() {
			return value;
		}
	}

}
