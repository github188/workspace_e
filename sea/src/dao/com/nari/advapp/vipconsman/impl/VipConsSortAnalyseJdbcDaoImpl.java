package com.nari.advapp.vipconsman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.vipconsman.VipConsSortAnalyseBean;
import com.nari.advapp.vipconsman.VipConsSortAnalyseJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.DateUtil;
import com.nari.util.exception.DBAccessException;

/**
 * 重点户排名分析查询Jdbc之Dao实现类
 * @author 姜炜超
 */
public class VipConsSortAnalyseJdbcDaoImpl extends JdbcBaseDAOImpl implements VipConsSortAnalyseJdbcDao{
	//定义日志
	private static final Logger logger = Logger.getLogger(VipConsSortAnalyseJdbcDaoImpl.class);
	/**
	 * 根据条件查询用电排名分析信息
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param flag 1表示最大电能量，2表示最小电能量
	 * @param start
	 * @param limit
	 * @return Page<VipConsPowerSortBean>
	 */
	public Page<VipConsSortAnalyseBean> findVipConsPowerSort(PSysUser pSysUser, String startDate,
			String endDate, int flag, long start, int limit) throws DBAccessException {
		StringBuffer sql = new StringBuffer();
		if(1 == flag){
			sql.append("SELECT STAT.ORG_NO,STAT.CONS_NO,ORG.ORG_NAME,CONS.CONS_NAME,STAT.PAP_E,\n")
				.append("ROW_NUMBER() OVER ( ORDER BY STAT.PAP_E DESC) AS SORT\n")
				.append("FROM A_CONS_STAT_D STAT, C_CONS_VIP VIP, C_CONS CONS, O_ORG ORG\n")
				.append("WHERE STAT.CONS_NO = VIP.CONS_NO\n")
				.append("AND STAT.CONS_NO = CONS.CONS_NO\n")
				.append("AND STAT.ORG_NO = ORG.ORG_NO\n")
				.append("AND STAT.STAT_DATE >= TO_DATE(?,'yyyy-MM-dd')\n")
				.append("AND STAT.STAT_DATE < (TO_DATE(?,'yyyy-MM-dd')+1)\n")
				.append("AND STAT.PAP_E IS NOT NULL\n")
			    .append("ORDER BY STAT.PAP_E DESC");
			logger.debug(sql);
			try{
				return super.pagingFind(sql.toString(), start, limit, new VipConsPowerMaxSortRowMapper(),
						new Object[]{startDate,endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据时间查询重点用户最大电能量排名分析信息出错！");
				throw e;
			}
		}else if(2 == flag){
			sql.append("SELECT STAT.ORG_NO,STAT.CONS_NO,ORG.ORG_NAME,CONS.CONS_NAME,STAT.PAP_E,\n")
				.append("ROW_NUMBER() OVER ( ORDER BY STAT.PAP_E ASC) AS SORT\n")
				.append("FROM A_CONS_STAT_D STAT, C_CONS_VIP VIP, C_CONS CONS, O_ORG ORG\n")
				.append("WHERE STAT.CONS_NO = VIP.CONS_NO\n")
				.append("AND STAT.CONS_NO = CONS.CONS_NO\n")
				.append("AND STAT.ORG_NO = ORG.ORG_NO\n")
				.append("AND STAT.STAT_DATE >= TO_DATE(?,'yyyy-MM-dd')\n")
				.append("AND STAT.STAT_DATE < (TO_DATE(?,'yyyy-MM-dd')+1)\n")
				.append("AND STAT.PAP_E IS NOT NULL\n")
			    .append("ORDER BY STAT.PAP_E ASC");
			logger.debug(sql);
			try{
				return super.pagingFind(sql.toString(), start, limit, new VipConsPowerMinSortRowMapper(),
						new Object[]{startDate,endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据时间查询重点用户最小电能量排名分析信息出错！");
				throw e;
			}
		}else{
			return new Page<VipConsSortAnalyseBean>();
		}
	}
	
	/**
	 * 根据条件查询负荷排名分析信息
	 * @param pSysUser
	 * @param startDate
	 * @param endDate
	 * @param flag 1表示最高负荷，2表示最低负荷
	 * @param start
	 * @param limit
	 * @return Page<VipConsSortAnalyseBean>
	 */
	public Page<VipConsSortAnalyseBean> findVipConsLoadSort(PSysUser pSysUser, String startDate,
			String endDate, int flag, long start, int limit) throws DBAccessException{
		StringBuffer sql = new StringBuffer();
		if(1 == flag){
			sql.append("SELECT STAT.ORG_NO,STAT.CONS_NO,ORG.ORG_NAME,CONS.CONS_NAME,STAT.MAX_P, STAT.MAX_P_TIME AS MAX_TIME,\n")
				.append("ROW_NUMBER() OVER ( ORDER BY STAT.MAX_P DESC) AS SORT\n")
				.append("FROM A_CONS_STAT_D STAT, C_CONS_VIP VIP, C_CONS CONS, O_ORG ORG\n")
				.append("WHERE STAT.CONS_NO = VIP.CONS_NO\n")
				.append("AND STAT.CONS_NO = CONS.CONS_NO\n")
				.append("AND STAT.ORG_NO = ORG.ORG_NO\n")
				.append("AND STAT.STAT_DATE >= TO_DATE(?,'yyyy-MM-dd')\n")
				.append("AND STAT.STAT_DATE < (TO_DATE(?,'yyyy-MM-dd')+1)\n")
				.append("AND STAT.MAX_P IS NOT NULL\n")
			    .append("ORDER BY STAT.MAX_P DESC");
			logger.debug(sql);
			try{
				return super.pagingFind(sql.toString(), start, limit, new VipConsLoadMaxSortRowMapper(),
						new Object[]{startDate,endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据时间查询重点用户最高负荷排名分析信息出错！");
				throw e;
			}
		}else if(2 == flag){
			sql.append("SELECT STAT.ORG_NO,STAT.CONS_NO,ORG.ORG_NAME,CONS.CONS_NAME,STAT.MIN_P, STAT.MIN_P_TIME AS MIN_TIME,\n")
				.append("ROW_NUMBER() OVER ( ORDER BY STAT.MIN_P DESC) AS SORT\n")
				.append("FROM A_CONS_STAT_D STAT, C_CONS_VIP VIP, C_CONS CONS, O_ORG ORG\n")
				.append("WHERE STAT.CONS_NO = VIP.CONS_NO\n")
				.append("AND STAT.CONS_NO = CONS.CONS_NO\n")
				.append("AND STAT.ORG_NO = ORG.ORG_NO\n")
				.append("AND STAT.STAT_DATE >= TO_DATE(?,'yyyy-MM-dd')\n")
				.append("AND STAT.STAT_DATE < (TO_DATE(?,'yyyy-MM-dd')+1)\n")
				.append("AND STAT.MIN_P IS NOT NULL\n")
			    .append("ORDER BY STAT.MIN_P DESC");
			logger.debug(sql);
			try{
				return super.pagingFind(sql.toString(), start, limit, new VipConsLoadMinSortRowMapper(),
						new Object[]{startDate,endDate});
			}catch(RuntimeException e){
				this.logger.debug("根据时间查询重点用户最低负荷排名分析信息出错！");
				throw e;
			}
		}else{
			return new Page<VipConsSortAnalyseBean>();
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户最大电能量排名分析
	 */
	class VipConsPowerMaxSortRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsSortAnalyseBean bean = new VipConsSortAnalyseBean();
			try {
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setSort(rs.getString("SORT"));
				bean.setMaxPapE(rs.getString("PAP_E"));
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				VipConsSortAnalyseJdbcDaoImpl.this.logger.error("取 VipConsPowerMaxSortRowMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户最小电能量排名分析
	 */
	class VipConsPowerMinSortRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsSortAnalyseBean bean = new VipConsSortAnalyseBean();
			try {
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setSort(rs.getString("SORT"));
				bean.setMinPapE(rs.getString("PAP_E"));
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				VipConsSortAnalyseJdbcDaoImpl.this.logger.error("取 VipConsPowerMinSortRowMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户最大电能量排名分析
	 */
	class VipConsLoadMaxSortRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsSortAnalyseBean bean = new VipConsSortAnalyseBean();
			try {
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setSort(rs.getString("SORT"));
				bean.setMaxP(rs.getString("PAP_E"));
				bean.setMaxPTime(timeStampToStr(rs.getTimestamp("MAX_TIME")));
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				VipConsSortAnalyseJdbcDaoImpl.this.logger.error("取 VipConsLoadMaxSortRowMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
	/**
	 * 自定义查询返回的值对象，用于查询重点用户最小电能量排名分析
	 */
	class VipConsLoadMinSortRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			VipConsSortAnalyseBean bean = new VipConsSortAnalyseBean();
			try {
				bean.setOrgNo(rs.getString("ORG_NO"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setSort(rs.getString("SORT"));
				bean.setMinP(rs.getString("PAP_E"));
				bean.setMinPTime(timeStampToStr(rs.getTimestamp("MIN_TIME")));
				return bean;
			} catch (Exception e) {
				e.printStackTrace();
				VipConsSortAnalyseJdbcDaoImpl.this.logger.error("取 VipConsLoadMinSortRowMapper 时出现错误！ ");
				return null;
			}
		}
	}
	
    /**
	 * timeStamp转化为String
	 * @param date
	 * @return String 
	 */
    private String timeStampToStr(Timestamp time){
    	return DateUtil.timeStampToStr(time);
    }  
}
