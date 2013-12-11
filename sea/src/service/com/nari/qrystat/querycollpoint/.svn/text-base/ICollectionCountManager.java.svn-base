package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface ICollectionCountManager {
	public Page<CollectionCountDTO> findCollectionCount(String treeType,String orgNo,String consNo,
			long start, int limit,PSysUser pSysUser,String dateStart,String dateEnd) throws DBAccessException;
	
	public List<CollectionCountDTO> findCollectionCountSUM(String treeType,String orgNo,String consNo,PSysUser pSysUser,String dateStart,String dateEnd) throws DBAccessException;
}
