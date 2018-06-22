package com.zhangqin.framework.dal.mapper;

import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SQL工厂类 </br>
 * 通过SQL工厂类及对应的方法生产SQL语句
 * 
 * @author zhagnqin
 *
 */
public class SqlProvider {
	
	public final Logger logger = LoggerFactory.getLogger(SqlProvider.class);
	
	/**
	 * 新增对象，新增所有字段
	 * @param object
	 * @return
	 */
	public String insert(Object object){
		logger.debug("通用insert方法--------start");
		return new SQL() {{
			if(null == object){
				logger.warn("insert 对象为空");
				throw new RuntimeException("insert 对象为空");
			}
		}}.toString();
	}
}
