package com.zhangqin.framework.common.enums;

/**
 * 
 * ClassName: ResponseCodeEnum 
 * @Description: 客户端响应状态码枚举
 * @author zhangqin
 * @date 2018年1月13日
 *
 * =================================================================================================
 *     Task ID			  Date			     Author		      Description
 * ----------------+----------------+-------------------+-------------------------------------------
 *
 */
public enum ResponseCodeEnum implements BaseEnum<ResponseCodeEnum, Integer> {
	/**
	 * 0:执行成功
	 */
	SUCCESS(0, "操作成功"),
	/**
	 * 1:执行失败
	 */
	FAIL(1, "操作失败"),
	/**
	 * 2:业务异常
	 */
	BUSINESS_ERROR(2, "业务异常"),
	/**
	 * 3:未登录或token失效
	 */
	NOT_LOGIN(3, "未登录或token失效"),
	/**
	 * 4:没有操作权限
	 */
	NOT_AUTHORITY(4, "没有操作权限"),
	/**
	 * 101:非法请求
	 */
	UNLAW_REQUEST(101, "非法请求"),
	/**
	 * 103:图片上传失败
	 */
	UPLOAD_FAIL(103, "图片上传失败"),
	/**
	 * 104:用户调用次数超限
	 */
	USER_CALL_LIMITED(104, "用户调用次数超限"),
	/**
	 * 105:会话调用次数超限
	 */
	SESSION_CALL_LIMITED(105, "会话调用次数超限"),
	/**
	 * 106:应用调用次数超限
	 */
	APP_CALL_LIMITED(106, "应用调用次数超限"),
	/**
	 * 107:应用调用频率超限
	 */
	APP_CALL_EXCEEDS_LIMITED_FREQUENCY(107, "应用调用频率超限"),
	/**
	 * 108:服务不可用
	 */
	SERVICE_CURRENTLY_UNAVAILABLE(108, "服务不可用"),
	/**
	 * 109:远程服务出错
	 */
	REMOTE_SERVICE_ERROR(109, "远程服务出错"),
	/**
	 * 110:缺少方法名参数
	 */
	MISSING_METHOD(110, "缺少方法名参数"),
	/**
	 * 111:不存在的方法名
	 */
	INVALID_METHOD(111, "不存在的方法名"),
	/**
	 * 112:非法数据格式
	 */
	INVALID_FORMAT(112, "非法数据格式"),
	/**
	 * 113:缺少签名参数
	 */
	MISSING_SIGNATURE(113, "缺少签名参数"),
	/**
	 * 114:非法签名
	 */
	INVALID_SIGNATURE(114, "非法签名"),
	/**
	 * 115:缺少版本参数
	 */
	MISSING_VERSION(115, "缺少版本参数"),
	/**
	 * 116:非法的版本参数
	 */
	INVALID_VERSION(116, "非法的版本参数"),
	/**
	 * 117:不支持的版本号
	 */
	UNSUPPORTED_VERSION(117, "不支持的版本号"),
	/**
	 * 118:缺少必选参数
	 */
	MISSING_REQUIRED_ARGUMENTS(118, "缺少必选参数"),
	/**
	 * 119:非法的参数
	 */
	INVALID_ARGUMENTS(119, "非法的参数"),
	/**
	 * 120:请求被禁止
	 */
	FORBIDDEN_REQUEST(120, "请求被禁止"),
	/**
	 * 121:参数错误
	 */
	PARAMETER_ERROR(121, "参数错误");
	/**
	 * 枚举编号
	 */
	private Integer value;
	/**
	 * 枚举描述
	 */
	private String desc;

	/**
	 * 
	 * <p>Title: 构造函数</p> 
	 * <p>Description: </p> 
	 * @param value
	 * @param desc
	 */
	ResponseCodeEnum(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	/**
	 * (non-Javadoc)
	 * @see com.zhangqin.framework.common.enums.BaseEnum#code()
	 */
	@Override
	public Integer getValue() {
		return value;
	}

	/**
	 * (non-Javadoc)
	 * @see com.zhangqin.framework.common.enums.BaseEnum#desc()
	 */
	@Override
	public String getDesc() {
		return desc;
	}
}