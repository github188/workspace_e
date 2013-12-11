package com.nari.runman.archivesman.impl;

import java.util.List;

import com.nari.runman.archivesman.IRemoteDebugDao;
import com.nari.runman.archivesman.IRemoteDebugManager;
import com.nari.runman.runstatusman.RemoteDebugDto;
import com.nari.support.Page;

public class RemoteDebugManagerImpl implements IRemoteDebugManager {
	IRemoteDebugDao remoteDebugDao;
	
	

	/**
	 * 查询指定采集点下所有的远程调试列表
	 * @param cpNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<RemoteDebugDto> queryRemoteDebugList(String cpNo, long start, int limit) throws Exception {
		return remoteDebugDao.queryRemoteDebugList(cpNo,start,limit);
	};
	public IRemoteDebugDao getRemoteDebugDao() {
		return remoteDebugDao;
	}
	public void setRemoteDebugDao(IRemoteDebugDao remoteDebugDao) {
		this.remoteDebugDao = remoteDebugDao;
	}
}
