package com.zhangqin.framework.web.gpe;

import com.zhangqin.framework.web.gpe.bean.GpeGlobalPropertyBean;

/**
 * GPE属性配置策略
 * @author zhangqin
 *
 */
public interface GpePropertyStrategy {
	/**
	 * 
	 * @Description: 获取PropertyBean
	 * @return PropertyBean
	 * @author zhangq
	 * @date 2017年9月25日
	 */
	GpeGlobalPropertyBean getPropertyBean();
}
