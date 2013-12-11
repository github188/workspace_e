package com.nari.dao.jdbc.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.nari.dao.BaseDao;
import com.nari.dao.jdbc.PublicDbDAO;
import com.nari.exception.ExceptionHandle;
import com.nari.global.Globals;
import com.nari.util.StringUtil;

public class PublicDbDAOJdbc extends BaseDao implements PublicDbDAO{
	
	protected transient final Log log = LogFactory.getLog(getClass());

	/**
	 * 电表变更，从中间库取数据
	 * @param appNo 
	 * @param appNo 申请编号
	 */
	public List<?> getRTmnlTaskSr(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_TMNL_TASK_SR WHERE APP_NO=? AND NEW_METER_ID IS NOT NULL ");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{appNo});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	

	/**
	 * 电表变更，从中间库取数据
	 * @param appNo 申请编号
	 */
	public List<?> getRTmnlTaskSrByAppNo(String appNo){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_TMNL_TASK_SR WHERE APP_NO=? ");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{appNo});	
		return list;
	}

	/**
	 * 插入终端调试信息
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
	 */
	public List<?> getRTmnlDebugByDebugId(Long debugId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_TMNL_DEBUG WHERE DEBUG_ID=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{debugId});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 更新负控终端工作正常标志
	 * 1正常、0不正常
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
	 * 更新负控开关工作正常标志
	 * 1正常、0不正常
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
	 * 更新终端与电能表通讯正常标志
	 * 1正常、0不正常
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
	 * 更新终端与主站通讯正常标志
	 * 1正常、0不正常
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
	 * 更新可控标志
	 * 1可控、0不可控
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
	 * 更新可控标志
	 * 1可远抄、0不可远抄
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
	 */
	public String getSeqREpctCommon() {
		String seq = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SEQ_R_EPCT_COMMON.NEXTVAL FROM DUAL");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{});
		if(list == null || list.size()== 0) {
			return null;
		}
		Map<?, ?> map = (Map<?, ?>) list.get(0);
		seq = String.valueOf(map.get("NEXTVAL"));
		return seq;
	}

	/**
	 * 根据终端ID从中间库取得单位代码
	 * @param tmnlId
	 */
	public List<?> getPublicTmnlOrg(String consId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ORG_NO FROM C_CONS WHERE CONS_ID=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{consId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	
	/**
	 * 根据终端ID从中间库取得单位代码
	 * @param tmnlId
	 */
	public List<?> getPublicTgOrg(String tgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM G_TG WHERE TG_ID=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{tgId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}

	@Override
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
	 */
	public String checkTmnlMeter(String tmnlId, String cpNo, String appNo, String tmnlTaskType, String flag, String newTmnlId) {		
		String tmnlAddr = null;     //终端资产
		String meterAddr = null;    //表地址
		String meterAsset = null;   //表资产
		String protocolCode = null; //终端通讯规约
		String collMode = null;     //采集方式
		String meterID = null;      //电能表ID
		String commPortNo = null;   //电能表通讯规约
        String rclNum = null;       //中间库采集对象数量
        String cMNum = null;        //中间库运行电能表数量
        String tmnlTypeCode = null; //终端类型
        String cpTypeCode = null;   //采集点类型
	
		if (!"".equals(newTmnlId)) {
			log.info("-------------------终端ID变更-------tmnlId: "+tmnlId+"--------------newTmnlId: "+newTmnlId+"------");
			tmnlId = newTmnlId;
		}
		
		//删除终端
		if ("02".equals(tmnlTaskType)) {
			return "1";
		}
		
		String sqlTmnlTask = "SELECT * FROM I_TMNL_TASK I WHERE I.APP_NO=?";
		List<?> listTask = this.getJdbcDAO().queryForList(sqlTmnlTask.toString(),new Object[]{appNo});
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT R.TERMINAL_ADDR, R.COLL_MODE, R.PROTOCOL_CODE, R.TERMINAL_TYPE_CODE FROM R_TMNL_RUN R WHERE R.TERMINAL_ID=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{tmnlId});
		if(list != null && list.size()> 0) {
			Map<?, ?> mapTmnl = (Map<?, ?>) list.get(0);
			tmnlAddr = StringUtil.removeNull(mapTmnl.get("TERMINAL_ADDR")) ;
			protocolCode = StringUtil.removeNull(mapTmnl.get("PROTOCOL_CODE"));
			collMode = StringUtil.removeNull(mapTmnl.get("COLL_MODE"));
			tmnlTypeCode = StringUtil.removeNull(mapTmnl.get("TERMINAL_TYPE_CODE"));
		}
		
		//关口验证
	    sql = new StringBuffer();
	    sql.append(" SELECT * FROM R_CP T INNER JOIN R_TMNL_RUN R ON R.CP_NO=T.CP_NO AND R.TERMINAL_ID=?" );
	    List<?> rCpLst = this.getJdbcYXDAO().queryForList(sql.toString(), new Object[]{tmnlId});
	    if (rCpLst != null && rCpLst.size() > 0) {
	    	Map<?,?> rCpMap = (Map<?,?>) rCpLst.get(0);
	    	cpTypeCode = StringUtil.removeNull(rCpMap.get("CP_TYPE_CODE"));
	    	cpNo = StringUtil.removeNull(rCpMap.get("CP_NO"));
	    }
	    //关口侧 判断对应的计量点存在用户ID
	    if ("1".equals(cpTypeCode)) {
	    	sql = new StringBuffer();
	 	    sql.append(" SELECT C.* FROM C_MP C INNER JOIN R_CP_MP_RELA R ON R.MP_ID=C.MP_ID AND R.CP_NO=? " );
	 	    List<?> cMpLst = this.getJdbcYXDAO().queryForList(sql.toString(), new Object[]{cpNo});
	 	    for (int i = 0; i < cMpLst.size(); i ++) {
	 	    	Map<?,?> cMpMap = (Map<?,?>) cMpLst.get(i);
	 	    	String consId = StringUtil.removeNull(cMpMap.get("CONS_ID"));
	 	    	if ("".equals(consId)) {
	 	    		//计量点表中没有变电站编码信息
	 	    		return Globals.EPCT_NO_SUBS_INFO;
	 	    	} else {
	 	    		String subSql = " SELECT * FROM G_SUBS T WHERE T.SUBS_ID=?";
	 	    		List<?> subLst = this.getJdbcYXDAO().queryForList(subSql, new Object[]{consId});
	 	    		if (subLst != null && subLst.size() == 0) {
	 	    			//变电站信息没有同步到营销中间库
	 	    			return Globals.EPCT_NO_SYN_SUBS_INFO;
	 	    		}
	 	    	}
	 	    }	
	    }
	    
	    
		//如果不存在申请号 就进行验证
		if (null != listTask && listTask.size() == 0) {
			//4为102规约
			if (("03".equals(tmnlTaskType) || "01".equals(tmnlTaskType)) && !"4".equals(protocolCode)) {
				log.info("----------------------------------------------新终端ID: "+newTmnlId);
				log.info("----------------------------------------------老终端ID: "+tmnlId);
				log.info("----------------------------------------------数据长度: "+list.size());
				if(list != null && list.size()== 0) {
					log.debug("终端ID："+tmnlId+"-------------------------------"+Globals.NO_TMNL);
					return Globals.NO_TMNL;//中间库运行终端表中无终端通讯地址
				} else {
					//采集方式为空
					if ("".equals(collMode)) {
						log.info("终端ID："+tmnlId+"-------------------------------"+Globals.NO_TMNL_TYPE_CODE_COLL_MODE);
						return Globals.NO_TMNL_TYPE_CODE_COLL_MODE;
					}
					
					//负控、低压集抄  判断规约
					if ("01".equals(tmnlTypeCode) || "02".equals(tmnlTypeCode)) {
						//负控 规约不是 04，05，698
						if (!"02".equals(protocolCode) && !"03".equals(protocolCode) && !"04".equals(protocolCode) && !"05".equals(protocolCode) && !"06".equals(protocolCode)) {
							log.debug("终端ID："+tmnlId+"-------------------------------"+Globals.UNKONWN_PROTOCOL);
							return Globals.UNKONWN_PROTOCOL;
						}
					}

					//终端地址重复
					if ("01".equals(tmnlTaskType)) {
						sql = new StringBuffer();
						sql.append("SELECT R.TERMINAL_ADDR FROM R_TMNL_RUN R WHERE R.TERMINAL_ADDR=?");
						List<?> listRun = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAddr});
						if (listRun != null && listRun.size() > 0) {
							log.debug("终端ID："+tmnlId+"-------------------------------"+Globals.DUPLICATE_TMNL_ADDRESS);
							return Globals.DUPLICATE_TMNL_ADDRESS;
						}
					}
					
					String rclSql = " SELECT COUNT(*) AS RNUM FROM R_COLL_OBJ T WHERE T.CP_NO=? ";
					List<?> rclLst = getJdbcYXDAO().queryForList(rclSql, new Object[]{cpNo});
					if (rclLst != null & rclLst.size() > 0) {
						Map<?, ?> rclMap = (Map<?,?>) rclLst.get(0);
						rclNum = StringUtil.removeNull(rclMap.get("RNUM"));
					}
					String cmSql = " SELECT COUNT(*) AS CNUM FROM C_METER C WHERE C.METER_ID IN (SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO = ?)";
					List<?> cmLst = getJdbcYXDAO().queryForList(cmSql, new Object[]{cpNo});
					if (cmLst != null & cmLst.size() > 0) {
						Map<?, ?> cmMap = (Map<?,?>) cmLst.get(0);
						cMNum = StringUtil.removeNull(cmMap.get("CNUM"));
					}
					
					//采集对象和运行电能表数量
					if (rclNum != null && cMNum != null) {
						if (!rclNum.equals(cMNum)) {
							log.debug("终端ID："+tmnlId+"-------------------------------"+Globals.NOT_EQUAL_NUM);
							return Globals.NOT_EQUAL_NUM;
						}
					}
					
					//中间库没有运行电能表
					if ("0".equals(cMNum)) {
						log.debug("终端ID："+tmnlId+"-------------------------------"+Globals.NO_CMETER);
						return Globals.NO_CMETER;
					}
					
					
					//一个采集点编号存在两个终端
					String rcpSql = " SELECT * FROM R_TMNL_RUN T WHERE T.CP_NO=? AND T.TERMINAL_ID <> '"+tmnlId+"' ";
					List<?> rcpLst = this.getJdbcDAO().queryForList(rcpSql, new Object[]{cpNo});
					if (rcpLst != null && rcpLst.size() > 0) {
						return Globals.DUPLICATE_CP_NO;
					}
					
				}
			}
				
			/**--------------------------------------电能表信息验证----------------------------------------*/
//			if ("01".equals(tmnlTaskType) || ("05".equals(tmnlTaskType) && "1".equals(flag)) || ("03".equals(tmnlTaskType) && "1".equals(flag))) {
			if ("01".equals(tmnlTaskType) || "05".equals(tmnlTaskType) || "03".equals(tmnlTaskType)) {
						
				sql = new StringBuffer();
				sql.append("SELECT D.ASSET_NO, D.METER_DIGITS FROM D_METER D, R_COLL_OBJ R WHERE R.CP_NO=? AND R.METER_ID=D.METER_ID ");
				List<?> listObj = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});
				
				if (listObj != null && listObj.size() == 0) {
					if ("".equals(meterAsset)) {
						log.debug("采集点编号："+cpNo+"-------------------------------"+Globals.NO_OR_DUPLICATE_METER);
						return Globals.NO_OR_DUPLICATE_METER;
					}
				}
				
				String rclSql = " SELECT COUNT(*) AS RNUM FROM R_COLL_OBJ T WHERE T.CP_NO=? ";
				List<?> rclLst = getJdbcYXDAO().queryForList(rclSql, new Object[]{cpNo});
				if (rclLst != null & rclLst.size() > 0) {
					Map<?, ?> rclMap = (Map<?,?>) rclLst.get(0);
					rclNum = StringUtil.removeNull(rclMap.get("RNUM"));
				}
				String cmSql = " SELECT COUNT(*) AS CNUM FROM C_METER C WHERE C.METER_ID IN (SELECT R.METER_ID FROM R_COLL_OBJ R WHERE R.CP_NO = ?)";
				List<?> cmLst = getJdbcYXDAO().queryForList(cmSql, new Object[]{cpNo});
				if (cmLst != null & cmLst.size() > 0) {
					Map<?, ?> cmMap = (Map<?,?>) cmLst.get(0);
					cMNum = StringUtil.removeNull(cmMap.get("CNUM"));
				}
				
				//采集对象和运行电能表数量
				if (rclNum != null && cMNum != null) {
					if (!rclNum.equals(cMNum)) {
						log.debug("终端ID："+tmnlId+"-------------------------------"+Globals.NOT_EQUAL_NUM);
						return Globals.NOT_EQUAL_NUM;
					}
				}
				
				//电能表位数
				for (int i = 0; i < listObj.size(); i++) {
					Map<?, ?> mapTmnl = (Map<?, ?>) listObj.get(0);
					meterAsset = StringUtil.removeNull(mapTmnl.get("ASSET_NO"));
					String meterDigits = StringUtil.removeNull(mapTmnl.get("METER_DIGITS"));
					String meterId = StringUtil.removeNull(mapTmnl.get("METER_ID"));
					
					if ("".equals(meterDigits)) {
						log.debug("表ID: "+meterId+"-------------------------------"+Globals.METER_DIGITS_ERROR);
						return Globals.METER_DIGITS_ERROR;
					}
				}
				
				//检验表的通讯地址和数据库中是否重复 
				sql = new StringBuffer();
				sql.append("SELECT R.COMM_ADDR1,R.CT_RATIO, R.PT_RATIO, R.METER_ID, R.T_FACTOR FROM R_COLL_OBJ R WHERE R.CP_NO=? ");
				List<?> listColl = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});
				
