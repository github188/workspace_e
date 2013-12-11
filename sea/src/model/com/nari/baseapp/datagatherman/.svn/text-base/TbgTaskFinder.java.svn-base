package com.nari.baseapp.datagatherman;

import java.util.Calendar;
import java.util.Date;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;

public class TbgTaskFinder {
	@SelectConfig(column = "tt.task_time", limit = LimitType.GE)
	private Date startTime;
	@SelectConfig(column = "tt.task_time", limit = LimitType.LE)
	private Date endTime;
	@SelectConfig(column="tt.task_name",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.LIKE)
	private String taskName;
	public Date getStartTime() {
		return startTime;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		if (endTime == null) {
			return null;
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(endTime);
			c.add(Calendar.DATE, 1);
			return c.getTime();
		}
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
