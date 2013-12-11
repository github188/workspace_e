package com.nari.util.exception;

/**
 * 异常类 DBAccessException
 *
 * @author zhangzhw zhongweizhang@163.com
 * @describe 用于ACTION 拦截器中处理的数据库异常类
 */
public class DBAccessException extends BaseException {
	private static final long serialVersionUID = -5604859926514506947L;

	public DBAccessException() {
		super();
	}

	public DBAccessException(Exception e, String msg) {
		super(e, msg);
	}

	public DBAccessException(String msg) {
		super(msg);
	}

}
