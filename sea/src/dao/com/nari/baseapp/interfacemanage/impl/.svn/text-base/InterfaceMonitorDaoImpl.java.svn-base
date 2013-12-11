package com.nari.baseapp.interfacemanage.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.interfacemanage.InterfaceMonitorBean;
import com.nari.baseapp.interfacemanage.InterfaceMonitorDao;
import com.nari.baseapp.interfacemanage.InterfaceMonitorDto;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;

/**
 * 
 * 接口监测dao实现类
 * @author ChunMingLi
 * @since  2010-6-22
 *
 */
public class InterfaceMonitorDaoImpl extends JdbcBaseDAOImpl implements
		InterfaceMonitorDao {

	@Override
	public Page<InterfaceMonitorDto> findInterfaceList(
			InterfaceMonitorBean monitorBean, PSysUser userInfo, long start,long limit) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sql = new StringBuffer();
		List<Object> paraList = new ArrayList<Object>();
		sql.append("SELECT F.SYS_NO,F.LOG_ID,  \n");
		sql.append("     F.INTERFACE_TYPE,  \n");
		sql.append("     F.INTERFACE_NAME,  \n");
		sql.append("     F.INTERFACE_CONTENT,  \n");
		sql.append("     F.ERR_NO,  \n");
		sql.append("     F.OP_TIME,  \n");
		sql.append("     F.RESP_ID  \n");
		sql.append("FROM I_INTERFACE_LOG F  \n");
		sql.append("WHERE \n");
		sql.append("F.SYS_NO = ? \n");
		paraList.add(monitorBean.getMonitorType());
		sql.append("  \n");
		//TO_DATE(? , 'yyyy-mm-dd hh24:mi:ss')
		sql.append("AND F.OP_TIME >= TO_DATE(? , 'yyyy-mm-dd hh24:mi:ss')   \n");
		sql.append("AND F.OP_TIME < TO_DATE(? , 'yyyy-mm-dd hh24:mi:ss')   \n");
		System.out.println(sdf.format(monitorBean.getStartDate()));
		paraList.add(sdf.format(monitorBean.getStartDate()));
		paraList.add(sdf.format(monitorBean.getEndDate()));
		sql.append("ORDER BY F.LOG_ID  \n");
		this.logger.debug(sql.toString());
		return pagingFind(sql.toString(), start, limit, new InterfaceMonitorDtoMapper(), paraList.toArray());
	}
	/**
	 * 
	 * 接口监视查询映射类
	 * 
	 * @author ChunMingLi
	 * @since  2010-7-8
	 *
	 */
	class InterfaceMonitorDtoMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException {
			InterfaceMonitorDto interfaceMonitorDto  = new InterfaceMonitorDto();
			try {
				interfaceMonitorDto.setLogId(rs.getLong("LOG_ID"));
				interfaceMonitorDto.setSysNo(rs.getString("SYS_NO"));
				interfaceMonitorDto.setInterfaceType(rs.getString("INTERFACE_TYPE"));
				interfaceMonitorDto.setInterfaceName(rs.getString("INTERFACE_NAME"));
				interfaceMonitorDto.setContent(rs.getString("INTERFACE_CONTENT"));
				interfaceMonitorDto.setErrNo(rs.getString("ERR_NO"));
				interfaceMonitorDto.setOpTime(rs.getTimestamp("OP_TIME"));
				interfaceMonitorDto.setRespId(rs.getString("RESP_ID"));
				return interfaceMonitorDto;
			} catch (Exception e) {
				return null;
			}
		}
	}


}
