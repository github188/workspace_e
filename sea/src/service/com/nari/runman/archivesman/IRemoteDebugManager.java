package com.nari.runman.archivesman;


import com.nari.runman.runstatusman.RemoteDebugDto;
import com.nari.support.Page;

/**
 * 远程调试Service接口
 * @author Taoshide
 *
 */
public interface IRemoteDebugManager {
	/**
	 * 查询指定采集点下所有的远程调试列表
	 * @param cpNo
	 * @return
	 * @throws Exception
	 */
	public Page<RemoteDebugDto> queryRemoteDebugList(String cpNo,long start,int limit) throws Exception;
}
