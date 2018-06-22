package com.zhangqin.framework.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhangqin.framework.common.exception.BizException;
import com.zhangqin.framework.dal.mapper.BaseMapper;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	/**
	 * 校验
	 */
	@Autowired(required = false)
	private Validator validator;
	
	/**
	 * mapper
	 */
	private BaseMapper<T> mapper;
	
	@PostConstruct
	private void initConfig() {
		this.mapper = this.getBaseMapper();
	}
	
	public abstract BaseMapper<T> getBaseMapper();
	
	/**
	 * 校验
	 * @param entity
	 * @throws Exception
	 */
	public void validate(T entity) throws BizException {
		if (null == validator) {
			return;
		}
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
		if (constraintViolations.size() > 0) {
			StringBuilder validateError = new StringBuilder();
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				validateError.append("属性：").append(constraintViolation.getPropertyPath()).append("报错！")
						.append(constraintViolation.getMessage()).append(";");
			}
			throw new BizException(validateError.toString());
		}
	}

	
	
}