				for (int i = 0; i < listColl.size(); i++) {
					Map<?, ?> mapTmnl = (Map<?, ?>) listColl.get(i);
					meterAddr = StringUtil.removeNull(mapTmnl.get("COMM_ADDR1"));
					meterID = StringUtil.removeNull(mapTmnl.get("METER_ID"));
					
					if ("".equals(meterAddr)) {
						String meterAssetNo = "";
						String meterSql = " SELECT * FROM D_METER T WHERE T.METER_ID=? ";
						List<?> meterLst = getJdbcYXDAO().queryForList(meterSql.toString(),new Object[]{meterID});
						if (meterLst != null && meterLst.size() > 0) {
							Map<?,?> meterMap = (Map<?,?>) meterLst.get(0);
							meterAssetNo = StringUtil.removeNull(meterMap.get("ASSET_NO"));
						}
						log.debug("表ID: "+meterID+"-------------------------------"+Globals.NO_METER_ADDRESS);
						return meterAssetNo +" : "+ Globals.NO_METER_ADDRESS;
					}
					
					//电能表通讯规约/r_coll_obj
					sql = new StringBuffer();
					sql.append("SELECT * FROM R_COLL_OBJ R WHERE R.METER_ID=?");
					List<?> listTemp2 = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{meterID});
					for (int j = 0; j < listTemp2.size(); j++) {
						Map<?, ?> mapMeterScheme = (Map<?, ?>) listTemp2.get(j);
						commPortNo = StringUtil.removeNull(mapMeterScheme.get("COMM_PROT_NO"));
					}
					if ("".equals(commPortNo)) {
						String meterAssetNo = "";
						String meterSql = " SELECT * FROM D_METER T WHERE T.METER_ID=? ";
						List<?> meterLst = getJdbcYXDAO().queryForList(meterSql.toString(),new Object[]{meterID});
						if (meterLst != null && meterLst.size() > 0) {
							Map<?,?> meterMap = (Map<?,?>) meterLst.get(0);
							meterAssetNo = StringUtil.removeNull(meterMap.get("ASSET_NO"));
						}
						log.debug("表ID: "+meterID+"-------------------------------"+Globals.NO_METER_ADDRESS_COMMNO);
						//电能表通讯规约为空
						return meterAssetNo +" : "+ Globals.NO_METER_ADDRESS_COMMNO;
					}
					
