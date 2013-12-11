package com.nari.runman.archivesman.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.runcontrol.TDialChannel;
import com.nari.runcontrol.TDialGroupChannel;
import com.nari.runcontrol.TDnnChannel;
import com.nari.runcontrol.TNetChannel;
import com.nari.runman.archivesman.ICollectionChannelDao;
import com.nari.runman.archivesman.ICollectionChannelService;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 实现类 ICollectionChannelService
 * 
 * @author zhangzhw 档案管理 采集通道管理维护
 */
public class CollectionChannelServiceImpl implements ICollectionChannelService {

	ICollectionChannelDao iCollectionChannelDao;

	@Override
	public List<TDialChannel> queryDialByTmnl(String assetNo) throws Exception{
		try {
			return iCollectionChannelDao.queryDialByTmnl(assetNo);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询区县下面的供电所出错");
		}
	}

	@Override
	public List<TDialGroupChannel> queryDialGroup()throws Exception {
		try {
			return iCollectionChannelDao.queryDialGroup();
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询区县下面的供电所出错");
		}
	}

	@Override
	public List<TDnnChannel> queryDnnByTmnl(String assetNo)throws Exception {
		try {
			return iCollectionChannelDao.queryDnnByTmnl(assetNo);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询区县下面的供电所出错");
		}
	}

	@Override
	public List<TNetChannel> queryNetByTmnl(String assetNo) throws Exception{
		try {
			return iCollectionChannelDao.queryNetByTmnl(assetNo);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询区县下面的供电所出错");
		}
	}
	
	/**
	 * 查询所有的规约编码
	 * @return
	 * @throws Exception
	 */
	public List<VwProtocolCode> queryAllProtocol() throws Exception{
		try {
			return iCollectionChannelDao.queryAllProtocol();
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询所有的规约编码出错");
		}
	}
	
	/**
	 * 保存、新增拨号组
	 * @return
	 */
	public String saveDialGroup(TDialGroupChannel tDialGroupChannel)throws Exception{
		if(tDialGroupChannel.getDialGroupNo() != null && !"".equals(tDialGroupChannel.getDialGroupNo())){
			List<TDialGroupChannel> list1 = this.iCollectionChannelDao.queryDialGroupByValue("dialGroupName_update", tDialGroupChannel);
			if(list1!=null && list1.size()>0){
				return "拨号组名称已存在！";
			}
			List<TDialGroupChannel> list2 = this.iCollectionChannelDao.queryDialGroupByValue("id_update", tDialGroupChannel);
			if(list2!=null && list2.size()>0){
				return "终端服务器已使用！";
			}
			this.iCollectionChannelDao.saveDialGroup(tDialGroupChannel);
			return "保存成功！";
		}else{
			List<TDialGroupChannel> list1 = this.iCollectionChannelDao.queryDialGroupByValue("dialGroupName_insert", tDialGroupChannel);
			if(list1!=null && list1.size()>0){
				return "拨号组名称已存在！";
			}
			List<TDialGroupChannel> list2 = this.iCollectionChannelDao.queryDialGroupByValue("id_insert", tDialGroupChannel);
			if(list2!=null && list2.size()>0){
				return "终端服务器端口已使用！";
			}
			this.iCollectionChannelDao.insertDialGroup(tDialGroupChannel);
			return "保存成功！";
		}
	}

	/**
	 * 保存、新增拨号通道
	 * @return
	 */
	public String saveDial(TDialChannel tDialChannel)throws Exception{
		if(tDialChannel.getDialChannelId() != null && !"".equals(tDialChannel.getDialChannelId())){
			this.iCollectionChannelDao.saveDial(tDialChannel);
			return "保存成功！";
		}else{
			this.iCollectionChannelDao.insertDial(tDialChannel);
			return "保存成功！";
		}
	}
	
	/**
	 * 保存、新增网络通道
	 * @return
	 */
	public String saveNet(TNetChannel tNetChannel)throws Exception{
		if(tNetChannel.getNetChannelId() != null && !"".equals(tNetChannel.getNetChannelId())){
			this.iCollectionChannelDao.saveNet(tNetChannel);
			return "保存成功！";
		}else{
			this.iCollectionChannelDao.insertNet(tNetChannel);
			return "保存成功！";
		}
	}
	
	/**
	 * 保存、新增专线通道
	 * @return
	 */
	public String saveDnn(TDnnChannel tDnnChannel)throws Exception{
		if(tDnnChannel.getDnnChannelId() != null && !"".equals(tDnnChannel.getDnnChannelId())){
			this.iCollectionChannelDao.saveDnn(tDnnChannel);
			return "保存成功！";
		}else{
			this.iCollectionChannelDao.insertDnn(tDnnChannel);
			return "保存成功！";
		}
	}
	
	/**
	 * 删除拨号组
	 * @param dialGroupNo
	 * @return
	 * @throws Exception
	 */
	public void deleteDialGroup(String dialGroupNo) throws Exception{
		try{
			this.iCollectionChannelDao.deleteDialGroup(dialGroupNo);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除拨号组出错");
		}
	}
	
	/**
	 * 删除拨号通道
	 * @param dialGroupNo
	 * @return
	 * @throws Exception
	 */
	public void deleteDial(String dialChannelId) throws Exception{
		try{
			this.iCollectionChannelDao.deleteDial(dialChannelId);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除拨号通道出错");
		}
	}
	
	/**
	 * 删除网络通道
	 * @param netChannelId
	 * @return
	 * @throws Exception
	 */
	public void deleteNet(String netChannelId) throws Exception{
		try{
			this.iCollectionChannelDao.deleteNet(netChannelId);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除网络通道出错");
		}
	}
	
	/**
	 * 删除专线通道
	 * @param dnnChannelId
	 * @return
	 * @throws Exception
	 */
	public void deleteDnn(String dnnChannelId) throws Exception{
		try{
			this.iCollectionChannelDao.deleteDnn(dnnChannelId);
		}catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("删除专线通道出错");
		} 
	}
	
	public ICollectionChannelDao getiCollectionChannelDao() {
		return iCollectionChannelDao;
	}
	public void setiCollectionChannelDao(
			ICollectionChannelDao iCollectionChannelDao) {
		this.iCollectionChannelDao = iCollectionChannelDao;
	}
}
