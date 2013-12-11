package com.nari.runman.archivesman;

import java.util.List;

import com.nari.customer.CCons;
import com.nari.grid.GTg;
import com.nari.measurepoint.CMeterMpRela;
import com.nari.runcontrol.CmeterJdbc;
import com.nari.runcontrol.CmpJdbc;
import com.nari.runcontrol.RConsTmnlRela;
import com.nari.runcontrol.RCpConsRela;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;
import com.nari.util.exception.DBAccessException;


/**
 * 接口 IOrganizeManDao
 * 
 * @author yut
 * @describe 档案Excel导入Dao接口
 */
public interface IImportExcelDao {
	
	/**
	 * 保存专变用户的档案信息
	 * @param cconsList 用户
	 * @param rcpList	采集点
	 * @param rcpparaList 采集点参数
	 * @param tmnlList 终端
	 * @param cmpList 计量点
	 * @param cmeterList 电表
	 * @param rCpConsRelaList 采集点和用户关联
	 * @param rConsTmnlRelaList 用户和终端关联
	 */
	public void savePrivacyExcel(List<CCons> cconsList,List<RcpRunJdbc> rcpList,List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList,List<CmpJdbc> cmpList,List<CmeterJdbc> cmeterList,
			List<RCpConsRela> rCpConsRelaList,List<RConsTmnlRela> rConsTmnlRelaList) throws DBAccessException;
	
	/**
	 * 保存专变用户的档案信息
	 * @param gtgList 台区
	 * @param cconsList 用户
	 * @param rcpList	采集点
	 * @param rcpparaList 采集点参数
	 * @param tmnlList 终端
	 * @param cmpList 计量点
	 * @param cmeterList 电表
	 * @param rCpConsRelaList 采集点和用户关联
	 * @param rConsTmnlRelaList 用户和终端关联
	 */
	public void savePrivacyExcel(List<GTg> gtgList,List<CCons> cconsList,List<RcpRunJdbc> rcpList,List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList,List<CmpJdbc> cmpList,List<CmeterJdbc> cmeterList,
			List<RCpConsRela> rCpConsRelaList,List<RConsTmnlRela> rConsTmnlRelaList,List<CMeterMpRela>  cMeterMpRelaList) throws DBAccessException;
}