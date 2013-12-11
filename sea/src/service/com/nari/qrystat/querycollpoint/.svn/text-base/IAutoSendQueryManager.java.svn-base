package com.nari.qrystat.querycollpoint;

import java.util.List;

import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface IAutoSendQueryManager {
	public Page<AutoSendQuery> findASQuery(String tgId, String orgType,String assetNo,String consName,String consNo,String asqid, long start, int limit) throws DBAccessException;
	public List<AutoSendQuery> findtmnlAssetNo(String tgId) throws DBAccessException;
}
