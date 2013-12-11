package com.nari.qrystat.colldataanalyse.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nari.baseapp.datagathorman.SqlData;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.baseapp.datagathorman.IDoDao;
import com.nari.baseapp.datagathorman.impl.DaoUtils;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LosePowerStatDto;
import com.nari.qrystat.colldataanalyse.LosePowerStatJdbcDao;
import com.nari.qrystat.colldataanalyse.LosePowerStatReadBean;
import com.nari.support.Page;
import com.nari.util.AutoLang;
import com.nari.util.DateUtil;
import com.nari.util.NodeTypeUtils;

/**
 * 台区用电损耗查询Jdbc之Dao实现类
 * @author 姜炜超
 */
public class LosePowerStatJdbcDaoImpl extends JdbcBaseDAOImpl implements LosePowerStatJdbcDao{
	//定义日志
	private static final Logger logger = Logger.getLogger(LosePowerStatJdbcDaoImpl.class);
	
	/**
	 * 根据条件查询时间段内台区用电损耗查询信息
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatDto>
	 */
	public Page<LosePowerStatDto> findLPSInfoByCond(String nodeId, String nodeType, String orgType, Integer startDate, 
			Integer endDate,long start, int limit, PSysUser pSysUser){
		
		Page<LosePowerStatDto> psc = null;
		String sql = "";
		
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			sql = 
				"SELECT ATP.TG_ID,\n" +
				"       ATP.LL_IDX_VALUE AS LL_IDX,\n" + 
				"       ATP.PPQ AS T_PPQ,\n" + 
				"       ATP.TG_SPQ AS T_SPQ,\n" + 
				"       TG.TG_NO,\n" + 
				"       TG.TG_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,ATP.STAT_DATE,ATP.READ_SUCC_RATE\n" + 
				"  FROM A_TG_PQ ATP, G_TG TG, O_ORG ORG\n" + 
				" WHERE ATP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ATP.TG_ID = TG.TG_ID\n" + 
				"   AND ATP.STAT_DATE >= ?\n" + 
				"   AND ATP.STAT_DATE < ?\n"; 
			if(NodeTypeUtils.NODETYPE_ORG03.equals(orgType) || NodeTypeUtils.NODETYPE_ORG04.equals(orgType)){
				sql = sql + "   AND ATP.ORG_NO LIKE ?";
				nodeId = nodeId+"%";
			}else if(NodeTypeUtils.NODETYPE_ORG05.equals(orgType) || NodeTypeUtils.NODETYPE_ORG06.equals(orgType)){
				sql = sql + " AND TG.ORG_NO = ?";
			}else{
				//
			}
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql = 
				"SELECT ATP.TG_ID,\n" +
				"       ATP.LL_IDX_VALUE AS LL_IDX,\n" + 
				"       ATP.PPQ AS T_PPQ,\n" + 
				"       ATP.TG_SPQ AS T_SPQ,\n" + 
				"       TG.TG_NO,\n" + 
				"       TG.TG_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,ATP.STAT_DATE,ATP.READ_SUCC_RATE\n" + 
				"  FROM A_TG_PQ ATP, G_TG TG, O_ORG ORG, G_LINE_TG_RELA RELA\n" + 
				" WHERE ATP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ATP.TG_ID = TG.TG_ID\n" + 
				"   AND ATP.TG_ID = RELA.TG_ID\n" + 
				"   AND RELA.RELA_FLAG = 1\n" +
				"   AND ATP.STAT_DATE >= ?\n" + 
				"   AND ATP.STAT_DATE < ?\n"+
				"   AND RELA.LINE_ID = ?";
		}else if(NodeTypeUtils.NODETYPE_CGP.equals(nodeType)){
			sql = 
				"SELECT ATP.TG_ID,\n" +
				"       ATP.LL_IDX_VALUE AS LL_IDX,\n" + 
				"       ATP.PPQ AS T_PPQ,\n" + 
				"       ATP.TG_SPQ AS T_SPQ,\n" + 
				"       TG.TG_NO,\n" + 
				"       TG.TG_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,ATP.STAT_DATE,ATP.READ_SUCC_RATE\n" + 
				"  FROM A_TG_PQ             ATP,\n" + 
				"       G_TG                TG,\n" + 
				"       O_ORG               ORG,\n" +  
				"       C_CONS              CONS,\n" + 
				"       T_TMNL_GROUP_DETAIL CGP\n" + 
				" WHERE ATP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ATP.TG_ID = TG.TG_ID\n" + 
				"   AND ATP.TG_ID = CONS.TG_ID\n" + 
				"   AND CONS.CONS_NO = CGP.CONS_NO\n" + 
				"   AND ATP.STAT_DATE >= ?\n" + 
				"   AND ATP.STAT_DATE < ?\n"+
				"   AND CGP.GROUP_NO = ?";
		}else if(NodeTypeUtils.NODETYPE_UGP.equals(nodeType)){
			sql = 
				"SELECT ATP.TG_ID,\n" +
				"       ATP.LL_IDX_VALUE AS LL_IDX,\n" + 
				"       ATP.PPQ AS T_PPQ,\n" + 
				"       ATP.TG_SPQ AS T_SPQ,\n" + 
				"       TG.TG_NO,\n" + 
				"       TG.TG_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,ATP.STAT_DATE,ATP.READ_SUCC_RATE\n" + 
				"  FROM A_TG_PQ             ATP,\n" + 
				"       G_TG                TG,\n" + 
				"       O_ORG               ORG,\n" + 
				"       C_CONS              CONS,\n" + 
				"       R_USER_GROUP_DETAIL UGP\n" + 
				" WHERE ATP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ATP.TG_ID = TG.TG_ID\n" + 
				"   AND ATP.TG_ID = CONS.TG_ID\n" + 
				"   AND CONS.CONS_NO = UGP.CONS_NO\n" + 
				"   AND ATP.STAT_DATE >= ?\n" + 
				"   AND ATP.STAT_DATE < ?\n"+ 
				"   AND UGP.GROUP_NO = ?";
		}else if(NodeTypeUtils.NODETYPE_TG.equals(nodeType)){
			sql = 
				"SELECT ATP.TG_ID,\n" +
				"       ATP.LL_IDX_VALUE AS LL_IDX,\n" + 
				"       ATP.PPQ AS T_PPQ,\n" + 
				"       ATP.TG_SPQ AS T_SPQ,\n" + 
				"       TG.TG_NO,\n" + 
				"       TG.TG_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,ATP.STAT_DATE,ATP.READ_SUCC_RATE\n" + 
				"  FROM A_TG_PQ ATP, G_TG TG, O_ORG ORG\n" + 
				" WHERE ATP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ATP.TG_ID = TG.TG_ID\n" + 
				"   AND ATP.STAT_DATE >= ?\n" + 
				"   AND ATP.STAT_DATE < ?\n"+
				"   AND ATP.TG_ID = ?";
		}else{
			//
		}

