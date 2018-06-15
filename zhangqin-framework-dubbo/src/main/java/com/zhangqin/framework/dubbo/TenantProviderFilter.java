package com.zhangqin.framework.dubbo;

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
 * dubbo请求拦截-实现租户设置
 * @author zhangqin
 *
 */
@Activate(group = { Constants.PROVIDER }, order = -9000)
public class TenantProviderFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String tenantId = RpcContext.getContext().getAttachment(BaseConstants.TENANT_ID);
		TenantSelector.setTenantId(tenantId);
		Result result = invoker.invoke(invocation);
		//TenantSelector.remove();
		return result;
	}
}
