package com.nari.sysman.securityman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.basicdata.BProtocolEvent;
import com.nari.basicdata.SimpleBEventMark;
import com.nari.basicdata.SimpleEventDef;
import com.nari.elementsdata.ETmnlEventFull;
import com.nari.elementsdata.SimpleETmnlEvent;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.TerminalAddr;
import com.nari.sysman.securityman.IAlertEventDao;

/**
 * DAO类 AlertEventDao
 * 
 * @author zhangzhw
 * @describe 处理报警事件的DAO
 */
public class AlertEventDao extends JdbcBaseDAOImpl implements IAlertEventDao {

	/**
	 * 方法 querySimpleEtmnlEvent
	 * 
	 * @param start
	 * @param limit
	 * @param username
	 * @return Page<SimpleETmnlEvent> 事件列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start, int limit,
			String alertLevel, String username, String consNo, String orgNo,
			String startDate, String endDate, PSysUser pSysUser) {

		/**
		 * SELECT EV.ROWID RD,EV.ID, EV.EVENT_NO, EV.IS_START, EV.EVENT_TIME,
		 * EV.RECEIVE_TIME, EV.FROM_TYPE, EV.FROM_NO, EV.STORE_TYPE,
		 * BP.EVENT_NAME, BP.EVENT_TYPE, BP.EVENT_LEVEL, BP.STORE_MODE,
		 * BP.PROTOCOL_CODE, ED.CONS_NO, ED.TMNL_ASSET_NO, ED.ASSET_NO,
		 * OO.ORG_NAME, ED.MP_TYPE, ED.DATA_SRC FROM E_TMNL_EVENT"+ areacode +"
		 * EV, B_PROTOCOL_EVENT BP, E_DATA_MP ED, O_ORG OO WHERE EV.EVENT_NO =
		 * BP.EVENT_NO AND EV.ID = ED.ID AND ED.ORG_NO = OO.ORG_NO
		 **/
		String areacode = null;
		if (areacode == null || areacode.isEmpty()) {
			areacode = super.getETableAreaByConsNo(consNo);
		}
		if (orgNo != null) {
			areacode = super.getETableAreaByOrgNo(orgNo);
		} else {
			areacode = super.getETableAreaByUser(pSysUser);
		}
		if (areacode == null || areacode.isEmpty()) {
			return null;
		}

		StringBuffer sb = new StringBuffer();
		List paramList = new ArrayList();
		sb.append("SELECT EV.ROWID RD,EV.ID,\n");
		sb.append("       EV.EVENT_NO,\n");
		sb.append("       EV.IS_START,\n");
		sb.append("       EV.EVENT_TIME,\n");
		sb.append("       EV.RECEIVE_TIME,\n");
		sb.append("       EV.FROM_TYPE,\n");
		sb.append("       EV.FROM_NO,\n");
		sb.append("       EV.FLOW_STATUS_CODE,\n");
		sb.append("       EV.STORE_TYPE STORE_MODE,\n");
		sb.append("       BP.EVENT_NAME,\n");
		sb.append("       BP.EVENT_TYPE,\n");
		sb.append("       BP.EVENT_LEVEL,\n");
		// sb.append("       BP.STORE_MODE,\n");
		sb.append("       BP.PROTOCOL_CODE,\n");
		sb.append("       ED.CONS_NO,\n");
		sb.append("       CONS.CONS_NAME,\n");
		sb.append("       ED.TERMINAL_ADDR AS TMNL_ASSET_NO,\n");
		sb.append("       ED.ASSET_NO,\n");
		sb.append("       OO.ORG_NAME,\n");
		sb.append("       OO.ORG_NO,\n");
		sb.append("       ED.MP_TYPE,\n");
		sb.append("       ED.DATA_SRC\n");
		sb.append("  FROM E_TMNL_EVENT  EV,\n");
		sb.append("       B_PROTOCOL_EVENT BP,\n");
		sb.append("       E_DATA_MP ED,\n");
		sb.append("       O_ORG OO,\n");
		sb.append("       C_CONS CONS\n");
		sb.append(" WHERE EV.EVENT_NO = BP.EVENT_NO\n");
		sb.append("       AND EV.ID = ED.ID\n");
		sb.append("       AND ED.ORG_NO = OO.ORG_NO \n");
		sb.append("       AND ED.CONS_NO = CONS.CONS_NO \n");
		sb.append("       AND ED.AREA_CODE=?\n");

