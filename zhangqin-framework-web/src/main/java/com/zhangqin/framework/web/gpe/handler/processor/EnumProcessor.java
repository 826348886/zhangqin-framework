package com.zhangqin.framework.web.gpe.handler.processor;

import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;

public class EnumProcessor {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(EnumProcessor.class);
	
	@SuppressWarnings("unchecked")
	public static void process(GpeFieldBean gpeField, Field originalField, Map<String, Object> map, Object value) {
		// gpeField是否为空
		if (null == gpeField) {
			return;
		}

		// 字段类型
		Class<?> type = originalField.getType();

		// 如果是枚举类型
		if (BaseEnum.class.isAssignableFrom(type)) {
			try {
				BaseEnum<? extends Enum<?>, ?> en = (BaseEnum<? extends Enum<?>, ?>) value;
				if (null != en) {
					map.put(originalField.getName() + "Desc", en.getDesc());
				}
			} catch (Exception e) {
				logger.error("枚举转换失败：{}", e);
			}
		}
	}
}
