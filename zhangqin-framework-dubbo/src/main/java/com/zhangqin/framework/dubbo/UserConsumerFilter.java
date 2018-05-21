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
import com.zhangqin.framework.common.dubbo.UserSelector;

/**
 * dubbo请求拦截,设置用户信息
 * @author zhangqin
 *
 */
@Activate(group = { Constants.CONSUMER }, order = -9000)
public class UserConsumerFilter implements Filter {

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
		String userId = RpcContext.getContext().getAttachment(BaseConstants.USER_ID);
		if (StringUtils.isNotEmpty(userId)) {
			RpcContext.getContext().setAttachment(BaseConstants.USER_ID, userId);
		} else {
			RpcContext.getContext().setAttachment(BaseConstants.USER_ID, UserSelector.getUserId());
		}
		return invoker.invoke(invocation);
	}
}