		paramList.add(areacode);

		if (consNo != null) {
			sb.append("AND ED.CONS_NO = ?\n");
			paramList.add(consNo);
		} else if (orgNo != null && alertLevel != null && startDate != null
				&& endDate != null) {
			sb.append("AND ED.AREA_NO LIKE ?\n");
			paramList.add(orgNo + "%");
			if (!"04".equals(alertLevel)) {
				sb.append("AND BP.EVENT_LEVEL = ?\n");
				paramList.add(alertLevel);
			}
			sb.append("AND EV.EVENT_TIME BETWEEN TO_DATE(?, 'yyyy-mm-dd') AND "
					+ "(TO_DATE(?, 'yyyy-mm-dd')+1)\n");

			sb.append(SysPrivilige.addPri(pSysUser, "CONS", "", 3));
			paramList.add(startDate);
			paramList.add(endDate);
			paramList.add(pSysUser.getStaffNo());
			paramList.add(pSysUser.getStaffNo());
		} else {
			sb.append("       AND EV.EVENT_TIME >= SYSDATE-1\n");
			sb.append("       AND BP.EVENT_LEVEL = ?\n");
			sb.append(SysPrivilige.addPri(pSysUser, "CONS", "", 3));
			paramList.add(alertLevel);
			paramList.add(pSysUser.getStaffNo());
			paramList.add(pSysUser.getStaffNo());
		}
		// sb.append("and exists ( select org_no from p_access_org where org_no =ev.orgNo and staffNo=?) ")
		// ;
		sb.append(" ORDER BY EV.EVENT_TIME DESC ");

