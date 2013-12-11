package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.ITTmnlGroupUserJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.TTmnlGroupUserDto;
import com.nari.privilige.PSysUser;
import com.nari.sysman.securityman.impl.SysPrivilige;
import com.nari.util.exception.DBAccessException;

/** 
 * 作者: 姜海辉
 * 创建时间：2010-1-9 上午10:50:51 
 * 描述：群组用户JdbcDao接口实现类
 */

public class TTmnlGroupUserJdbcDaoImpl extends JdbcBaseDAOImpl implements ITTmnlGroupUserJdbcDao{
	
	/**
	 * 根据终端资产编号查询用户信息
	 * @param tmnlAssetNo 终端资产编号
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByTmnlAssetNo(String tmnlAssetNo) throws DBAccessException{
		String sql=
			"select O.Org_Name,\n" + 
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
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
			"  and R.TMNL_ASSET_NO =?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据终端资产编号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据用户编号查询用户信息
	 * @param consNo 用户编号
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByConsNo(String consNo) throws DBAccessException{
		String sql=
			"select O.Org_Name,\n" + 
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
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
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{consNo},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据用户编号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据供电单位编号查询用户信息
	 * @param orgType  供电单位类型
	 * @param orgNo    供电单位编号
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByOrgNo(String orgNo,String orgType,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb=new  StringBuffer();
		sb.append( 
			"select O.Org_Name,\n" + 
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
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
			"  and B.TRADE_NO(+)= C.Trade_Code\n");
		if("01".equals(orgType)||"02".equals(orgType)||"03".equals(orgType)||"04".equals(orgType)){
			 orgNo="%"+orgNo+"%";
			 sb.append("  and C.AREA_No like ?");
		}else if("06".equals(orgType))
			 sb.append("  and C.Org_No = ?");
		else
			 return null;
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return this.getJdbcTemplate().query(sb.toString(),new Object[]{orgNo,pSysUser.getStaffNo()},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据供电单位编号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据线路Id查询用户信息
	 * @param lineId 线路Id
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByLineId(String lineId,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb=new  StringBuffer();
		sb.append(
			"select O.Org_Name,\n" + 
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
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
			"  and C.Line_Id= ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return  this.getJdbcTemplate().query(sb.toString(),new Object[]{lineId,pSysUser.getStaffNo()},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据线路Id查询用户信息出错！");
			throw e;
		}
	}
	/**
	 * 根据普通群组编号查询用户信息
	 * @param groupNo 普通群组编号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByCommonGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select O.Org_Name,\n" +
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
			"       C.Elec_Addr,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       G.Line_Name,\n" + 
			"       B.Trade_Name,\n" + 
			"       C.Run_Cap\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B,R_USER_GROUP_DETAIL RU\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and G.Line_Id = C.Line_Id\n" + 
			"  and B.TRADE_NO(+)= C.Trade_Code\n" + 
			"  and C.CONS_NO=R.CONS_NO\n" + 
			"  and R.TMNL_ASSET_NO=RU.TMNL_ASSET_NO\n" + 
			"  and RU.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据普通群组编号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据控制群组编号查询用户信息
	 * @param groupNo  控制群组编号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserByCtrlGroupNo(String groupNo) throws DBAccessException{
		String sql=
			"select O.Org_Name,\n" +
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
			"       C.Elec_Addr,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       G.Line_Name,\n" + 
			"       B.Trade_Name,\n" + 
			"       C.Run_Cap\n" + 
			"  FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B,T_TMNL_GROUP_DETAIL  TT\n" + 
			"  where O.ORG_NO = C.Org_No\n" + 
			"  and G.Line_Id = C.Line_Id\n" + 
			"  and B.TRADE_NO(+)= C.Trade_Code\n" + 
			"  and C.CONS_NO=R.CONS_NO\n" + 
			"  and R.TMNL_ASSET_NO=TT.TMNL_ASSET_NO\n" + 
			"  and TT.GROUP_NO = ?";
		try{
			return this.getJdbcTemplate().query(sql,new Object[]{groupNo},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据控制群组编号查询用户信息出错！");
			throw e;
		}
	}
	
	/**
	 * 根据变电站标识查询用户信息
	 * @param subsId 变电站标识
	 * @return 
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TTmnlGroupUserDto> findTTmnlGroupUserBySubsId(String subsId,PSysUser pSysUser) throws DBAccessException{
		StringBuffer sb=new  StringBuffer();
		//subsId= "%"+subsId+"%";
		sb.append( 
			"select O.Org_Name,\n" +
			"       C.Cons_No,\n" + 
			"       C.Cons_Name,\n" + 
			"       C.Elec_Addr,\n" + 
			"       R.TERMINAL_ADDR,\n" + 
			"       R.TMNL_ASSET_NO,\n" + 
			"       G.Line_Name,\n" + 
			"       B.Trade_Name,\n" + 
			"       C.Run_Cap\n" + 
			" FROM C_CONS C, O_ORG O, VW_TMNL_RUN R,G_LINE G, VW_TRADE  B\n" + 
			" where O.ORG_NO = C.Org_No\n" + 
			" and G.Line_Id = C.Line_Id\n" + 
			" and B.TRADE_NO(+)= C.Trade_Code\n" + 
			" and R.CONS_NO=C.CONS_NO\n" + 
			" and C.Subs_Id = ?");
		sb.append(SysPrivilige.addOrgPri(pSysUser,"C"));
		try{
			return  this.getJdbcTemplate().query(sb.toString(),new Object[]{subsId,pSysUser.getStaffNo()},new TTmnlGroupUserRowMapper());
		}catch(RuntimeException e){
			this.logger.debug("根据变电站标识查询用户信息出错！");
			throw e;
		}
	}
	
	class TTmnlGroupUserRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TTmnlGroupUserDto tTmnlGroupUserDto=new TTmnlGroupUserDto();
			try{
				//tTmnlGroupUserDto.setOrgNo(rs.getString("ORG_NO"));
				tTmnlGroupUserDto.setOrgName(rs.getString("ORG_NAME"));
				tTmnlGroupUserDto.setConsNo(rs.getString("CONS_NO"));
				tTmnlGroupUserDto.setConsName(rs.getString("CONS_NAME"));
				tTmnlGroupUserDto.setConsAddr(rs.getString("Elec_Addr"));
				tTmnlGroupUserDto.setTmnlAddr(rs.getString("TERMINAL_ADDR"));
				tTmnlGroupUserDto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				tTmnlGroupUserDto.setLine(rs.getString("Line_Name"));
				tTmnlGroupUserDto.setTrade(rs.getString("Trade_Name"));
				tTmnlGroupUserDto.setRunCap(rs.getString("Run_Cap"));
				return  tTmnlGroupUserDto;
			}catch (Exception e) {
				return null;
			}
		}
	}
   
}
        
    



