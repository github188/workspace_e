package com.nari.exception;

import org.apache.log4j.Logger;
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

public class BaseException extends Exception {

	private static final long serialVersionUID = -1009541660232554897L;
	static Logger LOG = Logger.getLogger(BaseException.class);
	protected String errorCode;
	protected String errorMsg;
	protected Exception errorCause;

	public BaseException() {
		super();
	}
	
	public BaseException(Exception e, String msg) {
		this.errorCode = e.getMessage();
		this.errorMsg = msg;
	}
	
	public BaseException(String msg) {
		super(msg);
		this.errorMsg = msg;
	}

	public BaseException(Exception e) {
		super(e.getMessage());
		this.errorCause = e;
	}

	public BaseException(String msg, String[] error) {
		super(msg);
		this.errorCode = error[0];
		this.errorMsg = error[1];
	}

	public BaseException(Exception e, String[] error) {
		super(e.getMessage());
		this.errorCode = error[0];
		this.errorMsg = error[1];
		this.errorCause = e;
	}

	public final String getErrorCode() {
		return errorCode;
	}

	public final void setErrorCode(String errorCodeParam) {
		this.errorCode = errorCodeParam;
	}

	public final String getErrorMsg() {
		return errorMsg;
	}

	public final void setErrorMsg(String errorMsgParam) {
		this.errorMsg = errorMsgParam;
	}

	public final Exception getErrorCause() {
		return errorCause;
	}

	public final void setErrorCause(Exception errorCauseParam) {
		this.errorCause = errorCauseParam;
	}
	/**
	 * 方法 processDBException
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
}