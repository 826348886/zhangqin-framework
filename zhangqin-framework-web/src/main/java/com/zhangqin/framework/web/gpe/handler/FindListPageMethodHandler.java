package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
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
		Class<?> targetClass = getProxyMethod().getDeclaringClass();
		Object obj = SpringContextUtils.getBean(targetClass);

		ServletInvocableHandlerMethod handler = new ServletInvocableHandlerMethod(obj, getProxyMethod());
		GpeRequestMappingHandlerAdapter adapter = SpringContextUtils.getBean(GpeRequestMappingHandlerAdapter.class);
		try {
			PageInfo<?> pageInfo = (PageInfo<?>) adapter.invokeForRequest(request, response, handler);
			if (null == pageInfo || CollectionUtils.isEmpty(pageInfo.getList())) {
				return new PageInfo<Map<String, Object>>();
			}
			
			List<Map<String, Object>> mapList = transformProcess(getViewObject(),pageInfo.getList());

			PageInfo<Map<String, Object>> newPage = new PageInfo<Map<String, Object>>();
			BeanMapper.copy(pageInfo, newPage);
			newPage.setList(mapList);

			return newPage;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new PageInfo<Map<String, Object>>();
	}
	
	/**
	 * 转换处理
	 * @param clazz
	 * @param list
	 * @return
	 */
	private List<Map<String, Object>> transformProcess(Class<?> clazz, List<?> list){
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
					enumProcess(fieldMap.get(fieldName), field, map, value);
					
					// 格式化处理
					formatProcess(fieldMap.get(fieldName), field,map, value);
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
	 * @param gpeField
	 * @param originalField
	 * @param map
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	private void enumProcess(GpeFieldBean gpeField, Field originalField,Map<String, Object> map, Object value) {
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
	 * @param gpeField
	 * @param originalField
	 * @param value
	 * @return
	 */
	private void formatProcess(GpeFieldBean gpeField, Field originalField,Map<String, Object> map, Object value) {
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
