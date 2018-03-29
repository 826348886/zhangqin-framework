package com.zhangqin.framework.web.gpe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldPropertyBean;
import com.zhangqin.framework.web.gpe.bean.GpeGlobalPropertyBean;

/**
 * GPE缓存管理
 * 
 * @author zhangqin
 *
 */
public class GpeCacheManager {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GpeCacheManager.class);

	/**
	 * 包含所有@GpeHeader、@GpeField、@GpeProperty注解的Map集合</br>
	 * 该Map是在项目运行中用户实例化对象调用Gpe时put的，故使用ConcurrentMap保障线程安全。</br>
	 * key:实体类的Class</br>
	 * value:实体类注解包装的bean对象
	 */
	private static ConcurrentMap<Class<?>, GpeBean> classFieldsCache = new ConcurrentHashMap<Class<?>, GpeBean>();
	
	/**
	 * 实体类-实体类所有属性字段集合的Cache
	 * key：实体类的Class
	 * value：所有的属性字段的Map集合。map的key：字段名，map的value：原始字段对象
	 */
	private static ConcurrentMap<Class<?>,ConcurrentMap<String,Field>> classOriginalFieldsCache = new ConcurrentHashMap<Class<?>, ConcurrentMap<String,Field>>();

	private static ConcurrentMap<Class<?>, GpeGlobalPropertyBean> properties = new ConcurrentHashMap<Class<?>, GpeGlobalPropertyBean>();

	/**
	 * 字段属性配置集合
	 * key:字段名称
	 * value:字段属性配置
	 */
	private static ConcurrentMap<String,GpeFieldPropertyBean> fieldProperties = new ConcurrentHashMap<String,GpeFieldPropertyBean>();
	
	public static final ThreadLocal<String> METHOD_THREAD_LOCAL = new ThreadLocal<String>();

	
	/**
	 * 私有化构造方法，不可new对象
	 */
	private GpeCacheManager() {

	}

	/**
	 * 获取一个GpeBean的副本
	 * 
	 * @param clazz
	 * @return
	 */
	public static GpeBean getCopyBean(Class<?> clazz) {
		if (classFieldsCache.containsKey(clazz)) {
			GpeBean bean = classFieldsCache.get(clazz);
			return BeanMapper.clone(bean);
		}
		return null;
	}

	/**
	 * 制作一个副本，并存入缓存
	 * 
	 * @param clazz
	 * @param bean
	 */
	public static void setCopyBean(Class<?> clazz, GpeBean bean) {
		GpeBean copy = BeanMapper.clone(bean);
		if (classFieldsCache.containsKey(clazz)) {
			classFieldsCache.remove(clazz);
		}
		classFieldsCache.put(clazz, copy);
	}

	/**
	 * GPE属性配置Bean
	 * 
	 * @param clazz
	 * @return
	 */
	public static GpeGlobalPropertyBean getProperty(Class<? extends GpePropertyStrategy> clazz) {
		// 缓存中存在，从缓存中取值
		if (properties.containsKey(clazz)) {
			return properties.get(clazz);
		}

		// 缓存中不存在，则根据class实例化一个策略，根据策略获取值
		try {
			// 实例化
			GpePropertyStrategy strategy = clazz.newInstance();
			// 获取属性
			GpeGlobalPropertyBean bean = strategy.getPropertyBean();
			// 添加到缓存
			properties.put(clazz, bean);
			return bean;
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error("实例化{}失败，{}。", clazz, e);
		}
		return null;
	}

	/**
	 * 添加字段属性配置
	 * @param field
	 * @param bean
	 */
	public static void setFieldProperty(String field,GpeFieldPropertyBean bean) {
		if(fieldProperties.containsKey(field)) {
			fieldProperties.remove(field);
		}
		fieldProperties.put(field, bean);
	}
	
	/**
	 * 获取字段属性配置
	 * @param field
	 */
	public static GpeFieldPropertyBean getFieldProperty(String field) {
		if(!fieldProperties.containsKey(field)) {
			return null;
		}
		return fieldProperties.get(field);
	}
	
	/**
	 * 获取实体类的原始字段列表
	 * @param clazz
	 * @return
	 */
	public static ConcurrentMap<String,Field> getOriginalFields(Class<?> clazz){
		if (classOriginalFieldsCache.containsKey(clazz)) {
			ConcurrentMap<String,Field> map = classOriginalFieldsCache.get(clazz);
			return map;
		}
		return null;
	}
	
	/**
	 * 设置实体类的原始字段列表
	 * @param clazz
	 * @param originalFields
	 */
	public static void setOriginalFields(Class<?> clazz,ConcurrentMap<String,Field> originalFields) {
		if (classOriginalFieldsCache.containsKey(clazz)) {
			classOriginalFieldsCache.remove(clazz);
		}
		classOriginalFieldsCache.put(clazz, originalFields);
	}
	
	 public static void setMethodGenericInfo(String genericInfo) {
		 METHOD_THREAD_LOCAL.set(genericInfo);
	 }
	
	 public static String getMethodGenericInfo() {
		 return METHOD_THREAD_LOCAL.get();
	 }
}
