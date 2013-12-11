package com.nari.dao.jdbc.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.nari.ami.database.map.basicdata.BClearProtocol;
import com.nari.ami.database.map.device.DMeter;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.ami.database.map.terminalparam.TTmnlTaskId;
import com.nari.dao.BaseDao;
import com.nari.exception.DBAccessException;
import com.nari.exception.ExceptionHandle;
import com.nari.model.CMeter;
import com.nari.model.Edatamp;
import com.nari.model.TbgTask;
import com.nari.model.TbgTaskDetail;
import com.nari.model.TtmnlParam;
import com.nari.util.StringUtil;

/**
 * 终端装接JDBC
 * 
 */
public class MarketTerminalConfigDAOJdbc extends BaseDao  {
	private static Log log = LogFactory.getLog(MarketTerminalConfigDAOJdbc.class);
	
	/**
	 * 更新客户状态（销户的情况，是否已注销状态为1；正常状态为0）
	 * @param consNo	户号
	 * @param status	状态
	 * @return
	 */
	public int updateCustomerStatus(String consNo, String status) {
		List<String> paramList = new ArrayList<String>();
		int nRet = 0 ;
		// 生成更新语句
		StringBuffer sql = new StringBuffer() ;
		sql.append("update C_CONS set STATUS_CODE = ?");
		sql.append(" ,CANCEL_DATE = SYSDATE");
		sql.append(" where HH=?") ;
		
		log.debug("updateCustomerStatus sql:" + sql.toString()) ;
		paramList.add(status);
		paramList.add(consNo);
		// 执行语句
		nRet = getJdbcDAO().update(sql.toString(),paramList.toArray());
		
		return nRet ;
	}
	
	/**
	 * 取终端局编号
	 * consNo用户编号 		
	 */
	public List<?> getAmeterInfo(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append("SELECT TMNL_ASSET_NO FROM R_TMNL_RUN WHERE CONS_NO = ?");

		log.debug("getTerminalInfo sql:" + sql.toString()) ;
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{consNo});		
		
