package com.nari.runman.feildman;

import java.util.List;

import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.runman.dutylog.TmnlTime;



/**
 * 终端对时service接口
 * @author 姜海辉
 *
 */
public interface ITerminalCorrectTimeManager {
	/**
	 * 对时
	 * @param tmnlList
	 * @return
	 * @throws Exception
	 */
	public List<TmnlExecStatusBean> correctTmnlTime(List<CtrlParamBean> tmnlList,Integer taskSecond)throws Exception;
	
	/**
	 * 时钟召测
	 * @param tmnlList
	 * @return
	 * @throws Exception
	 */
	public List<TmnlTime> fetchTmnlTime(List<String> tmnlList,Integer taskSecond)throws Exception;
	
}
