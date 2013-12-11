package com.nari.qrystat.querycollpoint.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.qrystat.querycollpoint.RCP;
import com.nari.qrystat.querycollpoint.IRCPDao;
import com.nari.qrystat.querycollpoint.RCollObj;
import com.nari.qrystat.querycollpoint.RcpCommPara;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class RCPDaoImpl extends JdbcBaseDAOImpl implements IRCPDao {
	Logger logger = Logger.getLogger(RCPDaoImpl.class);
	private RCP rcp;
	private RCollObj rCollObj;
	public RCP getRcp() {
		return rcp;
	}

	public void setRcp(RCP rcp) {
		this.rcp = rcp;
	}


	public RCollObj getrCollObj() {
		return rCollObj;
	}

	public void setrCollObj(RCollObj rCollObj) {
		this.rCollObj = rCollObj;
	}

	@Override
	public List<RCP> findRCP(String consNo) throws DBAccessException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT T.CONS_NO,\n" +
				"       R.NAME,\n" + 
				"       DECODE(R.CP_TYPE_CODE,'1','关口采集点','用户采集点') CP_TYPE_CODE,\n" + 
				"       R.STATUS_CODE,V.CP_STATUS,\n" + 
				"       R.CP_ADDR,\n" + 
				"       R.GPS_LONGITUDE,\n" + 
				"       R.GPS_LATITUDE\n" + 
				"  FROM R_CP R,VW_TMNL_RUN T,vw_cp_status_code V WHERE V.CP_STATUS_CODE=R.STATUS_CODE AND T.CONS_NO = ?");
		logger.debug(sb.toString());
		 List<RCP> rcpList =   super.getJdbcTemplate().query(sb.toString(),
				new Object[] { consNo }, new RCPRowMapper());
		 return rcpList;
	}

	class RCPRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		RCP rcp = new RCP();
		 try { 
		rcp.setConsNo(rs.getString("CONS_NO"));
		rcp.setName(rs.getString("NAME"));
		rcp.setCpTypeCode(rs.getString("CP_TYPE_CODE"));
		rcp.setStatusCode(rs.getString("STATUS_CODE"));
		rcp.setCpStatus(rs.getString("CP_STATUS"));
		rcp.setCpAddr(rs.getString("CP_ADDR"));
		rcp.setGpsLongitude(rs.getString("GPS_LONGITUDE"));
		rcp.setGpsLatitude(rs.getString("GPS_LATITUDE"));
		
		return rcp;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
	

	public Page<RCollObj> findRcpCharge(String consNo,long start, int limit)
	throws DBAccessException {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT R.CP_NO,\n");
		sb.append("       R.COLL_PORT,\n");
		sb.append("       D.ASSET_NO,\n");
		sb.append("       R.CT_RATIO,\n");
		sb.append("       R.PT_RATIO,\n");
		sb.append("       R.T_FACTOR,\n");
		sb.append("       R.METER_CONST,\n");
		sb.append("       R.PORT_NO,\n");
		sb.append("       R.PULSE_ATTR,\n");
		sb.append("       T.CONS_NO\n");
		sb.append("  FROM R_COLL_OBJ R, D_METER D, VW_TMNL_RUN T\n");
		sb.append(" WHERE D.METER_ID = R.METER_ID\n");
		sb.append("   AND R.CP_NO = T.CP_NO\n");
		sb.append("   AND T.CONS_NO = ?");
		String sql=sb.toString();
this.logger.debug(sql);
try{
	return  super.pagingFind(sb.toString(),start,limit,new RCollObjRowMapper(),new Object[]{consNo});
}catch(RuntimeException e){
	this.logger.debug(sql);
	throw e;
}
}
	
	
	/**
	 * 采集点查询rowMapper
	 * @author zhaoliang
	 *
	 */
	class RCollObjRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		RCollObj rcollobj = new RCollObj();
		 try { 
		rcollobj.setCpNo(rs.getString("CP_NO"));
		rcollobj.setCollPort(rs.getInt("COLL_PORT"));
		rcollobj.setAssetNo(rs.getString("ASSET_NO"));
		rcollobj.setCtRatio(rs.getInt("CT_RATIO"));
		rcollobj.setPtRatio(rs.getInt("PT_RATIO"));
		rcollobj.settFactor(rs.getLong("T_FACTOR"));
		rcollobj.setMeterConst(rs.getInt("METER_CONST"));
		rcollobj.setPortNo(rs.getInt("PORT_NO"));
		rcollobj.setPulseAttr(rs.getString("PULSE_ATTR"));
		rcollobj.setConsNo(rs.getString("CONS_NO"));
		return rcollobj;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}


	@Override
	public List<RcpCommPara> findRcpCommPara(String consNo)
			throws DBAccessException {
		StringBuffer sb=new StringBuffer();
		sb.append("SELECT T.CONS_NO,\n");
		sb.append("       T.CP_NO,\n");
		sb.append("       R.TERMINAL_ADDR,\n");
		sb.append("       R.PROTOCOL_CODE,\n");
		sb.append("       R.ALG_NO,\n");
		sb.append("       R.ALG_KEY,\n");
		sb.append("       R.MASTER_IP,\n");
		sb.append("       R.MASTER_PORT,\n");
		sb.append("       R.GATEWAY_IP,\n");
		sb.append("       R.GATEWAY_PORT,\n");
		sb.append("       R.SPARE_IP_ADDR,\n");
		sb.append("       R.SPARE_PORT,\n");
		sb.append("       R.PROXY_IP_ADDR,\n");
		sb.append("       R.PROXY_PORT,\n");
		sb.append("       R.GPRS_CODE,\n");
		sb.append("       R.SMS_NO,\n");
		sb.append("       R.HEARTBEAT_CYCLE,\n");
		//sb.append("       TO_CHAR(R.START_DATE,'yyyy-mm-dd') NEWDATE,\n");
		sb.append("    	  R.START_DATE,\n");
		sb.append("       R.CHANNEL_NO,\n");
		sb.append("       R.RTS_ON,\n");
		sb.append("       R.RTS_OFF,\n");
		sb.append("       R.TRANSMIT_DELAY,\n");
		sb.append("       R.RESP_TIMEOUT\n");
		sb.append("  FROM R_CP_COMM_PARA R, VW_TMNL_RUN T\n");
		sb.append(" WHERE T.CP_NO = R.CP_NO\n");
		sb.append("   AND T.CONS_NO = ?");
		String sql=sb.toString();
		logger.debug(sb.toString());
		return super.getJdbcTemplate().query(sb.toString(),
				new Object[] { consNo }, new RcpCommParaRowMapper());
	}
	
	class RcpCommParaRowMapper implements RowMapper {
		 @Override 
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		RcpCommPara rcpcommpara = new RcpCommPara();
		 try { 
//			 logger.debug("**************************************************");
//			 for(int i=0;i<rs.getMetaData().getColumnCount();i++)
//			{logger.debug(rs.getMetaData().getColumnLabel(i));
//			logger.debug(rs.getMetaData().getColumnName(i));
//			}
		rcpcommpara.setConsNo(rs.getString("CONS_NO"));
		rcpcommpara.setCpNo(rs.getString("CP_NO"));
		rcpcommpara.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
		rcpcommpara.setProtocolCode(rs.getString("PROTOCOL_CODE"));
		rcpcommpara.setAlgNo(rs.getString("ALG_NO"));
		rcpcommpara.setAlgKey(rs.getString("ALG_KEY"));
		rcpcommpara.setMasterIp(rs.getString("MASTER_IP"));
		rcpcommpara.setMasterPort(rs.getInt("MASTER_PORT"));
		rcpcommpara.setGatewayIp(rs.getString("GATEWAY_IP"));
		rcpcommpara.setGatewayPort(rs.getInt("GATEWAY_PORT"));
		rcpcommpara.setSpareIpAddr(rs.getString("SPARE_IP_ADDR"));
		rcpcommpara.setSparePort(rs.getInt("SPARE_PORT"));
		rcpcommpara.setProxyIpAddr(rs.getString("PROXY_IP_ADDR"));
		rcpcommpara.setProxyPort(rs.getInt("PROXY_PORT"));
		rcpcommpara.setGprsCode(rs.getString("GPRS_CODE"));
		rcpcommpara.setSmsNo(rs.getString("SMS_NO"));
		rcpcommpara.setHeartbeatCycle(rs.getInt("HEARTBEAT_CYCLE"));
		String dateString = rs.getDate("START_DATE").toString().substring(0,10);
		rcpcommpara.setStartDate(dateString);
		//rcpcommpara.setStartDate(rs.getDate("START_DATE"));
		rcpcommpara.setChannelNo(rs.getString("CHANNEL_NO"));
		//rcpcommpara.setRtsOn(rs.getInt("RTS_ON"));
		//rcpcommpara.setRtsOff(rs.getInt("RTS_OFF"));
		String rtsOn = String.valueOf(rs.getInt("RTS_ON"));
		String rtsOff = String.valueOf(rs.getInt("RTS_OFF"));
		rcpcommpara.setOnAndOff(""+rtsOn+"/"+rtsOff+"");
		rcpcommpara.setTransmitDelay(rs.getInt("TRANSMIT_DELAY"));
		rcpcommpara.setRespTimeout(rs.getInt("RESP_TIMEOUT"));
		return rcpcommpara;
		}
		catch (Exception e) {
		return null;
		 }
		}
		}
}
