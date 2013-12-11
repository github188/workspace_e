package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.AOrgLoadStatDto;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorCurveBean;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorDto;
import com.nari.qrystat.colldataanalyse.CurrLoadMonitorJdbcDao;
import com.nari.util.NodeTypeUtils;

/**
 * 日用电负荷监测Jdbc之Dao实现类
 * @author 姜炜超
 */
public class CurrLoadMonitorJdbcDaoImpl extends JdbcBaseDAOImpl implements CurrLoadMonitorJdbcDao{
	//定义日志
	private static final Logger logger = Logger.getLogger(CurrLoadMonitorJdbcDaoImpl.class);
	/**
	 * 根据条件查询日用电负荷监测信息，用于grid显示，a_main_page_curve，单位到区县
	 * 因为是从树左边拉过来的，所以，02，03用户要权限校验，而04用户不需要校验
	 * @param orgNo
	 * @param orgType
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorCurveBean> findCLMInfoByCond(String orgNo, String orgType, String queryDate, PSysUser pSysUser){
		if(null == orgNo || null == orgType || "".equals(orgNo) || "".equals(orgType)){
			return new ArrayList<CurrLoadMonitorCurveBean>();
		}
		List<CurrLoadMonitorCurveBean> list = null;
		String sql = "";
		if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
			sql = 
				"select to_char(m.time_point) as time_point, sum(m.p) as p\n" +
				"           from a_main_page_curve m, (select org_no, org_type from p_access_org where staff_no = ? and org_type=?) acc \n" + 
				"          where m.org_no = acc.org_no\n" +
				"          and m.curve_Date = to_date(? , 'yyyy-mm-dd')\n" + 
				"          group by m.time_point\n" + 
				"          order by m.time_point";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {pSysUser.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04,queryDate}, new CurrLoadMonitorMapper());
		}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
			orgNo = orgNo+"%";
			sql = 
				"select to_char(m.time_point) as time_point, sum(m.p) as p\n" +
				"          from a_main_page_curve m, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no like ?) acc \n" + 
				"         where m.org_no = acc.org_no\n" + 
				"           and m.curve_Date = to_date(?, 'yyyy-mm-dd')\n" + 
				"         group by m.time_point\n" + 
				"         order by m.time_point";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {pSysUser.getStaffNo(), NodeTypeUtils.NODETYPE_ORG04,orgNo, queryDate}, new CurrLoadMonitorMapper());
		}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
			sql = 
				"select to_char(m.time_point) as time_point, sum(m.p) as p\n" +
				"           from a_main_page_curve m \n" + 
				"          where m.curve_Date = to_date(?, 'yyyy-mm-dd')\n" + 
				"            and m.org_no = ?\n" + 
				"          group by m.org_no, m.time_point\n" + 
				"          order by m.time_point";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {queryDate, orgNo}, new CurrLoadMonitorMapper());
		}
		if(null == list){
			return new ArrayList<CurrLoadMonitorCurveBean>();
		}
		return list;
	}
	
	/**
	 * 根据条件查询冻结日用电负荷监测信息，A_ORG_LOAD_STAT_D也是到区县
	 * @param orgNo
	 * @param orgType
	 * @param queryDate
	 * @param pSysUser
	 * @return Page<AOrgLoadStatDto>
	 */
	public List<AOrgLoadStatDto> findCLMLSTATInfoByCond(String orgNo, String orgType, String queryDate, PSysUser pSysUser){
		if(null == orgNo || null == orgType || "".equals(orgNo) || "".equals(orgType)){
			return new ArrayList<AOrgLoadStatDto>();
		}
		List<AOrgLoadStatDto> list = null;
		String sql = "";		
		if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
			sql = 
				"select sum(d.h00) as h00, sum(d.h01) as h01, sum(d.h02) as h02 , sum(d.h03) as h03 , sum(d.h04) as h04,\n" +
				"       sum(d.h05) as h05, sum(d.h06) as h06, sum(d.h07) as h07 , sum(d.h08) as h08 , sum(d.h09) as h09,\n" + 
				"       sum(d.h10) as h10, sum(d.h11) as h11, sum(d.h12) as h12 , sum(d.h13) as h13 , sum(d.h14) as h14,\n" + 
				"       sum(d.h15) as h15, sum(d.h16) as h16, sum(d.h17) as h17 , sum(d.h18) as h18 , sum(d.h19) as h19,\n" + 
				"       sum(d.h20) as h20, sum(d.h21) as h21, sum(d.h22) as h22 , sum(d.h13) as h23\n" + 
				" from A_ORG_LOAD_STAT_D d, (select org_no, org_type from p_access_org where staff_no = ? and org_type=?) acc\n" + 
				"where d.org_no = acc.org_no\n" +
				"    and stat_Date = to_date(?, 'yyyy-mm-dd')\n" + 
				"  group by stat_Date";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {pSysUser.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04,queryDate}, new CurrLoadMonitorLSTATMapper());
		}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
			orgNo = orgNo+"%";
			sql = 
				"select sum(d.h00) as h00, sum(d.h01) as h01, sum(d.h02) as h02 , sum(d.h03) as h03 , sum(d.h04) as h04,\n" +
				"       sum(d.h05) as h05, sum(d.h06) as h06, sum(d.h07) as h07 , sum(d.h08) as h08 , sum(d.h09) as h09,\n" + 
				"       sum(d.h10) as h10, sum(d.h11) as h11, sum(d.h12) as h12 , sum(d.h13) as h13 , sum(d.h14) as h14,\n" + 
				"       sum(d.h15) as h15, sum(d.h16) as h16, sum(d.h17) as h17 , sum(d.h18) as h18 , sum(d.h19) as h19,\n" + 
				"       sum(d.h20) as h20, sum(d.h21) as h21, sum(d.h22) as h22 , sum(d.h13) as h23\n" + 
				"  from A_ORG_LOAD_STAT_D d, (select org_no, org_type from p_access_org where staff_no = ? and org_type=? and org_no like ?) acc\n" + 
				" where d.org_no = acc.org_no\n" + 
				"   and d.stat_Date = to_date(?, 'yyyy-mm-dd')\n" + 
				"   group by d.stat_Date";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {pSysUser.getStaffNo(), NodeTypeUtils.NODETYPE_ORG04, orgNo, queryDate}, new CurrLoadMonitorLSTATMapper());
		}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
			sql = 
				"select sum(h00) as h00, sum(h01) as h01, sum(h02) as h02 , sum(h03) as h03 , sum(h04) as h04,\n" +
				"      sum(h05) as h05, sum(h06) as h06, sum(h07) as h07 , sum(h08) as h08 , sum(h09) as h09,\n" + 
				"      sum(h10) as h10, sum(h11) as h11, sum(h12) as h12 , sum(h13) as h13 , sum(h14) as h14,\n" + 
				"      sum(h15) as h15, sum(h16) as h16, sum(h17) as h17 , sum(h18) as h18 , sum(h19) as h19,\n" + 
				"      sum(h20) as h20, sum(h21) as h21, sum(h22) as h22 , sum(h13) as h23\n" + 
				" from A_ORG_LOAD_STAT_D\n" + 
				"where org_no = ?\n" + 
				"  and stat_Date = to_date(?, 'yyyy-mm-dd')\n" + 
				"  group by org_no";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {orgNo, queryDate}, new CurrLoadMonitorLSTATMapper());
		}
		if(null == list){
			return new ArrayList<AOrgLoadStatDto>();
		}
		return list;
	}
	
	
	/**
	 * 根据供电单位查询该单位及子单位信息
	 * @param orgNo
	 * @param orgType
	 * @param pSysUser
	 * @return List<CurrLoadMonitorDto>
	 */
	public List<CurrLoadMonitorDto> findOrgInfo(String orgNo, String orgType, PSysUser pSysUser){
		String sql = "";
		List<CurrLoadMonitorDto> list = null;
		if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
			sql = 
				"select org_no, org_name, org_type\n" +
				"  from (select org_no, org_name, org_type\n" + 
				"          from o_org\n" + 
				"         where org_no = ? \n" + 
				"        union\n" + 
				"        select o.org_no, o.org_name, o.org_type from o_org o, \n"+
				"        (select org_no, org_type from p_access_org where staff_no \n"+
				"         = ? and org_type=?) acc where o.org_no = acc.org_no )\n" + 
				" order by org_no";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {orgNo,pSysUser.getStaffNo(),NodeTypeUtils.NODETYPE_ORG03}, new CurrLoadMonitorOrgInfoMapper());
		}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
			String orgNo2 = orgNo+"%";
			sql = 
				"select org_no, org_name, org_type\n" +
				"  from (select org_no, org_name, org_type\n" + 
				"          from o_org\n" + 
				"         where org_no = ? \n" + 
				"        union\n" + 
				"        select o.org_no, o.org_name, o.org_type from o_org o, \n"+
				"        (select org_no, org_type from p_access_org where staff_no \n"+
				"         = ? and org_type=? and org_no like ? ) acc where o.org_no = acc.org_no )\n" + 
				" order by org_no";
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {orgNo,pSysUser.getStaffNo(),NodeTypeUtils.NODETYPE_ORG04,orgNo2}, new CurrLoadMonitorOrgInfoMapper());
		}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
			sql = 
				"select org_no, org_name, org_type\n" +
				"      from o_org\n" + 
				"      where org_no = ?" ;
			logger.debug(sql);
			list = super.getJdbcTemplate().query(sql, new Object[] {orgNo}, new CurrLoadMonitorOrgInfoMapper());
			
		}else{
			//do nothing
		}		
		if(null == list){
			return new ArrayList<CurrLoadMonitorDto>();
		}
		return list;
	}
	
	/**
	 * 自定义查询返回的值对象，用户日用电负荷监测信息，grid和曲线都用到
	 */
	class CurrLoadMonitorMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CurrLoadMonitorCurveBean dto = new CurrLoadMonitorCurveBean();
			try {
				dto.setData((null != rs.getString("P"))? rs.getDouble("P") : null);
				dto.setTime(rs.getString("TIME_POINT"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				CurrLoadMonitorJdbcDaoImpl.this.logger.error("取 CurrLoadMonitorJdbcDaoImpl 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用户查询冻结日用电负荷监测信息
	 */
	class CurrLoadMonitorLSTATMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AOrgLoadStatDto dto = new AOrgLoadStatDto();
			try {
				dto.setH00(rs.getDouble("H00"));
				dto.setH01(rs.getDouble("H01"));
				dto.setH02(rs.getDouble("H02"));
				dto.setH03(rs.getDouble("H03"));
				dto.setH04(rs.getDouble("H04"));
				dto.setH05(rs.getDouble("H05"));
				dto.setH06(rs.getDouble("H06"));
				dto.setH07(rs.getDouble("H07"));
				dto.setH08(rs.getDouble("H08"));
				dto.setH09(rs.getDouble("H09"));
				dto.setH10(rs.getDouble("H10"));
				dto.setH11(rs.getDouble("H11"));
				dto.setH12(rs.getDouble("H12"));
				dto.setH13(rs.getDouble("H13"));
				dto.setH14(rs.getDouble("H14"));
				dto.setH15(rs.getDouble("H15"));
				dto.setH16(rs.getDouble("H16"));
				dto.setH17(rs.getDouble("H17"));
				dto.setH18(rs.getDouble("H18"));
				dto.setH19(rs.getDouble("H19"));
				dto.setH20(rs.getDouble("H20"));
				dto.setH21(rs.getDouble("H21"));
				dto.setH22(rs.getDouble("H22"));
				dto.setH23(rs.getDouble("H23"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				CurrLoadMonitorJdbcDaoImpl.this.logger.error("取 CurrLoadMonitorJdbcDaoImpl 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于Grid显示供电单位信息
	 */
	class CurrLoadMonitorOrgInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CurrLoadMonitorDto dto = new CurrLoadMonitorDto();
			try {
				dto.setOrgNo(rs.getString("ORG_NO"));
				dto.setOrgName(rs.getString("ORG_NAME"));
				dto.setOrgType(rs.getString("ORG_TYPE"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				CurrLoadMonitorJdbcDaoImpl.this.logger.error("取 CurrLoadMonitorJdbcDaoImpl 时出现错误！ ");
				return null;
			}
		}
	}
}
