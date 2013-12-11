package com.nari.advapp.statanalyse.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.nari.advapp.statanalyse.ChargeContrastAnalysisBean;
import com.nari.advapp.statanalyse.ElecContrastAnalysisDao;
import com.nari.advapp.statanalyse.ElecContrastAnalysisOrgNo;
import com.nari.advapp.statanalyse.ElecContrastAnalysisType;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class ElecContrastAnalysisDaoImpl extends JdbcBaseDAOImpl implements
		ElecContrastAnalysisDao {
	Logger logger = Logger.getLogger(ElecContrastAnalysisDaoImpl.class);

	@Override
	public List<ElecContrastAnalysisOrgNo> getElecContrastAnalysisOrgNo(String DateStart,String DateEnd,String ValueCombo,String[] ValueArray,String OrgNo,String OrgType)
			throws DBAccessException {
		StringBuffer sb=new StringBuffer();
		String addString = "";
		Object[] prams =new Object[]{};
		if(ValueCombo.equals("1")){
			
		    if(ValueArray.length == 1){
			     addString = "?";
			     prams = new Object[]{DateStart,DateEnd,ValueArray[0]};
		    }else if(ValueArray.length == 2){
			     addString = "?,?";
			     prams = new Object[]{DateStart,DateEnd,ValueArray[0],ValueArray[1]};
		    }else if(ValueArray.length == 3){
			     addString = "?,?,?";
			     prams = new Object[]{DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2]};
		    }else if(ValueArray.length == 4){
			     addString = "?,?,?,?";
			     prams = new Object[]{DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2],ValueArray[3]};
		    }
		    sb.append("select OA.ORG_NO,O.ORG_NAME,OA.PAP_E,OA.PAP_E1,OA.PAP_E2,OA.PAP_E3,OA.PAP_E4,OA.STAT_DATE\n");
			sb.append(" from A_ORG_STAT_D OA,O_ORG O WHERE O.ORG_NO = OA.ORG_NO \n");
			sb.append(" AND OA.STAT_DATE >= TO_DATE(?, 'yyyy-MM-dd') AND OA.STAT_DATE<= TO_DATE(?, 'yyyy-MM-dd')\n");
			sb.append("  AND O.ORG_NO IN ("+addString+") ORDER BY OA.STAT_DATE");
		}
		else
		{

			if(ValueArray.length == 1){
				 addString = "?";
				 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0]};
			}else if(ValueArray.length == 2){
				 addString = "?,?";
				 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0],ValueArray[1]};
			}else if(ValueArray.length == 3){
				 addString = "?,?,?";
				 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2]};
			}else if(ValueArray.length == 4){
				 addString = "?,?,?,?";
				 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2],ValueArray[3]};
			}
				
		}
        if(ValueCombo.equals("2")){

    		sb.append("select acgs.cap_grade_no,vcg.cap_grade_name,acgs.pap_e,acgs.pap_e1,acgs.pap_e2,acgs.pap_e3,acgs.pap_e4,acgs.stat_date\n");
    		sb.append(" from a_cap_grade_stat_d acgs,vw_cap_grade vcg WHERE vcg.cap_grade_no=acgs.cap_grade_no \n");
    		sb.append("and acgs.org_no = ? and acgs.org_type = ?");
    		sb.append(" AND acgs.stat_date >= TO_DATE(?, 'yyyy-MM-dd') AND acgs.stat_date<= TO_DATE(?, 'yyyy-MM-dd')\n");
    		sb.append("AND vcg.cap_grade_no IN ("+addString+") ORDER BY acgs.stat_date");
            
        }
        else if(ValueCombo.equals("3")){

    		sb.append("select avsd.volt_code,vvc.VOLT,avsd.pap_e,avsd.pap_e1,avsd.pap_e2,avsd.pap_e3,avsd.pap_e4,avsd.stat_date\n");
    		sb.append("from a_volt_stat_d avsd,vw_volt_code vvc WHERE avsd.volt_code = vvc.VOLT_CODE \n");
    		sb.append("and avsd.org_no = ? and avsd.org_type = ?");
    		sb.append(" and avsd.stat_date >= TO_DATE(?, 'yyyy-MM-dd') AND avsd.stat_date<= TO_DATE(?, 'yyyy-MM-dd')\n");
    		sb.append("AND vvc.VOLT_CODE IN ("+addString+") ORDER BY avsd.stat_date");
            
        }
        else if(ValueCombo.equals("4")){

    		sb.append("select atsd.trade_code,vt.TRADE_NAME,atsd.pap_e,atsd.pap_e1,atsd.pap_e2,atsd.pap_e3,atsd.pap_e4,atsd.stat_date\n");
    		sb.append(" from a_trade_stat_d atsd,vw_trade vt WHERE atsd.trade_code = vt.TRADE_NO \n");
    		sb.append(" and atsd.org_no = ? and atsd.org_type = ?");
    		sb.append("and atsd.stat_date >= TO_DATE(?, 'yyyy-MM-dd') AND atsd.stat_date<= TO_DATE(?, 'yyyy-MM-dd')\n");
    		sb.append("AND atsd.trade_code IN ("+addString+") ORDER BY atsd.stat_date");
            
        }
		logger.debug(sb.toString());
		return (List<ElecContrastAnalysisOrgNo>) super.getJdbcTemplate().query(sb.toString(),
				prams,	 new ElecContrastAnalysisOrgNoRowMapper());
	}

	public List<ChargeContrastAnalysisBean> getChargeContrastAnalysisOrgNo(String DateStart,String DateEnd,String ValueCombo,String[] ValueArray,String OrgNo,String OrgType)
	throws DBAccessException {
StringBuffer sb=new StringBuffer();
String addString = "";
Object[] prams =new Object[]{};
if(ValueCombo.equals("1")){
	
    if(ValueArray.length == 1){
	     addString = "?";
	     prams = new Object[]{DateStart,DateEnd,ValueArray[0]};
    }else if(ValueArray.length == 2){
	     addString = "?,?";
	     prams = new Object[]{DateStart,DateEnd,ValueArray[0],ValueArray[1]};
    }else if(ValueArray.length == 3){
	     addString = "?,?,?";
	     prams = new Object[]{DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2]};
    }else if(ValueArray.length == 4){
	     addString = "?,?,?,?";
	     prams = new Object[]{DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2],ValueArray[3]};
    }
    sb.append("select OA.ORG_NO,O.ORG_NAME,OA.AVG_P,OA.STAT_DATE\n");
	sb.append(" from A_ORG_STAT_D OA,O_ORG O WHERE O.ORG_NO = OA.ORG_NO \n");
	sb.append(" AND OA.STAT_DATE >= TO_DATE(?, 'yyyy-MM-dd') AND OA.STAT_DATE<= TO_DATE(?, 'yyyy-MM-dd')\n");
	sb.append("  AND O.ORG_NO IN ("+addString+") ORDER BY OA.STAT_DATE");
}
else
{

	if(ValueArray.length == 1){
		 addString = "?";
		 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0]};
	}else if(ValueArray.length == 2){
		 addString = "?,?";
		 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0],ValueArray[1]};
	}else if(ValueArray.length == 3){
		 addString = "?,?,?";
		 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2]};
	}else if(ValueArray.length == 4){
		 addString = "?,?,?,?";
		 prams = new Object[]{OrgNo,OrgType,DateStart,DateEnd,ValueArray[0],ValueArray[1],ValueArray[2],ValueArray[3]};
	}
		
}
if(ValueCombo.equals("2")){

	sb.append("select acgs.cap_grade_no,vcg.cap_grade_name,acgs.avg_p,acgs.stat_date\n");
	sb.append(" from a_cap_grade_stat_d acgs,vw_cap_grade vcg WHERE vcg.cap_grade_no=acgs.cap_grade_no \n");
	sb.append("and acgs.org_no = ? and acgs.org_type = ?");
	sb.append(" AND acgs.stat_date >= TO_DATE(?, 'yyyy-MM-dd') AND acgs.stat_date<= TO_DATE(?, 'yyyy-MM-dd')\n");
	sb.append("AND vcg.cap_grade_no IN ("+addString+") ORDER BY acgs.stat_date");
    
}
else if(ValueCombo.equals("3")){

	sb.append("select avsd.volt_code,vvc.VOLT,avsd.avg_p,avsd.stat_date\n");
	sb.append("from a_volt_stat_d avsd,vw_volt_code vvc WHERE avsd.volt_code = vvc.VOLT_CODE \n");
	sb.append("and avsd.org_no = ? and avsd.org_type = ?");
	sb.append(" and avsd.stat_date >= TO_DATE(?, 'yyyy-MM-dd') AND avsd.stat_date<= TO_DATE(?, 'yyyy-MM-dd')\n");
	sb.append("AND vvc.VOLT_CODE IN ("+addString+") ORDER BY avsd.stat_date");
    
}
else if(ValueCombo.equals("4")){

	sb.append("select atsd.trade_code,vt.TRADE_NAME,atsd.avg_p,atsd.stat_date\n");
	sb.append(" from a_trade_stat_d atsd,vw_trade vt WHERE atsd.trade_code = vt.TRADE_NO \n");
	sb.append(" and atsd.org_no = ? and atsd.org_type = ?");
	sb.append("and atsd.stat_date >= TO_DATE(?, 'yyyy-MM-dd') AND atsd.stat_date<= TO_DATE(?, 'yyyy-MM-dd')\n");
	sb.append("AND atsd.trade_code IN ("+addString+") ORDER BY atsd.stat_date");
    
}
logger.debug(sb.toString());
return (List<ChargeContrastAnalysisBean>) super.getJdbcTemplate().query(sb.toString(),
		prams,	 new ChargeContrastAnalysisOrgNoRowMapper());
}
	
	@Override
	public List<ElecContrastAnalysisType> getElecContrastAnalysiscapGrade()
			throws DBAccessException {
		Object[] prams =new Object[]{};
		StringBuffer sb=new StringBuffer();
		sb.append("select * from vw_cap_grade");

		String sql=sb.toString();

		logger.debug(sb.toString());
		return (List<ElecContrastAnalysisType>) super.getJdbcTemplate().query(sb.toString(),
				prams, new ElecContrastAnalysisTypeRowMapper());
	
	}
	
	public List<ElecContrastAnalysisType> getElecContrastAnalysiscapGradeVoltage()throws DBAccessException{
		Object[] prams = new Object[]{};
		StringBuffer sb = new StringBuffer();
		sb.append("select * from vw_volt_code");
		String sql = sb.toString();
		logger.debug(sql);
		return (List<ElecContrastAnalysisType>)super.getJdbcTemplate().query(sql,prams,new ElecContrastAnalysisTypeRowMapperVoltage());
	}
	
	public Page<ElecContrastAnalysisType> findPage(ElecContrastAnalysisType elecContrastAnalysisType,long start, long limit) throws DBAccessException{
		StringBuffer sb = new StringBuffer();
		sb.append("select * from vw_trade");
		return this.pagingFind(sb.toString(), start, limit, new ElecContrastAnalysisTypeRowMapperBuss());
	}

}



class ElecContrastAnalysisOrgNoRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	ElecContrastAnalysisOrgNo eleccontrastanalysisorgno = new ElecContrastAnalysisOrgNo();
	 try { 
	eleccontrastanalysisorgno.setOrgNo(rs.getString(1));
	eleccontrastanalysisorgno.setOrgName(rs.getString(2));
	eleccontrastanalysisorgno.setPapE(rs.getDouble(3));
	eleccontrastanalysisorgno.setPapE1(rs.getDouble(4));
	eleccontrastanalysisorgno.setPapE2(rs.getDouble(5));
	eleccontrastanalysisorgno.setPapE3(rs.getDouble(6));
	eleccontrastanalysisorgno.setPapE4(rs.getDouble(7));
	eleccontrastanalysisorgno.setStatDate(rs.getDate(8));
	return eleccontrastanalysisorgno;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}

class ChargeContrastAnalysisOrgNoRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		 ChargeContrastAnalysisBean chargeContrastAnalysisList = new ChargeContrastAnalysisBean();
	 try { 
		 chargeContrastAnalysisList.setOrgNo(rs.getString(1));
		 chargeContrastAnalysisList.setOrgName(rs.getString(2));
		 chargeContrastAnalysisList.setAvgP(rs.getDouble(3));
		 chargeContrastAnalysisList.setStatDate(rs.getDate(4));
	return chargeContrastAnalysisList;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}
class ElecContrastAnalysisTypeRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		 ElecContrastAnalysisType eleccontrastanalysisType = new ElecContrastAnalysisType();
	 try { 
		 eleccontrastanalysisType.setCapGradeName(rs.getString("cap_grade_name"));
		 eleccontrastanalysisType.setCapGradeNo(rs.getString("cap_grade_no"));
	return eleccontrastanalysisType;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}

class ElecContrastAnalysisTypeRowMapperVoltage implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		 ElecContrastAnalysisType eleccontrastanalysisType = new ElecContrastAnalysisType();
	 try { 
		 eleccontrastanalysisType.setCapGradeName(rs.getString("volt"));
		 eleccontrastanalysisType.setCapGradeNo(rs.getString("volt_code"));
	return eleccontrastanalysisType;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}

class ElecContrastAnalysisTypeRowMapperBuss implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
		 ElecContrastAnalysisType eleccontrastanalysisType = new ElecContrastAnalysisType();
	 try { 
		 eleccontrastanalysisType.setCapGradeName(rs.getString("trade_name"));
		 eleccontrastanalysisType.setCapGradeNo(rs.getString("trade_no"));
	return eleccontrastanalysisType;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}