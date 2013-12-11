package com.nari.advapp.vipconsman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.advapp.BItemCodeMappingBean;
import com.nari.advapp.IPubDetailBean;
import com.nari.advapp.IPubSchemaBean;
import com.nari.advapp.vipconsman.DataPubDao;
import com.nari.advapp.vipconsman.DataPubManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

public class DataPubManagerImpl implements DataPubManager {
	
	//Dao层注入
	private DataPubDao dataPubDao;

	public void setDataPubDao(DataPubDao dataPubDao) {
		this.dataPubDao = dataPubDao;
	}

	@Override
	public List<BItemCodeMappingBean> queryItemCode(String dataType)
			throws Exception {
		try {
			return this.dataPubDao.queryItemCode(dataType);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("数据项编码查询出错");
		}
	}

	@Override
	public List<IPubDetailBean> queryPubDetail(String pubSchemaId, String dataType)throws Exception{
		try {
			return this.dataPubDao.queryPubDetail(pubSchemaId, dataType);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("发布数据项明细查询出错");
		}
	}

	@Override
	public List<IPubSchemaBean> queryPubSchema(String dataType, String consType) throws Exception{
		try {
			List<IPubSchemaBean> ipuList = this.dataPubDao.queryPubSchema(dataType, consType);
			if (ipuList.size() > 1) {
				throw new ServiceException("发布计划查询出错");
			}
			return ipuList;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("发布计划查询出错");
		}
	}

	@Override
	public void saveOrUpdatePubDetail(List<IPubDetailBean> pubDetailList) throws Exception{
		try {
			this.dataPubDao.saveOrUpdatePubDetail(pubDetailList);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("发布数据项保存或更新出错");
		}
	}

	@Override
	public void saveOrUpdatePubSchema(IPubSchemaBean iPubSchema, List<IPubDetailBean> pubDetailList)throws Exception {
		try {
			if (iPubSchema.getPubSchemaId() == null) {
				//保存或更新发布计划
//				this.dataPubDao.saveOrUpdatePubSchema(iPubSchema);
			} else {
				for (IPubDetailBean ipb : pubDetailList) {
					ipb.setPubSchemaId(iPubSchema.getPubSchemaId());
				}
				iPubSchema.setPubBaseTime(iPubSchema.getPubBaseTime().replace("T", " "));
				//更新数据发布模板
				this.dataPubDao.saveOrUpdatePubSchema(iPubSchema);
				//删除发布数据项
				this.dataPubDao.deletePubDetailById(iPubSchema.getPubSchemaId());
				//保存发布数据项
				this.dataPubDao.saveOrUpdatePubDetail(pubDetailList);
			}
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("发布计划信息保存或更新出错");
		}
	}

	@Override
	public List<IPubSchemaBean> loadPubSchema(String consType) throws Exception {
		try {
			List<IPubSchemaBean> ipuList = this.dataPubDao.loadPubSchema(consType);
			return ipuList;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("数据发布模板查询出错");
		}
	}

}
