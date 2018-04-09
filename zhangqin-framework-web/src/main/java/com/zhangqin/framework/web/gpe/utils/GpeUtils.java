package com.zhangqin.framework.web.gpe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangqin.framework.web.gpe.bean.GpeBean;
import com.zhangqin.framework.web.gpe.bean.GpeFieldBean;
import com.zhangqin.framework.web.gpe.builder.Builder;
import com.zhangqin.framework.web.gpe.builder.ConcreteBuilder;
import com.zhangqin.framework.web.gpe.builder.Director;
import com.zhangqin.framework.web.gpe.enums.TextAlign;
import com.zhangqin.framework.web.gpe.enums.UseFor;

public class GpeUtils {

	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(GpeUtils.class);

	/**
	 * 
	 * <p>
	 * Title: 私有构造函数
	 * </p>
	 * <p>
	 * Description: 成员方法均为静态，禁止new对象
	 * </p>
	 */
	private GpeUtils() {

	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param clazz
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getGpeBean(Class<?> clazz) {
		return getOwnerGpeBean(null, clazz, null, UseFor.ALL);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param clazz
	 * @param useFor
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getGpeBean(Class<?> clazz, UseFor useFor) {
		return getOwnerGpeBean(null, clazz, null, useFor);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param clazz
	 * @param forbidSet
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getGpeBean(Class<?> clazz, Set<String> forbidSet) {
		return getOwnerGpeBean(null, clazz, forbidSet, UseFor.ALL);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param clazz
	 * @param forbidSet
	 * @param useFor
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getGpeBean(Class<?> clazz, Set<String> forbidSet, UseFor useFor) {
		return getOwnerGpeBean(null, clazz, forbidSet, useFor);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param clazz
	 * @param userId
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getOwnerGpeBean(String userId, Class<?> clazz) {
		return getOwnerGpeBean(userId, clazz, null, UseFor.ALL);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param userId
	 * @param clazz
	 * @param useFor
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getOwnerGpeBean(String userId, Class<?> clazz, UseFor useFor) {
		return getOwnerGpeBean(userId, clazz, null, useFor);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param userId
	 * @param clazz
	 * @param forbidSet
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getOwnerGpeBean(String userId, Class<?> clazz, Set<String> forbidSet) {
		return getOwnerGpeBean(userId, clazz, forbidSet, UseFor.ALL);
	}

	/**
	 * 
	 * @Description: 获取GpeBean对象
	 * @param userId
	 * @param clazz
	 * @param forbidSet
	 * @param useFor
	 * @return GpeBean
	 * @author zhangq
	 * @date 2017年9月22日
	 */
	public static GpeBean getOwnerGpeBean(String userId, Class<?> clazz, Set<String> forbidSet, UseFor useFor) {
		logger.debug("进入GpeUtils->getOwnerGpeBean(),userId,:{},clazz:{},forbidSet:{},useFor:{}.", userId, clazz,
				forbidSet, useFor);
		
		// 创建具体建造者对象
		Builder builder = new ConcreteBuilder(clazz);
		// 创造导演者角色，给定建造者对象
		Director director = new Director(builder);
		// 调用导演者角色，创建产品零件
		director.construct();
		GpeBean bean = builder.retrieveResult();

		return bean;
	}

	/**
	 * 
	 * @Description: List<FieldBean> 转为 List<List<ColumnBean>>
	 * @param list
	 * @return List<List<ColumnBean>>
	 * @author zhangq
	 * @date 2017年9月28日
	 */
	public static List<List<GpeFieldBean>> convertToTwoDimensionalList(List<GpeFieldBean> list) {
		// ColumnBean的二维集合，里面最多包含2个集合
		List<List<GpeFieldBean>> resultList = new ArrayList<List<GpeFieldBean>>();
		// 第一个集合
		List<GpeFieldBean> firstList = new ArrayList<GpeFieldBean>();
		// 第二个集合
		List<GpeFieldBean> secondList = new ArrayList<GpeFieldBean>();

		// 集合为空，直接返回
		if (CollectionUtils.isEmpty(list)) {
			return resultList;
		}

		// 上一个bean
		GpeFieldBean prevBean = null;
		// 最后一个bean
		GpeFieldBean lastBean = list.get(list.size() - 1);

		// 重复列表，中止重复时清空
		List<GpeFieldBean> repeatList = new ArrayList<GpeFieldBean>();

		// 循环处理
		for (GpeFieldBean currBean : list) {
			// 是否为最后一个bean
			boolean isLastBean = currBean.equals(lastBean);

			// 第一个bean
			if (null == prevBean) {
				// 既是第一个，也是最后一个，将当前bean添加到第1个list
				if (isLastBean) {
					// 添加到第1个list
					firstList.add(currBean);
					break;
				}

				// 本次不做处理，待到合并结束后统一处理。
				prevBean = currBean;
				repeatList.add(currBean);
				continue;
			}

			// 相邻的2个bean的ctitle相同，表示需要合并。
			if (!StringUtils.isEmpty(currBean.getCtitle()) && currBean.getCtitle().equals(prevBean.getCtitle())) {
				// 最后一个bean，与之前ctitle相同的bean进行合并
				if (isLastBean) {
					// 合并当前bean
					repeatList.add(currBean);
					int colspan = repeatList.size() == 1 ? 0 : repeatList.size();
					// 列合并的bean
					GpeFieldBean colspanBean = new GpeFieldBean();
					colspanBean.setField(repeatList.get(0).getField() + "_" + colspan);
					colspanBean.setTitle(currBean.getCtitle());
					colspanBean.setColspan(colspan);
					colspanBean.setAlign(TextAlign.CENTER);
					// TODO colspanBean.setType("class java.lang.String");
					// 添加到第1个list
					firstList.add(colspanBean);

					// 添加到第2个list
					secondList.addAll(repeatList);
					break;
				}

				// 本次不做处理，待到合并结束后统一处理。
				prevBean = currBean;
				repeatList.add(currBean);
				continue;
			}

			// 仅仅出现了一次未重复，表示上一个bean未进行colspan。
			if (repeatList.size() == 1) {
				// 添加到第1个list
				firstList.add(prevBean);

				// 最后一个bean，添加到第1个list
				if (isLastBean) {
					// 添加到第1个list
					firstList.add(currBean);
					break;
				}

				// 准备下一次循环的值
				prevBean = currBean;
				repeatList.clear();
				repeatList.add(currBean);
				continue;
			}

			// 出现了多次重复，且在本次中止重复。新增一个列合并的bean，添加到第1个list；重复的bean添加到第二个list。
			if (repeatList.size() > 1) {
				int colspan = repeatList.size() == 1 ? 0 : repeatList.size();

				// 列合并的bean
				GpeFieldBean colspanBean = new GpeFieldBean();
				colspanBean.setField(repeatList.get(0).getField() + "_" + colspan);
				colspanBean.setTitle(prevBean.getCtitle());
				colspanBean.setColspan(colspan);
				colspanBean.setAlign(TextAlign.CENTER);
				// TODO colspanBean.setType("class java.lang.String");
				// 添加到第1个list
				firstList.add(colspanBean);

				// 添加到第2个list
				secondList.addAll(repeatList);

				// 最后一个bean，添加到第1个list
				if (isLastBean) {
					// 添加到第1个list
					firstList.add(currBean);
					break;
				}

				// 准备下一次循环的值
				prevBean = currBean;
				repeatList.clear();
				repeatList.add(currBean);
				continue;
			}
		}

		// 第二个list不为空，代表有列合并情况，此时第一个list中未进行列合并的要进行行合并
		if (!CollectionUtils.isEmpty(secondList)) {
			for (GpeFieldBean bean : firstList) {
				if (bean.getColspan() == 0) {
					bean.setRowspan(2);
				}
			}
		}

		// 添加第一个集合
		if (!CollectionUtils.isEmpty(firstList)) {
			resultList.add(firstList);
		}
		// 添加第二个集合
		if (!CollectionUtils.isEmpty(secondList)) {
			resultList.add(secondList);
		}

		return resultList;
	}

}