		try{
		    psc = this.pagingFind(sql, start, limit, new LosePowerStatRowMapper(), new Object[]{startDate,endDate,nodeId});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询台区用电损耗查询信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 根据条件查询时间段内台区用户抄表成功和失败信息      1	营销标准代码：代码	内容	01	线路类	02	台区类	03	电压等级类	04	供电单位类
	 * @param tgId 台区id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @Param succFalg *表示全部，1表示成功，0表示失败
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatReadBean>
	 */
	public Page<LosePowerStatReadBean> findLPSReadSuccInfo(String tgId, String startDate, String ioValue,String succFalg,
			String endDate,long start, int limit, PSysUser pSysUser){
		Page<LosePowerStatReadBean> psc = null;	
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		sql.append("  SELECT EP.CONS_NO,CONS.CONS_NAME, METER.ASSET_NO,\n");
		sql.append("  EP.TMNL_ASSET_NO, MP.MP_NO, MP.MP_NAME, EP.STAT_DATE,\n");
		sql.append("   EP.PAP_R_S, EP.PAP_R_E, EP.PAP_E, EP.CT * EP.PT T_FACTOR\n");
		sql.append("  FROM G_IO_MP          IO,\n");
		sql.append("  G_CHKUNIT        CU,\n");
		sql.append("  C_METER          METER,\n");
		sql.append("  C_CONS           CONS,\n");
		sql.append("  C_MP             MP,\n");
		sql.append("  A_CALC_DAY_POWER EP\n");
		sql.append("  WHERE IO.METER_ID = METER.METER_ID\n");
		sql.append("  AND EP.ASSET_NO = METER.ASSET_NO\n");
		sql.append("  AND METER.MP_ID = MP.MP_ID\n");
		sql.append("  AND EP.CONS_NO = CONS.CONS_NO\n");
		sql.append("  AND IO.CHKUNIT_ID = CU.CHKUNIT_ID\n");
		sql.append("   AND CU.CHKUNIT_TYPE_CODE = '02'\n");
		if(!"*".equals(ioValue)){//入口出口全部选中
			if("0".equals(ioValue)){//出口
				sql.append("  AND IO.IS_IN = 0\n");
			}else{//入口
				sql.append("   AND IO.IS_IN = 1\n");
			}
		}
		sql.append("  AND IO.OBJ_ID = ?\n");
		parms.add(Long.valueOf(tgId));
		sql.append("  AND EP.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n");
		sql.append("  AND EP.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd') + 1\n");
		parms.add(startDate);
		parms.add(endDate);
		if(!"*".equals(succFalg)){//抄表标识 全部选中
			if("0".equals(succFalg)){//失败
				sql.append("  AND EP.PAP_E IS  NULL \n");
			}else if("1".equals(succFalg)){//成功
				sql.append("  AND EP.PAP_E IS NOT NULL \n");
			}
		}
		sql.append("ORDER BY EP.CT * EP.PT DESC  \n");
		logger.debug(sql.toString());
		try{
		    psc = this.pagingFind(sql.toString(), start, limit, new LPSSuccReadRowMapper(), parms.toArray());
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内台区用户抄表成功、失败信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 根据条件查询时间段内台区用户抄表失败信息
	 * @param tgId 台区id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LosePowerStatReadBean>
	 */
	public Page<LosePowerStatReadBean> findLPSReadFailInfo(String tgId, String startDate, String ioValue,
			String endDate,long start, int limit, PSysUser pSysUser){
		Page<LosePowerStatReadBean> psc = null;
		String sql = 
			"SELECT FAIL.CONS_NO,\n" +
			"       CONS.CONS_NAME,METER.ASSET_NO,FAIL.TMNL_ASSET_NO,\n" + 
			"       MP.MP_NO,\n" + 
			"       MP.MP_NAME,\n" + 
			"       FAIL.STAT_DATE,POWER.PAP_R_S, POWER.PAP_R_E, POWER.PAP_E\n" + 
			"  FROM A_READ_FAIL_LIST FAIL,\n" + 
			"       G_IO_MP          IO,\n" + 
			"       G_CHKUNIT        CU,\n" +
			"       C_METER          METER,\n" + 
			"       C_CONS           CONS,\n" + 
			"       C_MP          MP,\n" + 
			"       A_CALC_DAY_POWER POWER\n" + 
			" WHERE IO.METER_ID = METER.METER_ID\n" + 
			"   AND FAIL.ASSET_NO = METER.ASSET_NO\n" + 
			"   AND METER.MP_ID = MP.MP_ID\n" + 
			"   AND FAIL.CONS_NO = CONS.CONS_NO\n" + 
			"   AND IO.CHKUNIT_ID = CU.CHKUNIT_ID\n" +
			"   AND FAIL.CONS_NO = POWER.CONS_NO(+)\n" + 
			"   AND FAIL.STAT_DATE = POWER.STAT_DATE(+) AND POWER.PAP_E IS NULL\n" +
			"   AND CU.CHKUNIT_TYPE_CODE = '02'\n" +
			"   AND IO.OBJ_ID = ?\n" ;
		if(!"*".equals(ioValue)){//入口出口全部选中
			if("0".equals(ioValue)){//出口
				sql = sql + 
					"   AND IO.IS_IN = 0\n" ;
			}else{//入口
				sql = sql + 
					"   AND IO.IS_IN = 1\n" ;
			}
		}
		sql = sql +  
			"   AND FAIL.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n" + 
			"   AND FAIL.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd') + 1";
		try{
		    psc = this.pagingFind(sql, start, limit, new LPSFailReadRowMapper(), new Object[]{Long.valueOf(tgId),startDate,endDate});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内台区用户抄表失败信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 根据条件查询时间段内台区用户漏点信息，界面全选调用
	 * @param tgId 台区id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @return List<GatherByHandDto>
	 */
	@SuppressWarnings("unchecked")
	public List<GatherByHandDto> findTmnlMissingInfoByAll(String tgId, String startDate, 
			String ioValue,String endDate){
		List<GatherByHandDto> list = null;
		String sql = 
			"SELECT MARK.ID,\n" +
			"           MARK.ORG_NO,\n" + 
			"           MARK.TMNL_ASSET_NO,\n" + 
			"           MARK.DATA_SRC,\n" + 
			"           MARK.MP_SN,\n" + 
			"           MARK.IS_DENIZEN,\n" + 
			"           MARK.TEMPLATE_ID,\n" + 
			"           MARK.STAT_DATE,\n" + 
			"           MARK.APPLY_CNT,\n" + 
			"           MARK.FIRST_COLL_CNT,\n" + 
			"           MARK.MISSING_CNT,\n" + 
			"           MARK.POWER_CUT_CNT,\n" + 
			"           MARK.RECALL_CNT,\n" + 
			"           MARK.DATA_GROUP,\n" + 
			"           MARK.PROT_NO_LIST,\n" + 
			"           MARK.DENIZEN_MP\n" + 
			"      FROM R_TMNL_MISSING_MARK MARK,\n" + 
			"           (SELECT DISTINCT FAIL.TMNL_ASSET_NO\n" + 
			"              FROM A_READ_FAIL_LIST FAIL,\n" + 
			"                   G_IO_MP          IO,\n" + 
			"                   G_CHKUNIT        CU,\n" +
			"                   C_METER          METER,\n" + 
			"                   C_CONS           CONS,\n" + 
			"                   C_MP             MP\n" + 
			"             WHERE IO.METER_ID = METER.METER_ID\n" + 
			"               AND FAIL.ASSET_NO = METER.ASSET_NO\n" + 
			"               AND METER.MP_ID = MP.MP_ID\n" + 
			"               AND FAIL.CONS_NO = CONS.CONS_NO\n" + 
			"               AND IO.CHKUNIT_ID = CU.CHKUNIT_ID\n" +
			"               AND CU.CHKUNIT_TYPE_CODE = '02'\n" +
			"               AND IO.OBJ_ID = ? \n";
		
		if(!"*".equals(ioValue)){//入口出口全部选中
			if("0".equals(ioValue)){//出口
				sql = sql + 
					"   AND IO.IS_IN = 0\n" ;
			}else{//入口
				sql = sql + 
					"   AND IO.IS_IN = 1\n" ;
			}
		}
		sql = sql +  
			"AND FAIL.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n" +
			"               AND FAIL.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd') + 1) TMP\n" + 
			"     WHERE MARK.TMNL_ASSET_NO = TMP.TMNL_ASSET_NO\n" + 
			"       AND MARK.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n" + 
			"       AND MARK.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd') + 1";
		logger.info(sql);
		try{
			list = super.getJdbcTemplate().query(sql, new Object[]{tgId, startDate, endDate, startDate, endDate} , 
					new TmnlMissingInfoRowMapper());
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内台区用户漏点信息出错！");
		    throw e;
	    }
	    if(null == list){
	    	list = new ArrayList<GatherByHandDto>();
	    }
	    return list;
	}
	
	/**
	 * 根据条件查询时间段内台区用户漏点信息，非全选，仅仅对选中用户进行操作
	 * @param tmnlAssetNoList 终端资产编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List<GatherByHandDto>
	 */
	@SuppressWarnings("unchecked")
	public List<GatherByHandDto> findTmnlMissingInfoByANList(List<String> tmnlAssetNoList, String startDate, String endDate){
		List<GatherByHandDto> list = null;
		String sql = 
			"SELECT MARK.ID,\n" +
			"           MARK.ORG_NO,\n" + 
			"           MARK.TMNL_ASSET_NO,\n" + 
			"           MARK.DATA_SRC,\n" + 
			"           MARK.MP_SN,\n" + 
			"           MARK.IS_DENIZEN,\n" + 
			"           MARK.TEMPLATE_ID,\n" + 
			"           MARK.STAT_DATE,\n" + 
			"           MARK.APPLY_CNT,\n" + 
			"           MARK.FIRST_COLL_CNT,\n" + 
			"           MARK.MISSING_CNT,\n" + 
			"           MARK.POWER_CUT_CNT,\n" + 
			"           MARK.RECALL_CNT,\n" + 
			"           MARK.DATA_GROUP,\n" + 
			"           MARK.PROT_NO_LIST,\n" + 
			"           MARK.DENIZEN_MP\n" + 
			"      FROM R_TMNL_MISSING_MARK MARK \n"+
			"      WHERE MARK.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n"+
			"        AND MARK.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd') + 1\n"+
			"        AND ";
		StringBuilder sb = new StringBuilder(sql);
		HashSet s = new HashSet(tmnlAssetNoList);
		tmnlAssetNoList =  new ArrayList(s);
		LinkedList ls=new LinkedList(tmnlAssetNoList);
		ls.addFirst(endDate);
		ls.addFirst(startDate);
		sb.append(AutoLang.autoIn("MARK.TMNL_ASSET_NO",tmnlAssetNoList, 500));
		logger.info(sb);
		try{
			list = super.getJdbcTemplate().query(sb.toString(), ls.toArray() , 
					new TmnlMissingInfoRowMapper());
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内台区用户漏点信息出错！");
		    throw e;
	    }
	    if(null == list){
	    	list = new ArrayList<GatherByHandDto>();
	    }
	    return list;
	}
	
	/**
	 * 重新计算,0表示03地区
	 * @param orgNo，台区的供电单位
	 * @param statDate  
	 * @return 
	 */
	public void recalcData(final String orgNo, final Date statDate){
		String sql = "{call pkg_calc_service.calcpower(0, ?, ?)}"; 
		Object obj = super.getJdbcTemplate().execute(sql, 
				new CallableStatementCallback(){
			public Object doInCallableStatement(CallableStatement cs) throws SQLException, 
			    DataAccessException {
				cs.setDate(1, DateUtil.dateToSqlDate(statDate));
				cs.setString(2, orgNo);
				cs.execute();
				return 1; 
			}
		});
	}
	
	/**
	 * 从数据库中取出与图形相关的数据
	 * @param tgId  台区id
	 * @param start 统计的开始时间
	 * @param end  统计的结束时间
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> findChartData(final Integer tgId, final Date start,
			final Date end) {
		try {

			return DaoUtils.run("[*,c]", new IDoDao() {
				public <T> T execute(JdbcBaseDAOImpl jb, RowMapper rm,
						SqlData sd) {
					String sql = "select stat_date,\n"
							+ "       decode(ppq,\n"
							+ "              null,\n"
							+ "              0,\n"
							+ "              0,\n"
							+ "              0,\n"
							+ "              case\n"
							+ "                when ppq < tg_spq then\n"
							+ "                 0\n"
							+ "                else\n"
							+ "                 (ppq - tg_spq) / ppq\n"
							+ "              end) as ll_per,\n"
							+ "       nvl(ll_idx_value,0) as ll_idx_value\n"
							+ "  from a_tg_pq\n"
							+ " where tg_id = ? and stat_date between to_number(to_char(trunc(?), 'yyyymmdd')) and to_number(to_char(trunc(?), 'yyyymmdd'))\n"
							+ " order by stat_date";
					return (T) jb.getJdbcTemplate().query(sql,
							new Object[] { tgId, start, end }, rm);
				}
			});

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
			
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内台区用电损耗统计信息
	 */
	class LosePowerStatRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LosePowerStatDto dto = new LosePowerStatDto();
			try {
				dto.setTgId(rs.getString("TG_ID"));
				dto.setTgNo(rs.getString("TG_NO"));
				dto.setTgName(rs.getString("TG_NAME"));
				dto.setOrgNo(rs.getString("ORG_NO"));
				dto.setOrgName(rs.getString("ORG_NAME"));
				dto.setPpq((null != rs.getString("T_PPQ"))? rs.getDouble("T_PPQ") : null);
				dto.setSpq((null != rs.getString("T_SPQ"))? rs.getDouble("T_SPQ") : null);
				dto.setIdx((null != rs.getString("LL_IDX"))? rs.getDouble("LL_IDX") : null);
				dto.setDate(rs.getString("STAT_DATE"));
				dto.setReadSuccRate(rs.getDouble("READ_SUCC_RATE"));
				return dto;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内台区用户成功抄表信息
	 */
	class LPSSuccReadRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LosePowerStatReadBean bean = new LosePowerStatReadBean();
			try {
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setMpNo(rs.getString("MP_NO"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("STAT_DATE")));
                bean.setAssetNo(rs.getString("ASSET_NO"));
                bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setPapE(rs.getString("PAP_E"));
				bean.setPapRE(rs.getString("PAP_R_E"));
				bean.setPapRS(rs.getString("PAP_R_S"));
				bean.settFactor(rs.getString("T_FACTOR"));
				return bean;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内台区用户失败抄表信息
	 */
	class LPSFailReadRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LosePowerStatReadBean bean = new LosePowerStatReadBean();
			try {
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setMpNo(rs.getString("MP_NO"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("STAT_DATE")));
                bean.setAssetNo(rs.getString("ASSET_NO"));
                bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
                bean.setPapE(rs.getString("PAP_E"));
				bean.setPapRE(rs.getString("PAP_R_E"));
				bean.setPapRS(rs.getString("PAP_R_S"));
				return bean;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内台区用户漏点信息
	 */
	class TmnlMissingInfoRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			GatherByHandDto dto = new GatherByHandDto();
			try {
				dto.setMpSn(rs.getShort("MP_SN"));
				dto.setProtNoList(rs.getString("PROT_NO_LIST"));
				dto.setStatDate(rs.getDate("STAT_DATE"));
				dto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				dto.setApplyCnt(rs.getShort("APPLY_CNT"));
				dto.setDataGroup(rs.getByte("DATA_GROUP"));
				dto.setDataId(rs.getLong("ID"));
				dto.setDataSrc(rs.getString("DATA_SRC"));
				dto.setDenizenMp(rs.getString("DENIZEN_MP"));
				dto.setFirstCollCnt(rs.getShort("FIRST_COLL_CNT"));
				dto.setIsDenizen(rs.getBoolean("IS_DENIZEN"));
				dto.setMissingCnt(rs.getShort("MISSING_CNT"));
				dto.setPowerCutCnt(rs.getShort("POWER_CUT_CNT"));
				dto.setTemplateId(rs.getLong("TEMPLATE_ID"));
				return dto;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
}
