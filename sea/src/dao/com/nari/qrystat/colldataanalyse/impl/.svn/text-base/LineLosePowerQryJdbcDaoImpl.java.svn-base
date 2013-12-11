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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.qrystat.colldataanalyse.LineLosePowerBean;
import com.nari.qrystat.colldataanalyse.LineLosePowerQryJdbcDao;
import com.nari.qrystat.colldataanalyse.LineLosePowerReadBean;
import com.nari.support.Page;
import com.nari.util.AutoLang;
import com.nari.util.DateUtil;
import com.nari.util.NodeTypeUtils;

public class LineLosePowerQryJdbcDaoImpl  extends JdbcBaseDAOImpl implements LineLosePowerQryJdbcDao{
	//定义日志
	private static final Logger logger = Logger.getLogger(LineLosePowerQryJdbcDaoImpl.class);
	
	/**
	 * 线路用电损耗查询信息，主页面
	 * @param nodeId 节点id
	 * @param nodeType 节点类型
	 * @param orgType 如果节点是供电单位，显示供电单位类型
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerBean>
	 */
	public Page<LineLosePowerBean> findLineLosePowerQry(String nodeId, String nodeType, String orgType, Integer startDate, 
			Integer endDate,long start, int limit, PSysUser pSysUser){
		Page<LineLosePowerBean> psc = null;
		String sql = "";
		
		if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
			sql = 
				"SELECT ALP.LINE_ID,\n" +
				"       LINE.LINE_NO,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,\n" + 
				"       ALP.STAT_DATE,\n" + 
				"       ALP.LINE_SUPPLY_PQ,\n" + 
				"       ALP.LINE_SPQ,\n" + 
				"       ALP.READ_SUCC_RATE,\n" + 
				"       ALP.LL_IDX_VALUE\n" + 
				"  FROM A_LINE_PQ ALP, G_LINE LINE, O_ORG ORG\n" + 
				" WHERE ALP.LINE_ID = LINE.LINE_ID\n" + 
				"   AND ALP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ALP.STAT_DATE >= ?\n" + 
				"   AND ALP.STAT_DATE <= ?\n" +
				"   AND ALP.ORG_NO LIKE ?";
				nodeId = nodeId+"%";
		}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)){
			sql = 
				"SELECT ALP.LINE_ID,\n" +
				"       LINE.LINE_NO,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,\n" + 
				"       ALP.STAT_DATE,\n" + 
				"       ALP.LINE_SUPPLY_PQ,\n" + 
				"       ALP.LINE_SPQ,\n" + 
				"       ALP.READ_SUCC_RATE,\n" + 
				"       ALP.LL_IDX_VALUE\n" + 
				"  FROM A_LINE_PQ ALP, G_LINE LINE, O_ORG ORG\n" + 
				" WHERE ALP.LINE_ID = LINE.LINE_ID\n" + 
				"   AND ALP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ALP.STAT_DATE >= ?\n" + 
				"   AND ALP.STAT_DATE <= ?\n" + 
				"   AND ALP.LINE_ID = ?";
		}else if(NodeTypeUtils.NODETYPE_SUB.equals(nodeType)){
			sql =
				"SELECT ALP.LINE_ID,\n" +
				"       LINE.LINE_NO,\n" + 
				"       LINE.LINE_NAME,\n" + 
				"       ORG.ORG_NAME,ORG.ORG_NO,\n" + 
				"       ALP.STAT_DATE,\n" + 
				"       ALP.LINE_SUPPLY_PQ,\n" + 
				"       ALP.LINE_SPQ,\n" + 
				"       ALP.READ_SUCC_RATE,\n" + 
				"       ALP.LL_IDX_VALUE\n" + 
				"  FROM A_LINE_PQ ALP, G_LINE LINE, O_ORG ORG, G_SUBS_LINE_RELA RELA\n" + 
				" WHERE ALP.LINE_ID = LINE.LINE_ID\n" + 
				"   AND ALP.ORG_NO = ORG.ORG_NO\n" + 
				"   AND ALP.LINE_ID = RELA.LINE_ID\n" + 
				"   AND RELA.RELA_FLAG = 1\n" + 
				"   AND ALP.STAT_DATE >= ?\n" + 
				"   AND ALP.STAT_DATE <= ?\n" + 
				"   AND RELA.SUBS_ID = ?";
		}else{
			//
		}
        logger.info(sql);
		try{
		    psc = this.pagingFind(sql, start, limit, new LineLosePowerQryRowMapper(), new Object[]{startDate,endDate,nodeId});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询线路用电损耗查询信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 根据条件查询时间段内线路用户抄表成功信息
	 * @param lineId 线路id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerReadBean>
	 */
	public Page<LineLosePowerReadBean> findLineLPReadSuccInfo(String lineId, String startDate, String ioValue,
			String endDate,long start, int limit, PSysUser pSysUser){
		Page<LineLosePowerReadBean> psc = null;		
		String sql = 
			"SELECT SUCC.CONS_NO,\n" +
			"       CONS.CONS_NAME,METER.ASSET_NO,SUCC.TMNL_ASSET_NO,\n" + 
			"       MP.MP_NO,\n" + 
			"       MP.MP_NAME,\n" + 
			"       SUCC.STAT_DATE,SUCC.PAP_E\n" + 
			"  FROM A_CONS_STAT_D SUCC,\n" + 
			"       G_IO_MP       IO,\n" + 
			"       G_CHKUNIT     CU,\n" +
			"       C_METER       METER,\n" + 
			"       C_CONS        CONS,\n" + 
			"       C_MP          MP\n" + 
			" WHERE IO.METER_ID = METER.METER_ID\n" + 
			"   AND SUCC.ASSET_NO = METER.ASSET_NO\n" + 
			"   AND METER.MP_ID = MP.MP_ID\n" + 
			"   AND SUCC.CONS_NO = CONS.CONS_NO AND SUCC.PAP_E IS NOT NULL\n" + 
			"   AND IO.CHKUNIT_ID = CU.CHKUNIT_ID\n" +
			"   AND CU.CHKUNIT_TYPE_CODE = '01'\n" +
			"   AND IO.OBJ_ID = ?\n";
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
			"   AND SUCC.STAT_DATE >= TO_DATE(?, 'yyyy-mm-dd')\n" + 
			"   AND SUCC.STAT_DATE < TO_DATE(?, 'yyyy-mm-dd') + 1";
		logger.info(sql);
		try{
		    psc = this.pagingFind(sql, start, limit, new LineLPSuccReadRowMapper(), new Object[]{Long.valueOf(lineId),startDate,endDate});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内线路用户抄表成功信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 根据条件查询时间段内线路用户抄表失败信息
	 * @param lineId 线路id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @param start 分页开始项
	 * @param limit 分页数
	 * @param pSysUser 操作员
	 * @return Page<LineLosePowerReadBean>
	 */
	public Page<LineLosePowerReadBean> findLineLPReadFailInfo(String lineId, String startDate, String ioValue,
			String endDate,long start, int limit, PSysUser pSysUser){
		Page<LineLosePowerReadBean> psc = null;
		String sql = 
			"SELECT FAIL.CONS_NO,\n" +
			"       CONS.CONS_NAME,METER.ASSET_NO,FAIL.TMNL_ASSET_NO,\n" + 
			"       MP.MP_NO,\n" + 
			"       MP.MP_NAME,\n" + 
			"       FAIL.STAT_DATE\n" + 
			"  FROM A_READ_FAIL_LIST FAIL,\n" + 
			"       G_IO_MP          IO,\n" + 
			"       G_CHKUNIT        CU,\n" +
			"       C_METER          METER,\n" + 
			"       C_CONS           CONS,\n" + 
			"       C_MP             MP\n" + 
			" WHERE IO.METER_ID = METER.METER_ID\n" + 
			"   AND FAIL.ASSET_NO = METER.ASSET_NO\n" + 
			"   AND METER.MP_ID = MP.MP_ID\n" + 
			"   AND FAIL.CONS_NO = CONS.CONS_NO\n" + 
			"   AND IO.CHKUNIT_ID = CU.CHKUNIT_ID\n" +
			"   AND CU.CHKUNIT_TYPE_CODE = '01'\n" +
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
		logger.info(sql);
		try{
		    psc = this.pagingFind(sql, start, limit, new LineLPFailReadRowMapper(), new Object[]{Long.valueOf(lineId),startDate,endDate});
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内线路用户抄表失败信息出错！");
		    throw e;
	    }
	    return psc;
	}
	
	/**
	 * 根据条件查询时间段内线路用户漏点信息，界面全选调用
	 * @param lineId 线路id 
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @Param ioValue *表示全部，1表示入口，0表示出口
	 * @return List<GatherByHandDto>
	 */
	public List<GatherByHandDto> findTmnlMissingInfoByAll(String lineId, String startDate, 
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
			"               AND CU.CHKUNIT_TYPE_CODE = '01'\n" +
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
			list = super.getJdbcTemplate().query(sql, new Object[]{lineId, startDate, endDate, startDate, endDate} , 
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
	 * 根据条件查询时间段内线路用户漏点信息，非全选，仅仅对选中用户进行操作
	 * @param tmnlAssetNoList 终端资产编号
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return List<GatherByHandDto>
	 */
	public List<GatherByHandDto> findTmnlMissingInfoByANList(List<String> tmnlAssetNoList, 
			String startDate, String endDate){
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
		LinkedList ls = new LinkedList(tmnlAssetNoList);
		ls.addFirst(endDate);
		ls.addFirst(startDate);
		sb.append(AutoLang.autoIn("MARK.TMNL_ASSET_NO",tmnlAssetNoList, 500));
		logger.info(sb);
		try{
			list = super.getJdbcTemplate().query(sb.toString(), ls.toArray() , 
					new TmnlMissingInfoRowMapper());
	    }catch(RuntimeException e){
		    this.logger.debug("根据条件查询时间段内线路用户漏点信息出错！");
		    throw e;
	    }
	    if(null == list){
	    	list = new ArrayList<GatherByHandDto>();
	    }
	    return list;
	}
	
	/**
	 * 重新计算
	 * @param orgNo
	 * @param statDate  
	 * @return 
	 */
	public void recalcData(final String orgNo, final Date statDate){
		String sql = "{call pkg_calc_service.calcpower(0,?,?)}"; 
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
	 * 自定义查询返回的值对象，线路用电损耗查询信息
	 */
	class LineLosePowerQryRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LineLosePowerBean bean = new LineLosePowerBean();
			try {
				bean.setLineId(rs.getString("LINE_ID"));
				bean.setLineNo(rs.getString("LINE_NO"));
				bean.setLineName(rs.getString("LINE_NAME"));
				bean.setOrgName(rs.getString("ORG_NAME"));
				bean.setSupplypq((null != rs.getString("LINE_SUPPLY_PQ"))? rs.getDouble("LINE_SUPPLY_PQ") : null);
				bean.setSpq((null != rs.getString("LINE_SPQ"))? rs.getDouble("LINE_SPQ") : null);
				bean.setIdx((null != rs.getString("LL_IDX_VALUE"))? rs.getDouble("LL_IDX_VALUE") : null);
				bean.setDate(rs.getString("STAT_DATE"));
				bean.setReadSuccRate(rs.getDouble("READ_SUCC_RATE"));
				bean.setOrgNo(rs.getString("ORG_NO"));
				return bean;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内线路用户成功抄表信息
	 */
	class LineLPSuccReadRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LineLosePowerReadBean bean = new LineLosePowerReadBean();
			try {
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setMpNo(rs.getString("MP_NO"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("STAT_DATE")));
                bean.setAssetNo(rs.getString("ASSET_NO"));
                bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setValue(rs.getString("PAP_E"));
				return bean;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内线路用户失败抄表信息
	 */
	class LineLPFailReadRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int p) throws SQLException {
			LineLosePowerReadBean bean = new LineLosePowerReadBean();
			try {
				bean.setConsNo(rs.getString("CONS_NO"));
				bean.setConsName(rs.getString("CONS_NAME"));
				bean.setMpNo(rs.getString("MP_NO"));
				bean.setMpName(rs.getString("MP_NAME"));
				bean.setDate(new SimpleDateFormat("yyyy-MM-dd").format(rs.getTimestamp("STAT_DATE")));
                bean.setAssetNo(rs.getString("ASSET_NO"));
                bean.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				bean.setValue("");
				return bean;
			}catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}	
	
	/**
	 * 自定义查询返回的值对象，根据条件查询时间段内线路用户漏点信息
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
