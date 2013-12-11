package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.qrystat.querycollpoint.AutoSendQuery;
import com.nari.qrystat.querycollpoint.IAutoSendQueryDao;
import com.nari.support.Page;

public class AutoSendQueryDaoImpl extends JdbcBaseDAOImpl implements IAutoSendQueryDao {
	Logger logger = Logger.getLogger(AutoSendQueryDaoImpl.class);
	
	@Override
	public Page<AutoSendQuery> findASQuery(String tgId ,String orgType, String assetNo,String nodeType,String nodeValue,String consType, long start, int limit) {
		StringBuffer sb = new StringBuffer();
		
		String d = "";
		String r = "";
		List<Object> parms = new ArrayList<Object>();
		if (nodeType.equals("ugp")) {
			r = "R_USER_GROUP_DETAIL R,";
			d = "AND C.CONS_NO = R.CONS_NO";
		}
		if (nodeType.equals("cgp")) {
			r = "T_TMNL_GROUP_DETAIL D,";
			d = "AND C.CONS_NO = D.CONS_NO";
		}
		sb.append(
				"SELECT O.ORG_NAME,\n" +
				"       C.CONS_ID,\n" + 
				"       C.CONS_NO,\n" + 
				"       C.CONS_NAME,\n" + 
				"       C.ELEC_ADDR,\n" + 
				"       C.CONTRACT_CAP,\n" + 
				"       C.MR_SECT_NO,\n" + 
				"       E.ELEC_TYPE,\n" + 
				"       M.ASSET_NO,\n" + 
				"       M.BAUDRATE,\n" + 
				"       M.COMM_NO,\n" + 
				"       M.COMM_MODE,\n" + 
				"       M.COMM_ADDR1,\n" + 
				"       M.COMM_ADDR2,\n" + 
				"       M.INST_LOC,\n" + 
				"       M.INST_DATE,\n" + 
				"       M.T_FACTOR,\n" + 
				"       TG.TG_NAME,\n" + 
				"       (SELECT RTU.TERMINAL_ADDR  FROM VW_TMNL_RUN RTU  WHERE RTU.TERMINAL_ID = M.TMNL_ASSET_NO) AS TERMINAL_ADDR,\n" + 
				"       M.REG_STATUS,\n" + 
				"       M.REG_SN\n" + 
				"  FROM C_CONS C, O_ORG O, VW_ELEC_TYPE_CODE E," + r + " C_METER M ,VW_TMNL_RUN R , G_TG  TG\n" + 
				" WHERE C.ORG_NO = O.ORG_NO  \n  " + d + 
				"   AND C.ELEC_TYPE_CODE = E.ELEC_TYPE_CODE  \n AND C.CONS_NO = R.CONS_NO(+)  \n  AND TG.TG_ID = C.TG_ID  \n" + 
				"   AND C.CONS_NO = M.CONS_NO    \n" );
		
		sb.append("   AND C.CONS_TYPE = 5 ");
		if (nodeType.equals("org")) {
			//省公司操作员不需要条件 市级以下添加查询条件
			if(!orgType.equals("02")){
				sb.append(" AND C.ORG_NO like ?||'%' ");
				parms.add(nodeValue);
			}
		} else if (nodeType.equals("sub")) {
			sb.append(" AND C.SUBS_ID=? \n");
			parms.add(nodeValue);
		} else if (nodeType.equals("line")) {
			sb.append(" AND C.LINE_ID=? \n");
			parms.add(nodeValue);
		} else if (nodeType.equals("ugp")) {
			sb.append(" AND R.GROUP_NO=? \n");
			parms.add(nodeValue);
		} else if (nodeType.equals("cgp")) {
			sb.append(" AND D.GROUP_NO=? \n");
			parms.add(nodeValue);
		} else if(consType != null && consType.equals("5")){
			sb.append(" AND C.CONS_NO = ? \n");
			parms.add(nodeValue);
			if(assetNo != null && !assetNo.equals("")){
				sb.append(" AND M.TMNL_ASSET_NO = ?  \n");
				parms.add(assetNo);
			}
		}else if(tgId != null && !tgId.equals("")){
			sb.append(" AND TG.TG_ID = ?  \n");
			parms.add(tgId);
		}
		sb.append(" order by C.ORG_NO,C.CONS_NO\n"); 
		logger.debug(sb.toString());

		return super.pagingFind(sb.toString(), start, limit, new ASQRowMapper(),parms.toArray()	);
	}
	class ASQRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		AutoSendQuery autosendquery = new AutoSendQuery();
		 try { 
		autosendquery.setOrgName(rs.getString("ORG_NAME"));
		autosendquery.setConsId(rs.getLong("CONS_ID"));
		autosendquery.setConsNo(rs.getString("CONS_NO"));
		autosendquery.setConsName(rs.getString("CONS_NAME"));
		autosendquery.setElecAddr(rs.getString("ELEC_ADDR"));
		autosendquery.setContractCap(rs.getDouble("CONTRACT_CAP"));
		autosendquery.setMrSectNo(rs.getString("MR_SECT_NO"));
		autosendquery.setElecType(rs.getString("ELEC_TYPE"));
		autosendquery.setAssetNo(rs.getString("ASSET_NO"));
		autosendquery.setBaudrate(rs.getString("BAUDRATE"));
		autosendquery.setCommNo(rs.getString("COMM_NO"));
		if(rs.getString("COMM_MODE") == null){
			autosendquery.setCommMode("");
		}else{
		autosendquery.setCommMode(rs.getString("COMM_MODE"));
		}
		autosendquery.setCommAddr1(rs.getString("COMM_ADDR1"));
		autosendquery.setCommAddr2(rs.getString("COMM_ADDR2"));
		autosendquery.setInstLoc(rs.getString("INST_LOC"));
		autosendquery.setInstDate(rs.getTimestamp("INST_DATE"));
		autosendquery.settFactor(rs.getDouble("T_FACTOR"));
		autosendquery.setTmnlAssetNo(rs.getString("TERMINAL_ADDR"));
		autosendquery.setRegStatus(rs.getByte("REG_STATUS"));
		autosendquery.setRegSn(rs.getInt("REG_SN"));
		autosendquery.setTgName(rs.getString("TG_NAME"));
		return autosendquery;
		 }
		catch (Exception e) {
		return null;
		 }
		}
		}
	@SuppressWarnings("unchecked")
	@Override
	public List<AutoSendQuery> findtmnlAssetNo(String tgId) {

		StringBuffer sb=new StringBuffer();
		sb.append("SELECT distinct M.TMNL_ASSET_NO\n");
		sb.append("  FROM C_CONS C, C_METER M\n");
		sb.append(" WHERE C.CONS_NO = M.CONS_NO\n");
		sb.append("   AND C.TG_ID = ?");

		String sql=sb.toString();
		logger.debug(sb.toString());
		return super.getJdbcTemplate().query(sql, new Object[] { tgId },new findtmnlAssetNoRowMapper());
	}
	
	
	class findtmnlAssetNoRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		AutoSendQuery autosendquery = new AutoSendQuery();
		 try { 
		autosendquery.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
		return autosendquery;
		 }
		catch (Exception e) {
		return null;
		 }
		}
		}
}
