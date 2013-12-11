package com.nari.runman.archivesman.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.nari.ami.cache.DownloadThread;
import com.nari.ami.cache.context.DBDownLoadContext;
import com.nari.ami.cache.context.DBDownLoadParam;
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
import com.nari.runman.archivesman.IImportExcelDao;
import com.nari.runman.archivesman.IImportExcelManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class ImportExcelManagerImpl implements IImportExcelManager {
	protected Logger logger = Logger.getLogger(this.getClass());

	// DAO接口
	IImportExcelDao iImportExcelDao;

	public void setiImportExcelDao(IImportExcelDao iImportExcelDao) {
		this.iImportExcelDao = iImportExcelDao;
	}

	/**
	 * 保存专变用户的档案信息
	 * 
	 * @param cconsList
	 *            用户
	 * @param rcpList
	 *            采集点
	 * @param rcpparaList
	 *            采集点参数
	 * @param tmnlList
	 *            终端
	 * @param cmpList
	 *            计量点
	 * @param cmeterList
	 *            电表
	 * @param rCpConsRelaList
	 *            采集点和用户关联
	 * @param rConsTmnlRelaList
	 *            用户和终端关联
	 */
	public String savePrivacyExcel(List<CCons> cconsList,
			List<RcpRunJdbc> rcpList, List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList, List<CmpJdbc> cmpList,
			List<CmeterJdbc> cmeterList, List<RCpConsRela> rCpConsRelaList,
			List<RConsTmnlRela> rConsTmnlRelaList) throws Exception {
		try {
			this.iImportExcelDao.savePrivacyExcel(cconsList, rcpList,
					rcpparaList, tmnlList, cmpList, cmeterList,
					rCpConsRelaList, rConsTmnlRelaList);
			String msg = "用户数据导入" + cconsList.size() + "条<br>";
			msg += "采集点数据导入" + rcpList.size() + "条<br>";
			msg += "采集点参数数据导入" + rcpparaList.size() + "条<br>";
			msg += "终端数据导入" + tmnlList.size() + "条<br>";
			msg += "计量点数据导入" + cmpList.size() + "条<br>";
			msg += "电表数据导入" + cmeterList.size() + "条";
			return msg;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("新增档案信息出错");
		}
	}

	/**
	 * 保存专变用户的档案信息
	 * 
	 * @param gtgList
	 *            台区
	 * @param cconsList
	 *            用户
	 * @param rcpList
	 *            采集点
	 * @param rcpparaList
	 *            采集点参数
	 * @param tmnlList
	 *            终端
	 * @param cmpList
	 *            计量点
	 * @param cmeterList
	 *            电表
	 * @param rCpConsRelaList
	 *            采集点和用户关联
	 * @param rConsTmnlRelaList
	 *            用户和终端关联
	 * @param cMeterMpRelaList
	 *            电表与计量点关联   
	 */
	public String savePrivacyExcel(List<GTg> gtgList, List<CCons> cconsList,
			List<RcpRunJdbc> rcpList, List<RcpParaJdbc> rcpparaList,
			List<RtmnlRunJdbc> tmnlList, List<CmpJdbc> cmpList,
			List<CmeterJdbc> cmeterList, List<RCpConsRela> rCpConsRelaList,
			List<RConsTmnlRela> rConsTmnlRelaList,List<CMeterMpRela>  cMeterMpRelaList) throws Exception {
		try {
			this.iImportExcelDao.savePrivacyExcel(gtgList, cconsList, rcpList,
					rcpparaList, tmnlList, cmpList, cmeterList,
					rCpConsRelaList, rConsTmnlRelaList,cMeterMpRelaList);
			String msg = "台区导入" + gtgList.size() + "条<br>";
			msg += "用户数据导入" + cconsList.size() + "条<br>";
			msg += "采集点数据导入" + rcpList.size() + "条<br>";
			msg += "采集点参数数据导入" + rcpparaList.size() + "条<br>";
			msg += "终端数据导入" + tmnlList.size() + "条<br>";
			msg += "计量点数据导入" + cmpList.size() + "条<br>";
			msg += "电表数据导入" + cmeterList.size() + "条<br> 重复数据不重复导入";
			
			//重新下装以下数据库表到缓存
//			dbDownLoad("CCons");
//			dbDownLoad("RCp");
//			dbDownLoad("RCpCommPara");
//			dbDownLoad("RCollObj");
//			dbDownLoad("EDataMp");
//			dbDownLoad("RTmnlRun");
			dbDownLoad("com.nari.ami.database.map.runcontrol.RTmnlRun",
					"tmnlAssetNo", "getTmnlAssetNo", 0);

			return msg;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	
	/**
	 * dbDownLoad
	 * @param className
	 * @describe  下装coherence 表
	 */
	public void dbDownLoad(String className) {

		DBDownLoadParam param = getParam(className);
		try {
			DownloadThread load = new DownloadThread(param);
			load.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private DBDownLoadParam getParam(String className) {
		List<DBDownLoadParam> downloadParam = DBDownLoadContext.loadContext();

		for (int i = 0; i < downloadParam.size(); i++) {
			DBDownLoadParam param = downloadParam.get(i);
			if (param.getClassName().equalsIgnoreCase(className))
				return param;
		}

		return null;
	}
	/**
	 * 
	 * @param className
	 * @param key
	 * @param keyMethod
	 * @param loadType
	 * @describe 直接重新装载表 由于调用接口取download.xml 找不到 直接写死参数，如果coherence 的
	 *           download.xml 修改， 则需要修改调用时的参数 如果需要灵活，需要由前置组提供能用的接口
	 */
	public void dbDownLoad(String className, String key, String keyMethod,
			int loadType) {
		DBDownLoadParam param = new DBDownLoadParam(className, key, keyMethod,
				loadType);

		try {
			DownloadThread load = new DownloadThread(param);
			load.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
	