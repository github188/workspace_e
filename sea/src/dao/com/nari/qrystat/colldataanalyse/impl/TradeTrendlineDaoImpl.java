package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.TradeTrendlineDao;
import com.nari.qrystat.colldataanalyse.TradeTrendlineDataBean;
import com.nari.util.NodeTypeUtils;

/**
 * 行业用电趋势分析DAO实现类
 * @author jiangyike
 */
public class TradeTrendlineDaoImpl extends JdbcBaseDAOImpl implements
		TradeTrendlineDao {
	//定义日志
	private static final Logger logger = Logger.getLogger(TradeTrendlineDaoImpl.class);
	
	/**
	 * 根据日期查询行业用电信息，把操作员所在部门下的行业统计信息统计出来，(05，06权限用户忽略，菜单封闭)
	 * ps:a_trade_stat_d，供电单位为区县和地市都有
	 * @param pSysUser
	 * @param tradNo
	 * @param queryDate
	 * @return TradeTrendlineDto
	 */
	public TradeTrendlineDataBean findTradeTrendlineByDay(PSysUser pSysUser, String tradeNo, String queryDate){
		String sql = 
			"select sum(stat.pap_e) as pap_e\n" +
			"  from a_trade_stat_d stat,\n" + 
			"       (select org_no, org_type\n" + 
			"          from p_access_org\n" + 
			"         where staff_no = ?\n" + 
			"           and org_type = ?) acc\n" + 
			" where stat.org_no = acc.org_no\n" + 
			"   and stat.trade_code = ?\n" + 
			"   and stat.stat_date = to_date(?, 'yyyy-MM-dd')\n" + 
			" group by stat.trade_code";

		logger.debug(sql);
		List<TradeTrendlineDataBean> list = super.getJdbcTemplate().query(sql,  new Object[]{pSysUser.getStaffNo(), NodeTypeUtils.NODETYPE_ORG04, tradeNo, queryDate}, new tradeTrendlineMapper());
		if(null != list && 0 < list.size()){
			return (TradeTrendlineDataBean)list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据月份查询行业用电信息，把操作员所在部门下的行业统计信息统计出来，(05，06权限用户忽略，菜单封闭)
	 * ps:a_trade_stat_d，供电单位为区县和地市都有
	 * @param pSysUser
	 * @param tradNo
	 * @param startDate
	 * @param endDate
	 * @return TradeTrendlineDataBean
	 */
	public TradeTrendlineDataBean findTradeTrendlineByMonth(PSysUser pSysUser, String tradeNo, String startDate, String endDate){
		String sql = 
			"select sum(stat.pap_e) as pap_e\n" +
			"  from a_trade_stat_d stat,\n" + 
			"       (select org_no, org_type\n" + 
			"          from p_access_org\n" + 
			"         where staff_no = ?\n" + 
			"           and org_type = ?) acc\n" + 
			" where stat.org_no = acc.org_no\n" + 
			"   and stat.trade_code = ?\n" + 
			"   and stat.stat_date >= to_date(?, 'yyyy-MM-dd') and stat.stat_date < to_date(?, 'yyyy-MM-dd')\n" + 
			" group by stat.trade_code";

		logger.debug(sql);
		List<TradeTrendlineDataBean> list = super.getJdbcTemplate().query(sql,  new Object[]{pSysUser.getStaffNo(), NodeTypeUtils.NODETYPE_ORG04, tradeNo, startDate, endDate}, new tradeTrendlineMapper());
		if(null != list && 0 < list.size()){
			return (TradeTrendlineDataBean)list.get(0);
		}
		return null;
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询行业用电7天内的信息
	 */
	class tradeTrendlineMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			TradeTrendlineDataBean dto = new TradeTrendlineDataBean();
			try {				
				if(null == rs.getString("PAP_E") || "".equals(rs.getString("PAP_E"))){
					dto.setData(null);
					dto.setFlag(true);
				}else{
					dto.setData(rs.getDouble("PAP_E"));
					dto.setFlag(false);
				}
				return dto;
			} catch (Exception e) {
				return null;
			}
		}
	}
}
