package com.zhangqin.framework.service.dubbo;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.zhangqin.framework.common.BaseConstants;
import com.zhangqin.framework.common.dubbo.TenantSelector;
import com.zhangqin.framework.common.dubbo.UserSelector;

/**
 * dubbo请求拦截-实现用户设置
 * @author zhangqin
 *
 */
@Activate(group = { Constants.PROVIDER }, order = -9000)
public class UserProviderFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String userId = RpcContext.getContext().getAttachment(BaseConstants.USER_ID);
		UserSelector.setUserId(userId);
		Result result = invoker.invoke(invocation);
		TenantSelector.remove();
		return result;
	}
}
