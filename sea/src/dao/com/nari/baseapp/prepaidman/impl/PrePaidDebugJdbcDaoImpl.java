package com.nari.baseapp.prepaidman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.prepaidman.PPDebugTmnlInfoBean;
import com.nari.baseapp.prepaidman.PPDebugTotalBean;
import com.nari.baseapp.prepaidman.PPDebugTotalMpBean;
import com.nari.baseapp.prepaidman.PPDebugTotalSetBean;
import com.nari.baseapp.prepaidman.PPDebugUserInfoBean;
import com.nari.baseapp.prepaidman.PPDebugUserStatusInfoBean;
import com.nari.baseapp.prepaidman.PPDebugWFeectrlFlowBean;
import com.nari.baseapp.prepaidman.PrePaidDebugJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.elementsdata.EDataTotal;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.FlowCodeNodeUtil;
import com.nari.util.NodeTypeUtils;
import com.nari.util.SendStatusCodeUtil;

/**
 * 预付费投入调试DAO实现类
 * @author 姜炜超
 */
public class PrePaidDebugJdbcDaoImpl extends JdbcBaseDAOImpl implements PrePaidDebugJdbcDao {
	//定义日志
	private static final Logger logger = Logger.getLogger(PrePaidDebugJdbcDaoImpl.class);
	
	/**
	 * 查询预付费投入调试用户信息，查询按钮调用
	 * @param control 控制方式
	 * @param buyOrder 购电单号 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param consNo 用户编号
	 * @param orgNo 供电单位编号
	 * @param tmnlAddr 终端地址
	 * @param debugStatus 调试状态
	 * @param user 操作员信息
	 * @param start 开始
	 * @param limit 结束
	 * @return Page<PPDebugUserInfoBean>
	 */
	public Page<PPDebugUserInfoBean> findUserInfo(int control, String buyOrder, String startDate,
			String endDate, String consNo, String[] orgNo, String tmnlAddr, String debugStatus, 
			PSysUser user, long start, int limit){
		Page<PPDebugUserInfoBean> psc = null;
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"SELECT W.FEECTRL_ID,O.ORG_NAME,O.P_ORG_NO,\n" +
			"       W.ORG_NO,\n" + 
			"       W.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,W.TMNL_ASSET_NO,R.CIS_ASSET_NO,R.TERMINAL_ID,R.PROTOCOL_CODE,R.CP_NO,\n" + 
			"       W.TOTAL_NO, W.BUY_FLAG,W.BUY_VALUE, W.ALARM_VALUE, W.JUMP_VALUE, W.BUY_ORDER,W.METER_ID,W.TURN_FLAG,\n" +
			"       W.BUY_VALUE_UNIT, W.ALARM_VALUE_UNIT, W.JUMP_VALUE_UNIT,W.REFRESH_FLAG,\n"+
			"       DECODE(W.STATUS_CODE,'03','未调试','04','调试成功','05','调试失败','08','调试中','状态未知') AS STATUS_CODE\n"+
			"  FROM W_FEECTRL W, O_ORG O, C_CONS C, R_TMNL_RUN R\n" + 
			" WHERE W.ORG_NO = O.ORG_NO\n" + 
			"   AND W.CONS_NO = C.CONS_NO\n" + 
			"   AND W.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n" +
			"   AND W.BUY_FLAG = ?\n"; 
			//"   AND W.BUY_DATE >= TO_DATE(?, 'YYYY-MM-DD')\n" + 
			//"   AND W.BUY_DATE < TO_DATE(?, 'YYYY-MM-DD')+1\n" ;
		
		obj.add(control);
		//obj.add(startDate);
		//obj.add(endDate);
		if(!"*".equals(debugStatus)){//全部，前台传入*
			sql = sql + "AND W.STATUS_CODE = ?\n";
			obj.add(debugStatus);
		}

		sql = sql + "AND W.ORG_NO IN (\n";//供电单位
		for(int i = 0 ; i < orgNo.length ;i++){
			sql = sql + "?,";
			obj.add(orgNo[i]);
		}
		sql = sql.substring(0, sql.length()-1) + ")\n";
		
		if(orgNo ==null || orgNo.length<=0) {
			if(null != consNo && 0 < consNo.length()){//客户编号
				consNo = "%"+consNo+"%";
				sql = sql + "AND W.CONS_NO LIKE ?\n";
				obj.add(consNo);
			}
		}
		if(buyOrder != null && 0 < buyOrder.length()){//购电单号
			buyOrder = "%"+buyOrder+"%";
			sql+= "   AND W.BUY_ORDER LIKE ?\n";
			obj.add(buyOrder);
		}
		if(tmnlAddr != null && 0 < tmnlAddr.length()){//终端地址
			tmnlAddr = "%"+tmnlAddr+"%";
			sql+= "   AND R.TERMINAL_ADDR LIKE ?\n";
			obj.add(tmnlAddr);
		}
		logger.debug(sql);
		psc = super.pagingFind(sql, start, limit, new UserInfoMapper(), obj.toArray());
		if(null == psc){
			psc = new Page<PPDebugUserInfoBean>();
		}
		return psc;
	}
	
