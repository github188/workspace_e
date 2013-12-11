package com.nari.baseapp.prepaidman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.prepaidman.IUseValueQueryDao;
import com.nari.baseapp.prepaidman.UseValueResult;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class UseValueQueryDaoImpl extends JdbcBaseDAOImpl implements IUseValueQueryDao {
/*    *//**
     * 用户余额查询
     * @param orgName
     * @param consNo
     * @param appNo
     * @param terminalAddr
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     * @throws DBAccessException
     *//*
	@SuppressWarnings("unchecked")
	public Page<UseValueResult> valueQueryBy(String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException{
	  List<Object> list = new ArrayList<Object>();
	  StringBuffer sb= new StringBuffer();
	  sb.append("SELECT O.ORG_NO,\n")
	    .append("       O.ORG_NAME,\n")
	    .append("       C.CONS_NO,\n")
	    .append("       C.CONS_NAME,\n")
		.append("       WC.BUY_ORDER,\n")
		.append("       WC.BUY_FLAG,\n")
		.append("       R.TERMINAL_ID,\n")
		.append("       R.TMNL_ASSET_NO,\n")
		.append("       R.TERMINAL_ADDR,\n")
		.append("       WC.TOTAL_NO,\n")
		.append(" 		D.METER_ID,\n")
		.append(" 		D.ASSET_NO,\n")
		.append("		WC.BUY_DATE,\n")
		.append("		WC.BUY_VALUE,\n")
		.append("		WC.BUY_VALUE_UNIT,\n")
		.append("		WC.SEND_TIME,\n")
		.append("		WV.USE_VALUE,\n")
		.append("		WV.OP_TIME\n")
	  	.append("FROM W_FEE_USEVALUE WV, R_TMNL_RUN R, D_METER D, W_FEECTRL WC, O_ORG O,C_CONS C\n")  
	    .append("WHERE WV.USE_VALUE IS NOT NULL\n")
	  	.append("      AND O.ORG_NO=WC.ORG_NO\n")
  	 	.append("      AND C.CONS_NO=WC.CONS_NO\n")
	    .append("      AND R.TMNL_ASSET_NO = WC.TMNL_ASSET_NO\n")
	    .append("      AND D.METER_ID(+) = WC.METER_ID\n")
	    .append("      AND WC.BUY_ORDER = WV.APP_NO\n") 
	    .append("      AND WC.CTRL_FLAG = 1\n")
	    .append("      AND WC.BUY_DATE >= TO_DATE(?, 'YYYY-mm-dd')\n")
        .append("      AND WC.BUY_DATE < TO_DATE(?, 'YYYY-mm-dd')+1");
	  list.add(startDate);
	  list.add(endDate); 
	  if(null!=orgName&&!"".equals(orgName)){
		   sb.append("\n AND WC.ORG_NO = O.ORG_NO")
		   		   .append("\n AND O.ORG_NAME LIKE '%'||?||'%'");
		   list.add(orgName);
	   }
	   if(null!=consNo&&!"".equals(consNo)){
		   sb.append("\n AND WC.CONS_NO LIKE '%'||?||'%'");
		   list.add(consNo);
	   }
	   if(null!=appNo&&!"".equals(appNo)){
		   sb.append("\n AND WC.BUY_ORDER LIKE '%'||?||'%'");
		   list.add(appNo);
	   }
	   if(null!=terminalAddr&&!"".equals(terminalAddr)){
		   sb.append("\n AND R.TERMINAL_ADDR LIKE '%'||?||'%'");
		   list.add(terminalAddr);
	   }
	   try{
		    Page<UseValueResult> resultPage= new  Page<UseValueResult>();
		    List<UseValueResult> resultList= this.getJdbcTemplate().query(sb.toString(),list.toArray(),new UseValueRowMapper());
	      	if(null==resultList||resultList.size()==0)
	      		return resultPage;
		    //对查询结果按照keyId已经召测日期排序
	      	Collections.sort(resultList, new Comparator<UseValueResult>(){
					@Override
					public int compare(UseValueResult o1,
							UseValueResult o2) {
						if(o1.getKeyId().equals(o2.getKeyId()))
							return o2.getOpTime().compareTo(o1.getOpTime());
						return o1.getKeyId().compareTo(o2.getKeyId());
					}
      	    });
	      	//筛选出每个总加组最近一次召测日期的记录
		    List<UseValueResult> resultListByRj=new ArrayList<UseValueResult>();
		    UseValueResult flagBean=resultList.get(0);
		    resultListByRj.add(flagBean);
		    for(int i=1;i<resultList.size();i++){
		    	if(!resultList.get(i).getKeyId().equals(flagBean.getKeyId())){
		    		flagBean=resultList.get(i);
		    		resultListByRj.add(flagBean);
		    	}
		    }
		    //将筛选出来的记录放入Page
		    List<UseValueResult> resultPageList=new ArrayList<UseValueResult>();
	        if(start+limit-1<resultListByRj.size()){
	        	for(long j=start;j<start+limit;j++){
	        		resultPageList.add(resultListByRj.get((int)j));
	        	}
	        }
	        else{
	        	for(long k=start;k<resultListByRj.size();k++){
	        		resultPageList.add(resultListByRj.get((int)k));
	        	}
	        }
	        resultPage.setResult(resultPageList);
	        resultPage.setTotalCount(resultListByRj.size());
	        return resultPage;
	   }catch(RuntimeException e){
			this.logger.debug("查询用户余额出错！");
			throw e;
	   }
	}*/
	
	/**
     * 用户余额查询
     * @param orgName
     * @param consNo
     * @param appNo
     * @param terminalAddr
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     * @throws DBAccessException
     */
	@SuppressWarnings("unchecked")
	public Page<UseValueResult> valueQueryBy(String orgNo,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException{
	  List<Object> list = new ArrayList<Object>();
	  StringBuffer sb= new StringBuffer();
	  sb.append("SELECT O.ORG_NO,\n")
	    .append("       O.ORG_NAME,\n")
	    .append("       C.CONS_NO,\n")
	    .append("       C.CONS_NAME,\n")
		.append("       WC.BUY_ORDER,\n")
		.append("       WC.BUY_FLAG,\n")
		.append("       R.TERMINAL_ID,\n")
		.append("       R.TMNL_ASSET_NO,\n")
		.append("       R.TERMINAL_ADDR,\n")
		.append("       WC.TOTAL_NO,\n")
		.append(" 		D.METER_ID,\n")
		.append(" 		D.ASSET_NO,\n")
		.append("		WC.BUY_DATE,\n")
		.append("		WC.BUY_VALUE,\n")
		.append("		WC.BUY_VALUE_UNIT,\n")
		.append("		WC.SEND_TIME,\n")
		.append("		WV.USE_VALUE,\n")
		.append("		WV.OP_TIME\n")
	  	.append("FROM W_FEE_USEVALUE WV, R_TMNL_RUN R, C_METER D, W_FEECTRL WC, O_ORG O,C_CONS C\n")  
	    .append("WHERE  O.ORG_NO=WC.ORG_NO\n")
  	 	.append("      AND C.CONS_NO=WC.CONS_NO\n")
	    .append("      AND R.TMNL_ASSET_NO = WC.TMNL_ASSET_NO\n")
	    .append("      AND D.METER_ID = WC.METER_ID\n")
	    .append("      AND WC.BUY_ORDER = WV.APP_NO(+)\n") 
	    .append("      AND WC.TMNL_ASSET_NO = WV.TERMINAL_ID(+)\n") 
	    //.append("      AND WC.BUY_DATE >= TO_DATE(?, 'YYYY-mm-dd')\n")
        // .append("      AND WC.BUY_DATE < TO_DATE(?, 'YYYY-mm-dd')+1");
	    // list.add(startDate);
	    // list.add(endDate); 
	  /* if(null!=orgName&&!"".equals(orgName)){
		   sb.append("\n AND WC.ORG_NO = O.ORG_NO")
		   		   .append("\n AND O.ORG_NAME LIKE '%'||?||'%'");
		   list.add(orgName);
	   }*/
	    .append("\n AND WC.ORG_NO = ?");
       list.add(orgNo);
	   if(null!=consNo&&!"".equals(consNo)){
		   sb.append("\n AND WC.CONS_NO LIKE '%'||?||'%'");
		   list.add(consNo);
	   }
	   if(null!=appNo&&!"".equals(appNo)){
		   sb.append("\n AND WC.BUY_ORDER LIKE '%'||?||'%'");
		   list.add(appNo);
	   }
	   if(null!=terminalAddr&&!"".equals(terminalAddr)){
		   sb.append("\n AND R.TERMINAL_ADDR LIKE '%'||?||'%'");
		   list.add(terminalAddr);
	   }
	   try{
		    //System.out.println(sb.toString());
		    Page<UseValueResult> resultPage= new  Page<UseValueResult>();
		    List<UseValueResult> resultList= this.getJdbcTemplate().query(sb.toString(),list.toArray(),new UseValueRowMapper());
	      	if(null==resultList||resultList.size()==0)
	      		return resultPage;
		    //对查询结果按照keyId已经召测日期排序
	      	Collections.sort(resultList, new Comparator<UseValueResult>(){
					@Override
					public int compare(UseValueResult o1,
							UseValueResult o2) {
						if(o1.getKeyId().equals(o2.getKeyId()))
							return o2.getOpTime().compareTo(o1.getOpTime());
						return o1.getKeyId().compareTo(o2.getKeyId());
					}
      	    });
	      	//筛选出每个总加组最近一次召测日期的记录
		    List<UseValueResult> resultListByRj=new ArrayList<UseValueResult>();
		    UseValueResult flagBean=resultList.get(0);
		    resultListByRj.add(flagBean);
		    for(int i=1;i<resultList.size();i++){
		    	if(!resultList.get(i).getKeyId().equals(flagBean.getKeyId())){
		    		flagBean=resultList.get(i);
		    		resultListByRj.add(flagBean);
		    	}
		    }
		    //将筛选出来的记录放入Page
		    List<UseValueResult> resultPageList=new ArrayList<UseValueResult>();
	        if(start+limit-1<resultListByRj.size()){
	        	for(long j=start;j<start+limit;j++){
	        		resultPageList.add(resultListByRj.get((int)j));
	        	}
	        }
	        else{
	        	for(long k=start;k<resultListByRj.size();k++){
	        		resultPageList.add(resultListByRj.get((int)k));
	        	}
	        }
	        resultPage.setResult(resultPageList);
	        resultPage.setTotalCount(resultListByRj.size());
	        return resultPage;
	   }catch(RuntimeException e){
			this.logger.debug("查询用户余额出错！");
			throw e;
	   }
	}
	/**
	 * 剩余值明细查询
	 * @param tmnlAssetNo
	 * @param totalNo
	 * @param orgName
	 * @param consNo
	 * @param appNo
	 * @param terminalAddr
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 *//*
	public Page<UseValueResult> valueDetailQueryBy(String tmnlAssetNo,Short totalNo,String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException{
	  List<Object> list = new ArrayList<Object>();
	  StringBuffer sb= new StringBuffer();
	  sb.append("SELECT O.ORG_NO,\n")
	    .append("       O.ORG_NAME,\n")
	    .append("       C.CONS_NO,\n")
	    .append("       C.CONS_NAME,\n")
		.append("       WC.BUY_ORDER,\n")
		.append("       WC.BUY_FLAG,\n")
		.append("       R.TERMINAL_ID,\n")
		.append("       R.TMNL_ASSET_NO,\n")
		.append("       R.TERMINAL_ADDR,\n")
		.append("       WC.TOTAL_NO,\n")
		.append(" 		D.METER_ID,\n")
		.append(" 		D.ASSET_NO,\n")
		.append("		WC.BUY_DATE,\n")
		.append("		WC.BUY_VALUE,\n")
		.append("		WC.BUY_VALUE_UNIT,\n")
		.append("		WC.SEND_TIME,\n")
		.append("		WV.USE_VALUE,\n")
		.append("		WV.OP_TIME\n")
	  	.append("FROM W_FEE_USEVALUE WV, R_TMNL_RUN R, D_METER D, W_FEECTRL WC, O_ORG O,C_CONS C\n")  
	    .append("WHERE WV.USE_VALUE IS NOT NULL\n")
	  	.append("      AND O.ORG_NO=WC.ORG_NO\n")
	  	.append("      AND C.CONS_NO=WC.CONS_NO\n")
	    .append("      AND R.TMNL_ASSET_NO = WC.TMNL_ASSET_NO\n")
	    .append("      AND D.METER_ID(+) = WC.METER_ID\n")
	    .append("      AND WC.BUY_ORDER = WV.APP_NO\n") 
	   //.append("      AND WC.CTRL_FLAG = 1\n")
	    .append("      AND WC.TMNL_ASSET_NO =?\n")
	    .append("      AND WC.TOTAL_NO =?\n")
	    .append("      AND WC.BUY_DATE >= TO_DATE(?, 'YYYY-mm-dd')\n")
        .append("      AND WC.BUY_DATE < TO_DATE(?, 'YYYY-mm-dd')+1");  
	  list.add(tmnlAssetNo);
	  list.add(totalNo); 
	  list.add(startDate);
	  list.add(endDate); 
	  if(null!=orgName&&!"".equals(orgName)){
		   sb.append("\n AND WC.ORG_NO = O.ORG_NO")
		   		   .append("\n AND O.ORG_NAME LIKE '%'||?||'%'");
		   list.add(orgName);
	   }
	   if(null!=consNo&&!"".equals(consNo)){
		   sb.append("\n AND WC.CONS_NO LIKE '%'||?||'%'");
		   list.add(consNo);
	   }
	   if(null!=appNo&&!"".equals(appNo)){
		   sb.append("\n AND WC.BUY_ORDER LIKE '%'||?||'%'");
		   list.add(appNo);
	   }
	   if(null!=terminalAddr&&!"".equals(terminalAddr)){
		   sb.append("\n AND R.TERMINAL_ADDR LIKE '%'||?||'%'");
		   list.add(terminalAddr);
	   }
	   sb.append("\n   ORDER BY WV.OP_TIME DESC "); 
	   try{
			return super.pagingFind(sb.toString(),start,limit,new UseValueRowMapper(),list.toArray());
	   }catch(RuntimeException e){
			this.logger.debug("查询剩余值明细出错！");
			throw e;
	   }  
	}*/
	
	/**
	 * 剩余值明细查询
	 * @param tmnlAssetNo
	 * @param totalNo
	 * @param orgName
	 * @param consNo
	 * @param appNo
	 * @param terminalAddr
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	public Page<UseValueResult> valueDetailQueryBy(String tmnlAssetNo,Short totalNo,String orgNo,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException{
	  List<Object> list = new ArrayList<Object>();
	  StringBuffer sb= new StringBuffer();
	  sb.append("SELECT O.ORG_NO,\n")
	    .append("       O.ORG_NAME,\n")
	    .append("       C.CONS_NO,\n")
	    .append("       C.CONS_NAME,\n")
		.append("       WC.BUY_ORDER,\n")
		.append("       WC.BUY_FLAG,\n")
		.append("       R.TERMINAL_ID,\n")
		.append("       R.TMNL_ASSET_NO,\n")
		.append("       R.TERMINAL_ADDR,\n")
		.append("       WC.TOTAL_NO,\n")
		.append(" 		D.METER_ID,\n")
		.append(" 		D.ASSET_NO,\n")
		.append("		WC.BUY_DATE,\n")
		.append("		WC.BUY_VALUE,\n")
		.append("		WC.BUY_VALUE_UNIT,\n")
		.append("		WC.SEND_TIME,\n")
		.append("		WV.USE_VALUE,\n")
		.append("		WV.OP_TIME\n")
	  	.append("FROM W_FEE_USEVALUE WV, R_TMNL_RUN R, C_METER D, W_FEECTRL WC, O_ORG O,C_CONS C\n")  
	    .append("WHERE O.ORG_NO=WC.ORG_NO\n")
	  	.append("      AND C.CONS_NO=WC.CONS_NO\n")
	    .append("      AND R.TMNL_ASSET_NO = WC.TMNL_ASSET_NO\n")
	    .append("      AND D.METER_ID = WC.METER_ID\n")
	    .append("      AND WC.BUY_ORDER = WV.APP_NO\n") 
	    .append("      AND R.TERMINAL_ID = WV.TERMINAL_ID\n") 
	   //.append("      AND WC.CTRL_FLAG = 1\n")
	    .append("      AND WC.TMNL_ASSET_NO =?\n")
	    .append("      AND WC.TOTAL_NO =?\n")
	    //.append("      AND WC.BUY_DATE >= TO_DATE(?, 'YYYY-mm-dd')\n")
        //.append("      AND WC.BUY_DATE < TO_DATE(?, 'YYYY-mm-dd')+1"); 
	     .append("\n   AND WC.ORG_NO = ?");
       
	  list.add(tmnlAssetNo);
	  list.add(totalNo); 
	  list.add(orgNo);
	  //list.add(startDate);
	  //list.add(endDate); 
	  /* if(null!=orgName&&!"".equals(orgName)){
		   sb.append("\n AND WC.ORG_NO = O.ORG_NO")
		   		   .append("\n AND O.ORG_NAME LIKE '%'||?||'%'");
		   list.add(orgName);
	   }*/
	   if(null!=consNo&&!"".equals(consNo)){
		   sb.append("\n AND WC.CONS_NO LIKE '%'||?||'%'");
		   list.add(consNo);
	   }
	   if(null!=appNo&&!"".equals(appNo)){
		   sb.append("\n AND WC.BUY_ORDER LIKE '%'||?||'%'");
		   list.add(appNo);
	   }
	   if(null!=terminalAddr&&!"".equals(terminalAddr)){
		   sb.append("\n AND R.TERMINAL_ADDR LIKE '%'||?||'%'");
		   list.add(terminalAddr);
	   }
	   sb.append("\n   ORDER BY WV.OP_TIME DESC "); 
	   try{
			return super.pagingFind(sb.toString(),start,limit,new UseValueRowMapper(),list.toArray());
	   }catch(RuntimeException e){
			this.logger.debug("查询剩余值明细出错！");
			throw e;
	   }  
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public List<UseValueResult> valueListQueryBy(String orgName, String consNo,
			String appNo, String terminalAddr, String startDate, String endDate)
			throws DBAccessException {
		  List<Object> list = new ArrayList<Object>();
		  StringBuffer sb= new StringBuffer();
		  sb.append("SELECT O.ORG_NO,\n")
		    .append("       O.ORG_NAME,\n")
		    .append("       WV.CONS_NO,\n")
			.append("       WV.APP_NO,\n")
			.append("       R.TERMINAL_ID,\n")
			.append("       R.TMNL_ASSET_NO,\n")
			.append("       R.TERMINAL_ADDR,\n")
			.append(" 		D.METER_ID,\n")
			.append(" 		D.ASSET_NO,\n")
			.append("		WC.BUY_DATE,\n")
			.append("		WC.BUY_VALUE,\n")
			.append("		WC.SUCCESS_TIME,\n")
			.append("		WV.USE_VALUE,\n")
			.append("		WV.OP_TIME\n");
		   String fromsql="FROM W_FEE_USEVALUE WV, R_TMNL_RUN R, D_METER D, W_FEECTRL WC, O_ORG O";  
		   StringBuffer wheresql= new StringBuffer();
		   wheresql.append("\n   WHERE O.ORG_NO=WV.ORG_NO")
				   .append("\n       AND R.TERMINAL_ID = WV.TERMINAL_ID")
				   .append("\n       AND D.METER_ID(+) = WV.METER_ID")
				   .append("\n       AND WC.BUY_ORDER = WV.APP_NO") 
		   		   .append("\n       AND WC.CTRL_FLAG = 1");  
		   if(null!=orgName&&!"".equals(orgName)){
			   wheresql.append("\n AND WV.ORG_NO = O.ORG_NO")
			   		   .append("\n AND O.ORG_NAME LIKE '%'||?||'%'");
			   list.add(orgName);
		   }
		   if(null!=consNo&&!"".equals(consNo)){
			   wheresql.append("\n AND WV.CONS_NO LIKE '%'||?||'%'");
			   list.add(consNo);
		   }
		   if(null!=appNo&&!"".equals(appNo)){
			   wheresql.append("\n AND WV.APP_NO LIKE '%'||?||'%'");
			   list.add(appNo);
		   }
		   if(null!=terminalAddr&&!"".equals(terminalAddr)){
			   wheresql.append("\n AND R.TERMINAL_ADDR LIKE '%'||?||'%'");
			   list.add(terminalAddr);
		   }
		   if(startDate!=null&&!"".equals(startDate)&&endDate!=null&&!"".equals(endDate)){
			   wheresql.append("\n AND WC.BUY_DATE  BETWEEN TO_DATE(?, 'YYYY-mm-dd') AND TO_DATE(?, 'YYYY-mm-dd')");
			   list.add(startDate);
			   list.add(endDate);
		   }
		   try{
				return this.getJdbcTemplate().query(sb.toString()+fromsql+wheresql.toString(),list.toArray(),new UseValueRowMapper());
		   }catch(RuntimeException e){
				this.logger.debug("查询剩余电量出错！");
				throw e;
		   }
	}*/

	/**
	 * 获取终端Id
	 * @param TmnlAssetNo
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<String> findTerminalId(String tmnlAssetNo) throws DBAccessException {
        String sql="SELECT R.TERMINAL_ID FROM R_TMNL_RUN R WHERE R.TMNL_ASSET_NO =?";
        try{
        	return this.getJdbcTemplate().query(sql,new Object[]{tmnlAssetNo},new TerminalIdRowMapper());
        }catch(RuntimeException e){
			this.logger.debug("查询终端Id出错！");
			throw e;
	   }
	}
	
	/**
	 * 添加剩余值召测信息
	 * @param orgNo
	 * @param appNo
	 * @param consNo
	 * @param terminalId
	 * @param totalNo
	 * @param meterId
	 * @param UseValue
	 * @param statusCode
	 * @param opTime
	 * @param errCause
	 * @throws DBAccessException
	 */
	public void addUseValue(String orgNo, String appNo, String consNo, String terminalId,Short totalNo,
			String meterId, String UseValue, short statusCode, Date opTime,
			String errCause) throws DBAccessException {
		try{	 
			StringBuffer sb= new StringBuffer();
			sb.append("INSERT INTO W_FEE_USEVALUE\n")
			  .append("  (ORG_NO,\n") 
			  .append("   APP_NO,\n") 
		      .append("   CONS_NO,\n") 
			  .append("   TERMINAL_ID,\n") 
			  .append("   TOTAL_NO,\n") 
			  .append("   METER_ID,\n") 
			  .append("   USE_VALUE,\n") 
			  .append("   STATUS_CODE,\n") 
			  .append("   OP_TIME,\n") 
			  .append("   ERR_CAUSE)\n") 
			  .append("VALUES\n") 
			  .append("  (?, ?, ?, ?, ?, ?, ?, ?, SYSDATE, ?)");
			  this.getJdbcTemplate().update(sb.toString(),
						new Object[] {orgNo, appNo, consNo, terminalId, totalNo, meterId, UseValue,
								statusCode, errCause });
		}catch(RuntimeException e){
			this.logger.debug("更新剩余值召测表！");
			throw e;
		}
	}
	
	
	class UseValueRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			UseValueResult useValueResult=new UseValueResult();
			try {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				useValueResult.setKeyId(rs.getString("TMNL_ASSET_NO")+rs.getString("TOTAL_NO"));
				useValueResult.setOrgNo(rs.getString("ORG_NO"));
				useValueResult.setOrgName(rs.getString("ORG_NAME"));
				useValueResult.setConsNo(rs.getString("CONS_NO"));
				useValueResult.setConsName(rs.getString("CONS_NAME"));
				useValueResult.setAppNo(rs.getString("BUY_ORDER"));
				useValueResult.setBuyFlag(rs.getString("BUY_FLAG"));
				useValueResult.setTerminalId(rs.getString("TERMINAL_ID"));
				useValueResult.setTmnlAssetNo(rs.getString("TMNL_ASSET_NO"));
				useValueResult.setTerminalAddr(rs.getString("TERMINAL_ADDR"));
				if(null!=rs.getString("TOTAL_NO")&&!"".equals(rs.getString("TOTAL_NO")))
					useValueResult.setTotalNo(rs.getShort("TOTAL_NO"));
				useValueResult.setMeterId(rs.getString("METER_ID"));
				useValueResult.setMeterAssetNo(rs.getString("ASSET_NO"));
				if(null!=rs.getString("BUY_DATE")&&!"".equals(rs.getString("BUY_DATE")))
					useValueResult.setBuyDate(sdf1.format(rs.getDate("BUY_DATE")));
				useValueResult.setBuyValue(rs.getString("BUY_VALUE"));
				useValueResult.setBuyValueUnit(rs.getString("BUY_VALUE_UNIT"));
				if(null!=rs.getString("SEND_TIME")&&!"".equals(rs.getString("SEND_TIME")))
					useValueResult.setExecuteDate(sdf1.format(rs.getDate("SEND_TIME")));
				useValueResult.setUseValue(rs.getString("USE_VALUE"));
				if(null!=rs.getString("OP_TIME")&&!"".equals(rs.getString("OP_TIME")))
					useValueResult.setOpTime(sdf2.format(rs.getTimestamp("OP_TIME")));
				return useValueResult;
			}catch (Exception e) {
				return null;
			}
		}
	}
	
	class TerminalIdRowMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try {
			   return rs.getString("TERMINAL_ID");
			}catch (Exception e) {
				return null;
			}
		}
	}
	
}
