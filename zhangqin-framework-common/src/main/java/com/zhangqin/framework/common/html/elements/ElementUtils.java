package com.zhangqin.framework.common.html.elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangqin.framework.common.annotation.Selected;
import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.html.elements.select.OptionElement;
import com.zhangqin.framework.common.html.elements.select.SelectElement;

/**
 * HTML元素工具类
 * @author zhangqin
 *
 */
public class ElementUtils {
	/**
	 * Logger
	 */
	private static Logger logger = LoggerFactory.getLogger(ElementUtils.class);
	
	/**
	 * 获取select元素对象
	 * @param clazz
	 * @return
	 */
	public static <T> SelectElement<T> getSelectElement(Class<? extends BaseEnum<? extends Enum<?>, T>> clazz) {
		SelectElement<T> element = new SelectElement<T>();
		try {
			// 枚举值列表
			Method method = clazz.getMethod("values");

			@SuppressWarnings("unchecked")
			BaseEnum<? extends Enum<?>, T>[] inter = (BaseEnum<? extends Enum<?>, T>[]) method.invoke(null);

			// 所有枚举项
			List<OptionElement<T>> options = Arrays.asList(inter).parallelStream()
					.map(enu -> new OptionElement<T>(enu.getDesc(), enu.getValue())).collect(Collectors.toList());
			
			// 默认字段
			Field field = Arrays.asList(clazz.getDeclaredFields()).parallelStream()
					.filter(f -> null != f.getAnnotation(Selected.class)).findFirst()
					.orElse(clazz.getDeclaredFields()[0]);

			// 默认值
			T selected = Arrays.asList(inter).parallelStream().filter(enu -> enu.toString().equals(field.getName()))
					.findFirst().orElse(inter[0]).getValue();

			// 设置所有枚举值
			element.setOptions(options);
			// 设置默认值
			element.setSelected(selected);
		} catch (Exception e) {
			logger.error("获取select元素对象失败，{}", e);
		}

		return element;
	}
}
