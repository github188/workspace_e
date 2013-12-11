package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.EMpEnergyCurveBean;
import com.nari.qrystat.querycollpoint.ISendDataQueryDao;
import com.nari.qrystat.querycollpoint.SendDataQuery;
import com.nari.qrystat.querycollpoint.SendDataQueryB;
import com.nari.qrystat.querycollpoint.SendDataQueryBFail;
import com.nari.qrystat.querycollpoint.SendDataQueryC;
import com.nari.qrystat.querycollpoint.SendDataQueryD;
import com.nari.qrystat.querycollpoint.SendDataQueryDay;
import com.nari.qrystat.querycollpoint.SendDataQueryF;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class SendDataQueryDaoImpl  extends JdbcBaseDAOImpl implements ISendDataQueryDao {
	Logger logger = Logger.getLogger( SendDataQueryDaoImpl.class);
	private SendDataQuery sendDataQuery;
	private SendDataQueryB sendDataQueryB;
	private SendDataQueryC sendDataQueryC;
	private SendDataQueryD sendDataQueryD;
	private SendDataQueryF sendDataQueryF;
	private SendDataQueryBFail sendDataQueryBFail;
	private SendDataQueryDay sendDataQueryDay;
	String addString = "00";
	String addgp = "";
	String addgp2 = "";
	public SendDataQueryDay getSendDataQueryDay() {
		return sendDataQueryDay;
	}
	public void setSendDataQueryDay(SendDataQueryDay sendDataQueryDay) {
		this.sendDataQueryDay = sendDataQueryDay;
	}
	public SendDataQueryBFail getSendDataQueryBFail() {
		return sendDataQueryBFail;
	}
	public void setSendDataQueryBFail(SendDataQueryBFail sendDataQueryBFail) {
		this.sendDataQueryBFail = sendDataQueryBFail;
	}
	/**
	 * @return the sendDataQueryF
	 */
	public SendDataQueryF getSendDataQueryF() {
		return sendDataQueryF;
	}
	/**
	 * @param sendDataQueryF the sendDataQueryF to set
	 */
	public void setSendDataQueryF(SendDataQueryF sendDataQueryF) {
		this.sendDataQueryF = sendDataQueryF;
	}
	/**
	 * @return the sendDataQueryD
	 */
	public SendDataQueryD getSendDataQueryD() {
		return sendDataQueryD;
	}
	/**
	 * @param sendDataQueryD the sendDataQueryD to set
	 */
	public void setSendDataQueryD(SendDataQueryD sendDataQueryD) {
		this.sendDataQueryD = sendDataQueryD;
	}
	/**
	 * @return the sendDataQueryC
	 */
	public SendDataQueryC getSendDataQueryC() {
		return sendDataQueryC;
	}
	/**
	 * @param sendDataQueryC the sendDataQueryC to set
	 */
	public void setSendDataQueryC(SendDataQueryC sendDataQueryC) {
		this.sendDataQueryC = sendDataQueryC;
	}
	/**
	 * @return the sendDataQueryB
	 */
	public SendDataQueryB getSendDataQueryB() {
		return sendDataQueryB;
	}
	/**
	 * @param sendDataQueryB the sendDataQueryB to set
	 */
	public void setSendDataQueryB(SendDataQueryB sendDataQueryB) {
		this.sendDataQueryB = sendDataQueryB;
	}
	/**
	 * @return the sendDataQuery
	 */
	public SendDataQuery getSendDataQuery() {
		return sendDataQuery;
	}
	/**
	 * @param sendDataQuery the sendDataQuery to set
	 */
	public void setSendDataQuery(SendDataQuery sendDataQuery) {
		this.sendDataQuery = sendDataQuery;
	}
	@Override
	public Page<SendDataQuery> findSendDataQuery(String MR_SECT_NO,String sendDataQueryFlag,String  addr,String sendDQOrgType,String sendDQType,String DateStart,String DateEnd,String CONS_NO, String TG_ID,
			long start, int limit,PSysUser pSysUser) throws DBAccessException {
		 addgp = "";
		 addgp2 = "";
		String mrsectNoString = ""; 
		if(MR_SECT_NO.equals("")){
			mrsectNoString = ""; 
		}else{
			mrsectNoString = " AND C.MR_SECT_NO = "+MR_SECT_NO;
		}
		if ("usr".equals(sendDQType) || "usr1".equals(sendDQType) || "usr2".equals(sendDQType)) {
			addString = this.getETableAreaByConsNo(CONS_NO);
		}
		if ("org".equals(sendDQType)) {
			addString = this.getETableAreaByOrgNo(CONS_NO);
		}
		if ("sub".equals(sendDQType)) {
			addString = this.getETableAreaBySubId(CONS_NO);
		}
		if ("line".equals(sendDQType)) {
			addString = this.getETableAreaByLineId(CONS_NO);
		}
		String dayString = "1=1";
		if("onlyday".equals(sendDataQueryFlag)){
			dayString = "(E.STAT_DATE = TO_DATE(?, 'yyyy-MM-dd') or E.STAT_DATE = TO_DATE(?, 'yyyy-MM-dd'))";
		}else if("everyday".equals(sendDataQueryFlag)){
			dayString = "E.STAT_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";
		}
		StringBuffer sb = new StringBuffer();
		String addrString = "";
		String sendDQTypeString = "";
		Object[] newObj = new Object[]{addString,addr,DateStart,DateEnd,CONS_NO,TG_ID};
		//Object[] newObj = new Object[]{DateStart,DateEnd,CONS_NO,TG_ID};
		if(TG_ID == null){
			TG_ID = "";
				};
				if(CONS_NO == null){
					CONS_NO = "";
						};
				if(sendDQType.equals("org") && sendDQOrgType.equals("06")){
					sendDQTypeString = "C.ORG_NO = ?";
					newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
					//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
				}else if(sendDQType.equals("org") && sendDQOrgType.equals("04") || sendDQOrgType.equals("05")){
					sendDQTypeString = "O.P_ORG_NO = ? OR O.ORG_NO = ?";
					newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO,CONS_NO};
					//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
				}else if(sendDQType.equals("org") && sendDQOrgType.equals("03")){
					sendDQTypeString = "O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?) OR O.P_ORG_NO = ?";
					newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO,CONS_NO};
					//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
				}else if(sendDQType.equals("sub")){
					sendDQTypeString = "C.SUBS_ID=?";
					newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
					//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
				}else if(sendDQType.equals("line")){
					sendDQTypeString = "C.LINE_ID=?";
					newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
					//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
				}else if(sendDQType.equals("cgp")){
					sendDQTypeString = "GR.GROUP_NO = ?";
					addgp = ",T_TMNL_GROUP_DETAIL GR ";
					addgp2 = " AND GR.TMNL_ASSET_NO = D.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
					newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
				}else if(sendDQType.equals("ugp")){
					sendDQTypeString = "GR.GROUP_NO = ?";
					addgp = ",R_USER_GROUP_DETAIL GR ";
					addgp2 = " AND GR.TMNL_ASSET_NO = D.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
					newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
				}else if (sendDQType.equals("usr2") && !addr.equals("")) {
					addrString = " AND D.TMNL_ASSET_NO=?";
					sendDQTypeString = "C.TG_ID = ?";
					newObj = new Object[] { addString,addr,DateStart, DateEnd, TG_ID};
				} else if(sendDQType.equals("usr1") && !addr.equals("")){
					addrString = " AND D.TMNL_ASSET_NO=?";
					sendDQTypeString = "C.CONS_NO = ?";
					newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO};
				}else if (sendDQType.equals("usr2") && addr.equals("")) {
					sendDQTypeString = "C.TG_ID = ?";
					newObj = new Object[] { addString,DateStart, DateEnd, TG_ID};
				} else if(sendDQType.equals("usr1") && addr.equals("")){
					sendDQTypeString = "C.CONS_NO = ?";
					newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO};
				}else if(sendDQType.equals("usr") && addr.equals("")){
					sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
					newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO,TG_ID};
				}else if(sendDQType.equals("usr") && !addr.equals("")){
					sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
					addrString = " AND D.TMNL_ASSET_NO=?";
					newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO,TG_ID};
				}

				sb.append("SELECT C.CONS_NO,C.CONS_ID,\n");
				sb.append("       C.CONS_NAME,C.MR_SECT_NO,\n");
				sb.append("       C.ELEC_ADDR,\n");
				sb.append("       C.RUN_CAP,\n");
				sb.append("       O.ORG_NAME,\n");
				sb.append("       D.ASSET_NO,\n");
				sb.append("       D.CT*D.PT AS T_FACTOR,\n");
				sb.append("       D.TMNL_ASSET_NO,\n");
				sb.append("       D.TERMINAL_ADDR,\n");
				sb.append("      D.cis_tmnl_asset_no AS CIS_ASSET_NO,\n");
				sb.append("       E.ID,\n");
				sb.append("       TO_CHAR(E.STAT_DATE, 'YYYY-MM-DD') DATA_DATE,\n");
				sb.append("       E.PAP_E,\n");
				sb.append("       E.PAP_E1,\n");
				sb.append("       E.PAP_E2,\n");
				sb.append("       E.PAP_E3,\n");
				sb.append("       E.PAP_E4\n");
				sb.append("  FROM C_CONS C, O_ORG O,  E_DATA_MP D, A_CALC_DAY_POWER E"+addgp+"\n");
				sb.append(" WHERE C.ORG_NO = O.ORG_NO(+)"+addgp2+"\n");
				sb.append(" AND C.CONS_NO = D.CONS_NO AND D.MP_SN<>0 AND D.IS_VALID = 1 \n");
				sb.append("   AND D.ID = E.ID "+mrsectNoString+" AND D.AREA_CODE = ?"+addrString+"\n");
				sb.append("   AND "+dayString+"\n");
				sb.append("   AND ("+sendDQTypeString+")\n");
				sb.append(" ORDER BY E.STAT_DATE DESC");

		return  super.pagingFind(sb.toString(), start, limit, new SDQRowMapper(),newObj);
	}
	
	class SDQRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		SendDataQuery senddataquery = new SendDataQuery();
		 try { 
		senddataquery.setConsNo(rs.getString("CONS_NO"));
		senddataquery.setConsId(rs.getLong("CONS_ID"));
		senddataquery.setMrSectNo(rs.getString("MR_SECT_NO"));
		senddataquery.setConsName(rs.getString("CONS_NAME"));
		senddataquery.setElecAddr(rs.getString("ELEC_ADDR"));
		senddataquery.setRunCap(rs.getDouble("RUN_CAP"));
		senddataquery.setOrgName(rs.getString("ORG_NAME"));
		senddataquery.setAssetNo(rs.getString("ASSET_NO"));
		senddataquery.settFactor(rs.getDouble("T_FACTOR"));
		senddataquery.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
		senddataquery.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
		senddataquery.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
		senddataquery.setDataId(rs.getLong("ID"));
		senddataquery.setDataDate(rs.getString("DATA_DATE"));
		senddataquery.setPapRE(rs.getDouble("PAP_E"));
		senddataquery.setPapR1E(rs.getDouble("PAP_E1"));
		senddataquery.setPapR2E(rs.getDouble("PAP_E2"));
		senddataquery.setPapR3E(rs.getDouble("PAP_E3"));
		senddataquery.setPapR4E(rs.getDouble("PAP_E4"));
		return senddataquery;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}

	@Override
	public Page<SendDataQueryB> findSendDataQueryB(String MR_SECT_NO,String sendDataQueryFlag,String  addr,String sendDQOrgType,String sendDQType,
			String DateStart, String DateEnd, String CONS_NO, String TG_ID,
			int start, int limit, PSysUser pSysUser) throws DBAccessException {
		String mrsectNoString = ""; 
		 addgp = "";
		 addgp2 = "";
		if(MR_SECT_NO.equals("")){
			mrsectNoString = ""; 
		}else{
			mrsectNoString = " AND C.MR_SECT_NO = "+MR_SECT_NO;
		}
		if ("usr".equals(sendDQType) || "usr1".equals(sendDQType) || "usr2".equals(sendDQType)) {
			addString = this.getETableAreaByConsNo(CONS_NO);
		}
		if ("org".equals(sendDQType)) {
			addString = this.getETableAreaByOrgNo(CONS_NO);
		}
		if ("sub".equals(sendDQType)) {
			addString = this.getETableAreaBySubId(CONS_NO);
		}
		if ("line".equals(sendDQType)) {
			addString = this.getETableAreaByLineId(CONS_NO);
		}
		String dayString = "1=1";
		if("onlyday".equals(sendDataQueryFlag)){
			dayString = "(dr.DATA_DATE = TO_DATE(?, 'yyyy-MM-dd') or dr.DATA_DATE = TO_DATE(?, 'yyyy-MM-dd'))";
		}else if("everyday".equals(sendDataQueryFlag)){
			dayString = "dr.DATA_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd')";
		}

		StringBuffer sb = new StringBuffer();
		//DateStart = DateStart+" 00:00:00";
		//DateEnd = DateEnd+" 23:59:59";	
		String addrString = "";
		String sendDQTypeString = "";
		Object[] newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO, TG_ID };
		if (TG_ID == null) {
			TG_ID = "";
		};
		if(CONS_NO == null){
			CONS_NO = "";
				};
				if(sendDQType.equals("org") && sendDQOrgType.equals("06") || sendDQOrgType.equals("05")){
			sendDQTypeString = "C.ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if(sendDQType.equals("org") && sendDQOrgType.equals("04")){
			sendDQTypeString = "O.P_ORG_NO = ? OR O.ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if(sendDQType.equals("org") && sendDQOrgType.equals("03")){
			sendDQTypeString = "O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?) OR O.P_ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if (sendDQType.equals("sub")) {
			sendDQTypeString = "C.SUBS_ID=?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO };
		} else if (sendDQType.equals("line")) {
			sendDQTypeString = "C.LINE_ID=?";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}else if(sendDQType.equals("cgp")){
			sendDQTypeString = "GR.GROUP_NO = ?";
			addgp = ",T_TMNL_GROUP_DETAIL GR ";
			addgp2 = " AND GR.TMNL_ASSET_NO = DM.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}else if(sendDQType.equals("ugp")){
			sendDQTypeString = "GR.GROUP_NO = ?";
			addgp = ",R_USER_GROUP_DETAIL GR ";
			addgp2 = " AND GR.TMNL_ASSET_NO = DM.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		} else if (sendDQType.equals("usr2") && !addr.equals("")) {
			addrString = " AND dm.TMNL_ASSET_NO=?";
			sendDQTypeString = "C.TG_ID = ?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, TG_ID};
		} else if(sendDQType.equals("usr1") && !addr.equals("")){
			addrString = " AND dm.TMNL_ASSET_NO=?";
			sendDQTypeString = "C.CONS_NO = ?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO};
		} else if (sendDQType.equals("usr2") && addr.equals("")) {
			sendDQTypeString = "C.TG_ID = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, TG_ID};
		} else if(sendDQType.equals("usr1") && addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO};
		}else if(sendDQType.equals("usr") && addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO,TG_ID};
		}else if(sendDQType.equals("usr") && !addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
			addrString = " AND D.TMNL_ASSET_NO=?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO,TG_ID};
		}
		sb.append("SELECT c.CONS_NO,c.CONS_ID,\n");
		sb.append("       c.CONS_NAME,C.MR_SECT_NO,\n");
		sb.append("       c.ELEC_ADDR,\n");
		sb.append("       c.RUN_CAP,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       Dm.ASSET_NO,\n");
		sb.append("       Dm.CT*Dm.PT AS T_FACTOR,\n");
		sb.append("       dm.TMNL_ASSET_NO,\n");
		sb.append("       dm.TERMINAL_ADDR,\n");
		sb.append("       dm.cis_tmnl_asset_no AS CIS_ASSET_NO,\n");
		sb.append("       dr.ID,\n");
		sb.append("       TO_CHAR(dr.DATA_DATE, 'yyyy-mm-dd') DATA_DATE,\n");
		sb.append("       dr.PAP_R,\n");
		sb.append("       dr.PAP_R1,\n");
		sb.append("       dr.PAP_R2,\n");
		sb.append("       dr.PAP_R3,\n");
		sb.append("       dr.PAP_R4,\n");
		sb.append("       dr.RAP_R,\n");
		sb.append("       dr.RAP_R1,\n");
		sb.append("       dr.RAP_R2,\n");
		sb.append("       dr.RAP_R3,\n");
		sb.append("       dr.RAP_R4,\n");
		sb.append("       dr.RP1_R,\n");
		sb.append("       dr.RP4_R,dr.PRP_R,dr.RRP_R,dm.MP_SN\n");
		sb.append("FROM e_data_mp dm, e_mp_day_read dr, c_cons c,O_ORG O"+addgp+"\n");
		sb.append("   WHERE dm.id = dr.id\n");
		sb.append("         AND dm.area_code = dr.area_code  "+mrsectNoString+" \n");
		sb.append("         AND dm.cons_no = c.cons_no"+addgp2+"\n");
		sb.append("         AND c.ORG_NO = O.ORG_NO(+)  AND DM.MP_SN<>0 AND DM.IS_VALID = 1 \n");
		sb.append("         AND dr.area_code = ? "+addrString+"\n");
		sb.append("         AND "+dayString+"\n");
		sb.append("   AND ("+sendDQTypeString+")\n");
		sb.append(" ORDER BY dr.DATA_DATE DESC,dm.MP_SN");
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new SDQBRowMapper(), newObj);
	}
	class SDQBRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		SendDataQueryB senddataqueryb = new SendDataQueryB();
		 try { 
			 senddataqueryb.setConsNo(rs.getString("CONS_NO"));
			 senddataqueryb.setMpSn(rs.getDouble("MP_SN"));
			 senddataqueryb.setConsId(rs.getLong("CONS_ID"));
			 senddataqueryb.setMrSectNo(rs.getString("MR_SECT_NO"));
			 senddataqueryb.setConsName(rs.getString("CONS_NAME"));
			 senddataqueryb.setElecAddr(rs.getString("ELEC_ADDR"));
			 senddataqueryb.setRunCap(rs.getDouble("RUN_CAP"));
			 senddataqueryb.setOrgName(rs.getString("ORG_NAME"));
			 senddataqueryb.setAssetNo(rs.getString("ASSET_NO"));
			 senddataqueryb.settFactor(rs.getDouble("T_FACTOR"));
			 senddataqueryb.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
			 senddataqueryb.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
			 senddataqueryb.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
			 senddataqueryb.setDataId(rs.getLong("ID"));
			 senddataqueryb.setDataDate(rs.getString("DATA_DATE"));
			 senddataqueryb.setPapR(rs.getDouble("PAP_R"));
			 senddataqueryb.setPapR1(rs.getDouble("PAP_R1"));
			 senddataqueryb.setPapR2(rs.getDouble("PAP_R2"));
			 senddataqueryb.setPapR3(rs.getDouble("PAP_R3"));
			 senddataqueryb.setPapR4(rs.getDouble("PAP_R4"));
			 senddataqueryb.setRapR(rs.getDouble("RAP_R"));
			 senddataqueryb.setRapR1(rs.getDouble("RAP_R1"));
			 senddataqueryb.setRapR2(rs.getDouble("RAP_R2"));
			 senddataqueryb.setRapR3(rs.getDouble("RAP_R3"));
			 senddataqueryb.setRapR4(rs.getDouble("RAP_R4"));
			 senddataqueryb.setRp1R(rs.getDouble("RP1_R"));
			 senddataqueryb.setRp4R(rs.getDouble("RP4_R"));
			 senddataqueryb.setPrpR(rs.getDouble("PRP_R"));
			 senddataqueryb.setRrpR(rs.getDouble("RRP_R"));
		return senddataqueryb;
		 }
		catch (Exception e) {
		return null;
		 }
		}
		}

	@Override
	public List<SendDataQueryC> findSendDataQueryC(String DateStart,
			String DateEnd, String CONS_NO,PSysUser pSysUser)
			throws DBAccessException {
		addString = this.getETableAreaByConsNo(CONS_NO);
		if(addString == null){
			addString = this.getETableAreaByOrgNo(CONS_NO);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT D.ASSET_NO, TO_CHAR(E.DATA_DATE, 'yyyy-mm-dd') DATA_DATE, E.PAP_E\n" + 
				"  FROM E_DATA_MP D, E_MP_DAY_ENERGY E\n" + 
				" WHERE D.ID = E.ID AND E.AREA_CODE = ?\n" + 
				"   AND E.DATA_DATE BETWEEN TO_DATE(?, 'yyyy-mm-dd') AND\n" + 
				"       TO_DATE(?, 'yyyy-mm-dd')\n" + 
				"   AND D.CONS_NO = ?\n" + 
				" ORDER BY E.ID,E.DATA_DATE");
		logger.debug(sb.toString());
		return (List<SendDataQueryC>) super.getJdbcTemplate().query(sb.toString(),
				new Object[]{addString,DateStart,DateEnd,CONS_NO},new SDQCRowMapper());
	}
	
	class SDQCRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		SendDataQueryC senddataqueryc = new SendDataQueryC();
		 try { 
		senddataqueryc.setAssetNo(rs.getString("ASSET_NO"));
		senddataqueryc.setDataDate(rs.getString("DATA_DATE"));
		senddataqueryc.setPapE(rs.getDouble("PAP_E"));
		return senddataqueryc;
		 }
		catch (Exception e) {
		return null;
		 }
		}
		}

	public Page<SendDataQueryD> findSendDataQueryD(String CurDate,String TG_ID,long start, int limit,PSysUser pSysUser)
			throws DBAccessException {
		 addString = this.getETableAreaByUser(pSysUser);
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT C.CONS_ID,\n" +
				"       C.CONS_NO,\n" + 
				"       C.CONS_NAME,\n" + 
				"       C.ELEC_ADDR,\n" + 
				"       C.RUN_CAP,\n" + 
				"       D.TMNL_ASSET_NO,\n" + 
				"       D.ASSET_NO,\n" + 
				"       E.DATA_DATE,\n" + 
				"       E.PAP_E\n" + 
				"  FROM C_CONS C, E_DATA_MP D, E_MP_DAY_ENERGY E\n" + 
				" WHERE C.CONS_NO = D.CONS_NO AND E.AREA_CODE = ?\n" + 
				"   AND D.ID = E.ID AND E.AREA_CODE = ?\n" + 
				"   AND E.DATA_DATE = TO_DATE(?, 'YYYY-MM-DD')\n" + 
				"   AND (E.PAP_E = 0 OR E.PAP_E IS NULL)\n" + 
				"   AND C.TG_ID =?");
		logger.debug(sb.toString());
		return  super.pagingFind(sb.toString(), start, limit, new SDQDRowMapper(),
				//new Object[]{addString,CurDate,TG_ID});
				new Object[]{addString,CurDate,TG_ID});
	}
	
	class SDQDRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		SendDataQueryD senddataqueryd = new SendDataQueryD();
		 try { 
		senddataqueryd.setConsId(rs.getLong("CONS_ID"));
		senddataqueryd.setConsNo(rs.getString("CONS_NO"));
		senddataqueryd.setConsName(rs.getString("CONS_NAME"));
		senddataqueryd.setElecAddr(rs.getString("ELEC_ADDR"));
		senddataqueryd.setRunCap(rs.getDouble("RUN_CAP"));
		senddataqueryd.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
		senddataqueryd.setAssetNo(rs.getString("ASSET_NO"));
		senddataqueryd.setDataDate(rs.getDate("DATA_DATE"));
		senddataqueryd.setPapE(rs.getDouble("PAP_E"));
		return senddataqueryd;
		 }
		catch (Exception e) {
		return null;
		 }
		}
		}

