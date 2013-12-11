package com.nari.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.Logger;
import com.nari.util.StringUtil;

public class ExceptionHandle {

	static Logger logger = Logger.getLogger(ExceptionHandle.class);
	public static final String LOGTYPE_ERROR = "error";
	public static final String LOGTYPE_WARN = "warning";
	public static final String LOGTYPE_INFO = "info";
	public static final String LOGTYPE_NOLOG = "nologging";
	public static final int EXCEPTION_TRACE_LENGTH = 4000;
	static final String DOT = ".";
	private static final int INIT_BUFFER_SIZE = 1024;

	public static String getDetailedMessage(BaseException exception) {
		StringBuffer msg = new StringBuffer(INIT_BUFFER_SIZE);
		if (exception.getMessage() != null) {
			msg.append("Message : ");
			msg.append(exception.getMessage());
			msg.append("\n");
		}

		msg.append("Exception Stack Trace\n");
		try {
			StringWriter sw = new StringWriter(INIT_BUFFER_SIZE);
			PrintWriter pw = new PrintWriter(sw);
			exception.printStackTrace(pw);
			msg.append(sw.toString());
			sw.close();
		} catch (Exception e) {
			msg.append(exception.toString());
		}
		Throwable rootCause = exception.getCause();
		if (rootCause != null) {
			msg.append("\n Root Exception Stack Trace : ");
			msg.append(rootCause.toString());
			msg.append("\n");
			try {
				StringWriter sw = new StringWriter(INIT_BUFFER_SIZE);
				PrintWriter pw = new PrintWriter(sw);
				rootCause.printStackTrace(pw);
				msg.append(sw.toString());
				sw.close();
			} catch (Exception e) {
				msg.append(rootCause.toString());
			}
		}
		return msg.toString();
	}

	public static String getDetailedMessage(Throwable a) {
		StringBuffer msg = new StringBuffer();

		msg.append("Message : ");
		msg.append(a.getMessage());
		msg.append("\n");
		msg.append("Exception Stack Trace\n");
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			a.printStackTrace(pw);
			msg.append(sw.toString());
			sw.close();
		} catch (Exception e) {
			msg.append(a.toString());
		}
		String ret = msg.toString();
		msg = null;
		return ret;
	}

	public static String getClassName(Throwable e) {
		String className = e.getClass().getName();
		String errId = className;

		if (e instanceof BaseException) {
			int i = className.lastIndexOf('.');
			errId = className.substring(i + 1);
		}
		return errId;
	}

	public static String getExceptionLog(Throwable exp, String userId) {
		String errorId = getClassName(exp);
		String detailedMessage = null;
		if (exp instanceof BaseException) {
			BaseException ie = (BaseException) exp;
			detailedMessage = getDetailedMessage(ie);
		} else {
			detailedMessage = getDetailedMessage(exp);
		}

		StringBuffer lBuffer = new StringBuffer(INIT_BUFFER_SIZE);
		String msg = null;
		lBuffer.append("ERRORID :");
		lBuffer.append(errorId);
		lBuffer.append("\n");

		if (StringUtil.isEmptyString(userId)) {
			lBuffer.append("USERID : ");
			lBuffer.append(userId);
			lBuffer.append("\n");
		}

		lBuffer.append("EXCEPTION MESSAGE :");
		lBuffer.append(detailedMessage);
		lBuffer.append("\n");
		lBuffer.append("---------------------------------------\n");

		msg = lBuffer.toString();
		return msg;
	}

	public static ExceptionDTO getExceptionDetails(String context, Throwable exp) {
		ExceptionDTO exDTO = new ExceptionDTO();
		ExceptionInfoCache ecache = ExceptionInfoCache.getInstance();
		ExceptionInfoDTO exInfo = ecache.getExceptionInfo(getClassName(exp));
		if (exInfo != null) {
			exDTO.setLoggingType(exInfo.getLoggingType());
			exDTO.setConfirmation(exInfo.isConfirmationInd());
			String messageCode = exInfo.getMessageCode();
			if (exInfo.isContextSensitive()
					&& !StringUtil.isEmptyString(context))
				messageCode = messageCode + DOT + context;
			exDTO.setMessageCode(messageCode);
		}
		return exDTO;
	}

	public static void logException(Throwable th, String userId,
			String loggingType) {
		String exceptionTrace = getExceptionLog(th, userId);
		if (LOGTYPE_NOLOG.equals(loggingType))
			return;
		else if (LOGTYPE_ERROR.equals(loggingType))
			logger.error(exceptionTrace);
		else if (LOGTYPE_INFO.equals(loggingType))
			logger.info(exceptionTrace);
		else if (LOGTYPE_WARN.equals(loggingType))
			logger.warn(exceptionTrace);
		else {
			logger.error(exceptionTrace);
		}
	}

	public static void logException(Throwable th, String userId) {
		logException(th, userId, LOGTYPE_ERROR);
	}

	public static ExceptionDTO handleCheckedException(String context,
			String userId, Throwable exp) {
		ExceptionDTO exDTO = getExceptionDetails(context, exp);
		logException(exp, userId, exDTO.getLoggingType());
		return exDTO;
	}

	public static ExceptionDTO handleCheckedException(Throwable exp) {
		ExceptionDTO exDTO = getExceptionDetails("", exp);
		logException(exp, "", exDTO.getLoggingType());
		return exDTO;
	}

	public static String handleUnCheckedException(Exception exp) {
		logException(exp, "System", "error");
		return null;
	}
}