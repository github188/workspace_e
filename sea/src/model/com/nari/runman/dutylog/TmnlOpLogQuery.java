package com.nari.runman.dutylog;

import java.util.Calendar;
import java.util.Date;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;

/**
 * @author huangxuan
 *
 * 
 *
 * @类描述:用户操作日志查询
 *	
 */
public class TmnlOpLogQuery {

	public String getEmpNO() {
		return empNO;
	}
	public void setEmpNO(String empNO) {
		this.empNO = empNO;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	//
	public Date getEndDate() {
		if(endDate==null)
			return null;
		Calendar c = Calendar.getInstance();
		c.setTime(endDate);
		c.add(Calendar.DATE, 1);
		return c.getTime();
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOpType() {
		return opType;
	}
	public void setOpType(String opType) {
		this.opType = opType;
	}
	@SelectConfig(column="l.emp_no",limit=LimitType.LIKE,dealNull=DealValueType.PARSEEMPTY,likeMode=LikeMode.START)
	private String empNO;
	@SelectConfig(column="l.op_time",limit=LimitType.GE)
	private Date startDate;
	@SelectConfig(column="l.op_time",limit=LimitType.LT)
	private Date endDate;
	@SelectConfig(column="l.op_type",limit=LimitType.EQ,dealNull=DealValueType.PARSEEMPTY)
	private String opType;
}
