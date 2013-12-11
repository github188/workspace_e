package com.nari.runman.runstatusman.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.logofsys.LMasterStationResBean;
import com.nari.runman.runstatusman.LMasterStationResDao;
import com.nari.util.exception.DBAccessException;

public class LMasterStationResDaoImpl extends JdbcBaseDAOImpl implements LMasterStationResDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<LMasterStationResBean> findLMasterStationRes(Date msDate,String clusterType)
			throws DBAccessException {
		StringBuffer sql = new StringBuffer();//字符串长度可变?
		
		  sql.append("select l.ip_addr,\n")
			 .append("       to_number(to_char(l.monitor_time, 'hh24')) monitor_time,\n") 
			 .append("       avg(l.disk_use_ratio) disk_use_ratio,\n") 
			 .append("       avg(l.memory_use_ratio) memory_use_ratio,\n") 
			 .append("       avg(l.cpu_use_ratio) cpu_use_ratio,\n") 
			 .append("       avg(l.net_use_ratio) net_use_ratio\n") 
			 .append("  from l_master_station_res l\n") 
			 .append(" where l.cluster_name = ?\n") 
			 .append(" and to_char(l.monitor_time,'yyyy-mm-dd') = to_char(?,'yyyy-mm-dd')\n") //时间格式转换?
			 .append(" group by l.ip_addr, to_char(l.monitor_time, 'hh24')\n") 
			 .append(" order by l.ip_addr,monitor_time");
		return getJdbcTemplate().query(sql.toString(),new Object[]{clusterType, msDate},
				new lMasterStationResMapper());
	}

	class lMasterStationResMapper implements RowMapper {
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			LMasterStationResBean lmasterstationresbean = new LMasterStationResBean();
			try {
				lmasterstationresbean.setNetUseRatio(rs.getDouble("NET_USE_RATIO"));
				lmasterstationresbean.setCpuUseRatio(rs.getDouble("CPU_USE_RATIO"));
				lmasterstationresbean.setMemoryUseRatio(rs.getDouble("MEMORY_USE_RATIO"));
				lmasterstationresbean.setDiskUseRatio(rs.getDouble("DISK_USE_RATIO"));
				lmasterstationresbean.setIpAddr(rs.getString("IP_ADDR"));
				lmasterstationresbean.setMonitorTime(rs.getString("MONITOR_TIME"));
				return lmasterstationresbean;
			} catch (Exception e) {
					return null;
			}
		}
	}
	
	/**
	 * @desc 查询Web任务 
	 * @return
	 * @throws Exception 
	 */
	public List<Object> findTermTaskInfo() throws Exception{
		List<Object> list = null;
		 list = new ArrayList<Object>();

		Collection<Object> c = TaskDeal.getTaskHandle().getManualTasks(10000);
		
		for (Iterator i = c.iterator();i.hasNext();) {
			list.add(i.next());
		}
		return list;
	}
}
