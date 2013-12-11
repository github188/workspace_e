package com.nari.runman.abnormalhandle;

import com.nari.flowhandle.FEventSrc;

public interface EventNoCreateManager {
	
	/**
	 * 生成异常工单源
	 * 
	 * @param eventInfoBean
	 *            异常信息
	 * @return void
	 * @throws Exception
	 */
	public long createEventNo(FEventSrc fEventSrc) throws Exception;

}
