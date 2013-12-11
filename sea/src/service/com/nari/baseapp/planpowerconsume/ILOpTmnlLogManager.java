package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.logofsys.LOpTmnlLog;
import com.nari.support.PropertyFilter;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 
 * 创建时间：2009-12-22 下午01:37:35 
 * 描述：
 */

public interface ILOpTmnlLogManager {
	/**
	 * 添加日志
	 * @param lOpTmnlLog
	 * @throws DBAccessException
	 */
    public void saveTmnlLog(LOpTmnlLog lOpTmnlLog )throws DBAccessException;
    /**
     * 更新日志
     * @param lOpTmnlLog
     * @throws DBAccessException
     */
    public void updateTmnlLog(LOpTmnlLog lOpTmnlLog )throws DBAccessException;
    /**
     * 按条件查询日志
     * @param filters
     * @return
     * @throws DBAccessException
     */
    public List<LOpTmnlLog>findTmnlLogBy(List<PropertyFilter> filters)throws DBAccessException; 
}
