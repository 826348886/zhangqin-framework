package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.enums.CompareOperator;
import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.common.utils.EnumUtils;
import com.zhangqin.framework.common.utils.JsonMapper;
import com.zhangqin.framework.gpe.entity.SearchRuleMappingList;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.core.RequestMappingHandlerAdapterPlus;
import com.zhangqin.framework.web.gpe.ParameterRequestWrapper;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.utils.AnalysisUtils;
import com.zhangqin.framework.web.gpe.utils.GpeUtils;

/**
 * 分页查下方法
 * 
 * @author zhangqin
 *
 */
public class FindListPageMethodHandler extends AbstractGpeMethodHandler<PageInfo<Map<String, Object>>> {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(FindListPageMethodHandler.class);

	/**
	 * 构造方法
	 * 
	 * @param annotation
	 * @param mapping
	 * @param paths
	 * @param targetMethod
	 */
	public FindListPageMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, Method proxyMethod,
			String... paths) {
		super(annotation, mapping, proxyMethod, paths);
	}

	@Override
	@ResponseBody
	public PageInfo<Map<String, Object>> handler(HttpServletRequest request, HttpServletResponse response) {

		request = searchRuleMappingHandle(request);

		Class<?> targetClass = getProxyMethod().getDeclaringClass();
		Object obj = SpringContextUtils.getBean(targetClass);

		ServletInvocableHandlerMethod handler = new ServletInvocableHandlerMethod(obj, getProxyMethod());
		RequestMappingHandlerAdapterPlus adapter = SpringContextUtils.getBean(RequestMappingHandlerAdapterPlus.class);
		try {
			PageInfo<?> pageInfo = (PageInfo<?>) adapter.invokeForRequest(request, response, handler);
			if (null == pageInfo || CollectionUtils.isEmpty(pageInfo.getList())) {
				return new PageInfo<Map<String, Object>>();
			}

			List<Map<String, Object>> mapList = transformProcess(getViewObject(), pageInfo.getList());

			PageInfo<Map<String, Object>> newPage = new PageInfo<Map<String, Object>>();
			BeanMapper.copy(pageInfo, newPage);
			newPage.setList(mapList);

			return newPage;
		} catch (Exception e) {
			logger.error("处理分页查询失败，{}", e);
		}

		return new PageInfo<Map<String, Object>>();
	}

	/**
	 * 处理查询规则映射 </br>
	 * 普通方式提交，数字格式被动转化如下：</br>
	 * rules[0][field]: dictCode </br>
	 * rules[0][rule]: EQ </br>
	 * rules[1][field]: dictName </br>
	 * rules[1][rule]: LK
	 * 
	 * @param request
	 */
	private HttpServletRequest searchRuleMappingHandle(HttpServletRequest request) {
		// 获取所有的查询请求参数
		HashMap<String, String[]> paramMap = new HashMap<String, String[]>(request.getParameterMap());

		// 查询规则映射Map
		HashMap<String, String[]> ruleParamMap = Maps.newHashMap();

		// 原请求参数中，去除格式为：rules[n][x]的参数
		Iterator<Entry<String, String[]>> iterator = paramMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String[]> entry = iterator.next();
			if (entry.getKey().indexOf("rules[") == 0) {
				ruleParamMap.put(entry.getKey(), entry.getValue());
				iterator.remove();
			}
		}

		// 规则数量 = ruleParamMap.size()/2
		int ruleSize = ruleParamMap.size() / 2;
		SearchRuleMappingList mappingList = new SearchRuleMappingList();
		for (int i = 0; i < ruleSize; i++) {
			String field = ruleParamMap.get("rules[" + i + "][field]")[0];
			String rule = ruleParamMap.get("rules[" + i + "][rule]")[0];

			CompareOperator operator = (CompareOperator) EnumUtils.getEnumObj(CompareOperator.class, rule);
			mappingList.add(field,operator);
		}
		
		// 查询规则映射列表存在记录，则添加rules参数
		if(CollectionUtils.isNotEmpty(mappingList)) {
			paramMap.put("rules", new String[] { JsonMapper.toJson(mappingList) });
			// 修改request对象
			HttpServletRequest req = (HttpServletRequest) request;
			ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(req, paramMap);
			return requestWrapper;
		}
		
		return request;
	}

	/**
	 * 转换处理
	 * 
	 * @param clazz
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> transformProcess(Class<?> clazz, List<?> list) {
		GpeBean gpe = GpeUtils.getGpeBean(clazz);
		Map<String, GpeFieldBean> fieldMap = gpe.getFields().parallelStream()
				.collect(Collectors.toMap(GpeFieldBean::getField, Function.identity()));

		List<Field> originalFields = AnalysisUtils.analysisOriginalFields(clazz, true);
		List<Map<String, Object>> mapList = Lists.newArrayList();

		list.stream().forEach(data -> {
			Map<String, Object> map = Maps.newHashMap();
			originalFields.forEach(field -> {
				try {
					String fieldName = field.getName();
					Object value = field.get(data);
					// 枚举处理
					enumProcess(fieldMap.get(fieldName), field, map, value);

					// 格式化处理
					formatProcess(fieldMap.get(fieldName), field, map, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			});
			mapList.add(map);
		});
		return mapList;
	}

	/**
	 * 枚举处理
	 * 
	 * @param gpeField
	 * @param originalField
	 * @param map
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	private void enumProcess(GpeFieldBean gpeField, Field originalField, Map<String, Object> map, Object value) {
		// gpeField是否为空
		if (null == gpeField) {
			return;
		}

		// 字段类型
		Class<?> type = originalField.getType();

		if (BaseEnum.class.isAssignableFrom(type)) {
			try {
				BaseEnum<? extends Enum<?>, ?> en = (BaseEnum<? extends Enum<?>, ?>) value;
				if (null != en) {
					map.put(originalField.getName() + "Desc", en.getDesc());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 格式化处理
	 * 
	 * @param gpeField
	 * @param originalField
	 * @param value
	 * @return
	 */
	private void formatProcess(GpeFieldBean gpeField, Field originalField, Map<String, Object> map, Object value) {
		// gpeField是否为空
		if (null == gpeField) {
			map.put(originalField.getName(), value);
			return;
		}

		// 网格格式化
		String gformat = gpeField.getGformat();
		if (StringUtils.isBlank(gformat)) {
			map.put(originalField.getName(), value);
			return;
		}

		// value为空
		if (null == value) {
			map.put(originalField.getName(), value);
			return;
		}

		// 字段类型
		Class<?> type = originalField.getType();

		// 日期类型
		if (Date.class.isAssignableFrom(type)) {
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat(gformat);
			String datestr = sdf.format(value);
			map.put(originalField.getName(), datestr);
			return;
		}

		// BigDecimal类型
		if (BigDecimal.class.isAssignableFrom(type)) {
			// 格式化BigDecimal
			int scale = gformat.substring(gformat.lastIndexOf(".") - 1).length();
			BigDecimal decimal = (BigDecimal) value;
			String decimalstr = decimal.setScale(scale, RoundingMode.HALF_UP).toString();
			map.put(originalField.getName(), decimalstr);
			return;
		}

		map.put(originalField.getName(), value);
	}
}
