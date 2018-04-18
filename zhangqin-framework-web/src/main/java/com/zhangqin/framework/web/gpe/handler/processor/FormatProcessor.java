package com.zhangqin.framework.web.gpe.handler.processor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.enums.UseFor;

/**
 * 格式化处理
 * @author zhangqin
 *
 */
public class FormatProcessor {
	
	/**
	 * 格式化处理
	 * @param gpeField
	 * @param originalField
	 * @param map
	 * @param value
	 * @param useFor
	 */
	public static void process(GpeFieldBean gpeField, Field originalField, Map<String, Object> map, Object value,UseFor useFor) {
		// gpeField、value是否为空
		if (null == gpeField || null == value) {
			map.put(originalField.getName(), value);
			return;
		}
		
		// 格式化
		String format = null;
		if (UseFor.GRID.equals(useFor)) {
			format = gpeField.getGformat();
		} else if (UseFor.PRINT.equals(useFor)) {
			format = gpeField.getPformat();
		} else if (UseFor.EXPORT.equals(useFor)) {
			format = gpeField.getEformat();
		}
		
		// 未设置格式化
		if (StringUtils.isBlank(format)) {
			map.put(originalField.getName(), value);
			return;
		}

		// 字段类型
		Class<?> type = originalField.getType();
		
		// 日期类型
		if (Date.class.isAssignableFrom(type)) {
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			String datestr = sdf.format(value);
			map.put(originalField.getName(), datestr);
			return;
		}

		// BigDecimal类型
		if (BigDecimal.class.isAssignableFrom(type)) {
			// 格式化BigDecimal
			int scale = format.substring(format.lastIndexOf(".") - 1).length();
			BigDecimal decimal = (BigDecimal) value;
			String decimalstr = decimal.setScale(scale, RoundingMode.HALF_UP).toString();
			map.put(originalField.getName(), decimalstr);
			return;
		}

		map.put(originalField.getName(), value);
	}
}
