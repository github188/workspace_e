package com.nari.runman.dutylog;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;

public class StaffFindBean {
	@SelectConfig(column="staff_no",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.LIKE,likeMode=LikeMode.START)
private String staffNo;
	@SelectConfig(column="staff_name",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.LIKE,likeMode=LikeMode.START)
private String staffName;

	@SelectConfig(column="dept_name",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.LIKE,likeMode=LikeMode.START)
private String deptName;

	public String getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
