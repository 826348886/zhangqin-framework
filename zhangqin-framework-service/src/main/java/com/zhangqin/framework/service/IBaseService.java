package com.zhangqin.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zhangqin.framework.common.exception.BizException;

/**
 * 
 * @author zhangqin
 *
 * @param <T>
 */
public interface IBaseService<T> {
	/**
	 * 
	 * @Description: 插入一条记录
	 * @param entity
	 * @return int  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	int insert(T entity) throws BizException;

	/**
	 * 
	 * @Description: 批量插入记录
	 * @param list
	 * @return int  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	int insert(List<T> list) throws BizException;
	
	/**
	 * 
	 * @Description: 根据ID删除
	 * @param id
	 * @return int  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	int deleteById(Serializable id) throws BizException;

	/**
	 * 
	 * @Description: 根据ID修改
	 * @param entity
	 * @return int  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	int updateById(T entity) throws BizException;

	/**
	 * 
	 * @Description: 根据ID查询
	 * @param id
	 * @return T  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	T getById(Serializable id);

	/**
	 * 
	 * @Description: 根据ID集合，查询全部记录
	 * @param idList
	 * @return List<T>  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	List<T> listBatchIds(@Param("coll") Collection<? extends Serializable> idList);

}
