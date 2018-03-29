package com.zhangqin.framework.web.gpe.builder;

/**
 * 导演者
 * 
 * @author zhangqin
 *
 */
public class Director {
	/**
	 * 创建建造者对象
	 */
	private Builder builder;

	/**
	 * 构造函数，给定建造者对象
	 * 
	 * @param builder
	 *            建造者对象
	 */
	public Director(Builder builder) {
		this.builder = builder;
	}

	/**
	 * 产品构造方法，在该方法内，调用建造方法。
	 */
	public void construct() {
		// 根据原始字段初始化GpeBean
		builder.initByOriginalFields();

		// 使用全局配置覆盖GpeBean
		builder.coverByGlobalProperty();

		// 使用字段配置覆盖GpeBean
		builder.coverByFieldProperty();

		// 使用注解配置覆盖GpeBean
		builder.coverByHeaderAnnotation();
		builder.coverByFieldAnnotation();
		
		// 数据转换处理
		builder.transformProcess();

		// 使用用户配置覆盖GpeBean
		//builder.coverByUserProperty();
		
		builder.filterForbidFields();
	}

}
