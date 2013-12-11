package com.nari.runman.feildman.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.runman.feildman.FetchInfoBean;
import com.nari.runman.feildman.TerminalInfoBean;
import com.nari.runman.feildman.TerminalListDao;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.terminalparam.CallValueBean;
import com.nari.terminalparam.MpMeterInfoBean;
import com.nari.terminalparam.TTmnlParam;

public class TerminalListDaoImpl extends JdbcBaseDAOImpl implements
		TerminalListDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<FetchInfoBean> FindTerminalList(String proNo, String terminalNO) {
		StringBuffer sb = new StringBuffer();
		String[] para = { terminalNO, proNo + "%"};

		sb.append("SELECT\n")
		.append("  PROT_ITEM_NO,\n")
		.append("  BLOCK_SN,\n")
		.append("  INNER_BLOCK_SN,\n")
		.append("  TMNL_ASSET_NO,\n")
		.append("  CURRENT_VALUE,\n")
		.append("  STATUS_CODE,\n")
		.append("  SEND_TIME\n")
		.append("FROM\n")				
		.append("  T_TMNL_PARAM\n")
		.append("WHERE\n")
		.append("  TMNL_ASSET_NO = ?\n")
		.append("AND\n")
		.append("  PROT_ITEM_NO LIKE ?\n")
		.append("ORDER BY\n")
		.append("  BLOCK_SN, INNER_BLOCK_SN");

		return this.getJdbcTemplate().query(sb.toString(),para, new dtMapper());
	}

	// TTmnlParam的RowMapper类
	class dtMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			FetchInfoBean tp = new FetchInfoBean();
			try {
				tp.setCurrentValue(rs.getString("CURRENT_VALUE"));
				tp.setProtItemNo(rs.getString("PROT_ITEM_NO"));
				tp.setBlockSn(rs.getString("BLOCK_SN"));
				tp.setInnerBlockSn(rs.getInt("INNER_BLOCK_SN"));
				tp.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tp.setStatusCode(rs.getString("STATUS_CODE"));
				tp.setSendTime(rs.getDate("SEND_TIME"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

	@Override
	public int deleteTerminalInfo(String proNo, String terminalNO) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		String[] para = { terminalNO, proNo + "%"};
		sb.append("DELETE t_tmnl_param WHERE tmnl_asset_no = ? and prot_item_no LIKE ?");
		return super.deletePara(sb.toString(), para);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalInfoBean> searchTerminalList(String nodeType,
			String nodeValue, PSysUser userInfo, String protCode) {
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		sb.append("SELECT C.CONS_NO,\n")
		.append("       R.TMNL_ASSET_NO,\n")
		.append("       C.CONS_NAME,\n")
		.append("       O.ORG_NAME,\n")
		.append("       R.TERMINAL_ADDR,\n")
		.append("       R.PROTOCOL_CODE\n")
		.append("  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n");
		if ("cgp".equals(nodeType)){
			sb.append(",        T_TMNL_GROUP_DETAIL G\n");
		} else if ("ugp".equals(nodeType)){
			sb.append(",        R_USER_GROUP_DETAIL G\n");
		}
		sb.append("WHERE \n");
		if ("org".equals(nodeType)){
			paraList.add(nodeValue + "%");
			sb.append("   C.ORG_NO like ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if("line".equals(nodeType)){
			paraList.add(nodeValue);
			sb.append("   C.LINE_ID = ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		}else if("ugp".equals(nodeType) || "cgp".equals(nodeType)){
			paraList.add(nodeValue);
			sb.append("   G.CONS_NO = C.CONS_NO\n");
			sb.append("   AND G.TMNL_ASSET_NO = R.TMNL_ASSET_NO\n");
			sb.append("   AND G.GROUP_NO = ?");
			sb.append(SysPrivilige.addPri(userInfo, "C", "R", 7));
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
			paraList.add(userInfo.getStaffNo());
		} else if("usr".equals(nodeType)){
			paraList.add(nodeValue);
			sb.append("   R.TMNL_ASSET_NO = ?");
		} else {
			List<TerminalInfoBean> listInfo = new ArrayList<TerminalInfoBean>();
			return listInfo;
		}
		paraList.add(protCode);
		sb.append("   AND C.CONS_NO = R.CONS_NO\n")
		.append("     AND R.PROTOCOL_CODE = ?\n")
		.append("     AND C.AREA_NO = O.ORG_NO\n")
		.append("     ORDER BY R.TMNL_ASSET_NO");
		return this.getJdbcTemplate().query(sb.toString(),paraList.toArray(), new tiMapper());
//		return super.pagingFindList(sb.toString(), 0, 100, new tiMapper(), paraList.toArray());
	}
	
	// terminalInfoBean的RowMapper类
	class tiMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalInfoBean tp = new TerminalInfoBean();
			try {
				tp.setConsName(rs.getString("CONS_NAME"));
				tp.setConsNO(rs.getString("CONS_NO"));
				tp.setOrgName(rs.getString("ORG_NAME"));
				tp.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tp.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tp.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

	// terminalInfoBean的RowMapper类
	class meterMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			MpMeterInfoBean tp = new MpMeterInfoBean();
			try {
				tp.setMeterAssetNo(rs.getString("ASSET_NO"));
				tp.setMeterId(rs.getString("METER_ID"));
				tp.setMpId(rs.getString("MP_ID"));
				tp.setMpName(rs.getString("MP_NAME"));
				tp.setMpNo(rs.getString("MP_NO"));
				tp.setMpSn(rs.getInt("MP_SN"));
				tp.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MpMeterInfoBean> getTermMeterInfo(String termAssertNo) {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT CMMPR.METER_ID,\n");
		sb.append("       CM.ASSET_NO,\n");
		sb.append("       CMMPR.MP_ID,\n");
		sb.append("       C.MP_NO,\n");
		sb.append("       C.MP_NAME,\n");
		sb.append("       E.TMNL_ASSET_NO,\n");
		sb.append("       E.MP_SN\n");
		sb.append("  FROM C_METER_MP_RELA CMMPR, C_METER CM, C_MP C, E_DATA_MP E\n");
		sb.append(" WHERE CMMPR.METER_ID = CM.METER_ID\n");
		sb.append("   AND CMMPR.MP_ID = C.MP_ID\n");
		sb.append("   AND E.TMNL_ASSET_NO = ?\n");
		sb.append("   AND E.METER_ID = CMMPR.METER_ID\n");
		sb.append("   AND E.IS_VALID = '1'\n");
		sb.append(" ORDER BY E.MP_SN ASC");

		return this.getJdbcTemplate().query(sb.toString(), new Object[] {termAssertNo}, new meterMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalInfoBean> searchTerminalInfo(String termAssertNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT C.CONS_NO,\n");
		sb.append("       R.TMNL_ASSET_NO,\n");
		sb.append("       C.CONS_NAME,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       R.TERMINAL_ADDR,\n");
		sb.append("       P.PROTOCOL_NAME,\n");
		sb.append("       R.PROTOCOL_CODE\n");
		sb.append("  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R, VW_PROTOCOL_CODE P\n");
		sb.append("  WHERE R.TMNL_ASSET_NO = ?");
		sb.append("   AND C.CONS_NO = R.CONS_NO\n");
		sb.append("     AND C.AREA_NO = O.ORG_NO\n");
		sb.append("     AND R.PROTOCOL_CODE = P.PROTOCOL_CODE\n");
		sb.append("     ORDER BY R.TMNL_ASSET_NO");
		return this.getJdbcTemplate().query(sb.toString(), new Object[] {termAssertNo}, new tiTermMapper());
	}

	// terminalInfoBean的RowMapper类
	class tiTermMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TerminalInfoBean tp = new TerminalInfoBean();
			try {
				tp.setConsName(rs.getString("CONS_NAME"));
				tp.setConsNO(rs.getString("CONS_NO"));
				tp.setOrgName(rs.getString("ORG_NAME"));
				tp.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tp.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tp.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				tp.setProtocolName(rs.getString("PROTOCOL_NAME"));
				return tp;
			} catch (Exception e) {
				// throw new DBAccessException("dtMapper出现错误！");
				return null;
			}
		}
	}
	
	@Override
	public void updateTmnlTermInfo(final List<FetchInfoBean> fiBean) {

		StringBuffer sb = new StringBuffer();
		sb.append("UPDATE T_TMNL_PARAM\n");
		sb.append("   SET CURRENT_VALUE = ?,\n");
		sb.append("       HISTORY_VALUE = ?,\n");
		sb.append("       STATUS_CODE   = ?,\n");
		sb.append("       STAFF_NO      = ?,\n");
		sb.append("       SAVE_TIME     = ?\n");
		sb.append(" WHERE TMNL_ASSET_NO = ?\n");
		sb.append("   AND PROT_ITEM_NO = ?\n");
		sb.append("   AND BLOCK_SN = ?");

		String sql=sb.toString();

		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				int i = 0;
				FetchInfoBean bpe = fiBean.get(index);
				ps.setString(++i, bpe.getCurrentValue());
				ps.setString(++i, bpe.getHistoryValue());
				ps.setString(++i, bpe.getStatusCode());
				ps.setString(++i, bpe.getStaffNo());
				ps.setTimestamp(++i, new Timestamp(bpe.getSaveTime().getTime()));
				ps.setString(++i, bpe.getTmnlAssetNo());
				ps.setString(++i, bpe.getProtItemNo());
				ps.setString(++i, bpe.getBlockSn());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return fiBean.size();
			}
		});
	
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalInfoBean> searchTerminaArrlList(
			String[] tmnlAssetNoArr, PSysUser userInfo, String protCode) {
		StringBuffer sb = new StringBuffer();
		List<String> paraList = new ArrayList<String>();
		sb.append("SELECT C.CONS_NO,\n");
		sb.append("       R.TMNL_ASSET_NO,\n");
		sb.append("       C.CONS_NAME,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       R.TERMINAL_ADDR,\n");
		sb.append("       R.PROTOCOL_CODE\n");
		sb.append("  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n");
		sb.append("WHERE \n");
		sb.append("   R.TMNL_ASSET_NO in (");
		for (int i = 0; i < tmnlAssetNoArr.length; i++) {
			paraList.add(tmnlAssetNoArr[i]);
			if ( i == tmnlAssetNoArr.length -1) {
				sb.append("?");
			} else {
				sb.append("?,");
			}
		}
		sb.append(")\n");
		paraList.add(protCode);
		sb.append("   AND C.CONS_NO = R.CONS_NO\n");
		sb.append("     AND R.PROTOCOL_CODE = ?\n");
		sb.append("     AND C.AREA_NO = O.ORG_NO\n");
		sb.append("     ORDER BY R.TMNL_ASSET_NO");
		return this.getJdbcTemplate().query(sb.toString(),paraList.toArray(), new tiMapper());
//		return super.pagingFindList(sb.toString(), 0, 100, new tiMapper(), paraList.toArray());
	}
	
//增加没有测量点的时候保存callValue值	
public void saveOrUpdateFetchInfoBeanForTP(final List<FetchInfoBean> pubDetailList) {
		
//		StringBuffer sb = new StringBuffer();
//		sb.append(
//		"update t_tmnl_param tp\n" +
//		"   set tp.call_value = ?\n" + 
//		" where tp.tmnl_asset_no = ?\n" + 
//		"   and tp.prot_item_no = ?\n" + 
//		"   and tp.block_sn = ?\n" + 
//		"   and tp.inner_block_sn = ?");
//
//		String sql=sb.toString();
	StringBuffer sb=new StringBuffer();
	sb.append("MERGE INTO T_TMNL_PARAM TP\n");
	sb.append("USING (SELECT 1 FROM DUAL) B\n");
	sb.append("ON (TP.TMNL_ASSET_NO = ? AND TP.PROT_ITEM_NO = ? AND TP.BLOCK_SN = ? AND TP.INNER_BLOCK_SN = ?)\n");
	sb.append("WHEN MATCHED THEN\n");
	sb.append("		UPDATE SET CALL_VALUE = ?\n");
	sb.append("WHEN NOT MATCHED THEN\n");
	sb.append("		INSERT\n");
	sb.append("		(TMNL_ASSET_NO, PROT_ITEM_NO, BLOCK_SN, INNER_BLOCK_SN, CALL_VALUE)\n");
	sb.append("		VALUES\n");
	sb.append("		(?, ?, ?,?, ?)");

	String sql=sb.toString();
		logger.debug(sb.toString());
		this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				int i = 0;
				FetchInfoBean bpe = pubDetailList.get(index);
				ps.setString(++i, bpe.getTmnlAssetNo());
				ps.setString(++i, bpe.getProtItemNo());
				ps.setString(++i, bpe.getBlockSn());
				ps.setInt(++i, bpe.getInnerBlockSn());
				ps.setString(++i, bpe.getCallValue());
				ps.setString(++i, bpe.getTmnlAssetNo());
				ps.setString(++i, bpe.getProtItemNo());
				ps.setString(++i, bpe.getBlockSn());
				ps.setInt(++i, bpe.getInnerBlockSn());
				ps.setString(++i, bpe.getCallValue());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return pubDetailList.size();
			}
		});
	}

//增加有测量点的时候保存callValue值	
public void saveOrUpdateFetchInfoBeanForMP(final List<FetchInfoBean> pubDetailList) {
	
//	StringBuffer sb = new StringBuffer();
//	sb.append(
//
//			"update t_tmnl_mp_param mp\n" +
//			"   set mp.call_value = ?\n" + 
//			" where mp.id = (select e.id\n" + 
//			"                  from e_data_mp e\n" + 
//			"                 where e.tmnl_asset_no = ?\n" + 
//			"                   and e.mp_sn = ?)\n" + 
//			"   and mp.prot_item_no = ?\n" + 
//			"   and mp.block_sn = ?\n" + 
//			"   and mp.inner_block_sn = ?");

	StringBuffer sb=new StringBuffer();
	sb.append("MERGE INTO T_TMNL_MP_PARAM TP\n");
	sb.append("USING (SELECT E.ID\n");
	sb.append("		FROM E_DATA_MP E\n");
	sb.append("		WHERE E.TMNL_ASSET_NO = ? AND E.MP_SN = ?) B\n");
	sb.append("ON (TP.ID = B.ID AND TP.PROT_ITEM_NO = ? AND TP.BLOCK_SN = ? AND TP.INNER_BLOCK_SN = ?)\n");
	sb.append("WHEN MATCHED THEN\n");
	sb.append("		UPDATE SET CALL_VALUE = ?\n");
	sb.append("WHEN NOT MATCHED THEN\n");
	sb.append("		INSERT\n");
	sb.append("		(ID, PROT_ITEM_NO, BLOCK_SN, INNER_BLOCK_SN, CALL_VALUE)\n");
	sb.append("		VALUES\n");
	sb.append("		(B.ID, ?, ?, ?, ?)");

	String sql=sb.toString();

	logger.debug(sb.toString());
	this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
		
		@Override
		public void setValues(PreparedStatement ps, int index) throws SQLException {
			int i = 0;
			FetchInfoBean bpe = pubDetailList.get(index);
			ps.setString(++i, bpe.getTmnlAssetNo());
			ps.setString(++i, bpe.getMpSn());
			ps.setString(++i, bpe.getProtItemNo());
			ps.setString(++i, bpe.getBlockSn());
			ps.setInt(++i, bpe.getInnerBlockSn());
			ps.setString(++i, bpe.getCallValue());
			ps.setString(++i, bpe.getProtItemNo());
			ps.setString(++i, bpe.getBlockSn());
			ps.setInt(++i, bpe.getInnerBlockSn());
			ps.setString(++i, bpe.getCallValue());
		}
		
		@Override
		public int getBatchSize() {
			// TODO Auto-generated method stub
			return pubDetailList.size();
		}
	});
}