					//检验 集抄用户  居民表在中间库 用户信息
					if ("1".equals(flag)) {
						String typeCode = null;
						String mpAttrCode = null;

						StringBuffer sqlMp = new StringBuffer();
						sqlMp.append(" SELECT c.type_code,c.mp_attr_code from c_mp c,c_meter_mp_rela t WHERE t.meter_id=? AND t.mp_id=c.mp_id ");
						List<?> mpLst = this.getJdbcYXDAO().queryForList(sqlMp.toString(),new Object[]{meterID});
						if (null != mpLst && mpLst.size() > 0 ) {
							Map<?,?> mpMap = (Map<?,?>) mpLst.get(0);
							typeCode = StringUtil.removeNull(mpMap.get("TYPE_CODE"));
							mpAttrCode = StringUtil.removeNull(mpMap.get("MP_ATTR_CODE"));
						}
						if (!("03".equals(typeCode) && "02".equals(mpAttrCode))) {
							String consSql = " SELECT C.CONS_NO FROM C_MP A, C_METER_MP_RELA B, C_CONS C WHERE B.MP_ID = A.MP_ID AND A.CONS_ID = C.CONS_ID AND B.METER_ID = ?";
							List<?> consLst = this.getJdbcYXDAO().queryForList(consSql.toString(),new Object[]{meterID});
						    if (consLst != null && consLst.size() == 0) {
						    	
						    	String meterAssetNo = "";
								String meterSql = " SELECT * FROM D_METER T WHERE T.METER_ID=? ";
								List<?> meterLst = getJdbcYXDAO().queryForList(meterSql.toString(),new Object[]{meterID});
								if (meterLst != null && meterLst.size() > 0) {
									Map<?,?> meterMap = (Map<?,?>) meterLst.get(0);
									meterAssetNo = StringUtil.removeNull(meterMap.get("ASSET_NO"));
								}
								log.debug("表ID: "+meterID+"-------------------------------"+Globals.EPCT_NO_CONS_INFO);
						    	
								//居民表在中间库没有对应的用户信息
								return meterAssetNo+" : "+ Globals.EPCT_NO_CONS_INFO;
						    }
						}	
					}
					
				}
			}
		}
		//检验成功
		return "1";
	}

	
	/**
	 * 根据终端ID从中间库取得TmnlTypeCode
	 * @param tmnlId
	 * 
	 */
	public List<?> getPublicTmnlTypeCodeById(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TERMINAL_TYPE_CODE FROM R_TMNL_RUN WHERE TERMINAL_ID=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{tmnlId});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 东软营销的方法，朗新营销没有用到
	 */
	public List<?> getCancleConsNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 东软营销的方法，朗新营销没有用到
	 */
	public List<?> getChgAppNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 东软营销的方法，朗新营销没有用到
	 */
	public List<?> getMidRcollObjByCpNo(String cpNo) {
		// TODO Auto-generated method stub
		return null;
	}
}