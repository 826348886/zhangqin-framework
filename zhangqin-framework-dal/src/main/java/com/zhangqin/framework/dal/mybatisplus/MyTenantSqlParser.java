package com.zhangqin.framework.dal.mybatisplus;

import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.parser.tenant.TenantHandler;
import com.baomidou.mybatisplus.plugins.parser.tenant.TenantSqlParser;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;

public class MyTenantSqlParser extends TenantSqlParser {

	/**
	 * <p>
	 * insert 语句处理
	 * </p>
	 */
	@Override
	public void processInsert(Insert insert) {
		TenantHandler tenantHandler = this.getTenantHandler();
		if (tenantHandler.doTableFilter(insert.getTable().getName())) {
			// 过滤退出执行
			return;
		}
		insert.getColumns().add(new Column(tenantHandler.getTenantIdColumn()));
		if (insert.getSelect() != null) {
			processPlainSelect((PlainSelect) insert.getSelect().getSelectBody(), true);
		} else if (insert.getItemsList() != null) {
			if (insert.getItemsList() instanceof ExpressionList) {
				((ExpressionList) insert.getItemsList()).getExpressions().add(tenantHandler.getTenantId());
			} else if (insert.getItemsList() instanceof MultiExpressionList) {
				for (ExpressionList exprList : ((MultiExpressionList) insert.getItemsList()).getExprList()) {
					exprList.getExpressions().add(tenantHandler.getTenantId());
				}
			}
		} else {
			throw new MybatisPlusException(
					"Failed to process multiple-table update, please exclude the tableName or statementId");
		}
	}
}