//	public String getETableAreaByAssetNo(String asserNo) {
//		String rtn = null;
//		String sql = "select o.area_code from e_data_mp e,O_ORG o where  o.org_no=e.org_no and e.asset_no=?";
//
//		List<String> list = super.getJdbcTemplate().query(sql,
//				new Object[] { asserNo }, new StringRowMapper());
//		if (list.size() > 0)
//			rtn = list.get(0);
//
//		return rtn;
//
//	}
	@Override
	public EMpEnergyCurveBean findSendDataQueryE(String consNo,String asserNo,
			String dataDate,PSysUser pSysUser) throws DBAccessException { 
		addString = this.getETableAreaByConsNo(consNo);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DATA_POINT_FLAG, E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12,\n")
		.append(" E13, E14, E15, E16, E17, E18, E19, E20, E21, E22, E23, E24,\n")
		.append(" E25, E26, E27, E28, E29, E30, E31, E32, E33, E34, E35, E36,\n")
		.append(" E37, E38, E39, E40, E41, E42, E43, E44, E45, E46, E47, E48,\n")
		.append(" E49, E50, E51, E52, E53, E54, E55, E56, E57, E58, E59, E60,\n")
		.append(" E61, E62, E63, E64, E65, E66, E67, E68, E69, E70, E71, E72,\n")
		.append(" E73, E74, E75, E76, E77, E78, E79, E80, E81, E82, E83, E84,\n")
		.append(" E85, E86, E87, E88, E89, E90, E91, E92, E93, E94, E95, E96\n")
		.append("FROM e_mp_energy_curve EM JOIN e_data_mp ED ON EM.ID = ED.ID\n")
		.append("WHERE ASSET_NO = ? AND EM.AREA_CODE = ?\n")
		.append(" AND DATA_DATE = TO_DATE(?,'YYYY-MM-DD')");
		
		logger.debug(sql.toString());
		List<EMpEnergyCurveBean> list = super.pagingFindList(sql.toString(),
				0, 2, new eMpEnergyCurveMapper(), new Object[] { addString,asserNo,
						dataDate });
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	class eMpEnergyCurveMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			EMpEnergyCurveBean empenergycurvebean = new EMpEnergyCurveBean();
			try {
				empenergycurvebean.setDataPointFlag(rs.getInt("DATA_POINT_FLAG"));
				empenergycurvebean.setE1(rs.getString("E1"));
				empenergycurvebean.setE2(rs.getString("E2"));
				empenergycurvebean.setE3(rs.getString("E3"));
				empenergycurvebean.setE4(rs.getString("E4"));
				empenergycurvebean.setE5(rs.getString("E5"));
				empenergycurvebean.setE6(rs.getString("E6"));
				empenergycurvebean.setE7(rs.getString("E7"));
				empenergycurvebean.setE8(rs.getString("E8"));
				empenergycurvebean.setE9(rs.getString("E9"));
				empenergycurvebean.setE10(rs.getString("E10"));
				empenergycurvebean.setE11(rs.getString("E11"));
				empenergycurvebean.setE12(rs.getString("E12"));
				empenergycurvebean.setE13(rs.getString("E13"));
				empenergycurvebean.setE14(rs.getString("E14"));
				empenergycurvebean.setE15(rs.getString("E15"));
				empenergycurvebean.setE16(rs.getString("E16"));
				empenergycurvebean.setE17(rs.getString("E17"));
				empenergycurvebean.setE18(rs.getString("E18"));
				empenergycurvebean.setE19(rs.getString("E19"));
				empenergycurvebean.setE20(rs.getString("E20"));
				empenergycurvebean.setE21(rs.getString("E21"));
				empenergycurvebean.setE22(rs.getString("E22"));
				empenergycurvebean.setE23(rs.getString("E23"));
				empenergycurvebean.setE24(rs.getString("E24"));
				empenergycurvebean.setE25(rs.getString("E25"));
				empenergycurvebean.setE26(rs.getString("E26"));
				empenergycurvebean.setE27(rs.getString("E27"));
				empenergycurvebean.setE28(rs.getString("E28"));
				empenergycurvebean.setE29(rs.getString("E29"));
				empenergycurvebean.setE30(rs.getString("E30"));
				empenergycurvebean.setE31(rs.getString("E31"));
				empenergycurvebean.setE32(rs.getString("E32"));
				empenergycurvebean.setE33(rs.getString("E33"));
				empenergycurvebean.setE34(rs.getString("E34"));
				empenergycurvebean.setE35(rs.getString("E35"));
				empenergycurvebean.setE36(rs.getString("E36"));
				empenergycurvebean.setE37(rs.getString("E37"));
				empenergycurvebean.setE38(rs.getString("E38"));
				empenergycurvebean.setE39(rs.getString("E39"));
				empenergycurvebean.setE40(rs.getString("E40"));
				empenergycurvebean.setE41(rs.getString("E41"));
				empenergycurvebean.setE42(rs.getString("E42"));
				empenergycurvebean.setE43(rs.getString("E43"));
				empenergycurvebean.setE44(rs.getString("E44"));
				empenergycurvebean.setE45(rs.getString("E45"));
				empenergycurvebean.setE46(rs.getString("E46"));
				empenergycurvebean.setE47(rs.getString("E47"));
				empenergycurvebean.setE48(rs.getString("E48"));
				empenergycurvebean.setE49(rs.getString("E49"));
				empenergycurvebean.setE50(rs.getString("E50"));
				empenergycurvebean.setE51(rs.getString("E51"));
				empenergycurvebean.setE52(rs.getString("E52"));
				empenergycurvebean.setE53(rs.getString("E53"));
				empenergycurvebean.setE54(rs.getString("E54"));
				empenergycurvebean.setE55(rs.getString("E55"));
				empenergycurvebean.setE56(rs.getString("E56"));
				empenergycurvebean.setE57(rs.getString("E57"));
				empenergycurvebean.setE58(rs.getString("E58"));
				empenergycurvebean.setE59(rs.getString("E59"));
				empenergycurvebean.setE60(rs.getString("E60"));
				empenergycurvebean.setE61(rs.getString("E61"));
				empenergycurvebean.setE62(rs.getString("E62"));
				empenergycurvebean.setE63(rs.getString("E63"));
				empenergycurvebean.setE64(rs.getString("E64"));
				empenergycurvebean.setE65(rs.getString("E65"));
				empenergycurvebean.setE66(rs.getString("E66"));
				empenergycurvebean.setE67(rs.getString("E67"));
				empenergycurvebean.setE68(rs.getString("E68"));
				empenergycurvebean.setE69(rs.getString("E69"));
				empenergycurvebean.setE70(rs.getString("E70"));
				empenergycurvebean.setE71(rs.getString("E71"));
				empenergycurvebean.setE72(rs.getString("E72"));
				empenergycurvebean.setE73(rs.getString("E73"));
				empenergycurvebean.setE74(rs.getString("E74"));
				empenergycurvebean.setE75(rs.getString("E75"));
				empenergycurvebean.setE76(rs.getString("E76"));
				empenergycurvebean.setE77(rs.getString("E77"));
				empenergycurvebean.setE78(rs.getString("E78"));
				empenergycurvebean.setE79(rs.getString("E79"));
				empenergycurvebean.setE80(rs.getString("E80"));
				empenergycurvebean.setE81(rs.getString("E81"));
				empenergycurvebean.setE82(rs.getString("E82"));
				empenergycurvebean.setE83(rs.getString("E83"));
				empenergycurvebean.setE84(rs.getString("E84"));
				empenergycurvebean.setE85(rs.getString("E85"));
				empenergycurvebean.setE86(rs.getString("E86"));
				empenergycurvebean.setE87(rs.getString("E87"));
				empenergycurvebean.setE88(rs.getString("E88"));
				empenergycurvebean.setE89(rs.getString("E89"));
				empenergycurvebean.setE90(rs.getString("E90"));
				empenergycurvebean.setE91(rs.getString("E91"));
				empenergycurvebean.setE92(rs.getString("E92"));
				empenergycurvebean.setE93(rs.getString("E93"));
				empenergycurvebean.setE94(rs.getString("E94"));
				empenergycurvebean.setE95(rs.getString("E95"));
				empenergycurvebean.setE96(rs.getString("E96"));				
				return empenergycurvebean;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public List<SendDataQueryF> findSendDataQueryF(String DateStart,
			String DateEnd, String CONS_NO, String TG_ID,PSysUser pSysUser)
			throws DBAccessException {
		addString = this.getETableAreaByConsNo(CONS_NO);
		if(addString == null){
			addString = this.getETableAreaByOrgNo(CONS_NO);
		}
		StringBuffer sb = new StringBuffer();
		String DateStart2 = DateStart;
		String DateEnd2 = DateEnd;
		sb.append(

				"SELECT Z.DATA_DATE, Z.PAP_EZ, F.PAP_EF, (Z.PAP_EZ - F.PAP_EF) PAP_EL\n" +
				"  FROM (SELECT TO_CHAR(E.DATA_DATE, 'YYYY-MM-DD') DATA_DATE,\n" + 
				"               SUM(E.PAP_E) PAP_EZ\n" + 
				"          FROM E_DATA_MP D, E_MP_DAY_ENERGY E\n" + 
				"         WHERE D.ID = E.ID\n AND E.AREA_CODE = ?" + 
				//"         WHERE D.ID = E.DATA_ID\n" + 
				"           AND E.DATA_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND\n" + 
				"               TO_DATE(?, 'YYYY-MM-DD')\n" + 
				"           AND D.CONS_NO = ?\n" + 
				"         GROUP BY E.DATA_DATE) Z,\n" + 
				"       (SELECT TO_CHAR(E.DATA_DATE, 'YYYY-MM-DD') DATA_DATE,\n" + 
				"               SUM(E.PAP_E) PAP_EF\n" + 
				"          FROM C_CONS C, E_DATA_MP D, E_MP_DAY_ENERGY E\n" + 
				"         WHERE C.CONS_NO = D.CONS_NO\n" + 
				"           AND D.ID = E.ID\n AND E.AREA_CODE = ?" + 
				//"           AND D.ID = E.DATA_ID\n" +
				"           AND E.DATA_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND\n" + 
				"               TO_DATE(?, 'YYYY-MM-DD')\n" + 
				"           AND C.TG_ID = ?\n" + 
				"         GROUP BY E.DATA_DATE) F\n" + 
				" WHERE Z.DATA_DATE = F.DATA_DATE(+)\n" + 
				" ORDER BY Z.DATA_DATE");
		logger.debug(sb.toString());
		return (List<SendDataQueryF>) super.getJdbcTemplate().query(sb.toString(),
				new Object[]{addString,DateStart,DateEnd,CONS_NO,addString,DateStart2,DateEnd2,TG_ID},new SDQFRowMapper());
				//new Object[]{DateStart,DateEnd,CONS_NO,DateStart2,DateEnd2,TG_ID},new SDQFRowMapper());
	}
	
	class SDQFRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		SendDataQueryF senddataqueryf = new SendDataQueryF();
		 try { 
		senddataqueryf.setDataDate(rs.getString("DATA_DATE"));
		senddataqueryf.setPapEz(rs.getLong("PAP_EZ"));
		senddataqueryf.setPapEf(rs.getLong("PAP_EF"));
		senddataqueryf.setPapEl(rs.getLong("PAP_EL"));
		return senddataqueryf;
		 }
		catch (Exception e) {
		return null;
		 }
		}
		}
	
	// 简单RowMapper
	class StringRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			String s = null;
			try {
				s = rs.getString("AREA_CODE");
				return s;
			} catch (Exception e) {
				return null;
			}
		}
	}
