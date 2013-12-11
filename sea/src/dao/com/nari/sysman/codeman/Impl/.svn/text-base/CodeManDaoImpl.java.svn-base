package com.nari.sysman.codeman.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.support.Page;
import com.nari.sysman.codeman.CodeManIn;
import com.nari.sysman.codeman.CodeManOut;
import com.nari.sysman.codeman.CodeManOutSub;
import com.nari.sysman.codeman.ICodeManDao;

public class CodeManDaoImpl extends JdbcBaseDAOImpl implements ICodeManDao{
	Logger logger = Logger.getLogger(CodeManDaoImpl.class);
	@Override
	public Page<CodeManOut> queryCodeManOut(long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		sql.append("select S.CODE_SORT_ID,S.NAME from p_code_sort S");
		logger.debug(sql.toString());
		return super.pagingFind(sql.toString(), start, limit,
				new CodeManOutRowMapper(), parms.toArray());
	}

	@Override
	public Page<CodeManOutSub> queryCodeManOutSub(String sortId, long start,
			int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		parms.add(sortId);
		sql.append("select C.VALUE,C.NAME from p_code C WHERE C.CODE_SORT_ID = ?");
		logger.debug(sql.toString());
		return super.pagingFind(sql.toString(), start, limit,
				new CodeManOutSubRowMapper(), parms.toArray());
	}

	@Override
	public Page<CodeManIn> queryCodeManIn(long start, int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		sql.append("select distinct d.dict_catalog,d.dict_catalog_name from b_sys_dictionary D order by dict_catalog");
		logger.debug(sql.toString());
		return super.pagingFind(sql.toString(), start, limit,
				new CodeManInRowMapper(), parms.toArray());
	}

	@Override
	public Page<CodeManIn> queryCodeManInSub(String sortId, long start,
			int limit) {
		StringBuffer sql = new StringBuffer();
		List<Object> parms = new ArrayList<Object>();
		parms.add(sortId);
		sql.append("select D.DICT_NO,D.DICT_NAME from b_sys_dictionary D WHERE dict_catalog = ?");
		logger.debug(sql.toString());
		return super.pagingFind(sql.toString(), start, limit,
				new CodeManInSubRowMapper(), parms.toArray());
	}

	@Override
	public void CodeManSet(String sql) {
		logger.debug(sql.toString());
		try{
			this.getJdbcTemplate().update(sql,new Object[]{});
		}catch(RuntimeException e){
			this.logger.debug("编码管理内部数据操作失败！");
			throw e;
		}
	}
	
	
	 
}
class CodeManOutRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	CodeManOut codemanout = new CodeManOut();
	 try { 
	codemanout.setCodeSortId(rs.getLong("CODE_SORT_ID"));
	codemanout.setName(rs.getString("NAME"));
	return codemanout;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}

class CodeManOutSubRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	CodeManOutSub codemanoutsub = new CodeManOutSub();
	 try { 
	codemanoutsub.setValue(rs.getString("VALUE"));
	codemanoutsub.setName(rs.getString("NAME"));
	return codemanoutsub;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}

class CodeManInRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	CodeManIn codemanin = new CodeManIn();
	 try { 
	codemanin.setDictCatalog(rs.getString("DICT_CATALOG"));
	codemanin.setDictCatalogName(rs.getString("DICT_CATALOG_NAME"));
	return codemanin;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}

class CodeManInSubRowMapper implements RowMapper {
	 @Override 
	public Object mapRow(ResultSet rs, int paramInt) throws SQLException { 
	CodeManIn codemanin = new CodeManIn();
	 try { 
		 codemanin.setDictNo(rs.getString("DICT_NO"));
		 codemanin.setDictName(rs.getString("DICT_NAME")); 
	return codemanin;
	}
	catch (Exception e) {
	return null;
	 }
	}
	}