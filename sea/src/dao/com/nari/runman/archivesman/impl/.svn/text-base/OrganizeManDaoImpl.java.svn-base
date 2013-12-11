package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runcontrol.GLineJdbc;
import com.nari.runcontrol.GSubsJdbc;
import com.nari.runcontrol.OOrgJdbc;
import com.nari.runcontrol.VwRunStatus;
import com.nari.runman.archivesman.IOrganizeManDao;
import com.nari.util.exception.DBAccessException;

public class OrganizeManDaoImpl extends JdbcBaseDAOImpl implements
		IOrganizeManDao {

	/**
	 * 根据区县编号查询供电所
	 * @param orgNo 区县编码
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<OOrgJdbc> findOrg(String orgNo) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT O.ORG_NO,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       O.ORG_SHORT_NAME,\n");
		sb.append("       O.P_ORG_NO,\n");
		sb.append("       O.ORG_TYPE,\n");
		sb.append("       O.SORT_NO,\n");
		sb.append("       O.IS_BULK_SALE,\n");
		sb.append("       O.TMNL_CODE,\n");
		sb.append("       O.AREA_CODE,\n");
		sb.append("       O.IS_DIRECT,\n");
		sb.append("       O.INTER_ORG_NO,\n");
		sb.append("       O.CALC_ORDER,\n");
		sb.append("       O.IS_STAT\n");
		sb.append("  FROM O_ORG O\n");
		sb.append(" WHERE O.P_ORG_NO = ?\n");
		sb.append("   AND O.ORG_TYPE = '06'");
		String sql=sb.toString();
		try {
			this.logger.debug(sb.toString());
			List<OOrgJdbc> list = new ArrayList<OOrgJdbc>();
			list = this.getJdbcTemplate().query(sql,new Object[] { orgNo }, new OOrgRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("查询供电所出错！");
			throw e;
		}
	}
	
	/**
	 * 根据单位编号查询变电站
	 * @param orgNo 区县编码或地市编码
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<GSubsJdbc> findSubs(String orgNo) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.SUBS_ID,\n");
		sb.append("       T.SUBS_NAME,\n");
		sb.append("       T.SUBS_NO,\n");
		sb.append("       T.VOLT_CODE,\n");
		sb.append("       T.SUBS_ADDR,\n");
		sb.append("       T.MT_NUM,\n");
		sb.append("       T.MT_CAP,\n");
		sb.append("       T.ORG_NO,\n");
		sb.append("       T.INLINE_ID,\n");
		sb.append("       T.CHG_DATE,\n");
		sb.append("       T.RUN_STATUS_CODE\n");
		sb.append("  FROM G_SUBS T\n");
		sb.append(" WHERE T.ORG_NO = ?");
		String sql=sb.toString();

		try {
			this.logger.debug(sb.toString());
			List<GSubsJdbc> list = new ArrayList<GSubsJdbc>();
			list = this.getJdbcTemplate().query(sql,new Object[] { orgNo }, new GSubsRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("查询变电站出错！");
			throw e;
		}
	}
	
	/**
	 * 根据变电站编号查询线路
	 * @param subsNo 变电站编号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<GLineJdbc> findLine(String subsNo) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.LINE_ID,\n");
		sb.append("       T.LINE_NO,\n");
		sb.append("       T.LINE_NAME,\n");
		sb.append("       T.ORG_NO,\n");
		sb.append("       T.VOLT_CODE,\n");
		sb.append("       T.WIRE_SPEC_CODE,\n");
		sb.append("       T.WIRE_LEN,\n");
		sb.append("       T.SUBLINE_FLAG,\n");
		sb.append("       T.CHG_DATE,\n");
		sb.append("       T.LN_FLAG,\n");
		sb.append("       T.RURAL_GRID_FLAG,\n");
		sb.append("       T.RUN_STATUS_CODE,\n");
		sb.append("       T.LL_CALC_MODE,\n");
		sb.append("       T.AP_LL_VALUE,\n");
		sb.append("       T.RP_LL_VALUE,\n");
		sb.append("       T.UNIT_RESI,\n");
		sb.append("       T.UNIT_REAC,\n");
		sb.append("       T.CONS_ID\n");
		sb.append("  FROM G_LINE T, G_SUBS_LINE_RELA G\n");
		sb.append(" WHERE T.LINE_ID = G.LINE_ID\n");
		sb.append("   AND G.SUBS_ID = ?");
		String sql=sb.toString();

		try {
			this.logger.debug(sb.toString());
			List<GLineJdbc> list = new ArrayList<GLineJdbc>();
			list = this.getJdbcTemplate().query(sql,new Object[] { subsNo }, new GLineRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("查询线路出错！");
			throw e;
		}
	}
	
	/**
	 * 根据单位编码查询单位
	 * @param orgNo
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<OOrgJdbc> findOrgByNo(String orgNo) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT O.ORG_NO,\n");
		sb.append("       O.ORG_NAME,\n");
		sb.append("       O.ORG_SHORT_NAME,\n");
		sb.append("       O.P_ORG_NO,\n");
		sb.append("       O.ORG_TYPE,\n");
		sb.append("       O.SORT_NO,\n");
		sb.append("       O.IS_BULK_SALE,\n");
		sb.append("       O.TMNL_CODE,\n");
		sb.append("       O.AREA_CODE,\n");
		sb.append("       O.IS_DIRECT,\n");
		sb.append("       O.INTER_ORG_NO,\n");
		sb.append("       O.CALC_ORDER,\n");
		sb.append("       O.IS_STAT\n");
		sb.append("  FROM O_ORG O\n");
		sb.append(" WHERE O.ORG_NO = ?\n");
		String sql=sb.toString();
		try {
			this.logger.debug(sb.toString());
			List<OOrgJdbc> list = new ArrayList<OOrgJdbc>();
			list = this.getJdbcTemplate().query(sql,new Object[] { orgNo }, new OOrgRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("查询供电所出错！");
			throw e;
		}
	}
	
	/**
	 * 根据变电站编号/id/名称 查询变电站
	 * @param value 变电站编号/id/名称
	 * @param queryT （subsNo/subsId/subsName）
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<GSubsJdbc> findSubsByNo(GSubsJdbc gSubsJdbc,String queryT) throws DBAccessException{
		List<Object> list = new ArrayList<Object>();
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.SUBS_ID,\n");
		sb.append("       T.SUBS_NAME,\n");
		sb.append("       T.SUBS_NO,\n");
		sb.append("       T.VOLT_CODE,\n");
		sb.append("       T.SUBS_ADDR,\n");
		sb.append("       T.MT_NUM,\n");
		sb.append("       T.MT_CAP,\n");
		sb.append("       T.ORG_NO,\n");
		sb.append("       T.INLINE_ID,\n");
		sb.append("       T.CHG_DATE,\n");
		sb.append("       T.RUN_STATUS_CODE\n");
		sb.append("  FROM G_SUBS T\n");
		if(queryT.equals("subsNo_update")){
			sb.append(" WHERE T.SUBS_NO = ?");
			sb.append(" AND T.SUBS_ID <> ?");
			list.add(gSubsJdbc.getSubsNo());
			list.add(gSubsJdbc.getSubsId());
		}else if(queryT.equals("subsName_update")){
			sb.append(" WHERE T.SUBS_NAME = ?");
			sb.append(" AND T.SUBS_ID <> ?");
			list.add(gSubsJdbc.getSubsName());
			list.add(gSubsJdbc.getSubsId());
		}else if(queryT.equals("subsNo_insert")){
			sb.append(" WHERE T.SUBS_NO = ?");
			list.add(gSubsJdbc.getSubsName());
		}else if(queryT.equals("subsName_insert")){
			sb.append(" WHERE T.SUBS_NAME = ?");
			list.add(gSubsJdbc.getSubsName());
		}
		String sql=sb.toString();

		try {
			this.logger.debug(sb.toString());
			List<GSubsJdbc> subsList = new ArrayList<GSubsJdbc>();
			subsList = this.getJdbcTemplate().query(sql, list.toArray(), new GSubsRowMapper());
			return subsList;
		} catch (RuntimeException e) {
			this.logger.debug("查询变电站出错！");
			throw e;
		}
	}
	
	/**
	 * 根据线路编号/id/名称查询线路
	 * @param value 线路编号/id/名称
	 * @param queryT （lineNo/lineId/lineName）
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<GLineJdbc> findLineByNo(GLineJdbc gLineJdbc,String queryT) throws DBAccessException{
		List<Object> paramList = new ArrayList<Object>();
		
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.LINE_ID,\n");
		sb.append("       T.LINE_NO,\n");
		sb.append("       T.LINE_NAME,\n");
		sb.append("       T.ORG_NO,\n");
		sb.append("       T.VOLT_CODE,\n");
		sb.append("       T.WIRE_SPEC_CODE,\n");
		sb.append("       T.WIRE_LEN,\n");
		sb.append("       T.SUBLINE_FLAG,\n");
		sb.append("       T.CHG_DATE,\n");
		sb.append("       T.LN_FLAG,\n");
		sb.append("       T.RURAL_GRID_FLAG,\n");
		sb.append("       T.RUN_STATUS_CODE,\n");
		sb.append("       T.LL_CALC_MODE,\n");
		sb.append("       T.AP_LL_VALUE,\n");
		sb.append("       T.RP_LL_VALUE,\n");
		sb.append("       T.UNIT_RESI,\n");
		sb.append("       T.UNIT_REAC,\n");
		sb.append("       T.CONS_ID\n");
		sb.append("  FROM G_LINE T\n");
		if("lineNo_update".equals(queryT)){
			sb.append(" WHERE T.LINE_NO = ?");
			sb.append(" AND T.LINE_ID <> ?");
			paramList.add(gLineJdbc.getLineNo());
			paramList.add(gLineJdbc.getLineId());
		}else if("lineId".equals(queryT)){
			sb.append(" WHERE T.LINE_ID = ?");
			paramList.add(gLineJdbc.getLineId());
		}else if("lineName_update".equals(queryT)){
			sb.append(" WHERE T.LINE_NAME = ?");
			sb.append(" AND T.LINE_ID <> ?");
			paramList.add(gLineJdbc.getLineName());
			paramList.add(gLineJdbc.getLineId());
		}else if(queryT.equals("lineNo_insert")){
			sb.append(" WHERE T.LINE_NO = ?");
			paramList.add(gLineJdbc.getLineNo());
		}else if(queryT.equals("lineName_insert")){
			sb.append(" WHERE T.LINE_NAME = ?");
			paramList.add(gLineJdbc.getLineName());
		}
		String sql=sb.toString();

		try {
			this.logger.debug(sb.toString());
			List<GLineJdbc> list = new ArrayList<GLineJdbc>();
			list = this.getJdbcTemplate().query(sql,paramList.toArray(), new GLineRowMapper());
			return list;
		} catch (RuntimeException e) {
			this.logger.debug("查询线路出错！");
			throw e;
		}
	}
	
	/**
	 * 新增供电所
	 * @throws DBAccessException
	 */
	public void insertOrg(OOrgJdbc orgJdbc) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO O_ORG\n");
		sb.append("  (ORG_NAME,\n");
		sb.append("   ORG_SHORT_NAME,\n");
		sb.append("   P_ORG_NO,\n");
		sb.append("   ORG_TYPE,\n");
		sb.append("   SORT_NO,\n");
		sb.append("   IS_BULK_SALE,\n");
		sb.append("   TMNL_CODE,\n");
		sb.append("   AREA_CODE,\n");
		sb.append("   IS_DIRECT,\n");
		sb.append("   CALC_ORDER,\n");
		sb.append("   IS_STAT,\n");
		sb.append("   ORG_NO)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --       org_name,\n");
		sb.append("   ?, --       org_short_name,\n");
		sb.append("   ?, --       p_org_no,\n");
		sb.append("   ?, --       org_type,\n");
		sb.append("   ?, --       sort_no,\n");
		sb.append("   ?, --       is_bulk_sale,\n");
		sb.append("   ?, --       tmnl_code,\n");
		sb.append("   ?, --       area_code,\n");
		sb.append("   ?, --       is_direct,\n");
		sb.append("   ?, --       calc_order,\n");
		sb.append("   ?, --       is_stat,\n");
		sb.append("   ? --org_no\n");
		sb.append("   )");

		String sql=sb.toString();

		try{
			this.getJdbcTemplate().update(sql, this.saveOrgList(orgJdbc).toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增供电所出错！");
			throw e;
		}
	}
	
	/**
	 * 保存供电所
	 * @throws DBAccessException
	 */
	public void saveOrg(String hiddenOrgNo,OOrgJdbc orgJdbc) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE O_ORG\n");
		sb.append("   SET ORG_NAME       = ?,\n");
		sb.append("       ORG_SHORT_NAME = ?,\n");
		sb.append("       P_ORG_NO       = ?,\n");
		sb.append("       ORG_TYPE       = ?,\n");
		sb.append("       SORT_NO        = ?,\n");
		sb.append("       IS_BULK_SALE   = ?,\n");
		sb.append("       TMNL_CODE      = ?,\n");
		sb.append("       AREA_CODE      = ?,\n");
		sb.append("       IS_DIRECT      = ?,\n");
		sb.append("       CALC_ORDER     = ?,\n");
		sb.append("       IS_STAT        = ?,\n");
		sb.append("       ORG_NO        = ?\n");
		sb.append(" WHERE ORG_NO = ?");
		String sql=sb.toString();
		
		try{
			List<Object> list = this.saveOrgList(orgJdbc);
			list.add(hiddenOrgNo);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存供电所出错！");
			throw e;
		}
	}
	
	/**
	 * 新增变电站
	 * @throws DBAccessException
	 */
	public void insertSubs(GSubsJdbc subsJdbc) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO G_SUBS\n");
		sb.append("   (SUBS_NAME,\n");
		sb.append("   SUBS_NO,\n");
		sb.append("   VOLT_CODE,\n");
		sb.append("   SUBS_ADDR,\n");
		sb.append("   MT_NUM,\n");
		sb.append("   MT_CAP,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   INLINE_ID,\n");
		sb.append("   CHG_DATE,\n");
		sb.append("   RUN_STATUS_CODE,\n");
		sb.append("  SUBS_ID)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --       subs_name,\n");
		sb.append("   ?, --       subs_no,\n");
		sb.append("   ?, --       volt_code,\n");
		sb.append("   ?, --       subs_addr,\n");
		sb.append("   ?, --       mt_num,\n");
		sb.append("   ?, --       mt_cap,\n");
		sb.append("   ?, --       org_no,\n");
		sb.append("   ?, --       inline_id,\n");
		sb.append("   ?, --       chg_date,\n");
		sb.append("   ?, --       run_status_code,\n");
		sb.append("   S_G_SUBS.Nextval --subs_id\n");
		sb.append("   )");
		String sql=sb.toString();

		try{
			List<Object> list = this.saveSubsList(subsJdbc);
			list.remove(list.size()-1);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增变电站出错！");
			throw e;
		}
	}
	
	/**
	 * 保存变电站
	 * @throws DBAccessException
	 */
	public void saveSubs(GSubsJdbc subsJdbc) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE G_SUBS\n");
		sb.append("   SET SUBS_NAME       = ?,\n");
		sb.append("       SUBS_NO         = ?,\n");
		sb.append("       VOLT_CODE       = ?,\n");
		sb.append("       SUBS_ADDR       = ?,\n");
		sb.append("       MT_NUM          = ?,\n");
		sb.append("       MT_CAP          = ?,\n");
		sb.append("       ORG_NO          = ?,\n");
		sb.append("       INLINE_ID       = ?,\n");
		sb.append("       CHG_DATE        = ?,\n");
		sb.append("       RUN_STATUS_CODE = ?\n");
		sb.append(" WHERE SUBS_ID = ?");
		String sql=sb.toString();
		try{
			List<Object> list = this.saveSubsList(subsJdbc);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存变电站出错！");
			throw e;
		}
	}
		
	/**
	 * 新增线路
	 * @throws DBAccessException
	 */
	public void insertLine(GLineJdbc lineJdbc) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("INSERT INTO G_LINE\n");
		sb.append("  (LINE_NO,\n");
		sb.append("   LINE_NAME,\n");
		sb.append("   ORG_NO,\n");
		sb.append("   VOLT_CODE,\n");
		sb.append("   WIRE_SPEC_CODE,\n");
		sb.append("   WIRE_LEN,\n");
		sb.append("   SUBLINE_FLAG,\n");
		sb.append("   CHG_DATE,\n");
		sb.append("   LN_FLAG,\n");
		sb.append("   RURAL_GRID_FLAG,\n");
		sb.append("   RUN_STATUS_CODE,\n");
		sb.append("   LL_CALC_MODE,\n");
		sb.append("   AP_LL_VALUE,\n");
		sb.append("   RP_LL_VALUE,\n");
		sb.append("   UNIT_RESI,\n");
		sb.append("   UNIT_REAC,\n");
		sb.append("   LINE_ID)\n");
		sb.append("VALUES\n");
		sb.append("  (?, --line_no,\n");
		sb.append("   ?, --       line_name,\n");
		sb.append("   ?, --       org_no,\n");
		sb.append("   ?, --       volt_code,\n");
		sb.append("   ?, --       wire_spec_code,\n");
		sb.append("   ?, --       wire_len,\n");
		sb.append("   ?, --       subline_flag,\n");
		sb.append("   ?, --       chg_date,\n");
		sb.append("   ?, --       ln_flag,\n");
		sb.append("   ?, --       rural_grid_flag,\n");
		sb.append("   ?, --       run_status_code,\n");
		sb.append("   ?, --       ll_calc_mode,\n");
		sb.append("   ?, --       ap_ll_value,\n");
		sb.append("   ?, --       rp_ll_value,\n");
		sb.append("   ?, --       unit_resi,\n");
		sb.append("   ?, --       unit_reac,\n");
		sb.append("   ? --       line_id\n");
		sb.append("   )");
		String sql=sb.toString();
		
		try{
			List<Object> list = this.saveLineList(lineJdbc);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("新增线路出错！");
			throw e;
		}
	}
	
	/**
	 * 新增线路和变电站关系
	 * @throws DBAccessException
	 */
	public void insertSubLineRela(String subsId,String lineId) throws DBAccessException{
		String sql = "INSERT INTO g_subs_line_rela (rela_id,subs_id,line_id) VALUES (S_SUBS_LINE_RELA.NEXTVAL,?,?)";
		
		try{
			this.getJdbcTemplate().update(sql, new Object[]{subsId,lineId});
		}catch (RuntimeException e) {
			this.logger.debug("新增线路出错！");
			throw e;
		}
	}
	
	/**
	 * 保存线路
	 * @throws DBAccessException
	 */
	public void saveLine(GLineJdbc lineJdbc) throws DBAccessException{
		StringBuffer sb=new StringBuffer();
		sb.append("UPDATE G_LINE\n");
		sb.append("   SET LINE_NO         = ?,\n");
		sb.append("       LINE_NAME       = ?,\n");
		sb.append("       ORG_NO          = ?,\n");
		sb.append("       VOLT_CODE       = ?,\n");
		sb.append("       WIRE_SPEC_CODE  = ?,\n");
		sb.append("       WIRE_LEN        = ?,\n");
		sb.append("       SUBLINE_FLAG    = ?,\n");
		sb.append("       CHG_DATE        = ?,\n");
		sb.append("       LN_FLAG         = ?,\n");
		sb.append("       RURAL_GRID_FLAG = ?,\n");
		sb.append("       RUN_STATUS_CODE = ?,\n");
		sb.append("       LL_CALC_MODE    = ?,\n");
		sb.append("       AP_LL_VALUE     = ?,\n");
		sb.append("       RP_LL_VALUE     = ?,\n");
		sb.append("       UNIT_RESI       = ?,\n");
		sb.append("       UNIT_REAC       = ?\n");
		sb.append(" WHERE LINE_ID = ?");

		String sql=sb.toString();
		try{
			List<Object> list = this.saveLineList(lineJdbc);
			this.getJdbcTemplate().update(sql, list.toArray());
		}catch (RuntimeException e) {
			this.logger.debug("保存线路出错！");
			throw e;
		}
	}
	
	/**
	 * 删除供电所
	 * @param orgNo 单位编码
	 * @throws DBAccessException
	 */
	public void deleteOrg(String orgNo) throws DBAccessException{
		String sql = "DELETE FROM o_org WHERE org_no = ?";
		
		try{
			this.getJdbcTemplate().update(sql, new Object[]{orgNo});
		}catch (RuntimeException e) {
			this.logger.debug("删除供电所出错！\n");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除变电站
	 * @param subsId 变电站id
	 * @throws DBAccessException
	 */
	public void deleteSubs(String subsId) throws DBAccessException{
		String sql = "DELETE FROM G_SUBS WHERE SUBS_ID = ?";
		
		try{
			this.getJdbcTemplate().update(sql, new Object[]{subsId});
		}catch (RuntimeException e) {
			this.logger.debug("删除变电站出错！\n");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除线路
	 * @param lineId 线路id
	 * @throws DBAccessException
	 */
	public void deleteLine(String lineId) throws DBAccessException{
		String sql = "DELETE FROM G_LINE WHERE LINE_ID = ?";
		try{
			this.getJdbcTemplate().update(sql, new Object[]{lineId});
		}catch (RuntimeException e) {
			this.logger.debug("删除线路出错！\n");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 删除线路和变电站关系
	 * @param lineId 线路id
	 * @throws DBAccessException
	 */
	public void deleteLineSub(String lineId) throws DBAccessException{
		String sql = "DELETE FROM G_SUBS_LINE_RELA WHERE LINE_ID = ?";
		try{
			this.getJdbcTemplate().update(sql, new Object[]{lineId});
		}catch (RuntimeException e) {
			this.logger.debug("删除线路和变电站关系！\n");
			e.printStackTrace();
			throw e;
		}
	}

	
	/**
	 * 查询运行状态码表
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<VwRunStatus> findRunStatusList() throws DBAccessException{
		String sql = "SELECT RUN_STATUS_CODE,RUN_STATUS_NAME FROM VW_RUN_STATUS_CODE";
		try{
			return this.getJdbcTemplate().query(sql, new vwStatusRowMapper());
		}catch (RuntimeException e) {
			this.logger.debug("删除供电所出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 查询线路的序列，主键
	 * @return
	 * @throws DBAccessException
	 */
	public Long findLineSq() throws DBAccessException{
		String sql = "SELECT s_g_line.nextval FROM dual";
		try {
			return this.getJdbcTemplate().queryForLong(sql);
		} catch (RuntimeException e) {
			this.logger.debug("查询线路序列主键出错！");
			e.printStackTrace();
			throw e;
		}
	}
	
	
	class OOrgRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			OOrgJdbc oorgjdbc = new OOrgJdbc();
			try {
				oorgjdbc.setOrgNo(rs.getString("ORG_NO"));
				oorgjdbc.setOrgName(rs.getString("ORG_NAME"));
				oorgjdbc.setOrgShortName(rs.getString("ORG_SHORT_NAME"));
				oorgjdbc.setpOrgNo(rs.getString("P_ORG_NO"));
				oorgjdbc.setOrgType(rs.getString("ORG_TYPE"));
				oorgjdbc.setSortNo(rs.getInt("SORT_NO"));
				oorgjdbc.setIsBulkSale(rs.getByte("IS_BULK_SALE"));
				oorgjdbc.setTmnlCode(rs.getString("TMNL_CODE"));
				oorgjdbc.setAreaCode(rs.getString("AREA_CODE"));
				oorgjdbc.setIsDirect(rs.getByte("IS_DIRECT"));
				oorgjdbc.setInterOrgNo(rs.getString("INTER_ORG_NO"));
				oorgjdbc.setCalcOrder(rs.getByte("CALC_ORDER"));
				oorgjdbc.setIsStat(rs.getByte("IS_STAT"));
				return oorgjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}
	class GSubsRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GSubsJdbc gsubsjdbc = new GSubsJdbc();
			try {
				gsubsjdbc.setSubsId(rs.getLong("SUBS_ID"));
				gsubsjdbc.setSubsName(rs.getString("SUBS_NAME"));
				gsubsjdbc.setSubsNo(rs.getString("SUBS_NO"));
				gsubsjdbc.setVoltCode(rs.getString("VOLT_CODE"));
				gsubsjdbc.setSubsAddr(rs.getString("SUBS_ADDR"));
				gsubsjdbc.setMtNum(rs.getInt("MT_NUM"));
				gsubsjdbc.setMtCap(rs.getLong("MT_CAP"));
				gsubsjdbc.setOrgNo(rs.getString("ORG_NO"));
				gsubsjdbc.setInlineId(rs.getString("INLINE_ID"));
				gsubsjdbc.setChgDate(rs.getTimestamp("CHG_DATE"));
				gsubsjdbc.setRunStatusCode(rs.getString("RUN_STATUS_CODE"));
				return gsubsjdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}
	class GLineRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			GLineJdbc glinejdbc = new GLineJdbc();
			try {
				glinejdbc.setLineId(rs.getLong("LINE_ID"));
				glinejdbc.setLineNo(rs.getString("LINE_NO"));
				glinejdbc.setLineName(rs.getString("LINE_NAME"));
				glinejdbc.setOrgNo(rs.getString("ORG_NO"));
				glinejdbc.setVoltCode(rs.getString("VOLT_CODE"));
				glinejdbc.setWireSpecCode(rs.getString("WIRE_SPEC_CODE"));
				glinejdbc.setWireLen(rs.getDouble("WIRE_LEN"));
				glinejdbc.setSublineFlag(rs.getString("SUBLINE_FLAG"));
				glinejdbc.setChgDate(rs.getTimestamp("CHG_DATE"));
				glinejdbc.setLnFlag(rs.getString("LN_FLAG"));
				glinejdbc.setRuralGridFlag(rs.getString("RURAL_GRID_FLAG"));
				glinejdbc.setRunStatusCode(rs.getString("RUN_STATUS_CODE"));
				glinejdbc.setLlCalcMode(rs.getString("LL_CALC_MODE"));
				glinejdbc.setApLlValue(rs.getDouble("AP_LL_VALUE"));
				glinejdbc.setRpLlValue(rs.getDouble("RP_LL_VALUE"));
				glinejdbc.setUnitResi(rs.getDouble("UNIT_RESI"));
				glinejdbc.setUnitReac(rs.getDouble("UNIT_REAC"));
				glinejdbc.setConsId(rs.getLong("CONS_ID"));
				return glinejdbc;
			} catch (Exception e) {
				return null;
			}
		}
	}
	class vwStatusRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VwRunStatus v = new VwRunStatus();
			try {
				v.setRunStatusCode(rs.getString("RUN_STATUS_CODE"));
				v.setRunStatusName(rs.getString("RUN_STATUS_NAME"));
				return v;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * 方法 saveOrgList
	 * @param orgJdbc
	 * @return 保存org的SQL参数
	 */
	private List<Object> saveOrgList(OOrgJdbc orgJdbc){
		List<Object> list = new ArrayList<Object>();
		list.add(orgJdbc.getOrgName());
		list.add(orgJdbc.getOrgShortName());
		list.add(orgJdbc.getpOrgNo());
		list.add(orgJdbc.getOrgType());
		list.add(orgJdbc.getSortNo());
		list.add(orgJdbc.getIsBulkSale());
		list.add(orgJdbc.getTmnlCode());
		list.add(orgJdbc.getAreaCode());
		list.add(orgJdbc.getIsDirect());
		list.add(orgJdbc.getCalcOrder());
		list.add(orgJdbc.getIsStat());
		list.add(orgJdbc.getOrgNo());
		return list;
	}
	
	/**
	 * 方法 saveSubsList
	 * @param subsJdbc
	 * @return 保存subs的SQL参数
	 */
	private List<Object> saveSubsList(GSubsJdbc subsJdbc){
		List<Object> list = new ArrayList<Object>();
		list.add(subsJdbc.getSubsName());
		list.add(subsJdbc.getSubsNo());
		list.add(subsJdbc.getVoltCode());
		list.add(subsJdbc.getSubsAddr());
		list.add(subsJdbc.getMtNum());
		list.add(subsJdbc.getMtCap());
		list.add(subsJdbc.getOrgNo());
		list.add(subsJdbc.getInlineId());
		list.add(subsJdbc.getChgDate());
		list.add(subsJdbc.getRunStatusCode());
		list.add(subsJdbc.getSubsId());
		return list;
	}
	
	private List<Object> saveLineList(GLineJdbc gLineJdbc){
		List<Object> list = new ArrayList<Object>();
		list.add(gLineJdbc.getLineNo());
		list.add(gLineJdbc.getLineName());
		list.add(gLineJdbc.getOrgNo());
		list.add(gLineJdbc.getVoltCode());
		list.add(gLineJdbc.getWireSpecCode());
		list.add(gLineJdbc.getWireLen());
		list.add(gLineJdbc.getSublineFlag());
		list.add(gLineJdbc.getChgDate());
		list.add(gLineJdbc.getLnFlag());
		list.add(gLineJdbc.getRuralGridFlag());
		list.add(gLineJdbc.getRunStatusCode());
		list.add(gLineJdbc.getLlCalcMode());
		list.add(gLineJdbc.getApLlValue());
		list.add(gLineJdbc.getRpLlValue());
		list.add(gLineJdbc.getUnitResi());
		list.add(gLineJdbc.getUnitReac());
		list.add(gLineJdbc.getLineId());
		return list;
	}
}
