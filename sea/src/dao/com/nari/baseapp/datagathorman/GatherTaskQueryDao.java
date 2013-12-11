package com.nari.baseapp.datagathorman;

import java.util.Date;

import com.nari.baseapp.datagatherman.TaskStatInfoBean;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface GatherTaskQueryDao {
	/**
	 * 任务执行情况查询
	 * @param taskType 任务类型
	 * @param nodeType 节点类型
	 * @param nodeValue 节点值
	 * @param startTime 查询开始时间
	 * @param endTime  查询结束时间
	 * @param userInfo  用户信息
	 * @param start   检索开始位置
	 * @param limit   检索限制条数
	 * @return
	 * @throws DBAccessException
	 */
	public Page<TaskStatInfoBean> queryGatherTaskStat(int taskType,
			String nodeType, String nodeValue, Date startTime, Date endTime,
			PSysUser userInfo, long start, long limit) throws DBAccessException;
}
