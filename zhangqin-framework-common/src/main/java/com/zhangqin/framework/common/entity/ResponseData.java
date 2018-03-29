package com.zhangqin.framework.common.entity;

import java.io.Serializable;

import com.zhangqin.framework.common.enums.BaseEnum;
import com.zhangqin.framework.common.enums.ResponseCodeEnum;
import com.zhangqin.framework.common.utils.BeanMapper;

/**
 * 
 * ClassName: ResponseData 
 * @Description: 客户端响应对象
 * @author zhangqin
 * @date 2018年1月13日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public class ResponseData<T> implements Serializable {
	/**
	 * @Fields serialVersionUID : serialVersionUID
	 */
	private static final long serialVersionUID = 5084805328527653848L;
	/**
	 * 数据
	 */
	private T data;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 状态码
	 */
	private Integer code;

	/**
	 * <p>Title: 构造方法1</p> 
	 * <p>Description:</p>
	 */
	public ResponseData() {
		this(ResponseCodeEnum.SUCCESS);
	}

	/**
	 * 
	 * <p>Title: 构造方法2</p> 
	 * <p>Description: </p> 
	 * @param result
	 */
	public ResponseData(BaseEnum<?, Integer> result) {
		this.code = result.getValue();
		this.message = result.getDesc();
	}

	/**
	 * 
	 * <p>Title: 构造方法3</p> 
	 * <p>Description: </p> 
	 * @param result
	 * @param data
	 */
	public ResponseData(BaseEnum<?, Integer> result, T data) {
		this.code = result.getValue();
		this.message = result.getDesc();
		this.data = data;
	}

	/**
	 * 
	 * <p>Title: 构造方法4</p> 
	 * <p>Description: </p> 
	 * @param code
	 * @param message
	 * @param data
	 */
	public ResponseData(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	/**
	 * 创建ResponseData
	 * @param data
	 * @param result
	 * @return
	 */
	public static <E> ResponseData<E> builder(E data, BaseEnum<?, Integer> result) {
		return new ResponseData<E>(result, data);
	}

	/**
	 * @Description: ResponseData中Data对象转换
	 * @param destinationClass
	 * @return  
	 * @author guocp
	 * @date 2017年11月8日
	 */
	public <E> ResponseData<E> map(Class<E> destinationClass) {
		ResponseData<E> response = new ResponseData<E>();
		response.setCode(code);
		response.setMessage(message);
		response.setData(BeanMapper.map(this.data, destinationClass));
		return response;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * ClassName: Builder 
	 * @Description: ResponseData构造器
	 * @author zhangqin
	 * @date 2017年12月5日
	 *
	 * =================================================================================================
	 *     Task ID			  Date			     Author		      Description
	 * ----------------+----------------+-------------------+-------------------------------------------
	 *
	 */
	public static class Builder<T> {
		/**
		 * 数据
		 */
		private T data;
		/**
		 * 消息
		 */
		private String message;
		/**
		 * 状态码
		 */
		private Integer code;

		public Builder<T> code(Integer code) {
			this.code = code;
			return this;
		}

		public Builder<T> message(String message) {
			this.message = message;
			return this;
		}

		public Builder<T> data(T data) {
			this.data = data;
			return this;
		}

		public Builder<T> result(BaseEnum<?, Integer> result) {
			this.code = result.getValue();
			this.message = result.getDesc();
			return this;
		}

		public ResponseData<T> build() {
			return new ResponseData<T>(this.code, this.message, this.data);
		}
	}
}
