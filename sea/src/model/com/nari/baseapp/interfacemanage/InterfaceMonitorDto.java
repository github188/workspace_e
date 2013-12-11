package com.nari.baseapp.interfacemanage;

import java.util.Date;

/**
 * 
 * 
 * @author ChunMingLi
 * @since 2010-6-22
 * 
 */
public class InterfaceMonitorDto {
	//日志ID
	private long logId;
	// 外围系统编码
	private String sysNo;
	// 接口类型
	private String interfaceType;
	// 接口名称
	private String interfaceName;
	// 错误编码
	private String errNo;
	// 接口内容
	private String content;
	// 反馈结果
	private String respId; 
	//操作时间
	private Date opTime;

	//记录应答
	private String answer ;
	
	/**
	 *  the answer
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 *  the answer to set
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	/**
	 *  the opTime
	 * @return the opTime
	 */
	public Date getOpTime() {
		return opTime;
	}

	/**
	 *  the opTime to set
	 * @param opTime the opTime to set
	 */
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	/**
	 * the sysNo
	 * 
	 * @return the sysNo
	 */
	public String getSysNo() {
		return sysNo;
	}

	/**
	 * the sysNo to set
	 * 
	 * @param sysNo
	 *            the sysNo to set
	 */
	public void setSysNo(String sysNo) {
		this.sysNo = sysNo;
	}

	/**
	 * the interfaceType
	 * 
	 * @return the interfaceType
	 */
	public String getInterfaceType() {
		return interfaceType;
	}

	/**
	 * the interfaceType to set
	 * 
	 * @param interfaceType
	 *            the interfaceType to set
	 */
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	/**
	 * the interfaceName
	 * 
	 * @return the interfaceName
	 */
	public String getInterfaceName() {
		return interfaceName;
	}

	/**
	 * the interfaceName to set
	 * 
	 * @param interfaceName
	 *            the interfaceName to set
	 */
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	/**
	 * the errNo
	 * 
	 * @return the errNo
	 */
	public String getErrNo() {
		return errNo;
	}

	/**
	 * the errNo to set
	 * 
	 * @param errNo
	 *            the errNo to set
	 */
	public void setErrNo(String errNo) {
		this.errNo = errNo;
	}

	/**
	 * the content
	 * 
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * the content to set
	 * 
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 *  the logId
	 * @return the logId
	 */
	public long getLogId() {
		return logId;
	}

	/**
	 *  the logId to set
	 * @param logId the logId to set
	 */
	public void setLogId(long logId) {
		this.logId = logId;
	}

	/**
	 *  the respId
	 * @return the respId
	 */
	public String getRespId() {
		return respId;
	}

	/**
	 *  the respId to set
	 * @param respId the respId to set
	 */
	public void setRespId(String respId) {
		this.respId = respId;
	}




}
