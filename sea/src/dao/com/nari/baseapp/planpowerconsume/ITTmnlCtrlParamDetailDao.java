package com.nari.baseapp.planpowerconsume;

import com.nari.orderlypower.TTmnlCtrlParamDetail;
import com.nari.util.exception.DBAccessException;

/***
 * 
 * hibernate 终端控制参数明细
 * @author huangxuan
 * **/
public interface ITTmnlCtrlParamDetailDao {
	public  void saveOrUpdate(final TTmnlCtrlParamDetail entity) throws DBAccessException;
}
