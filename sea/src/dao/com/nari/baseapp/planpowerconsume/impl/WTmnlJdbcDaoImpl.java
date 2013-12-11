package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IWTmnlJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.WTmnlDto;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-28 下午02:08:10 
 * 描述：查询终端相关信息JdbcDao接口实现类（保电、剔除）
 */

public class WTmnlJdbcDaoImpl extends JdbcBaseDAOImpl implements IWTmnlJdbcDao{
	
	/**
	 * 根据用户查询相关信息放入备选用户Grid中（单条记录）
	 * @throws DBAccessException
	 */
	public Page<WTmnlDto> findTmnlByConsNo(String consNo,long start, int limit) throws DBAccessException{
		String sql = 
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.CONS_NO= ?";
		System.out.println(sql);
		try{
			Page<WTmnlDto> page = new Page<WTmnlDto>();
			page = this.pagingFind(sql, start, limit,new WTmnlRowMapper(),consNo);
			return page;
		}catch(RuntimeException e){
			this.logger.debug("根据用户编号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据终端资产号查询终端相关信息
	 * @param tmnlAssetNo 终端资产号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlDto> findTmnlByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
		String sql =
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO= ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo},new WTmnlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据终端资产号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据供电单位编号查询终端相关信息
	 * @param orgNo   供电单位编号
	 * @param orgType 供电单位类型
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlDto> findTmnlByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.Cons_Name,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n");
		if("03".equals(orgType)||"04".equals(orgType)){
			orgNo="%"+orgNo+"%";
			sb.append("  and C.Area_No like ?");
		}else if("06".equals(orgType))
			sb.append("  and C.Org_No= ?");
		else
			 return null;
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[]{orgNo,pSysUser.getStaffNo()},new WTmnlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据供电单位查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据线路Id查询终端相关信息
	 * @param lineId 线路Id
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlDto> findTmnlByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE\n" +
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.Line_Id= ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[] {lineId,pSysUser.getStaffNo()},new WTmnlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据线路Id查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据普通群组编号查询终端相关信息
	 * @param groupNo  群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlDto> findTmnlByCommonGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       R.PROTOCOL_CODE\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,R_USER_GROUP_DETAIL RU\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO = RU.TMNL_ASSET_NO\n" + 
			"  and RU.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new WTmnlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据普通群组编号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据控制群组编号查询终端相关信息
	 * @param groupNo  控制群组编号
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlDto> findTmnlByCtrlGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" +
			"       R.PROTOCOL_CODE\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,T_TMNL_GROUP_DETAIL  TT\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and R.TMNL_ASSET_NO = TT.TMNL_ASSET_NO\n" + 
			"  and TT.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new WTmnlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据控制群组编号查询关联的终端出错！");
			throw e;
		}
	}
	
	/**
	 * 根据变电站查询终端相关信息
	 * @param subsId 变电站标识
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<WTmnlDto> findTmnlBySubsId(String subsId, PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		//subsId="%"+subsId+"%";
		sb.append(
			"select C.ORG_NO,\n" +
			"       O.ORG_NAME,\n" + 
			"       C.CONS_NO,\n" + 
			"       C.CONS_NAME,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" +
			"       R.PROTOCOL_CODE\n" +
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and C.Subs_Id = ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[] {subsId,pSysUser.getStaffNo()},new WTmnlRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询关联的终端出错！");
			throw e;
		}	
	}
	
//	/**
//	 * 根据供电所查询相关信息放入备选用户Grid中（包括总加组）
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> findWTmnlByOrgWithT(String OrgNo,long start, int limit) throws DBAccessException{
//		String sql =
//			"SELECT C.Org_No,\n" +
//			"       O.ORG_NAME,\n" + 
//			"       C.CONS_NO,\n" + 
//			"       C.CONS_NAME,\n" + 
//			"       R.TERMINAL_ADDR,\n" + 
//			"       T.TMNL_ASSET_NO,\n" + 
//			"       T.Total_No\n" + 
//			"FROM  C_CONS C, O_ORG O, VW_TMNL_RUN R, t_tmnl_total_info T\n" + 
//			"WHERE O.ORG_NO = C.Org_No\n" + 
//			"AND   R.Tmnl_Asset_No=T.TMNL_ASSET_NO\n" + 
//			"AND   T.Cons_No=C.Cons_No\n" + 
//			"AND   C.BUREAU_NO=?";
//		try{
//			Page<WTmnlDto> page = new Page<WTmnlDto>();
//			page = this.pagingFind(sql, start, limit,new WTmnlRowMapperWithT(),OrgNo);
//			return page;
//		}catch(RuntimeException e){
//			this.logger.debug("根据供电所查询关联的终端出错！");
//			throw e;
//		}
//	}
	
//	/**
//     * 根据方案ID查询终端信息（终端保电）
//     * @param schemeId  方案ID
//     * @return
//     * @throws DBAccessException
//     */
//	@SuppressWarnings("unchecked")
//	public List<WTmnlDto> findProtectTmnlBySchemeId(Long schemeId) {
//		String sql = 
//			"select C.ORG_NO,\n" +
//			"       O.ORG_NAME,\n" + 
//			"       C.CONS_NO,\n" + 
//			"       C.Cons_Name,\n" + 
//			"       R.TERMINAL_ADDR,\n" +
//			"       R.PROTOCOL_CODE,\n" + 
//			"       R.TMNL_ASSET_NO,\n" + 
//			"       W.Auto_Paul_Power,\n" + 
//			"       W.Security_Value,\n" + 
//			"       W.Duration\n" + 
//			"  FROM w_Tmnl_Paul_Power w,C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
//			"  where w.org_no=O.ORG_NO\n" + 
//			"  and   w.cons_no=C.Cons_No\n" + 
//			"  and   W.Tmnl_Asset_No=R.Tmnl_Asset_No\n" + 
//			"  and   W.CTRL_SCHEME_ID=?";
//		try{
//			return this.getJdbcTemplate().query(sql,new Object[]{schemeId},new TmnlProtectRowMapper());
//		}catch(RuntimeException e){
//			this.logger.debug("根据方案ID查询关联的终端出错！");
//			throw e;
//		}
//	}
//	
//	/**
//	 * 根据方案ID查询终端信息（终端剔除）
//	 * @param schemeId 方案ID
//	 * @return
//	 * @throws DBAccessException
//	 */
//	@SuppressWarnings("unchecked")
//	public List<WTmnlDto> findEliminateTmnlBySchemeId(Long schemeId) throws DBAccessException{
//		String sql = 
//			"SELECT   W.Org_No,\n" +
//			"         O.ORG_NAME,\n" + 
//			"         W.CONS_NO,\n" + 
//			"         C.CONS_NAME,\n" + 
//			"         R.TERMINAL_ADDR,\n" + 
//			"         R.PROTOCOL_CODE,\n" + 
//			"         W.TMNL_ASSET_NO,\n" + 
//			//"         W.Total_No,\n" + 
//			"         W.Is_Reject\n" + 
//			"  FROM  W_Tmnl_Reject w,C_CONS C, O_ORG O, VW_TMNL_RUN R\n" + 
//			"  WHERE O.ORG_NO = W.Org_No\n" + 
//			"  AND   C.CONS_NO= W.CONS_NO\n" + 
//			"  AND   R.Tmnl_Asset_No=W.TMNL_ASSET_NO\n" + 
//			"  AND   w.ctrl_scheme_id=?";
//		try{
//			return this.getJdbcTemplate().query(sql,new Object[]{schemeId},new TmnlEliminateRowMapper());
//		}catch(RuntimeException e){
//			this.logger.debug("根据方案ID查询关联的终端出错！");
//			throw e;
//		}
//	}
	
