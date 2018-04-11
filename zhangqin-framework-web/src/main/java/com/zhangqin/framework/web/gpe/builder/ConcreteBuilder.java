package com.zhangqin.framework.web.gpe.builder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.gpe.GpeCacheManager;
import com.zhangqin.framework.web.gpe.GpeRealm;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldPropertyBean;
import com.zhangqin.framework.web.gpe.bean.GpeGlobalPropertyBean;
import com.zhangqin.framework.web.gpe.bean.GpeHeaderBean;
import com.zhangqin.framework.web.gpe.bean.analysis.GpeFieldAnalysis;
import com.zhangqin.framework.web.gpe.bean.analysis.GpeHeaderAnalysis;
import com.zhangqin.framework.web.gpe.bean.result.UserColumnSetting;
import com.zhangqin.framework.web.gpe.bean.result.UserColumnSettingField;
import com.zhangqin.framework.web.gpe.enums.TextAlign;
import com.zhangqin.framework.web.gpe.enums.UseFor;
import com.zhangqin.framework.web.gpe.utils.AnalysisUtils;

/**
 * 具体建造者，实现建造的具体每个步骤
 * 
 * @author zhangqin
 *
 */
public class ConcreteBuilder implements Builder {
	/**
	 * 待解析的实体类Class
	 */
	private Class<?> clazz;
	
	/**
	 * 解析用户
	 */
	private UseFor useFor;

	/**
	 * 万能的GPE对象
	 */
	private GpeBean gpe;

	/**
	 * 是否跳过部分的建造
	 */
	private boolean skip = false;
	
	/**
	 * 是否解析所有的字段
	 */
	private boolean analysisAll = false;

	/**
	 * 原始字段Map
	 */
	private ConcurrentMap<String, Field> originalFieldsMap = Maps.newConcurrentMap();

	/**
	 * 所有的数字类型
	 */
	private static final String[] NUMBER_TYPES = { "int", "Integer", "short", "Short", "long", "Long", "float", "Float",
			"BigDecimal", "Double" };

	public ConcreteBuilder(Class<?> clazz,UseFor useFor) {
		this.clazz = clazz;
		this.useFor = useFor;
	}

	@Override
	public void initByOriginalFields() {
		// 解析原始字段
		List<Field> originalFields = AnalysisUtils.analysisOriginalFields(clazz,analysisAll);

		// 原始字段转为GpeFieldBean对象
		int sort = 0;
		List<GpeFieldBean> fields = Lists.newArrayList();
		for (Field originalField : originalFields) {
			GpeFieldBean field = new GpeFieldBean();
			field.setField(originalField.getName());
			field.setSort(sort++);
			fields.add(field);
		}
		gpe.setFields(fields);

		// 原始字段存入Cache
		originalFieldsMap = GpeCacheManager.getOriginalFields(clazz);
		if (null == originalFieldsMap) {
			originalFieldsMap = originalFields.parallelStream()
					.collect(Collectors.toConcurrentMap(Field::getName, Function.identity()));
			GpeCacheManager.setOriginalFields(clazz, originalFieldsMap);
		}
	}

	@Override
	public void coverByGlobalProperty() {
		// 无需覆盖
		if (skip) {
			return;
		}

		// 解析全局配置
		GpeGlobalPropertyBean property = AnalysisUtils.analysisProperty(clazz);
		if (null == property) {
			return;
		}

		// 使用全局配置覆盖GpeBean
		gpe.getFields().forEach(field -> {
			// 覆盖属性
			BeanMapper.copySkipNullAndEmpty(property, field);
			
			// 宽度
			field.setGwidth(property.getWidth());
			field.setPwidth(property.getWidth());
			field.setEwidth(property.getWidth());

			// 字段类型
			String simpleTypeName = originalFieldsMap.get(field.getField()).getType().getSimpleName();

			// 数字对齐方式
			if (Arrays.asList(NUMBER_TYPES).parallelStream().collect(Collectors.toSet()).contains(simpleTypeName)) {
				field.setAlign(property.getNumericAlign());
			} else if (simpleTypeName.equals("Date")) { // 日期对齐方式
				field.setAlign(property.getTextAlign());
			} else { // 字符串或类型对齐方式
				field.setAlign(property.getTextAlign());
			}

			// 浮点格式化
			if (originalFieldsMap.get(field.getField()).getType().getSimpleName().equals("BigDecimal")) {
				// 浮点格式化
				field.setGformat(property.getgDecimalFormat());
				field.setPformat(property.getpDecimalFormat());
				field.setEformat(property.geteDecimalFormat());
			}

			// 日期格式化
			if (originalFieldsMap.get(field.getField()).getType().getSimpleName().equals("Date")) {
				field.setGformat(property.getgDateFormat());
				field.setPformat(property.getpDateFormat());
				field.setEformat(property.geteDateFormat());
			}
			
			// 是否隐藏
			field.setHidden(false);
		});

	}

	@Override
	public void coverByFieldProperty() {
		// 无需覆盖
		if (skip) {
			return;
		}

		// 使用字段配置覆盖GpeBean
		gpe.getFields().forEach(field -> {
			GpeFieldPropertyBean property = GpeCacheManager.getFieldProperty(field.getField());
			if (null == property) {
				return;
			}
			BeanMapper.copySkipNullAndEmpty(property, field);
		});
	}

