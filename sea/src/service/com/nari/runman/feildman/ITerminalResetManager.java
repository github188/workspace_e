package com.nari.runman.feildman;

import java.util.List;

import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;

/**终端复位service接口
 * @author 姜海辉
 *
 */
public interface ITerminalResetManager {
	
	/**
	 * 终端复位
	 * @param tmnlList
	 * @return
	 * @throws Exception
	 */
	public List<TmnlExecStatusBean> tmnlReset(List<CtrlParamBean> tmnlList,Short resetType,Integer taskSecond)throws Exception;

}