		String sql = sb.toString();
		this.logger.debug(sql);
		return super.pagingFind(sql, start, limit, new ETmnlEventMapper(),
				paramList.toArray());

	}

	/***
	 * @desc 查询事件
	 * @ chenjg
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String type,String nodeId,String terminalAddr, String startDate,String endDate,String staffNo,String orgType,String consType, String eventType){
		StringBuffer sql = new StringBuffer();
		List<Object> paramList = new ArrayList<Object>();
		sql.append("SELECT EV.ROWID         RD,\n");
		sql.append("       EV.ID,\n");
		sql.append("       EV.EVENT_NO,\n");
		sql.append("EV.IS_START,\n");
		sql.append("       EV.EVENT_TIME,\n");
		sql.append("       EV.RECEIVE_TIME,\n");
		sql.append("       EV.FROM_TYPE,\n");
		sql.append("       EV.FROM_NO,\n");
		sql.append("       EV.FLOW_STATUS_CODE,\n");
		sql.append("       EV.STORE_TYPE    STORE_MODE,\n");
		sql.append("       BP.EVENT_NAME,\n");
		sql.append("       BP.EVENT_TYPE,\n");
		sql.append("       BP.EVENT_LEVEL,\n");
		sql.append("       BP.PROTOCOL_CODE,\n");
		sql.append("       ED.CONS_NO,\n");
		sql.append("       CONS.CONS_NAME,\n");
		sql.append("       ED.TERMINAL_ADDR AS TMNL_ASSET_NO,\n");
		sql.append("       ED.ASSET_NO,\n");
		sql.append("       OO.ORG_NAME,\n");
		sql.append("       OO.ORG_NO,\n");
		sql.append("       ED.MP_TYPE,\n");
		sql.append("       ED.DATA_SRC\n");
		sql.append("  FROM E_TMNL_EVENT     EV,\n");
		sql.append("       B_PROTOCOL_EVENT BP,\n");
		sql.append("       E_DATA_MP        ED,\n");
		sql.append("       O_ORG            OO,\n");

		if(type.equals("sub")){//变电站
			sql.append("       G_LINE LINE,\n");
			sql.append("       G_SUBS_LINE_RELA RELA,\n");
		}else if (type.equals("ugp")){//普通群组
			sql.append("       R_USER_GROUP_DETAIL UG, \n");
		}else if(type.equals("cgp")){//控制群组
			sql.append("       T_TMNL_GROUP_DETAIL TG, \n");
		}else if(type.equals("trade")){//行业
			sql.append("       (SELECT vwt.TRADE_NO\n");
			sql.append("         FROM vw_trade vwt\n");
			sql.append("       START WITH vwt.TRADE_NO = ?\n" );
			sql.append("       CONNECT BY PRIOR vwt.TRADE_NO = vwt.p_trade_NO) TRADE ,\n");
		}

		sql.append("       C_CONS           CONS\n");
		sql.append(" WHERE EV.EVENT_NO = BP.EVENT_NO\n");
		sql.append("   AND EV.ID = ED.ID\n");
		sql.append("   AND ED.ORG_NO = OO.ORG_NO\n");
		sql.append("   AND ED.CONS_NO = CONS.CONS_NO\n");
		if(null != eventType && !"".equals(eventType)){
			sql.append("       AND SUBSTR(EV.EVENT_NO, 2, 6) = ?");
			paramList.add(eventType);
		}
		if(null != consType && !"".equals(consType)){
			sql.append("   AND CONS.CONS_TYPE = ?\n");
			paramList.add(consType);
		}
		
		if(!"-1".equals(terminalAddr) && null != terminalAddr && !"".equals(terminalAddr)){
			sql.append("   AND ED.TERMINAL_ADDR = ?\n");
			paramList.add(terminalAddr);
		}
		
		if(type.equals("usr")){//用户
			sql.append("   AND CONS.CONS_NO = ?\n");
		}else if(type.equals("org") && !"02".equals(orgType)){//组织机构
			sql.append("   AND OO.ORG_NO like ?\n");
		}else if (type.equals("sub")){//变电站
			sql.append("   AND LINE.LINE_ID = RELA.LINE_ID\n");
			sql.append("   AND CONS.LINE_ID = LINE.LINE_ID\n");
			sql.append("   AND RELA.SUBS_ID = ?\n");
		}else if(type.equals("line")){//线路
			sql.append("   AND CONS.LINE_ID = ?\n");
		}else if (type.equals("ugp")){//普通群组
			sql.append("   AND CONS.CONS_NO = UG.CONS_NO\n");
			sql.append("   AND UG.GROUP_NO = ?\n");
		}else if(type.equals("cgp")){//控制群组
			sql.append("   AND CONS.CONS_NO = TG.CONS_NO\n");
			sql.append("   AND TG.GROUP_NO = ?\n");
		}else if(type.equals("trade")){//行业
			sql.append("   AND TRADE.TRADE_NO = CONS.TRADE_CODE \n");
		}
		
		sql.append("   AND EV.EVENT_TIME BETWEEN TO_DATE(?, 'yyyy-mm-dd') AND\n");
		sql.append("       (TO_DATE(?, 'yyyy-mm-dd'))\n");
		sql.append("   AND EXISTS (SELECT 1\n");
		sql.append("          FROM P_ACCESS_ORG PRI_ORG\n");
		sql.append("         WHERE PRI_ORG.ORG_NO = CONS.AREA_NO\n");
		sql.append("           AND PRI_ORG.STAFF_NO = ?)\n");
		sql.append("   AND EXISTS (SELECT 1\n");
		sql.append("          FROM P_ACCESS_CONS_TYPE PRI_CONS_TYPE\n");
		sql.append("         WHERE PRI_CONS_TYPE.CONS_TYPE = CONS.CONS_TYPE\n");
		sql.append("           AND PRI_CONS_TYPE.STAFF_NO = ?)\n");
		sql.append(" ORDER BY EV.EVENT_TIME DESC\n");
		
		logger.debug("事件查询：" +sql);
		if(type.equals("usr")){//用户
			paramList.add(nodeId);
		}else if(type.equals("org") && !"02".equals(orgType)){//组织机构
			paramList.add(nodeId + "%");
		}else if(type.equals("sub")){//变电站
			paramList.add(nodeId);
		}else if(type.equals("line")){//线路
			paramList.add(nodeId);
		}else if(type.equals("ugp")){//普通群组
			paramList.add(nodeId);
		}else if(type.equals("cgp")){//控制群组
			paramList.add(nodeId);
		}else if(type.equals("trade")){//行业
			paramList.add(nodeId);
		}
		
		paramList.add(startDate);
		paramList.add(endDate);
		paramList.add(staffNo);
		paramList.add(staffNo);
		
		return pagingFind(sql.toString(), start, limit, new ETmnlEventMapper(),paramList.toArray());
	}

	
	/***
	 * @desc 根据rowid查询事件
	 * @ chenjg
	 */
	public Page<SimpleETmnlEvent> querySimpleEtmnlEvent(long start , int limit, String rowid){
		StringBuffer sql = new StringBuffer();
		List paramList = new ArrayList();
		sql.append("SELECT EV.ROWID         RD,\n");
		sql.append("       EV.ID,\n");
		sql.append("       EV.EVENT_NO,\n");
		sql.append("EV.IS_START,\n");
		sql.append("       EV.EVENT_TIME,\n");
		sql.append("       EV.RECEIVE_TIME,\n");
		sql.append("       EV.FROM_TYPE,\n");
		sql.append("       EV.FROM_NO,\n");
		sql.append("       EV.FLOW_STATUS_CODE,\n");
		sql.append("       EV.STORE_TYPE    STORE_MODE,\n");
		sql.append("       BP.EVENT_NAME,\n");
		sql.append("       BP.EVENT_TYPE,\n");
		sql.append("       BP.EVENT_LEVEL,\n");
		sql.append("       BP.PROTOCOL_CODE,\n");
		sql.append("       ED.CONS_NO,\n");
		sql.append("       CONS.CONS_NAME,\n");
		sql.append("       ED.TMNL_ASSET_NO,\n");
		sql.append("       ED.ASSET_NO,\n");
		sql.append("       OO.ORG_NAME,\n");
		sql.append("       OO.ORG_NO,\n");
		sql.append("       ED.MP_TYPE,\n");
		sql.append("       ED.DATA_SRC\n");
		sql.append("  FROM E_TMNL_EVENT     EV,\n");
		sql.append("       B_PROTOCOL_EVENT BP,\n");
		sql.append("       E_DATA_MP        ED,\n");
		sql.append("       O_ORG            OO,\n");


		sql.append("       C_CONS           CONS\n");
		sql.append(" WHERE EV.EVENT_NO = BP.EVENT_NO\n");
		sql.append("   AND EV.ID = ED.ID\n");
		sql.append("   AND ED.ORG_NO = OO.ORG_NO\n");
		sql.append("   AND ED.CONS_NO = CONS.CONS_NO\n");
		sql.append("   AND EV.ROWID = ?\n");
		
		
		
		logger.debug("事件查询：" +sql);
		
		paramList.add(rowid);
		
		return pagingFind(sql.toString(), start, limit, new ETmnlEventMapper(),paramList.toArray());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> queryAlertEventCount(PSysUser pSysUser) {

		String areacode = super.getETableAreaByUser(pSysUser);
		if (areacode == null || areacode.isEmpty())
			return null;

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT COUNT(DECODE(EVENT_LEVEL, 3, 1, NULL)) LEVEL1COUNT, COUNT(DECODE(EVENT_LEVEL, 2, 1, NULL)) LEVEL2COUNT, COUNT(DECODE(EVENT_LEVEL, 1, 1, NULL)) LEVEL3COUNT\n");
		sb
				.append("  FROM E_TMNL_EVENT"
						+ "  EV, B_PROTOCOL_EVENT BP, E_DATA_MP ED, O_ORG OO,C_CONS CONS\n");
		sb.append(" WHERE EV.EVENT_NO = BP.EVENT_NO\n");
		sb.append("   AND EV.ID = ED.ID\n");
		sb.append("   AND ED.ORG_NO = OO.ORG_NO\n");
		sb.append("   AND ED.CONS_NO = CONS.CONS_NO\n");
		sb.append("   AND ED.AREA_CODE=? \n");
		sb.append("   AND EV.EVENT_TIME >= SYSDATE-1\n");
		sb.append(SysPrivilige.addPri(pSysUser, "CONS", "", 3));

		String sql = sb.toString();

		return super.getJdbcTemplate().queryForList(
				sql,
				new Object[] { areacode, pSysUser.getStaffNo(),
						pSysUser.getStaffNo() });

	}

	/**
	 * 查询事件
	 */
	@SuppressWarnings("unchecked")
	public List<ETmnlEventFull> queryETmnlEventFull(String orgNo, String rd,
			PSysUser pSysUser) {

		String areacode = super.getETableAreaByUser(pSysUser);
		if (areacode == null) {
			areacode = super.getETableAreaByOrgNo(orgNo);
		}
		if (areacode == null || areacode.isEmpty())
			return null;

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT ID, EVENT_NO, IS_START, EVENT_TIME, RECEIVE_TIME, FROM_TYPE, FROM_NO, STORE_TYPE, EVENT_DATA, DATA1, DATA2, DATA3, DATA4, DATA5, DATA6, DATA7, DATA8, DATA9, DATA10, DATA11, DATA12, DATA13, DATA14, DATA15, DATA16, DATA17, DATA18, DATA19, DATA20, DATA21, DATA22, DATA23, DATA24, DATA25, DATA26, DATA27, DATA28, DATA29, DATA30, DATA31, DATA32, DATA33, DATA34, DATA35, DATA36, DATA37, DATA38, DATA39, DATA40, DATA41, DATA42, DATA43, DATA44, DATA45, DATA46, DATA47, DATA48, DATA49, DATA50,FLOW_STATUS_CODE\n");
		sb.append("  FROM E_TMNL_EVENT WHERE ROWID=?");

		String sql = sb.toString();

		return (List<ETmnlEventFull>) super.getJdbcTemplate().query(sql,
				new Object[] { rd }, new ETmnlEventFullMapper());

	}

	/**
	 * 查询事件定义
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SimpleEventDef> queryEventDef(String eventNo, String itemNos) {

		StringBuffer sb = new StringBuffer();
		sb
				.append("SELECT EVENT_NO, ITEM_NO, PROT_ITEM_SN, ITEM_NAME, DATAN, EXPLAN_DATAN, DATA_FORMAT, BYTES, DATA_STYLE, OCCUPY_BITS, BLOCK_RULE, UNIT\n");
		sb.append("  FROM B_EVENT_DATA_DEF WHERE DATAN>0 AND EVENT_NO=?");

		if (itemNos == null || itemNos.isEmpty()) {
			return null;
		} else if (itemNos.equals("blank")) {

		} else {
			sb.append("  AND ITEM_NO IN " + itemNos);
		}
		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql, new Object[] { eventNo },
				new SimpleEventDefMapper());
	}

	@SuppressWarnings("unchecked")
	public List<SimpleBEventMark> queryEventMark(String eventNo, int dataN) {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT EVENT_NO, DATAN, MARK_NO, MARK_NAME, MARK0, MARK1\n");
		sb.append("  FROM B_EVENT_MARK\n");
		sb.append(" WHERE EVENT_NO = ? \n");
		sb.append("   AND DATAN = ?");

		String sql = sb.toString();

		return super.getJdbcTemplate().query(sql,
				new Object[] { eventNo, dataN }, new SimpleBEventMarkMapper());

	}
	
	

	/**
	 * 内部类 ETmnlEventMapper
	 * 
	 * @author zhangzhw
	 * @describe 简单的事件列表RowMapper
	 */
	class ETmnlEventMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SimpleETmnlEvent simpleetmnlevent = new SimpleETmnlEvent();
			try {
				simpleetmnlevent.setRd(rs.getString("RD"));
				simpleetmnlevent.setDataId(rs.getLong("ID"));
				simpleetmnlevent.setEventNo(rs.getString("EVENT_NO"));
				simpleetmnlevent.setIsStart(rs.getByte("IS_START"));
				simpleetmnlevent.setEventTime(rs.getTimestamp("EVENT_TIME"));
				simpleetmnlevent
						.setReceiveTime(rs.getTimestamp("RECEIVE_TIME"));
				simpleetmnlevent.setFromType(rs.getByte("FROM_TYPE"));
				simpleetmnlevent.setFromNo(rs.getString("FROM_NO"));
				// simpleetmnlevent.setStoreType(rs.getByte("STORE_TYPE"));
				simpleetmnlevent.setEventName(rs.getString("EVENT_NAME"));
				simpleetmnlevent.setEventType(rs.getByte("EVENT_TYPE"));
				simpleetmnlevent.setEventLevel(rs.getByte("EVENT_LEVEL"));
				simpleetmnlevent.setStoreMode(rs.getByte("STORE_MODE"));
				simpleetmnlevent.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				simpleetmnlevent.setConsNo(rs.getString("CONS_NO"));
				simpleetmnlevent.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				simpleetmnlevent.setAssetNo(rs.getString("ASSET_NO"));
				simpleetmnlevent.setOrgName(rs.getString("ORG_NAME"));
				simpleetmnlevent.setOrgNo(rs.getString("ORG_NO"));
				simpleetmnlevent.setMpType(rs.getByte("MP_TYPE"));
				simpleetmnlevent.setDataSrc(rs.getString("DATA_SRC"));
				simpleetmnlevent.setFlowStatusCode(rs.getString("FLOW_STATUS_CODE"));
				simpleetmnlevent.setConsName(rs.getString("CONS_NAME"));
				return simpleetmnlevent;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * RowMapper ETmnlEventFullMapper
	 * 
	 * @author zhangzhw 完整的　ETmnlEventFull映射
	 */
	class ETmnlEventFullMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ETmnlEventFull etmnleventfull = new ETmnlEventFull();
			try {
				etmnleventfull.setDataId(rs.getLong("ID"));
				etmnleventfull.setEventNo(rs.getString("EVENT_NO"));
				etmnleventfull.setIsStart(rs.getByte("IS_START"));
				etmnleventfull.setEventTime(rs.getTimestamp("EVENT_TIME"));
				etmnleventfull.setReceiveTime(rs.getTimestamp("RECEIVE_TIME"));
				etmnleventfull.setFromType(rs.getByte("FROM_TYPE"));
				etmnleventfull.setFromNo(rs.getString("FROM_NO"));
				etmnleventfull.setStoreType(rs.getByte("STORE_TYPE"));
				etmnleventfull.setEventData(rs.getString("EVENT_DATA"));
				etmnleventfull.setData1(rs.getString("DATA1"));
				etmnleventfull.setData2(rs.getString("DATA2"));
				etmnleventfull.setData3(rs.getString("DATA3"));
				etmnleventfull.setData4(rs.getString("DATA4"));
				etmnleventfull.setData5(rs.getString("DATA5"));
				etmnleventfull.setData6(rs.getString("DATA6"));
				etmnleventfull.setData7(rs.getString("DATA7"));
				etmnleventfull.setData8(rs.getString("DATA8"));
				etmnleventfull.setData9(rs.getString("DATA9"));
				etmnleventfull.setData10(rs.getString("DATA10"));
				etmnleventfull.setData11(rs.getString("DATA11"));
				etmnleventfull.setData12(rs.getString("DATA12"));
				etmnleventfull.setData13(rs.getString("DATA13"));
				etmnleventfull.setData14(rs.getString("DATA14"));
				etmnleventfull.setData15(rs.getString("DATA15"));
				etmnleventfull.setData16(rs.getString("DATA16"));
				etmnleventfull.setData17(rs.getString("DATA17"));
				etmnleventfull.setData18(rs.getString("DATA18"));
				etmnleventfull.setData19(rs.getString("DATA19"));
				etmnleventfull.setData20(rs.getString("DATA20"));
				etmnleventfull.setData21(rs.getString("DATA21"));
				etmnleventfull.setData22(rs.getString("DATA22"));
				etmnleventfull.setData23(rs.getString("DATA23"));
				etmnleventfull.setData24(rs.getString("DATA24"));
				etmnleventfull.setData25(rs.getString("DATA25"));
				etmnleventfull.setData26(rs.getString("DATA26"));
				etmnleventfull.setData27(rs.getString("DATA27"));
				etmnleventfull.setData28(rs.getString("DATA28"));
				etmnleventfull.setData29(rs.getString("DATA29"));
				etmnleventfull.setData30(rs.getString("DATA30"));
				etmnleventfull.setData31(rs.getString("DATA31"));
				etmnleventfull.setData32(rs.getString("DATA32"));
				etmnleventfull.setData33(rs.getString("DATA33"));
				etmnleventfull.setData34(rs.getString("DATA34"));
				etmnleventfull.setData35(rs.getString("DATA35"));
				etmnleventfull.setData36(rs.getString("DATA36"));
				etmnleventfull.setData37(rs.getString("DATA37"));
				etmnleventfull.setData38(rs.getString("DATA38"));
				etmnleventfull.setData39(rs.getString("DATA39"));
				etmnleventfull.setData40(rs.getString("DATA40"));
				etmnleventfull.setData41(rs.getString("DATA41"));
				etmnleventfull.setData42(rs.getString("DATA42"));
				etmnleventfull.setData43(rs.getString("DATA43"));
				etmnleventfull.setData44(rs.getString("DATA44"));
				etmnleventfull.setData45(rs.getString("DATA45"));
				etmnleventfull.setData46(rs.getString("DATA46"));
				etmnleventfull.setData47(rs.getString("DATA47"));
				etmnleventfull.setData48(rs.getString("DATA48"));
				etmnleventfull.setData49(rs.getString("DATA49"));
				etmnleventfull.setData50(rs.getString("DATA50"));
				etmnleventfull.setFlowStatusCode(rs.getString("FLOW_STATUS_CODE"));
				return etmnleventfull;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类　SimpleEventDefMapper
	 * 
	 * @author zhangzhw 事件定义的内部类
	 */
	class SimpleEventDefMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SimpleEventDef simpleeventdef = new SimpleEventDef();
			try {
				simpleeventdef.setEventNo(rs.getString("EVENT_NO"));
				simpleeventdef.setItemNo(rs.getString("ITEM_NO"));
				simpleeventdef.setProtItemSn(rs.getShort("PROT_ITEM_SN"));
				simpleeventdef.setItemName(rs.getString("ITEM_NAME"));
				simpleeventdef.setDatan(rs.getShort("DATAN"));
				simpleeventdef.setExplanDatan(rs.getByte("EXPLAN_DATAN"));
				simpleeventdef.setDataFormat(rs.getString("DATA_FORMAT"));
				simpleeventdef.setBytes(rs.getString("BYTES"));
				simpleeventdef.setDataStyle(rs.getString("DATA_STYLE"));
				simpleeventdef.setOccupyBits(rs.getString("OCCUPY_BITS"));
				simpleeventdef.setBlockRule(rs.getString("BLOCK_RULE"));
				simpleeventdef.setUnit(rs.getString("UNIT"));
				return simpleeventdef;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 内部类　SimpleBEventMarkMapper
	 * 
	 * @author zhangzhw 　映射　B_Event_Mark 的　RowMapper
	 */
	class SimpleBEventMarkMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			SimpleBEventMark simplebeventmark = new SimpleBEventMark();
			try {
				simplebeventmark.setEventNo(rs.getString("EVENT_NO"));
				simplebeventmark.setDatan(rs.getShort("DATAN"));
				simplebeventmark.setMarkNo(rs.getShort("MARK_NO"));
				simplebeventmark.setMarkName(rs.getString("MARK_NAME"));
				simplebeventmark.setMark0(rs.getString("MARK0"));
				simplebeventmark.setMark1(rs.getString("MARK1"));
				return simplebeventmark;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * chenjg
	 * 更新flow_status_code
	 */
	public void update(String rowid,String statusCode){
		String sql = "UPDATE e_tmnl_event ev SET ev.flow_status_code = ? WHERE ev.rowid =?";
		this.updatePara(sql, new Object[]{statusCode,rowid});
	}
	
	/**
	 * chenjg
	 * 根据rowid 查找
	 * @param rowId
	 * @return
	 */
	public ETmnlEventFull findByRowId(String rowId){
		String sql = "SELECT EV.ROWID,EV.* FROM E_TMNL_EVENT EV WHERE EV.ROWID = ?";
		List list = this.getJdbcTemplate().query(sql, new Object[]{rowId},new ETmnlEventFullMapper());
		if(list != null){
			return (ETmnlEventFull)list.get(0);
		}
		return null;
	}
	
	/**
	 * chenjg
	 * 根据用户号查询终端
	 * @param consNo
	 * @return
	 */
	public List<TerminalAddr> findTerminalAddrByConsNo(String consNo){
		String sql = "select tmnl.terminal_addr from vw_tmnl_run tmnl where tmnl.cons_no = ?";
		return getJdbcTemplate().query(sql,new Object[]{consNo}, new TerminalAddrRowMapper());
	}
	
	class TerminalAddrRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalAddr terminalAddr = new TerminalAddr();
			terminalAddr.setTerminalId(rs.getString("terminal_addr"));
			terminalAddr.setTerminalAddr(rs.getString("terminal_addr"));
			return terminalAddr;
		}
		
	}

	class EventTypeRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			BProtocolEvent bpe = new BProtocolEvent();
			bpe.setEventNo(rs.getString("EVENT_NO"));
			bpe.setEventName(rs.getString("EVENT_NAME"));
			return bpe;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BProtocolEvent> findEventType() {
		String sql = "SELECT EVENT_NO, EVENT_NAME FROM VW_PROTOCOL_EVENT ORDER BY EVENT_NO";
		return this.getJdbcTemplate().query(sql, new EventTypeRowMapper());

	}
}
