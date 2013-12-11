package com.nari.dao.jdbc.impl.dr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import weblogic.entitlement.util.Logger;

import com.nari.dao.BaseDao;
import com.nari.dao.jdbc.PublicDbDAO;
import com.nari.exception.ExceptionHandle;
import com.nari.global.Globals;
import com.nari.util.StringUtil;

@SuppressWarnings("unused")
public class PublicDbDAOJdbc extends BaseDao implements PublicDbDAO{
	
	protected transient final Log log = LogFactory.getLog(getClass());

	/**
	 * 电表变更，从中间库取数据
	 * 
	 * @param appNo 申请编号
	 * @return List list or null
	 */
	public List<?> getRTmnlTaskSr(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_TMNL_TASK_SR WHERE APP_NO=?");
		log.debug("getRTmnlTaskSr" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{appNo});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}

	/**
	 * 插入终端调试信息
	 * @param debugId
	 * @param terminalId
	 */
	public void insertRTmnlDebug(String debugId, String terminalId) {
		final StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO R_TMNL_DEBUG(DEBUG_ID, TERMINAL_ID, DEBUG_TIME) "); 
		sql.append(" VALUES(?,?,sysdate) ");
		log.debug("insertTerminalTestNotice insert sql:" + sql.toString());
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{debugId, terminalId});
			getJdbcDAO().update(sql.toString(),new Object[]{debugId, terminalId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}	
	}
	
