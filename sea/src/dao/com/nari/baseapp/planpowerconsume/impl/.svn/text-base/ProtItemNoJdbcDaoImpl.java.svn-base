package com.nari.baseapp.planpowerconsume.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.nari.baseapp.planpowerconsume.IProtItemNoJdbcDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;

/**
 * 获取prot_item_no相关信息JdbcDao接口实现类
 * @author 姜海辉
 *
 */
public class ProtItemNoJdbcDaoImpl extends JdbcBaseDAOImpl implements IProtItemNoJdbcDao{
	/**
    * 查询终端规约码
    * @param tmnlAssetNo 终端资产编号
    * @return
    */
	@SuppressWarnings("unchecked")
	public String findProtocolCode(String tmnlAssetNo)throws DBAccessException{
		String  sql=
			"select r.protocol_code\n" +
			" from r_tmnl_run r\n" + 
			" where r.tmnl_asset_no = ?";
		try{
			List<String> list = this.getJdbcTemplate().query(sql,new String[]{tmnlAssetNo}, new ProtocolRowMapper());
			if(null!=list&&list.size()>0) 
			  return list.get(0);
			else
			  throw new DBAccessException(ExceptionConstants.BAE_PROTOCOLCODEEERROR);
		}catch(RuntimeException e){
			this.logger.debug("查询终端规约码出错！");
			throw e;
		}	
	}

	/**
	 * 查询prot_item_no
	 * @param protocolNo 规约编号
	 * @return
	 * @throws DBAccessException
	 */
	@SuppressWarnings("unchecked")
	public String[] findProtItemNo(String protocolNo)throws DBAccessException{
		String  sql=
            "select b.prot_item_no\n" +
			"  from b_comm_protocol_item b\n" + 
			"  where b.protocol_no = ?";
		try{
			List<String> list = this.getJdbcTemplate().query(sql,new String[]{protocolNo}, new ProtItemNoRowMapper());
			if(null!=list&&list.size()>0){
				String[] ProtItemNos=new String[list.size()];
				for(int i=0;i<list.size();i++)
				  ProtItemNos[i]=list.get(i);
				return ProtItemNos;
			}
			else
			  throw new DBAccessException(ExceptionConstants.BAE_PROTITEMNOEERROR);
		}catch(RuntimeException e){
			this.logger.debug("查询规约项编号出错！");
			throw e;
		}		
	}
	
	
	class ProtocolRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try {
				return rs.getString("protocol_code");
			}catch (Exception e) {
				return null;
			}
		}	
	}
	
	class ProtItemNoRowMapper implements RowMapper{
		@Override
		public Object mapRow(ResultSet rs, int paramInt) throws SQLException{
			try {
				return rs.getString("prot_item_no");
			}catch (Exception e) {
				return null;
			}
		}	
	}
	
}