	@Override
	public void coverByHeaderAnnotation() {
		// 从缓存中取GpeBean对象
		GpeBean bean = GpeCacheManager.getCopyBean(clazz);
		if (null != bean) {
			gpe = bean;
			skip = true;
			return;
		}else {
			gpe = new GpeBean();
		}
		// 无需覆盖
		if (skip) {
			return;
		}

		GpeHeaderAnalysis header = AnalysisUtils.analysisHeader(clazz);
		analysisAll = header.isAll();
		GpeHeaderBean headerBean = BeanMapper.map(header, GpeHeaderBean.class);
		gpe.setHeader(headerBean);
	}

	@Override
	public void coverByFieldAnnotation() {
		// 无需覆盖
		if (skip) {
			return;
		}
		
		// 解析所有字段
		List<GpeFieldAnalysis> fieldList = AnalysisUtils.analysisFieldList(clazz);
		Map<String,GpeFieldAnalysis> fieldMap = fieldList.parallelStream().collect(Collectors.toMap(GpeFieldAnalysis::getField, Function.identity()));

		// 使用字段配置覆盖GpeBean
		gpe.getFields().forEach(field -> {
			// 解析原始字段注解
			GpeFieldAnalysis bean = fieldMap.get(field.getField());
			if (null == bean) {
				return;
			}
			// 避免被-1覆盖
			if (bean.getGwidth() == -1) {
				bean.setGwidth(field.getGwidth());
			}
			if (bean.getPwidth() == -1) {
				bean.setPwidth(field.getPwidth());
			}
			if (bean.getEwidth() == -1) {
				bean.setEwidth(field.getEwidth());
			}
			// 避免被TextAlign.NULL覆盖
			if (null == bean.getAlign() || bean.getAlign().equals(TextAlign.NULL)) {
				bean.setAlign(field.getAlign());
			}
			
			// 覆盖属性
			BeanMapper.copySkipNullAndEmpty(bean, field);
			
		});
		
		
	}
	
	@Override
	public void transformProcess() {
		List<GpeFieldBean> needAdd = Lists.newArrayList();
		gpe.getFields().forEach(field -> {
			// 枚举处理
			Field originalField = originalFieldsMap.get(field.getField());
			if (BaseEnum.class.isAssignableFrom(originalField.getType())) {
				// 新增一个字段用于枚举描述展现
				GpeFieldBean newField = new GpeFieldBean();
				BeanMapper.copySkipNullAndEmpty(field, newField);
				;
				newField.setField(field.getField() + "Desc");
				newField.setAlign(TextAlign.LEFT);
				newField.setHidden(false);
				needAdd.add(newField);

				// 原字段隐藏
				field.setHidden(true);
			}

		});
		gpe.getFields().addAll(needAdd);
	}

	@Override
	public void coverByUserProperty() {
		GpeRealm realm = SpringContextUtils.getBean(GpeRealm.class);
		String methodGenericInfo = GpeCacheManager.getMethodGenericInfo();
		UserColumnSetting result = realm.getUserColumnSetting(methodGenericInfo);
		if (null == result) {
			return;
		}
		
		// 将用户自定义列设置明细转换为Map
		Map<String, UserColumnSettingField> fieldMap = result.getFields().parallelStream()
				.collect(Collectors.toMap(UserColumnSettingField::getField, Function.identity()));

		// 使用用户自定义列设置覆盖GpeBean
		gpe.getFields().forEach(field -> {
			// 获取用户自定义列设置
			UserColumnSettingField bean = fieldMap.get(field.getField());
			if (null == bean) {
				return;
			}
			// 覆盖属性
			BeanMapper.copySkipNullAndEmpty(bean, field);
		});
	}
	
	@Override
	public void filterForbidFields() {
		Iterator<GpeFieldBean> iterator = gpe.getFields().iterator();
		while (iterator.hasNext()) {
			GpeFieldBean field = iterator.next();
			// 不显示
			if (useFor.equals(UseFor.GRID) && !field.getGshow()) {
				iterator.remove();
				continue;
			}
			// 不打印
			if (useFor.equals(UseFor.PRINT) && (!field.getPshow() || field.getHidden())) {
				iterator.remove();
				continue;
			}
			// 不导出
			if (useFor.equals(UseFor.EXPORT) && (!field.getEshow() || field.getHidden())) {
				iterator.remove();
				continue;
			}
			// 设置不显示隐藏字段
			if (field.getHidden()) {
				iterator.remove();
				continue;
			}
		}
		
//		String userId = GpeCacheManager.USER_THREAD_LOCAL.get();
//		GpeRealm gpeDataInterface = SpringContextUtils.getBean(GpeRealm.class);
//		Set<String> forbidFields = gpeDataInterface.getForbidFields(userId);
//		if(CollectionUtils.isEmpty(forbidFields)) {
//			return ;
//		}
//		Iterator<GpeFieldBean> iterator = gpe.getFields().iterator();
//		while(iterator.hasNext()) {
//			GpeFieldBean field = iterator.next();
//			if(forbidFields.contains(field.getField())) {
//				iterator.remove();
//			}
//		}
	}


	@Override
	public GpeBean retrieveResult() {
		Collections.sort(gpe.getFields());
		return gpe;
	}


}
