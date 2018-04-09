package com.zhangqin.framework.web.gpe;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;

import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.utils.EnumUtils;
import com.zhangqin.framework.web.gpe.bean.GpeFieldPropertyBean;

public class GpeFieldPropertyConfigurer extends PropertyPlaceholderConfigurer {

	/**
	 * 存取properties配置文件key-value结果
	 */
	private Properties props;

	/**
	 * 
	 * <p>
	 * Title: 构造函数
	 * </p>
	 * <p>
	 * Description: 初始设置
	 * </p>
	 */
	public GpeFieldPropertyConfigurer() {
		super();
		this.setIgnoreUnresolvablePlaceholders(true);
		this.setIgnoreResourceNotFound(true);
		this.setLocation(new ClassPathResource("gpe.properties"));
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory,
	 *      java.util.Properties)
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		this.props = props;

		// 读取properties文件
		readProperties();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.PropertyResourceConfigurer#getOrder()
	 */
	@Override
	public int getOrder() {
		return LOWEST_PRECEDENCE;
	}

	/**
	 * 读取配置文件
	 */
	@SuppressWarnings("unchecked")
	public void readProperties() {
		// 遍历所有的配置,语法：gpe.字段名={属性名:属性值,...}
		props.entrySet().parallelStream().forEach(entry -> {
			// Key，即：gpe.字段名
			String key = entry.getKey().toString().trim();
			// Value，即：{属性名:属性值,...}
			String value = entry.getValue().toString().trim();

			// 字段属性配置Bean
			GpeFieldPropertyBean bean = new GpeFieldPropertyBean();

			// 字段名
			String fieldName = key.replaceFirst("gpe.", "").trim();
			bean.setField(fieldName);

			// 遍历所有属性，赋值给bean
			Arrays.asList(value.substring(1, value.length() - 1).replace("，", ",").split(",")).parallelStream()
					.forEach(property -> {
						property = property.replace("：", ":");
						//String[] array = property.replace("：", ":").split(":");
						
						String propertyName = property.substring(0, property.indexOf(":"));
						String propertyValue = property.substring(property.indexOf(":") + 1);

						Field field = ReflectionUtils.findField(GpeFieldPropertyBean.class, propertyName);
						if (null != field) {
							field.setAccessible(true);
							
							
							try {
								String typeName = field.getType().getSimpleName();
								if ("boolean".equals(typeName) || "boolean".equals(typeName)) {
									field.setBoolean(bean, Boolean.parseBoolean(propertyValue));
								} else if ("int".equals(typeName) || "Integer".equals(typeName)) {
									// field.setInt(bean, Integer.valueOf(propertyValue));
									ReflectionUtils.setField(field, bean, Integer.valueOf(propertyValue));
								} else if("String".equals(typeName)) {
									field.set(bean, propertyValue);
								} else if(BaseEnum.class.isAssignableFrom(field.getType())){
									Class<? extends BaseEnum<? extends Enum<?>, String>> enumClass = (Class<? extends BaseEnum<? extends Enum<?>, String>>) field
											.getType();
									BaseEnum<? extends Enum<?>, String> enumObj = EnumUtils.getEnumObjT(enumClass, propertyValue);
									ReflectionUtils.setField(field, bean, enumObj);
								}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
							// ReflectionUtils.setField(field, bean, propertyValue);
						}
					});

			// 添加到缓存
			GpeCacheManager.setFieldProperty(fieldName, bean);
		});
	}
}