@Override
public Page<CallValueBean> getCallValueBean(String tmnlAssetNo, String protNO,List blockSn, long start, int limit) {
	List<Object> parms = new ArrayList<Object>();
	StringBuffer sb = new StringBuffer();
	StringBuffer blocksql = new StringBuffer();
	 int j = 0;
	 String blockStart = "";
	for(Object blocksn : blockSn){
		if(j == 0){
			blockStart = "tp.block_sn like ? || '%' ";	
		}else{
			blocksql.append(" or tp.block_sn like ? || '%'");
		}
		parms.add(blocksn);
		j++;
	}
	parms.add(tmnlAssetNo);
	parms.add(protNO + "%");
	sb.append(
			"select b.unit,\n" +
			"       tp.status_code,\n" + 
			"       tp.block_sn,\n" + 
			"       tp.tmnl_asset_no,\n" + 
			"       tp.current_value,\n" + 
			"       tp.call_value,\n" + 
			"       b.prot_item_name\n" + 
			"  from t_tmnl_param tp, b_comm_protocol_item b\n" + 
			" where tp.prot_item_no = b.prot_item_no\n" + 
			"   and ("+blockStart+""+blocksql+")\n" + 
			"   and tp.tmnl_asset_no = ?\n" + 
			"   and tp.prot_item_no like ?\n" +
			"   order by tp.tmnl_asset_no, tp.block_sn, tp.prot_item_no\n");
	logger.debug(sb.toString());

	return super.pagingFind(sb.toString(), start, limit, new CallValueBeanRowMap(),parms.toArray());

}

