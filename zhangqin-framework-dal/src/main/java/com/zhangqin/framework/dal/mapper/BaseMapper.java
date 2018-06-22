package com.zhangqin.framework.dal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;

/**
 * 
 * ClassName: IBaseMapper 
 * @Description: Mapper 继承该接口后，无需编写 mapper.xml 文件，即可获得CRUD功能
 * @author zhangqin
 * @date 2018年1月15日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public interface BaseMapper<T>{
	
    /**
     * @Description: 新增对象，新增所有字段
     * @param record
     * @return   
     * @date 2016年9月3日
     */
	@InsertProvider(type = SqlProvider.class, method = "insert")
    <Entity> int insert(Entity record);
    
    /**
     * 新增对象，只新增非空字段
     * @param record
     * @return
     */
	@InsertProvider(type = SqlProvider.class, method = "insertSelective")
    <Entity> int insertSelective(Entity record);

    /**
     * @Description: 更新对象，所有字段都更新
     * @param record
     * @return   
     * @date 2016年9月3日
     */
    <Entity> int updateByPrimaryKey(Entity record);
    
    /**
     * @Description: 更新对象，只更新非空字段
     * @param record
     * @return   
     * @date 2016年9月3日
     */
    <Entity> int updateByPrimaryKeySelective(Entity record);
    
	/**
	 * @Description: 根据ID删除对象
	 * @param id
	 * @return   
	 * @date 2016年9月3日
	 */
    int deleteByPrimaryKey(Long id);
    
    /**
     * 根据主键id获取对应的对象
     * @param id
     * @return
     */
    <Entity> Entity selectByPrimaryKey(Long id);
    
    /**
     * 根据条件查询对应的数据
     * @param record
     * @return
     */
    <Entity> List<Entity> selectBySelective(Entity record);
    
    /**
     * <p>
     * 批量插入记录
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer batchInsert(List<T> list);
}
