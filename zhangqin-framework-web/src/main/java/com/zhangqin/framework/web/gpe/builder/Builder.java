package com.zhangqin.framework.web.gpe.builder;

import com.zhangqin.framework.web.gpe.bean.GpeBean;

/**
 * 抽象建造者，提供GpeBean建造方法及返回结果方法
 * 
 * @author zhangqin
 *
 */
public interface Builder {
	/**
	 * 使用GpeHeader注解解析覆盖GpeBean
	 */
	void coverByHeaderAnnotation();
	
	/**
	 * 根据原始字段初始化GpeBean
	 */
	void initByOriginalFields();

	/**
	 * 使用全局配置覆盖GpeBean
	 */
	void coverByGlobalProperty();

	/**
	 * 使用字段配置覆盖GpeBean
	 */
	void coverByFieldProperty();

	/**
	 * 根据GpeHeader注解解析覆盖GpeBean
	 */
	void coverByFieldAnnotation();

	/**
	 * 使用用户配置覆盖GpeBean
	 */
	void coverByUserProperty();
	
	/**
	 * 数据转换处理
	 */
	void transformProcess();
	
	/**
	 * 过滤没有权限的字段
	 */
	void filterForbidFields();
	
	/**
	 * 获得构建结果
	 * 
	 * @return
	 */
	GpeBean retrieveResult();
}
