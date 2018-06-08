package com.zhangqin.framework.service;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhangqin.framework.dal.mapper.IBaseMapper;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {
	/**
	 * 校验
	 */
	@Autowired(required = false)
	private Validator validator;
	
	/**
	 * mapper
	 */
	private IBaseMapper<T> mapper;
	
	@PostConstruct
	private void initConfig() {
		this.mapper = this.getBaseMapper();
	}
	
	public abstract IBaseMapper<T> getBaseMapper();
	
	/**
	 * 校验
	 * @param entity
	 * @throws Exception
	 */
	public void validate(T entity) throws Exception {
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
			throw new Exception(validateError.toString());
		}
	}
	
	
}
