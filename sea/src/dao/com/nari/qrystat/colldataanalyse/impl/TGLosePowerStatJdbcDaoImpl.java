package com.nari.qrystat.colldataanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LosePowerStatDto;
import com.nari.qrystat.colldataanalyse.TGLosePowerStatJdbcDao;
import com.nari.support.Page;
import com.nari.util.NodeTypeUtils;

/**
 * 台区用电损耗统计Jdbc之Dao实现类
 * @author 姜炜超
 */
public class TGLosePowerStatJdbcDaoImpl extends JdbcBaseDAOImpl implements TGLosePowerStatJdbcDao {

	//定义日志
	private static final Logger logger = Logger.getLogger(TGLosePowerStatJdbcDaoImpl.class);
	
	/**
	 * 根据条件查询时间段内台区用电损耗统计信息
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param statFlag 1表示月，2表示季度，3表示年
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatDto>
	 */
	public Page<LosePowerStatDto> findLosePowerStat(String nodeId, String nodeType, String orgType, 
			String statFlag, Integer startDate, Integer endDate,long start, int limit, PSysUser pSysUser){
		
		Page<LosePowerStatDto> psc = null;
		String sql = "";
		
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType) 
					|| NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
			sql = 
				"SELECT TG.TG_ID,TG.TG_NO,\n" +
				"       ORG.ORG_NAME,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       TG.TG_NAME,\n" + 
				"       DECODE(TG.PUB_PRIV_FLAG,'0','专变','1','公变','') AS PUB_PRIV_FLAG,\n" + 
				"       TG.STAT_DATE,\n" + 
				"       TG.READ_PPQ,\n" + 
				"       TG.SETTLE_SPQ,\n" + 
				"       TG.T_PPQ,\n" + 
				"       TG.LPQ,\n" + 
				"       TG.L_LLR,\n" + 
				"       TG.LL_IDX\n"+
				"  FROM A_TG_LL_DET    TG,\n" + 
				"       G_LINE_TG_RELA RELA,\n" + 
				"       G_LINE         LINE,\n" + 
				"       O_ORG          ORG\n" + 
				" WHERE TG.TG_ID = RELA.TG_ID(+)\n" + 
				"   AND RELA.LINE_ID = LINE.LINE_ID(+)\n" + 
				"   AND TG.ORG_NO = ORG.ORG_NO\n" +  
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND TG.STAT_CYCLE = ?\n" + 
				"   AND TG.STAT_DATE >= ?\n" + 
				"   AND TG.STAT_DATE <= ?\n" + 
				"   AND TG.ORG_NO LIKE ?";
				nodeId = nodeId+"%";
			}else if(NodeTypeUtils.NODETYPE_ORG05.equals(orgType) || NodeTypeUtils.NODETYPE_ORG06.equals(orgType)){
				sql = 
					"SELECT TG.TG_ID,TG.TG_NO,\n" +
					"       ORG.ORG_NAME,\n" + 
					"       LINE.LINE_NAME,\n" + 
					"       TG.TG_NAME,\n" + 
					"       DECODE(TG.PUB_PRIV_FLAG,'0','专变','1','公变','') AS PUB_PRIV_FLAG,\n" + 
					"       TG.STAT_DATE,\n" + 
					"       TG.READ_PPQ,\n" + 
					"       TG.SETTLE_SPQ,\n" + 
					"       TG.T_PPQ,\n" + 
					"       TG.LPQ,\n" + 
					"       TG.L_LLR,\n" + 
					"       TG.LL_IDX\n"+
					"  FROM A_TG_LL_DET    TG,\n" + 
					"       G_LINE_TG_RELA RELA,\n" + 
					"       G_LINE         LINE,\n" + 
					"       O_ORG          ORG,\n" + 
					"       G_TG           GTG\n" + 
					" WHERE TG.TG_ID = RELA.TG_ID(+)\n" + 
					"   AND RELA.LINE_ID = LINE.LINE_ID(+)\n" + 
					"   AND TG.ORG_NO = ORG.ORG_NO\n" + 
					"   AND GTG.TG_ID = TG.TG_ID\n" + 
					"   AND RELA.RELA_FLAG = 1\n" +
					"   AND TG.STAT_CYCLE = ?\n" + 
					"   AND TG.STAT_DATE >= ?\n" + 
					"   AND TG.STAT_DATE <= ?\n" + 
					"   AND GTG.ORG_NO = ?";
			}else{
				//
			}
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql = 
				"SELECT TG.TG_ID,TG.TG_NO,\n" +
				"       ORG.ORG_NAME,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       TG.TG_NAME,\n" + 
				"       DECODE(TG.PUB_PRIV_FLAG,'0','专变','1','公变','') AS PUB_PRIV_FLAG,\n" + 
				"       TG.STAT_DATE,\n" + 
				"       TG.READ_PPQ,\n" + 
				"       TG.SETTLE_SPQ,\n" + 
				"       TG.T_PPQ,\n" + 
				"       TG.LPQ,\n" + 
				"       TG.L_LLR,\n" + 
				"       TG.LL_IDX\n"+
				"  FROM A_TG_LL_DET    TG,\n" + 
				"       G_LINE_TG_RELA RELA,\n" + 
				"       G_LINE         LINE,\n" + 
				"       O_ORG          ORG\n" + 
				" WHERE TG.TG_ID = RELA.TG_ID(+)\n" + 
				"   AND RELA.LINE_ID = LINE.LINE_ID(+)\n" + 
				"   AND TG.ORG_NO = ORG.ORG_NO\n" +
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND TG.STAT_CYCLE = ?\n" + 
				"   AND TG.STAT_DATE >= ?\n" + 
				"   AND TG.STAT_DATE <= ?\n" +  
				"   AND LINE.LINE_ID = ?";
		}else if(NodeTypeUtils.NODETYPE_CGP.equals(nodeType)){
			sql = 
				"SELECT TG.TG_ID,TG.TG_NO,\n" +
				"       ORG.ORG_NAME,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       TG.TG_NAME,\n" + 
				"       DECODE(TG.PUB_PRIV_FLAG,'0','专变','1','公变','') AS PUB_PRIV_FLAG,\n" + 
				"       TG.STAT_DATE,\n" + 
				"       TG.READ_PPQ,\n" + 
				"       TG.SETTLE_SPQ,\n" + 
				"       TG.T_PPQ,\n" + 
				"       TG.LPQ,\n" + 
				"       TG.L_LLR,\n" + 
				"       TG.LL_IDX\n"+
				"  FROM A_TG_LL_DET         TG,\n" + 
				"       G_LINE_TG_RELA      RELA,\n" + 
				"       G_LINE              LINE,\n" + 
				"       O_ORG               ORG,\n" + 
				"       C_CONS              CONS,\n" + 
				"       T_TMNL_GROUP_DETAIL CGP\n" + 
				" WHERE TG.TG_ID = RELA.TG_ID(+)\n" + 
				"   AND RELA.LINE_ID = LINE.LINE_ID(+)\n" + 
				"   AND TG.ORG_NO = ORG.ORG_NO\n" + 
				"   AND TG.TG_ID = CONS.TG_ID\n" + 
				"   AND CGP.CONS_NO = CONS.CONS_NO\n" + 
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND TG.STAT_CYCLE = ?\n" + 
				"   AND TG.STAT_DATE >= ?\n" + 
				"   AND TG.STAT_DATE <= ?\n" + 
				"   AND CGP.GROUP_NO = ?";
		}else if(NodeTypeUtils.NODETYPE_UGP.equals(nodeType)){
			sql = 
				"SELECT TG.TG_ID,TG.TG_NO,\n" +
				"       ORG.ORG_NAME,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       TG.TG_NAME,\n" + 
				"       DECODE(TG.PUB_PRIV_FLAG,'0','专变','1','公变','') AS PUB_PRIV_FLAG,\n" + 
				"       TG.STAT_DATE,\n" + 
				"       TG.READ_PPQ,\n" + 
				"       TG.SETTLE_SPQ,\n" + 
				"       TG.T_PPQ,\n" + 
				"       TG.LPQ,\n" + 
				"       TG.L_LLR,\n" + 
				"       TG.LL_IDX\n"+
				"  FROM A_TG_LL_DET         TG,\n" + 
				"       G_LINE_TG_RELA      RELA,\n" + 
				"       G_LINE              LINE,\n" + 
				"       O_ORG               ORG,\n" + 
				"       C_CONS              CONS,\n" + 
				"       R_USER_GROUP_DETAIL UGP\n" + 
				" WHERE TG.TG_ID = RELA.TG_ID(+)\n" + 
				"   AND RELA.LINE_ID = LINE.LINE_ID(+)\n" + 
				"   AND TG.ORG_NO = ORG.ORG_NO\n" + 
				"   AND TG.TG_ID = CONS.TG_ID\n" + 
				"   AND UGP.CONS_NO = CONS.CONS_NO\n" + 
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND TG.STAT_CYCLE = ?\n" + 
				"   AND TG.STAT_DATE >= ?\n" + 
				"   AND TG.STAT_DATE <= ?\n" + 
				"   AND UGP.GROUP_NO = ?";
		}else if(NodeTypeUtils.NODETYPE_TG.equals(nodeType)){
			sql = 
				"SELECT TG.TG_ID,TG.TG_NO,\n" +
				"       ORG.ORG_NAME,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       TG.TG_NAME,\n" + 
				"       DECODE(TG.PUB_PRIV_FLAG,'0','专变','1','公变','') AS PUB_PRIV_FLAG,\n" +  
				"       TG.STAT_DATE,\n" + 
				"       TG.READ_PPQ,\n" + 
				"       TG.SETTLE_SPQ,\n" + 
				"       TG.T_PPQ,\n" + 
				"       TG.LPQ,\n" + 
				"       TG.L_LLR,\n" + 
				"       TG.LL_IDX\n"+
				"  FROM A_TG_LL_DET    TG,\n" + 
				"       G_LINE_TG_RELA RELA,\n" + 
				"       G_LINE         LINE,\n" + 
				"       O_ORG          ORG\n" +
				" WHERE TG.TG_ID = RELA.TG_ID(+)\n" + 
				"   AND RELA.LINE_ID = LINE.LINE_ID(+)\n" + 
				"   AND TG.ORG_NO = ORG.ORG_NO\n" + 
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND TG.STAT_CYCLE = ?\n" + 
				"   AND TG.STAT_DATE >= ?\n" + 
				"   AND TG.STAT_DATE <= ?\n" + 
				"   AND TG.TG_ID = ?";
		}else{
			//
		}
        logger.debug(sql);
		try{
		    psc = this.pagingFind(sql, start, limit, new TGLosePowerStatRowMapper(), new Object[]{statFlag,startDate,endDate,nodeId});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询台区用电损耗统计信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内台区用电损耗统计信息
	 */
	class TGLosePowerStatRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LosePowerStatDto dto = new LosePowerStatDto();
			try {
				dto.setTgId(rs.getString("TG_ID"));
				dto.setTgNo(rs.getString("TG_NO"));
				dto.setTgName(rs.getString("TG_NAME"));
				dto.setOrgName(rs.getString("ORG_NAME"));
				dto.setLineName(rs.getString("LINE_NAME"));
				dto.setPubPrivFlag(rs.getString("PUB_PRIV_FLAG"));
				dto.setDate(rs.getString("STAT_DATE"));
				dto.setReadPpq((null != rs.getString("READ_PPQ"))? rs.getDouble("READ_PPQ") : null);
				dto.setSettleSpq((null != rs.getString("SETTLE_SPQ"))? rs.getDouble("SETTLE_SPQ") : null);
				dto.setPpq((null != rs.getString("T_PPQ"))? rs.getDouble("T_PPQ") : null);
				dto.setLpq((null != rs.getString("LPQ"))? rs.getDouble("LPQ") : null);
				dto.setIdx((null != rs.getString("LL_IDX"))? rs.getDouble("LL_IDX") : null);
				dto.setLlr((null != rs.getString("L_LLR"))? rs.getDouble("L_LLR") : null);
				return dto;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
}
