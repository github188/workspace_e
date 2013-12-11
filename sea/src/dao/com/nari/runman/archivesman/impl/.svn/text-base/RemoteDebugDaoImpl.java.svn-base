package com.nari.runman.archivesman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.runman.archivesman.IRemoteDebugDao;
import com.nari.runman.runstatusman.RemoteDebugDto;
import com.nari.support.Page;

public class RemoteDebugDaoImpl extends JdbcBaseDAOImpl implements IRemoteDebugDao {

	/**
	 * 根据采集点标识查询此采集点下所有的远程调试列表
	 * @param cpNo 要进行查询的采集点标识
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<RemoteDebugDto> queryRemoteDebugList(String cpNo,long start, int limit) throws Exception {
		 String sql = " SELECT ORG.ORG_NAME, --供电单位\n  "+
		    " CONS.CONS_NO, --用户编号 \n        "+
		    " CONS.CONS_NAME, --用户名称 \n      "+
		    " CONS.ELEC_ADDR, --用电地址\n       "+
		    " CONS.MR_SECT_NO, --抄表段\n       "+
		    " MP.MP_NAME, --计量点名称\n          "+
		    " MPU.USAGE_TYPE_NAME, --计量点类型\n "+
		    " TMNL.TMNL_ASSET_NO, \n         "+
		    " TMNL.CIS_ASSET_NO, --终端资产号 \n  "+
		    " TMNL.TERMINAL_ADDR, --终端地址\n   "+
		    " TMNL.COLL_MODE,\n              "+
		    " COMD.COMM_MODE, --通信方式\n       "+
		    " PCODE.PROTOCOL_CODE,\n         "+
		    " PCODE.PROTOCOL_NAME, --通信规约\n  "+
		    " DMP.MP_SN, --测量点序号\n           "+
		    " '' CURRZ,  --示数(总)\n           "+
		    " '' CURRF,  --示数(峰) \n          "+
		    " '' CURRP,  --示数(平)\n           "+
		    " '' CURRG,  --示数(谷) \n          "+
		    " MET.FMR_ASSET_NO, --采集器编码\n    "+
		    " MET.ASSET_NO, --电表资产\n         "+
		    " MET.COMM_ADDR1, --表地址\n        "+
		    " MET.T_FACTOR --倍率\n            "+
		    " FROM R_TMNL_RUN       TMNL,\n  "+
		    " R_CP_CONS_RELA   CCR, \n       "+
		    " C_CONS           CONS, \n      "+
		    " E_DATA_MP        DMP,  \n      "+
		    " C_MP             MP,  \n       "+
		    " C_METER          MET,  \n      "+
		    " O_ORG            ORG, \n       "+
		    " VW_MP_USAGE_TYPE MPU, \n       "+
		    " VW_COMM_MODE     COMD, \n      "+
		    " VW_PROTOCOL_CODE PCODE  \n     "+
		    " WHERE CONS.ORG_NO = ORG.ORG_NO \n "+
			" AND CONS.CONS_ID = CCR.CONS_ID \n"+
			" AND CONS.CONS_NO = MP.CONS_NO\n"+
			" AND MP.MP_ID = MET.MP_ID     \n"+
			" AND MET.METER_ID = DMP.METER_ID    \n"+
			" AND DMP.TMNL_ASSET_NO = TMNL.TMNL_ASSET_NO \n  "+
			" AND MP.USAGE_TYPE_CODE = MPU.USAGE_TYPE_CODE(+)\n    "+
			" AND TMNL.COLL_MODE = COMD.COMM_MODE_CODE(+)\n   "+
			" AND TMNL.PROTOCOL_CODE = PCODE.PROTOCOL_CODE(+)\n "+
			" AND CCR.CP_NO = TMNL.CP_NO \n  "+
			" AND TMNL.CP_NO = ? "    +
			" ORDER BY CONS.CONS_NO       ";
		 
		 return super.pagingFind(sql, start, limit, new RemoteDebugRowMapper(),
					new Object[]{cpNo});
	}
	
	class RemoteDebugRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			RemoteDebugDto rdd = new RemoteDebugDto();
			rdd.setOrgName(rs.getString("ORG_NAME"));
			rdd.setConsNo(rs.getString("CONS_NO"));
			rdd.setConsName(rs.getString("CONS_NAME"));
			rdd.setElecAddr(rs.getString("ELEC_ADDR"));
			rdd.setMrSect_no(rs.getString("MR_SECT_NO"));
			rdd.setMpName(rs.getString("MP_NAME"));
			rdd.setUsageTypeName(rs.getString("USAGE_TYPE_NAME"));
			rdd.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
			rdd.setCisAssetNo(rs.getString("CIS_ASSET_NO"));
			rdd.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
			rdd.setColl_mode(rs.getString("COLL_MODE"));
			rdd.setComm_mode(rs.getString("COMM_MODE"));
			rdd.setProtocol_code(rs.getString("PROTOCOL_CODE"));
			rdd.setProtocolName(rs.getString("PROTOCOL_NAME"));
			rdd.setMpSn(rs.getString("MP_SN"));
			rdd.setCurrz(rs.getString("CURRZ"));
			rdd.setCurrf(rs.getString("CURRF"));
			rdd.setCurrp(rs.getString("CURRP"));
			rdd.setCurrg(rs.getString("CURRG"));
			rdd.setFmrAssetNo(rs.getString("FMR_ASSET_NO"));
			rdd.setAssetNo(rs.getString("ASSET_NO"));
			rdd.setCommAddr1(rs.getString("COMM_ADDR1"));
			rdd.setTfactor(rs.getString("T_FACTOR"));
			return rdd;
		}
		
	}
	
}
