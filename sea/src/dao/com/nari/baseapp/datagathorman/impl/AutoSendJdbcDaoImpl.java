package com.nari.baseapp.datagathorman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.datagatherman.AutoSendDto;
import com.nari.baseapp.datagathorman.AutoSendJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;

public class AutoSendJdbcDaoImpl extends JdbcBaseDAOImpl implements AutoSendJdbcDao {
	Logger logger = Logger.getLogger(AutoSendJdbcDaoImpl.class);
	/**
	 * 查找相关的居名集抄户信息
	 * @author yutao
	 * @param consId传入的查询id
	 * @param start
	 * @param limit
	 */
	public Page<AutoSendDto> findASQuery(String consId,String queryTmnlNo, long start, int limit) {

		StringBuffer sb=new StringBuffer();
		sb.append(" SELECT ORG.ORG_NAME,--供电单位\n");
		sb.append("      CONS.CONS_ID,\n");
		sb.append("      CONS.CONS_NO,--用户编号\n");
		sb.append("      CONS.CONS_NAME,--用户名称\n");
		sb.append("      CONS.ELEC_ADDR,--用电地址\n");
		sb.append("      CONS.CONTRACT_CAP,--合同容量\n");
		sb.append("      MET.ASSET_NO,--电能表资产\n");
		sb.append("      MET.TMNL_ASSET_NO,\n");
		sb.append("      MET.BAUDRATE,--波特率\n");
		sb.append("      MET.COMM_ADDR1,--表地址\n");
		sb.append("      MET.INST_DATE,--安装日期\n");
		sb.append("      MET.T_FACTOR,--倍率\n");
		sb.append("      DECODE(MET.REG_STATUS,1,'已注册','未注册') REG_STATUS,--注册状态\n");
		sb.append("      MET.REG_SN,--注册序号\n");
		sb.append("      MET.FMR_ASSET_NO,--采集器编码\n");
		sb.append("      TMNL.CIS_ASSET_NO,--集中器资产\n");
		sb.append("      TMNL.TERMINAL_ADDR--终端地址\n");
		sb.append(" FROM C_CONS          CONS,\n");
		sb.append("      R_CP_CONS_RELA  CCR,\n");
		sb.append("      R_TMNL_RUN      TMNL,\n");
		sb.append("      C_MP            MP,\n");
		sb.append("      C_METER_MP_RELA MMR,\n");
		sb.append("      C_METER         MET,\n");
		sb.append("      O_ORG           ORG\n");
		sb.append("WHERE CONS.CONS_ID = MP.CONS_ID\n");
		sb.append("  AND CONS.CONS_ID = CCR.CONS_ID\n");
		sb.append("  AND CCR.CP_NO = TMNL.CP_NO\n");
		sb.append("  AND MP.MP_ID = MMR.MP_ID\n");
		sb.append("  AND MMR.METER_ID = MET.METER_ID\n");
		sb.append("  AND CONS.ORG_NO = ORG.ORG_NO\n");
		sb.append("  AND CONS.TG_ID = ?");
		sb.append("  AND MET.TMNL_ASSET_NO = ?");
		sb.append("  ORDER BY MET.REG_SN");

		logger.debug(sb.toString());
		return super.pagingFind(sb.toString(), start, limit, new AutoSendDtoRowMapper(),
				new Object[]{consId,queryTmnlNo});
	}

	class AutoSendDtoRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		AutoSendDto autosenddto = new AutoSendDto();
		 try { 
		autosenddto.setOrgName(rs.getString("ORG_NAME"));
		autosenddto.setConsId(rs.getLong("CONS_ID"));
		autosenddto.setConsNo(rs.getString("CONS_NO"));
		autosenddto.setConsName(rs.getString("CONS_NAME"));
		autosenddto.setElecAddr(rs.getString("ELEC_ADDR"));
		autosenddto.setContractCap(rs.getDouble("CONTRACT_CAP"));
		autosenddto.setAssetNo(rs.getString("ASSET_NO"));
		autosenddto.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
		autosenddto.setBaudrate(rs.getString("BAUDRATE"));
		autosenddto.setCommAddr1(rs.getString("COMM_ADDR1"));
		autosenddto.setInstDate(rs.getTimestamp("INST_DATE"));
		autosenddto.settFactor(rs.getDouble("T_FACTOR"));
		autosenddto.setRegStatus(rs.getString("REG_STATUS"));
		autosenddto.setRegSn(rs.getInt("REG_SN"));
		autosenddto.setFmrAssetNo(rs.getString("FMR_ASSET_NO"));
		autosenddto.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
		autosenddto.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
		return autosenddto;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
}