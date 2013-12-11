package com.nari.runman.abnormalhandle;

import java.util.List;

import com.nari.basicdata.BProtocolEvent;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.DBAccessException;

/**
 * 事件管理业务层接口
 * 
 * @author longkc
 */
public interface EventManageManager {

	/**
	 * 查询所有终端规约类型，按编码排序。
	 * 
	 * @return 终端规约类型列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<VwProtocolCodeBean> getProCodeList() throws Exception;

	/**
	 * 查询终端规约对应数据类型。
	 * 
	 * @param proCode
	 *            通信规约类型
	 * @param proNO
	 *            规约项编码
	 * @return 终端规约对应数据类型列表
	 * @throws DBAccessException
	 *             数据库异常
	 */
	public List<VwDataTypePBean> getDataTypeList(String proCode, String proNO)
			throws Exception;

	/**
	 * 更新/保存规约信息
	 * 
	 * @param ttmInfo
	 *            规约信息
	 * @return void
	 * @throws Exception
	 */
	public void saveORUpdateTerminal(List<TTmnlParam> ttmInfo) throws Exception;
	
	/**
	 * 更新规约事件级别
	 * @param eventInfo 事件信息
	 * @throws Exception
	 */
	public void updateEventInfo(List<BProtocolEvent> eventInfo) throws Exception;

	/**
	 * 查询规约事件信息
	 * 
	 * @param proNo
	 *            规约项编码
	 * @return 事件信息
	 * @throws Exception
	 */
	public List<BProtocolEvent> getEventInfo(String proNo) throws Exception;

}
