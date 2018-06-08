package com.zhangqin.framework.dubbo;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.zhangqin.framework.common.dubbo.TenantSelector;

/**
 * dubbo请求拦截,设置租户信息
 * @author zhangqin
 *
 */
@Activate(group = { Constants.CONSUMER }, order = -9000)
public class TenantConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String tenantId = RpcContext.getContext().getAttachment(BaseConstants.TENANT_ID);
		if (StringUtils.isNotEmpty(tenantId)) {
			RpcContext.getContext().setAttachment(BaseConstants.TENANT_ID, tenantId);
		} else {
			RpcContext.getContext().setAttachment(BaseConstants.TENANT_ID, TenantSelector.getTenantId());
		}
		return invoker.invoke(invocation);
	}
}
