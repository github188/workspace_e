package com.nari.bg;

import java.util.List;

public interface BackGroundTaskManager {
	/**
	 * 根据序列获取taskId
	 * @return
	 */
	public long getTaskId() throws Exception;
	
	/**
	 * 保存后台下发任务主表信息
	 */
	public void saveTBgTaskInfo(List<TBgTask> tBgTask) throws Exception;
	
	/**
	 * 保存下发参数信息
	 */
	public void saveTBgTaskDetail(List<TBgTaskDetail> tBgTaskDetail) throws Exception;
}
