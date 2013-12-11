package com.nari.util.exception;

import org.springframework.dao.CleanupFailureDataAccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.dao.UncategorizedDataAccessException;

/**
 * 类 BaseException 异常基类
 *
 * @author zhangzhw
 * @describe 异常基类 系统非数据库异常继承此类
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = 2594840626133600138L;

	public String message;
	public String detail;

	public BaseException() {
		super();
	}

	public BaseException(Exception e, String msg) {
		this.detail = e.getMessage();
		this.message = msg;
	}

	public BaseException(String msg) {
		this.message = msg;
	}

	/**
	 * 方法 processDBException
	 *
	 * @param e
	 * @return 自定义的异常代码
	 * @describe 将数据库异常转换为自定义异常代码
	 */
	public static String processDBException(DataAccessException e) {
		String rtn = "未知数据库错误";
		if (e instanceof CleanupFailureDataAccessException)
			rtn = ExceptionConstants.DBE_CLOSERESOURCEFAIL;
		else if (e instanceof DataAccessResourceFailureException)
			rtn = ExceptionConstants.DBE_DATARESOURCEFAIL;
		else if (e instanceof DataIntegrityViolationException)
			rtn = ExceptionConstants.DBE_DATAINTEGRITYVIOLATION;
		else if (e instanceof DataRetrievalFailureException)
			rtn = ExceptionConstants.DBE_DATANOTFIND;
		else if (e instanceof DeadlockLoserDataAccessException)
			rtn = ExceptionConstants.DBE_DEADLOCK;
		else if (e instanceof IncorrectUpdateSemanticsDataAccessException)
			rtn = ExceptionConstants.DBE_UPDATEFAIL;
		else if (e instanceof InvalidDataAccessApiUsageException)
			rtn = ExceptionConstants.DBE_INVALIDAPI;
		else if (e instanceof InvalidDataAccessResourceUsageException)
			rtn = ExceptionConstants.DBE_DATARESOURCE;
		else if (e instanceof OptimisticLockingFailureException)
			rtn = ExceptionConstants.DBE_LOCKINGFAIL;
		else if (e instanceof TypeMismatchDataAccessException)
			rtn = ExceptionConstants.DBE_MATCHDATAFAIL;
		else if (e instanceof UncategorizedDataAccessException)
			rtn = ExceptionConstants.DBE_UNCATEGORIZED;
		else
			rtn = ExceptionConstants.DBE_OTHERS;
		return rtn;
	}

	// getters and setters
	@Override
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
