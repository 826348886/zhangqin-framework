package com.zhangqin.framework.dal.mybatisplus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Intercepts({
		@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class, Integer.class }) })
public class SchemaInterceptor implements Interceptor {

	private static final Logger logger = LoggerFactory.getLogger(SchemaInterceptor.class);

	private static final String SQL_SET_SCHEMA = "SET search_path TO \"%s\"";

	private String dialectType;

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if ("mysql".equals(dialectType)) {
			return invocation.proceed();
		}
		String schema = TenantSelector.getTenantId();
		if (StringUtils.isNotEmpty(schema)) {
			Connection connection = (Connection) invocation.getArgs()[0];
			// 设置schema
			String setSechemaSql = String.format(SQL_SET_SCHEMA, schema);
			try (PreparedStatement statement = connection.prepareStatement(setSechemaSql)) {
				statement.execute();
			} catch (Exception e) {
				logger.error("Error: Method SET search_path execution error ! ", e);
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		String dialectType = properties.getProperty("dialectType");
		if (StringUtils.isNotEmpty(dialectType)) {
			this.setDialectType(dialectType);
		}
	}

	public String getDialectType() {
		return dialectType;
	}

	public void setDialectType(String dialectType) {
		this.dialectType = dialectType;
	}

}