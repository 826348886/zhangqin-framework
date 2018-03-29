package com.zhangqin.framework.web.gpe;

import com.zhangqin.framework.web.common.utils.SpringContextUtils;
import com.zhangqin.framework.web.gpe.bean.GpeGlobalPropertyBean;
import com.zhangqin.framework.web.gpe.enums.TextAlign;

/**
 * GPE默认属性配置策略
 * @author zhangqin
 *
 */
public class GpeDefaultPropertyStrategy implements GpePropertyStrategy {

	@Override
	public GpeGlobalPropertyBean getPropertyBean() {
		GpeGlobalPropertyBean defaultGpeProperty = SpringContextUtils.getBean("defaultGpeProperty");
		if (null == defaultGpeProperty) {
			defaultGpeProperty = getDefaultProperty();
		}
		return defaultGpeProperty;
	}

	/**
	 * 获得默认属性配置
	 * @return
	 */
	private GpeGlobalPropertyBean getDefaultProperty() {
		GpeGlobalPropertyBean property = new GpeGlobalPropertyBean();
		property.setGshow(true);
		property.setPshow(true);
		property.setEshow(true);
		property.setTextAlign(TextAlign.LEFT);
		property.setNumericAlign(TextAlign.RIGHT);
		property.setDateAlign(TextAlign.LEFT);
		property.setgDecimalFormat("00.00");
		property.setpDecimalFormat("00.00");
		property.seteDecimalFormat("00.0000");
		property.setgDateFormat("yyyy-MM-dd");
		property.setpDateFormat("yyyy-MM-dd");
		property.seteDateFormat("yyyy-MM-dd HH:mm:ss");
		property.setWidth(100);
		property.setSortable(false);
		return property;
	}

}
