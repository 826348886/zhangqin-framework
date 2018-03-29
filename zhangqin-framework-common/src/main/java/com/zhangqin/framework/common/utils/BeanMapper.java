package com.zhangqin.framework.common.utils;

import java.util.Collection;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import com.google.common.collect.Lists;

/**
 * 
 * @Description: 简单封装Dozer, 实现深度转换Bean<->Bean的Mapper
 * 实现:
 * 1. 持有Mapper的单例.
 * 2. 返回值类型转换.
 * 3. 批量转换Collection中的所有对象.
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 * 
 * @author zhangqin
 * @date 2018年1月15日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class BeanMapper {
	/**
	 * 持有Dozer单例, 避免重复创建DozerMapper消耗资源
	 */
	private static DozerBeanMapper dozer = new DozerBeanMapper();

	/**
	 * 
	 * @Description: 基于Dozer转换对象的类型
	 * @param source
	 * @param destinationClass
	 * @return T  
	 * @author zhangqin
	 * @date 2018年1月15日
	 */
	public static <T> T map(Object source, Class<T> destinationClass) {
		if (source == null) {
			return null;
		}
		return dozer.map(source, destinationClass);
	}

	/**
	 * 
	 * @Description: 基于Dozer转换Collection中对象的类型
	 * @param sourceList
	 * @param destinationClass
	 * @return List<T>  
	 * @author zhangqin
	 * @date 2018年1月15日
	 */
	public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass) {
		List<T> destinationList = Lists.newArrayList();
		for (Object source : sourceList) {
			T destinationObject = dozer.map(source, destinationClass);
			destinationList.add(destinationObject);
		}
		return destinationList;
	}

	/**
	 * 
	 * @Description: 基于Dozer将对象A的值拷贝到对象B中(强制复制，null或空字符串等将都会复制到B中)
	 * @param source
	 * @param destinationObject   
	 * @return void  
	 * @author zhangqin
	 * @date 2018年1月15日
	 */
	public static void copy(Object source, Object destinationObject) {
		dozer.map(source, destinationObject);
	}
	
	/**
	 * 
	 * @Description: 基于Dozer将对象A的值拷贝到对象B中（不拷贝null或空字符串）
	 * @param source
	 * @param destinationObject   
	 * @return void  
	 * @author zhangqin
	 * @date 2018年1月15日
	 */
	public static void copySkipNullAndEmpty(Object source, Object destinationObject) {
		DozerBeanMapper dozer = new DozerBeanMapper();
		dozer.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), destinationObject.getClass(), TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false));
            }
        });
		dozer.map(source, destinationObject);
	}
	
	/**
	 * 对象深度拷贝</br>
	 * 实现对象克隆有两种方式：
	 * 1). 实现Cloneable接口并重写Object类中的clone()方法；
	 * 2). 实现Serializable接口，通过对象的序列化和反序列化实现克隆，可以实现真正的深度克隆。
	 * 此处使用第二种，避免遗漏克隆bean中的成员变量
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T bean) {
		try {
			return (T) JsonMapper.fromJson(JsonMapper.toJson(bean),bean.getClass());
		} catch (Exception e) {
		}
		return null;
	}
}