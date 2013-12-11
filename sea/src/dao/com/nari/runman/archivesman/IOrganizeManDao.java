package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.GLineJdbc;
import com.nari.runcontrol.GSubsJdbc;
import com.nari.runcontrol.OOrgJdbc;
import com.nari.runcontrol.VwRunStatus;
import com.nari.util.exception.DBAccessException;


/**
 * 接口 IOrganizeManDao
 * 
 * @author yut
 * @describe 组织机构维护接口
 */
public interface IOrganizeManDao {
	
	/**
	 * 根据区县编号查询供电所
	 * @param orgNo 区县编码
	 * @return
	 * @throws DBAccessException
	 */
	public List<OOrgJdbc> findOrg(String orgNo) throws DBAccessException;
	
	/**
	 * 根据单位编号查询变电站
	 * @param orgNo 区县编码或地市编码
	 * @throws DBAccessException
	 */
	public List<GSubsJdbc> findSubs(String orgNo) throws DBAccessException;
	
	/**
	 * 根据变电站编号查询线路
	 * @param subsNo 变电站编号
	 * @return
	 * @throws DBAccessException
	 */
	public List<GLineJdbc> findLine(String subsNo) throws DBAccessException;
	
	/**
	 * 根据单位编码查询单位
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	public List<OOrgJdbc> findOrgByNo(String orgNo) throws DBAccessException;
	/**
	 * 根据变电站编号/id/名称 查询变电站
	 * @param gSubsJdbc 变电站
	 * @param queryT （subsNo/subsId/subsName）
	 * @throws DBAccessException
	 */
	public List<GSubsJdbc> findSubsByNo(GSubsJdbc gSubsJdbc,String queryT) throws DBAccessException;
	
	/**
	 * 根据线路编号/id/名称查询线路
	 * @param gLineJdbc 线路
	 * @param queryT （lineNo/lineId/lineName）
	 * @return
	 * @throws DBAccessException
	 */
	public List<GLineJdbc> findLineByNo(GLineJdbc gLineJdbc,String queryT) throws DBAccessException;
	
	/**
	 * 新增供电所
	 * @throws DBAccessException
	 */
	public void insertOrg(OOrgJdbc orgJdbc) throws DBAccessException;
	
	/**
	 * 保存供电所
	 * @throws DBAccessException
	 */
	public void saveOrg(String hiddenOrgNo,OOrgJdbc orgJdbc) throws DBAccessException;
	
	/**
	 * 新增变电站
	 * @throws DBAccessException
	 */
	public void insertSubs(GSubsJdbc subsJdbc) throws DBAccessException;
	
	/**
	 * 保存变电站
	 * @throws DBAccessException
	 */
	public void saveSubs(GSubsJdbc subsJdbc) throws DBAccessException;
	
	/**
	 * 新增线路
	 * @throws DBAccessException
	 */
	public void insertLine(GLineJdbc lineJdbc) throws DBAccessException;
	
	/**
	 * 新增线路和变电站关系
	 * @throws DBAccessException
	 */
	public void insertSubLineRela(String subsId,String lineId) throws DBAccessException;
	
	/**
	 * 保存线路
	 * @throws DBAccessException
	 */
	public void saveLine(GLineJdbc lineJdbc) throws DBAccessException;
	
	/**
	 * 删除供电所
	 * @param orgNo 单位编码
	 * @throws DBAccessException
	 */
	public void deleteOrg(String orgNo) throws DBAccessException;
	
	/**
	 * 删除变电站
	 * @param subsId 变电站id
	 * @throws DBAccessException
	 */
	public void deleteSubs(String subsId) throws DBAccessException;
	
	/**
	 * 删除线路
	 * @param lineId 线路id
	 * @throws DBAccessException
	 */
	public void deleteLine(String lineId) throws DBAccessException;
	
	/**
	 * 删除线路和变电站关系
	 * @param lineId 线路id
	 * @throws DBAccessException
	 */
	public void deleteLineSub(String lineId) throws DBAccessException;
	
	/**
	 * 查询运行状态码表
	 * @return
	 * @throws DBAccessException
	 */
	public List<VwRunStatus> findRunStatusList() throws DBAccessException;
	
	/**
	 * 查询线路的序列，主键
	 * @return
	 * @throws DBAccessException
	 */
	public Long findLineSq() throws DBAccessException;
}
