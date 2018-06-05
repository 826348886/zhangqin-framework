package com.zhangqin.framework.common.exception;

/**
 * 业务异常基类，所有业务异常都必须继承于此异常。
 * @author zhangqin
 *
 */
public class BizException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3368372366159404826L;

	/**
	 * 
	 * <p>
	 * Title: 初始化异常
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public BizException() {
		super();
	}

	/**
	 * 
	 * <p>
	 * Title: 初始化异常
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param message
	 */
	public BizException(String message) {
		super(message);
		this.msg = message;
	}

	/**
	 * 
	 * <p>
	 * Title: 初始化异常
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param cause
	 */
	public BizException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * <p>
	 * Title: 初始化异常
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param code
	 * @param msgFormat
	 * @param args
	 */
	public BizException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	/**
	 * 
	 * <p>
	 * Title: 初始化异常
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param message
	 * @param cause
	 */
	public BizException(String message, Throwable cause) {
		super(message, cause);
		this.msg = message;
	}

	/**
	 * 
	 * <p>
	 * Title: 初始化异常
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param message
	 * @param cause
	 */
	public BizException(int code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.msg = message;
	}

	/**
	 * 异常信息
	 */
	protected String msg;
	/**
	 * 具体异常码
	 */
	protected int code;

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

}
