package com.zhangqin.framework.web.gpe.handler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.utils.EnumUtils;
import com.zhangqin.framework.common.utils.JsonMapper;
import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.gpe.GpeCacheManager;
import com.zhangqin.framework.web.gpe.annotation.GpeField;
import com.zhangqin.framework.web.gpe.annotation.GpeRequestMapping;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.utils.GpeUtils;

/**
 * 分页查下方法
 * 
 * @author zhangqin
 *
 */
public class FindListPageMethodHandler extends AbstractGpeMethodHandler<PageInfo<Map<String, Object>>> {
	/**
	 * 目标方法
	 */
	private Method targetMethod;

	/**
	 * 构造方法
	 * 
	 * @param annotation
	 * @param mapping
	 * @param paths
	 */
	private FindListPageMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, String[] paths) {
		super(annotation, mapping, paths);
	}

	/**
	 * 构造方法
	 * 
	 * @param annotation
	 * @param mapping
	 * @param paths
	 * @param targetMethod
	 */
	public FindListPageMethodHandler(GpeRequestMapping annotation, RequestMappingInfo mapping, String[] paths,
			Method targetMethod) {
		super(annotation, mapping, paths);
		this.targetMethod = targetMethod;
	}

	@Override
	@ResponseBody
	public PageInfo<Map<String, Object>> handler(HttpServletRequest request, HttpServletResponse response) {

		Class<?> targetClass = this.targetMethod.getDeclaringClass();
		Object obj = SpringContextUtils.getBean(targetClass);

		ServletInvocableHandlerMethod handler = new ServletInvocableHandlerMethod(obj, targetMethod);
		GpeRequestMappingHandlerAdapter adapter = SpringContextUtils.getBean(GpeRequestMappingHandlerAdapter.class);
		try {
			PageInfo<?> pageInfo = (PageInfo<?>) adapter.invokeForRequest(request, response, handler);

			// 对象集合转为Map集合
			List<Map<String, Object>> mapList = pageInfo.getList().parallelStream()
					.map(new Function<Object, Map<String, Object>>() {

						@SuppressWarnings("unchecked")
						@Override
						public Map<String, Object> apply(Object item) {
							String json = JsonMapper.toJson(item);
							return JsonMapper.fromJson(json, Map.class);
						}
					}).collect(Collectors.toList());

			mapList = enumHandler(getViewObject(), mapList);

			return new PageInfo<Map<String, Object>>(mapList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new PageInfo<Map<String, Object>>();
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> enumHandler(Class<?> clazz, List<Map<String, Object>> list) {
		// 所有字段的Map集合，包含父类
		Map<String, Field> originalFieldMap = new HashMap<String, Field>();
		Class<?> tempClass = clazz;
		while (null != tempClass) {
			// 获取tempClass的所有Field
			Map<String, Field> map = Arrays.asList(tempClass.getDeclaredFields()).parallelStream().peek(field -> {
				field.setAccessible(true);
			}).collect(Collectors.toMap(field -> field.getName(), field -> field));
			originalFieldMap.putAll(map);

			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}
		
		GpeBean gpe = GpeUtils.getGpeBean(clazz);
		Map<String,GpeFieldBean> fieldMap = gpe.getFields().parallelStream().collect(Collectors.toMap(GpeFieldBean::getField, Function.identity()));

		// 遍历转换所有字典
		list = list.parallelStream().peek(row -> {
			Map<String, Object> needAdd = Maps.newHashMap();
			originalFieldMap.values().forEach(field -> {
				
				if(BaseEnum.class.isAssignableFrom(field.getType())){
					try {
						
						Object value = ((LinkedHashMap<String,Object>)row.get(field.getName())).get("value");

						Class<? extends BaseEnum<? extends Enum<?>, ?>> enumClass = (Class<? extends BaseEnum<? extends Enum<?>, ?>>) field
								.getType();

						String desc = EnumUtils.getEnumDesc(enumClass, value);

						needAdd.put(field.getName() + "Desc", desc);
						
						
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
			

			// 使用迭代器，内部进行格式化处理
			Iterator<Entry<String, Object>> iterator = row.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, Object> entry = iterator.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				
				// 获取原始字段
				Field originalField = GpeCacheManager.getOriginalFields(clazz).get(key);
				
				GpeField annotation = originalField.getAnnotation(GpeField.class);
				// 集合处理，指定由哪一个Handle处理集合类型的字段
//				Class<? extends GpeFieldProcessor> processorClass = annotation.processor();
//				if (!processorClass.equals(GpeFieldProcessor.class)) {
//					GpeFieldProcessor collectionHandle = SpringContextUtils.getBean(processorClass);
//					try {
//						Object obj = originalField.get(value);
//						Map<String,Object> hmap = collectionHandle.valueProcessor(obj);
//						row.putAll(hmap);
//					} catch (IllegalArgumentException | IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
				
				if (fieldMap.containsKey(key) && originalFieldMap.containsKey(key)) {
					String gformat = fieldMap.get(key).getGformat();
					if(StringUtils.isBlank(gformat)) {
						continue;
					}
					// 字段类型
					Class<?> type = originalFieldMap.get(key).getType();
					
					// 日期类型
					if(Date.class.isAssignableFrom(type)) {
						// 格式化日期
						Long date = (Long) value;
						SimpleDateFormat sdf = new SimpleDateFormat(gformat);
						String datestr = sdf.format(date);
						row.put(key, datestr);
					}
					
					// BigDecimal类型
					if(BigDecimal.class.isAssignableFrom(type)) {
						// 格式化BigDecimal
						int scale = gformat.substring(gformat.lastIndexOf(".") - 1).length();
						BigDecimal decimal = BigDecimal.valueOf((Double) value);
						String decimalstr = decimal.setScale(scale, RoundingMode.HALF_UP).toString();
						row.put(key, decimalstr);
					}
				}
			}

			row.putAll(needAdd);
		}).collect(Collectors.toList());
		return list;
	}
}