		return list ;
	}
	
	/**
	 * 取表计局编号
	 * consNo用户编号 		
	 */
	public List<?> getTerminalInfo(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append("SELECT ASSET_NO FROM C_METER WHERE CONS_NO = ?");

		log.debug("getAmeterInfo sql:" + sql.toString()) ;
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{consNo});
		
		return list ;
	}
	
	/**
	 * 从中间库取得终端信息
	 * @param tmnlId
	 * @return
	 */
	public List<?> getYXTerminalById(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append(" SELECT * FROM R_TMNL_RUN T WHERE T.TERMINAL_ID=? ");
		log.debug("getYXTerminalById sql:" + sql.toString()) ;
		List<?> list = this.getJdbcYXDAO().queryForList(sql.toString(), new Object[]{tmnlId});
		return list ;
	}
	
	/**
	 * 更新终端测量点状态
	 * @param ZDJH 终端局编号
	 * @param status 测量点是否有效
	 */
	public void updateTerminalMeasurePonitStatus(String zdjh, String status) {
		StringBuffer sql = new StringBuffer();
		List<String> param = new ArrayList<String>();
		sql.append("UPDATE E_DATA_MP SET IS_VALID =?, DISABLE_DATE =SYSDATE where 1=1 ");
		param.add(status);
		if (zdjh != null && !"".equals(zdjh)){
		   sql.append(" AND ZDJH=?");
		   param.add(zdjh);
		}
		
		log.debug("updateTerminalMeasurePonitStatus sql:" + sql.toString()) ;
		if ((zdjh != null && !"".equals(zdjh))){ 		
		  getJdbcDAO().update(sql.toString(),param.toArray());		
		}
	}
	
	/**
	 * 更新终端运行状态
	 * @param ZDJH 终端局编号
	 * @param status 终端状态（-1拆除，006  待投，007 运行，008停运，009待检修）
	 */
	public void updateTerminalStatus(String zdjh, String status) {
		if("007".equals(status)){//当终端状态为运行的时候需要修改终端的投运日期RUN_DATE
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE R_TMNL_RUN SET RUN_DATE=sysdate, STATUS_CODE =? ");
			sql.append("	WHERE TMNL_ASSET_NO=?");
			
			log.debug("updateTerminalStatus:" + sql.toString()) ;
			getJdbcDAO().update(sql.toString(),new Object[]{status,zdjh});		
		}else if("-1".equals(status)){  //拆除的终端调用存储过程将之移到历史表中
			int nRet = this.removeZD(zdjh);
			log.debug("拆除终端返回结果为：" + nRet) ;
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE R_TMNL_RUN SET STATUS_CODE =? ");
			sql.append("	WHERE TMNL_ASSET_NO=?");
			
			log.debug("updateTerminalStatus:" + sql.toString()) ;
			getJdbcDAO().update(sql.toString(),new Object[]{status,zdjh});			
		}
	}

	/**
	 * 处理拆除的终端-调用存储过程
	 * @param zdjh
	 * @return
	 */
	private int removeZD(final String zdjh) {
		Object obj = getJdbcDAO().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) {
				CallableStatement cstmt = null;
				int nRet = -1;
				try {
					conn.setAutoCommit(false);
					cstmt = conn.prepareCall("{call pgk_cis_interface.removeZD(?)}");
					cstmt.setString(1, zdjh);
					cstmt.execute();
					nRet = 1;
				} catch (Exception e) {
					ExceptionHandle.handleUnCheckedException(e);
				} finally {
					if (cstmt != null) {
						try {
							cstmt.close();
						} catch (SQLException e) {
						}
					}
				}
				return String.valueOf(nRet);
			}
		});
		return Integer.valueOf(obj.toString()).intValue();
	}

	/**
	 * 更新表计状态
	 * @param JBJH 表计局编号
	 * @param status 表计状态（012 运行，015拆回待退）
	 */
	public void updateBJStatus(String bjjh, String status) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE D_METER SET CUR_STATUS_CODE =? ") ;
		sql.append("	WHERE ASSET_NO=?");
		
		log.debug("updateBJStatus:" + sql.toString()) ;
		try {
			getJdbcDAO().update(sql.toString(),new Object[]{status,bjjh});
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}

	/**
	 * 根据户号删除群组
	 * @param consNo 用户编号
	 */
	public void deleteQzByYh(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql = new StringBuffer();
		sql.append("DELETE FROM T_TMNL_GROUP_DETAIL ")
				.append(" WHERE CONS_NO = ?") ;

		log.debug("deleteQzByYh sql:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(),new Object[]{consNo}) ;
	}

	/**
	 * 插入webService:WS_TMNL_TASK_SR数据（终端运行管理）
	 * @param map XML 数据
	 */
	public void insertJkWsTmnlTaskSr(Map<String, Object> map) {
		//TODU
	}

	/**
	 * 根据终端ID获得终端的当前信息.
	 * @param list
	 */
	public List<?> getTerminalById(String terminalId) {
		StringBuffer sql = new StringBuffer();
		//增加ATTACH_METER_FLAG(是否附属于电能表)的查询结果，在建测量点时判断ATTACH_METER_FLAG是否是1（2合1终端）不需要建测量点1
		sql.append("SELECT * FROM R_TMNL_RUN WHERE TERMINAL_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list; 
	}
	
	/**
	 * 
	 * @param appNo
	 * @return
	 */
	public List<?> getCancelRTmnlTaskSr(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_TMNL_TASK_SR T WHERE T.APP_NO=? AND T.OLD_METER_ID IS NOT NULL AND NEW_METER_ID IS NULL ");
		List<?> list = this.getJdbcYXDAO().queryForList(sql.toString(),new Object[]{appNo});		
		return list; 
	}
	
	/**
	 * 取得中间库表信息
	 * @param appNo
	 * @return
	 */
	public List<?> getRtmnlTaskSr(String appNo) {
		List<?> list = null;
		try {
			String sql = " SELECT * FROM R_TMNL_TASK_SR T WHERE T.APP_NO=? ";
			list = this.getJdbcYXDAO().queryForList(sql, new Object[]{appNo});
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	/**
	 * 根据终端ID取得采集点对象数量
	 * @param list
	 */
	public int getRcollObjNumByTmnlId(String terminalId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_COLL_OBJ R,R_TMNL_RUN T WHERE T.CP_NO=R.CP_NO AND T.TERMINAL_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if (null != list) {
			return list.size();
		}
		return 0;
	}
	
	/**
	 * 根据终端ID取得采集点对象列表
	 * @param list
	 */
	public List<?> getRcollObjByTmnlId(String terminalId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_COLL_OBJ R,R_TMNL_RUN T WHERE T.CP_NO=R.CP_NO AND T.TERMINAL_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		return list;
	}
	
	/**
	 * 取得测量点数据主表
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getALLEDataMpByTmnl(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO=? AND T.IS_VALID='1' ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		return list;
	}
	
	/**
	 * 检查主表存在
	 * @param tmnlAssetNo
	 * @param k
	 * @return
	 */
	public int checkMainMeterIsHaved(String tmnlAssetNo, int k, int w) {
		int maxMpSn = 0;
		String sql = " SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO =? AND T.MP_SN=? ";
		List<?> list = this.getJdbcDAO().queryForList(sql, new Object[]{tmnlAssetNo, k});
		if (list != null && list.size() > 0) {
			String maxSql = " SELECT * FROM C_METER T WHERE T.TMNL_ASSET_NO=?";
			List<?> cmLst = this.getJdbcDAO().queryForList(maxSql, new Object[]{tmnlAssetNo});
			maxMpSn = cmLst.size()+1;
			if (maxMpSn == w) {
				maxMpSn = maxMpSn+1;
			}
			return maxMpSn;
		} else {
			return k;
		}
	}
	
	/**
	 * 更新终端总加组参数表
	 * @param tmnlId
	 */
	public void updateTtmnlTotalInfo(String terminalId){
		String cpNo = "";          //采集点编号
		String orgNo = "";         //单位编码
		String consNo = "";        //用户编号
		String meterId = "";       //表ID
		String mpSn = "1";         //测量点编号
		String pulseAttr = "1";    //脉冲属性 1:正向 2:反向
		String calcFlag = "0";     //0：加运算 1:减运算
		String totalNo = "1";      //总加组号
		String sql = " SELECT * FROM R_TMNL_RUN R WHERE R.TERMINAL_ID=?";
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if (null != list && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			cpNo = StringUtil.removeNull(map.get("CP_NO"));	
		}
		sql = " SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO=? AND T.MP_TYPE <>0";
		list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if (null != list && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			orgNo = StringUtil.removeNull(map.get("ORG_NO"));
			consNo = StringUtil.removeNull(map.get("CONS_NO"));
			meterId = StringUtil.removeNull(map.get("METER_ID"));
			
		}
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlsf = new StringBuffer();
		StringBuffer sqlValue = new StringBuffer();
		//using 
		sqlSelect.append(" SELECT '"+ cpNo +"' AS CP_NO, '"+ orgNo +"' AS ORG_NO, '"+ consNo +"' AS CONS_NO,'"+ meterId +"' AS METER_ID,'"+ mpSn +"' AS MP_SN,'"+terminalId+"' AS TMNL_ASSET_NO FROM DUAL ");
		//insert value
		sqlValue.append("'"+cpNo+"','"+orgNo+"','"+consNo+"','"+terminalId+"','"+meterId+"','"+mpSn+"','"+pulseAttr+"','"+calcFlag+"','"+totalNo+"'");
		sqlsf.append(" MERGE INTO T_TMNL_TOTAL_INFO A USING( " + sqlSelect.toString() + " ) B ON (A.TMNL_ASSET_NO = B.TMNL_ASSET_NO AND A.MP_SN=B.MP_SN) ");
		sqlsf.append(" WHEN MATCHED THEN ");
		sqlsf.append(" 	UPDATE SET  A.CP_NO=B.CP_NO,A.ORG_NO=B.ORG_NO,A.CONS_NO=B.CONS_NO,A.METER_ID=B.METER_ID ");
		sqlsf.append(" WHEN NOT MATCHED THEN ");
		sqlsf.append(" 	INSERT(CP_NO, ORG_NO, CONS_NO, TMNL_ASSET_NO, METER_ID, MP_SN, PULSE_ATTR, CALC_FLAG, TOTAL_NO) "); 
		sqlsf.append(" 	VALUES("+sqlValue.toString()+")");
		getJdbcDAO().update(sqlsf.toString());
	}
	
	
	/**
	 * 更新终端总加组主表
	 * @param terminalId
	 */
	public void updateEDataTotal(String terminalId){
		String areaCode = "";     //地区编码
		String orgNo = "";        //单位编码
		String areaNo = "";       //地区编号
		String consNo = "";       //用户编号
		String cisAssetNo = "";   //营销资产号
		String tmnlAddr = "";     //终端地址
		String totalNo = "1";     //总加组号

		String sql = " SELECT * FROM R_TMNL_RUN R WHERE R.TERMINAL_ID=?";
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if (null != list && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			cisAssetNo = StringUtil.removeNull(map.get("CIS_ASSET_NO"));	
		}
		sql = " SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO=?";
		list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		if (null != list && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			orgNo = StringUtil.removeNull(map.get("ORG_NO"));
			consNo = StringUtil.removeNull(map.get("CONS_NO"));
			areaCode = StringUtil.removeNull(map.get("AREA_CODE"));
			areaNo = StringUtil.removeNull(map.get("AREA_NO"));
			tmnlAddr = StringUtil.removeNull(map.get("TERMINAL_ADDR"));
		}
		StringBuffer sqlSelect = new StringBuffer();
		StringBuffer sqlsf = new StringBuffer();
		StringBuffer sqlValue = new StringBuffer();
		//using 
		sqlSelect.append(" SELECT '"+ areaCode +"' AS AREA_CODE, '"+ orgNo +"' AS ORG_NO, '"+ consNo +"' AS CONS_NO,'"+ areaNo +"' AS AREA_NO,'"+ cisAssetNo +"' AS CIS_TMNL_ASSET_NO,'"+terminalId+"' AS TMNL_ASSET_NO,'"+ tmnlAddr +"' AS TERMINAL_ADDR,'"+ totalNo +"' AS TOTAL_NO FROM DUAL ");
		//insert value
		sqlValue.append("'"+areaCode+"','"+orgNo+"','"+areaNo+"','"+consNo+"','"+terminalId+"','"+cisAssetNo+"','"+tmnlAddr+"','"+totalNo+"'");
		sqlsf.append(" MERGE INTO E_DATA_TOTAL A USING( " + sqlSelect.toString() + " ) B ON (A.TERMINAL_ADDR = B.TERMINAL_ADDR AND A.TOTAL_NO=B.TOTAL_NO) ");
		sqlsf.append(" WHEN MATCHED THEN ");
		sqlsf.append(" 	UPDATE SET  A.AREA_CODE=B.AREA_CODE,A.ORG_NO=B.ORG_NO,A.CONS_NO=B.CONS_NO,A.AREA_NO=B.AREA_NO,A.TMNL_ASSET_NO=B.TMNL_ASSET_NO,A.CIS_TMNL_ASSET_NO=B.CIS_TMNL_ASSET_NO ");
		sqlsf.append(" WHEN NOT MATCHED THEN ");
		sqlsf.append(" 	INSERT(ID, AREA_CODE, ORG_NO, AREA_NO, CONS_NO, TMNL_ASSET_NO, CIS_TMNL_ASSET_NO, TERMINAL_ADDR, TOTAL_NO) "); 
		sqlsf.append(" 	VALUES(S_E_DATA_TOTAL.NEXTVAL,"+sqlValue.toString()+") ");
		getJdbcDAO().update(sqlsf.toString());
	}
	
	/**
	 * 根据表计ID获得表计的当前信息.
	 * @param list
	 */
	public List<?> getAmeterById(String ameterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT METER_ID, ASSET_NO FROM C_METER WHERE METER_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{ameterId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list; 
	}

	/**
	 * 根据tmnlAssetNo，ameterAssetNo取测量点信息
	 * @param tmnlAssetNo 终端资产号
	 * @param ameterAssetNo 表计资产号
	 */
	public List<?> getMpSn(String tmnlAssetNo, String ameterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MP_SN FROM E_DATA_MP WHERE TMNL_ASSET_NO = ? AND METER_ID = ? ");//e_data_mp 加字段
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo, ameterId});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list; 
	}
	
	
	/**
	 * 根据终端资产号和测量点号更新测量点数据状态
	 * @param tmnlAssetNo
	 * @param mpSn 
	 */
	public void updateDataMpIsValid(String tmnlAssetNo, String mpSn) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE E_DATA_MP SET IS_VALID ='0' WHERE TMNL_ASSET_NO = ? AND MP_SN = ? ");//
		getJdbcDAO().update(sql.toString(),new Object[]{tmnlAssetNo, mpSn});
	}
	
	/**
	 * 根据终端资产号tmnlAssetNo取表计测量点数量
	 * @param tmnlId
	 */
	public List<?> getMpSnByTmnl(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM E_DATA_MP WHERE TMNL_ASSET_NO = ? AND IS_VALID = 1 AND MP_SN <> 0 ORDER BY MP_SN ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		return list;
	}
	
	/**
	 * 根据终端资产号tmnlAssetNo取表计测量点数量
	 * @param tmnlId
	 */
	public List<?> getALLMpSnByTmnl(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM E_DATA_MP WHERE TMNL_ASSET_NO = ? AND IS_VALID = 1 ORDER BY MP_SN ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		return list;
	}
	
	/**
	 * 根据终端资产号tmnlAssetNo取表计测量点数量(集抄)
	 * @param tmnlId
	 */
	public List<?> getFrmMpSnByTmnl(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM E_DATA_MP WHERE TMNL_ASSET_NO = ? AND IS_VALID = 1 AND MP_SN <> 0 AND CALC_MODE=0 ORDER BY MP_SN ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		return list;
	}
	
	/**
	 * 根据终端ID取得明细
	 * @param terminld
	 */
	public List<?> getTmnlTaskByTmnlId(String terminalId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.ORG_NO, T.TG_ID, G.TG_NAME FROM I_TMNL_TASK T,G_TG G WHERE T.TERMINAL_ID=? AND T.CONS_NO LIKE '%T%' AND G.TG_ID=T.TG_ID ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{terminalId});
		return list;
	}
	
	/**
	 * 取得考核单元
	 * @param ID
	 * @return
	 */
	public List<?> getChkUnit(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM G_CHKUNIT G WHERE G.OBJ_ID= ? ");
		List<?> list = this.getJdbcDAO().queryForList(sql.toString(),new Object[]{id});
		return list;
	}
	
	/**
	 * 插入考核关系表
	 * @param chkUnitId
	 * @param chkUnitTypeCode
	 * @param tgChkUnitId
	 */
	public void insertGChkUnitLineRelaForFrm(String chkUnitId, String chkUnitTypeCode, String tgChkUnitId) {
		StringBuffer sf = new StringBuffer();
		sf.append(" MERGE INTO G_CHKUNIT_LINK_RELA A USING ( SELECT '"+chkUnitId+"' AS CHKUNIT_ID, '"+chkUnitTypeCode+"' AS CHKUNIT_TYPE_CODE, '"+tgChkUnitId+"' AS LINK_OBJ_ID FROM DUAL ) B ");
		sf.append("       ON (A.CHKUNIT_ID=B.CHKUNIT_ID AND A.LINK_OBJ_ID=B.LINK_OBJ_ID) ");
		sf.append("    WHEN MATCHED THEN UPDATE SET A.IS_IN='0' ");
		sf.append("    WHEN NOT MATCHED THEN INSERT (CHKUNIT_ID, CHKUNIT_TYPE_CODE, LINK_OBJ_ID, LINK_OBJ_TYPE_CODE, IS_IN)     ");
		sf.append("                          VALUES (B.CHKUNIT_ID, B.CHKUNIT_TYPE_CODE, B.LINK_OBJ_ID, '0', '0')        ");
		this.getJdbcDAO().update(sf.toString());
		System.out.println(sf);
	}
	
	/**
	 * 检验一致性
	 * @param terminalId
	 * @param tgId
	 */
	public List<?> checkGIoMp(String terminalId, String tgId){
		StringBuffer sf = new StringBuffer();
		sf.append(" SELECT t.METER_ID, t.ID FROM E_DATA_MP t WHERE t.TMNL_ASSET_NO='"+terminalId+"' AND t.METER_ID IS NOT NULL ");
		sf.append("    MINUS SELECT g.METER_ID,g.DATA_ID FROM G_IO_MP g WHERE g.OBJ_ID='"+tgId+"' ");
		return this.getJdbcDAO().queryForList(sf.toString());
	}
	
	/**
	 * 检验一致性
	 * @param terminalId
	 * @param tgId
	 */
	public List<?> checkGIoMpExtra(String terminalId, String tgId){
		StringBuffer sf = new StringBuffer();
		sf.append(" SELECT g.METER_ID,g.DATA_ID FROM G_IO_MP g WHERE g.OBJ_ID='"+tgId+"' ");
		sf.append("    MINUS SELECT t.METER_ID, t.ID FROM E_DATA_MP t WHERE t.TMNL_ASSET_NO='"+terminalId+"' AND t.METER_ID IS NOT NULL ");
		return this.getJdbcDAO().queryForList(sf.toString());
	}
	
	/**
	 * 建立考核单元
	 * @param orgNo
	 * @param tgName
	 */
	public String insertChkUnit(String orgNo, String tgName, String tgId) {
		
		String sql = " SELECT S_G_CHKUNIT.NEXTVAL FROM DUAL ";
		List<?> seqLst = getJdbcDAO().queryForList(sql);
		Map<?, ?> map = (Map<?,?>) seqLst.get(0);
		String seq = StringUtil.removeNull(map.get("NEXTVAL"));
		
		StringBuffer sf = new StringBuffer();
		sf.append(" MERGE INTO G_CHKUNIT A USING (SELECT '"+tgName+"' AS CHKUNIT_NAME, '"+orgNo+"' AS ORG_NO, '"+tgId+"' AS OBJ_ID FROM DUAL) B ");
		sf.append("     ON (A.OBJ_ID=B.OBJ_ID )  ");
		sf.append(" WHEN MATCHED THEN UPDATE   ");
		sf.append(" 	SET A.ORG_NO=B.ORG_NO, A.CHKUNIT_NAME=B.CHKUNIT_NAME  ");
		sf.append(" WHEN NOT MATCHED THEN INSERT (CHKUNIT_ID, CHKUNIT_TYPE_CODE, CHKUNIT_NAME, ORG_NO, EXAM_FLAG, LINK_FLAG, RESP_EMP_NO, CHK_CYCLE, DATA_SRC, OBJ_ID)   ");
		sf.append(" 	VALUES ('"+seq+"', '02', B.CHKUNIT_NAME, B.ORG_NO, '1', '0', '2001', '11111', '02', B.OBJ_ID)   ");
		this.getJdbcDAO().update(sf.toString());
		return seq;
	}
	
	/**
	 * 根据终端ID取得用户计量点信息
	 * @return
	 */
	public List<?> getConsCmpByTmnlId(String terminalId) {
		StringBuffer sf = new StringBuffer();
		sf.append(" SELECT MP.TYPE_CODE, MP.WIRING_MODE,CONS.CONS_TYPE, CONS.CONTRACT_CAP, CONS.STATUS_CODE, CONS.CONS_ID ");
		sf.append(" 	FROM C_METER C INNER JOIN C_METER_MP_RELA RE ON C.METER_ID = RE.METER_ID ");
		sf.append("  			AND C.TMNL_ASSET_NO = ? ");
		sf.append("  			INNER JOIN C_MP MP ON MP.MP_ID = RE.MP_ID  ");
		sf.append("  			INNER JOIN C_CONS CONS ON CONS.CONS_NO = C.CONS_NO  ");
		List<?> list = getJdbcDAO().queryForList(sf.toString(), new Object[]{terminalId});
		return list;
	}
	
	/**
	 * 更新用户分类
	 * @param consId
	 * @param consSort
	 */
	public void updateCconsConsSort(String consId, String consSort) {
		String consSortUpdate = " UPDATE C_CONS C SET C.CONS_SORT=? WHERE C.CONS_ID=?";
		getJdbcDAO().update(consSortUpdate, new Object[]{consSort, consId});
	}
	
	/**
	 * 取得调试信息
	 * @param tmnlId
	 * @return
	 */
	public List<?> getITmnlTaskByTerminalId(String tmnlId){
		String sql = " SELECT * FROM I_TMNL_TASK T WHERE T.TERMINAL_ID=? ";
		return getJdbcDAO().queryForList(sql, new Object[]{tmnlId});
	}
	
	/**
	 * 根据表ID取得计量点
	 * @param meterId
	 * @return
	 */
	public List<?> getCMpByMeterId(String meterId){
		String sql = " SELECT C.* FROM C_MP C, C_METER_MP_RELA T WHERE T.METER_ID=? AND T.MP_ID=C.MP_ID ";
		return getJdbcDAO().queryForList(sql, new Object[]{meterId});
	}
	
	/**
	 * 更新虚拟用户分类
	 * @param terminalId
	 */
	public void updateCyberConsSort(String terminalId){
		String consSortUpdate = " UPDATE C_CONS C SET C.CONS_SORT = 'F' WHERE C.CONS_NO IN (SELECT T.CONS_NO FROM C_METER T  WHERE T.TMNL_ASSET_NO = ?  AND T.CONS_NO LIKE '%T%') ";
		getJdbcDAO().update(consSortUpdate, new Object[]{terminalId});
	}
	
	/**
	 * 更新虚拟用户分类
	 * @param terminalId
	 */
	public void updateCyberConsSortForGate(String terminalId){
		String consSortUpdate = " UPDATE C_CONS C SET C.CONS_SORT = 'G' WHERE C.CONS_NO IN (SELECT T.CONS_NO FROM C_METER T  WHERE T.TMNL_ASSET_NO = ?  AND T.CONS_NO LIKE '%S%') ";
		getJdbcDAO().update(consSortUpdate, new Object[]{terminalId});
	}
	
	/**
	 * 考核单元副表
	 * @param chkUnitId
	 * @param tgID
	 */
	public void insertChkUnitComp(String chkUnitId, String tgID) {
		StringBuffer sf = new StringBuffer();
		sf.append(" MERGE INTO G_CHKUNIT_COMP A USING (SELECT '"+chkUnitId+"' AS CHKUNIT_ID, '"+tgID+"' AS OBJ_ID FROM DUAL) B ");
		sf.append("     ON (A.CHKUNIT_ID=B.CHKUNIT_ID) ");
		sf.append(" WHEN MATCHED THEN UPDATE   ");
		sf.append(" 	SET A.OBJ_ID=B.OBJ_ID   ");
		sf.append(" WHEN NOT MATCHED THEN INSERT (COMP_ID, CHKUNIT_ID, OBJ_ID, OBJ_TYPE_CODE )   ");
		sf.append(" 	VALUES (S_G_CHKUNIT_COMP.NEXTVAL, B.CHKUNIT_ID, B.OBJ_ID, '02')   ");
		getJdbcDAO().update(sf.toString());
	}
	
	/**
	 * 流入流出线损
	 * @param terminalId
	 * @param chkUnitId
	 */
	public void insertGIoMpByTmnlId(String terminalId, String chkUnitId, String objId) {
		String cMeterSql = " SELECT T.MP_ID,T.METER_ID,E.ID, E.CALC_MODE FROM C_METER T, E_DATA_MP E WHERE T.TMNL_ASSET_NO=? AND T.METER_ID=E.METER_ID ";
		List<?> cmLst = this.getJdbcDAO().queryForList(cMeterSql, new Object[]{terminalId});
		
		if (cmLst != null && cmLst.size() > 0) {
			for (int i = 0; i < cmLst.size(); i++) {
				Map<?,?> cmMap = (Map<?,?>) cmLst.get(i);
				String mpId = StringUtil.removeNull(cmMap.get("MP_ID"));
				String meterId = StringUtil.removeNull(cmMap.get("METER_ID"));
				String dataId = StringUtil.removeNull(cmMap.get("ID"));
				String cmpSql = " SELECT * FROM C_MP T WHERE T.MP_ID=? ";
				List<?> cmpLst = this.getJdbcDAO().queryForList(cmpSql, new Object[]{mpId});
				
				String mpTypeCode = "";
				if (cmpLst != null && cmpLst.size() > 0) {
					mpTypeCode = StringUtil.removeNull(((Map<?,?>)cmpLst.get(0)).get("TYPE_CODE"));
				}
				String isIn = "0";
				if (!"1".equals(mpTypeCode)) {
					isIn = "1";
				}
				
				StringBuffer sf = new StringBuffer();
				sf.append(" MERGE INTO G_IO_MP A USING (SELECT '"+chkUnitId+"' AS CHKUNIT_ID, '"+objId+"' AS OBJ_ID, '"+mpId+"' AS MP_ID, '"+meterId+"' AS METER_ID, '"+dataId+"' AS DATA_ID, '"+isIn+"' AS IS_IN FROM DUAL) B ");
				sf.append("     ON (A.OBJ_ID=B.OBJ_ID AND A.METER_ID=B.METER_ID ) ");
				sf.append(" WHEN MATCHED THEN UPDATE   ");
				sf.append(" 	SET A.MP_ID=B.MP_ID, A.DATA_ID=B.DATA_ID   ");
				sf.append(" WHEN NOT MATCHED THEN INSERT (IO_MP_ID, CHKUNIT_ID, OBJ_ID, MP_ID, METER_ID, DATA_ID, IS_IN, USED_DATE, IS_VALID )   ");
				sf.append(" 	VALUES (S_G_IO_MP.NEXTVAL,B.CHKUNIT_ID, B.OBJ_ID, B.MP_ID, B.METER_ID, B.DATA_ID, B.IS_IN, SYSDATE, '1')   ");
				this.getJdbcDAO().update(sf.toString());
			}
		}
	}
	
	/**
	 * 加入缺失的流入流出单位
	 * @param tgChkUnitId
	 * @param tgId
	 * @param meterId
	 * @param dataId
	 */
	public void insertGIoMpPerMeter(String tgChkUnitId, String tgId, String meterId, String dataId) {
		String cMeterSql = " SELECT C.MP_ID FROM C_METER E, C_METER_MP_RELA C WHERE E.METER_ID=? AND  C.METER_ID=E.METER_ID ";
		List<?> cmLst = this.getJdbcDAO().queryForList(cMeterSql, new Object[]{meterId});
		if (cmLst != null && cmLst.size() > 0) {
			for (int i = 0; i< cmLst.size(); i++) {
				Map<?,?> cmMap = (Map<?,?>) cmLst.get(0);
				String mpId = StringUtil.removeNull(cmMap.get("MP_ID"));
				
				String cmpSql = " SELECT * FROM C_MP T WHERE T.MP_ID=? ";
				List<?> cmpLst = this.getJdbcDAO().queryForList(cmpSql, new Object[]{mpId});
				
				String mpTypeCode = "";
				if (cmpLst != null && cmpLst.size() > 0) {
					mpTypeCode = StringUtil.removeNull(((Map<?,?>)cmpLst.get(0)).get("TYPE_CODE"));
				}
				String isIn = "0";
				if (!"1".equals(mpTypeCode)) {
					isIn = "1";
				}
				
				StringBuffer sf = new StringBuffer();
				sf.append(" MERGE INTO G_IO_MP A USING (SELECT '"+tgChkUnitId+"' AS CHKUNIT_ID, '"+tgId+"' AS OBJ_ID, '"+mpId+"' AS MP_ID, '"+meterId+"' AS METER_ID, '"+dataId+"' AS DATA_ID, '"+isIn+"' AS IS_IN FROM DUAL) B ");
				sf.append("     ON (A.OBJ_ID=B.OBJ_ID AND A.METER_ID=B.METER_ID ) ");
				sf.append(" WHEN MATCHED THEN UPDATE   ");
				sf.append(" 	SET A.MP_ID=B.MP_ID, A.DATA_ID=B.DATA_ID   ");
				sf.append(" WHEN NOT MATCHED THEN INSERT (IO_MP_ID, CHKUNIT_ID, OBJ_ID, MP_ID, METER_ID, DATA_ID, IS_IN, USED_DATE, IS_VALID )   ");
				sf.append(" 	VALUES (S_G_IO_MP.NEXTVAL,B.CHKUNIT_ID, B.OBJ_ID, B.MP_ID, B.METER_ID, B.DATA_ID, B.IS_IN, SYSDATE, '1')   ");
				this.getJdbcDAO().update(sf.toString());
			}
		}
	}
	
	/**
	 * 更新多余的流入流出线损
	 * @param meterId
	 * @param dataId
	 */
	public void updateExtraGIoMp(String meterId, String dataId) {
		String sql = " UPDATE G_IO_MP T SET T.IS_VALID='0' WHERE T.METER_ID=? AND T.DATA_ID=? ";
		this.getJdbcDAO().update(sql, new Object[]{meterId, dataId});
	}
	
	
	/**
	 * 考核关系表
	 * @param chkUnitId
	 * @param meterId
	 * @param typeCode
	 */
	public void insertGChkUnitLineRelaForGate(String chkUnitId, String chkUnitType, String meterId, String typeCode) {
		String pqFlag = "";
		String isIn = "";
		String linkObjTypeCode = "1";
		if (!"1".equals(typeCode)) {
			pqFlag = "82";
			isIn = "1";
		} else {
			pqFlag = "81";
			isIn = "0";
		}
		
		StringBuffer sf = new StringBuffer();
		sf.append("    MERGE INTO G_CHKUNIT_LINK_RELA A USING (SELECT '"+chkUnitId+"' AS CHKUNIT_ID, '"+chkUnitType+"' AS CHKUNIT_TYPE_CODE, '"+meterId+"' AS LINK_OBJ_ID, '"+linkObjTypeCode+"' AS LINK_OBJ_TYPE_CODE, '"+pqFlag+"' AS PQ_FLAG, '"+isIn+"' AS IS_IN FROM DUAL ) B   ");
		sf.append("         ON (A.CHKUNIT_ID=B.CHKUNIT_ID AND A.LINK_OBJ_ID=B.LINK_OBJ_ID)  ");
		sf.append("      WHEN MATCHED THEN UPDATE SET A.CHKUNIT_TYPE_CODE=B.CHKUNIT_TYPE_CODE  ");
		sf.append("      WHEN NOT MATCHED THEN INSERT (CHKUNIT_ID, CHKUNIT_TYPE_CODE, LINK_OBJ_ID, LINK_OBJ_TYPE_CODE, PQ_FLAG, IS_IN)  ");
		sf.append("                            VALUES (B.CHKUNIT_ID, B.CHKUNIT_TYPE_CODE, B.LINK_OBJ_ID, B.LINK_OBJ_TYPE_CODE, B.PQ_FLAG, B.IS_IN)  ");
		this.getJdbcDAO().update(sf.toString());
	}
	
	/**
	 * 根据终端资产号tmnlAssetNo和测量点号取表计测量点数量
	 * @param tmnlId
	 */
	public List<?> getMpSnByTmnlSn(String tmnlAssetNo, String[] mpSnList) {
		StringBuffer sql = new StringBuffer();
		StringBuffer mpSn = new StringBuffer();
		for (int i = 0; i < mpSnList.length; i++) {
			mpSn.append("'"+mpSnList[i]+"',");
		}
		String param = mpSn.substring(0, mpSn.length()-1);
		
		sql.append("SELECT * FROM E_DATA_MP WHERE TMNL_ASSET_NO = '"+tmnlAssetNo+"' AND IS_VALID = 1 AND MP_SN <> 0 AND MP_SN IN ("+param+") ORDER BY MP_SN ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString());
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 取得集抄居民任务模板
	 * @return
	 */
	public String getiTmnlTaskSetUp() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM I_TMNL_TASK_SETUP T WHERE T.CONS_TYPE='5' ");
		List<?> list = getJdbcDAO().queryForList(sql.toString());
		if(list == null || list.size()== 0) {
			return null;
		}
		Map<?,?> map = (Map<?,?>) list.get(0);
		return StringUtil.removeNull(map.get("TEMPLATE_ID_LIST"));
	}

	/**
	 * 根据tmnlAssetNo取终端信息
	 * @param tmnlAssetNo 终端资产号
	 */
	public RTmnlRun getTmnlByAssetNo(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TERMINAL_ID, CIS_ASSET_NO, CP_NO, TMNL_ASSET_NO, TERMINAL_ADDR, ATTACH_METER_FLAG, TERMINAL_TYPE_CODE, PROTOCOL_CODE, SEND_UP_MODE FROM R_TMNL_RUN WHERE TMNL_ASSET_NO=?");
		
		try{
			List<RTmnlRun> list = new ArrayList<RTmnlRun>();
			list = getJdbcDAO().query(sql.toString(), new Object[]{tmnlAssetNo}, new rTmnlRunRowMapper());
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}catch(RuntimeException e){
			log.debug("根据tmnlAssetNo取终端信息出错！");
			throw e;
		}
		return null;
	}
	

	/**
	 * 根据终端ID取得运行电能表数量
	 * @param terminalId
	 * @return
	 */
	public List<?> getCMeterByTmnlId(String terminalId) {
		String sql = " 	SELECT * FROM C_METER T WHERE T.TMNL_ASSET_NO=? ";
		List<?> list = this.getJdbcDAO().queryForList(sql, new Object[]{terminalId});
		return list;
	}
	
	
	class rTmnlRunRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RTmnlRun rTmnlRun = new RTmnlRun();
			try {
				rTmnlRun.setTerminalId(rs.getString("TERMINAL_ID"));
				rTmnlRun.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
				rTmnlRun.setCpNo(rs.getString("CP_NO"));
				rTmnlRun.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				rTmnlRun.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				rTmnlRun.setAttachMeterFlag(rs.getString("ATTACH_METER_FLAG"));
				rTmnlRun.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				rTmnlRun.setTerminalTypeCode(rs.getString("TERMINAL_TYPE_CODE"));
				if("1".equals(rs.getString("SEND_UP_MODE"))){
					rTmnlRun.setSendUpMode(true);
				}else if("0".equals(rs.getString("SEND_UP_MODE"))){
					rTmnlRun.setSendUpMode(false);
				}
				return rTmnlRun;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 根据终端局号取得运行终端
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getRTmnlRunByAssetNo(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_TMNL_RUN T WHERE T.TMNL_ASSET_NO=? ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{tmnlAssetNo});
		return list;
	}
	
	/**
	 * 根据终端局号取得运行终端
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getYXRTmnlRunByAssetNo(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_TMNL_RUN T WHERE T.TERMINAL_ID=? ");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(), new Object[]{tmnlAssetNo});
		return list;
	}
	
	
	/**
	 * 根据ameterAssetNo取表计信息（20100508表e_data_mp等表中的ASSET_NO用METER_ID代替）
	 * @param ameterAssetNo 表计资产号
	 * 
	 */
	public CMeter getAmeterByAssetNo(String ameterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM C_METER WHERE METER_ID=?");
		
		try{
			List<CMeter> list = new ArrayList<CMeter>();
			list = getJdbcDAO().query(sql.toString(), new Object[]{ameterId}, new cMeterRowMapper());
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}catch(RuntimeException e){
			log.debug("根据ameterAssetNo取表计信息出错！");
			throw e;
		}
		return null;
	}
	
	class cMeterRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CMeter cMeter = new CMeter();
			try {
				cMeter.setMeterId(rs.getLong("METER_ID"));
				cMeter.setAssetNo(rs.getString("ASSET_NO"));
				cMeter.setMpId(rs.getLong("MP_ID"));
				cMeter.setOrgNo(rs.getString("ORG_NO"));
				cMeter.setConsNo(rs.getString("CONS_NO"));
				cMeter.setBaudrate(rs.getString("BAUDRATE"));
				cMeter.setCommNo(rs.getString("COMM_NO"));
				cMeter.setCommAddr1(rs.getString("COMM_ADDR1"));
				cMeter.setFmrAssetNo(rs.getString("FMR_ASSET_NO"));
				return cMeter;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 根据ameterAssetNo取表计档案（20100508表e_data_mp等表中的ASSET_NO用METER_ID代替）
	 * @param ameterAssetNo 表计资产号
	 */
	public DMeter getDmeterByAssetNo(String ameterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT METER_ID, ASSET_NO, SELF_FACTOR, VOLT_CODE, RATED_CURRENT, WIRING_MODE FROM D_METER WHERE METER_ID=?");
		
		try{
			List<DMeter> list = new ArrayList<DMeter>();
			list = getJdbcDAO().query(sql.toString(), new Object[]{ameterId}, new dMeterRowMapper());
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}catch(RuntimeException e){
			log.debug("根据ameterAssetNo取表计档案出错！");
			throw e;
		}
		return null;
	}
	
	/**
	 * 根据表ID取得用户容量和计量方式
	 * @param meterId
	 * @return
	 */
	public List<?> getCapMeasMode(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT C.MEAS_MODE, CONS.CONTRACT_CAP FROM C_METER_MP_RELA T, C_MP C, C_CONS CONS ");
		sql.append("      WHERE T.METER_ID = ? AND T.MP_ID = C.MP_ID AND CONS.CONS_ID = C.CONS_ID ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{meterId});
		return list;
	}
	
	class dMeterRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			DMeter dMeter = new DMeter();
			try {
				dMeter.setMeterId(rs.getLong("METER_ID"));
				dMeter.setAssetNo(rs.getString("ASSET_NO"));
				dMeter.setVoltCode(rs.getString("VOLT_CODE"));
				dMeter.setRatedCurrent(rs.getString("RATED_CURRENT"));
				dMeter.setWiringMode(rs.getString("WIRING_MODE"));
				dMeter.setWiringMode(rs.getString("SELF_FACTOR"));
				return dMeter;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 根据表ID取得自身倍率
	 * @param meterId 
	 */
	public String getSelfFactorMeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT METER_ID, ASSET_NO, SELF_FACTOR, VOLT_CODE, RATED_CURRENT, WIRING_MODE FROM D_METER WHERE METER_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{meterId});
		if (list != null && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			return StringUtil.removeNull(map.get("SELF_FACTOR")); 
		}
		return null;
	}
	
	/**
	 * 根据表ID取得自身倍率
	 * @param meterId 
	 */
	public List<?> getDmeterByMeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM D_METER WHERE METER_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{meterId});
		return list;
	}
	
	/**
	 * 根据表ID取得表上级采集器的地址
	 * @param ameterId
	 * @return
	 */
	public List<?> getfrmAddr(String ameterId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.COMM_ADDR,T.COLL_MODE FROM R_EXEC_OTHER_DEV T ");
		sql.append("     INNER JOIN C_METER C ON C.METER_ID =? ");
		sql.append("     AND C.FMR_ASSET_NO = T.COLLECTOR_ID ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{ameterId});
		return list;
	}
	
	/**
	 * 根据表ID取得示数位数
	 * @param meterId 
	 */
	public String getMeterDigitsByMeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT D.METER_DIGITS FROM D_METER D WHERE METER_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{meterId});
		if (list != null && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			return StringUtil.removeNull(map.get("METER_DIGITS")); 
		}
		return null;
	}

	/**
	 * 根据采集点编号取采集对象
	 * @param cpNo 采集点编号
	 */
	public List<?> getCollObjByCpNo(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COLL_OBJ_ID, METER_ID, CP_NO, COLL_PORT, CT_RATIO, PT_RATIO, T_FACTOR, METER_CONST, PORT_NO, PULSE_ATTR FROM R_COLL_OBJ WHERE CP_NO=? ORDER BY T_FACTOR DESC");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{cpNo});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list; 
	}
	
	/**
	 * 取得最大的测量点号
	 * @return
	 */
	public String getEDataMpMaxMpsn(String tmnlAssetNo) {
		String mpSn = "0";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(t.MP_SN) AS MP_SN FROM E_DATA_MP t WHERE t.IS_VALID=1 AND t.TMNL_ASSET_NO=? ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{tmnlAssetNo});
		if (list != null && list.size() > 0) {
			Map<?,?> map = (Map<?,?>)list.get(0);
			mpSn = StringUtil.removeNull(map.get("MP_SN"));
		}
		return mpSn;
	}

	/**
	 * 根据电能表标识取采集对象 
	 * @param meterId 电能表标识
	 */
	public List<?> getCollObjByMeterId(Long meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COLL_OBJ_ID, METER_ID, CP_NO, COLL_PORT, CT_RATIO, PT_RATIO, T_FACTOR, METER_CONST, PORT_NO, PULSE_ATTR FROM R_COLL_OBJ WHERE METER_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{meterId});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 取测量点数据主表数据
	 * @param tmnlAssetNo 终端资产编号
	 * @param ameterAssetNo 表计资产编号
	 * @param meterId 表ID
	 * @ 是否有效为1
	 */
	public List<?> getDataMpByParam(String tmnlAssetNo, String mpSn, String meterId) {
		StringBuffer sql = new StringBuffer();
		String param = null;
		if ("0".equals(mpSn)) {
			param = "MP_SN='"+mpSn+"'";
		} 
		
		else {
			param = "METER_ID='"+meterId+"'";
		}
		sql.append("SELECT * FROM E_DATA_MP WHERE TMNL_ASSET_NO = '"+tmnlAssetNo+"' AND "+param+" AND IS_VALID = 1");
		List<?> list = getJdbcDAO().queryForList(sql.toString());
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	
	/**
	 * 根据测量点号和终端资产号取得测量点数据主表
	 * @param tmnlAssetNo
	 * @param mpSn
	 * @return
	 */
	public List<?> getDataMpByTmnlNoMpSn(String tmnlAssetNo, String mpSn) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM E_DATA_MP WHERE TMNL_ASSET_NO = ? AND MP_SN = ? AND IS_VALID = 1");
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{tmnlAssetNo, mpSn});
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	
	/**
	 * 取测量点数据主表数据
	 * @param comm1 表通讯地址
	 * @param mpSn 测量点号
	 */
	public List<?> getDataMpByMpSnComm1(String mpSn, String comm1) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM E_DATA_MP WHERE MP_SN = ? AND COMM_ADDR like '%"+comm1+"%' AND IS_VALID = 1");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{mpSn});
		if(list == null || list.size()== 0) {
			return list;
		}		
		return list;
	}
	

	/**
	 * 插入测量点数据
	 * @param orgNo 供电单位编号
	 * @param 
	 */
	public void insertDataMp(String orgNo, String consNO, String tmnlAssetNo, String cisTmnlAssetNo, String terminalAddr, String aMeterId,
			String ameterAssetNo, String commAddr, String mpType, String collObj, String dataSrc, String mpSn,
			String pt, String ct, String selfFactor, String calcMode,
			String isValid, String areaCode) {
		StringBuffer sql = new StringBuffer();
		if ("".equals(aMeterId)) {
			sql.append("INSERT INTO E_DATA_MP(ID, ORG_NO,AREA_NO, CONS_NO, TMNL_ASSET_NO, CIS_TMNL_ASSET_NO, TERMINAL_ADDR, ASSET_NO, COMM_ADDR, MP_TYPE,COLL_OBJ_ID, DATA_SRC, MP_SN, PT, CT, SELF_FACTOR, CALC_MODE, IS_VALID, AREA_CODE) "); 
			sql.append(" VALUES(S_E_DATA_MP.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		} else {
			sql.append("INSERT INTO E_DATA_MP(ID, ORG_NO,AREA_NO, CONS_NO, TMNL_ASSET_NO, CIS_TMNL_ASSET_NO, TERMINAL_ADDR, METER_ID, ASSET_NO, COMM_ADDR, MP_TYPE,COLL_OBJ_ID, DATA_SRC, MP_SN, PT, CT, SELF_FACTOR, CALC_MODE, IS_VALID, AREA_CODE) "); 
			sql.append(" VALUES(S_E_DATA_MP.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		}
		List<String> param = new ArrayList<String>();
		param.add(orgNo);
		param.add(StringUtil.subStr(orgNo, 0, 7));
		param.add(consNO);
		param.add(tmnlAssetNo);
		param.add(cisTmnlAssetNo);
		param.add(terminalAddr);
		if (!"".equals(aMeterId)) {
			param.add(aMeterId);
		} 
		param.add(ameterAssetNo);
		param.add(commAddr);
		param.add(mpType);
		param.add(collObj);
		param.add(dataSrc);
		param.add(mpSn);
		param.add(pt);
		param.add(ct);
		param.add(selfFactor);
		param.add(calcMode);
		param.add(isValid);	
		param.add(areaCode);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}
	
	/**
	 * 更新测量点数据主表
	 */
	public void updateDateMp(String dataId, String orgNo, String consNo, String tmnlAssetNo, String cisTmnlAssetNo, String terminalAddr, String aMeterId,
			String ameterAssetNo, String commAddr, String mpType, String collObj, String dataSrc, String mpSn,
			String pt, String ct, String selfFactor, String calcMode,
			String isValid) {
		StringBuffer sql = new StringBuffer();
		//用户状态标识
		sql.append("UPDATE E_DATA_MP T SET T.ORG_NO= ?, T.AREA_NO = ?, T.CONS_NO =?, T.TMNL_ASSET_NO = ?, T.CIS_TMNL_ASSET_NO = ?, T.TERMINAL_ADDR = ?, T.ASSET_NO = ?, T.COMM_ADDR = ?, T.MP_TYPE = ?, T.COLL_OBJ_ID = ?, T.DATA_SRC = ?, T.MP_SN = ?, T.PT = ?, T.CT = ?, T.SELF_FACTOR = ?, T.CALC_MODE = ?, T.IS_VALID = ?  ");
		sql.append("	WHERE T.ID = ?");
		log.debug("updateDateMp:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{orgNo, StringUtil.subStr(orgNo, 0, 7), consNo, tmnlAssetNo, cisTmnlAssetNo, terminalAddr, ameterAssetNo, commAddr, mpType, collObj,dataSrc, Long.parseLong(mpSn), Long.parseLong("".equals(pt) ? "0" : pt),Long.parseLong("".equals(ct) ? "0" : ct),Long.parseLong("".equals(selfFactor) ? "0" : selfFactor),Long.parseLong("".equals(calcMode) ? "0" : calcMode),Long.parseLong("".equals(isValid) ? "0" : isValid),Long.parseLong(dataId)});
	}
	
	/**
	 * 更新测量点数据主表
	 */
	public void mergeEDataMp(List<Edatamp> list) {
		try {
			String[] mergeArray = new String[list.size()];
			
			for (int i = 0; i < list.size(); i++) {
				StringBuffer merge = new StringBuffer();
				StringBuffer using = new StringBuffer();
				
				Edatamp edataMp= list.get(i);
				//id
				String id = StringUtil.removeNull(edataMp.getId());
				//部门编号
				String orgNo = StringUtil.removeNull(edataMp.getOrgNo());
				//区域编码
				String areaCode = StringUtil.removeNull(edataMp.getAreaCode());
				//区域编号
				String areaNo = StringUtil.removeNull(edataMp.getAreaNo());
				//用户编号
				String consNo = StringUtil.removeNull(edataMp.getConsNo());
				//终端编号
				String tmnlAssetNo = StringUtil.removeNull(edataMp.getTmnlAssetNo());
				//营销资产号
				String cisTmnlAssetNo = StringUtil.removeNull(edataMp.getCisTmnlAssetNo());
				//终端地址
				String terminalAddr = StringUtil.removeNull(edataMp.getTerminalAddr());
				//表ID
				String meterId = StringUtil.removeNull(edataMp.getMeterId());
				//表资产号
				String assetNo = StringUtil.removeNull(edataMp.getAssetNo());
				//表地址
				String commAddr = StringUtil.removeNull(edataMp.getCommAddr());
				//类型
				String mpType = StringUtil.removeNull(edataMp.getMpType());
				//objId
				String collObjId = StringUtil.removeNull(edataMp.getCollObjId());
				//采样模式
				String dataSrc = StringUtil.removeNull(edataMp.getDataSrc());
				//测量点号
				String mpSn = StringUtil.removeNull(edataMp.getMpSn());
				//pt
				String pt = StringUtil.removeNull(edataMp.getPt());
				//ct
				String ct = StringUtil.removeNull(edataMp.getCt());
				//自身倍率
				String selfFactor = StringUtil.removeNull(edataMp.getSelfFactor());
				//是否主表
				String calcMode = StringUtil.removeNull(edataMp.getCalcMode());
				//是否有效
				String isValid = StringUtil.removeNull(edataMp.getIsValid());

				//using
				using.append(" SELECT '"+id+"' AS ID, '"+areaCode+"' AS AREA_CODE, '"+orgNo+"' AS ORG_NO,'"+areaNo+"' AS AREA_NO,'"+consNo+"' AS CONS_NO,'"+tmnlAssetNo+"' AS TMNL_ASSET_NO,'"+cisTmnlAssetNo+"' AS CIS_TMNL_ASSET_NO, ");
				using.append(" 	      '"+terminalAddr+"' AS TERMINAL_ADDR,'"+meterId+"' AS METER_ID,'"+assetNo+"' AS ASSET_NO,'"+commAddr+"' AS COMM_ADDR,'"+mpType+"' AS MP_TYPE,'"+collObjId+"' AS COLL_OBJ_ID,'"+dataSrc+"' AS DATA_SRC,'"+mpSn+"' AS MP_SN, ");
				using.append(" 	      '"+pt+"' AS PT, '"+ct+"' AS CT,'"+selfFactor+"' AS SELF_FACTOR,'"+calcMode+"' AS CALC_MODE, '"+isValid+"' AS IS_VALID FROM DUAL ");
				
				//merge
				merge.append("  MERGE INTO E_DATA_MP A USING ("+using.toString()+")B ");
				merge.append("        ON (A.TMNL_ASSET_NO=B.TMNL_ASSET_NO AND A.METER_ID=B.METER_ID) ");
				merge.append("  WHEN MATCHED THEN 	");
				merge.append("  	  UPDATE SET A.ID=B.ID, A.AREA_CODE=B.AREA_CODE, ");
				merge.append("  	             A.ORG_NO=B.ORG_NO, A.AREA_NO=B.AREA_NO, ");
				merge.append("  	             A.CONS_NO=B.CONS_NO,  ");
				merge.append("  	             A.CIS_TMNL_ASSET_NO=B.CIS_TMNL_ASSET_NO, A.TERMINAL_ADDR=B.TERMINAL_ADDR, ");
				merge.append("  	             A.ASSET_NO=B.ASSET_NO, A.COMM_ADDR=B.COMM_ADDR,");
				merge.append("  	             A.MP_TYPE=B.MP_TYPE, A.COLL_OBJ_ID=B.COLL_OBJ_ID, ");
				merge.append("  	             A.DATA_SRC=B.DATA_SRC, A.MP_SN=B.MP_SN, ");
				merge.append("  	             A.PT=B.PT, A.CT=B.CT, ");
				merge.append("  	             A.SELF_FACTOR=B.SELF_FACTOR, A.CALC_MODE=B.CALC_MODE, ");
				merge.append("  	             A.IS_VALID=B.IS_VALID ");
				merge.append("  WHEN NOT MATCHED THEN 	");
				merge.append("        INSERT (ID, AREA_CODE, ORG_NO, AREA_NO, CONS_NO, TMNL_ASSET_NO, CIS_TMNL_ASSET_NO, 	");
				merge.append("                TERMINAL_ADDR, METER_ID, ASSET_NO,COMM_ADDR, MP_TYPE, COLL_OBJ_ID, DATA_SRC, MP_SN,	");
				merge.append("                PT, CT, SELF_FACTOR, CALC_MODE, IS_VALID)	");
				merge.append("        VALUES (S_E_DATA_MP.NEXTVAL, B.AREA_CODE, B.ORG_NO, B.AREA_NO, B.CONS_NO, B.TMNL_ASSET_NO, B.CIS_TMNL_ASSET_NO, 	");
				merge.append("                B.TERMINAL_ADDR, B.METER_ID, B.ASSET_NO,B.COMM_ADDR, B.MP_TYPE, B.COLL_OBJ_ID, B.DATA_SRC, B.MP_SN,	");
				merge.append("                B.PT, B.CT, B.SELF_FACTOR, B.CALC_MODE, B.IS_VALID)	");
				mergeArray[i] = merge.toString();
			}
			this.getJdbcDAO().batchUpdate(mergeArray);
			//batchUpdate
//			this.updateJdbc(mergeArray);
			System.out.println("																							");
			System.out.println("***********************************测量点数据批处理完成**************************************");
			System.out.println("																							");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 更新终端下发参数表
	 * @param tTmnlParamLst
	 */
	public void mergeTTmnlParam(List<TtmnlParam> tTmnlParamLst) {

		try {
			String[] mergeArray = new String[tTmnlParamLst.size()];
			for (int i = 0; i < tTmnlParamLst.size(); i++) {
				StringBuffer merge = new StringBuffer();
				StringBuffer using = new StringBuffer();
			
				TtmnlParam tmnlParam = tTmnlParamLst.get(i);
				String tmnlAssetNo = StringUtil.removeNull(tmnlParam.getTmnlAssetNo());
				String protItemNo = StringUtil.removeNull(tmnlParam.getProtItemNo());
				String currentValue = StringUtil.removeNull(tmnlParam.getCurrentValue());
				String historyValue = StringUtil.removeNull(tmnlParam.getHistoryValue());
				String blockSn = StringUtil.removeNull(tmnlParam.getBlockSn());
				String innerBlockSn = StringUtil.removeNull(tmnlParam.getInnerBlockSn());
				String statusCode = StringUtil.removeNull(tmnlParam.getStatusCode());
				String isValid = StringUtil.removeNull(tmnlParam.getIsValid());
				
				//using
				using.append(" SELECT '"+tmnlAssetNo+"' AS TMNL_ASSET_NO,'"+protItemNo+"' AS PROT_ITEM_NO,'"+currentValue+"' AS CURRENT_VALUE,'"+historyValue+"' AS HISTORY_VALUE,'"+blockSn+"' AS BLOCK_SN, '"+innerBlockSn+"' AS INNER_BLOCK_SN,'"+statusCode+"' AS STATUS_CODE,'"+isValid+"' AS IS_VALID FROM DUAL ");
				
				//merge
				merge.append("  MERGE INTO T_TMNL_PARAM A USING ("+using.toString()+")B ");
				merge.append("        ON (A.TMNL_ASSET_NO=B.TMNL_ASSET_NO AND A.PROT_ITEM_NO=B.PROT_ITEM_NO AND A.BLOCK_SN=B.BLOCK_SN AND A.INNER_BLOCK_SN=B.INNER_BLOCK_SN) ");
				merge.append("  WHEN MATCHED THEN 	");
				merge.append("  	  UPDATE SET A.CURRENT_VALUE=B.CURRENT_VALUE, A.HISTORY_VALUE=B.HISTORY_VALUE,  ");
				merge.append("  	             A.STATUS_CODE=B.STATUS_CODE, A.IS_VALID=B.IS_VALID ");
				merge.append("  WHEN NOT MATCHED THEN 	");
				merge.append("        INSERT (TMNL_ASSET_NO, PROT_ITEM_NO, CURRENT_VALUE, HISTORY_VALUE, BLOCK_SN, INNER_BLOCK_SN, STATUS_CODE,IS_VALID) 	");
				merge.append("        VALUES (B.TMNL_ASSET_NO, B.PROT_ITEM_NO, B.CURRENT_VALUE, B.HISTORY_VALUE, B.BLOCK_SN, B.INNER_BLOCK_SN, B.STATUS_CODE,B.IS_VALID)	");
				mergeArray[i] = merge.toString();
			}
			//batchUpdate
			this.getJdbcDAO().batchUpdate(mergeArray);
			System.out.println("																					 ");
			System.out.println("***********************************F10批处理完成**************************************");
			System.out.println("																					  ");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 取测量点参数
	 * @param dataId 测量点id
	 * @param protItemNo 规约项数据编码
	 */
	public List<?> getTmnlMpParamByIdAndProtNo(String dataId, String protItemNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_TMNL_MP_PARAM WHERE ID = ? AND PROT_ITEM_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{dataId, protItemNo});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 插入终端测量点参数
	 * @param dataId 测量点id
	 * @param protItemNo 规约项数据编码
	 * @param currentValue 参数当前值
	 */
	public void insertTmnlMpParam(String dataId, String protItemNo,
			String currentValue, String blockSn, String innerBlockSn, String statusCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO T_TMNL_MP_PARAM (ID, PROT_ITEM_NO, CURRENT_VALUE, BLOCK_SN, INNER_BLOCK_SN, STATUS_CODE, SAVE_TIME) "); 
		sql.append(" VALUES(?,?,?,?,?,?, SYSDATE)");
		List<String> param = new ArrayList<String>();
		param.add(dataId);
		param.add(protItemNo);
		param.add(currentValue);
		param.add(blockSn);
		param.add(innerBlockSn);
		param.add(statusCode);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 更新终端测量点参数
	 * @param dataId 测量点id
	 * @param protItemNo 规约项数据编码
	 * @param currentValue 参数当前值
	 * @param historyValue 参数历史值
	 * @param lastSendTime 上次下发时间
	 */
	public void updateTmnlMpParam(String dataId, String protItemNo,
			String currentValue, String historyValue, String blockSn, String innerBlockSn, String statusCode, Date lastSendTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_MP_PARAM SET CURRENT_VALUE = ?, HISTORY_VALUE =?, BLOCK_SN = ?, INNER_BLOCK_SN = ?, STATUS_CODE = ?, SAVE_TIME = SYSDATE, LAST_SEND_TIME = ?");
		sql.append("	WHERE ID = ? AND PROT_ITEM_NO = ?");
		
		getJdbcDAO().update(sql.toString(),new Object[]{currentValue, historyValue, blockSn, innerBlockSn, statusCode, lastSendTime, dataId, protItemNo});
	}

	/**
	 * 根据任务模板ID取透明规约
	 * @param templateId 任务模板ID
	 */
	public List<BClearProtocol> findAllByTemplateId(Long templateId){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT B.PROTOCOL_NO, B.PROTOCOL_NAME, B.DATA_TYPE\n")
		.append("  FROM B_CLEAR_PROTOCOL B\n")
		.append("  LEFT JOIN T_TASK_TEMPLATE_DATA T ON T.TEMPLATE_ID = ?\n")
		.append(" WHERE T.PROTOCOL_NO = B.PROTOCOL_NO\n")
		.append(" ORDER BY T.PROTOCOL_NO");
		try{
			List<BClearProtocol> list = new ArrayList<BClearProtocol>();
			list = getJdbcDAO().query(sb.toString(), new Object[]{templateId}, new BClearProtocolRowMapper());
			return list;
		}catch(RuntimeException e){
			log.debug("根据模板ID查询关联的透明规约出错！");
			throw e;
		}
	}
	
	/**
	 * 内部类　BClearProtocolRowMapper
	 * @author yut
	 * @describe BClearProtocol　RowMapper实现
	 */
	class BClearProtocolRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BClearProtocol bClearProtocol = new BClearProtocol();
			try {
				bClearProtocol.setDataType(rs.getByte("data_type"));
				bClearProtocol.setProtocolName(rs.getString("protocol_name"));
				bClearProtocol.setProtocolNo(rs.getString("protocol_no"));
				return bClearProtocol;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 根据终端资产编号和任务模板id确定任务是否存在
	 * @param tmnlAssetNo 终端资产编号
	 * @param templateId 任务模板id
	 * @return
	 * @throws DBAccessException
	 */
	public TTmnlTask findTaskByNoId(String tmnlAssetNo,Long templateId){
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.TMNL_ASSET_NO, T.TEMPLATE_ID, T.TASK_PROPERTY, T.MP_SN, T.TASK_NO\n")
		.append("  FROM T_TMNL_TASK T\n")
		.append(" WHERE T.TMNL_ASSET_NO = ?\n")
		.append("   AND T.TEMPLATE_ID = ?");
		try{
			List<TTmnlTask> list = new ArrayList<TTmnlTask>();
			list = getJdbcDAO().query(sb.toString(), new Object[]{tmnlAssetNo,templateId}, new tTmnlTaskRowMapper());
			if(list!=null&&list.size()>0){
				return list.get(0);
			}
		}catch(RuntimeException e){
			log.debug("根据终端资产编号和任务模板id确定任务是否存在出错！");
			throw e;
		}
		return null;
	}
	
	class tTmnlTaskRowMapper implements RowMapper {
		@Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
			TTmnlTask tTmnlTask = new TTmnlTask();
			TTmnlTaskId tTmnlTaskId = new TTmnlTaskId();
		 try { 
			 tTmnlTaskId.setTaskNo(rs.getShort("TASK_NO"));
			 tTmnlTaskId.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
			 tTmnlTask.setId(tTmnlTaskId);
			 tTmnlTask.setTemplateId(rs.getLong("TEMPLATE_ID"));
			 tTmnlTask.setMpSn(rs.getString("MP_SN"));
			 tTmnlTask.setTaskProperty(rs.getString("TASK_PROPERTY"));
			return tTmnlTask;
		 }catch (Exception e) {
			 return null;
		 	}
		}
	}

	/**
	 * 根据规约项数据编码取块内序号
	 * @param protItemNo 规约项数据编码
	 * @return InnerBlockSn 块内序号
	 */
	public List<?> getProtocolItemByProtItemNo(String protItemNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT PROT_ITEM_SN FROM B_COMM_PROTOCOL_ITEM WHERE PROT_ITEM_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{protItemNo});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 根据终端资产编号 和 块序号判断所属的规约项编码是否存在
	 * @param tmnlAssetNo 终端资产号
	 * @return blockSn 块序号
	 */
	public List<?> getTmnlParamByTmnlAssetNoAndBlockSn(String tmnlAssetNo,
			String blockSn) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_TMNL_PARAM WHERE TMNL_ASSET_NO = ? AND BLOCK_SN = ? ORDER BY INNER_BLOCK_SN ASC");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo, blockSn});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据终端资产编号取得F10终端参数
	 * @param tmnlAssetNo 终端资产号
	 * @return blockSn 块序号
	 */
	public List<?> getF10TmnlParamByTmnlAssetNo(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM T_TMNL_PARAM T WHERE TMNL_ASSET_NO = ? AND T.PROT_ITEM_NO LIKE '%040A00%' AND T.PROT_ITEM_NO <> '5040A001' ORDER BY TO_NUMBER(T.BLOCK_SN), t.INNER_BLOCK_SN ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 根据终端资产编号 和 块序号判断所属的规约项编码是否存在
	 * @param tmnlAssetNo 终端资产号
	 * @param blockSn 块序号
	 * @param isValid 是否有效
	 * @param statusCode 
	 */
	public void updateTmnlParam(String tmnlAssetNo, String blockSn,
			String isValid) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_PARAM SET IS_VALID = ?");
		sql.append("	WHERE TMNL_ASSET_NO = ? AND BLOCK_SN = ?");
		
		List<String> param = new ArrayList<String>();
		param.add(isValid);
		param.add(tmnlAssetNo);
		param.add(blockSn);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 取终端参数
	 * @param tmnlAssetNo 终端资产号
	 * @param protItemNo 规约项数据编码
	 * @param blockSn 块序号
	 * @param innerBlockSn 块内序号
	 */
	public List<?> getTmnlParamByKey(String tmnlAssetNo, String protItemNo,
			String blockSn, String innerBlockSn) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_TMNL_PARAM WHERE TMNL_ASSET_NO = ? AND PROT_ITEM_NO = ? AND BLOCK_SN = ? AND INNER_BLOCK_SN = ?");
		
		List<String> param = new ArrayList<String>();
		param.add(tmnlAssetNo);
		param.add(protItemNo);
		param.add(blockSn);
		param.add(innerBlockSn);
		List<?> list = getJdbcDAO().queryForList(sql.toString(),param.toArray());
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 插入终端参数
	 * @param statusCode 
	 */
	public void insertTmnlParam(String tmnlAssetNo, String protItemNo,
			String currentValue, String blockSn, String innerBlockSn, String statusCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO T_TMNL_PARAM (TMNL_ASSET_NO, PROT_ITEM_NO, CURRENT_VALUE, BLOCK_SN, INNER_BLOCK_SN, STATUS_CODE, SAVE_TIME, SUCCESS_TIME) "); 
		sql.append(" VALUES(?,?,?,?,?,?,SYSDATE,SYSDATE)");
		List<String> param = new ArrayList<String>();
		param.add(tmnlAssetNo);
		param.add(protItemNo);
		param.add(currentValue);
		param.add(blockSn);
		param.add(innerBlockSn);
		param.add(statusCode);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	
	
	/**
	 * 更新终端参数
	 */
	public void updateTmnlParam(String tmnlAssetNo, String protItemNo,
			String currentValue, String historyValue, String blockSn,
			String innerBlockSn, String statusCode, String isValid, Date lastSendTime) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_PARAM SET CURRENT_VALUE = ?, HISTORY_VALUE = ?, IS_VALID = ?, STATUS_CODE = ?, SUCCESS_TIME = SYSDATE");
		sql.append("	WHERE TMNL_ASSET_NO = ? AND PROT_ITEM_NO = ? AND BLOCK_SN = ? AND INNER_BLOCK_SN = ?");
		List<String> param = new ArrayList<String>();
		param.add(currentValue);
		param.add(historyValue);
		param.add(isValid);
		param.add(statusCode);
		param.add(tmnlAssetNo);
		param.add(protItemNo);
		param.add(blockSn);
		param.add(innerBlockSn);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}
	
	/**
	 * 取得取得终端事件分类
	 * @param protocol
	 * @return
	 */
	public List<?> getLevel(String protocol) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.EVENT_NO,DECODE(T.EVENT_LEVEL, '01', 0, '02', 0, 1) AS EVENT_LEVEL,T.IS_REC ");
		sql.append("    FROM B_PROTOCOL_EVENT T WHERE T.PROTOCOL_CODE = ? ORDER BY T.EVENT_NO ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{protocol});
		return list;
	}

	/**
	 * 插入调试明细
	 * @param appNo 申请单号
	 * @param terminalId 终端编号
	 * @param ameterId 电能表标识
	 * @param debugStart 调试进度
	 */
	public void insertTmnlDebug(String appNo, String terminalId,
			String ameterId, String debugProgress) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO I_TMNL_DEBUG (APP_NO, TERMINAL_ID, METER_ID, DEBUG_PROGRESS, DEBUG_TIME) "); 
		sql.append(" VALUES(?,?,?,?,SYSDATE)");
		List<String> param = new ArrayList<String>();
		param.add(appNo);
		param.add(terminalId);
		param.add(ameterId);
		param.add(debugProgress);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}
	
	public void insertTmnlDebug(String appNo, String terminalId,
			String ameterId, String debugProgress, String failCause) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO I_TMNL_DEBUG (APP_NO, TERMINAL_ID, METER_ID, DEBUG_PROGRESS, FAIL_CAUSE, DEBUG_TIME) "); 
		sql.append(" VALUES(?,?,?,?,?,SYSDATE)");
		List<String> param = new ArrayList<String>();
		param.add(appNo);
		param.add(terminalId);
		param.add(ameterId);
		param.add(debugProgress);
		param.add(failCause);
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 插入日志
	 * @param logId 
	 * @param sysNo 系统编号
	 * @param interfaceType 接口类型
	 * @param interfaceName 接口名称
	 * @param interfaceContent 接口内容
	 * @param respId 写入的logId
	 */
	public void writeLog(String logId, String sysNo, String interfaceType,
			String interfaceName, String interfaceContent, String respId) {
		
		try {
			StringBuffer sql = null;
			List<String> param = new ArrayList<String>();
			//为空 调试开始log
			if ("".equals(respId)) {
				sql = new StringBuffer();
				sql.append("INSERT INTO I_INTERFACE_LOG (LOG_ID, SYS_NO, INTERFACE_TYPE, INTERFACE_NAME, INTERFACE_CONTENT, ERR_NO, RESP_ID, OP_TIME) "); 
				sql.append(" VALUES(?,?,?,?,?,?,?,SYSDATE)");
				param.add(logId);
				param.add(sysNo);
				param.add(interfaceType);
				param.add(interfaceName);
				param.add(interfaceContent);
				param.add("0");
				param.add("");	
			} else {//调试结束log
				sql = new StringBuffer();
				sql.append("INSERT INTO I_INTERFACE_LOG (LOG_ID, SYS_NO, INTERFACE_TYPE, INTERFACE_NAME, INTERFACE_CONTENT, ERR_NO, RESP_ID, OP_TIME) "); 
				sql.append(" VALUES(S_I_INTERFACE_LOG.NEXTVAL,?,?,?,?,?,?,SYSDATE)");
				param.add(sysNo);
				param.add(interfaceType);
				param.add(interfaceName);
				param.add(interfaceContent);
				param.add("0");
				param.add(respId);	
			}
			log.debug("writeLog:" + sql.toString()) ;
			getJdbcDAO().update(sql.toString(), param.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 更新运行电能表c_meter中的，注册序号REG_SN
	 * @param ameterAssetNo 
	 * @param mpSn 
	 */
	public void updateMeterRegSn(String ameterId, String mpSn) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE C_METER SET REG_SN = ? WHERE METER_ID = ?");
		List<String> param = new ArrayList<String>();
		param.add(mpSn);
		param.add(ameterId);
		
		log.debug("updateMeterRegSn:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(),param.toArray());
	}
	
	/**
	 * 更新终端的接线方式
	 * @param tmnlAssetNo
	 */
	public void updateWiringMode(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE R_TMNL_RUN T SET T.WIRING_MODE=(SELECT D.WIRING_MODE FROM D_METER D WHERE D.METER_ID IN ");
		sql.append("       (SELECT E.METER_ID FROM E_DATA_MP E WHERE E.TMNL_ASSET_NO =? AND E.MP_SN = '1' AND E.IS_VALID='1')) ");
		List<String> param = new ArrayList<String>();
		param.add(tmnlAssetNo);
		log.debug("updateWiringMode:" + sql.toString()) ;
		this.getJdbcDAO().update(sql.toString(),param.toArray());
	}
	
	/**
	 * 根据终端ID取得运行电能表ID
	 * @param tmnlId
	 */
	public List<?> getCMeterIdBytmnlId(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM C_METER C WHERE C.TMNL_ASSET_NO=? ");
		List<String> param = new ArrayList<String>();
		param.add(tmnlId);
		log.debug("getCMeterIdBytmnlId:" + sql.toString()) ;
		List<?> list = getJdbcDAO().queryForList(sql.toString(),param.toArray());	
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	///////////////////////////////////////////////////销终端//////////////////////////////////////////////////////////
	
	/**
	 * 删除运行终端接线图
	 * @param tmnlId 终端编号
	 */
	public void deleteTerminalRunGraph(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_TMNL_RUN_GRAPH   ");
		sql.append("	WHERE TERMINAL_ID=?");
		log.debug("deleteTerminalRunGraph:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});		
	}
	
	/**
	 * 删除SIM卡费用信息
	 * @param tmnlId 终端编号
	 */
	public void deleteSimCharge(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_SIM_CHARGE    ");
		sql.append("	WHERE TERMINAL_ID=?");
		log.debug("deleteSimCharge:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});		
	}
	
	/**
	 * 删除终端检修信息 
	 * @param tmnlId 终端编号
	 */
	public void deleteTmnlMaint(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_TMNL_MAINT    ");
		sql.append("	WHERE TERMINAL_ID=?");
		log.debug("deleteTmnlMaint:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});		
	}
	
	/**
	 * 删除终端调试信息 
	 * @param tmnlId 终端编号
	 */
	public void deleteTmnlDebug(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_TMNL_DEBUG   ");
		sql.append("	WHERE TERMINAL_ID=?");
		log.debug("deleteTmnlDebug:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});		
	}
	
	/**
	 * 删除采集点现场巡视
	 * @param tmnlId 终端编号
	 */
	public void deleteRPatrol(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_PATROL  ");
		sql.append("	WHERE TERMINAL_ID=?");
		log.debug("deleteRPatrol:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});		
	}
	
	/**
	 * 删除运行终端
	 * @param tmnlId 终端编号
	 */
	public void deleteTerminalRun(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_TMNL_RUN ");
		sql.append("	WHERE TERMINAL_ID=?");
		log.debug("deleteTerminalRun:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});		
	}
	
	/**
	 * 删除 运行终端
	 * @param tmnlId 终端编号
	 */
	public void delTerminalRun(String tmnlId) {
//		deleteTerminalRunGraph(tmnlId);
		deleteSimCharge(tmnlId);
		deleteTmnlMaint(tmnlId);
		deleteTmnlDebug(tmnlId);
		deleteRPatrol(tmnlId);
		deleteTerminalRun(tmnlId);
	}
		
	/**
	 * 更新测量点数据主表( 是否有效)
	 * @param tmnlAssetNo 终端资产编号
	 */
	public void updateDataMp(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE E_DATA_MP SET IS_VALID ='0' ");//0:无效 1：有效
		sql.append("	WHERE TMNL_ASSET_NO =?");
		log.debug("deleteDataMp:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlAssetNo});	
	}
	
	/**
	 * 更新测量点数据主表( 是否有效)
	 * @param meterId 电能表ID
	 */
	public void updateDataMpBymeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE E_DATA_MP SET IS_VALID ='0' ");//0:无效 1：有效
		sql.append("	WHERE METER_ID =?");
		log.debug("updateDataMpBymeterId:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 更新 用电客户 (用电客户状态)
	 * @param consNo 终端资产编号
	 */
	public void updateCons(String consNo) {
		StringBuffer sql = new StringBuffer();
		//用户状态标识
		sql.append("UPDATE C_CONS SET STATUS_CODE='9', CANCEL_DATE=sysdate ");
		sql.append("	WHERE CONS_NO =?");
		log.debug("updateCons:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{consNo});	
	}
	
	/**
	 * 更新终端资产 状态（负控设备信息）
	 * @param tmnlId 终端编号
	 */
	public void updateEquip(String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE D_LC_EQUIP T SET T.CUR_STATUS_CODE='04', T.RMV_DATE=sysdate  ");
		sql.append("	WHERE  T.ID=?  ");
		log.debug("updateEquip:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlId});	
	}
	
	/**
	 * 根据终端ID删除终端用户联系表
	 * @param oldTmnlId
	 */
	public void delRConsTmnlRelaByConsNoByTmnlId(String oldTmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CONS_TMNL_RELA A ");
		sql.append("	WHERE A.TMNL_ASSET_NO=?  ");
		log.debug("delRConsTmnlRelaByConsNoByTmnlId:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{oldTmnlId});	
	}
	
	/**
	 * 更新电能表信息
	 * @param newTmnlId
	 */
	public void updateCMeter(String oldTmnlId, String newTmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE C_METER T SET T.TMNL_ASSET_NO=? WHERE T.TMNL_ASSET_NO=? ");
		log.debug("updateCMeter:" + sql.toString());
		getJdbcDAO().update(sql.toString(), new Object[]{newTmnlId, oldTmnlId});
	}
	
	/**
	 * 更新测量点数据主表
	 * @param oldTmnlId
	 * @param newTmnlId
	 */
	public void updateEDataMpByTmnlId(String oldTmnlId, String newTmnlId) {
		String cisAssetNo = "";
		String tmnlAddr = "";
		
		String sql = " SELECT * FROM R_TMNL_RUN T WHERE T.TERMINAL_ID=?" ;
		List<?> list = this.getJdbcYXDAO().queryForList(sql, new Object[]{newTmnlId});
		
		String sql2 = " SELECT * FROM D_LC_EQUIP T WHERE T.ID=?" ;
		List<?> list2 = this.getJdbcYXDAO().queryForList(sql2, new Object[]{newTmnlId});
		
		if (list2 != null & list2.size() > 0)  {
			Map<?,?> map = (Map<?,?>) list.get(0);
			cisAssetNo = StringUtil.removeNull(map.get("ASSET_NO"));
		}
		if (list != null & list.size() > 0)  {
			Map<?,?> map = (Map<?,?>) list.get(0);
			tmnlAddr = StringUtil.removeNull(map.get("TERMINAL_ADDR"));
		}
		
		String updateSql = " UPDATE E_DATA_MP T SET T.CIS_TMNL_ASSET_NO=?, T.TERMINAL_ADDR=?, T.TMNL_ASSET_NO = ? WHERE T.TMNL_ASSET_NO=? ";
		this.getJdbcDAO().update(updateSql, new Object[]{cisAssetNo, tmnlAddr, newTmnlId, oldTmnlId});	
	}
	
	/**
	 * 删除关口联系表
	 * @param tmnlId
	 */
	public void deleteCMeterPbsRelaByTmnlId(String tmnlId) {
		String sql = " SELECT * FROM C_METER T WHERE T.TMNL_ASSET_NO=? ";
		List<?> list = this.getJdbcDAO().queryForList(sql, new Object[]{tmnlId});
		String[] batch = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String delSql = " DELETE FROM C_METER_PBS_RELA T WHERE T.METER_ID =? ";
			batch[i] = delSql;
		}
		this.getJdbcDAO().batchUpdate(batch);
	}
	
	/**
	 * 删除联系表
	 * @param tmnlId
	 */
	public void deleteGIoMpByTmnlId(String tmnlId) {
		String sql = " SELECT * FROM C_METER T WHERE T.TMNL_ASSET_NO=? ";
		List<?> list = this.getJdbcDAO().queryForList(sql, new Object[]{tmnlId});
		String[] batch = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String updateSql = " UPDATE G_IO_MP T SET T.IS_VALID=0 WHERE T.METER_ID =? ";
			batch[i] = updateSql;
		}
		this.getJdbcDAO().batchUpdate(batch);
	}
	
	/**
	 * 删除 电能表计量点关系 
	 * @param meterId 电能表ID
	 */
	public void deleteCMeterMpRela(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE C_METER_MP_RELA A ");
		sql.append("	WHERE A.METER_ID=?  ");
		log.debug("deleteCMeterMpRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 删除 采集对象
	 * @param meterId 电能表ID
	 */
	public void deleteCollObj(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE R_COLL_OBJ A ");
		sql.append("	WHERE A.METER_ID=?  ");
		log.debug("deleteCollObj:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 删除 运行电能表（表计信息）
	 * @param meterId 电能表ID
	 */
	public void deleteCMeter(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE C_METER A ");
		sql.append("	WHERE A.METER_ID=?  ");
		log.debug("deleteCMeter:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 更新 电能表 当前状态
	 * @param meterId 电能表ID
	 */
	public void updateDMeterStatus(String meterId) {
		StringBuffer sql = new StringBuffer();
		//用户状态标识
		sql.append("UPDATE D_METER T SET T.CUR_STATUS_CODE='04',  T.RMV_DATE=SYSDATE ");
		sql.append("	WHERE  T.METER_ID=?  ");
		log.debug("updateDMeterStatus:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 删除 终端参数 
	 * @param tmnId 终端编号
	 */
	public void deleteTmnlParam(String tmnId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE T_TMNL_PARAM A ");
		sql.append("	WHERE A.TMNL_ASSET_NO =? ");//资产号就是终端ID（内码）
		log.debug("deleteTmnlParam:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnId});	
	}
	
	/**
	 * 更新 终端任务
	 * @param tmnId 终端编号
	 */
	public void updateTmnlTask(String tmnId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_TASK A SET A.IS_VALID='0' , A.DISABLE_DATE=SYSDATE  ");//0：无效 1：有效
		sql.append("	WHERE A.TMNL_ASSET_NO =? ");
		log.debug("updateTmnlTask:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnId});	
	}
	
	/**
	 * 删除 变压器
	 * @param tgId 台区标识
	 */
	public void deleteGTran(String tgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE G_TRAN A ");
		sql.append("	WHERE A.TG_ID=?  ");
		log.debug("deleteGTran:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tgId});	
	}
	
	/**
	 * 删除 线路台区关系
	 * @param tgId 台区标识
	 */
	public void deleteLineTgRela(String tgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE G_LINE_TG_RELA A ");
		sql.append("	WHERE A.TG_ID=?  ");
		log.debug("deleteLineTgRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tgId});	
	}
	
	/**
	 * 更新 台区 运行状态
	 * @param tgId 台区标识
	 */
	public void updateTtg(String tgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE G_TG A SET A.RUN_STATUS_CODE='00', A.CHG_DATE =SYSDATE ");//00：停用  01：运行
		sql.append("	WHERE A.TG_ID=?  ");
		log.debug("updateTtg:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tgId});	
	}
	

	/**
	 * 删除采集计量关系
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpRela(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_MP_RELA A  ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCpRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除采集计量关系
	 * @param meterId 电能表ID
	 */
	public void deleteRCpMpRelaByMeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM R_CP_MP_RELA R WHERE R.MP_ID IN   ");
		sql.append("	(SELECT C.MP_ID FROM  C_METER_MP_RELA C WHERE C.METER_ID=?)  ");
		log.debug("deleteRCpMpRelaByMeterId:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 删除抄表明细
	 * @param meterId 电能表ID
	 */
	public void deleteImeterRead(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM I_METER_READ I WHERE I.METER_ID=? ");
		log.debug("deleteImeterRead:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 删除关口考核联系表
	 * @param oldMeterId
	 */
	public void deleteCMeterPbsRela(String oldMeterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM C_METER_PBS_RELA I WHERE I.METER_ID=? ");
		log.debug("deleteCMeterPbsRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{oldMeterId});	
	}
	
	/**
	 * 负控专变增加考核单元
	 * @param newMeterId
	 */
	public void insertGIoMpPerMeterId(String tmnlId, String newMeterId) {
		String dataId = "";
		String eDataMpsql = " SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO = ? AND T.METER_ID=? AND IS_VALID=1 ";
		List<?> eDataMpLst = this.getJdbcDAO().queryForList(eDataMpsql, new Object[]{tmnlId, newMeterId});
		if (eDataMpLst != null && eDataMpLst.size() > 0) {
			Map<?,?> eDataMpMap = (Map<?,?>) eDataMpLst.get(0);
			dataId = StringUtil.removeNull(eDataMpMap.get("ID"));
		}

		String cMpSql = " SELECT T.* FROM C_MP T INNER JOIN C_METER_MP_RELA C ON C.METER_ID = ? AND T.MP_ID=C.MP_ID ";
		List<?> cMpLst = this.getJdbcDAO().queryForList(cMpSql, new Object[]{newMeterId});
	   
		for (int i = 0; i < cMpLst.size(); i++ ) {
	    	Map<?,?> cMpMap = (Map<?,?>) cMpLst.get(i);
	    	String lineId = StringUtil.removeNull(cMpMap.get("LINE_ID"));
	    	String orgNo = StringUtil.removeNull(cMpMap.get("ORG_NO"));
	    	
	    	if (!"".equals(lineId)) {
				//检验存在考核单元  线路
				List<?> lineLst = this.getChkUnit(lineId);
				if (lineLst != null && lineLst.size() > 0) {
					String chkUnitId = StringUtil.removeNull(((Map<?,?>)lineLst.get(0)).get("CHKUNIT_ID"));
					if (!"".equals(chkUnitId)) {
						//负控考核单元（线路）
						this.insertGIoMpPerMeter(chkUnitId, lineId, newMeterId, dataId);
					}
				}
			}
	    	if (!"".equals(orgNo)) {
				//检验存在考核单元   部门
				List<?> orgNoLst = this.getChkUnit(orgNo);
				if (orgNoLst != null && orgNoLst.size() > 0) {
					String chkUnitId = StringUtil.removeNull(((Map<?,?>)orgNoLst.get(0)).get("CHKUNIT_ID"));
					if (!"".equals(chkUnitId)) {
						//负控考核单元（部门）
						this.insertGIoMpPerMeter(chkUnitId, orgNo, newMeterId, dataId);
					}
				}
			}		
	    }
	}
	
	/**
	 * 插入关口联系表
	 * @param newMeterId
	 */
	public void insertCMeterPbsRelaPerMeter(String newMeterId) {
		String cMpSql = " SELECT * FROM C_MP T INNER JOIN C_METER_MP_RELA C ON C.METER_ID = ? AND T.MP_ID=C.MP_ID ";
		List<?> cMpLst = this.getJdbcDAO().queryForList(cMpSql, new Object[]{newMeterId});
		StringBuffer sf = null;
		for (int i = 0; i < cMpLst.size(); i++) {
			Map <?,?> cMpMap = (Map<?,?>) cMpLst.get(i);
			String mpId = StringUtil.removeNull(cMpMap.get("MP_ID"));
			String orgNo = StringUtil.removeNull(cMpMap.get("ORG_NO"));
			String lineId = StringUtil.removeNull(cMpMap.get("LINE_ID"));
			String consNo = StringUtil.removeNull(cMpMap.get("CONS_NO"));
			
			if (!"".equals(lineId)) {
				sf = new StringBuffer();
				sf.append(" MERGR INTO C_METER_PBS_RELA A USING (SELECT '"+newMeterId+"' AS METER_ID, '"+mpId+"' AS MP_ID, '"+orgNo+"' AS ORG_NO, '"+consNo+"' AS CONS_NO, '"+lineId+"' AS LINE_ID, '"+newMeterId+"' AS MASTER_METER_ID FROM DUAL) B ");
				sf.append("     ON (A.METER_ID=B.METER_ID)  ");
				sf.append("   WHEN MATCHED THEN UPDATE SET MP_ID=B.MP_ID, ORG_NO=B.ORG_NO, CONS_NO=B.CONS_NO, LINE_ID=B.LINE_ID ");
				sf.append("   WHEN NOT MATCHED THEN INSERT (METER_ID, MP_ID, ORG_NO, CONS_NO, LINE_ID, MASTER_METER_ID)    ");
				sf.append("                         VALUES (B.METER_ID, B.MP_ID, B.ORG_NO, B.CONS_NO,B.LINE_ID,B.MASTER_METER_ID)     ");
				this.getJdbcDAO().update(sf.toString());
			}
		}
	}
	
	/**
	 * 更新 计量点状态
	 * @param cpNo 采集点编号
	 */
	public void updateCMp(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE C_MP A SET A.STATUS_CODE ='04'  ");//标明计量点的当前状态，包括：01设立、02在用、03停用、04撤销等
		sql.append("	WHERE A.MP_ID IN (SELECT C.MP_ID FROM R_COLL_OBJ R,C_MP C, C_METER_MP_RELA CL  ");
		sql.append("   		WHERE R.CP_NO=? AND R.METER_ID=CL.METER_ID AND CL.MP_ID=C.MP_ID)");
		log.debug("updateCMp:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除流入流出线损
	 * @param meterId 电能表ID
	 */
	public void updateGIoMp(String oldMeterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE G_IO_MP T SET UNUSED_DATE=SYSDATE, IS_VALID='0' WHERE T.METER_ID=? ");
		log.debug("updateGIoMp:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{oldMeterId});
	}
	
	/**
	 * 插入流入流出线损
	 * @param oldMeterId
	 * @param newMeterId
	 */
	public void insertGIoMpByMeterId(String tmnlAssetNo, String oldMeterId, String newMeterId) {
		String chkUnit = "";
		String objId = "";
		String dataId = "";
		String mpId = "";
		String isin = "0";

		StringBuffer selsf = new StringBuffer();
		//新增表
		if ("".equals(oldMeterId)) {
			String itmnlSql = " SELECT * FROM I_TMNL_TASK T WHERE T.TERMINAL_ID=?";
			List<?> itmnlTaskLst = getJdbcDAO().queryForList(itmnlSql, new Object[]{tmnlAssetNo});
			if (itmnlTaskLst != null && itmnlTaskLst.size() > 0) {
				Map<?,?> itmnltaskMap = (Map<?,?>) itmnlTaskLst.get(0);
				objId = StringUtil.removeNull(itmnltaskMap.get("TG_ID"));
			}
			//取chkunitId
			String chkunitSql = " SELECT * FROM G_CHKUNIT_COMP T WHERE T.OBJ_ID=? ";
			List<?> chkunitLst = getJdbcDAO().queryForList(chkunitSql, new Object[]{objId});
			if (chkunitLst != null && chkunitLst.size() > 0) {
				Map<?,?> chkunitMap = (Map<?,?>) chkunitLst.get(0);
				chkUnit = StringUtil.removeNull(chkunitMap.get("CHKUNIT_ID"));
			}
			//取mpId
			String cmpSql = " SELECT * FROM C_METER_MP_RELA T WHERE T.METER_ID=? ";
			List<?> cmpLst = getJdbcDAO().queryForList(cmpSql, new Object[]{newMeterId});
			if (cmpLst != null && cmpLst.size() > 0) {
				Map<?,?> cmpLstMap = (Map<?,?>) cmpLst.get(0);
				mpId = StringUtil.removeNull(cmpLstMap.get("MP_ID"));
			}
			//取dataId
			String eDataMpSql = " SELECT * FROM E_DATA_MP T WHERE T.METER_ID=?";
			List<?> eDataMpLst = getJdbcDAO().queryForList(eDataMpSql, new Object[]{newMeterId});
			if (eDataMpLst != null && eDataMpLst.size() > 0) {
				Map<?,?> eDataMpMap = (Map<?,?>) eDataMpLst.get(0);
				dataId = StringUtil.removeNull(eDataMpMap.get("ID"));
			}
			//IS_VALID
			long i = this.checkMainMater(newMeterId);
			if (i == 0) {
				isin = "1";
			}
		}
		//换表
		else {
			//流入流出
			String gIoMpSql = " SELECT * FROM G_IO_MP G WHERE G.METER_ID = ? ";
			List<?> gIoMpLst = getJdbcDAO().queryForList(gIoMpSql, new Object[]{oldMeterId});
			if (gIoMpLst != null && gIoMpLst.size() > 0) {
				Map<?,?> gIoMpMap = (Map<?,?>) gIoMpLst.get(0);
				chkUnit = StringUtil.removeNull(gIoMpMap.get("CHKUNIT_ID"));
				objId = StringUtil.removeNull(gIoMpMap.get("OBJ_ID"));
				dataId = StringUtil.removeNull(gIoMpMap.get("DATA_ID"));
				isin = StringUtil.removeNull(gIoMpMap.get("IS_IN"));
			}
			//取得计量点ID
			String cmpSql = " SELECT * FROM C_METER_MP_RELA T WHERE T.METER_ID=? ";
			List<?> cmpLst = getJdbcDAO().queryForList(cmpSql, new Object[]{newMeterId});
			if (cmpLst != null && cmpLst.size() > 0) {
				Map<?,?> cmpLstMap = (Map<?,?>) cmpLst.get(0);
				mpId = StringUtil.removeNull(cmpLstMap.get("MP_ID"));
			}
		}
		if (!"".equals(chkUnit)) {
			selsf.append("  SELECT '"+chkUnit+"' AS CHKUNIT_ID, '"+objId+"' AS OBJ_ID, '"+mpId+"' AS MP_ID, '"+newMeterId+"' AS METER_ID, ");
			selsf.append(" 		'"+dataId+"' AS DATA_ID, '"+isin+"' AS IS_IN  FROM DUAL ");
			
			StringBuffer sf = new StringBuffer();
			sf.append(" MERGE INTO G_IO_MP A USING  " );
			sf.append("       ("+selsf.toString()+") B  ");
			sf.append("        ON (A.CHKUNIT_ID=B.CHKUNIT_ID AND A.METER_ID= B.METER_ID)   ");
			sf.append(" WHEN MATCHED THEN  ");
			sf.append(" 	   UPDATE SET A.OBJ_ID=B.OBJ_ID, A.MP_ID=B.MP_ID,   ");
			sf.append(" 	              A.DATA_ID=B.DATA_ID, A.IS_IN=B.IS_IN  ");
			sf.append(" WHEN NOT MATCHED THEN  ");
			sf.append("        INSERT (IO_MP_ID, CHKUNIT_ID, OBJ_ID, MP_ID, METER_ID, DATA_ID, IS_IN, USED_DATE, IS_VALID )  ");
			sf.append("        VALUES (S_G_IO_MP.NEXTVAL, B.CHKUNIT_ID, B.OBJ_ID, B.MP_ID, B.METER_ID, B.DATA_ID, B.IS_IN, SYSDATE, '1' )  ");
			this.getJdbcDAO().update(sf.toString());
		}
	}

	/**
	 * 根据表ID取得流入流出线损
	 * @return
	 */
	public List<?> getGIoMpByMeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM  G_IO_MP T  WHERE T.METER_ID=? ");
		log.debug("updateGIoMp:" + sql.toString()) ;
		return getJdbcDAO().queryForList(sql.toString(), new Object[]{meterId});
	}
	
	/**
	 * 删除流入流出线损
	 * @param meterId 电能表ID
	 */
	public void delGIoMp(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE FROM G_IO_MP WHERE METER_ID=? ");
		log.debug("delGIoMp:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	
	/**
	 * 更新 计量点状态
	 * @param meterId 电能表ID
	 */
	public void updateCMpByMeter(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE C_MP A SET A.STATUS_CODE ='04'  ");//标明计量点的当前状态，包括：01设立、02在用、03停用、04撤销等
		sql.append("	WHERE A.MP_ID IN (SELECT C.MP_ID FROM  C_METER_MP_RELA C WHERE C.METER_ID=?) ");
		log.debug("updateCMpByMeter:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{meterId});	
	}
	/////////////////////////////////////////////删除采集点//////////////////////////////////////////////////////
	/**
	 * 删除 负控开关轮次
	 * @param cpNo 采集点编号
	 */
	public void deleteRLcSwitchTurns(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_LC_SWITCH_TURNS A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRLcSwitchTurns:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点开关量参数
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpSwitchPara(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_SWITCH_PARA A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRLcSwitchTurns:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点控制参数
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpCtrlPara(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_CTRL_PARA A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCpCtrlPara:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点通信参数
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpCommPara(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_COMM_PARA A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCpCommPara:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集用户关系
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpConsRela(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_CONS_RELA A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCpConsRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点勘察位置图
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpSurveyDiagram(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_SURVEY_DIAGRAM A   ");
		sql.append("	WHERE A.INST_SCHEME_ID=(SELECT B.INST_SCHEME_ID FROM R_CP_SURVEY B WHERE B.CP_NO=?  )  ");
		log.debug("deleteRCpSurveyDiagram:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点勘察信息
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpSurvey(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_SURVEY A   ");
		sql.append("	WHERE A.CP_NO=?   ");
		log.debug("deleteRCpSurvey:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点现场消缺信息
	 * @param cpNo 采集点编号
	 */
	public void deleteRSiteFaultRmv(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_SITE_FAULT_RMV A   ");
		sql.append("	WHERE A.SITE_FAULT_ID=(SELECT B.SITE_FAULT_ID FROM R_CP_FAULT B WHERE B.CP_NO=?)  ");
		log.debug("deleteRditrFaultRmv:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点缺陷信息
	 * @param cpNo 采集点编号
	 */
	public void deleteRCpFault(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP_FAULT A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCpFault:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	
	/**
	 * 删除 采集点
	 * @param cpNo 采集点编号
	 */
	public void deleteRCp(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CP A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCp:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});	
	}
	///////////////////////////////////////////删除采集点///////////////////////////////////////////////////////

	/**
	 * 根据终端资产号，取户号和单位
	 * @param tmnlAssetNo
	 */
	public List<?> getConsTmnlRelaByTmnlAssetNo(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ORG_NO, CONS_NO FROM R_CONS_TMNL_RELA WHERE TMNL_ASSET_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 更新终端参数T_TMNL_PARAM状态为04
	 */
	public void updateTmnlParamStatue(String tmnlAssetNo, String f9, String f10) {
		f9 = f9 + "%";
		f10 = f10 + "%";
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_PARAM SET STATUS_CODE = '04' WHERE TMNL_ASSET_NO = ? AND (PROT_ITEM_NO LIKE '" + f9 + "' OR PROT_ITEM_NO LIKE '" + f10 + "')");
		List<String> param = new ArrayList<String>();
		param.add(tmnlAssetNo);
		
		log.debug("updateTmnlParamStatue:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(),param.toArray());
	}
	
	/**
	 * 更新非国网终端参数T_TMNL_PARAM状态为04
	 */
	public void updateTmnlParamOtherStatue(String tmnlAssetNo, String blockSn, String protItemNo) {
		protItemNo = protItemNo + "%";
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_PARAM SET STATUS_CODE = '04' WHERE TMNL_ASSET_NO = ? AND PROT_ITEM_NO LIKE '" + protItemNo + "' AND BLOCK_SN = ?");
		List<String> param = new ArrayList<String>();
		param.add(tmnlAssetNo);
		param.add(blockSn);
		
		log.debug("updateTmnlParamOtherStatue:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 更新终端测量点参数T_TMNL_MP_PARAM状态
	 */
	public void updateTmnlMpParamStatue(String dataId, String fn,
			String statusCode) {
		fn = fn + "%";
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE T_TMNL_MP_PARAM SET STATUS_CODE = ? WHERE ID = ? AND PROT_ITEM_NO LIKE '" + fn + "'");
		List<String> param = new ArrayList<String>();
		param.add(statusCode);
		param.add(dataId);
		
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 根据表ID取得测量点数据主表
	 * @param meterId
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpByMeterId(String tmnlAssetNo, String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO=? AND T.METER_ID=? AND T.IS_VALID='1' ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo, meterId});
		return list;
	}
	
	/**
	 * 根据表ID取得测量点数据主表
	 * @param meterId
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpForChangeMeterByMeterId(String tmnlAssetNo, String oldMeterId){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO=? AND T.METER_ID=?  ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo, oldMeterId});
		return list;
	}
	
	/**
	 * 更新测量点数据主表
	 * @param tmnlAssetNo
	 * @param oldMeterId
	 */
	public void updateEDataMpByTmnlMeterId(String tmnlAssetNo, String oldMeterId){
		String sql = " UPDATE E_DATA_MP T SET T.IS_VALID='0' WHERE T.TMNL_ASSET_NO=? AND T.METER_ID=? ";
		this.getJdbcDAO().update(sql, new Object[]{tmnlAssetNo, tmnlAssetNo});
	}
	
	/**
	 * 取得中间库表变更信息
	 * @param appNo
	 * @param meterId
	 * @return
	 */
	public List<?> getRTmnlTaskSr(String appNo, String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_TMNL_TASK_SR T WHERE T.APP_NO=? AND T.NEW_METER_ID=? ");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{appNo, meterId});
		return list;
	}
	
	
	/**
	 * 根据consNo取C_Cons
	 * @param consNo
	 */
	public List<?> getCConsByConsNo(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM C_CONS WHERE CONS_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{consNo});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}

	/**
	 * 根据容量等级取任务模板
	 * @param consType
	 */
	public List<?> getTmnlConsTaskByGapGradeNo(String consType) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM I_TMNL_TASK_SETUP WHERE CONS_TYPE = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{consType});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据容量等级,用户类型取任务模板
	 * @param gapGradeNo
	 * @param consType
	 */
	public List<?> getTmnlConsTaskByGapGradeNo(String consType,
			String gapGradeNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM I_TMNL_TASK_SETUP WHERE CONS_TYPE = ? AND CAP_GRADE_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{consType, gapGradeNo});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 删除 用户终端联系表
	 * @param consNo 用户编号
	 * @param tmnlNo 终端编号
	 */
	public void deleteRconsTmnlRela(String consNo, String tmnlNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CONS_TMNL_RELA A   ");
		sql.append("	WHERE A.CONS_NO=? AND TMNL_ASSET_NO=? ");
		log.debug("deleteRconsTmnlRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{consNo, tmnlNo});
	}
	
	/**
	 * 删除 用户终端联系表
	 * @param tmnlNo 终端编号
	 */
	public void deleteRconsTmnlRela(String tmnlNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CONS_TMNL_RELA A   ");
		sql.append("	WHERE TMNL_ASSET_NO=? ");
		log.debug("deleteRconsTmnlRela:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{tmnlNo});
	}
	
	/**
	 * 删除 群组
	 * @param consNo 用户编号
	 */
	public void deleteGroupDetail(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE T_TMNL_GROUP_DETAIL A   ");
		sql.append("	WHERE A.CONS_NO=? ");
		log.debug("deleteGroupDetail:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{consNo});
	}
	
	/**
	 * 取得营销WS信息
	 * @param tmnlId
	 */
	public List<?> getWSInfo() {
		String param_no = "INTERFACE";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT B.PARAM_ITEM_NO,B.PARAM_ITEM_VAL FROM B_SYS_PARAMETER B WHERE PARAM_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{param_no});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/*
	 * 保存运行管理接口接收到的信息
	 */
	public void insert(Map<String, Object> map, String flag) {

		try {
			String appNo = (String) map.get("APP_NO");//申请编号
			appNo = appNo == null ? "" : appNo.trim();
			// 一个传单编号的配终端通知只允许保存一条记录
			String sqlTmp = "SELECT APP_NO FROM I_TMNL_TASK WHERE APP_NO=?";
			List<?> list_yh = getJdbcDAO().queryForList(sqlTmp,new Object[] { appNo });   
			String sqlMeterFlag = "SELECT * from r_tmnl_task_sr t WHERE t.app_no=?";
			//取得换表的数据
			List<?> meterflagList = this.getJdbcYXDAO().queryForList(sqlMeterFlag,new Object[] { appNo });
			
			List<String> param = null;
			int length = list_yh.size();
			if (length <= 0) {	//当前传单编号不存在则新增
				StringBuffer sql = new StringBuffer();		
				sql.append("INSERT INTO I_TMNL_TASK (APP_NO,TERMINAL_ID,TMNL_TASK_TYPE,CONS_CHG_TYPE,METER_FLAG,CONS_NO,TG_ID,MP_NO,TYPE_CODE,USAGE_TYPE_CODE,NEW_TERMINAL_ID,TERMINAL_TYPE_CODE,ELEC_ADDR,CONS_ID,CP_NO,WKST_APP_NO,DEBUG_STATUS_CODE, ORG_NO) ");
				sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				param = new ArrayList<String>();
				param.add(appNo);
				String terminalId = (String) map.get("TERMINAL_ID");//终端编号
				terminalId = terminalId == null ? "" : terminalId.trim();
				param.add(terminalId);
				String terminalTaskType = (String) map.get("TMNL_TASK_TYPE");//终端变更
				terminalTaskType = terminalTaskType == null ? "" : terminalTaskType.trim();
				param.add(terminalTaskType);
				String consChgType = (String) map.get("CONS_CHG_TYPE");//用户变更
				consChgType = consChgType == null ? "" : consChgType.trim();
				param.add(consChgType);
				
				String meterFlag = null;
				if ("0".equals(flag)) {
					meterFlag = (String) map.get("METER_FLAG");//电表(CT/PT)是否变更
					meterFlag = meterFlag == null ? "" : meterFlag.trim();
					param.add(meterFlag);
				} else {
					if (null != meterflagList && meterflagList.size() > 0 && !"01".equals(terminalTaskType)) {
						meterFlag = "1";
					} else {
						meterFlag = "0";
					}
					param.add(meterFlag);
				}
				String consNo = (String) map.get("CONS_NO");//用户编号
				consNo = consNo == null ? "" : consNo.trim();
				param.add(consNo);
				String tgId = (String) map.get("TG_ID");//台区标识
				tgId = tgId == null ? "" : tgId.trim();
				param.add(tgId);
				String mpNo = (String) map.get("MP_NO");//计量点编号
				mpNo = mpNo == null ? "" : mpNo.trim();
				param.add(mpNo);
				String typeCode = (String) map.get("TYPE_CODE");//计量点分类
				typeCode = typeCode == null ? "" : typeCode.trim();
				param.add(typeCode);
				String usageTypeCode = (String) map.get("USAGE_TYPE_CODE");//计量点主用途类型
				usageTypeCode = usageTypeCode == null ? "" : usageTypeCode.trim();
				param.add(usageTypeCode);
				String newTerminalId = (String) map.get("NEW_TERMINAL_ID");//新终端编号
				newTerminalId = newTerminalId == null ? "" : newTerminalId.trim();
				param.add(newTerminalId);
				String terminalTypeCode = (String) map.get("TERMINAL_TYPE_CODE");//终端类型
				terminalTypeCode = terminalTypeCode == null ? "" : terminalTypeCode.trim();
				param.add(terminalTypeCode);
				String elecAddr = (String) map.get("ELEC_ADDR");//用电地址
				elecAddr = elecAddr == null ? "" : elecAddr.trim();
				param.add(elecAddr);
				String consId = (String) map.get("CONS_ID");//用户标识
				consId = consId == null ? "" : consId.trim();
				param.add(consId);
				String cpNo = (String) map.get("CP_NO");//采集点编号
				cpNo = cpNo == null ? "" : cpNo.trim();
				param.add(cpNo);
				String wkstAppNo = (String) map.get("WKST_APP_NO");//业扩申请编号
				wkstAppNo = wkstAppNo == null ? "" : wkstAppNo.trim();
				param.add(wkstAppNo);
				param.add("1"); //状态:1:初始状态
				String orgNo = (String) map.get("ORG_NO");//单位编号
				orgNo = orgNo == null ? "" : orgNo.trim();
				param.add(orgNo);
				log.debug("sql:" + sql.toString());
				getJdbcDAO().update(sql.toString(),param.toArray());
			}else{
				//更新
				StringBuffer sql = new StringBuffer();		
				sql.append("UPDATE I_TMNL_TASK SET TERMINAL_ID = ?, TMNL_TASK_TYPE = ?, CONS_CHG_TYPE = ?,METER_FLAG = ?,CONS_NO = ?,TG_ID = ?,MP_NO = ?,TYPE_CODE = ?,USAGE_TYPE_CODE = ?,NEW_TERMINAL_ID = ?,TERMINAL_TYPE_CODE = ?,ELEC_ADDR = ?,CONS_ID = ?,CP_NO = ?,WKST_APP_NO = ?,DEBUG_STATUS_CODE = ?, ORG_NO = ?, DEBUG_TIME = SYSDATE ");
				sql.append(" WHERE APP_NO = ?");
				param = new ArrayList<String>();
				
				String terminalId = (String) map.get("TERMINAL_ID");//终端编号
				terminalId = terminalId == null ? "" : terminalId.trim();
				param.add(terminalId);
				String terminalTaskType = (String) map.get("TMNL_TASK_TYPE");//终端变更
				terminalTaskType = terminalTaskType == null ? "" : terminalTaskType.trim();
				param.add(terminalTaskType);
				String consChgType = (String) map.get("CONS_CHG_TYPE");//用户变更
				consChgType = consChgType == null ? "" : consChgType.trim();
				param.add(consChgType);
				String meterFlag = null;
				if ("0".equals(flag)) {
					meterFlag = (String) map.get("METER_FLAG");//电表(CT/PT)是否变更
					meterFlag = meterFlag == null ? "" : meterFlag.trim();
					param.add(meterFlag);
				} else {
					if (null != meterflagList && meterflagList.size() > 0 && !"01".equals(terminalTaskType)) {
						meterFlag = "1";
					} else {
						meterFlag = "0";
					}
					param.add(meterFlag);
				}
				String consNo = (String) map.get("CONS_NO");//用户编号
				consNo = consNo == null ? "" : consNo.trim();
				param.add(consNo);
				String tgId = (String) map.get("TG_ID");//台区标识
				tgId = tgId == null ? "" : tgId.trim();
				param.add(tgId);
				String mpNo = (String) map.get("MP_NO");//计量点编号
				mpNo = mpNo == null ? "" : mpNo.trim();
				param.add(mpNo);
				String typeCode = (String) map.get("TYPE_CODE");//计量点分类
				typeCode = typeCode == null ? "" : typeCode.trim();
				param.add(typeCode);
				String usageTypeCode = (String) map.get("USAGE_TYPE_CODE");//计量点主用途类型
				usageTypeCode = usageTypeCode == null ? "" : usageTypeCode.trim();
				param.add(usageTypeCode);
				String newTerminalId = (String) map.get("NEW_TERMINAL_ID");//新终端编号
				newTerminalId = newTerminalId == null ? "" : newTerminalId.trim();
				param.add(newTerminalId);
				String terminalTypeCode = (String) map.get("TERMINAL_TYPE_CODE");//终端类型
				terminalTypeCode = terminalTypeCode == null ? "" : terminalTypeCode.trim();
				param.add(terminalTypeCode);
				String elecAddr = (String) map.get("ELEC_ADDR");//用电地址
				elecAddr = elecAddr == null ? "" : elecAddr.trim();
				param.add(elecAddr);
				String consId = (String) map.get("CONS_ID");//用户标识
				consId = consId == null ? "" : consId.trim();
				param.add(consId);
				String cpNo = (String) map.get("CP_NO");//采集点编号
				cpNo = cpNo == null ? "" : cpNo.trim();
				param.add(cpNo);
				String wkstAppNo = (String) map.get("WKST_APP_NO");//业扩申请编号
				wkstAppNo = wkstAppNo == null ? "" : wkstAppNo.trim();
				param.add(wkstAppNo);
				param.add("1"); //状态:1:初始状态
				String orgNo = (String) map.get("ORG_NO");//单位编号
				orgNo = orgNo == null ? "" : orgNo.trim();
				param.add(orgNo);
				param.add(appNo);
				log.debug("sql:" + sql.toString());
				getJdbcDAO().update(sql.toString(),param.toArray());	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 更新测试状态
	 */
	public void updateTestStatue(String appNo, String status) {
		try {
			StringBuffer sql = new StringBuffer();
			List<String> param = new ArrayList<String>();
			sql.append("UPDATE I_TMNL_TASK SET DEBUG_STATUS_CODE =? WHERE 1=1 ");
			param.add(status);
			if (appNo != null && !"".equals(appNo)){
			   sql.append(" AND APP_NO=?");
			   param.add(appNo);
			}
			log.debug("updateTestStatue sql:" + sql.toString()) ;	
			if (appNo != null && !"".equals(appNo)){
				getJdbcDAO().update(sql.toString(),param.toArray());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 关口表
	 * @param tmnlId
	 */
	public void insertCMeterPbsRela(String tmnlId) {
		String cmeterSql = " SELECT * FROM C_METER T WHERE T.TMNL_ASSET_NO= ?";
		List<?> cMeterLst = this.getJdbcDAO().queryForList(cmeterSql, new Object[]{tmnlId});
		
		String numSql = " SELECT COUNT(C.MP_ID) AS MP_NUM FROM C_METER T, C_METER_MP_RELA C WHERE T.TMNL_ASSET_NO=? AND T.METER_ID=C.METER_ID ";
		List<?> list = this.getJdbcDAO().queryForList(numSql, new Object[]{tmnlId});
		Map<?,?> resultMap = (Map<?,?>) list.get(0);
		int num = Integer.parseInt(StringUtil.removeNull(resultMap.get("MP_NUM")));
		int k = 0;
		String[] merge = new String[num];
		//METER
		for (int i = 0; i < cMeterLst.size(); i++) {
			Map<?, ?> cmeterMap = (Map<?,?>) cMeterLst.get(i);
			String meterId = StringUtil.removeNull(cmeterMap.get("METER_ID"));
			
			//cmp
			String sql = " SELECT P.* FROM C_METER_MP_RELA C, C_MP P WHERE C.METER_ID=? AND C.MP_ID=P.MP_ID ";
			List<?> cmpLst =  this.getJdbcDAO().queryForList(sql, new Object[]{});
			for (int j = 0; j < cmpLst.size(); j++) {
				Map<?,?> cmpMap = (Map<?,?>) cmpLst.get(j);
				String mpId = StringUtil.removeNull(cmpMap.get("MP_ID"));
				String orgNo = StringUtil.removeNull(cmpMap.get("ORG_NO"));
				String consNo = StringUtil.removeNull(cmpMap.get("CONS_NO"));
				String lineId = StringUtil.removeNull(cmpMap.get("LINE_ID"));
				
				StringBuffer sf = new StringBuffer();
				sf .append("  MERGE INTO C_METER_PBS_RELA A USING (SELECT '"+meterId+"' AS METER_ID, '"+mpId+"' AS MP_ID, '"+orgNo+"' AS ORG_NO, '"+consNo+"' AS CONS_NO, '"+lineId+"' AS LINE_ID, '"+meterId+"' AS MASTER_METER_ID FROM DUAL )B ");
				sf .append("      ON (A.METER_ID=B.METER_ID) ");
				sf .append("      WHEN MATCHED THEN UPDATE SET A.MP_ID=B.MP_ID, A.ORG_NO=B.ORG_NO, A.CONS_NO=B.CONS_NO, A.LINE_ID=B.LINE_ID ");
				sf.append("       WHEN NOT MATCHED THEN INSERT( METER_ID, MP_ID, ORG_NO, CONS_NO, LINE_ID, MASTER_METER_ID )");
				sf.append("                             VALUES(B.METER_ID, B.MP_ID, B.ORG_NO, B.CONS_NO, B.LINE_ID, B.MASTER_METER_ID ");
				
				merge[k] = sf.toString();
				k++;
			}
		}	
		//merge
		this.getJdbcDAO().batchUpdate(merge);
	}
	
	
	/**
	 * 删除终端实时工况
	 * @param tmnlId
	 * @return
	 */
	public void removeATmnlRealTime(String tmnlId) {
		String sql = " DELETE FROM A_TMNL_REAL_TIME T WHERE t.TMNL_ASSET_NO=? ";
		this.getJdbcDAO().update(sql, new Object[]{tmnlId});
	}
	
	/**
	 * 更新考核单元状态
	 * @param tgId
	 */
	public void updateGChkUnitStatus(String tgId) {
		String sql = " UPDATE G_CHKUNIT T SET T.STATUS_CODE ='2' WHERE T.OBJ_ID=? ";
		this.getJdbcDAO().update(sql, new Object[]{tgId});
		
		String sql2 = " UPDATE G_IO_MP T SET T.IS_VALID ='0' WHERE T.OBJ_ID=? ";
		this.getJdbcDAO().update(sql2, new Object[]{tgId});
	}
	
	/**
	 * 增加终端实时工况
	 * @param tmnlId
	 */
	public void addATmnlRealTime(String tmnlId) {
		StringBuffer sf = new StringBuffer();
		sf.append(" MERGE INTO A_TMNL_REAL_TIME A USING (SELECT '"+tmnlId+"' AS TMNL_ASSET_NO, '0' AS IS_ONLINE FROM DUAL) B ");
		sf.append("       ON (A.TMNL_ASSET_NO=B.TMNL_ASSET_NO ) ");
		sf.append("     WHEN MATCHED THEN UPDATE SET A.IS_ONLINE=B.IS_ONLINE ");
		sf.append("     WHEN NOT MATCHED THEN INSERT (TMNL_ASSET_NO) VALUES (B.TMNL_ASSET_NO) ");
		this.getJdbcDAO().update(sf.toString());
	}
	
	/**
	 * 更新终端运行状态
	 * @param terminalId
	 */
	public void updateTmnlStatus(String terminalId) {
		String sql = " UPDATE R_TMNL_RUN T SET T.STATUS_CODE='01' WHERE T.TERMINAL_ID=? ";
		getJdbcDAO().update(sql.toString(), new Object[]{terminalId});
		log.debug("updateTmnlStatus sql:" + sql.toString()) ;	
	}
	
	/**
	 * 根据台区ID取得线路ID
	 * @param tgId
	 * @return
	 */
	public List<String> getLineByTgId(String tgId) {
		String sql = " SELECT * FROM G_LINE_TG_RELA T WHERE T.TG_ID=?";
		List<?> list = this.getJdbcDAO().queryForList(sql.toString(),new Object[]{tgId});
		List<String> lineLst = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map<?,?> map = (Map<?,?>) list.get(i);
			String lineId = StringUtil.removeNull(map.get("LINE_ID"));
			lineLst.add(lineId);
		}
		return lineLst;
	}
	
	
	/**
	 * 根据台区ID取得部门编号
	 * @param tgId
	 * @return
	 */
	public List<String> getOrgNoByTgId(String tgId) {
		String sql = " SELECT * FROM G_TG T WHERE T.TG_ID=? ";
		List<?> list = this.getJdbcDAO().queryForList(sql.toString(),new Object[]{tgId});
		List<String> lineLst = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map<?,?> map = (Map<?,?>) list.get(i);
			String orgNo = StringUtil.removeNull(map.get("ORG_NO"));
			lineLst.add(orgNo);
		}
		return lineLst;
	}
	
	
	/**
	 * 根据线路ID取得线路
	 * @param lineId
	 * @return
	 */
	public List<String> getLineByLineId(String lineId) {
		String sql = " SELECT * FROM G_LINE_RELA t WHERE t.LINK_LINE_ID=? OR t.LINE_ID=? AND t.CASCADE_FLAG='1' ";
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{lineId});
		List<String> lineLst = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map<?,?> map = (Map<?,?>) list.get(i);
			String line = StringUtil.removeNull(map.get("LINE_ID"));
			lineLst.add(line);
		}
		return lineLst;
	}
	
	/**
	 * 检查考核组成
	 * @param lineId
	 */
	public String checkChkUnit(String Id) {//TODO
		
		
		
		
		return null;
	}
	
	
	/**
	 * 根据采集点编号取得采集点编号
	 * @param cpNo
	 * @return
	 */
	public List<?> getRcpBycpNo(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM R_CP R WHERE R.CP_NO=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});	
		log.debug("getRcpBycpNo sql:" + sql.toString()) ;			
		return list;
	}
	
	/**
	 * 根据采集点编号取得采集点计量点关系
	 * @param cpNo
	 * @return
	 */
	public List<?> getCMpByCpNo(String cpNo){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT C.* FROM C_MP C INNER JOIN R_CP_MP_RELA R ON R.MP_ID=C.MP_ID AND R.CP_NO=?");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});	
		log.debug("getCMpByCpNo sql:" + sql.toString()) ;			
		return list;
	}
	
	/**
	 * 根据采集点编号取得变电站线路关系
	 * @param cpNo
	 * @return
	 */
	public List<?> getGSubsLineRelaByCpNo(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT G.SUBS_ID FROM G_SUBS_LINE_RELA G ");
		sql.append(" 	INNER JOIN C_MP T ON G.LINE_ID = T.LINE_ID AND T.LINE_ID IS NOT NULL ");
		sql.append(" 	INNER JOIN R_CP_MP_RELA R ON R.CP_NO = ? AND R.MP_ID = T.MP_ID ");
		List<?> list = getJdbcYXDAO().queryForList(sql.toString(),new Object[]{cpNo});	
		log.debug("getGSubsLineRelaBy sql:" + sql.toString()) ;			
		return list;
	}

	
	public List<?> getITmnlTaskByAppNo(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM I_TMNL_TASK WHERE APP_NO=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{appNo});
		
		log.debug("getITmnlTaskByAppNo sql:" + sql.toString()) ;		
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	
	/**
	 * 更新流程测试状态
	 * @param appNo
	 */
	public void updateItmnlTaskDebugStatus(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" UPDATE I_TMNL_TASK T SET T.DEBUG_STATUS_CODE = '1' WHERE T.APP_NO=?");
		getJdbcDAO().update(sql.toString(),new Object[]{appNo});
		log.debug("updateItmnlTaskDebugStatus sql:" + sql.toString()) ;		
	}

	public void delByAppNo(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE I_TMNL_TASK A   ");
		sql.append("	WHERE A.APP_NO=?   ");
		log.debug("delByAppNo:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{appNo});	
	}

	/**
	 * 取通信规约小项
	 * @param protocolNo 终端编号
	 */
	public List<?> getProtItemByProtocolNo(String protocolNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM B_COMM_PROTOCOL_ITEM WHERE PROTOCOL_NO=? ORDER BY PROT_ITEM_SN");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{protocolNo});
		
		log.debug("getProtItemByProtocolNo:" + sql.toString()) ;
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}
	
	/**
	 * 检查是否是主表
	 * @param ameterId
	 * @return 0：主表  1：居民表
	 */
	public long checkMainMater(String ameterId) {
		String typeCode = null;
		String mpAttrCode = null;

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.type_code,c.mp_attr_code from c_mp c,c_meter_mp_rela t WHERE t.meter_id=? AND t.mp_id=c.mp_id ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{ameterId});
		if (null != list && list.size() > 0 ) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			typeCode = StringUtil.removeNull(map.get("TYPE_CODE"));
			mpAttrCode = StringUtil.removeNull(map.get("MP_ATTR_CODE"));
		}
		if ("03".equals(typeCode) && "02".equals(mpAttrCode)) {
			return 0;
		} else {
			return 1;
		}	
	}

	/**
	 * 取集抄设备
	 * @param fmrAssetNo 集抄设备ID
	 */
	public List<?> getOtherDev(String fmrAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_EXEC_OTHER_DEV WHERE COLLECTOR_ID=?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{fmrAssetNo});
		
		log.debug("getOtherDev:" + sql.toString()) ;
		if(list == null || list.size()== 0) {
			return null;
		}		
		return list;
	}

	/**
	 * 根据申请编号和调试进度取得调试进度
	 * @param debugProgress 
	 * @param appNo
	 */
	public List<?> getITmnlDebug(String appNo, String debugProgress) {
		String isql = " SELECT * FROM I_TMNL_TASK T WHERE T.APP_NO =? AND T.CONS_NO LIKE 'T%' ";
		List<?> iLst = this.getJdbcDAO().queryForList(isql, new Object[]{appNo});
		//集抄
		if (iLst != null && iLst.size() > 0) {
			String frmSql = " SELECT * FROM I_FLOW_PROCESS T WHERE T.APP_NO=? AND T.FLOW_STATUS='0' ";
			List<?> frmLst = this.getJdbcDAO().queryForList(frmSql, new Object[]{appNo});
			if (frmLst != null && frmLst.size() == 5) {
				return frmLst;
			} else {
				return null;
			}
		} 
		//负控
		else {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM I_TMNL_DEBUG WHERE APP_NO = ? AND DEBUG_PROGRESS = ?");
			List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{appNo, debugProgress});
			
			log.debug("getITmnlDebug:" + sql.toString()) ;
			if(list == null || list.size()== 0) {
				return null;
			}		
			return list;
		}
	}

	/**
	 * 插入接口抄表明细
	 */
	public void insertIMeterRead(String meterId, String mrFlag, String mrNum) {
		StringBuffer sqlSlect = new StringBuffer();
		sqlSlect.append("SELECT '"+ meterId +"' AS METER_ID, '"+ mrFlag +"' AS MR_FLAG, '"+ mrNum +"' AS MR_NUM FROM DUAL");
		
		StringBuffer sql = new StringBuffer();
		sql.append(" MERGE INTO  I_METER_READ A USING( " + sqlSlect.toString() + " ) B ON (A.METER_ID = B.METER_ID)");
		sql.append(" when MATCHED then ");
		sql.append(" 	UPDATE SET A.MR_FLAG = B.MR_FLAG, A.MR_NUM = B.MR_NUM, A.MR_TIME = SYSDATE ");
		sql.append(" when NOT MATCHED then ");
		sql.append(" 	INSERT(METER_ID, MR_FLAG, MR_NUM, MR_TIME) "); 
		sql.append(" 	VALUES(?,?,?,SYSDATE)");
		
		log.debug("insertIMeterRead:" + sql.toString());

		getJdbcDAO().update(sql.toString(),new Object[]{meterId, mrFlag, mrNum});
	}
	
	/**
	 * 取得call_max
	 */
	public int getCallNum() {
		int callMax = 0;
		try {
			String sql = " SELECT * FROM B_SYS_PARAMETER T WHERE T.PARAM_NO='INTERFACE' AND T.PARAM_ITEM_NO='CALL_MAX' ";
			List<?> list = this.getJdbcDAO().queryForList(sql);
			if (list != null && list.size() > 0) {
				Map<?,?> map = (Map<?,?>) list.get(0);
				callMax = Integer.parseInt(String.valueOf(map.get("PARAM_ITEM_VAL")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return callMax;
	}
	
	/**
	 * 批量处理参数状态
	 * @param tmnlAssetNo
	 * @param mpSns
	 */
	public void batchUpdateTTmnlParamStatus(String tmnlAssetNo, String mpSns) {
		try {
			String[] mpArr = mpSns.split(",");
			String[] updateArr = new String[mpArr.length];
			
			for (int i = 0; i < mpArr.length; i++) {
				String updateSql = " UPDATE T_TMNL_PARAM T SET T.STATUS_CODE='04' WHERE T.TMNL_ASSET_NO='"+tmnlAssetNo+"' AND T.BLOCK_SN='"+mpArr[i]+"' ";
				updateArr[i] = updateSql;
			}
			this.getJdbcDAO().batchUpdate(updateArr);
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 取S_T_BG_TASK.NEXTVAL
	 */
	public String getSeqTbgTask() {
		String seq = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S_T_BG_TASK.NEXTVAL FROM DUAL");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{});
		if(list == null || list.size()== 0) {
			return null;
		}
		Map<?, ?> map = (Map<?, ?>) list.get(0);
		seq = String.valueOf(map.get("NEXTVAL"));
		return seq;
	}
	
	/**
	 * 取得测量点数据主表(终端除外)
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> getEDataMpWithoutTmnlByTmnlId(String tmnlAssetNo) {
		String sql = " SELECT * FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO=? AND T.IS_VALID='1' AND T.MP_SN<>0 ";
		return this.getJdbcDAO().queryForList(sql, new Object[]{tmnlAssetNo});
	}
	
	/**
	 * 取得终端参数数量
	 * @param tmnlAssetNo
	 */
	public String getTTmnlParmNumByAssetNo(String tmnlAssetNo) {
		String num = "0";
		String sql = " SELECT COUNT(DISTINCT t.BLOCK_SN) AS PARAM_NUM FROM t_tmnl_param t WHERE t.TMNL_ASSET_NO=? AND t.PROT_ITEM_NO LIKE '5040A%' ";
		List<?> list = this.getJdbcDAO().queryForList(sql, new Object[]{tmnlAssetNo});
		if (list != null && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			num = StringUtil.removeNull(map.get("PARAM_NUM"));
		}
		return num;
	}
	
	/**
	 * 取S_I_INTERFACE_LOG.NEXTVAL
	 */
	public String getIinterfaceLog() {
		String seq = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT S_I_INTERFACE_LOG.NEXTVAL FROM DUAL");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{});
		if(list == null || list.size()== 0) {
			return null;
		}
		Map<?, ?> map = (Map<?, ?>) list.get(0);
		seq = String.valueOf(map.get("NEXTVAL"));
		return seq;
	}
	
	/**
	 * 中断的流程调试
	 * @param appNo
	 * @param tmnlId
	 */
	public void solveHalfOperation(String appNo, String tmnlId, String cpNo) {
		String taskSql = " SELECT * FROM I_TMNL_TASK T WHERE T.APP_NO=? ";
		List<?> list = this.getJdbcDAO().queryForList(taskSql, new Object[]{appNo});
		int appNoNum = list.size();
		
		String objSql = " SELECT * FROM R_COLL_OBJ T WHERE T.CP_NO=? ";
		List<?> objLst = this.getJdbcDAO().queryForList(objSql, new Object[]{cpNo});
		int objNum = objLst.size();
		
		String midobjSql = " SELECT * FROM R_COLL_OBJ T WHERE T.CP_NO=? ";
		List<?> midobjLst = this.getJdbcYXDAO().queryForList(midobjSql, new Object[]{cpNo});
		int midobjNum = midobjLst.size();
		
		if (appNoNum == 1 && (objNum < midobjNum)) {
			String eDataMpSql = " DELETE FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO='"+tmnlId+"' ";
			String tTmnlParamSql = " DELETE FROM T_TMNL_PARAM T WHERE T.TMNL_ASSET_NO='"+tmnlId+"' ";
			String iFlowProcessSql = " DELETE FROM I_FLOW_PROCESS T WHERE T.TERMINAL_ID='"+tmnlId+"' AND T.APP_NO='"+appNo+"' ";
			this.getJdbcDAO().batchUpdate(new String[]{eDataMpSql, tTmnlParamSql, iFlowProcessSql});
		}
	}
	
	/**
	 * 插入中间异常信息
	 * @param orgNo
	 * @param collObjId
	 * @param alarmCode
	 * @param alarmDesc
	 * @param typeCode
	 * @param occurTime
	 * @param operator
	 */
	public void insertRExcpAlarm(String orgNo, String collObjId, String alarmCode, 
			String alarmDesc, String typeCode, String occurTime, String operator, String terminalId) {
		try {
			StringBuffer sf = new StringBuffer();
			SimpleDateFormat sif = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sif.parse(occurTime);
			sf.append(" INSERT INTO R_EXCP_ALARM R (ALARM_ID, RPT_DATE, COLL_OBJ_ID, ORG_NO, OCCUR_TIME, RPT_EMP_NO,  ");
			sf.append(" 							ALARM_CODE, ALARM_DESC, TYPE_CODE, TERMINAL_ID) ");
			sf.append("             VALUES (SEQ_R_EPCT_COMMON.NEXTVAL, SYSDATE,?,?,?,?,?,?,?,?) ");
			this.getJdbcYXDAO().update(sf.toString(), new Object[]{collObjId, orgNo, date, operator, alarmCode, alarmDesc, typeCode, terminalId});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 插入后台任务主表
	 * @param tbgTask
	 */
	public void addTbgTask(TbgTask tbgTask) {
		StringBuffer using = new StringBuffer();
		using.append(" SELECT '"+tbgTask.getTaskId()+"' AS TASK_ID, '"+tbgTask.getTmnlAssetNo()+"' AS TMNL_ASSET_NO, '"+tbgTask.getTaskType()+"' AS TASK_TYPE FROM DUAL");
		StringBuffer sf = new StringBuffer();
		sf.append(" MERGE INTO T_BG_TASK A USING ("+using.toString()+") B ON (A.TMNL_ASSET_NO=B.TMNL_ASSET_NO AND A.TASK_TYPE=B.TASK_TYPE) ");
		sf.append("		WHEN  MATCHED THEN UPDATE SET A.TASK_STATUS='03', A.SEND_TIME=SYSDATE, A.NEXT_SEND_TIME=SYSDATE+5/1440, A.SEND_TIMES='0' ");
		sf.append("     WHEN NOT MATCHED THEN INSERT (A.TASK_ID, A.TMNL_ASSET_NO, A.TASK_TYPE, A.OBJ_TYPE, A.TASK_STATUS, A.SEND_TIME, A.NEXT_SEND_TIME, A.MAX_TRY, A.TASK_TIME)");
		sf.append("     	VALUES (B.TASK_ID, B.TMNL_ASSET_NO, '"+tbgTask.getTaskType()+"', '"+tbgTask.getObjType()+"', '"+tbgTask.getTaskStatus()+"', SYSDATE, SYSDATE+2/1440, '"+tbgTask.getMaxTry()+"', SYSDATE)");
		getJdbcDAO().update(sf.toString());
		log.info("-----------后台保存任务成功-----------");
	}
	
	/**
	 * 插入后台任务主表明细表
	 * @param tbgTaskDetail
	 */
	public void addTbgTaskDetail(TbgTaskDetail tbgTaskDetail) {
		StringBuffer sf = new StringBuffer();
		sf.append( " INSERT INTO T_BG_TASK_DETAIL ( TASK_ID, DATA_ITEM_NO, DATA_TYPE) ");
		sf.append("  	VALUES (?, ?, ?) ");
		getJdbcDAO().update(sf.toString(), new Object[]{tbgTaskDetail.getTaskId(), tbgTaskDetail.getDataItemNo(), tbgTaskDetail.getDataType()});
	}

	/**
	 * 根据tmnlAssetNo取对应的终端参数
	 * @param tmnlAssetNo
	 */
	public List<?> getTmnlParamByTmnlAssetNo(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_TMNL_PARAM WHERE TMNL_ASSET_NO = ? AND PROT_ITEM_NO LIKE '%0A002%' ORDER BY TO_NUMBER(BLOCK_SN) ASC");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});	
		log.debug("getTmnlParamByTmnlAssetNo:" + sql.toString()) ;
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 根据tmnlAssetNo取对应的终端参数
	 * @param tmnlAssetNo
	 */
	public List<?> getTmnlParamByTmnlAssetNoMpSn(String tmnlAssetNo, String[] mpSnArrary) {
		StringBuffer mpSn = new StringBuffer();
		for (int i = 0; i < mpSnArrary.length; i++) {
			mpSn.append("'"+mpSnArrary[i]+"',");
		}
		String param = mpSn.substring(0, mpSn.length()-1);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM T_TMNL_PARAM WHERE TMNL_ASSET_NO ="+tmnlAssetNo+" AND PROT_ITEM_NO LIKE '%0A002%' AND BLOCK_SN IN ("+param+") ");
		List<?> list = getJdbcDAO().queryForList(sql.toString());	
		log.debug("getTmnlParamByTmnlAssetNoMpSn:" + sql.toString()) ;
		return list;
	}
	
	/**
	 * 取得失败原因
	 * @param tmnlAssetNo
	 */
	public String getFailureCodeFromTTmnlParam(String tmnlAssetNo){
		String sql = "	SELECT * FROM T_TMNL_PARAM T WHERE T.TMNL_ASSET_NO=? AND T.PROT_ITEM_NO LIKE '5040A%'  AND T.FAILURE_CODE IS NOT NULL ";
		List<?> lst = this.getJdbcDAO().queryForList(sql, new Object[]{tmnlAssetNo});
		if (lst != null && lst.size() > 0) {
			Map<?,?> map = (Map<?,?>) lst.get(0);
			return StringUtil.removeNull(map.get("FAILURE_CODE"));
		} else {
			return "-17";
		}
	}
	
	/**
	 * 取得表通讯规约
	 * @param tmnlAssetNo
	 */
	public String getCommNo(String meterId, String protocolCode) {
		String commNo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_COLL_OBJ R WHERE R.METER_ID=?");
		List<?> list = this.getJdbcYXDAO().queryForList(sql.toString(),new Object[]{meterId});	
		log.debug("getCommNo:" + sql.toString()) ;
		if(list != null && list.size() > 0) {
			Map<?, ?> map = (Map<?, ?>) list.get(0);
			commNo = StringUtil.removeNull(map.get("COMM_PROT_NO"));
		}
		return commNo;
	}
	
	/**
	 * 取得中间库采集对象中的collectorId
	 * @param meterId
	 * @return
	 */
	public String judgePort(String meterId) {
		String collectorId = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM R_COLL_OBJ R WHERE R.METER_ID=?");
		List<?> list = this.getJdbcYXDAO().queryForList(sql.toString(),new Object[]{meterId});	
		if(list != null && list.size() > 0) {
			Map<?, ?> map = (Map<?, ?>) list.get(0);
			collectorId = StringUtil.removeNull(map.get("COLLECTOR_ID"));
		}
		return collectorId;
	}
	
	/**
	 * 根据终端ID取得流程明细 
	 * @param appNo      申请号
	 * @param tmnlId     终端ID
	 * @param flowNode   节点标识
	 * @return
	 */
	public Map<?, ?> getIFlowProcess(String appNo, String tmnlId, String flowNode) {
		Map<?,?> map = null;
		String sql = " SELECT * FROM I_FLOW_PROCESS I WHERE I.APP_NO=? AND I.TERMINAL_ID=? AND I.FLOW_NODE=?";
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{appNo, tmnlId, flowNode});
		if (list != null && list.size() > 0) {
			map = (Map<?,?>) list.get(0);
		}
		return map;
	}
	
	/**
	 * 取得E_DATA_MP总数
	 * @return
	 */
	public int getEDataMpNumByCpNo(String tmnlId) {
		String sql = " SELECT * FROM E_DATA_MP I WHERE I.Tmnl_Asset_No=? AND IS_VALID <>0";
		List<?> list = getJdbcDAO().queryForList(sql.toString(), new Object[]{tmnlId});
		if (list != null) {
			return list.size();
		}
		return 0;
	}
	
	/**
	 * 根据终端ID取得测量点数据主表
	 * @param tmnlId
	 * @return
	 */
	public List<?> getEDataMpByTmnlId(String tmnlId) {
		String sql = " SELECT * FROM E_DATA_MP I WHERE I.Tmnl_Asset_No=? AND IS_VALID <>0";
		return getJdbcDAO().queryForList(sql.toString(), new Object[]{tmnlId});
	}
	
	/**
	 * 更新终端流程明细
	 * @param appNo
	 * @param tmnlAssetNo
	 * @param flowNode
	 * @param flowStatus
	 * @param successNum
	 * @param failureNum
	 * @param mpSnList
	 */
	public void updateIFlowProcess(String appNo, String tmnlAssetNo, String flowNode, String flowStatus, String mpSnList, String failCode) {
		//Using
		StringBuffer sf = new StringBuffer();
		StringBuffer usingSf = new StringBuffer();
		usingSf.append(" '"+appNo+"' AS APP_NO, '"+tmnlAssetNo+"' AS TERMINAL_ID, '"+flowNode+"' AS FLOW_NODE, ");
		usingSf.append(" '"+flowStatus+"' AS FLOW_STATUS, '"+mpSnList+"' AS MP_SN_LIST, '"+failCode+"' AS FAILURE_CODE ");
		
		//merge
		sf.append(" MERGE INTO I_FLOW_PROCESS A USING (SELECT "+usingSf.toString()+" FROM DUAL) B ");
		sf.append("     ON (A.APP_NO=B.APP_NO AND A.TERMINAL_ID=B.TERMINAL_ID AND A.FLOW_NODE=B.FLOW_NODE) ");
		sf.append("     WHEN MATCHED THEN UPDATE SET A.FLOW_STATUS=B.FLOW_STATUS, A.MP_SN_LIST=B.MP_SN_LIST,A.FAILURE_CODE=B.FAILURE_CODE, A.OP_TIME=SYSDATE  ");
		sf.append("     WHEN NOT MATCHED THEN INSERT (A.APP_NO, A.TERMINAL_ID, A.FLOW_NODE, A.FLOW_STATUS, A.MP_SN_LIST,FAILURE_CODE, A.OP_TIME) ");
		sf.append("          VALUES (B.APP_NO, B.TERMINAL_ID,B.FLOW_NODE,B.FLOW_STATUS,B.MP_SN_LIST,B.FAILURE_CODE, SYSDATE) " );
		getJdbcDAO().update(sf.toString());
	}
	
	/**
	 * 更新测量点明细
	 * @param appNo
	 * @param terminalId
	 * @param flowNode
	 * @param sendTmnlFailure
	 */
	public void updateUpdateMpSnList(String appNo, String terminalId, String flowNode, String sendTmnlFailure) {
		String sql = "UPDATE I_FLOW_PROCESS SET MP_SN_LIST =? AND OP_TIME =SYSDATE WHERE APP_NO=? AND TERMINAL_ID=? AND FLOW_NODE=?";
		getJdbcDAO().update(sql, new Object[]{sendTmnlFailure, appNo, terminalId, flowNode});
	}
	
	/**
	 * 查看流程是否成功
	 * @param appNo
	 * @param tmnlAssetNo
	 * @return
	 */
	public List<?> checkSuccessOrFaliure(String appNo, String tmnlAssetNo) {
		String sql = " SELECT * FROM I_FLOW_PROCESS T WHERE T.APP_NO=? AND TERMINAL_ID=? AND FLOW_STATUS =0";
		List<?> list = getJdbcDAO().queryForList(sql, new Object[]{appNo, tmnlAssetNo});
		return list;
	}
	
	/**
	 * 更新运行电能表c_meter中的，注册状态REG_STATUE
	 * @param tmnlAssetNo 
	 * @param mpSn 
	 */
	public void updateMeterRegStatus(String tmnlAssetNo, String mpSn) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE C_METER SET REG_STATUS = 1 WHERE TMNL_ASSET_NO = ? AND REG_SN = ?");
		List<String> param = new ArrayList<String>();
		param.add(tmnlAssetNo);
		param.add(mpSn);
		
		log.debug("updateMeterRegStatus:" + sql.toString());
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 查询为注册的电表的测量点
	 * @param tmnlAssetNo 
	 * @param mpSn 
	 */
	public List<?> getMpSnsByTmnlAndStatus(String tmnlAssetNo, String regStatus) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT E.ID, E.TMNL_ASSET_NO, E.TERMINAL_ADDR, E.COMM_ADDR, E.MP_SN, E.METER_ID ");
		sql.append("   FROM E_DATA_MP E, C_METER C ");
		sql.append("  WHERE E.TMNL_ASSET_NO = ? ");
		sql.append("     AND E.IS_VALID = 1 ");
		sql.append("    AND E.MP_SN <> 0 ");
		sql.append("    AND E.METER_ID = C.METER_ID ");
		sql.append("    AND (C.REG_STATUS IS NULL OR C.REG_STATUS = ?) ");
		sql.append("  ORDER BY E.MP_SN ASC ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo, regStatus});
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 取得任务号
	 * @param tmnlAssetNo
	 * @param templateId
	 * @return
	 */
	public List<?> getTTmnlTaskByTmnlNoTemplateId(String tmnlAssetNo, String templateId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM T_TMNL_TASK T WHERE T.TMNL_ASSET_NO=? AND T.TEMPLATE_ID=? ORDER BY T.TASK_NO DESC");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo, templateId});
		return list;
	}
	
	/**
	 * 取得任务号
	 * @param tmnlAssetNo
	 * @param templateId
	 * @return
	 */
	public String getTaskNoByTmnlNo(String tmnlAssetNo) {
		String taskNo = "";
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT MAX(T.TASK_NO) AS TASK_NO FROM T_TMNL_TASK T WHERE T.TMNL_ASSET_NO=? ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		if (null != list && list.size() > 0) {
			Map<?,?> map = (Map<?,?>) list.get(0);
			taskNo = StringUtil.removeNull(map.get("TASK_NO"));
		}
		return taskNo;
	}
	
	/**
	 * 取得额定电压电流
	 * @param contract_cap
	 * @param wiring_mode
	 * @param meas_mode
	 * @param ct
	 * @param pt
	 * @return
	 */
	public String getVoltCurr(final String contract_cap, final String wiring_mode,final String meas_mode, final String ct, final String pt) {
		Object obj = this.getJdbcDAO().execute(new ConnectionCallback() {
			public Object doInConnection(Connection conn) {
				CallableStatement cstmt = null;
				String result = "";
				try {
					cstmt = conn.prepareCall("{?=call getVoltCurr(?,?,?,?,?)}");
					cstmt.registerOutParameter(1, Types.VARCHAR);
					cstmt.setString(2, contract_cap);
					cstmt.setString(3, wiring_mode);
					cstmt.setString(4, meas_mode);
					cstmt.setString(5, ct);
					cstmt.setString(6, pt);
					cstmt.execute();
					result = cstmt.getString(1);
				} catch (Exception e) {
					ExceptionHandle.handleUnCheckedException(e);
				} finally {
					if (cstmt != null) {
						try {
							cstmt.close();
						} catch (SQLException e) {
						}
					}
				}
				result = result == null ? "" : result.trim();
				return result;
			}
		});
		return obj.toString();
	}

	/**
	 * 根据用户编号取终端信息
	 * @param consNo
	 * @return
	 */
	public List<?> getTmnlByConsNo(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from vw_tmnl_run t where t.cons_no = ? ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{consNo});	
		log.debug("getTmnlByConsNo:" + sql.toString()) ;
		if(list == null || list.size()== 0) {
			return null;
		}
		return list;
	}
	
	/**
	 * 采集器变更
	 * @param appNo
	 * @return
	 */
	public List<?> getFrmCollector(String appNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * from r_other_task_sr t WHERE t.chg_mode ='04' AND t.app_no=? ");
		List<?> list = this.getJdbcYXDAO().queryForList(sql.toString(),new Object[]{appNo});	
		log.debug("getFrmCollector:" + sql.toString()) ;
		return list;
	}
	
	/**
	 * 删除运行采集器信息
	 * @param collectorId
	 * @return
	 */
	public void delRexecOtherDev(String collectorId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from r_exec_other_dev t where t.COLLECTOR_ID=? ");
		this.getJdbcDAO().update(sql.toString(),new Object[]{collectorId});	
		log.debug("delRexecOtherDev:" + sql.toString()) ;
	}

	/**
	 * 获取采集库连接状态
	 * @return
	 */
	public boolean getDBStatus() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM DUAL");
		boolean status = true;
		try{
			this.getJdbcDAO().update(sql.toString());	
		}catch (Exception e){
			status = false;
			ExceptionHandle.handleUnCheckedException(e);
		}	
		return status;
	}

	/**
	 * 获取中间库连接状态
	 * @return
	 */
	public boolean getMidDBStatus() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM DUAL");
		boolean status = true;
		try{
			this.getJdbcYXDAO().update(sql.toString());
		}catch (Exception e){
			status = false;
			ExceptionHandle.handleUnCheckedException(e);
		}	
		return status;
	}

	/**
	 * 更新E_DATA_MP中的COLL_OBJ_ID
	 */
	public void updateEdataMpByTmnlIdAndMeterId(String collObjId,
			String meterId, String tmnlId) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE E_DATA_MP SET COLL_OBJ_ID = ? WHERE METER_ID = ? AND TMNL_ASSET_NO = ?");
		List<String> param = new ArrayList<String>();
		param.add(collObjId);
		param.add(meterId);
		param.add(tmnlId);
		
		log.debug("updateEdataMpByTmnlIdAndMeterId:" + sql.toString());
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 更新R_COLL_OBJ中的COLL_OBJ_ID
	 */
	public void updateRcollObjByCpNoAndMeterId(String collObjId,
			String meterId, String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE R_COLL_OBJ SET COLL_OBJ_ID = ? WHERE METER_ID = ? AND CP_NO = ?");
		List<String> param = new ArrayList<String>();
		param.add(collObjId);
		param.add(meterId);
		param.add(cpNo);
		
		log.debug("updateRcollObjByCpNoAndMeterId:" + sql.toString());
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 更新I_TMNL_TASK中的业扩申请编号
	 */
	public void updateWkstAppNo(String appNo, String flag) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE I_TMNL_TASK SET WKST_APP_NO = ? WHERE APP_NO = ?");
		List<String> param = new ArrayList<String>();
		param.add(flag);
		param.add(appNo);
		
		log.debug("updateWkstAppNo:" + sql.toString());
		getJdbcDAO().update(sql.toString(),param.toArray());
	}

	/**
	 * 取最大的测量点号
	 */
	public String getMaxMpSn(String tmnlAssetNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(T.MP_SN)+1 AS MAX_MP_SN FROM E_DATA_MP T WHERE T.TMNL_ASSET_NO = ?");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{tmnlAssetNo});
		Map<?, ?> map = (Map<?, ?>) list.get(0);
		String maxMpSn = String.valueOf(map.get("MAX_MP_SN")==null ? 1 : map.get("MAX_MP_SN"));
		return maxMpSn;
	}

	/**
	 * 删除R_CONS_TMNL_RELA中的用户终端关系
	 */
	public void delRConsTmnlRelaByConsNo(String consNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_CONS_TMNL_RELA A   ");
		sql.append("	WHERE A.CONS_NO=?  ");
		log.debug("delRConsTmnlRelaByConsNo:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{consNo});
	}

	/**
	 * 根据METER_ID取CONS_NO
	 */
	public List<?> getConsNoByMeterId(String meterId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT T.CONS_NO FROM C_MP T, C_METER_MP_RELA C WHERE T.MP_ID = C.MP_ID AND C.METER_ID = ? ");
		List<?> list = getJdbcDAO().queryForList(sql.toString(),new Object[]{meterId});
		return list;
	}

	public void deleteRCollObj(String cpNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE R_COLL_OBJ A   ");
		sql.append("	WHERE A.CP_NO=?  ");
		log.debug("deleteRCollObj:" + sql.toString()) ;
		getJdbcDAO().update(sql.toString(), new Object[]{cpNo});
	}

}