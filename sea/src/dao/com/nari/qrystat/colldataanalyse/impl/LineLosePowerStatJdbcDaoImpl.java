package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LineLosePowerBean;
import com.nari.qrystat.colldataanalyse.LineLosePowerStatJdbcDao;
import com.nari.support.Page;
import com.nari.util.NodeTypeUtils;

/**
 * 线路用电损耗统计Jdbc之Dao实现类
 * @author 姜炜超
 */
public class LineLosePowerStatJdbcDaoImpl extends JdbcBaseDAOImpl implements LineLosePowerStatJdbcDao{
	//定义日志
	private static final Logger logger = Logger.getLogger(LineLosePowerStatJdbcDaoImpl.class);
	
	/**
	 * 根据条件查询时间段内线路用电损耗统计信息
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param statFlag 02表示月，03表示季度，04表示年
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerBean>
	 */
	public Page<LineLosePowerBean> findLineLosePowerStat(String nodeId, String nodeType, String orgType, 
			String statFlag, Integer startDate, Integer endDate,long start, int limit, PSysUser pSysUser){
		Page<LineLosePowerBean> psc = null;
		String sql = "";
		
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			sql = 
				"SELECT LLR.LINE_ID,\n" +
				"       LLR.LINE_NO,\n" + 
				"       LLR.LINE_NAME,\n" + 
				"       ORG.ORG_NAME,\n" + 
				"       LLR.STAT_DATE,\n" + 
				"       LLR.READ_PPQ,\n" + 
				"       LLR.SETTLE_SPQ,\n" + 
				"       LLR.T_PPQ,\n" + 
				"       LLR.LPQ,\n" + 
				"       LLR.L_LLR,\n" + 
				"       LLR.LL_IDX\n" + 
				"  FROM A_LLR_CMPL LLR, O_ORG ORG\n" + 
				" WHERE LLR.ORG_NO = ORG.ORG_NO\n" + 
				"   AND LLR.STAT_CYCLE = ?\n" + 
				"   AND LLR.STAT_DATE >= ?\n" + 
				"   AND LLR.STAT_DATE <= ?\n" + 
				"   AND LLR.ORG_NO LIKE ?";
				nodeId = nodeId+"%";
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql = 
				"SELECT LLR.LINE_ID,\n" +
				"       LLR.LINE_NO,\n" + 
				"       LLR.LINE_NAME,\n" + 
				"       ORG.ORG_NAME,\n" + 
				"       LLR.STAT_DATE,\n" + 
				"       LLR.READ_PPQ,\n" + 
				"       LLR.SETTLE_SPQ,\n" + 
				"       LLR.T_PPQ,\n" + 
				"       LLR.LPQ,\n" + 
				"       LLR.L_LLR,\n" + 
				"       LLR.LL_IDX\n" + 
				"  FROM A_LLR_CMPL LLR, O_ORG ORG\n" + 
				" WHERE LLR.ORG_NO = ORG.ORG_NO\n" + 
				"   AND LLR.STAT_CYCLE = ?\n" + 
				"   AND LLR.STAT_DATE >= ?\n" + 
				"   AND LLR.STAT_DATE <= ?\n" + 
				"   AND LLR.LINE_ID = ?";
		}else if(NodeTypeUtils.NODETYPE_SUB.equals(nodeType)){
			sql = 
				"SELECT LLR.LINE_ID,\n" +
				"       LLR.LINE_NO,\n" + 
				"       LLR.LINE_NAME,\n" + 
				"       ORG.ORG_NAME,\n" + 
				"       LLR.STAT_DATE,\n" + 
				"       LLR.READ_PPQ,\n" + 
				"       LLR.SETTLE_SPQ,\n" + 
				"       LLR.T_PPQ,\n" + 
				"       LLR.LPQ,\n" + 
				"       LLR.L_LLR,\n" + 
				"       LLR.LL_IDX\n" + 
				"  FROM A_LLR_CMPL LLR, O_ORG ORG, G_SUBS_LINE_RELA RELA\n" + 
				" WHERE LLR.ORG_NO = ORG.ORG_NO\n" + 
				"   AND LLR.LINE_ID = RELA.LINE_ID\n" + 
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND LLR.STAT_CYCLE = ?\n" + 
				"   AND LLR.STAT_DATE >= ?\n" + 
				"   AND LLR.STAT_DATE <= ?\n" + 
				"   AND RELA.SUBS_ID = ?";
		}else{
			//
		}
        logger.debug(sql);
		try{
		    psc = this.pagingFind(sql, start, limit, new LineLosePowerStatRowMapper(), new Object[]{statFlag,startDate,endDate,nodeId});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询线路用电损耗统计信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内线路用电损耗统计信息
	 */
	class LineLosePowerStatRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LineLosePowerBean bean = new LineLosePowerBean();
			try {
				bean.setLineId(rs.getString("LINE_ID"));
				bean.setLineNo(rs.getString("LINE_NO"));
				bean.setLineName(rs.getString("LINE_NAME"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setDate(rs.getString("STAT_DATE"));
				bean.setReadPpq((null != rs.getString("READ_PPQ"))? rs.getDouble("READ_PPQ") : null);
				bean.setSettleSpq((null != rs.getString("SETTLE_SPQ"))? rs.getDouble("SETTLE_SPQ") : null);
				bean.setSupplypq((null != rs.getString("T_PPQ"))? rs.getDouble("T_PPQ") : null);
				bean.setLpq((null != rs.getString("LPQ"))? rs.getDouble("LPQ") : null);
				bean.setIdx((null != rs.getString("LL_IDX"))? rs.getDouble("LL_IDX") : null);
				bean.setLlr((null != rs.getString("L_LLR"))? rs.getDouble("L_LLR") : null);
				return bean;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
}
