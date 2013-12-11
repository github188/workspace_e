package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.qrystat.querycollpoint.CCust;
import com.nari.qrystat.querycollpoint.CITRun;
import com.nari.qrystat.querycollpoint.CPS;
import com.nari.qrystat.querycollpoint.CSP;
import com.nari.qrystat.querycollpoint.Ccontact;
import com.nari.qrystat.querycollpoint.DMeter;
import com.nari.qrystat.querycollpoint.Gtran;
import com.nari.qrystat.querycollpoint.ICCustDao;
import com.nari.qrystat.querycollpoint.RSIMCharge;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class CCustDaoImpl extends JdbcBaseDAOImpl implements ICCustDao {
	Logger logger = Logger.getLogger(CCustDaoImpl.class);
	private CCust ccust;
	private Ccontact ccontact;
	private CSP csp;
	private CPS cps;
	private DMeter dmeter;
	private CITRun citrun;
	private Gtran gtran;

	public Gtran getGtran() {
		return gtran;
	}

	public void setGtran(Gtran gtran) {
		this.gtran = gtran;
	}

	public CITRun getCitrun() {
		return citrun;
	}

	public void setCitrun(CITRun citrun) {
		this.citrun = citrun;
	}

	/**
	 * @return the dmeter
	 */
	public DMeter getDmeter() {
		return dmeter;
	}

	/**
	 * @param dmeter
	 *            the dmeter to set
	 */
	public void setDmeter(DMeter dmeter) {
		this.dmeter = dmeter;
	}

	/**
	 * @return the cps
	 */
	public CPS getCps() {
		return cps;
	}

	/**
	 * @param cps
	 *            the cps to set
	 */
	public void setCps(CPS cps) {
		this.cps = cps;
	}

	/**
	 * @return the csp
	 */
	public CSP getCsp() {
		return csp;
	}

	/**
	 * @param csp
	 *            the csp to set
	 */
	public void setCsp(CSP csp) {
		this.csp = csp;
	}

	/**
	 * @return the ccontact
	 */
	public Ccontact getCcontact() {
		return ccontact;
	}

	/**
	 * @param ccontact
	 *            the ccontact to set
	 */
	public void setCcontact(Ccontact ccontact) {
		this.ccontact = ccontact;
	}

	/**
	 * @return the ccust
	 */
	public CCust getCcust() {
		return ccust;
	}

	/**
	 * @param ccust
	 *            the ccust to set
	 */
	public void setCcust(CCust ccust) {
		this.ccust = ccust;
	}

	@Override
	public List<CCust> findConsumerInfo(String custNo) throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT C.CUST_NO,\n" + "       C.NAME,\n"
				+ "       C.INDUSTRY_CODE,\n" + "       DECODE(C.ECONOMY_TYPE_CODE,'01','全民所有制经济','02','集体所有制经济','03','私人所有制经济','04','混合所有制经济','') ECONOMY_TYPE_CODE,\n"
				+ "       C.ANNUAL_GP,\n" + "       DECODE(C.VIP_FLAG,'Y','VIP客户','N','非VIP客户','0','非VIP客户','1','VIP客户','') VIP_FLAG,\n"
				+ "       C.REG_CAPITAL,\n" + "       C.CREDIT_LEVEL_CODE,\n"
				+ "       C.VALUE_LEVEL_CODE,\n"
				+ "       C.RISK_LEVEL_CODE,\n" + "       C.T_CAPTAL,\n"
				+ "       C.BRIEF,\n" + "       C.ENTEPRISE_WEBSITE,\n"
				+ "       C.LEGAL_PERSON\n" + "  FROM C_CUST C, C_CONS\n"
				+ " WHERE C.CUST_ID = C_CONS.CUST_ID(+)\n"
				+ "   AND C_CONS.CUST_ID = ?");
		logger.debug(sb.toString());
		return (List<CCust>) super.getJdbcTemplate().query(sb.toString(),
				new Object[] { custNo }, new CCustRowMapper());
	}

	public List<CSP> findCSP(String consNo) throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT S.CONS_ID,\n" + "       S.TYPE_CODE,\n"
				+ "       S.SP_NAME,\n" + "       S.INTERLOCK_MODE,\n"
				+ "       S.PS_NUM_CODE,\n" + "       S.EQUIP_LOC,\n"
				+ "       S.PS_SWITCH_MODE,\n" + "       S.SP_REMARK,\n"
				+ "       S.SPARE_POWER_CAP,\n"
				+ "       S.SPARE_POWER_FLAG,\n" + "       S.LOCK_MODE\n"
				+ "  FROM C_SP S\n" + " WHERE S.CONS_ID = ?");
		logger.debug(sb.toString());
		return (List<CSP>) super.getJdbcTemplate().query(sb.toString(),
				new Object[] { consNo }, new CSPRowMapper());
	}

	public Page<Ccontact> findCcontact(String custNo, long start, int limit)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append(

		"SELECT C.CONTACT_NAME,\n" + "       C.GENDER,\n"
				+ "       C.DEPT_NO,\n" + "       C.TITLE,\n"
				+ "       C.OFFICE_TEL,\n" + "       C.MOBILE,\n"
				+ "       C.HOMEPHONE,\n" + "       C.CONTACT_MODE,\n"
				+ "       C.CONTACT_PRIO,\n" + "       C.ADDR,\n"
				+ "       C.CONTACT_REMARK\n" + "  FROM C_CONTACT C, C_CONS\n"
				+ " WHERE C.CUST_ID = C_CONS.CUST_ID(+)\n"
				+ "   AND C_CONS.CUST_ID = ?");
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new CcontactRowMapper(), new Object[] { custNo });
	}

	class CcontactRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			Ccontact ccontact = new Ccontact();
			try {
				ccontact.setContactName(rs.getString("CONTACT_NAME"));
				ccontact.setGender(rs.getString("GENDER"));
				ccontact.setDeptNo(rs.getString("DEPT_NO"));
				ccontact.setTitle(rs.getString("TITLE"));
				ccontact.setOfficeTel(rs.getString("OFFICE_TEL"));
				ccontact.setMobile(rs.getString("MOBILE"));
				ccontact.setHomephone(rs.getString("HOMEPHONE"));
				ccontact.setContactMode(rs.getString("CONTACT_MODE"));
				ccontact.setContactPrio(rs.getInt("CONTACT_PRIO"));
				ccontact.setAddr(rs.getString("ADDR"));
				ccontact.setContactRemark(rs.getString("CONTACT_REMARK"));
				return ccontact;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class CCustRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CCust ccust = new CCust();
			try {
				ccust.setCustNo(rs.getString("CUST_NO"));
				ccust.setName(rs.getString("NAME"));
				ccust.setIndustryCode(rs.getString("INDUSTRY_CODE"));
				ccust.setEconomyTypeCode(rs.getString("ECONOMY_TYPE_CODE"));
				ccust.setAnnualGp(rs.getDouble("ANNUAL_GP"));
				ccust.setVipFlag(rs.getString("VIP_FLAG"));
				ccust.setRegCapital(rs.getDouble("REG_CAPITAL"));
				ccust.setCreditLevelCode(rs.getString("CREDIT_LEVEL_CODE"));
				ccust.setValueLevelCode(rs.getString("VALUE_LEVEL_CODE"));
				ccust.setRiskLevelCode(rs.getString("RISK_LEVEL_CODE"));
				ccust.settCaptal(rs.getDouble("T_CAPTAL"));
				ccust.setBrief(rs.getString("BRIEF"));
				ccust.setEntepriseWebsite(rs.getString("ENTEPRISE_WEBSITE"));
				ccust.setLegalPerson(rs.getString("LEGAL_PERSON"));
				return ccust;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class CSPRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CSP csp = new CSP();
			try {
				csp.setConsId(rs.getLong("CONS_ID"));
				csp.setTypeCode(rs.getString("TYPE_CODE"));
				csp.setSpName(rs.getString("SP_NAME"));
				csp.setInterlockMode(rs.getString("INTERLOCK_MODE"));
				csp.setPsNumCode(rs.getString("PS_NUM_CODE"));
				csp.setEquipLoc(rs.getString("EQUIP_LOC"));
				csp.setPsSwitchMode(rs.getString("PS_SWITCH_MODE"));
				csp.setSpRemark(rs.getString("SP_REMARK"));
				csp.setSparePowerCap(rs.getDouble("SPARE_POWER_CAP"));
				csp.setSparePowerFlag(rs.getString("SPARE_POWER_FLAG"));
				csp.setLockMode(rs.getString("LOCK_MODE"));
				return csp;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public Page<CPS> findCPS(String consNo, long start, int limit)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT PS_NO,\n" + "       PS_CAP,\n"
				+ "       TYPE_CODE,\n" + "       PHASE_CODE,\n"
				+ "       SUBS_ID,\n" + "       LINE_ID,\n"
				+ "       PS_VOLT,\n" + "       LINEIN_MODE,\n"
				+ "       PS_ATTR,\n" + "       RELAY_PROTECT_MODE,\n"
				+ "       RUN_MODE,\n" + "       REMARK,\n"
				+ "       PR_POINT\n" + "  FROM C_PS WHERE CONS_ID = ?");
		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit,
				new CPSRowMapper(), new Object[] { consNo });
	}

	class CPSRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CPS cps = new CPS();
			try {
				cps.setPsNo(rs.getString("PS_NO"));
				cps.setPsCap(rs.getDouble("PS_CAP"));
				cps.setTypeCode(rs.getString("TYPE_CODE"));
				cps.setPhaseCode(rs.getString("PHASE_CODE"));
				cps.setSubsId(rs.getLong("SUBS_ID"));
				cps.setLineId(rs.getLong("LINE_ID"));
				cps.setPsVolt(rs.getString("PS_VOLT"));
				cps.setLineinMode(rs.getString("LINEIN_MODE"));
				cps.setPsAttr(rs.getString("PS_ATTR"));
				cps.setRelayProtectMode(rs.getString("RELAY_PROTECT_MODE"));
				cps.setRunMode(rs.getString("RUN_MODE"));
				cps.setRemark(rs.getString("REMARK"));
				cps.setPrPoint(rs.getString("PR_POINT"));
				return cps;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public Page<RSIMCharge> findSIM(String SIM_NO, long start, int limit)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append(

		"SELECT MR_SIM_CHARGE_ID,\n" + "       SIM_NO,\n"
				+ "       GPRS_CODE,\n" + "       CHARGE_DATE,\n"
				+ "       OVERRUN_FLUX,\n" + "       ACTUAL_FLUX,\n"
				+ "       COMM_CHARGE\n" + "  FROM R_SIM_CHARGE\n"
				+ " WHERE SIM_NO = ?");
		logger.debug(sb.toString());

		return super.pagingFind(sb.toString(), start, limit,
				new RSIMChargeRowMapper(), new Object[] { SIM_NO });
	}

	class RSIMChargeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			RSIMCharge rsimcharge = new RSIMCharge();
			try {
				rsimcharge.setMrSimChargeId(rs.getLong("MR_SIM_CHARGE_ID"));
				rsimcharge.setSimNo(rs.getString("SIM_NO"));
				rsimcharge.setGprsCode(rs.getString("GPRS_CODE"));
				rsimcharge.setChargeDate(rs.getString("CHARGE_DATE"));
				rsimcharge.setOverrunFlux(rs.getLong("OVERRUN_FLUX"));
				rsimcharge.setActualFlux(rs.getLong("ACTUAL_FLUX"));
				rsimcharge.setCommCharge(rs.getDouble("COMM_CHARGE"));
				return rsimcharge;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class DMeterRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			DMeter dmeter = new DMeter();
			try {
				dmeter.settFactor(rs.getLong("CT")*rs.getLong("PT"));
				dmeter.setAssetNo(rs.getString("ASSET_NO"));
				dmeter.setBarCode(rs.getString("BAR_CODE"));
				dmeter.setLotNo(rs.getString("LOT_NO"));
				dmeter.setMadeNo(rs.getString("MADE_NO"));
				dmeter.setSortCode(rs.getString("SORT_CODE"));
				dmeter.setTypeCode(rs.getString("TYPE_CODE"));
				dmeter.setModelCode(rs.getString("MODEL_CODE"));
				dmeter.setWiringMode(rs.getString("WIRING_MODE"));
				dmeter.setVoltCode(rs.getString("VOLT_CODE"));
				dmeter.setMeterDigit(rs.getFloat("METER_DIGITS"));
				dmeter.setOverloadFactor(rs.getString("OVERLOAD_FACTOR"));
				dmeter.setConstCode(rs.getString("CONST_CODE"));
				dmeter.setManufacturer(rs.getString("MANUFACTURER"));
				String madeDate = rs.getString("MADE_DATE");
				if( madeDate!= null){
				dmeter.setMadeDate(rs.getString("MADE_DATE").substring(0,10));
				}
				dmeter.setMeterUsage(rs.getString("METER_USAGE"));
				dmeter.setFreqCode(rs.getString("FREQ_CODE"));
				dmeter.setPulseConstantCode(rs.getString("PULSE_CONSTANT_CODE"));
				dmeter.setMeasTheory(rs.getString("MEAS_THEORY"));
				dmeter.setCi(rs.getString("CI"));
				dmeter.setConsNo(rs.getString("CONS_NO"));
				dmeter.setConsName(rs.getString("CONS_NAME"));
				dmeter.setMpNo(rs.getString("MP_NO"));
				dmeter.setMpName(rs.getString("MP_NAME"));
				dmeter.setTypeCode(rs.getString("TYPE_CODE"));
				dmeter.setSelfFactor(rs.getInt("SELF_FACTOR"));
				dmeter.setBothWayCal(rs.getString("BOTH_WAY_CALC"));
				dmeter.setRepayFlag(rs.getString("PREPAY_FLAG"));
				dmeter.setMultiRateFlag(rs.getString("MULTIRATE_FALG"));				
				return dmeter;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<DMeter> findDMeter(String consNo) throws DBAccessException {

		StringBuffer sb=new StringBuffer();
		sb.append("SELECT D.ASSET_NO,\n");
		sb.append("       D.BAR_CODE,\n");
		sb.append("       D.LOT_NO,\n");
		sb.append("       D.MADE_NO,\n");
		sb.append("       DECODE(D.SORT_CODE,'01','有功表','02','无功表','03','多功能表','04','复费率表','05','需量表','06','预付费表','07','长寿命技术表','08','亚长寿命技术表','09','有功+无功表','') AS SORT_CODE,\n");
		sb.append("       DECODE(D.TYPE_CODE,'11','感应式-普通型','12','感应式-长寿命','13','感应式-亚长寿命','21','电子式-普通型','22','电子式-复费率',\n  " );
		sb.append("       '23','电子式-最大需量','24','电子式-多功能','25','电子式-预付费','31','机电式-复费率','32','机电式-最大需量','33','机电式-最大需量+复费率',\n   ");
		sb.append("       '34','机电式-普通型','35','机电式-单费率 ','') AS　TYPE_CODE,\n");
		sb.append("       DECODE(D.MODEL_CODE,'001','DD862-4','002','DTSD532','003','DD204-4','004','DTS532','005','DTS532(2)','') AS MODEL_CODE,\n");
		sb.append("       DECODE(D.WIRING_MODE,'01','直接接入','02','经互感器接入','') AS WIRING_MODE,\n");
		sb.append("       DECODE(D.VOLT_CODE,'01','220V','02','3x380V','03','3x380/220V','04','3x100V','05','3x57.7/100V','') AS VOLT_CODE,\n");
		sb.append("       D.RATED_CURRENT,\n");
		sb.append("       DECODE(D.OVERLOAD_FACTOR,'01','1','02','1.2','03','1.5','04','2','05','2.5','') AS OVERLOAD_FACTOR,\n");
		sb.append("       DECODE(D.CONST_CODE,'01','30','02','40','03','50','04','60','05','80','06','100','') AS CONST_CODE,\n");
		sb.append("       D.MANUFACTURER,\n");
		sb.append("       D.MADE_DATE,\n");
		sb.append("       DECODE(D.METER_USAGE,'01','GPRS','02','GSM','03','网络表','04','数据采集表','') AS METER_USAGE,\n");
		sb.append("       D.FREQ_CODE,\n");
		sb.append("       D.PULSE_CONSTANT_CODE,\n");
		sb.append("       DECODE(D.MEAS_THEORY,'01','二线连接(单相)','02','三线连接(三相三线)','03','四线连接(三相三线)','04','四线连接(三相四线','05','六线连接(三相四线)') AS MEAS_THEORY,\n");
		sb.append("       DECODE(D.CI,'01','232','02','485','03','232+485','04','双485','05','') AS CI,\n");
		sb.append("       M.CONS_NO,\n");
		sb.append("       (SELECT CONS_NAME FROM C_CONS CONS WHERE CONS.CONS_NO=DMP.CONS_NO) AS CONS_NAME,\n");
		sb.append("       D.ASSET_NO,\n");
		sb.append("       M.MP_NO,\n");
		sb.append("       M.MP_NAME,\n");
		sb.append("       D.SELF_FACTOR,DECODE(D.BOTH_WAY_CALC,'01','是','02','否','') BOTH_WAY_CALC,DECODE(D.PREPAY_FLAG,'01','是','02','否','') PREPAY_FLAG,DECODE(D.MULTIRATE_FALG,'01','是','02','否','') MULTIRATE_FALG,D.METER_DIGITS,");
		sb.append("       DECODE(M.TYPE_CODE,'11','感应式-普通型','12','感应式-长寿命','13','感应式-亚长寿命','21','电子式-普通型','22','电子式-复费率',\n  " );
		sb.append("       '23','电子式-最大需量','24','电子式-多功能','25','电子式-预付费','31','机电式-复费率','32','机电式-最大需量','33','机电式-最大需量+复费率',\n   ");
		sb.append("       '34','机电式-普通型','35','机电式-单费率 ','') TYPE_CODE, \n");
		sb.append("       DMP.CT,\n");
		sb.append("       DMP.PT\n");
		sb.append("  FROM D_METER D,C_MP M,E_DATA_MP DMP,C_METER_MP_RELA MMR WHERE\n");
		sb.append("      D.METER_ID=MMR.METER_ID AND M.MP_ID=MMR.MP_ID AND dmp.meter_id=d.meter_id\n");
		sb.append("      and M.CONS_NO =DMP.CONS_NO AND DMP.TERMINAL_ADDR=? AND DMP.IS_VALID=1 and dmp.mp_sn <>0");
		logger.debug(sb.toString());
		List<DMeter> mList=(List<DMeter>) super.getJdbcTemplate().query(sb.toString(),new Object[] { consNo }, new DMeterRowMapper());
		
		return mList;
	}

	@Override
	public Page<CITRun> findCITRun(String tFactor, long start, int limit)
			throws DBAccessException {
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT R.SORT_CODE,\n");
		sb.append("       R.PHASE_CODE,\n");
		sb.append("       R.INST_LOC,\n");
		sb.append("       R.INST_DATE,\n");
		sb.append("       R.CURRENT_RATIO_CODE,\n");
		sb.append("       R.VOLT_RATIO_CODE,\n");
		sb.append("       R.PRIV_FLAG\n");
		sb.append("  FROM C_IT_RUN R, C_METER M\n");
		sb.append(" WHERE R.CONS_NO = M.CONS_NO\n");
		sb.append("   AND M.T_FACTOR = ?");
		String sql = sb.toString();
		logger.debug(sb.toString());
		return super.pagingFind(sql, start, limit, new CITRunRowMapper(),
				new Object[] { tFactor });
	}

	class CITRunRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			CITRun citrun = new CITRun();
			try {
				citrun.setSortCode(rs.getString("SORT_CODE"));
				citrun.setPhaseCode(rs.getString("PHASE_CODE"));
				citrun.setInstLoc(rs.getString("INST_LOC"));
				citrun.setInstDate(rs.getDate("INST_DATE"));
				citrun.setCurrentRatioCode(rs.getString("CURRENT_RATIO_CODE"));
				citrun.setVoltRatioCode(rs.getString("VOLT_RATIO_CODE"));
				citrun.setPrivFlag(rs.getString("PRIV_FLAG"));
				return citrun;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gtran> findGtran(String CONS_ID,String consType) throws DBAccessException {
		StringBuffer sb=new StringBuffer();
		List<Object> pame = new ArrayList<Object>();
		sb.append("SELECT G.CONS_ID,G.EQUIP_ID,\n");
		sb.append("       O.ORG_NAME AS ORG_NO,\n");
		sb.append("       G.TRAN_NAME,\n");
		sb.append("       G.K_VALUE,\n");
		sb.append("       DECODE(G.TS_ALG_FLAG,'0','不计算','1','按定比','2','按定量','3','按标准公式','4','按标准表','') AS TS_ALG_FLAG,\n");
		sb.append("       G.PLATE_CAP,\n");
		sb.append("       DECODE(G.TYPE_CODE,'01','变压器','02','高压电动机','') AS TYPE_CODE,\n");
		sb.append("       G.INST_ADDR,\n");
		sb.append("       G.INST_DATE,\n");
		sb.append("       DECODE(G.CHG_REMARK,'01','新装','02','停用','03','启用','04','拆除','05','减容','06','减容恢复','') AS CHG_REMARK,\n");
//		sb.append("       G.CHG_REMARK,\n");
		sb.append("       VR.run_status_name AS RUN_STATUS_CODE,\n");
		sb.append("       VP.PUB_PRIV AS PUB_PRIV_FLAG,\n");
		sb.append("       G.MADE_DATE,\n");
		sb.append("       G.MODEL_NO,\n");
		sb.append("       G.FACTORY_NAME,\n");
		sb.append("       G.MADE_NO,\n");
		sb.append("       DECODE(G.PROTECT_MODE,'01','反时限过流保护','02','反时限速断保护','03','定时限过流保护','04','定时限速断保护','05','差动保护','') AS PROTECT_MODE,\n");
		sb.append("       vvc.VOLT as FRSTSIDE_VOLT_CODE,vvcc.VOLT as SNDSIDE_VOLT_CODE,\n");
		sb.append("       G.SNDSIDE_VOLT_CODE,DECODE(PR_CODE,'01',' 局属','02','用户','1',' 局属','2','用户','') AS PR_CODE\n");
		sb.append("  FROM G_TRAN G,O_ORG O,VW_RUN_STATUS_CODE VR,VW_PUB_PRIV_FLAG VP,vw_volt_code vvc,vw_volt_code vvcc\n");
		sb.append("    WHERE VP.PUB_PRIV_FLAG = G.PUB_PRIV_FLAG\n");
		sb.append("     and vvc.VOLT_CODE(+)=g.frstside_volt_code and vvcc.VOLT_CODE(+)=g.sndside_volt_code   AND VR.run_status_code = G.RUN_STATUS_CODE\n");
		sb.append("      AND G.ORG_NO = O.ORG_NO\n");
		if(consType.equals("2")){
			sb.append("       AND TG_ID = ?");
			pame.add(CONS_ID);
		}else{
			sb.append("       AND CONS_ID = ?");
			pame.add(CONS_ID);
		}
		String sql=sb.toString();

		logger.debug(sb.toString());
		return (List<Gtran>) super.getJdbcTemplate().query(sb.toString(),
				pame.toArray(), new GtranRowMapper());
	}

	class GtranRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			Gtran gtran = new Gtran();
			try {
				gtran.setEquipId(rs.getDouble("EQUIP_ID"));
				gtran.setConsId(rs.getLong("CONS_ID"));
				gtran.setOrgNo(rs.getString("ORG_NO"));
				gtran.setTranName(rs.getString("TRAN_NAME"));
				gtran.setPlateCap(rs.getDouble("PLATE_CAP"));
				gtran.setTypeCode(rs.getString("TYPE_CODE"));
				gtran.setInstAddr(rs.getString("INST_ADDR"));
				if(rs.getDate("INST_DATE") != null){
					String instString = rs.getDate("INST_DATE").toString().substring(0,10);
					gtran.setInstDate(instString);
				}			
				gtran.setChgRemark(rs.getString("CHG_REMARK"));
				gtran.setRunStatusCode(rs.getString("RUN_STATUS_CODE"));
				gtran.setPubPrivFlag(rs.getString("PUB_PRIV_FLAG"));
				String madeString = "";
				if(rs.getDate("MADE_DATE") != null){
					madeString = rs.getDate("MADE_DATE").toString().substring(0,10);
					gtran.setMadeDate(madeString);
				}
				gtran.setModelNo(rs.getString("MODEL_NO"));
				gtran.setFactoryName(rs.getString("FACTORY_NAME"));
				gtran.setMadeNo(rs.getString("MADE_NO"));
				gtran.setProtectMode(rs.getString("PROTECT_MODE"));
				gtran.setFrstsideVoltCode(rs.getString("FRSTSIDE_VOLT_CODE"));
				gtran.setSndsideVoltCode(rs.getString("SNDSIDE_VOLT_CODE"));
				gtran.setPrCode(rs.getString("PR_CODE"));
				gtran.setTsAlgFlag(rs.getString("TS_ALG_FLAG"));
				gtran.setkValue(rs.getFloat("K_VALUE"));			
				return gtran;
			} catch (Exception e) {
				return null;
			}
		}
	}

	class GTranGridRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			Gtran gtran = new Gtran();
			try {
				gtran.setConsId(rs.getLong("CONS_ID"));
				gtran.setTranName(rs.getString("TRAN_NAME"));
				gtran.setTypeCode(rs.getString("TYPE_CODE"));
				gtran.setInstAddr(rs.getString("INST_ADDR"));
				String instString = rs.getDate("INST_DATE").toString().substring(0,10);
				gtran.setInstDate(instString);
				gtran.setPlateCap(rs.getDouble("PLATE_CAP"));
				gtran.setChgRemark(rs.getString("CHG_REMARK"));
				gtran.setRunStatusCode(rs.getString("RUN_STATUS_CODE"));
				return gtran;
			} catch (Exception e) {
				return null;
			}
		}
	}

	@Override
	public Page<Gtran> findGtran(String CONS_ID, long start, int limit)
			throws DBAccessException {

		StringBuffer sb=new StringBuffer();
		sb.append("SELECT CONS_ID,\n");
		sb.append("       TRAN_NAME,\n");
		sb.append("       DECODE(TYPE_CODE,'01','变压器','02','高压电动机') AS TYPE_CODE,\n");
		sb.append("       INST_ADDR,\n");
		sb.append("       INST_DATE,\n");
		sb.append("       PLATE_CAP,\n");
		sb.append("       DECODE(CHG_REMARK,'01','新装','02','停用','03','启用','04','拆除','05','减容','06','减容恢复','') AS CHG_REMARK,\n");
		sb.append("       RUN_STATUS_CODE\n");
		//sb.append("  FROM G_TRAN WHERE 1=1 OR CONS_ID=?");
		sb.append("  FROM G_TRAN WHERE CONS_ID = ?");

		String sql=sb.toString();

		logger.debug(sb.toString());
		return super.pagingFind(sql, start, limit, new GTranGridRowMapper(),
				new Object[] { CONS_ID });
	}
}