//抄表失败情况下调用的方法
	@Override
	public Page<SendDataQueryBFail> findSendDataQueryBFail(String MR_SECT_NO,
			String sendDataQueryFlag, String addr, String sendDQOrgType,
			String sendDQType, String DateStart, String DateEnd,
			String CONS_NO, String TG_ID, int start, int limit,
			PSysUser pSysUser) throws DBAccessException {
		String mrsectNoString = ""; 
		 addgp = "";
		 addgp2 = "";
		if(MR_SECT_NO.equals("")){
			mrsectNoString = ""; 
		}else{
			mrsectNoString = " AND C.MR_SECT_NO = "+MR_SECT_NO;
		}
		if ("usr".equals(sendDQType) || "usr1".equals(sendDQType) || "usr2".equals(sendDQType)) {
			addString = this.getETableAreaByConsNo(CONS_NO);
		}
		if ("org".equals(sendDQType)) {
			addString = this.getETableAreaByOrgNo(CONS_NO);
		}
		if ("sub".equals(sendDQType)) {
			addString = this.getETableAreaBySubId(CONS_NO);
		}
		if ("line".equals(sendDQType)) {
			addString = this.getETableAreaByLineId(CONS_NO);
		}
		String dayString = "1=1";
		if("onlyday".equals(sendDataQueryFlag)){
			dayString = "(dr.STAT_DATE = TO_DATE(?, 'yyyy-MM-dd') or dr.STAT_DATE = TO_DATE(?, 'yyyy-MM-dd'))";
		}else if("everyday".equals(sendDataQueryFlag)){
			dayString = "dr.STAT_DATE BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd')";
		}

		StringBuffer sb = new StringBuffer();
		//DateStart = DateStart+" 00:00:00";
		//DateEnd = DateEnd+" 23:59:59";	
		String addrString = "";
		String sendDQTypeString = "";
		Object[] newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO, TG_ID };
		if (TG_ID == null) {
			TG_ID = "";
		};
		if(CONS_NO == null){
			CONS_NO = "";
				};
				if(sendDQType.equals("org") && sendDQOrgType.equals("06")){
			sendDQTypeString = "C.ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if(sendDQType.equals("org") && sendDQOrgType.equals("04") || sendDQOrgType.equals("05")){
			sendDQTypeString = "O.P_ORG_NO = ? OR O.ORG_NO = ? ";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if(sendDQType.equals("org") && sendDQOrgType.equals("03")){
			sendDQTypeString = "O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?) OR O.P_ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if (sendDQType.equals("sub")) {
			sendDQTypeString = "C.SUBS_ID=?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO };
		} else if (sendDQType.equals("line")) {
			sendDQTypeString = "C.LINE_ID=?";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}else if(sendDQType.equals("cgp")){
			sendDQTypeString = "GR.GROUP_NO = ?";
			addgp = ",T_TMNL_GROUP_DETAIL GR ";
			addgp2 = " AND GR.TMNL_ASSET_NO = DM.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}else if(sendDQType.equals("ugp")){
			sendDQTypeString = "GR.GROUP_NO = ?";
			addgp2 = " AND GR.TMNL_ASSET_NO = DM.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
			addgp = ",R_USER_GROUP_DETAIL GR ";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		} else if (sendDQType.equals("usr2") && !addr.equals("")) {
			addrString = " AND dm.TMNL_ASSET_NO=?";
			sendDQTypeString = "C.TG_ID = ?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, TG_ID};
		} else if(sendDQType.equals("usr1") && !addr.equals("")){
			addrString = " AND dm.TMNL_ASSET_NO=?";
			sendDQTypeString = "C.CONS_NO = ?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO};
		} else if (sendDQType.equals("usr2") && addr.equals("")) {
			sendDQTypeString = "C.TG_ID = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, TG_ID};
		} else if(sendDQType.equals("usr1") && addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO};
		}else if(sendDQType.equals("usr") && addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO,TG_ID};
		}else if(sendDQType.equals("usr") && !addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
			addrString = " AND D.TMNL_ASSET_NO=?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO,TG_ID};
		}
				sb.append("SELECT C.CONS_NO,C.CONS_ID,C.MR_SECT_NO,\n");
				sb.append("       C.CONS_NAME,\n");
				sb.append("       C.ELEC_ADDR,\n");
				sb.append("       C.RUN_CAP,\n");
				sb.append("       O.ORG_NAME,\n");
				sb.append("       Dm.ASSET_NO,\n");
				sb.append("       Dm.CT*Dm.PT AS T_FACTOR,\n");
				sb.append("       dm.TMNL_ASSET_NO,\n");
				sb.append("       dm.TERMINAL_ADDR,\n");
				sb.append("       dm.cis_tmnl_asset_no AS CIS_ASSET_NO,\n");
				sb.append("       TO_CHAR(dr.STAT_DATE, 'yyyy-mm-dd') STAT_DATE\n");
				sb.append("FROM e_data_mp dm, A_READ_FAIL_LIST dr, c_cons c,O_ORG O"+addgp+" ");
				sb.append(" WHERE dm.cons_no= dr.cons_no AND dm.tmnl_asset_no = dm.tmnl_asset_no AND dm.mp_sn = dr.mp_sn "+addgp2+"\n");
				sb.append("        AND dm.cons_no = c.cons_no  "+mrsectNoString+" \n");
				sb.append("        AND c.ORG_NO = O.ORG_NO(+)");
		sb.append("         AND dm.area_code = ? "+addrString+"\n");
		sb.append("         AND "+dayString+"\n");
		sb.append("   AND ("+sendDQTypeString+")\n");
		sb.append(" ORDER BY dr.STAT_DATE DESC");
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new SendDataQueryFailRowMapper(), newObj);
	
	}
	@Override
	public Page<SendDataQueryDay> findSendDataQueryDay(String fieldConsNo,String fieldassetNo,String MR_SECT_NO,
			String sendDataQueryFlag, String addr, String sendDQOrgType,
			String sendDQType, String DateStart, String DateEnd,
			String CONS_NO, String TG_ID, int start, int limit,
			PSysUser pSysUser) throws DBAccessException {
		String mrsectNoString = ""; 
		 addgp = "";
		 addgp2 = "";
		if(MR_SECT_NO.equals("")){
			mrsectNoString = ""; 
		}else{
			mrsectNoString = " AND C.MR_SECT_NO = "+MR_SECT_NO;
		}
		if ("usr".equals(sendDQType) || "usr1".equals(sendDQType) || "usr2".equals(sendDQType)) {
			addString = this.getETableAreaByConsNo(CONS_NO);
		}
		if ("org".equals(sendDQType)) {
			addString = this.getETableAreaByOrgNo(CONS_NO);
		}
		if ("sub".equals(sendDQType)) {
			addString = this.getETableAreaBySubId(CONS_NO);
		}
		if ("line".equals(sendDQType)) {
			addString = this.getETableAreaByLineId(CONS_NO);
		}
		String dayString = "1=1";
		if("onlyday".equals(sendDataQueryFlag)){
			dayString = "(dr.DATA_TIME = TO_DATE(?, 'yyyy-MM-dd') or dr.DATA_TIME = TO_DATE(?, 'yyyy-MM-dd'))";
		}else if("everyday".equals(sendDataQueryFlag)){
			dayString = " dr.data_time > TO_DATE(?, 'yyyy-MM-dd') and dr.data_time < TO_DATE(?, 'yyyy-MM-dd')+1";
		}

		StringBuffer sb = new StringBuffer();
		//DateStart = DateStart+" 00:00:00";
		//DateEnd = DateEnd+" 23:59:59";	
		String addrString = "";
		String sendDQTypeString = "";
		Object[] newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO, TG_ID };
		if (TG_ID == null) {
			TG_ID = "";
		};
		if(CONS_NO == null){
			CONS_NO = "";
				};
				if(sendDQType.equals("org") && sendDQOrgType.equals("06")){
			sendDQTypeString = "C.ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if(sendDQType.equals("org") && sendDQOrgType.equals("04") || sendDQOrgType.equals("05")){
			sendDQTypeString = "O.P_ORG_NO = ?";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if(sendDQType.equals("org") && sendDQOrgType.equals("03")){
			sendDQTypeString = "O.P_ORG_NO in (select ORG_NO from o_org where P_ORG_NO=?)";
			newObj = new Object[]{addString,DateStart,DateEnd,CONS_NO};
			//newObj = new Object[]{DateStart,DateEnd,CONS_NO};
		}else if (sendDQType.equals("sub")) {
			sendDQTypeString = "C.SUBS_ID=?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO };
		} else if (sendDQType.equals("line")) {
			sendDQTypeString = "C.LINE_ID=?";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}else if(sendDQType.equals("cgp")){
			sendDQTypeString = "GR.GROUP_NO = ?";
			addgp2 = " AND GR.TMNL_ASSET_NO = DM.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
			addgp = ",T_TMNL_GROUP_DETAIL GR ";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}else if(sendDQType.equals("ugp")){
			sendDQTypeString = "GR.GROUP_NO = ?";
			addgp = ",R_USER_GROUP_DETAIL GR ";
			addgp2 = " AND GR.TMNL_ASSET_NO = DM.TMNL_ASSET_NO AND GR.CONS_NO = C.CONS_NO ";
			newObj = new Object[] {addString, DateStart, DateEnd, CONS_NO };
		}  else if (sendDQType.equals("usr2") && !addr.equals("")) {
			addrString = " AND dm.TMNL_ASSET_NO=?";
			sendDQTypeString = "C.TG_ID = ?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, TG_ID};
		} else if(sendDQType.equals("usr1") && !addr.equals("")){
			addrString = " AND dm.TMNL_ASSET_NO=?";
			sendDQTypeString = "C.CONS_NO = ?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO};
		} else if (sendDQType.equals("usr2") && addr.equals("")) {
			sendDQTypeString = "C.TG_ID = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, TG_ID};
		} else if(sendDQType.equals("usr1") && addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO};
		}else if(sendDQType.equals("usr") && addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
			newObj = new Object[] { addString,DateStart, DateEnd, CONS_NO,TG_ID};
		}else if(sendDQType.equals("usr") && !addr.equals("")){
			sendDQTypeString = "C.CONS_NO = ? or C.TG_ID = ?";
			addrString = " AND D.TMNL_ASSET_NO=?";
			newObj = new Object[] { addString,addr,DateStart, DateEnd, CONS_NO,TG_ID};
		}
				if(!fieldConsNo.equals("") && !fieldassetNo.equals("")){
					sendDQTypeString = "Dm.ASSET_NO = ?";
					addrString = " AND c.cons_no = ? ";	
					newObj = new Object[] {addString,fieldConsNo,DateStart,DateEnd,fieldassetNo};
				}
		sb.append("SELECT c.CONS_NO,c.CONS_ID,\n");
		sb.append("       c.CONS_NAME,C.MR_SECT_NO,\n");
		sb.append("       c.ELEC_ADDR,\n");
		sb.append("       c.RUN_CAP,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       Dm.ASSET_NO,\n");
		sb.append("       Dm.CT*Dm.PT AS T_FACTOR,\n");
		sb.append("       dm.TMNL_ASSET_NO,\n");
		sb.append("       dm.TERMINAL_ADDR,\n");
		sb.append("       dm.cis_tmnl_asset_no AS CIS_ASSET_NO,\n");
		sb.append("       dr.ID,\n");
		sb.append("       dr.DATA_TIME DATA_DATE,\n");
		sb.append("       dr.PAP_R,\n");
		sb.append("       dr.PAP_R1,\n");
		sb.append("       dr.PAP_R2,\n");
		sb.append("       dr.PAP_R3,\n");
		sb.append("       dr.PAP_R4,\n");
		sb.append("       dr.RAP_R,\n");
		sb.append("       dr.RAP_R1,\n");
		sb.append("       dr.RAP_R2,\n");
		sb.append("       dr.RAP_R3,\n");
		sb.append("       dr.RAP_R4,\n");
		sb.append("       dr.RP1_R,\n");
		sb.append("       dr.RP4_R,dr.PRP_R\n");
		sb.append("FROM e_data_mp dm, e_mp_read dr, c_cons c,O_ORG O"+addgp+" \n");
		sb.append("   WHERE dm.id = dr.id\n");
		sb.append("         AND dm.area_code = dr.area_code  "+mrsectNoString+" \n");
		sb.append("         AND dm.cons_no = c.cons_no "+addgp2+"\n");
		sb.append("         AND c.ORG_NO = O.ORG_NO(+)\n");
		sb.append("         AND dr.area_code = ? "+addrString+"\n");
		sb.append("         AND "+dayString+"\n");
		sb.append("   AND ("+sendDQTypeString+")\n");
		sb.append(" ORDER BY dr.DATA_TIME DESC");
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new SendDataQueryDayRowMapper(), newObj);
	}
}
class SendDataQueryFailRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	SendDataQueryBFail senddataqueryfail = new SendDataQueryBFail();
	 try { 
	senddataqueryfail.setConsNo(rs.getString("CONS_NO"));
	senddataqueryfail.setConsId(rs.getLong("CONS_ID"));
	senddataqueryfail.setMrSectNo(rs.getString("MR_SECT_NO"));
	senddataqueryfail.setConsName(rs.getString("CONS_NAME"));
	senddataqueryfail.setElecAddr(rs.getString("ELEC_ADDR"));
	senddataqueryfail.setRunCap(rs.getDouble("RUN_CAP"));
	senddataqueryfail.setOrgName(rs.getString("ORG_NAME"));
	senddataqueryfail.setAssetNo(rs.getString("ASSET_NO"));
	senddataqueryfail.settFactor(rs.getLong("T_FACTOR"));
	senddataqueryfail.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
	senddataqueryfail.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
	senddataqueryfail.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
	senddataqueryfail.setStatDate(rs.getString("STAT_DATE"));
	return senddataqueryfail;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}
class SendDataQueryDayRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	SendDataQueryDay senddataqueryday = new SendDataQueryDay();
	 try { 
	senddataqueryday.setConsNo(rs.getString("CONS_NO"));
	senddataqueryday.setConsId(rs.getLong("CONS_ID"));
	senddataqueryday.setConsName(rs.getString("CONS_NAME"));
	senddataqueryday.setMrSectNo(rs.getString("MR_SECT_NO"));
	senddataqueryday.setElecAddr(rs.getString("ELEC_ADDR"));
	senddataqueryday.setRunCap(rs.getDouble("RUN_CAP"));
	senddataqueryday.setOrgName(rs.getString("ORG_NAME"));
	senddataqueryday.setAssetNo(rs.getString("ASSET_NO"));
	senddataqueryday.settFactor(rs.getLong("T_FACTOR"));
	senddataqueryday.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
	senddataqueryday.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
	senddataqueryday.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
	senddataqueryday.setId(rs.getLong("ID"));
	senddataqueryday.setDataDate(rs.getTimestamp("DATA_DATE"));
	senddataqueryday.setPapR(rs.getDouble("PAP_R"));
	senddataqueryday.setPapR1(rs.getDouble("PAP_R1"));
	senddataqueryday.setPapR2(rs.getDouble("PAP_R2"));
	senddataqueryday.setPapR3(rs.getDouble("PAP_R3"));
	senddataqueryday.setPapR4(rs.getDouble("PAP_R4"));
	senddataqueryday.setRapR(rs.getDouble("RAP_R"));
	senddataqueryday.setRapR1(rs.getDouble("RAP_R1"));
	senddataqueryday.setRapR2(rs.getDouble("RAP_R2"));
	senddataqueryday.setRapR3(rs.getDouble("RAP_R3"));
	senddataqueryday.setRapR4(rs.getDouble("RAP_R4"));
	senddataqueryday.setRp1R(rs.getDouble("RP1_R"));
	senddataqueryday.setRp4R(rs.getDouble("RP4_R"));
	senddataqueryday.setPrpR(rs.getDouble("PRP_R"));
	return senddataqueryday;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}