package com.nari.runman.archivesman.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nari.runcontrol.GLineJdbc;
import com.nari.runcontrol.GSubsJdbc;
import com.nari.runcontrol.OOrgJdbc;
import com.nari.runcontrol.VwRunStatus;
import com.nari.runman.archivesman.IOrganizeManDao;
import com.nari.runman.archivesman.IOrganizeManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class OrganizeManagerImpl implements IOrganizeManager {
	protected Logger logger = Logger.getLogger(this.getClass());
	
	// DAO接口
	IOrganizeManDao iOrganizeManDao;
	public void setiOrganizeManDao(IOrganizeManDao iOrganizeManDao) {
		this.iOrganizeManDao = iOrganizeManDao;
	}
	
	/**
	 * 查询区县下面的供电所
	 * @param orgNo 区县编码
	 * @return
	 * @throws Exception
	 */
	public List<OOrgJdbc> findOrg(String orgNo) throws Exception{
		try {
			List<OOrgJdbc> list = this.iOrganizeManDao.findOrg(orgNo);
			if (list == null) {
				list = new ArrayList<OOrgJdbc>();
			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询区县下面的供电所出错");
		}
	}
	
	/**
	 * 查询单位下属的供电所
	 * @param orgNo 区县编号或地市编号
	 * @throws Exception
	 */
	public List<GSubsJdbc> findSubs(String orgNo) throws Exception{
		try {
			List<GSubsJdbc> list = this.iOrganizeManDao.findSubs(orgNo);
			if (list == null) {
				list = new ArrayList<GSubsJdbc>();
			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询单位下属的供电所出错");
		}
	}
	
	/**
	 * 查询变电站下属的供电所
	 * @param orgNo 变电站编号
	 * @throws Exception
	 */
	public List<GLineJdbc> findLine(String subNo) throws Exception{
		try {
			List<GLineJdbc> list = this.iOrganizeManDao.findLine(subNo);
			if (list == null) {
				list = new ArrayList<GLineJdbc>();
			}
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询变电站下属的供电所出错");
		}
	}
	
	/**
	 * 新增、保存供电所
	 * @param orgJdbc 供电所
	 * @throws Exception
	 */
	public String saveOrUpdateOrg(String hiddenOrgNo,OOrgJdbc orgJdbc) throws Exception{
		try {
			if(!hiddenOrgNo.equals(orgJdbc.getOrgNo())){
				List<OOrgJdbc> list = this.iOrganizeManDao.findOrgByNo(orgJdbc
						.getOrgNo());
				if (list != null && list.size() > 0) {
					return "单位编号已存在！";
				}
			}
			if("".equals(hiddenOrgNo)){
				this.iOrganizeManDao.insertOrg(orgJdbc);
			}else{
				this.iOrganizeManDao.saveOrg(hiddenOrgNo,orgJdbc);
			}
			return "保存成功！";
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("新增、保存供电所出错");
		}
	}
	
	/**
	 * 新增、保存变电站
	 * @param subsJdbc 变电站
	 * @throws Exception
	 */
	public String saveOrUpdateSubs(GSubsJdbc subsJdbc) throws Exception{
		if(subsJdbc.getSubsId() != null && !"".equals(subsJdbc.getSubsId())){
			List<GSubsJdbc> list1 = this.iOrganizeManDao.findSubsByNo(subsJdbc, "subsNo_update");
			if(list1!=null && list1.size()>0){
				return "变电站编号已存在！";
			}
			List<GSubsJdbc> list2 = this.iOrganizeManDao.findSubsByNo(subsJdbc, "subsName_update");
			if(list2!=null && list2.size()>0){
				return "变电站名称已存在！";
			}
			this.iOrganizeManDao.saveSubs(subsJdbc);
			return "保存成功！";
		}else{
			List<GSubsJdbc> list1 = this.iOrganizeManDao.findSubsByNo(subsJdbc, "subsNo_insert");
			if(list1!=null && list1.size()>0){
				return "变电站编号已存在！";
			}
			List<GSubsJdbc> list2 = this.iOrganizeManDao.findSubsByNo(subsJdbc, "subsName_insert");
			if(list2!=null && list2.size()>0){
				return "变电站名称已存在！";
			}
			this.iOrganizeManDao.insertSubs(subsJdbc);
			return "保存成功！";
		}
	}
	
	/**
	 * 新增、保存线路
	 * @param lineJdbc 线路
	 * @throws Exception
	 */
	public String saveOrUpdateLine(String nodeValue,GLineJdbc lineJdbc) throws Exception{
		if(lineJdbc.getLineId() != null && !"".equals(lineJdbc.getLineId())){
//			List<GLineJdbc> list1 = this.iOrganizeManDao.findLineByNo(lineJdbc, "lineNo_update");
//			if(list1!=null && list1.size()>0){
//				return "线路编号已存在！";
//			}
			List<GLineJdbc> list2 = this.iOrganizeManDao.findLineByNo(lineJdbc, "lineName_update");
			if(list2!=null && list2.size()>0){
				return "线路名称已存在！";
			}
			this.iOrganizeManDao.saveLine(lineJdbc);
			return "保存成功！";
		}else{
			List<GLineJdbc> list1 = this.iOrganizeManDao.findLineByNo(lineJdbc, "lineNo_insert");
			if(list1!=null && list1.size()>0){
				return "线路编号已存在！";
			}
			List<GLineJdbc> list2 = this.iOrganizeManDao.findLineByNo(lineJdbc, "lineName_insert");
			if(list2!=null && list2.size()>0){
				return "线路名称已存在！";
			}
			Long lineId = this.iOrganizeManDao.findLineSq();
			if(lineId==null) return "保存失败！";
			lineJdbc.setLineId(lineId);
			this.iOrganizeManDao.insertLine(lineJdbc);
			this.iOrganizeManDao.insertSubLineRela(nodeValue, lineId.toString());
			return "保存成功！";
		}
	}
	
	/**
	 * 删除供电所
	 * @param orgNo 单位编码
	 * @throws Exception
	 */
	public void deleteOrg(String orgNo) throws Exception{
		try{
			this.iOrganizeManDao.deleteOrg(orgNo);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除供电所出错");
		}
	}
	
	/**
	 * 删除变电站
	 * @param subsId 变电站id
	 * @throws Exception
	 */
	public void deleteSubs(String subsId) throws Exception{
		try{
			this.iOrganizeManDao.deleteSubs(subsId);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除变电站出错");
		}
	}
	
	/**
	 * 删除线路
	 * @param lineId 线路id
	 * @throws Exception
	 */
	public void deleteLine(String lineId) throws Exception{
		try{
			this.iOrganizeManDao.deleteLine(lineId);
			this.iOrganizeManDao.deleteLineSub(lineId);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除线路出错");
		}
	}
	
	/**
	 * 查询运行状态码表
	 * @return
	 * @throws Exception
	 */
	public List<VwRunStatus> findRunStatusList() throws Exception{
		try{
			return this.iOrganizeManDao.findRunStatusList();
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除供电所出错");
		}
	}
}