	/**
	 * 查询预付费投入调试用户信息，供全选选中调用
	 * @param control 控制方式
	 * @param buyOrder 购电单号 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param consNo 用户编号
	 * @param orgNo 供电单位编号
	 * @param tmnlAddr 终端地址
	 * @param debugStatus 调试状态
	 * @param user 操作员信息
	 * @return List<PPDebugUserInfoBean>
	 */
	@SuppressWarnings("unchecked")
	public List<PPDebugUserInfoBean> findUserInfoByAll(int control, String buyOrder, String startDate,
			String endDate, String consNo, String[] orgNo, String tmnlAddr, String debugStatus, 
			PSysUser user){
		List<PPDebugUserInfoBean> list = null;
		List<Object> obj = new ArrayList<Object>();
		String sql = 
			"SELECT W.FEECTRL_ID,O.ORG_NAME,\n" +
			"       W.ORG_NO,\n" + 
			"       W.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,W.TMNL_ASSET_NO,R.CIS_ASSET_NO,R.TERMINAL_ID,R.PROTOCOL_CODE,\n" + 
			"       W.TOTAL_NO, W.BUY_VALUE, W.ALARM_VALUE, W.JUMP_VALUE, W.BUY_ORDER,W.METER_ID,W.TURN_FLAG,\n" +
			"       W.BUY_VALUE_UNIT, W.ALARM_VALUE_UNIT, W.JUMP_VALUE_UNIT,W.REFRESH_FLAG,\n"+
			"       DECODE(W.STATUS_CODE,'03','未调试','04','调试成功','05','调试失败','08','调试中','状态未知') AS STATUS_CODE\n"+
			"  FROM W_FEECTRL W, O_ORG O, C_CONS C, R_TMNL_RUN R\n" + 
			" WHERE W.ORG_NO = O.ORG_NO\n" + 
			"   AND W.CONS_NO = C.CONS_NO\n" + 
			"   AND W.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n" +
			"   AND W.BUY_FLAG = ?\n" + 
			"   AND W.BUY_DATE >= TO_DATE(?, 'YYYY-MM-DD')\n" + 
			"   AND W.BUY_DATE < TO_DATE(?, 'YYYY-MM-DD')+1\n" ;
		
		obj.add(control);
		obj.add(startDate);
		obj.add(endDate);
		if(!"*".equals(debugStatus)){//全部，前台传入*
			sql = sql + "AND W.STATUS_CODE = ?\n";
			obj.add(debugStatus);
		}

		sql = sql + "AND W.ORG_NO IN (\n";//供电单位
		for(int i = 0 ; i < orgNo.length ;i++){
			sql = sql + "?,";
			obj.add(orgNo[i]);
		}
		sql = sql.substring(0, sql.length()-1) + ")\n";
		
		if(null != consNo && 0 < consNo.length()){//客户编号
			consNo = "%"+consNo+"%";
			sql = sql + "AND W.CONS_NO LIKE ?\n";
			obj.add(consNo);
		}
		if(buyOrder != null && 0 < buyOrder.length()){//购电单号
			buyOrder = "%"+buyOrder+"%";
			sql+= "   AND W.BUY_ORDER LIKE ?\n";
			obj.add(buyOrder);
		}
		if(tmnlAddr != null && 0 < tmnlAddr.length()){//购电单号
			tmnlAddr = "%"+tmnlAddr+"%";
			sql+= "   AND R.TERMINAL_ADDR LIKE ?\n";
			obj.add(tmnlAddr);
		}
		logger.debug(sql);
		list = super.getJdbcTemplate().query(sql, obj.toArray() , new UserInfoMapper());
		if(null == list){
			list = new ArrayList<PPDebugUserInfoBean>();
		}
		return list;
	}
	
