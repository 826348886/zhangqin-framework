package com.zhangqin.framework.web.gpe.analysis;

import java.util.Arrays;
import java.util.List;

import com.zhangqin.framework.web.gpe.bean.GpeBean;

/**
 * 解析链配置
 * @author zhangqin
 *
 */
public class AnalysisChain {

	public static List<AnalysisBuilder> buildAnalysisChain(GpeBean gpe){
		// 解析链
		List<AnalysisBuilder> analysisChainList = Arrays.asList(new AnalysisBuilder[] {
			
		});
		
		analysisChainList.forEach(action->{
			action.invoke(gpe);
		});
		return null;
	}
}
