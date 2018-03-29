package com.zhangqin.framework.dal.mybatisplus;

import java.util.List;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.Configuration;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;

/**
 * SQL 自动注入逻辑处理器 </br>
 * 1.支持批量新增
 * 
 * @author zhangqin
 *
 */
public class MySqlInjector extends LogicSqlInjector {

	@Override
	public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass,
			Class<?> modelClass, TableInfo table) {
		this.injectBatchInsertSql(mapperClass, modelClass, table);
	}

	/**
	 * <p>
	 * 注入插入 SQL 语句
	 * </p>
	 *
	 * @param selective
	 *            是否选择插入
	 * @param mapperClass
	 * @param modelClass
	 * @param table
	 */
	protected void injectBatchInsertSql(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
		String sqlId = "batchInsert";
		String sqlFormat = "<script>INSERT INTO %s %s VALUES %s</script>";

		/*
		 * INSERT INTO table <trim prefix="(" suffix=")" suffixOverrides=","> xx,xx,
		 * </trim> VALUES <foreach collection="list" item="item" separator="," open=""
		 * close=""> <trim prefix="(" suffix=")" suffixOverrides=",">
		 * #{item.xx},#{item.xx}, </trim> </foreach>
		 */
		KeyGenerator keyGenerator = new NoKeyGenerator();
		StringBuilder fieldBuilder = new StringBuilder();
		StringBuilder placeholderBuilder = new StringBuilder();
		SqlMethod sqlMethod = SqlMethod.INSERT_ONE_ALL_COLUMN;

		fieldBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
		placeholderBuilder.append("\n<foreach collection=\"list\" item=\"item\" separator=\",\" open=\"\" close=\"\">\n");
		placeholderBuilder.append("\n<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n");
		String keyProperty = null;
		String keyColumn = null;

		// 表包含主键处理逻辑,如果不包含主键当普通字段处理
		if (StringUtils.isNotEmpty(table.getKeyProperty())) {
			if (table.getIdType() == IdType.AUTO) {
				/* 自增主键 */
				keyGenerator = new Jdbc3KeyGenerator();
				keyProperty = table.getKeyProperty();
				keyColumn = table.getKeyColumn();
			} else {
				if (null != table.getKeySequence()) {
					keyGenerator = TableInfoHelper.genKeyGenerator(table, builderAssistant, sqlMethod.getMethod(),
							languageDriver);
					keyProperty = table.getKeyProperty();
					keyColumn = table.getKeyColumn();
					fieldBuilder.append(table.getKeyColumn()).append(",");
					placeholderBuilder.append("#{item.").append(table.getKeyProperty()).append("},");
				} else {
					/* 用户输入自定义ID */
					fieldBuilder.append(table.getKeyColumn()).append(",");
					// 正常自定义主键策略
					placeholderBuilder.append("#{item.").append(table.getKeyProperty()).append("},");
				}
			}
		}

		// 是否 IF 标签判断
		List<TableFieldInfo> fieldList = table.getFieldList();
		for (TableFieldInfo fieldInfo : fieldList) {
			if (fieldInfo.getColumn().equals("tenant_id")) {
				continue;
			}
			fieldBuilder.append(fieldInfo.getColumn()).append(",");
			placeholderBuilder.append("#{item.").append(fieldInfo.getEl()).append("},");
		}
		fieldBuilder.append("\n</trim>");
		placeholderBuilder.append("\n</trim>");
		placeholderBuilder.append("\n</foreach>");

		String sql = String.format(sqlFormat, table.getTableName(), fieldBuilder.toString(),
				placeholderBuilder.toString());
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		this.addInsertMappedStatement(mapperClass, modelClass, sqlId, sqlSource, keyGenerator, keyProperty, keyColumn);
	}
}
