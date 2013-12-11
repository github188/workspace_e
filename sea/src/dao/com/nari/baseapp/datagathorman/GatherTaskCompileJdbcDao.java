package com.nari.baseapp.datagathorman;

import java.util.List;

import com.nari.baseapp.datagatherman.GatherTaskCompileDto;
import com.nari.baseapp.datagatherman.TaskInfoDto;
import com.nari.baseapp.datagatherman.TbgTaskSend;
import com.nari.privilige.PSysUser;
import com.nari.ami.database.map.terminalparam.TTmnlTask;
import com.nari.util.exception.DBAccessException;

public interface GatherTaskCompileJdbcDao {

	/**
	 * 根据用户查询相关信息放入备选用户Grid中（单条或多条记录）
	 * @param csNo 用户编号（consNo）
	 * @param isConsNo true-根据户号查询  false-根据终端资产编号查询
	 * @return
	 * @throws DBAccessException
	 */
	public List<GatherTaskCompileDto> findGatherTaskCompileByCons(String csNo[],boolean isConsNo) throws DBAccessException;
	
	/**
	 * 根据单位编码或者线路id查询相关信息放入备选用户Grid中（多条记录）
	 * @param csNo 单位编码或线路ID
	 * @param nodeType ORG---供电所  	LINE---线路
	 * @throws DBAccessException
	 */
	public List<GatherTaskCompileDto> findGatherTaskCompileByOrg(PSysUser ps,String csNo,String nodeType) throws DBAccessException;
	
	/**
	 * 根据终端资产编号和任务号查询任务信息
	 * @param tmnlAssetNo 终端资产编号
	 * @param taskNo 任务号
	 * @return
	 * @throws DBAccessException
	 */
	public TaskInfoDto findTaskInfoById(String tmnlAssetNo,String taskNo) throws DBAccessException;
	
	/**
	 * 根据终端资产编号和任务模板id确定任务是否存在
	 * @param tmnlAssetNo 终端资产编号
	 * @param templateId 任务模板id
	 * @throws DBAccessException
	 */
	public TTmnlTask findTaskByNoId(String tmnlAssetNo,Long templateId) throws DBAccessException;

	void mergeBgTaskSend(TbgTaskSend t) throws DBAccessException;
}
