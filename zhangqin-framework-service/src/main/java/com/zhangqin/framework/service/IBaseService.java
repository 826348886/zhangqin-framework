package com.zhangqin.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.baomidou.mybatisplus.mapper.Wrapper;
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
	int add(T entity) throws BizException;

	/**
	 * 
	 * @Description: 批量插入记录
	 * @param list
	 * @return int  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	int add(List<T> list) throws BizException;

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
	 * @Description: 根据ID删除
	 * @param id
	 * @return int  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	int deleteById(Serializable id) throws BizException;

	/**
	 * 
	 * @Description: 根据ID查询
	 * @param id
	 * @return T  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	T findById(Serializable id);

	/**
	 * 
	 * @Description: 根据ID集合，查询全部记录
	 * @param idList
	 * @return List<T>  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	List<T> findList(@Param("coll") Collection<? extends Serializable> idList);

	/**
	 * 
	 * @Description: 根据 Wrapper条件，查询全部记录
	 * @param wrapper
	 * @return List<T>  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	List<T> findList(Wrapper<T> wrapper);

	/**
	 * 
	 * @Description: 根据 Wrapper和分页条件，查询全部记录
	 * @param wrapper
	 * @param rowBounds
	 * @return List<T>  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	List<T> findList(Wrapper<T> wrapper, RowBounds rowBounds);

	/**
	 * 
	 * @Description: 根据 Wrapper条件，查询总记录数
	 * @param wrapper
	 * @return List<T>  
	 * @author zhangqin
	 * @date 2018年1月17日
	 */
	List<T> findCount(Wrapper<T> wrapper);
}
