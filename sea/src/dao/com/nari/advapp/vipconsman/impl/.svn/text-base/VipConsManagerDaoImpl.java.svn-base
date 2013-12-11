package com.nari.advapp.vipconsman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.vipconsman.VipConsManagerDao;
import com.nari.advapp.vipconsman.VipConsManagerDto;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/**
 * 重点用户定义列表 dao
 * 
 * @author ChunMingLi
 * @since  2010-6-3
 *
 */
public class VipConsManagerDaoImpl extends JdbcBaseDAOImpl implements
		VipConsManagerDao {


	@Override
	public Page<VipConsManagerDto> findVipConsDefineListByLine(String line,PSysUser pSysUser, long start, int limit) throws DBAccessException {
		List<Object> vipList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
			
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.ORG_NO,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("      G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("       C.Run_Cap  \n");
		sql.append("FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B  \n");
		sql.append("where O.ORG_NO = C.Org_No \n");
		sql.append("and R.Cons_No = C.Cons_No  \n");
		sql.append("and G.Line_Id = C.Line_Id  \n");
		sql.append("and B.TRADE_NO(+)= C.Trade_Code  \n");
		sql.append("and C.Line_Id= ?  \n");
		vipList.add(line);
		sql.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		vipList.add(pSysUser.getStaffNo());
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据线路Id查询用户信息出错！");
			throw e;
		}
	}

	@Override
	public Page<VipConsManagerDto> findVipConsDefineListByOrgNo(String orgNo,String orgType, PSysUser pSysUser, long start, int limit) throws DBAccessException{
		List<Object> vipList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.ORG_NO,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("       G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("       C.Run_Cap  \n");
		sql.append(" FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B  \n");
		sql.append(" where O.ORG_NO = C.Org_No \n");
		sql.append(" and R.Cons_No = C.Cons_No \n");
		sql.append(" and G.Line_Id = C.Line_Id \n");
		sql.append(" and B.TRADE_NO(+)= C.Trade_Code \n");
		if("01".equals(orgType)||"02".equals(orgType)||"03".equals(orgType)||"04".equals(orgType)){
			 orgNo="%"+orgNo+"%";
			 sql.append("  and C.AREA_No like ? \n");
			 vipList.add(orgNo);
		}else if("06".equals(orgType)){
			sql.append("  and C.Org_No = ?  \n");
			vipList.add(orgNo);
		}
		sql.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		vipList.add(pSysUser.getStaffNo());
		
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据供电单位编号查询用户信息出错！");
			throw e;
		}
	}

	@Override
	public Page<VipConsManagerDto> findVipConsDefineListBySub(String subId,PSysUser pSysUser, long start, int limit) throws DBAccessException{
		List<Object> vipList = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.ORG_NO,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("       G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("      C.Run_Cap  \n");
		sql.append("FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B  \n");
		sql.append("  where O.ORG_NO = C.Org_No \n");
		sql.append("and G.Line_Id = C.Line_Id  \n");
		sql.append("and B.TRADE_NO(+)= C.Trade_Code  \n");
		sql.append(" and R.CONS_NO=C.CONS_NO \n");
		sql.append("and C.Subs_Id = ?  \n");
		vipList.add(subId);
		sql.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		vipList.add(pSysUser.getStaffNo());
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询用户信息出错！");
			throw e;
		}
	}

	@Override
	public Page<VipConsManagerDto> findVipConsDefineListByTmnlAssetNo(String tmnlAssetNo, long start, int limit) throws DBAccessException{
		List<Object> vipList = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.ORG_NO,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("       G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("       C.Run_Cap  \n");
		sql.append("FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B  \n");
		sql.append("where O.ORG_NO = C.Org_No  \n");
		sql.append("and R.Cons_No = C.Cons_No  \n");
		sql.append("and G.Line_Id = C.Line_Id  \n");
		sql.append("and B.TRADE_NO(+)= C.Trade_Code  \n");
		sql.append("and R.TMNL_ASSET_NO =?  \n");
		vipList.add(tmnlAssetNo);
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据终端资产编号查询用户信息出错！");
			throw e;
		}
	}
	
	

	@Override
	public Page<VipConsManagerDto> findVipConsDefineListByCommonGroupNo(String groupNo, long start, int limit) throws DBAccessException{
		List<Object> vipList = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.Org_No,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("       G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("       C.Run_Cap  \n");
		sql.append(" FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B,R_USER_GROUP_DETAIL RU  \n");
		sql.append(" where O.ORG_NO = C.Org_No \n");
		sql.append(" and G.Line_Id = C.Line_Id \n");
		sql.append("  and B.TRADE_NO(+)= C.Trade_Code\n");
		sql.append(" and C.CONS_NO=R.CONS_NO \n");
		sql.append("  and R.TMNL_ASSET_NO=RU.TMNL_ASSET_NO\n");
		sql.append("  and RU.GROUP_NO = ?\n");
		vipList.add(groupNo);
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据普通群组编号查询用户信息出错！");
			throw e;
		}
	}

	@Override
	public Page<VipConsManagerDto> findVipConsDefineListByCtrlGroupNo(	String groupNo, long start, int limit) throws DBAccessException{
		List<Object> vipList = new ArrayList<Object>();
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.Org_No,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("       G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("        C.Run_Cap \n");
		sql.append("FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B,T_TMNL_GROUP_DETAIL  TT  \n");
		sql.append("where O.ORG_NO = C.Org_No  \n");
		sql.append("and G.Line_Id = C.Line_Id  \n");
		sql.append("and B.TRADE_NO(+)= C.Trade_Code  \n");
		sql.append("and C.CONS_NO=R.CONS_NO  \n");
		sql.append("and R.TMNL_ASSET_NO=TT.TMNL_ASSET_NO  \n");
		sql.append("and TT.GROUP_NO = ?  \n");
		vipList.add(groupNo);
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据控制群组编号查询用户信息出错！");
			throw e;
		}
	}
	
	
	@Override
	public Page<VipConsManagerDto> findVipConsDefineListByConsNo(String consNo,long start, int limit) throws DBAccessException {
		List<Object> vipList = new ArrayList<Object>();
		String sql=
			"select O.Org_Name,\n" + 
			"       O.Org_No,  \n"+
			"       O.ORG_TYPE,  \n"+
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
			"       C.CONS_ID,  \n" + 
			"       C.Elec_Addr,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       G.Line_Name,\n" + 
			"       B.Trade_Name,\n" + 
			"       C.Run_Cap\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and R.Cons_No = C.Cons_No\n" + 
			"  and G.Line_Id = C.Line_Id\n" + 
			"  and B.TRADE_NO(+)= C.Trade_Code\n" + 
			"  and C.Cons_No=?";
		vipList.add(consNo);
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsManagerRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据用户编号查询用户信息出错！");
			throw e;
		}
	}
	

	/**
	 * 
	 * 重点用户的备选用户映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-6-5
	 *
	 */
	class VipConsManagerRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsManagerDto managerDto  = new VipConsManagerDto();
			try {
				managerDto.setOrgName(rs.getString("Org_Name"));
				managerDto.setOrgNo(rs.getString("Org_No"));
				managerDto.setOrgType(rs.getString("ORG_TYPE"));
				managerDto.setConsNo(rs.getString("Cons_No"));
				managerDto.setConsName(rs.getString("Cons_Name"));
				managerDto.setConsAddr(rs.getString("Elec_Addr"));
				managerDto.setTerminalAdd(rs.getString("TERMINAL_ADDR"));
				managerDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				managerDto.setLine(rs.getString("Line_Name"));
				managerDto.setTrade(rs.getString("Trade_Name"));
				managerDto.setRunCap(rs.getString("Run_Cap"));
				managerDto.setConsId(rs.getString("CONS_ID"));
				return managerDto;
			} catch (Exception e) {
				return null;
			}
		}
	}



	@Override
	public Page<VipConsManagerDto> findVipConsListByStaffNo(String staffNo, long start, int limit) throws DBAccessException{
		List<Object> vipList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select O.Org_Name,  \n");
		sql.append("       O.Org_No,  \n");
		sql.append("       O.ORG_TYPE,  \n");
		sql.append("       C.Cons_No,  \n");
		sql.append("       C.Cons_Name,  \n");
		sql.append("       C.CONS_ID,  \n");
		sql.append("       C.Elec_Addr,  \n");
		sql.append("       R.TERMINAL_ADDR,  \n");
		sql.append("       R.TMNL_ASSET_NO,  \n");
		sql.append("       G.Line_Name,  \n");
		sql.append("       B.Trade_Name,  \n");
		sql.append("       C.Run_Cap,  \n");
		sql.append("       V.CONS_SORT, --用户类别   \n");
		sql.append("       V.MONITOR_FREQ, --监视频度  \n");
		sql.append("       V.MONITOR_FLAG, --后台监视   \n");
		sql.append("       V.MONITOR_EVENT, --监视终端事件 \n");
		sql.append("       V.MONITOR_RUN, --监视工况   \n");
		sql.append("       V.MONITOR_EC --监视用电异常  \n");
		sql.append("FROM C_CONS_VIP V,C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B  \n");
		sql.append("WHERE O.ORG_NO = C.Org_No  \n");
		sql.append("AND C.CONS_NO = V.CONS_NO \n");
		sql.append("AND R.Cons_No = C.Cons_No  \n");
		sql.append(" AND G.Line_Id = C.Line_Id  \n");
		sql.append(" AND B.TRADE_NO(+) = C.Trade_Code  \n");
		sql.append(" AND V.STAFF_NO = ? \n");
		vipList.add(staffNo);
		sql.append("  ORDER BY O.ORG_NO  \n");
		logger.debug(sql.toString());
		try{
			return super.pagingFind(sql.toString(), start, limit, new VipConsUserRowMapper(), vipList.toArray());
		}catch(RuntimeException e){
			this.logger.debug("根据操作员查询重点用户信息出错！");
			throw e;
		}
	}
	/**
	 * 
	 * 重点用户列表映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-6-5
	 *
	 */
	class VipConsUserRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsManagerDto managerDto  = new VipConsManagerDto();
			try {
				managerDto.setOrgName(rs.getString("Org_Name"));
				managerDto.setOrgNo(rs.getString("Org_No"));
				managerDto.setOrgType(rs.getString("ORG_TYPE"));
				managerDto.setConsNo(rs.getString("Cons_No"));
				managerDto.setConsName(rs.getString("Cons_Name"));
				managerDto.setConsAddr(rs.getString("Elec_Addr"));
				managerDto.setTerminalAdd(rs.getString("TERMINAL_ADDR"));
				managerDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				managerDto.setLine(rs.getString("Line_Name"));
				managerDto.setTrade(rs.getString("Trade_Name"));
				managerDto.setRunCap(rs.getString("Run_Cap"));
				managerDto.setConsId(rs.getString("CONS_ID"));
				
				managerDto.setConsSort(rs.getString("CONS_SORT"));
				managerDto.setMonitorEc(rs.getString("MONITOR_EC"));
				managerDto.setMonitorEvent(rs.getString("MONITOR_EVENT"));
				managerDto.setMonitorFlag(rs.getString("MONITOR_FLAG"));
				managerDto.setMonitorFreq(rs.getString("MONITOR_FREQ"));
				managerDto.setMonitorRun(rs.getString("MONITOR_RUN"));
				return managerDto;
			} catch (Exception e) {
				return null;
			}
		}
	}
	
}