	/**
	 * 供电单位树。
	 * @param staffNo 操作员编号
	 * @param orgNo 供电单位编号
	 * @param orgType 供电单位类别
	 * @return List<OOrg>
	 */
	@SuppressWarnings("unchecked")
	public List<OOrg> findOrgTree(String staffNo, String orgNo, String orgType){
		if(null == orgType){
			return new ArrayList<OOrg>();
		}
		List<OOrg> list  = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ORG.ORG_NO, ORG.ORG_NAME, ORG.ORG_TYPE\n");
		
		if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){//省级用户
		    sql.append("FROM O_ORG ORG,\n")
			   .append(" (SELECT ORG_NO, ORG_TYPE\n")
			   .append("  FROM P_ACCESS_ORG\n")
			   .append("  WHERE STAFF_NO = ?\n")
			   .append("  AND ORG_TYPE = ?) ACC\n")
			   .append("WHERE ORG.ORG_NO = ACC.ORG_NO\n")
			   .append("AND ORG.P_ORG_NO = ? ORDER BY ORG.ORG_NO");
		    logger.debug(sql);
		    list = super.getJdbcTemplate().query(sql.toString(), new Object[] {staffNo,NodeTypeUtils.NODETYPE_ORG03, orgNo}, new OrgInfoMapper());		
		}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){//地市级用户
			sql.append("FROM O_ORG ORG,\n")
			   .append(" (SELECT ORG_NO, ORG_TYPE\n")
			   .append("  FROM P_ACCESS_ORG\n")
			   .append("  WHERE STAFF_NO = ?\n")
			   .append("  AND ORG_TYPE = ?) ACC\n")
			   .append("WHERE ORG.ORG_NO = ACC.ORG_NO\n")
			   .append("AND ORG.P_ORG_NO = ? ORDER BY ORG.ORG_NO");
			logger.debug(sql);
		    list = super.getJdbcTemplate().query(sql.toString(), new Object[] {staffNo,NodeTypeUtils.NODETYPE_ORG04, orgNo}, new OrgInfoMapper());		
		}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){//区县级用户
			sql.append("FROM O_ORG ORG,\n")
			   .append(" (SELECT ORG_NO, ORG_TYPE\n")
			   .append("  FROM P_ACCESS_ORG\n")
			   .append("  WHERE STAFF_NO = ?\n")
			   .append("  AND ORG_TYPE = ? OR ORG_TYPE = ?) ACC\n")
			   .append("WHERE ORG.ORG_NO = ACC.ORG_NO\n")
			   .append("AND ORG.P_ORG_NO = ? ORDER BY ORG.ORG_NO");
			logger.debug(sql);
		    list = super.getJdbcTemplate().query(sql.toString(), new Object[] {staffNo, NodeTypeUtils.NODETYPE_ORG05, NodeTypeUtils.NODETYPE_ORG06, orgNo}, new OrgInfoMapper());		
		}else if(NodeTypeUtils.NODETYPE_ORG05.equals(orgType) || NodeTypeUtils.NODETYPE_ORG06.equals(orgType)){//供电所级用户
			sql.append("FROM O_ORG ORG\n")
			   .append("AND ORG.ORG_NO = ?");
			logger.debug(sql);
		    list = super.getJdbcTemplate().query(sql.toString(), new Object[] {orgNo}, new OrgInfoMapper());		
		}else{
			
		}
		if(null == list){
			return new ArrayList<OOrg>();
		}
		return list;
	}
	
	/**
	 * 根据购电单号查询预付费参数详细信息，目前按照设计，只会出现一条记录，为防止以后出现变动，这里用分页查询
	 * @param feeCtrlId 费控id
	 * @param orderNo 购电单号
	 * @return PPDebugUserInfoBean
	 */
	public PPDebugUserInfoBean findOrderParamInfo(Long feeCtrlId,String buyOrder){
		String sql = 
			"SELECT W.FEECTRL_ID,O.ORG_NAME,\n" +
			"       W.ORG_NO,\n" + 
			"       W.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       W.TMNL_ASSET_NO,R.CIS_ASSET_NO,W.TOTAL_NO,\n" + 
			"       W.BUY_VALUE_UNIT, W.ALARM_VALUE_UNIT, W.JUMP_VALUE_UNIT,\n"+
			"       W.BUY_VALUE, W.BUY_ORDER, W.ALARM_VALUE, W.JUMP_VALUE, W.METER_ID, W.TURN_FLAG\n" + 
			"  FROM W_FEECTRL W, O_ORG O, C_CONS C, R_TMNL_RUN R\n" + 
			" WHERE W.ORG_NO = O.ORG_NO\n" + 
			"   AND W.CONS_NO = C.CONS_NO\n" + 
			"   AND W.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n" +
		    "   AND W.FEECTRL_ID =? AND W.BUY_ORDER = ?\n";
		logger.debug(sql);
		List<PPDebugUserInfoBean> list = super.pagingFindList(sql, 0, 2, new OrderInfoMapper(), new Object[]{feeCtrlId,buyOrder});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据购电单号、终端id、电表id查询终端工况
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return PPDebugTmnlInfoBean
	 */
	public PPDebugTmnlInfoBean findTmnlInfo(String appNo, String terminalId, String meterId){
		String sql = 
			"SELECT TMNL.TMNL_ASSET_NO,\n" +
			"       TMNL.TERMINAL_ID,\n" + 
			"       TMNL.CP_NO,\n" + 
			"       CP.CP_ADDR,\n" + 
			"       DECODE(RT.IS_ONLINE, 0, 0, 1, 1, 2) AS IS_ONLINE,\n" + 
			"       TMNL.PS_ENSURE_FLAG,\n" + 
			"       DECODE(TMNL.PS_ENSURE_FLAG, '0', '不保电', '1', '保电', '状态未知') AS PAULPOWER,\n" + 
			"       DECODE(TMP.FLOW_NODE_STATUS,\n" + 
			"              '0',\n" + 
			"              '通讯正常',\n" + 
			"              '1',\n" + 
			"              '通讯不正常',\n" + 
			"              '状态未知') AS COMMSTATUS\n" + 
			"  FROM R_TMNL_RUN TMNL,\n" + 
			"       A_TMNL_REAL_TIME RT,\n" + 
			"       R_CP CP,\n" + 
			"       (SELECT FEEFLOW.TERMINAL_ID, FEEFLOW.FLOW_NODE_STATUS\n" + 
			"          FROM W_FEECTRL_FLOW FEEFLOW\n" + 
			"         WHERE FEEFLOW.TERMINAL_ID = ?\n" + 
			"           AND FEEFLOW.APP_NO = ?\n" + 
			"           AND FEEFLOW.METER_ID = ?\n" + 
			"           AND FEEFLOW.FLOW_NO_CODE = ?) TMP\n" + 
			" WHERE TMNL.TMNL_ASSET_NO = RT.TMNL_ASSET_NO\n" + 
			"   AND TMNL.CP_NO = CP.CP_NO\n" + 
			"   AND TMNL.TERMINAL_ID = TMP.TERMINAL_ID(+)\n" + 
			"   AND TMNL.TERMINAL_ID = ?";

		logger.debug(sql);
		List<PPDebugTmnlInfoBean> list = super.pagingFindList(sql, 0, 2, new TmnlInfoMapper(), 
				new Object[]{terminalId,appNo,meterId,FlowCodeNodeUtil.FIR_TMNL_READ,terminalId});
		if(list == null || list.size() ==0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 更新预付费参数
	 * @param feeCtrlId 费控id
	 * @param orderNo 购电单号
	 * @param jumpValue
	 * @param buyValue
	 * @param alarmValue
	 * @param turnFlag
	 * @param totalNo
	 * @return 
	 * @throws Exception 
	 */
	public void updatePPDPara(Long feeCtrlId,String buyOrder,Integer jumpValue,
			Integer buyValue, Integer alarmValue, String turnFlag, Short totalNo){
		StringBuffer sb = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		sb.append("UPDATE W_FEECTRL FEE SET FEE.BUY_ORDER = ?, FEE.BUY_VALUE = ? , FEE.JUMP_VALUE = ? , FEE.ALARM_VALUE = ?\n")
		  .append(", FEE.TURN_FLAG = ?, FEE.TOTAL_NO = ? WHERE FEE.FEECTRL_ID = ?");
		logger.debug(sb);
		list.add(buyOrder);
		list.add(buyValue != null ? buyValue.intValue() : null);
		list.add(jumpValue != null ? jumpValue.intValue() : null);
		list.add(alarmValue != null ? alarmValue.intValue() : null);
		list.add(turnFlag);
		list.add(totalNo);
		list.add(feeCtrlId);
		super.updatePara(sb.toString(),list.toArray());
	}
	
	/**
	 * 查询预付费用户调试状态具体流程信息
	 * @param appNo 购电单号
	 * @param terminalId 终端id
	 * @param meterId 电表id
	 * @return List<PPDebugUserStatusInfoBean>
	 */
	@SuppressWarnings("unchecked")
	public List<PPDebugUserStatusInfoBean> findUserDebugInfo(String appNo, String terminalId, String meterId){
		String sql = 
			"SELECT FLOW.TERMINAL_ID,\n" +
			"       TMNL.TMNL_ASSET_NO,\n" + 
			"       TMNL.TERMINAL_ADDR,\n" + 
			"       FLOW.METER_ID,\n" + 
			"       METER.ASSET_NO,\n" + 
			"       DECODE(RT.IS_ONLINE, 0, '不在线', 1, '在线', '状态未知') AS IS_ONLINE,\n" + 
			"       FLOW.FLOW_NO_CODE,\n" + 
			"       VWFLOW.FLOW_NODE_NAME,\n" + 
			"       FLOW.FLOW_NODE_STATUS,\n" + 
			"       FLOW.ERR_CAUSE,\n" + 
			"       FLOW.OP_TIME\n" + 
			"  FROM W_FEECTRL_FLOW    FLOW,\n" + 
			"       R_TMNL_RUN        TMNL,\n" + 
			"       C_METER           METER,\n" + 
			"       A_TMNL_REAL_TIME  RT,\n" + 
			"       VW_FLOW_NODE_CODE VWFLOW\n" + 
			" WHERE FLOW.TERMINAL_ID = TMNL.TERMINAL_ID\n" + 
			"   AND FLOW.METER_ID = METER.METER_ID\n" + 
			"   AND TMNL.TMNL_ASSET_NO = RT.TMNL_ASSET_NO\n" + 
			"   AND FLOW.FLOW_NO_CODE = VWFLOW.FLOW_NODE_CODE(+)\n" + 
			"   AND FLOW.TERMINAL_ID = ?\n" + 
			"   AND FLOW.APP_NO = ?\n" + 
			"   AND FLOW.METER_ID = ?";

		logger.debug(sql);
		List<PPDebugUserStatusInfoBean> list = super.getJdbcTemplate().query(sql,
				new Object[] {terminalId,appNo,meterId}, new UserStatusInfoMapper());
		if(list == null) {
			return new ArrayList<PPDebugUserStatusInfoBean>();
		}
		return list;
	}
	
	/**
	 * 根据终端查询总加组列表，预付费参数显示调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<EDataTotal>
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<EDataTotal> findParamTotalInfo(String tmnlAssetNo){
		String sql = 
			"SELECT TOTAL.TMNL_ASSET_NO, TOTAL.TOTAL_NO\n" +
			"  FROM E_DATA_TOTAL TOTAL\n" + 
			" WHERE TOTAL.TMNL_ASSET_NO = ?\n" + 
			"   AND TOTAL.IS_VALID = 1\n" + 
			" ORDER BY TOTAL.TOTAL_NO ASC";

		logger.debug(sql);
		List<EDataTotal> list = super.getJdbcTemplate().query(sql,
				new Object[] {tmnlAssetNo}, new TotalInfoMapper());
		if(list == null) {
			return new ArrayList<EDataTotal>();
		}
		return list;
	}
	
	/**
	 * 修改费控主表状态
	 * @Param feeCtrlId 费控id
	 * @param status 状态
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlStatus(Long feeCtrlId, String status){
        String sql;
        if(status==SendStatusCodeUtil.PARAM_SEND_SUCCESS)
        	sql="UPDATE W_FEECTRL SET STATUS_CODE = ?, SUCCESS_TIME = SYSDATE WHERE FEECTRL_ID = ?";
        else if(status==SendStatusCodeUtil.PARAM_SEND_FAILED)
        	sql="UPDATE W_FEECTRL SET STATUS_CODE = ? WHERE FEECTRL_ID = ?";
        else
        	return;
		logger.debug(sql);
		super.updatePara(sql,new Object[]{status, feeCtrlId});
	}

	/**
	 * 修改或新增费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void saveOrUpdateWFeeCtrlFlow(PPDebugWFeectrlFlowBean bean){
		List<Object> list = new ArrayList<Object>();
		String sql = 
			"MERGE INTO W_FEECTRL_FLOW DEST\n" +
			"USING (SELECT ? AS APP_NO,\n" + 
			"              ? AS TERMINAL_ID,\n" + 
			"              ? AS METER_ID,\n" + 
			"              ? AS FLOW_NO_CODE,\n" + 
			"              ? AS FLOW_NODE_STATUS,\n" + 
			"              ? AS ERR_CAUSE,\n" + 
			"              ? AS OP_TIME\n" + 
			"         FROM DUAL) SRC\n" + 
			"ON (DEST.APP_NO = SRC.APP_NO AND DEST.TERMINAL_ID = SRC.TERMINAL_ID AND DEST.METER_ID = SRC.METER_ID AND DEST.FLOW_NO_CODE = SRC.FLOW_NO_CODE)\n" + 
			"WHEN MATCHED THEN\n" + 
			"  UPDATE\n" + 
			"     SET DEST.FLOW_NODE_STATUS = SRC.FLOW_NODE_STATUS,\n" + 
			"         DEST.ERR_CAUSE        = SRC.ERR_CAUSE,\n" + 
			"         DEST.OP_TIME          = SRC.OP_TIME\n" + 
			"WHEN NOT MATCHED THEN\n" + 
			"  INSERT\n" + 
			"    (APP_NO,\n" + 
			"     TERMINAL_ID,\n" + 
			"     METER_ID,\n" + 
			"     FIRST_FLAG,\n" + 
			"     FLOW_NO_CODE,\n" + 
			"     FLOW_NODE_STATUS,\n" + 
			"     OP_TIME,\n" + 
			"     ERR_CAUSE)\n" + 
			"  VALUES\n" + 
			"    (?, ?, ?, ?, ?, ?, ?, ?)";
		
		logger.debug(sql);
		list.add(bean.getAppNo());
		list.add(bean.getTerminalId());
		list.add(bean.getMeterId());
		list.add(bean.getFlowNoCode());
		list.add(bean.getFlowNodeStatus());
		list.add(null);
		list.add(bean.getOpTime());
		
		list.add(bean.getAppNo());
		list.add(bean.getTerminalId());
		list.add(bean.getMeterId());
		list.add(Short.valueOf("1"));
		list.add(bean.getFlowNoCode());
		list.add(bean.getFlowNodeStatus());
		list.add(bean.getOpTime());
		list.add(null);
		
		super.updatePara(sql, list.toArray());
	}
	
	/**
	 * 修改费控流程表记录
	 * @param bean
	 * @return 
	 * @throws Exception 
	 */
	public void updateWFeeCtrlFlowStatus(PPDebugWFeectrlFlowBean bean){
		List<Object> list = new ArrayList<Object>();
		String sql = 
			"UPDATE W_FEECTRL_FLOW\n" +
			"   SET FLOW_NODE_STATUS = ?, ERR_CAUSE = ?\n" + 
			" WHERE APP_NO = ?\n" + 
			"   AND TERMINAL_ID = ?\n" + 
			"   AND METER_ID = ?\n" + 
			"   AND FLOW_NO_CODE = ?";

		logger.debug(sql);
		list.add(bean.getFlowNodeStatus());
	    list.add(bean.getErrCause());
		list.add(bean.getAppNo());
		list.add(bean.getTerminalId());
		list.add(bean.getMeterId());
		list.add(bean.getFlowNoCode());
		super.updatePara(sql, list.toArray());
	}
	
	/**
	 * 根据终端查询总加组详细列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<PPDebugTotalBean>
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List<PPDebugTotalBean> findTotalInfo(String tmnlAssetNo){
		String sql =
			"SELECT DISTINCT TOTAL_NO, TOTAL_NAME, TMNL_ASSET_NO\n" +
			"  FROM T_TMNL_TOTAL_INFO\n" + 
			" WHERE TMNL_ASSET_NO = ? AND TOTAL_NO IS NOT NULL ORDER BY TOTAL_NO ASC";
		logger.debug(sql);
		List<PPDebugTotalBean> list = super.getJdbcTemplate().query(sql,
				new Object[] {tmnlAssetNo}, new TotalSetInfoMapper());
		if(list == null) {
			return new ArrayList<PPDebugTotalBean>();
		}
		return list;
	}
	
	/**
	 * 根据终端查询总加组所属测量点列表，总加组设置调用
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组编号
	 * @return List<PPDebugTotalMpBean>
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List<PPDebugTotalMpBean> findTotalMpInfo(String tmnlAssetNo, Short totalNo){
		String sql =
			"SELECT TOTAL.MP_SN,\n" +
			"       VW.DATA_SRC AS MP_TYPE,\n" + 
			"       TOTAL.CALC_FLAG,\n" + 
			"       DECODE(TOTAL.CALC_FLAG,NULL,'-请选择-','0','加','1','减','-请选择-') AS CALC_FLAG_NAME, \n"+
			"       TOTAL.PULSE_ATTR,\n" + 
			"       DECODE(TOTAL.PULSE_ATTR,NULL,'-请选择-','1','正向','2','反向','-请选择-') AS PULSE_ATTR_NAME, \n"+
			"       CMP.MP_NO AS CMP_NO,\n" + 
			"       CMP.MP_NAME AS CMP_NAME,TOTAL.TOTAL_NAME\n" + 
			"  FROM T_TMNL_TOTAL_INFO TOTAL,\n" + 
			"       E_DATA_MP         EMP,\n" + 
			"       VW_DATA_SRC       VW,\n" + 
			"       C_METER_MP_RELA   MMR,\n" + 
			"       C_MP              CMP\n" + 
			" WHERE TOTAL.TMNL_ASSET_NO = EMP.TMNL_ASSET_NO\n" + 
			"   AND TOTAL.MP_SN = EMP.MP_SN\n" + 
			"   AND EMP.DATA_SRC = VW.DATA_SRC_NO\n" + 
			"   AND EMP.METER_ID = MMR.METER_ID(+)\n" + 
			"   AND MMR.MP_ID = CMP.MP_ID(+)\n" + 
			"   AND TOTAL.TMNL_ASSET_NO = ?\n" + 
			"   AND TOTAL.TOTAL_NO = ? ORDER BY TOTAL.MP_SN ASC";

		logger.debug(sql);
		List<PPDebugTotalMpBean> list = super.getJdbcTemplate().query(sql,
				new Object[] {tmnlAssetNo,totalNo}, new TotalMpSetInfoMapper());
		if(list == null) {
			return new ArrayList<PPDebugTotalMpBean>();
		}
		return list;
	}
	
	/**
	 * 根据终端查询终端下的测量点列表，总加组设置调用，新建调用
	 * @param tmnlAssetNo 终端资产编号
	 * @return List<PPDebugTotalMpBean>
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List<PPDebugTotalMpBean> findTmnlMpInfo(String tmnlAssetNo){
		String sql = 
			"SELECT EMP.MP_SN,\n" +
			"       VW.DATA_SRC AS MP_TYPE,\n" + 
			"       CMP.MP_NO AS CMP_NO,\n" + 
			"       CMP.MP_NAME AS CMP_NAME\n" + 
			"  FROM E_DATA_MP EMP, VW_DATA_SRC VW, C_METER_MP_RELA MMR, C_MP CMP\n" + 
			" WHERE EMP.DATA_SRC = VW.DATA_SRC_NO\n" + 
			"   AND EMP.METER_ID = MMR.METER_ID(+)\n" + 
			"   AND MMR.MP_ID = CMP.MP_ID(+)\n" + 
			"   AND EMP.TMNL_ASSET_NO = ? \n" + 
			" ORDER BY EMP.MP_SN ASC";
		logger.debug(sql);
		List<PPDebugTotalMpBean> list = super.getJdbcTemplate().query(sql,
				new Object[] {tmnlAssetNo}, new TmnlMpSetInfoMapper());
		if(list == null) {
			return new ArrayList<PPDebugTotalMpBean>();
		}
		return list;
	}
	
	/**
	 * 修改总加组主表为无效（删除操作）
	 * @Param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return 
	 * @throws Exception 
	 */
	public void updateEDataTotalInvalid(String tmnlAssetNo, Short totalNo){
		if(null == tmnlAssetNo || null == totalNo){
			return;
		}
        String sql = 
        	"UPDATE E_DATA_TOTAL TOTAL\n" +
        	"   SET TOTAL.IS_VALID = 0\n" + 
        	" WHERE TOTAL.TMNL_ASSET_NO = ?\n" + 
        	"   AND TOTAL.TOTAL_NO = ?";
		logger.debug(sql);
		super.updatePara(sql,new Object[]{tmnlAssetNo, totalNo});
	}
	
	/**
	 * 新增总加组主表记录
	 * @Param PPDebugTotalSetBean 总加组信息
	 * @return 
	 * @throws Exception 
	 */
	public void addEDataTotal(PPDebugTotalSetBean bean){
		if(null == bean || null == bean.getConsNo()){
			return;
		}
		String areaCode = super.getETableAreaByConsNo(bean.getConsNo());
        String sql = 
        	"INSERT INTO E_DATA_TOTAL\n" +
        	"  (ID,\n" + 
        	"   AREA_CODE,\n" + 
        	"   ORG_NO,\n" + 
        	"   AREA_NO,\n" + 
        	"   CONS_NO,\n" + 
        	"   TMNL_ASSET_NO,\n" + 
        	"   CIS_TMNL_ASSET_NO,\n" + 
        	"   TERMINAL_ADDR,\n" + 
        	"   TOTAL_NO,\n" + 
        	"   DISABLE_DATE,\n" + 
        	"   IS_VALID)\n" + 
        	"VALUES\n" + 
        	"  (S_E_DATA_TOTAL.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		logger.debug(sql);
		super.updatePara(sql,new Object[]{areaCode, bean.getOrgNo(), bean.getAreaNo(),
				bean.getConsNo(), bean.getTmnlAssetNo(), bean.getCisTmnlAssetNo(), bean.getTmnlAddr(),
				bean.getTotalNo(),null,1});
	}
	
	/**
	 * 新增终端总加组主表记录
	 * @Param PPDebugTotalSetBean 总加组信息
	 * @return 
	 * @throws Exception 
	 */
	public void addTTmnlTotalInfo(PPDebugTotalSetBean bean){
		if(null == bean){
			return;
		}
        String sql = 
        	"INSERT INTO T_TMNL_TOTAL_INFO\n" +
        	"  (ORG_NO,\n" + 
        	"   CP_NO,\n" + 
        	"   CONS_NO,\n" + 
        	"   TMNL_ASSET_NO,\n" + 
        	"   METER_ID,\n" + 
        	"   MP_SN,\n" + 
        	"   PULSE_ATTR,\n" + 
        	"   CALC_FLAG,\n" + 
        	"   TOTAL_NO,\n" + 
        	"   TOTAL_NAME)\n" + 
        	"VALUES\n" + 
        	"  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		logger.debug(sql);
		super.updatePara(sql,new Object[]{bean.getOrgNo(), bean.getCpNo(),
				bean.getConsNo(), bean.getTmnlAssetNo(), bean.getMeterId(), bean.getMpSn(),
				bean.getPulseAttr(), bean.getCalcFlag(),bean.getTotalNo(),bean.getTotalName()});
	}
	
	/**
	 * 修改终端总加组表
	 * @Param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @param mpSn 测量点号 
	 * @return 
	 * @throws Exception 
	 */
	public void updateTTmnlTotalInfo(String pulseAttr, Short calcFlag, String totalName, 
			String tmnlAssetNo, Short totalNo, Integer mpSn){
		if(null == tmnlAssetNo || null == totalNo || null == mpSn){
			return;
		}
        String sql = 
        	"UPDATE T_TMNL_TOTAL_INFO TOTAL\n" +
        	"   SET TOTAL.PULSE_ATTR = ?, TOTAL.CALC_FLAG = ?, TOTAL.TOTAL_NAME = ?\n" + 
        	" WHERE TOTAL.TMNL_ASSET_NO = ?\n" + 
        	"   AND TOTAL.TOTAL_NO = ?\n" + 
        	"   AND TOTAL.MP_SN = ?";
		logger.debug(sql);
		super.updatePara(sql,new Object[]{pulseAttr, calcFlag, totalName, tmnlAssetNo, totalNo, mpSn});
	}
	
	/**
	 * 删除终端总加组表
	 * @Param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return 
	 * @throws Exception 
	 */
	public void dltTTmnlTotalInfo(String tmnlAssetNo, Short totalNo){
		if(null == tmnlAssetNo || null == totalNo){
			return;
		}
        String sql = 
        	"DELETE FROM T_TMNL_TOTAL_INFO TOTAL\n" +
        	" WHERE TOTAL.TMNL_ASSET_NO = ?\n" + 
        	"   AND TOTAL.TOTAL_NO = ?";
		logger.debug(sql);
		super.deletePara(sql,new Object[]{tmnlAssetNo, totalNo});
	}
	
	/**
	 * 校验终端下某个总加组是否下发投入，目前只考虑预购电下发、功率控制下发，费控下发
	 * @param tmnlAssetNo 终端资产编号
	 * @param totalNo 总加组号
	 * @return record 终端下某个总加组已经投入的记录条数
	 * @throws 
	 */
	public int checkTmnlTotal(String tmnlAssetNo, Short totalNo){
		if(null == tmnlAssetNo || null == totalNo){
			return 0;
		}
		String sql = 
			"SELECT TOTAL_NO\n" +
			"  FROM W_FEECTRL\n" + 
			" WHERE TMNL_ASSET_NO = ?\n" + 
			"   AND TOTAL_NO = ?\n" + 
			"   AND CTRL_FLAG = 1\n" + 
			"UNION\n" + 
			"SELECT TOTAL_NO\n" + 
			"  FROM W_TMNL_CTRL_STATUS\n" + 
			" WHERE TMNL_ASSET_NO = ?\n" + 
			"   AND TOTAL_NO = ?\n" + 
			"   AND PWRCTRL_FLAG = 1\n" + 
			"    OR FEECTRL_FLAG = 1";
		logger.debug(sql);
		Long record = super.pagingFindCount(sql , new Object[]{tmnlAssetNo, totalNo,tmnlAssetNo, totalNo});
		return record.intValue();
	}
	
	/**
	 * 查询预付费投入调试用户信息，查询按钮调用
	 **/
	class UserInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugUserInfoBean bean = new PPDebugUserInfoBean();
			try {
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				bean.setTotalNo(null != rs.getString("TOTAL_NO") ? rs.getShort("TOTAL_NO") : null);
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				bean.setBuyValue(null != rs.getString("BUY_VALUE") ? rs.getInt("BUY_VALUE") : null);
				bean.setAlarmValue(null != rs.getString("ALARM_VALUE") ? rs.getInt("ALARM_VALUE") : null);
				bean.setJumpValue(null != rs.getString("JUMP_VALUE") ? rs.getInt("JUMP_VALUE") : null);
				bean.setTurnFlag(rs.getString("TURN_FLAG"));
				bean.setBuyOrder(rs.getString("BUY_ORDER"));
				bean.setBuyFlag(rs.getInt("BUY_FLAG"));
				bean.setDebugStatus(rs.getString("STATUS_CODE"));
				bean.setMeterId(rs.getString("METER_ID"));
				bean.setFeeCtrlId(rs.getBigDecimal("FEECTRL_ID"));
				bean.setTerminalId(rs.getString("TERMINAL_ID"));
				bean.setCisTmnlAssetNo(rs.getString("CIS_ASSET_NO"));
				bean.setTurnFlag(rs.getString("TURN_FLAG"));
				bean.setAlarmValueUnit(rs.getInt("ALARM_VALUE_UNIT"));
				bean.setJumpValueUnit(rs.getInt("JUMP_VALUE_UNIT"));
				bean.setBuyValueUnit(rs.getInt("BUY_VALUE_UNIT"));
				bean.setRefreshFlag(rs.getString("REFRESH_FLAG"));
				bean.setCpNo(rs.getString("CP_NO"));
				bean.setpOrgNo(rs.getString("P_ORG_NO"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 根据购电单号查询预付费参数详细信息
	 **/
	class OrderInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugUserInfoBean bean = new PPDebugUserInfoBean();
			try {
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setBuyValue(null != rs.getString("BUY_VALUE") ? rs.getInt("BUY_VALUE") : null);
				bean.setAlarmValue(null != rs.getString("ALARM_VALUE") ? rs.getInt("ALARM_VALUE") : null);
				bean.setJumpValue(null != rs.getString("JUMP_VALUE") ? rs.getInt("JUMP_VALUE") : null);
				bean.setBuyOrder(rs.getString("BUY_ORDER"));
				bean.setTurnFlag(rs.getString("TURN_FLAG"));
				bean.setMeterId(rs.getString("METER_ID"));
				bean.setFeeCtrlId(rs.getBigDecimal("FEECTRL_ID"));
				bean.setCisTmnlAssetNo(rs.getString("CIS_ASSET_NO"));
				bean.setTotalNo(null != rs.getString("TOTAL_NO") ? rs.getShort("TOTAL_NO") : null);
				bean.setAlarmValueUnit(rs.getInt("ALARM_VALUE_UNIT"));
				bean.setJumpValueUnit(rs.getInt("JUMP_VALUE_UNIT"));
				bean.setBuyValueUnit(rs.getInt("BUY_VALUE_UNIT"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 供电单位树
	 **/
	class OrgInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OOrg bean = new OOrg();
			try {
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setOrgNo(rs.getString("ORG_NO"));
                bean.setOrgType(rs.getString("ORG_TYPE"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 根据购电单号、终端id、电表id查询终端工况
	 **/
	class TmnlInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugTmnlInfoBean bean = new PPDebugTmnlInfoBean();
			try {
				bean.setTerminalId(rs.getString("TERMINAL_ID"));
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setCpNo(rs.getString("CP_NO"));
				bean.setCpAddr(rs.getString("CP_ADDR"));
				bean.setIsOnline(rs.getInt("IS_ONLINE"));
				bean.setPowerPaulCode(rs.getString("PS_ENSURE_FLAG"));
				bean.setPowerPaulStatus(rs.getString("PAULPOWER"));
				bean.setCommStauts(rs.getString("COMMSTATUS"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 用户操作流程信息
	 **/
	class UserStatusInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugUserStatusInfoBean bean = new PPDebugUserStatusInfoBean();
			try {
				bean.setTerminalId(rs.getString("TERMINAL_ID"));
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				bean.setMeterId(rs.getString("METER_ID"));
				bean.setAssetNo(rs.getString("ASSET_NO"));
				bean.setIsOnline(rs.getString("IS_ONLINE"));
				bean.setFlowNodeStatus(rs.getString("FLOW_NODE_STATUS"));
				bean.setFlowNodeName(rs.getString("FLOW_NODE_NAME"));
				bean.setErrCause(rs.getString("ERR_CAUSE"));
				bean.setOpTime(DateUtil.timeStampToStr(rs.getTimestamp("OP_TIME")));
				bean.setFlowNoCode(rs.getString("FLOW_NO_CODE"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 根据终端查询总加组列表
	 **/
	class TotalInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EDataTotal bean = new EDataTotal();
			try {
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setTotalNo(null != rs.getString("TOTAL_NO") ? rs.getShort("TOTAL_NO") : null);
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 总加组设置总加组信息
	 **/
	class TotalSetInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugTotalBean bean = new PPDebugTotalBean();
			try {
				bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setTotalNo(rs.getShort("TOTAL_NO"));
				bean.setTotalName(rs.getString("TOTAL_NAME"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 总加组设置总加组下属测量点信息
	 **/
	class TotalMpSetInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugTotalMpBean bean = new PPDebugTotalMpBean();
			try {
				bean.setMpSn(rs.getInt("MP_SN"));
				bean.setMpType(rs.getString("MP_TYPE"));
				bean.setCalcFlag(null != rs.getString("CALC_FLAG") ? rs.getShort("CALC_FLAG") : null);
				bean.setPulseAttr(rs.getString("PULSE_ATTR"));
				bean.setCmpNo(rs.getString("CMP_NO"));
				bean.setCmpName(rs.getString("CMP_NAME"));
				bean.setCalcFlagName(rs.getString("CALC_FLAG_NAME"));
				bean.setPulseArrtName(rs.getString("PULSE_ATTR_NAME"));
				bean.setTotalName(rs.getString("TOTAL_NAME"));
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}	
	
	/**
	 * 总加组设置终端下属测量点信息
	 **/
	class TmnlMpSetInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			PPDebugTotalMpBean bean = new PPDebugTotalMpBean();
			try {
				bean.setMpSn(rs.getInt("MP_SN"));
				bean.setMpType(rs.getString("MP_TYPE"));
				bean.setCalcFlag(null);
				bean.setPulseAttr(null);
				bean.setCmpNo(rs.getString("CMP_NO"));
				bean.setCmpName(rs.getString("CMP_NAME"));
				bean.setCalcFlagName("");
				bean.setPulseArrtName("");
				bean.setTotalName("");
				return bean;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
