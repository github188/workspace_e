package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.TDialChannel;
import com.nari.runcontrol.TDialGroupChannel;
import com.nari.runcontrol.TDnnChannel;
import com.nari.runcontrol.TNetChannel;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.util.exception.DBAccessException;

/**
 * 接口 ICollectionChannelDao
 * 
 * @author zhangzhw 档案管理 采集通道维护 DAO
 */
public interface ICollectionChannelDao {

	/**
	 * 方法 queryDialGroup
	 * 
	 * @return 查询拨号通道组
	 */
	public List<TDialGroupChannel> queryDialGroup()throws DBAccessException;
	
	/**
	 * 根据不同的条件查询拨号通道组
	 * @param queryType 查询条件类型
	 * @param t TDialGroupChannel对象
	 * @return
	 * @throws DBAccessException
	 */
	public List<TDialGroupChannel> queryDialGroupByValue(String queryType,TDialGroupChannel t) throws DBAccessException;

	/**
	 * 方法 queryDialByTmnl
	 * 
	 * @param assetNo
	 * @return 查询拨号通道
	 */
	public List<TDialChannel> queryDialByTmnl(String assetNo)throws DBAccessException;

	/**
	 * 方法 queryDnnByTmnl
	 * 
	 * @param assetNo
	 * @return 查询专线通道
	 */
	public List<TDnnChannel> queryDnnByTmnl(String assetNo)throws DBAccessException;

	/**
	 * 方法 queryNetByTmnl
	 * 
	 * @param assetNo
	 * @return 查询网络通道
	 */
	public List<TNetChannel> queryNetByTmnl(String assetNo)throws DBAccessException;
	
	/**
	 * 新增拨号组
	 * @param tDialGroupChannel
	 */
	public void insertDialGroup(TDialGroupChannel tDialGroupChannel) throws DBAccessException;
	
	/**
	 * 新增拨号通道
	 */
	public void insertDial(TDialChannel tDialChannel) throws DBAccessException;
	
	/**
	 * 新增网络通道
	 */
	public void insertNet(TNetChannel tNetChannel) throws DBAccessException;
	
	/**
	 * 新增专线通道
	 */
	public void insertDnn(TDnnChannel tDnnChannel) throws DBAccessException;
	
	/**
	 * 保存拨号组
	 * @param tDialGroupChannel
	 */
	public void saveDialGroup(TDialGroupChannel tDialGroupChannel) throws DBAccessException;
	
	/**
	 * 保存拨号通道
	 */
	public void saveDial(TDialChannel tDialChannel) throws DBAccessException;
	
	/**
	 * 保存网络通道
	 */
	public void saveNet(TNetChannel tNetChannel) throws DBAccessException;
	
	/**
	 * 保存专线通道
	 */
	public void saveDnn(TDnnChannel tDnnChannel) throws DBAccessException;
	
	/**
	 * 删除拨号组
	 * @param dialGroupNo
	 * @throws DBAccessException
	 */
	public void deleteDialGroup(String dialGroupNo) throws DBAccessException;
	
	/**
	 * 删除拨号通道
	 * @throws DBAccessException
	 */
	public void deleteDial(String dialChannelId) throws DBAccessException;
	
	/**
	 * 删除网络通道
	 * @throws DBAccessException
	 */
	public void deleteNet(String netChannelId) throws DBAccessException;
	
	/**
	 * 删除专线通道
	 * @throws DBAccessException
	 */
	public void deleteDnn(String dnnChannelId) throws DBAccessException;
	
	/**
	 * 查询所有规约
	 * @return
	 * @throws DBAccessException
	 */
	public List<VwProtocolCode> queryAllProtocol() throws DBAccessException;

}
