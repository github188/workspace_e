package com.nari.qrystat.taskQuery.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nari.qrystat.taskQuery.TaskStatDto;
import com.nari.qrystat.taskQuery.TaskStatTypeDto;
import com.nari.qrystat.taskQuery.TaskStateArea;
import com.nari.qrystat.taskQuery.TaskStateDao;
import com.nari.qrystat.taskQuery.TaskStateManager;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;

/**
 * 
 * 工单查询统计service
 * 
 * @author ChunMingLi
 * 2010-4-28
 *
 */
public class TaskStateManagerImpl implements TaskStateManager {
	private TaskStateDao taskStateDao;

	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateManager#findTask(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TaskStateArea> findTaskArea(Date dateFrom, Date dateTo) throws DBAccessException {
		try {
			return this.taskStateDao.findTaskArea(dateFrom, dateTo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateManager#findTaskType(java.lang.String, java.lang.String, long, int)
	 */
	public List<TaskStatTypeDto> findTaskType(Date dateFrom, Date dateTo)throws DBAccessException {
		return this.taskStateDao.findTaskType(dateFrom, dateTo);
	}
	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateManager#findTaskOrgName()
	 */
	public List<String> findTaskOrgNo(Date dateFrom, Date dateTo) throws DBAccessException {
		try {
			return this.taskStateDao.findTaskOrgNo(dateFrom, dateTo);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nari.qrystat.taskQuery.TaskStateManager#findTaskOrgNo()
	 */
	public List<String> findTaskOrgNo() throws DBAccessException {
		try {
			return this.taskStateDao.findTaskOrgNo();
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		}
	}
	
	/**
	 * 
	 * 获得展现DTO
	 * 
	 * @param orgNoList  确定的供电单位工单集合
	 * @return TaskStatDto 展现DTO
	 */
	public TaskStatDto getTaskStatDto(List<TaskStateArea> orgNoList){
		//状态统计数组
		int[] stateCountArray = new int[10] ;
		
		//空是判断
		if(orgNoList == null || orgNoList.size() < 0){
			return null;
		}
		TaskStatDto taskStatDto = new TaskStatDto();
		taskStatDto.setOrgNo(orgNoList.get(0).getOrgNo());
		taskStatDto.setOrgName(orgNoList.get(0).getOrgName());
		taskStatDto.setEventID(orgNoList.get(0).getEventID());
		//合计总数
		int total = 0;
		//统计在工单某种状态的数量
		for(TaskStateArea state : orgNoList){
			//统计待办工单
			if(OP_TYPE_STATE_DBGD.equals(state.getEventStatuCode())){
				stateCountArray[0] ++;
				total ++;
			}
			//统计营销处理中
			else if(OP_TYPE_STATE_YXCLZ.equals(state.getEventStatuCode())){
				stateCountArray[1] ++;
				total ++;
			}
			//统计正常归档
			else if(OP_TYPE_STATE_ZCGD.equals(state.getEventStatuCode())){
				stateCountArray[2] ++;
				total ++;
			}
			//统计误报工单
			else if(OP_TYPE_STATE_WBQR.equals(state.getEventStatuCode())){
				stateCountArray[3] ++;
				total ++;
			}
			//统计挂起工单
			else if(OP_TYPE_STATE_GQ.equals(state.getEventStatuCode())){
				stateCountArray[4] ++;
				total ++;
			}
			//统计本地处理中
			else if(OP_TYPE_STATE_BDCLZ.equals(state.getEventStatuCode())){
				stateCountArray[5] ++;
				total ++;
			}
			//统计强制归档工单
			else if(OP_TYPE_STATE_QZGD.equals(state.getEventStatuCode())){
				stateCountArray[6] ++;
				total ++;
			}
			
		}
		//把处理后的数据赋值给DTO 七种流程状态统计
		taskStatDto.setOpTypeStateDBGD(stateCountArray[0]);
		taskStatDto.setOpTypeStateYXCLZ(stateCountArray[1]);
		taskStatDto.setOpTypeStateZCGD(stateCountArray[2]);
		taskStatDto.setOpTypeStateWBQR(stateCountArray[3]);
		taskStatDto.setOpTypeStateGQ(stateCountArray[4]);
		taskStatDto.setOpTypeStateBDCLZ(stateCountArray[5]);
		taskStatDto.setOpTypeStateQZGD(stateCountArray[6]);
		//统计总数
		taskStatDto.setTotal(total);
		
		return taskStatDto;
		
	}
		
	
	/**
	 * 
	 * 分析工单查询按照供电单位分类
	 * 
	 * @param orgNoNameList 供电单位集合
	 * @param taskList      工单集合
	 * @return  按照供电单位分类的集合
	 */
	public List<TaskStatDto> processTaskStateList(List<String> orgNoNameList,List<TaskStateArea> taskList){
		if(orgNoNameList == null || taskList == null){
			return null;
		}
		Map<String, List<TaskStateArea>> map = new HashMap<String, List<TaskStateArea>>();
		for(String orgNo : orgNoNameList){
			List<TaskStateArea> orgNoList = new ArrayList<TaskStateArea>();
			for(TaskStateArea org : taskList){
				if(orgNo.equals(org.getOrgNo())){
					orgNoList.add(org);
				}
			}
			if(orgNoList.size() > 0){
				map.put(orgNo, orgNoList);
			}
		}
		if(map.size() < 1 ){
			return null;
		}
		//获得DTO集合
		List<TaskStatDto> statDtos = new ArrayList<TaskStatDto>();
		for(String orgTemp : map.keySet()){
			TaskStatDto taskStatDto = getTaskStatDto(map.get(orgTemp));
			statDtos.add(taskStatDto);
		}
		
		return statDtos;
		
	}

	public TaskStateDao getTaskStateDao() {
		return taskStateDao;
	}

	public void setTaskStateDao(TaskStateDao taskStateDao) {
		this.taskStateDao = taskStateDao;
	}


	
}
