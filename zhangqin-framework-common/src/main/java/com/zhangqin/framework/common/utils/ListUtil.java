package com.zhangqin.framework.common.utils;  

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * ClassName: ListUtil 
 * @Description: List工具集
 * @author zhangq
 * @date 2017年5月24日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class ListUtil {
	/**
	 * 私有构造函数
	 * <p>Title: 私有构造函数</p> 
	 * <p>Description: 增加私有构造函数用以隐藏公开构造函数</p>
	 */
	private ListUtil(){
		super();
	}
	
	/**
	 * 
	 * @Description: List分割
	 * @param list 原数据LIST
	 * @param subListSize 分割成包括多少个元素的LIST
	 * @return List分割结果
	 * @author zhangq
	 * @date 2017年5月24日
	 */
	public static <T> List<List<T>> splitList(List<T> list, int subListSize) {
		// 创建list数组 ,用来保存分割后的list
		List<List<T>> listArray = new ArrayList<List<T>>();
		// 数组每一位放入一个分割后的list
		List<T> subList = new ArrayList<T>();
		int count = list.size() / subListSize + (list.size() % subListSize > 0 ? 1 : 0);
		for (int i = 0; i < count; i++) {
			if (subListSize * (i + 1) < list.size()) {
				subList = list.subList(i * subListSize, subListSize * (i + 1));
			} else {
				subList = list.subList(i * subListSize, list.size());
			}
			listArray.add(subList);
		}
		return listArray;
	}
	
	/**
	 * 
	 * @Description: List分割ArrayList的集合
	 * @param list 原数据LIST
	 * @param subListSize 分割成包括多少个元素的LIST
	 * @return List分割结果
	 * @author zhangq
	 * @date 2017年7月15日
	 */
	public static <T> List<ArrayList<T>> splitArrayList(List<T> list, int subListSize) {
		// 创建list数组 ,用来保存分割后的list
		List<ArrayList<T>> listArray = new ArrayList<ArrayList<T>>();
		
		// 数组每一位放入一个分割后的list
		List<T> subList = new ArrayList<T>();
		int count = list.size() / subListSize + (list.size() % subListSize > 0 ? 1 : 0);
		for (int i = 0; i < count; i++) {
			if (subListSize * (i + 1) < list.size()) {
				subList = list.subList(i * subListSize, subListSize * (i + 1));
			} else {
				subList = list.subList(i * subListSize, list.size());
			}
			// 转换成ArrayList
			ArrayList<T> arrayList = new ArrayList<T>(0);
			arrayList.addAll(subList);
			listArray.add(arrayList);
		}
		return listArray;
	}
}
