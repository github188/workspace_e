package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.GLineJdbc;
import com.nari.runcontrol.GSubsJdbc;
import com.nari.runcontrol.OOrgJdbc;
import com.nari.runcontrol.VwRunStatus;

public interface IOrganizeManager {
	
	/**
	 * 查询区县下属的供电所
	 * @param orgNo 区县编号
	 * @throws Exception
	 */
	public List<OOrgJdbc> findOrg(String orgNo) throws Exception;
	
	/**
	 * 查询单位下属的供电所
	 * @param orgNo 区县编号或地市编号
	 * @throws Exception
	 */
	public List<GSubsJdbc> findSubs(String orgNo) throws Exception;

	/**
	 * 查询变电站下属的供电所
	 * @param subNo 变电站编号
	 * @throws Exception
	 */
	public List<GLineJdbc> findLine(String subNo) throws Exception;
	
	/**
	 * 新增、保存供电所
	 * @param orgJdbc 供电所
	 * @throws Exception
	 */
	public String saveOrUpdateOrg(String hiddenOrgNo,OOrgJdbc orgJdbc) throws Exception;
	
	/**
	 * 新增、保存变电站
	 * @param subsJdbc 变电站
	 * @throws Exception
	 */
	public String saveOrUpdateSubs(GSubsJdbc subsJdbc) throws Exception;
	
	/**
	 * 新增、保存线路
	 * @param lineJdbc 线路
	 * @throws Exception
	 */
	public String saveOrUpdateLine(String nodeValue,GLineJdbc lineJdbc) throws Exception;
	
	/**
	 * 删除供电所
	 * @param orgNo 单位编码
	 * @throws Exception
	 */
	public void deleteOrg(String orgNo) throws Exception;
	
	/**
	 * 删除变电站
	 * @param subsId 变电站id
	 * @throws Exception
	 */
	public void deleteSubs(String subsId) throws Exception;
	
	/**
	 * 删除线路
	 * @param lineId 线路id
	 * @throws Exception
	 */
	public void deleteLine(String lineId) throws Exception;
	
	/**
	 * 查询运行状态码表
	 * @return
	 * @throws Exception
	 */
	public List<VwRunStatus> findRunStatusList() throws Exception;
}
