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

/**
 * 接口 IImportExcelManager
 * 
 * @author yut
 * @describe 档案Excel导入Service接口
 */
public interface IImportExcelManager {
	
	/**
	 * 保存专变用户的档案信息
	 * @param cconsList 用户
	 * @param rcpList	采集点
	 * @param rcpparaList 采集点参数
	 * @param tmnlList 终端
	 * @param cmpList 计量点
	 * @param cmeterList 电表
	 * @return
	 * @throws Exception
	 */
	public String savePrivacyExcel(List<CCons> cconsList,List<RcpRunJdbc> rcpList,List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList,List<CmpJdbc> cmpList,List<CmeterJdbc> cmeterList,
			List<RCpConsRela> rCpConsRelaList,List<RConsTmnlRela> rConsTmnlRelaList) throws Exception;

	
	/**
	 * 保存专变用户的档案信息
	 * @param gtgList 台区
	 * @param cconsList 用户
	 * @param rcpList	采集点
	 * @param rcpparaList 采集点参数
	 * @param tmnlList 终端
	 * @param cmpList 计量点
	 * @param cmeterList 电表
	 * @return
	 * @throws Exception
	 */
	public String savePrivacyExcel(List<GTg> gtgList,List<CCons> cconsList,List<RcpRunJdbc> rcpList,List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList,List<CmpJdbc> cmpList,List<CmeterJdbc> cmeterList,
			List<RCpConsRela> rCpConsRelaList,List<RConsTmnlRela> rConsTmnlRelaList,List<CMeterMpRela>  cMeterMpRelaList) throws Exception;

}