public Page<CallValueBean> getCallValueBeanMpSn(String tmnlAssetNo, String protNO,
		List mpSn, List blockSn, long start, int limit) {
	StringBuffer sb = new StringBuffer();
	List<Object> parms = new ArrayList<Object>();
//	StringBuffer blocksql = new StringBuffer();
	StringBuffer snsql = new StringBuffer();
//	 int j = 0;
//	 String blockStart = "";
//	for(Object blocksn : blockSn){
//		if(j == 0){
//			blockStart = "mp.block_sn like ? || '%' ";	
//		}else{
//			blocksql.append(" or mp.block_sn like ? || '%'");
//		}
//		parms.add(blocksn);
//		j++;
//	}
	parms.add(tmnlAssetNo);
	parms.add(protNO + "%");
	int i = 0;

	for(Object mpsn : mpSn){
//		if(i==0){
//			
//		}			
//		else{
		snsql.append(",'"+mpsn+"'");
	//	}
//		parms.add(mpsn);
//		i++;
	}
	sb.append(
			"select b.unit,\n" +
			"       mp.status_code,\n" + 
			"       mp.block_sn,\n" + 
			"       e.tmnl_asset_no,\n" + 
			"        e.mp_sn,\n" + 
			"       mp.current_value,\n" + 
			"       mp.call_value,\n" + 
			"       b.prot_item_name\n" + 
			"  from t_tmnl_mp_param mp, b_comm_protocol_item b, e_data_mp e\n" + 
			" where mp.prot_item_no = b.prot_item_no\n" + 
			"   and mp.id = e.id\n" + 
//			"   and ("+blockStart+""+blocksql+")\n" + 
			"   and e.tmnl_asset_no = ?\n" + 
			"   and mp.prot_item_no like ?\n" + 
			"   and e.mp_sn in (''"+snsql+")\n" +
			"   order by e.tmnl_asset_no, e.mp_sn, mp.block_sn, mp.prot_item_no\n");

	logger.debug(sb.toString());

	return super.pagingFind(sb.toString(), start, limit, new CallValueBeanMpSnRowMap(),parms.toArray());

}
}
class CallValueBeanRowMap implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		CallValueBean callValueBean = new CallValueBean();
		try{
			callValueBean.setBlockSn(rs.getString("block_sn"));
			callValueBean.setCallValue(rs.getString("call_value"));
//			callValueBean.setMpSn(rs.getString("mp_sn"));
			callValueBean.setCurrentValue(rs.getString("current_value"));
			callValueBean.setProtItemName(rs.getString("prot_item_name"));
			callValueBean.setStatusCode(rs.getString("status_code"));
//			callValueBean.setTmnlAssetNo(rs.getString("tmnl_asset_no"));
			callValueBean.setUnit(rs.getString("unit"));
		}catch(Exception e){
			return null;
		}
		return callValueBean;
	}
}

class CallValueBeanMpSnRowMap implements RowMapper{
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		CallValueBean callValueBean = new CallValueBean();
		try{
			callValueBean.setBlockSn(rs.getString("block_sn"));
			callValueBean.setCallValue(rs.getString("call_value"));
			callValueBean.setCurrentValue(rs.getString("current_value"));
			callValueBean.setProtItemName(rs.getString("prot_item_name"));
			callValueBean.setStatusCode(rs.getString("status_code"));
			callValueBean.setMpSn(rs.getString("mp_sn"));
			callValueBean.setUnit(rs.getString("unit"));
		}catch(Exception e){
			return null;
		}
		return callValueBean;
	}
}
