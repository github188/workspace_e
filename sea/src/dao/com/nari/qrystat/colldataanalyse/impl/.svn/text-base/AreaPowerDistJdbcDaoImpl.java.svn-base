package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.AreaPowerDistDto;
import com.nari.qrystat.colldataanalyse.AreaPowerDistJdbcDao;
import com.nari.support.Page;
import com.nari.util.NodeTypeUtils;

/**
 * 地区电量分布查询Jdbc之Dao实现类
 * @author 姜炜超
 */
public class AreaPowerDistJdbcDaoImpl extends JdbcBaseDAOImpl implements AreaPowerDistJdbcDao {
	
	/**
	 * 根据条件查询地区电量分布信息，a_org_stat_d，单位为区县和地市都有
	* @param pSysUser
	 * @param orgType
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return Page<AreaPowerDistDto>
	 */
	public Page<AreaPowerDistDto> findAPDInfoByCond(PSysUser pSysUser, String orgType, String startDate, 
			String endDate,long start, int limit){
		Page<AreaPowerDistDto> psc = new Page<AreaPowerDistDto>();
		if("".equals(orgType)){
			return psc;
		}
		String sql = "";
		if(NodeTypeUtils.NODETYPE_ORG02.equals(orgType)){
			sql = 
				"select tmp.org_no, org.org_name, tmp.pap_e\n" +
				"  from (select stat.org_no, sum(stat.pap_e) as pap_e\n" + 
				"          from a_org_stat_d stat,\n" + 
				"               (select org_no, org_type\n" + 
				"                  from p_access_org\n" + 
				"                 where staff_no = ?\n" + 
				"                   and org_type = ?) acc\n" + 
				"         where stat.org_no = acc.org_no\n" + 
				"           and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"           and stat.stat_date < to_date(?, 'yyyy-MM-dd') + 1\n" + 
				"         group by stat.org_no) tmp,\n" + 
				"       o_org org\n" + 
				" where tmp.org_no = org.org_no order by tmp.org_no asc";
			try{
				psc = this.pagingFind(sql, start, limit, new AreaPowerDistRowMapper(),new Object[]{pSysUser.getStaffNo(), 
					NodeTypeUtils.NODETYPE_ORG03, startDate, endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据条件查询地区电量分布信息出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType)){
			sql = 
				"select tmp.org_no, org.org_name, tmp.pap_e\n" +
				"  from (select stat.org_no, sum(stat.pap_e) as pap_e\n" + 
				"          from a_org_stat_d stat,\n" + 
				"               (select org_no, org_type\n" + 
				"                  from p_access_org\n" + 
				"                 where staff_no = ?\n" + 
				"                   and org_type = ? ) acc\n" + 
				"         where stat.org_no = acc.org_no\n" + 
				"           and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"           and stat.stat_date < to_date(?, 'yyyy-MM-dd') + 1\n" + 
				"         group by stat.org_no) tmp,\n" + 
				"       o_org org\n" + 
				" where tmp.org_no = org.org_no order by tmp.org_no asc";
			try{
				psc = this.pagingFind(sql, start, limit, new AreaPowerDistRowMapper(),new Object[]{pSysUser.getStaffNo(), 
					NodeTypeUtils.NODETYPE_ORG04, startDate, endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据条件查询地区电量分布信息出错！");
				throw e;
			}
		}else if(NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
			sql = 
				"select * from ("+
				"select org.org_name,org.org_no,\n" +
				"       sum(stat.pap_e) as pap_e\n" + 
				"  from a_org_stat_d stat, o_org org \n" + 
				" where stat.org_no = org.org_no\n" + 
				"   and org.org_no = ?\n" + 
				"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd')\n" + 
				"   and stat.stat_date < to_date(?, 'yyyy-MM-dd') + 1\n" +
				"   group by org.org_name,org.org_no ) order by org_no asc";
			try{
				psc = this.pagingFind(sql, start, limit, new AreaPowerDistRowMapper(),new Object[]{pSysUser.getOrgNo(), 
						startDate, endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据条件查询地区电量分布信息出错！");
				throw e;
			}
		}
		return psc;
	}
	
	/**
	 * 根据供电单位查询该单位类型
	 * @param orgNo
	 * @return String
	 */
	public String findOrgType(String orgNo){
		String sql = "select org_type\n" +
			"      from o_org\n" + 
			"      where org_no = ?" ;
		List<AreaPowerDistDto> list= super.getJdbcTemplate().query(sql, new Object[] {orgNo}, new AreaPowerDistOrgInfoMapper());
	    if(list == null || 0 == list.size()){
	    	return "";
	    }
	    return ((AreaPowerDistDto)list.get(0)).getOrgType();
	}		
	
	/**
	 * 自定义查询返回的值对象，用于地区电量分布的Grid显示
	 */
	class AreaPowerDistRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AreaPowerDistDto areaPowerDistDto = new AreaPowerDistDto();
			try {
				areaPowerDistDto.setOrgName(rs.getString("ORG_NAME"));
				areaPowerDistDto.setOrgNo(rs.getString("ORG_NO"));
				areaPowerDistDto.setPapEBasic((null != rs.getString("PAP_E"))? rs.getDouble("PAP_E") : null);
				return areaPowerDistDto;
			} catch (Exception e) {
				e.printStackTrace();
				AreaPowerDistJdbcDaoImpl.this.logger.error("取 AreaPowerDistRowMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询供电单位类别
	 */
	class AreaPowerDistOrgInfoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			AreaPowerDistDto dto = new AreaPowerDistDto();
			try {
				dto.setOrgType(rs.getString("ORG_TYPE"));
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}
}