//	/**
//	 * 根据用户查询相关信息放入备选用户Grid中（包括总加组）
//	 * @return
//	 * @throws DBAccessException
//	 */
//	public Page<WTmnlDto> findWTmnlByConsWithT(String consNo,long start, int limit) throws DBAccessException{
//		String sql = 
//			"SELECT   C.Org_No,\n" +
//			"         O.ORG_NAME,\n" + 
//			"         C.CONS_NO,\n" + 
//			"         C.CONS_NAME,\n" + 
//			"         R.TERMINAL_ADDR,\n" + 
//			"         T.TMNL_ASSET_NO,\n" + 
//			"         T.Total_No\n" + 
//			"  FROM  C_CONS C, O_ORG O, VW_TMNL_RUN R, t_tmnl_total_info T\n" + 
//			"  WHERE O.ORG_NO = C.Org_No\n" + 
//			"  AND   R.Tmnl_Asset_No=T.TMNL_ASSET_NO\n" + 
//			"  AND   T.Cons_No=C.Cons_No\n" + 
//			"  AND   C.CONS_NO=?";
//		try{
//			Page<WTmnlDto> page = new Page<WTmnlDto>();
//			page = this.pagingFind(sql, start, limit,new WTmnlRowMapperWithT(),consNo);
//			return page;
//		}catch(RuntimeException e){
//			this.logger.debug("根据用户编号查询关联的终端出错！");
//			throw e;
//		}
//	}
	class WTmnlRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			WTmnlDto wTmnlDto=new WTmnlDto();
			try {
				wTmnlDto.setOrgNo(rs.getString("ORG_NO"));
				wTmnlDto.setOrgName(rs.getString("ORG_NAME"));
				wTmnlDto.setConsNo(rs.getString("CONS_NO"));
				wTmnlDto.setConsName(rs.getString("CONS_NAME"));
				wTmnlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				wTmnlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				wTmnlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
				return wTmnlDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
//	class WTmnlRowMapperWithT implements RowMapper{
//		@Override
//		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
//			WTmnlDto wTmnlDto=new WTmnlDto();
//			try {
//				wTmnlDto.setTmnlTotal(rs.getString("TMNL_ASSET_NO")+rs.getString("Total_No"));
//				wTmnlDto.setOrgNo(rs.getString("ORG_NO"));
//				wTmnlDto.setOrgName(rs.getString("ORG_NAME"));
//				wTmnlDto.setConsNo(rs.getString("CONS_NO"));
//				wTmnlDto.setConsName(rs.getString("CONS_NAME"));
//				wTmnlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
//				wTmnlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
//				wTmnlDto.setTotalNo(rs.getShort("Total_No"));
//				return wTmnlDto;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//	}
//	
	
//	class TmnlProtectRowMapper implements RowMapper{
//		@Override
//		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
//			WTmnlDto wTmnlDto=new WTmnlDto();
//			try {
//				wTmnlDto.setOrgNo(rs.getString("ORG_NO"));
//				wTmnlDto.setOrgName(rs.getString("ORG_NAME"));
//				wTmnlDto.setConsNo(rs.getString("CONS_NO"));
//				wTmnlDto.setConsName(rs.getString("CONS_NAME"));
//				wTmnlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
//				wTmnlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
//				wTmnlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
//				return wTmnlDto;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//	}
//	
//	class TmnlEliminateRowMapper implements RowMapper{
//		@Override
//		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
//			WTmnlDto wTmnlDto=new WTmnlDto();
//			try {
//				//wTmnlDto.setTmnlTotal(rs.getString("TMNL_ASSET_NO")+rs.getString("Total_No"));
//				wTmnlDto.setOrgNo(rs.getString("ORG_NO"));
//				wTmnlDto.setOrgName(rs.getString("ORG_NAME"));
//				wTmnlDto.setConsNo(rs.getString("CONS_NO"));
//				wTmnlDto.setConsName(rs.getString("CONS_NAME"));
//				wTmnlDto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
//				wTmnlDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
//				//wTmnlDto.setTotalNo(rs.getShort("Total_No"));
//				wTmnlDto.setProtocolCode(rs.getString("PROTOCOL_CODE"));
//				return wTmnlDto;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//	}
	
}