	/**
	 * 根据debugId取终端调试信息
	 * @param debugId
	 * 
	 * @return List list or null 
	 */
	public List<?> getRTmnlDebugByDebugId(Long debugId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_TMNL_DEBUG WHERE DEBUG_ID=?");
		log.debug("getRTmnlDebugByDebugId :" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{debugId});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 更新负控终端工作正常标志       "1"正常、"0"不正常
	 * @param debugId
	 * @param terminalFlag
	 */
	public void updateRTmnlDebugTerminalFlag(Long debugId, String terminalFlag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_TMNL_DEBUG SET TERMINAL_FLAG =? WHERE DEBUG_ID = ?") ;
		log.debug("updateRTmnlDebugTerminalFlag:" + sql.toString()) ;
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{terminalFlag,debugId});
			getJdbcDAO().update(sql.toString(),new Object[]{terminalFlag,debugId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}
	
	/**
	 * 更新负控开关工作正常标志   "1"正常、"0"不正常
	 * @param debugId
	 * @param switchFlag
	 */
	public void updateRTmnlDebugSwitchFlag(Long debugId, String switchFlag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_TMNL_DEBUG SET SWITCH_FLAG =? WHERE DEBUG_ID = ?") ;
		log.debug("updateRTmnlDebugSwitchFlag:" + sql.toString()) ;
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{switchFlag,debugId});
			getJdbcDAO().update(sql.toString(),new Object[]{switchFlag,debugId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}
	
	/**
	 * 更新终端与电能表通讯正常标志   "1"正常、"0"不正常
	 * @param debugId
	 * @param meterCommFlag
	 */
	public void updateRTmnlDebugMeterCommFlag(Long debugId, String meterCommFlag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_TMNL_DEBUG SET METER_COMM_FLAG =? WHERE DEBUG_ID = ?") ;
		log.debug("updateRTmnlDebugMeterCommFlag:" + sql.toString()) ;
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{meterCommFlag,debugId});
			getJdbcDAO().update(sql.toString(),new Object[]{meterCommFlag,debugId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}
	
	/**
	 * 更新终端与主站通讯正常标志   "1"正常、"0"不正常
	 * @param debugId
	 * @param masterCommFlag
	 */
	public void updateRTmnlDebugMasterCommFlag(Long debugId, String masterCommFlag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_TMNL_DEBUG SET MASTER_COMM_FLAG =? WHERE DEBUG_ID = ?") ;
		log.debug("updateRTmnlDebugMasterCommFlag:" + sql.toString()) ;
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{masterCommFlag,debugId});
			getJdbcDAO().update(sql.toString(),new Object[]{masterCommFlag,debugId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}
	
	/**
	 * 更新可控标志   "1"可控、"0"不可控
	 * @param debugId
	 * @param ctrlFlag
	 */
	public void updateRTmnlDebugCtrlFlag(Long debugId, String ctrlFlag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_TMNL_DEBUG SET CTRL_FLAG =? WHERE DEBUG_ID = ?") ;
		log.debug("updateRTmnlDebugCtrlFlag:" + sql.toString()) ;
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{ctrlFlag,debugId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}
	
	/**
	 * 更新可控标志   "1"可远抄、"0"不可远抄
	 * 
	 * @param debugId
	 * @param cllFlag
	 */
	public void updateRTmnlDebugCllFlag(Long debugId, String cllFlag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_TMNL_DEBUG SET COLL_FLAG =? WHERE DEBUG_ID = ?") ;
		log.debug("updateRTmnlDebugCllFlag:" + sql.toString()) ;
		try {
			getJdbcYXDAO().update(sql.toString(),new Object[]{cllFlag,debugId});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}

	/**
	 * 取seq_r_epct_common.nextval
	 * @return String seq or null
	 */
	public String getSeqREpctCommon() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SEQ_R_EPCT_COMMON.NEXTVAL FROM DUAL");
		log.debug("getSeqREpctCommon :" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{});
		if(list == null || list.size()== 0) {
			return null;
		}
		Map<?, ?> map = (Map<?, ?>) list.get(0);
		String seq = String.valueOf(map.get("NEXTVAL"));
		return seq;
	}

	/**
	 * 根据终端ID从中间库取得单位代码
	 * @param consId
	 * @return List list or null
	 */
	public List<?> getPublicTmnlOrg(String consId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ORG_NO FROM C_CONS WHERE CONS_ID=?");
		log.debug("getPublicTmnlOrg :" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{consId});
		if(list == null || list.size()== 0) {
			list = this.getOrgByScheme(consId);
			if(list == null || list.size()== 0) {
				return null;
			}
		}
		return list;
	}
	
	/**
	 * 根据终端ID从中间库取得TmnlTypeCode
	 * @param tmnlId
	 * @return List list or null
	 */
	public List<?> getPublicTmnlTypeCodeById(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TERMINAL_TYPE_CODE FROM R_TMNL_RUN WHERE TERMINAL_ID=?");
		log.debug("getPublicTmnlTypeCodeById :" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{tmnlId});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据consId从中间库S_BATCH_CUST_APP表中取得单位代码
	 * @param consId
	 * @return List list or null
	 */
	private List<?> getOrgByScheme(String consId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ORG_NO FROM S_BATCH_CUST_APP WHERE CONS_ID=?");
		log.debug("getOrgByScheme :" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{consId});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据终端ID从中间库取得单位代码
	 * @param tmnlId
	 * @return List list or null
	 */
	public List<?> getPublicTgOrg(String tgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM G_TG WHERE TG_ID=?");
		log.debug("getPublicTgOrg :" + sql.toString());
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{tgId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}

	/**
	 * 根据终端ID从中间库取运行终端信息
	 * @param terminalId
	 * @return List list or null
	 */
	public List<?> getRTmnlDebugByTmnlId(String terminalId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_TMNL_DEBUG WHERE TERMINAL_ID=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	
	/**
	 * 校验是否有重复的终端和电能表的地址
	 * @param tmnlId 终端编号
	 * @param cpNo   采集点编号
	 * @param appNo  申请编号
	 * @param tmnlTaskType  终端更换标志
	 * @param flag 
	 * @param newTmnlId  新终端ID
	 * 
	 * @return String 成功返回"1",失败返回失败提示信息
	 */
	public String checkTmnlMeter(String tmnlId, String cpNo, String appNo, String tmnlTaskType, String flag, String newTmnlId) {		
		String tmnlAddr = null;     //终端资产
		String meterAddr = null;    //表地址
		String meterAsset = null;   //表资产
		String protocolCode = null; //终端通讯规约
		String collMode = null;     //采集方式
		String ct = null;           //CT
		String pt = null;           //PT
		String tFactor = null;      //综合倍率
		String meterID = null;      //电能表ID
		String commNo = null;       //电能表通讯规约/c_meter
		String commPortNo = null;   //电能表通讯规约/r_coll_obj_scheme
		
		if (!"".equals(newTmnlId)) {
			tmnlId = newTmnlId;
			log.info("-------------------终端ID变更-------" + tmnlId + "--------------" + newTmnlId + "------");
		}
		
		String sqlTmnlTask = "SELECT * FROM I_TMNL_TASK I WHERE I.APP_NO=?";
		log.debug(sqlTmnlTask);
		List<?> listTask = this.getJdbcDAO().queryForList(sqlTmnlTask.toString(),new Object[]{appNo});
		//如果不存在申请号 就进行验证
		//新装 和 更换终端 时 校验 数据合法性
		/*
		 * 表变更就不验证终端类
		 *  || "05".equals(tmnlTaskType)
		 */
		if ("01".equals(tmnlTaskType) || "03".equals(tmnlTaskType)) {
			StringBuffer sql = null;
			// 表变更不走终端验证
			// if (!"1".equals(flag)) {
			//中间库运行终端表取得对应的终端地址码
			sql = new StringBuffer();
			sql.append("SELECT R.TERMINAL_ADDR, R.COLL_MODE, R.PROTOCOL_CODE FROM R_TMNL_RUN R WHERE R.TERMINAL_ID=?");
			log.debug(sql);
			List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{tmnlId});
			log.info("----------------------------------------------newtmnlId:" + newTmnlId);
			log.info("----------------------------------------------tmnlId:" + tmnlId);
			log.info("----------------------------------------------数据长度:" + list.size());
			if(list != null && list.size()== 0) {
				log.info(tmnlId+"-------------------------------" + Globals.NO_TMNL);
				//中间库运行终端表中无终端通讯地址
				return Globals.NO_TMNL;
			} else {
				Map<?, ?> mapTmnl = (Map<?, ?>) list.get(0);
				tmnlAddr = StringUtil.removeNull(mapTmnl.get("TERMINAL_ADDR")) ;
				protocolCode = StringUtil.removeNull(mapTmnl.get("PROTOCOL_CODE"));
				collMode = StringUtil.removeNull(mapTmnl.get("COLL_MODE"));
				if ("".equals(protocolCode) || "".equals(collMode)) {
					log.info(tmnlId+"-------------------------------"+Globals.NO_TMNL_TYPE_CODE_COLL_MODE);
					//终端通讯规约或者采集方式为空
					return Globals.NO_TMNL_TYPE_CODE_COLL_MODE;
				}

				sql = new StringBuffer();
				sql.append("SELECT R.TERMINAL_ADDR FROM R_TMNL_RUN R WHERE R.TERMINAL_ADDR=?");
				log.debug(sql);
				List<?> listRun = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAddr});
				if (listRun != null && listRun.size() > 0) {
					log.info(tmnlId+"-------------------------------"+Globals.DUPLICATE_TMNL_ADDRESS);
					//终端通信地址重复
					return Globals.DUPLICATE_TMNL_ADDRESS;
				}
			}
			
			//检验电能表资产是否重复
			sql = new StringBuffer();
			sql.append("SELECT D.ASSET_NO, D.METER_DIGITS FROM D_METER D, R_COLL_OBJ R WHERE R.CP_NO=? AND R.METER_ID=D.METER_ID ");
			log.debug(sql);
			List<?> listObj = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});
			for (int i = 0; i < listObj.size(); i++) {
				Map<?, ?> mapTmnl = (Map<?, ?>) listObj.get(0);
				meterAsset = StringUtil.removeNull(mapTmnl.get("ASSET_NO"));
				String meterDigits = StringUtil.removeNull(mapTmnl.get("METER_DIGITS"));
				if (meterAsset == null || "".equals(meterAsset)) {
					log.info(cpNo+"-------------------------------"+Globals.NO_OR_DUPLICATE_METER);
					//中间库电能表无资产号
					return Globals.NO_OR_DUPLICATE_METER;
				}
				
//					int zNum = Integer.parseInt(meterDigits.substring(0, 1));//整数
//					int xNum = Integer.parseInt(meterDigits.substring(2, 3));//小数
//					if (zNum + xNum != 8) {
//						log.info(cpNo+"-------------------------------"+Globals.METER_DIGITS_ERROR);
//						//整数位小数位之和不正确
//						return Globals.METER_DIGITS_ERROR;
//					}

				sql = new StringBuffer();
				sql.append("SELECT * FROM C_METER C WHERE C.ASSET_NO=?");
				log.debug(sql);
				List<?> listTemp = getJdbcDAO().queryForList(sql.toString(),new Object[]{meterAsset});
				
				sql = new StringBuffer();
				sql.append("SELECT R.TMNL_ASSET_NO FROM R_CONS_TMNL_RELA R WHERE R.CONS_NO IN (SELECT T.CONS_NO FROM C_METER T WHERE T.ASSET_NO=?)");
				List<?> tmnlIdList = getJdbcDAO().queryForList(sql.toString(),new Object[]{meterAsset});
				String tmnlAssetNo = null;
				boolean isOldTmnl = false;
				if (null != tmnlIdList && tmnlIdList.size() > 0) {
					tmnlAssetNo = StringUtil.removeNull(mapTmnl.get("TMNL_ASSET_NO"));
					if (tmnlId.equals(tmnlAssetNo)) {
						isOldTmnl = true;//终端填错信息的情况
					}
				}
				if (listTemp != null && listTemp.size() > 0 && !isOldTmnl) {
					log.info(meterAsset+"-------------------------------+"+Globals.NO_OR_DUPLICATE_METER);
					//电能表资产号重复
					return Globals.NO_OR_DUPLICATE_METER;
				}
			}
			
			//一终端多表（集抄换表）检验表的通讯地址和数据库中是否重复 
			sql = new StringBuffer();
			sql.append("SELECT R.CT_RATIO, R.PT_RATIO, R.METER_ID, R.T_FACTOR FROM R_COLL_OBJ R WHERE R.CP_NO=? ");
			log.debug(sql);
			List<?> listColl = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});
			for (int i = 0; i < listColl.size(); i++) {
				Map<?, ?> mapTmnl = (Map<?, ?>) listColl.get(i);
				
				ct = StringUtil.removeNull(mapTmnl.get("CT_RATIO"));
				pt = StringUtil.removeNull(mapTmnl.get("PT_RATIO"));
				tFactor = StringUtil.removeNull(mapTmnl.get("T_FACTOR"));
				meterID = StringUtil.removeNull(mapTmnl.get("METER_ID"));
				
				sql = new StringBuffer();
				sql.append("SELECT R.METER_KIND FROM D_METER R WHERE R.METER_ID = ? ");
				log.debug(sql);
				List<?> listDMeter = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{meterID});
				for (int j = 0; j < listColl.size(); j++) {
					Map<?, ?> mapDMeter = (Map<?, ?>) listDMeter.get(0);
					meterAddr = StringUtil.removeNull(mapDMeter.get("METER_KIND"));
					log.info("------------------------------------------表通讯地址验证-------------------------------------------------------"+meterAddr);
					if (meterAddr == null || "".equals(meterAddr)) {
						log.info(meterAsset+"-------------------------------"+Globals.NO_METER_ADDRESS);
						//中间库电能表地址为空或者不存在
						return Globals.NO_METER_ADDRESS;
					}
				}
				
				//判断CT PT
				if ("".equals(ct) || "".equals(pt) ) {
					log.info(meterAsset+"-------------------------------"+Globals.CT_PT_IS_NULL);
					//CT||PT值为空
					return Globals.CT_PT_IS_NULL;
				}
				//判断CT*PT 是否和综合倍率相同
				if ("".equals(tFactor)) {
					log.info(meterAsset+"-------------------------------"+Globals.TFACTOR_IS_NULL);
					//综合倍率为空
					return Globals.TFACTOR_IS_NULL;
				}
				//判断CT*PT 是否和综合倍率相同
				if ((Long.parseLong(ct)*Long.parseLong(pt)) != Long.parseLong(tFactor)) {
					log.info(meterAsset+"-------------------------------"+Globals.TFACTOR);
					//CT*PT不等于综合倍率
					return Globals.TFACTOR;
				}
				
				//电能表通讯规约/r_coll_obj
				sql = new StringBuffer();
				sql.append("SELECT * FROM C_METER R WHERE R.METER_ID=?");
				log.debug(sql);
				List<?> listTemp2 = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{meterID});
				for (int j = 0; j < listTemp2.size(); j++) {
					Map<?, ?> mapMeter = (Map<?, ?>) listTemp2.get(j);
					//电能表通讯规约
					commNo = StringUtil.removeNull(mapMeter.get("COMM_NO"));
				}
				if ("".equals(commNo)) {
					//电能表通讯规约为空
					log.info(meterAsset+"-------------------------------"+Globals.NO_METER_ADDRESS_COMMNO);
					return Globals.NO_METER_ADDRESS_COMMNO;
				}
			}
		}
		
		//检验成功
		return "1";
	}

	/**
	 * 取销户信息
	 * @return List list
	 */
	public List<?> getCancleConsNo() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT cons_no FROM r_cons_no t  ");
		sql.append(" WHERE to_char(update_date, 'yyyymmdd') = to_char(SYSDATE - 1, 'yyyymmdd') ");
		sql.append(" AND t.process_flag = 1 ");
		sql.append(" AND t.update_status = '03' ");
		log.debug(sql);
		List<?> list = getJdbcYXDAO().queryForList(sql.toString());
		log.debug("getCancleConsNo:" + sql.toString()) ;
		return list;
	}

	/**
	 * 取归档的申请编号
	 * @return List list
	 */
	public List<?> getChgAppNo() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT cp_no, app_no ");
		sql.append(" FROM r_cp_no a ");
		sql.append(" WHERE to_char(update_date, 'yyyymmdd') = to_char(SYSDATE - 1, 'yyyymmdd') ");
		sql.append(" AND process_flag = 1 ");
		log.debug(sql);
		List<?> list = getJdbcYXDAO().queryForList(sql.toString());
		log.debug("getCancleConsNo:" + sql.toString()) ;
		return list;
	}

	/**
	 * 取采集点对象
	 */
	public List<?> getMidRcollObjByCpNo(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from r_coll_obj t where t.cp_no = ? ");
		log.debug(sql);
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(), new Object[]{cpNo});
		log.debug("getMidRcollObjByCpNo:" + sql.toString()) ;
		return list;
	}

	@Override
	public List<?> getRTmnlTaskSrByAppNo(String appNo) {
		// TODO Auto-generated method stub
		return null;
	} 
}