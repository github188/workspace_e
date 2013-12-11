package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.qrystat.querycollpoint.Cmeter;
import com.nari.qrystat.querycollpoint.CmeterInfo;
import com.nari.qrystat.querycollpoint.ConsumerInfo;
import com.nari.qrystat.querycollpoint.IConsumerInfoDao;
import com.nari.qrystat.querycollpoint.NewMpDayPower;
import com.nari.qrystat.querycollpoint.Tmnlrun;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class ConsumerInfoDaoImpl extends JdbcBaseDAOImpl implements
		IConsumerInfoDao {
	/**
	 * @param 用电客户
	 * @param 运行终端
	 */
	private ConsumerInfo consumerInfo;
	private Tmnlrun tmnlrun;
	private Cmeter cmeter;
	private CmeterInfo cmeterInfo;
	private NewMpDayPower newMpDayPower;
	
	
	public void setCmeterInfo(CmeterInfo cmeterInfo) {
		this.cmeterInfo = cmeterInfo;
	}

	public void setNewMpDayPower(NewMpDayPower newMpDayPower) {
		this.newMpDayPower = newMpDayPower;
	}

	Logger logger = Logger.getLogger(ConsumerInfoDaoImpl.class);
	/**
	 * @param cmeter
	 *            the cmeter to set
	 */
	public void setCmeter(Cmeter cmeter) {
		this.cmeter = cmeter;
	}

	/**
	 * @param consumerInfo
	 *            the consumerInfo to set
	 */
	public void setConsumerInfo(ConsumerInfo consumerInfo) {
		this.consumerInfo = consumerInfo;
	}

	/**
	 * @param tmnlrun
	 *            the tmnlrun to set
	 */
	public void setTmnlrun(Tmnlrun tmnlrun) {
		this.tmnlrun = tmnlrun;
	}

	/**
	 * 查找运行终端信息
	 */
	@Override
	public List<Tmnlrun> findTmnlrun(String CONS_NO) throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append(

				"\n" +
				"SELECT T.TERMINAL_ID,        T.CP_NO,\n" + 
				"               T.TMNL_ASSET_NO,T.CIS_ASSET_NO,T.TERMINAL_ADDR,\n" + 
				"               T.SIM_NO,        T.CONS_NO, T.CP_NO,\n" + 
				"               TO_CHAR(T.RUN_DATE, 'yyyy-mm-dd') RUN_DATE,\n" + 
				"               M.TMNL_TYPE,        F.FACTORY_NAME,\n" + 
				"               P.PROTOCOL_NAME,       C.COMM_MODE COMM,O.COMM_MODE COLL,\n" + 
				"               S.STATUS_NAME,        T.ATTACH_METER_FLAG,\n" + 
				"               T.HARMONIC_DEV_FLAG,\n" + 
				"               T.AC_SAMPLING_FLAG,        T.ELIMINATE_FLAG,\n" + 
				"               T.PS_ENSURE_FLAG,v.mode_name,T.PRIO_PS_MODE,\n" +
				"               DECODE(T.GATE_ATTR_FLAG,'1','常开','2','常闭','') GATE_ATTR_FLAG,\n" +
				"               DECODE(T.SEND_UP_MODE,0,'主站召测','终端上送') SEND_UP_MODE,E.PS_MODE_NAME,\n" +
				//"               T.FREEZE_MODE\n" +
				"               T.FREEZE_CYCLE_NUM\n" +
				"          FROM VW_TMNL_RUN         T,\n" + 
				"               VW_TMNL_TYPE_CODE   M,\n" + 
				"               VW_TMNL_FACTORY     F,\n" + 
				"               VW_COMM_MODE        C,\n" + 
				"               VW_COMM_MODE        O,\n" + 
				"               VW_TMNL_STATUS_CALC S,\n" + 
				"               VW_PROTOCOL_CODE    P,\n" + 
				"               VW_PS_MODE          E,vw_tmnl_mode_code V\n" + 
				"         WHERE T.PRIO_PS_MODE = E.ps_mode_no(+)\n" + 
				"           AND T.TERMINAL_TYPE_CODE = M.TMNL_TYPE_CODE(+)\n" + 
				"           AND T.FACTORY_CODE = F.FACTORY_CODE(+)\n" + 
				"           AND T.ID = V.MODE_CODE(+)\n" + 				
				"           AND T.COMM_MODE = C.COMM_MODE_CODE(+)\n" + 
				"           AND T.COLL_MODE = O.COMM_MODE_CODE(+)\n" + 
				"           AND T.STATUS_CODE = S.STATUS_CODE(+)\n" + 
				"           AND T.PROTOCOL_CODE = P.PROTOCOL_CODE(+)\n" + 
				"           AND T.CONS_NO = ?");


		logger.debug(sb.toString());
		return (List<Tmnlrun>) super.getJdbcTemplate().query(sb.toString(),
				new Object[] { CONS_NO }, new TRRowMapper());
	}

	@Override
	/**
	 * 查找用电客户信息
	 */
	public List<ConsumerInfo> findConsumerInfo(String CONS_NO) {
		StringBuffer sb = new StringBuffer();
		sb.append(
"SELECT C.CONS_NO,\n" +
"       C.CONS_NAME,\n" + 
"       C.ELEC_ADDR,\n" + 
"       C.CUST_NO,\n" + 
"       C.CONTRACT_CAP,\n" + 
"       C.RUN_CAP,\n" + 
"       T.TRADE_NAME,\n" + 
"       O.ORG_NAME,\n" + 
"       S.SUBS_NAME,\n" + 
"       L.LINE_NAME,\n" + 
"       V.VOLT,\n" + 
"       E.ELEC_TYPE,\n" + 
"       W.CONS_TYPE_NAME\n" + 
"  FROM C_CONS            C,\n" + 
"       O_ORG             O,\n" + 
"       G_LINE            L,\n" + 
"       G_SUBS            S,\n" + 
"       VW_TRADE          T,\n" + 
"       VW_VOLT_CODE      V,\n" + 
"       VW_ELEC_TYPE_CODE E, VW_CONS_TYPE  W\n" + 
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
" WHERE C.ORG_NO = O.ORG_NO(+)\n" + 
"   AND C.TRADE_CODE = T.TRADE_NO(+)\n" + 
"   AND C.LINE_ID = L.LINE_ID(+)\n" + 
"   AND C.SUBS_ID = S.SUBS_ID(+)\n" + 
"   AND C.VOLT_CODE = V.VOLT_CODE(+)\n" + 
"   AND C.ELEC_TYPE_CODE = E.ELEC_TYPE_CODE(+)\n" + 
"   AND C.CONS_TYPE = W.CONS_TYPE(+)\n" +
"   AND C.CONS_NO = ?");
		logger.debug(sb.toString());
		List<ConsumerInfo> list = (List<ConsumerInfo>) super.getJdbcTemplate()
				.query(sb.toString(), new Object[] { CONS_NO },
						new CIRowMapper());

		return list;

	}

	@Override
	public Page<Cmeter> findCmeter(String CONS_NO,String TG_ID,long start,int limit)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		if(TG_ID == null){
			TG_ID = "";
				};
		String[] param = {CONS_NO,TG_ID};
				if(CONS_NO.substring(0,1).equals("T")){
					sb.append("\n");
					sb.append("SELECT C.CONS_NO,P.MP_NO,P.MP_NAME,\n");
					sb.append("       C.CONS_NAME,\n");
					sb.append("       C.ELEC_ADDR,\n");
					sb.append("       M.METER_ID,\n");
					sb.append("       M.ASSET_NO,\n");
					sb.append("       M.BAUDRATE,\n");
					sb.append("       M.COMM_NO,\n");
					sb.append("       M.COMM_MODE,\n");
					sb.append("       M.COMM_ADDR1,\n");
					sb.append("       M.COMM_ADDR2,\n");
					sb.append("       M.INST_LOC,\n");
					sb.append("       M.INST_DATE,\n");
					sb.append("       M.T_FACTOR,\n");
					sb.append("       M.REG_STATUS,\n");
					sb.append("       M.REG_SN\n");
					sb.append("  FROM C_CONS C, C_METER M,C_MP P\n");
					sb.append(" WHERE C.CONS_NO = M.CONS_NO AND P.CONS_ID = C.CONS_ID\n");
					sb.append("   AND M.tmnl_asset_no in (SELECT tmnl_asset_no from C_METER where cons_no = ?)");
					param = new String[]{CONS_NO};
				}else{
				sb.append("SELECT distinct C.CONS_NO,P.MP_NO,P.MP_NAME,\n");
				sb.append("       C.CONS_NAME,\n");
				sb.append("       C.ELEC_ADDR,\n");
				sb.append("       M.METER_ID,\n");
				sb.append("       M.ASSET_NO,\n");
				sb.append("       M.BAUDRATE,\n");
				sb.append("       M.COMM_NO,\n");
				sb.append("       M.COMM_MODE,\n");
				sb.append("       M.COMM_ADDR1,\n");
				sb.append("       M.COMM_ADDR2,\n");
				sb.append("       M.INST_LOC,\n");
				sb.append("       M.INST_DATE,\n");
				sb.append("       M.T_FACTOR,\n");
				sb.append("       M.REG_STATUS,\n");
				sb.append("       M.REG_SN\n");
				sb.append("  FROM C_CONS C, C_METER M,C_MP P,c_meter_mp_rela R\n");
				sb.append(" WHERE R.METER_ID = M.METER_ID AND R.MP_ID = P.MP_ID AND C.CONS_NO = M.CONS_NO");
				sb.append("   AND (M.CONS_NO = ? OR C.TG_ID = ?) ORDER BY C.CONS_NO");
				}
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit, new CMRowMapper(),
				param);
	}

	/**
	 * 用电客户内部类
	 * 
	 * @author zhaoliang
	 */
	class CIRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			ConsumerInfo consumerinfo = new ConsumerInfo();
			try {
				consumerinfo.setConsNo(rs.getString("CONS_NO"));
				consumerinfo.setConsName(rs.getString("CONS_NAME"));
				consumerinfo.setElecAddr(rs.getString("ELEC_ADDR"));
				consumerinfo.setCustNo(rs.getString("CUST_NO"));
				consumerinfo.setContractCap(rs.getDouble("CONTRACT_CAP"));
				consumerinfo.setRunCap(rs.getDouble("RUN_CAP"));
				consumerinfo.setTradeName(rs.getString("TRADE_NAME"));
				consumerinfo.setOrgName(rs.getString("ORG_NAME"));
				consumerinfo.setVolt(rs.getString("VOLT"));
				consumerinfo.setElecType(rs.getString("ELEC_TYPE"));
				consumerinfo.setConsTypeName(rs.getString("CONS_TYPE_NAME"));
				consumerinfo.setLineName(rs.getString("LINE_NAME"));
				consumerinfo.setSubsName(rs.getString("SUBS_NAME"));
				return consumerinfo;
			} catch (Exception e) {
				return null;
			}
		}
	}

	/**
	 * 运行终端的内部类
	 * 
	 * @author zhaoliang
	 */
	class TRRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			Tmnlrun tmnlrun = new Tmnlrun();
			try {
				tmnlrun.setModeName(rs.getString("mode_name"));
				tmnlrun.setTerminalId(rs.getString("TERMINAL_ID"));
				tmnlrun.setCpNo(rs.getString("CP_NO"));
				tmnlrun.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
     			tmnlrun.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
				tmnlrun.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				tmnlrun.setSimNo(rs.getString("SIM_NO"));
				tmnlrun.setConsNo(rs.getString("CONS_NO"));
				tmnlrun.setRunDate(rs.getString("RUN_DATE"));
				tmnlrun.setTmnlType(rs.getString("TMNL_TYPE"));
				tmnlrun.setFactoryName(rs.getString("FACTORY_NAME"));
				tmnlrun.setProtocolName(rs.getString("PROTOCOL_NAME"));
				tmnlrun.setComm(rs.getString("COMM"));
				tmnlrun.setCpNo(rs.getString("CP_NO"));
				tmnlrun.setColl(rs.getString("COLL"));
				tmnlrun.setStatusName(rs.getString("STATUS_NAME"));
				tmnlrun.setAttachMeterFlag(rs.getString("ATTACH_METER_FLAG"));
				tmnlrun.setHarmonicDevFlag(rs.getString("HARMONIC_DEV_FLAG"));
				tmnlrun.setAcSamplingFlag(rs.getString("AC_SAMPLING_FLAG"));
				tmnlrun.setEliminateFlag(rs.getString("ELIMINATE_FLAG"));
				tmnlrun.setPsEnsureFlag(rs.getString("PS_ENSURE_FLAG"));
				tmnlrun.setPsModeName(rs.getString("PS_MODE_NAME"));
				tmnlrun.setFreezeCycleNum(rs.getDouble("FREEZE_CYCLE_NUM"));
				tmnlrun.setPrioPsMode(rs.getString("PRIO_PS_MODE"));
				tmnlrun.setGateAttrFlag(rs.getString("GATE_ATTR_FLAG"));
				tmnlrun.setSendUpMode(rs.getString("SEND_UP_MODE"));	
				return tmnlrun;
			} catch (Exception e) {
				logger.error("*****************************************");
				logger.error(e.getMessage());
				return null;
			}
		}
	}

	/**
	 * 运行电能表
	 * 
	 * @author zhaoliang
	 * 
	 */
	class CMRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			Cmeter cmeter = new Cmeter();
			try {
				cmeter.setMpName(rs.getString("MP_NAME"));
				cmeter.setMpNo(rs.getString("MP_NO"));
				cmeter.setConsNo(rs.getString("CONS_NO"));
				cmeter.setConsName(rs.getString("CONS_NAME"));
				cmeter.setElecAddr(rs.getString("ELEC_ADDR"));
				cmeter.setMeterId(rs.getLong("METER_ID"));
				//cmeter.setCollObjId(rs.getLong("COLL_OBJ_ID"));
				cmeter.setAssetNo(rs.getString("ASSET_NO"));
				cmeter.setBaudrate(rs.getString("BAUDRATE"));
				cmeter.setCommNo(rs.getString("COMM_NO"));
				cmeter.setCommMode(rs.getString("COMM_MODE"));
				cmeter.setCommAddr1(rs.getString("COMM_ADDR1"));
				cmeter.setCommAddr2(rs.getString("COMM_ADDR2"));
				cmeter.setInstLoc(rs.getString("INST_LOC"));
				cmeter.setInstDate(rs.getDate("INST_DATE"));
				cmeter.settFactor(rs.getDouble("T_FACTOR"));
				//cmeter.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				cmeter.setRegStatus(rs.getByte("REG_STATUS"));
				cmeter.setRegSn(rs.getInt("REG_SN"));
				return cmeter;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public Page<NewMpDayPower> findNewMpDayPower(String assetNo,String flagValue,String CONS_NO,
			String DateStart, String DateEnd, long start, int limit)
			throws DBAccessException {
		String addString = this.getETableAreaByConsNo(CONS_NO);
		StringBuffer sb=new StringBuffer();
		Object[] object = new Object[]{addString,CONS_NO,DateStart,DateEnd};
		String addAsset = ""; 
		if(!assetNo.equals("显示电能表资产号")){
			addAsset = " AND M.ASSET_NO = ?";
			object  = new Object[]{addString,CONS_NO,assetNo,DateStart,DateEnd};
		}
		sb.append("SELECT TO_CHAR(E.DATA_TIME,'YYYY-MM-DD') DATA_TIME1,M.CT,M.PT," +
				"TO_CHAR(E.DATA_TIME,'HH24:MI:SS') DATA_TIME2, --时间\n");
//		sb.append("       M.CT * M.PT T_FACTOR, --倍率\n");
		sb.append("       M.ASSET_NO, --电能表资产\n");
			sb.append("       E.P, --有功功率\n");
			sb.append("       E.PA, --A相有功功率\n");
			sb.append("       E.PB, --B相有功功率\n");
			sb.append("       E.PC, --C相有功功率\n");
			sb.append("       E.UA, --A相电压\n");
			sb.append("       E.UB, --B相电压\n");
			sb.append("       E.UC, --C相电压\n");
			sb.append("       E.IA, --A相电流\n");
			sb.append("       E.IB, --B相电流\n");
			sb.append("       E.IC, --C相电流\n");
			sb.append("       E.I0, --零序电流\n");
			sb.append("       E.Q, --无功功率\n");
			sb.append("       E.QA, --A相无功功率\n");
			sb.append("       E.QB, --B相无功功率\n");
			sb.append("       E.QC, --C相无功功率\n");
		sb.append("       E.F, --功率因素\n");
		sb.append("       E.FA, --A相功率因素\n");
		sb.append("       E.FB, --B相功率因素\n");
		sb.append("       E.FC, --C相功率因素\n");
		sb.append("       --后面默认不显示\n");
		sb.append("       E.PAP_E, --正向有功总电量\n");
		sb.append("       E.PRP_E, --正向无功总电量\n");
		sb.append("       E.RAP_E, --反向有功总电量\n");
		sb.append("       E.RRP_E, --反向无功总电量\n");
		sb.append("       E.PAP_R, --正向有功总示值\n");
		sb.append("       E.PRP_R, --正向无功总示值\n");
		sb.append("       E.RAP_R, --反向有功总示值\n");
		sb.append("       E.RRP_R, --反向无功总示值\n");
		sb.append("       T.TERMINAL_ADDR, --终端地址\n");
		sb.append("       C.CONS_NO, --用户编号\n");
		sb.append("       C.CONS_NAME, --用户名称\n");
		sb.append("       C.ELEC_ADDR --用电地址\n");
		sb.append("  FROM VW_TMNL_RUN T, C_CONS C, E_DATA_MP M, E_MP_CURVE E --按地区\n");
		sb.append(" WHERE T.CONS_NO = C.CONS_NO\n");
		sb.append("   AND T.TMNL_ASSET_NO = M.TMNL_ASSET_NO\n");
		sb.append("   AND M.ID = E.ID AND E.AREA_CODE = ?\n");
		sb.append("   AND C.CONS_NO = ? "+addAsset+"--传入参数\n");
		sb.append("   AND E.DATA_TIME BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')+1 --传入参数默认当前日期\n");
		sb.append(" ORDER BY E.DATA_TIME DESC");
		logger.debug(sb.toString());
		if(flagValue.equals("1")){
			return  super.pagingFind(sb.toString(),start, limit,
					new NewMpDayPowerRowMapper2(),object);
		}else{
			
			return  super.pagingFind(sb.toString(),start, limit,
					new NewMpDayPowerRowMapper(),object);
		}
		
	}
	
	
	class NewMpDayPowerRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		NewMpDayPower newmpdaypower = new NewMpDayPower();
		 try { 
		newmpdaypower.setDataTime1(rs.getString("DATA_TIME1"));
		newmpdaypower.setDataTime2(rs.getString("DATA_TIME2"));
		long pt = rs.getLong("PT");
		long ct = rs.getLong("CT");
		long tFactor = pt*ct;
		newmpdaypower.settFactor(tFactor);
		newmpdaypower.setAssetNo(rs.getString("ASSET_NO"));
		newmpdaypower.setP(rs.getDouble("P"));
		newmpdaypower.setPa(rs.getDouble("PA"));
		newmpdaypower.setPb(rs.getDouble("PB"));
		newmpdaypower.setPc(rs.getDouble("PC"));
		newmpdaypower.setUa(rs.getDouble("UA"));
		newmpdaypower.setUb(rs.getDouble("UB"));
		newmpdaypower.setUc(rs.getDouble("UC"));
		newmpdaypower.setIa(rs.getDouble("IA"));
		newmpdaypower.setIb(rs.getDouble("IB"));
		newmpdaypower.setIc(rs.getDouble("IC"));
		newmpdaypower.setI0(rs.getDouble("I0"));
		newmpdaypower.setQ(rs.getDouble("Q"));
		newmpdaypower.setQa(rs.getDouble("QA"));
		newmpdaypower.setQb(rs.getDouble("QB"));
		newmpdaypower.setQc(rs.getDouble("QC"));
		newmpdaypower.setF(rs.getDouble("F"));
		newmpdaypower.setFa(rs.getDouble("FA"));
		newmpdaypower.setFb(rs.getDouble("FB"));
		newmpdaypower.setFc(rs.getDouble("FC"));
		newmpdaypower.setPapE(rs.getDouble("PAP_E"));
		newmpdaypower.setPrpE(rs.getDouble("PRP_E"));
		newmpdaypower.setRapE(rs.getDouble("RAP_E"));
		newmpdaypower.setRrpE(rs.getDouble("RRP_E"));
		newmpdaypower.setPapR(rs.getDouble("PAP_R"));
		newmpdaypower.setPrpR(rs.getDouble("PRP_R"));
		newmpdaypower.setRapR(rs.getDouble("RAP_R"));
		newmpdaypower.setRrpR(rs.getDouble("RRP_R"));
		newmpdaypower.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
		newmpdaypower.setConsNo(rs.getString("CONS_NO"));
		newmpdaypower.setConsName(rs.getString("CONS_NAME"));
		newmpdaypower.setElecAddr(rs.getString("ELEC_ADDR"));
		return newmpdaypower;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	class NewMpDayPowerRowMapper2 implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		NewMpDayPower newmpdaypower = new NewMpDayPower();
		 try { 
		newmpdaypower.setDataTime1(rs.getString("DATA_TIME1"));
		newmpdaypower.setDataTime2(rs.getString("DATA_TIME2"));
		long pt = rs.getLong("PT");
		long ct = rs.getLong("CT");
		long tFactor = pt*ct;
		newmpdaypower.settFactor(tFactor);
		newmpdaypower.setAssetNo(rs.getString("ASSET_NO"));
		newmpdaypower.setP(tFactor * rs.getDouble("P"));
		newmpdaypower.setPa(tFactor * rs.getDouble("PA"));
		newmpdaypower.setPb(tFactor * rs.getDouble("PB"));
		newmpdaypower.setPc(tFactor * rs.getDouble("PC"));
		newmpdaypower.setUa(pt * rs.getDouble("UA"));
		newmpdaypower.setUb(pt * rs.getDouble("UB"));
		newmpdaypower.setUc(pt * rs.getDouble("UC"));
		newmpdaypower.setIa(ct * rs.getDouble("IA"));
		newmpdaypower.setIb(ct * rs.getDouble("IB"));
		newmpdaypower.setIc(ct * rs.getDouble("IC"));
		newmpdaypower.setI0(ct * rs.getDouble("I0"));
		newmpdaypower.setQ(tFactor * rs.getDouble("Q"));
		newmpdaypower.setQa(tFactor * rs.getDouble("QA"));
		newmpdaypower.setQb(tFactor * rs.getDouble("QB"));
		newmpdaypower.setQc(tFactor * rs.getDouble("QC"));
		newmpdaypower.setF(rs.getDouble("F"));
		newmpdaypower.setFa(rs.getDouble("FA"));
		newmpdaypower.setFb(rs.getDouble("FB"));
		newmpdaypower.setFc(rs.getDouble("FC"));
		newmpdaypower.setPapE(rs.getDouble("PAP_E"));
		newmpdaypower.setPrpE(rs.getDouble("PRP_E"));
		newmpdaypower.setRapE(rs.getDouble("RAP_E"));
		newmpdaypower.setRrpE(rs.getDouble("RRP_E"));
		newmpdaypower.setPapR(rs.getDouble("PAP_R"));
		newmpdaypower.setPrpR(rs.getDouble("PRP_R"));
		newmpdaypower.setRapR(rs.getDouble("RAP_R"));
		newmpdaypower.setRrpR(rs.getDouble("RRP_R"));
		newmpdaypower.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
		newmpdaypower.setConsNo(rs.getString("CONS_NO"));
		newmpdaypower.setConsName(rs.getString("CONS_NAME"));
		newmpdaypower.setElecAddr(rs.getString("ELEC_ADDR"));
		return newmpdaypower;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}

	@Override
	//查询采集点信息
	public Page<CmeterInfo> findCmeterInfo(String CONS_NO,
			long start, int limit){
		StringBuffer sb = new StringBuffer();
		String[] param = {CONS_NO};
		sb.append("select cp.*,\n");
		sb.append("       (select t.terminal_addr\n");
		sb.append("          from vw_tmnl_run t\n");
		sb.append("         where t.cp_no = cp.cp_no) as rtu\n");
		sb.append("  from r_cp cp\n");
		sb.append(" where cp.cp_no in (select cp_no\n");
		sb.append("                      from vw_tmnl_run rtu\n");
		sb.append("                     where rtu.cons_no = ?)");

		return super.pagingFind(sb.toString(), start, limit, new CmeterInfoRowMapper(),
				param);
	
	}
	class CmeterInfoRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		CmeterInfo cmeterinfo = new CmeterInfo();
		 try {
			 cmeterinfo.setCpNo(rs.getString("CP_NO"));
			 cmeterinfo.setName(rs.getString("NAME"));
			 cmeterinfo.setCpTypeCode(rs.getString("CP_TYPE_CODE"));
			 cmeterinfo.setStatusCode(rs.getString("STATUS_CODE"));
			 cmeterinfo.setCpAddr(rs.getString("CP_ADDR"));
			 cmeterinfo.setGpsLongitude(rs.getString("GPS_LONGITUDE"));
			 cmeterinfo.setGpsLatitude(rs.getString("GPS_LATITUDE"));
			 cmeterinfo.setRtu(rs.getString("RTU"));
		return cmeterinfo;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
}
