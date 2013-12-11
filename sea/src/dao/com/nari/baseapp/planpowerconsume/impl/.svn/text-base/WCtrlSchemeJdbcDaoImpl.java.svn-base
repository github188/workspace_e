package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IWCtrlSchemeJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.WCtrlScheme;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;
/****
 * 控制方案ctrlscheme的jdbc实现
 * @author huangxuan
 * ***/
public class WCtrlSchemeJdbcDaoImpl extends JdbcBaseDAOImpl implements
		IWCtrlSchemeJdbcDao {
	/**
	 * 对不满足删除要求的已经进行了过来删除
	 * @param listId待删除的方案的id 
	 * *
	 * @throws DBAccessException **/
	@SuppressWarnings("unchecked")
	public void deleteValidateIds(List listId) throws DBAccessException {
		if(null==listId){
			throw new RuntimeException("listId不能为空");
		}
		//得到可以被处理的列表
		listId=findValidateIds(listId);
		StringBuilder sb = new StringBuilder("(");
		for (Iterator iterator = listId.iterator(); iterator.hasNext();) {
			iterator.next();
			if (iterator.hasNext()) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}
		String ids=sb.toString();
		//删除从表中的数据 控制方案终端控
		String sqlCtrlTmnl ="delete from W_CTRL_SCHEME_TMNL where CTRL_SCHEME_ID in "+ids;
		//删除控制方案时段控中的数据
		String sqlCtrlSection="delete from W_CTRL_SCHEME_SECTION where CTRL_SCHEME_ID in "+ids;
		String sql="delete from W_CTRL_SCHEME where CTRL_SCHEME_ID in "+ids;
		//执行删除操作
		getJdbcTemplate().update(sqlCtrlTmnl,listId.toArray());
		getJdbcTemplate().update(sqlCtrlSection,listId.toArray());
		getJdbcTemplate().update(sql,listId.toArray());
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> findValidateIds(List listId) throws DBAccessException {
		if(null==listId||0==listId.size()){
			throw new RuntimeException("未选择删除的项");
		}
		// 此表id的类型为数字，通过listId来生成一个id的列表
		// 通过上面操作sb成为满足sql规范的字符串片段
		StringBuilder sb = new StringBuilder("select CTRL_SCHEME_ID from W_CTRL_SCHEME where CTRL_SCHEME_ID in (");
		for (Iterator iterator = listId.iterator(); iterator.hasNext();) {
			iterator.next();
			if (iterator.hasNext()) {
				sb.append("?,");
			} else {
				sb.append("?)");
			}
		}
		//添加其他的限制性条件 没有启动并且 结束时间小于当前的时间或者开始的时间小于结束的时间
		sb.append(" and IS_VALID=0 and CTRL_DATE_END<sysdate ");
		// 得到满足条件的list
		List rsList = getJdbcTemplate().query(sb.toString(),listId.toArray(),new  RowMapper(){
			
			public Object mapRow(ResultSet resultset, int i)
					throws SQLException {
				return resultset.getObject("CTRL_SCHEME_ID");
			}});
		//对参数listId进行处理 使其和rsList中的内容保持一致
		if(rsList.size()==0){
			throw new DBAccessException("你选择的项目全都为不能删除的项目");
		}
		if(rsList.size()<listId.size()){
			throw new DBAccessException("你选择的项目中存在不能删除的项目，请检查");
		}
		listId.clear();
		listId.addAll(rsList);
		return rsList;
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		List list = new LinkedList();
		list.add(33);
		list.add(44);
		list.add(33);
		StringBuilder sb = new StringBuilder(list.toString());
		sb.replace(0, 1, "(");
		sb.replace(sb.length() - 1, sb.length(), ")");
		System.out.println(sb.toString());
	}
	/**
	 * 
	 * @return key 以大写的形式返回
	 * ***/
	@SuppressWarnings("unchecked")
	@Override
	public Page findAllSchemeNames(String staffNo,long start,int limit) {
		String sql="select CTRL_SCHEME_ID,CTRL_SCHEME_NAME from W_CTRL_SCHEME where STAFF_NO=?";
		//return pag;
		return pagingFind(sql, start, limit, new MapResultMapper("[*,u]"),staffNo);
	}
	@Override
	public Page<WCtrlScheme> findPage(PSysUser staff, WCtrlScheme scheme, long start, int limit) {
		String sql =
			"SELECT CTRL_SCHEME_ID,\n" +
			"       ORG_NO,\n" + 
			"       CTRL_SCHEME_NAME,\n" + 
			"       CTRL_SCHEME_TYPE,\n" + 
			"       CTRL_DATE_START,\n" + 
			"       CTRL_DATE_END,\n" + 
			"       CTRL_LOAD,\n" + 
			"       LIMIT_TYPE,\n" + 
			"       IS_VALID,\n" + 
			"       STAFF_NO,\n" + 
			"       SCHEME_REMARK,\n" + 
			"       CREATE_DATE\n" + 
			"  FROM W_CTRL_SCHEME\n" +
			"  WHERE STAFF_NO=?\n";
		
		List<Object> paramList = new ArrayList<Object>();
		paramList.add(staff.getStaffNo());
		
		if(scheme.getIsValid()!=null) {
			sql +=" and IS_VALID =?\n";
			paramList.add(scheme.getIsValid());
		}
		if(scheme.getCtrlSchemeType()!=null && !"".equals(scheme.getCtrlSchemeType())) {
			sql +=" and CTRL_SCHEME_TYPE =?\n";
			paramList.add(scheme.getCtrlSchemeType());
		} else {
			sql += " and CTRL_SCHEME_TYPE in('01','02','04','06')\n";
		}
		if (scheme.getCtrlDateStart() != null) {
			sql +=" and CTRL_DATE_START >=?\n";
			paramList.add(scheme.getCtrlDateStart());
		}
		if (scheme.getCtrlDateEnd() != null) {
			sql +=" and CTRL_DATE_END <=?\n";
			paramList.add(scheme.getCtrlDateEnd());
		}
		sql +=" ORDER BY CREATE_DATE DESC";
		return super.pagingFind(sql, start, limit, new wCtrlSchemeRowMapper(), paramList.toArray());
	}
	
	class wCtrlSchemeRowMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			WCtrlScheme wctrlscheme = new WCtrlScheme();
			try {
				wctrlscheme.setCtrlSchemeId(rs.getLong("CTRL_SCHEME_ID"));
				wctrlscheme.setOrgNo(rs.getString("ORG_NO"));
				wctrlscheme.setCtrlSchemeName(rs.getString("CTRL_SCHEME_NAME"));
				wctrlscheme.setCtrlSchemeType(rs.getString("CTRL_SCHEME_TYPE"));
				wctrlscheme.setCtrlDateStart(rs.getDate("CTRL_DATE_START"));
				wctrlscheme.setCtrlDateEnd(rs.getDate("CTRL_DATE_END"));
				String ctrlLoad = rs.getString("CTRL_LOAD");
				if (ctrlLoad != null && !"".equals(ctrlLoad)) {
					wctrlscheme.setCtrlLoad(Double.parseDouble(ctrlLoad));
				}

				wctrlscheme.setLimitType(rs.getString("LIMIT_TYPE"));
				String isValid = rs.getString("IS_VALID");
				if (isValid != null && !"".equals(isValid)) {
					wctrlscheme.setIsValid(Long.parseLong(isValid));
				}
				wctrlscheme.setStaffNo(rs.getString("STAFF_NO"));
				wctrlscheme.setSchemeRemark(rs.getString("SCHEME_REMARK"));
				wctrlscheme.setCreateDate(rs.getDate("CREATE_DATE"));
				return wctrlscheme;
			} catch (Exception e) {
				return null;
			}
		}
	}
}