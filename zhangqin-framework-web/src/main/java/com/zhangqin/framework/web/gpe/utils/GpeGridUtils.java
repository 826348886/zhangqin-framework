package com.zhangqin.framework.web.gpe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangqin.framework.common.utils.BeanMapper;
import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.bean.result.ColumnResult;
import com.zhangqin.framework.web.gpe.bean.result.GridResult;
import com.zhangqin.framework.web.gpe.enums.UseFor;

public class GpeGridUtils {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GpeGridUtils.class);

	/**
	 * 
	 * <p>
	 * Title: 私有构造函数
	 * </p>
	 * <p>
	 * Description: 成员方法均为静态，禁止new对象
	 * </p>
	 */
	private GpeGridUtils() {
	}

	public static GridResult getGridResult(Class<?> clazz) {
		logger.debug("进入GpeGridUtils->getGridResult(),clazz:{}.", clazz);
		GpeBean bean = GpeUtils.getGpeBean(clazz, UseFor.GRID);

		return getGridResultFromGpeBean(bean);
	}

	private static GridResult getGridResultFromGpeBean(GpeBean bean) {
		// 正常的列
		List<GpeFieldBean> normalList = new ArrayList<GpeFieldBean>();
		// 冻结的列
		List<GpeFieldBean> frozenList = new ArrayList<GpeFieldBean>();

		// 分类放入不同的List中
		bean.getFields().stream().forEach(field -> {
			if (field.isFrozen()) {
				frozenList.add(field);
			} else {
				normalList.add(field);
			}
		});

		// 正常的列转二维数组
		List<List<GpeFieldBean>> normalTwoDimensionalList = GpeUtils.convertToTwoDimensionalList(normalList);
		// 冻结的列转二维数组
		List<List<GpeFieldBean>> frozenTwoDimensionalList = GpeUtils.convertToTwoDimensionalList(frozenList);

		// 正常的列或冻结的列存在有行列合并情况时，二维数组中的第一个数组中未进行列合并的要进行行合并
		if (normalTwoDimensionalList.size() > 1 || frozenTwoDimensionalList.size() > 1) {
			// 处理正常的列
			if (!CollectionUtils.isEmpty(normalTwoDimensionalList)) {
				for (GpeFieldBean b : normalTwoDimensionalList.get(0)) {
					if (b.getColspan() == 0) {
						b.setRowspan(2);
					}
				}
			}

			// 处理冻结的列
			if (!CollectionUtils.isEmpty(frozenTwoDimensionalList)) {
				for (GpeFieldBean b : frozenTwoDimensionalList.get(0)) {
					if (b.getColspan() == 0) {
						b.setRowspan(2);
					}
				}
			}
		}

		// 正常列存在行列合并，冻结的列不存在时，冻结列的二维数组需要添加一个空数组
		if (normalTwoDimensionalList.size() > 1 && frozenTwoDimensionalList.size() == 1) {
			List<GpeFieldBean> list = new ArrayList<GpeFieldBean>();
			frozenTwoDimensionalList.add(list);
		}

		// 冻结列存在行列合并，正常的列不存在时，正常列的二维数组需要添加一个空数组
		if (frozenTwoDimensionalList.size() > 1 && normalTwoDimensionalList.size() == 1) {
			List<GpeFieldBean> list = new ArrayList<GpeFieldBean>();
			normalTwoDimensionalList.add(list);
		}

		// 用一个数组封装这2个二维数组（需要对formatter特殊处理，使用对象或map形式会导致页面JSON.parse()或eval()失败）
		GridResult result = new GridResult();
		result.setColumns(convertToTwoDimensionalGridColumnBeanList(normalTwoDimensionalList));
		result.setForzenColumns(convertToTwoDimensionalGridColumnBeanList(frozenTwoDimensionalList));
		return result;
	}
	
	/**
	 * 
	 * @Description: List<List<FieldBean>> -> List<List<GridColumnBean>>
	 * @param twoDimensionalFieldBeanList
	 * @return List<List<GridColumnBean>>  
	 * @author zhangq
	 * @date 2017年11月1日
	 */
	private static List<List<ColumnResult>> convertToTwoDimensionalGridColumnBeanList(
			List<List<GpeFieldBean>> twoDimensionalFieldBeanList) {
		List<List<ColumnResult>> resultList = new ArrayList<List<ColumnResult>>();
		for (List<GpeFieldBean> list : twoDimensionalFieldBeanList) {
			List<ColumnResult> gridColumnBeanList = new ArrayList<ColumnResult>();
			for (GpeFieldBean fieldBean : list) {
				ColumnResult gridColumnBean = getGridColumnResultFromGpeFieldBean(fieldBean);
				gridColumnBeanList.add(gridColumnBean);
			}
			resultList.add(gridColumnBeanList);
		}
		return resultList;
	}
	
	/**
	 * 
	 * @Description: GpeFieldBean 转为 ColumnResult
	 * @param fieldBean
	 * @return GridColumnResult  
	 * @author zhangqin
	 * @date 2017年9月28日
	 */
	private static ColumnResult getGridColumnResultFromGpeFieldBean(GpeFieldBean fieldBean) {
		// 对象转换
		ColumnResult bean = BeanMapper.map(fieldBean, ColumnResult.class);
		
		// 宽度
		bean.setWidth(fieldBean.getGwidth());
		
		// 对齐方式
		bean.setAlign(fieldBean.getAlign().name().toLowerCase());
		
		// 格式化
		bean.setFormat(fieldBean.getGformat());

		// 拓展坞集合
		if (CollectionUtils.isNotEmpty(fieldBean.getDocks())) {
			List<String> docks = fieldBean.getDocks().parallelStream().map(dock -> dock.name().toUpperCase()).collect(Collectors.toList());
			bean.setDocks(docks);
		}
		return bean;
	}
}
