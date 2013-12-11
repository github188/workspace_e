package com.nari.runman.archivesman;

import java.util.List;

import com.nari.runcontrol.TDialChannel;
import com.nari.runcontrol.TDialGroupChannel;
import com.nari.runcontrol.TDnnChannel;
import com.nari.runcontrol.TNetChannel;
import com.nari.sysman.templateman.VwProtocolCode;

/**
 * 接口 ICollectionChannelService
 * 
 * @author zhangzhw 档案管理 采集通道管理维护
 */
public interface ICollectionChannelService {

	/**
	 * 方法 queryDialGroup
	 * 
	 * @return 查询拨号通道组
	 */
	public List<TDialGroupChannel> queryDialGroup()throws Exception;

	/**
	 * 方法 queryDialByTmnl
	 * 
	 * @param assetNo
	 * @return 查询拨号通道
	 */
	public List<TDialChannel> queryDialByTmnl(String assetNo)throws Exception;

	/**
	 * 方法 queryDnnByTmnl
	 * 
	 * @param assetNo
	 * @return 查询专线通道
	 */
	public List<TDnnChannel> queryDnnByTmnl(String assetNo)throws Exception;

	/**
	 * 方法 queryNetByTmnl
	 * 
	 * @param assetNo
	 * @return 查询网络通道
	 */
	public List<TNetChannel> queryNetByTmnl(String assetNo)throws Exception;
	
	/**
	 * 保存、新增拨号组
	 * @return
	 */
	public String saveDialGroup(TDialGroupChannel tDialGroupChannel)throws Exception;
	
	/**
	 * 保存、新增拨号通道
	 * @return
	 */
	public String saveDial(TDialChannel tDialChannel)throws Exception;
	
	/**
	 * 保存、新增网络通道
	 * @return
	 */
	public String saveNet(TNetChannel tNetChannel)throws Exception;
	
	/**
	 * 保存、新增专线通道
	 * @return
	 */
	public String saveDnn(TDnnChannel tDnnChannel)throws Exception;
	
	/**
	 * 删除拨号组
	 * @param dialGroupNo
	 * @return
	 * @throws Exception
	 */
	public void deleteDialGroup(String dialGroupNo) throws Exception;
	
	/**
	 * 删除拨号通道
	 * @param dialChannelId
	 * @return
	 * @throws Exception
	 */
	public void deleteDial(String dialChannelId) throws Exception;
	
	/**
	 * 删除网络通道
	 * @param netChannelId
	 * @return
	 * @throws Exception
	 */
	public void deleteNet(String netChannelId) throws Exception;
	
	/**
	 * 删除专线通道
	 * @param dnnChannelId
	 * @return
	 * @throws Exception
	 */
	public void deleteDnn(String dnnChannelId) throws Exception;
	
	/**
	 * 查询所有的规约编码
	 * @return
	 * @throws Exception
	 */
	public List<VwProtocolCode> queryAllProtocol() throws Exception;

}
