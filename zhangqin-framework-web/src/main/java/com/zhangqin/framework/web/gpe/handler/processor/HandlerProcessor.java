package com.zhangqin.framework.web.gpe.handler.processor;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.enums.UseFor;
import com.zhangqin.framework.web.gpe.utils.AnalysisUtils;
import com.zhangqin.framework.web.gpe.utils.GpeUtils;

public class HandlerProcessor {
	public static List<Map<String, Object>> process(Class<?> clazz, List<?> list,UseFor useFor){
		GpeBean gpe = GpeUtils.getGpeBean(clazz);
		Map<String,GpeFieldBean> fieldMap = gpe.getFields().parallelStream().collect(Collectors.toMap(GpeFieldBean::getField, Function.identity()));

		List<Field> originalFields = AnalysisUtils.analysisOriginalFields(clazz, true);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		
		list.stream().forEach(data->{
			Map<String, Object> map = Maps.newHashMap();
			originalFields.forEach(field->{
				try {
					String fieldName = field.getName();
					Object value = field.get(data);
					// 枚举处理
					EnumProcessor.process(fieldMap.get(fieldName), field, map, value);
					
					// 格式化处理
					FormatProcessor.process(fieldMap.get(fieldName), field, map, value, useFor);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
			mapList.add(map);
		});
		return mapList;
	}
}
