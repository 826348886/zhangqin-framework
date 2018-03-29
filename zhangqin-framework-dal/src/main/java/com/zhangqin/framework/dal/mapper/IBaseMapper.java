package com.zhangqin.framework.dal.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;

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
public interface IBaseMapper<T> extends BaseMapper<T> {
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
