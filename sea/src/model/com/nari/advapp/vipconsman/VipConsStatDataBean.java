package com.nari.advapp.vipconsman;

import java.io.Serializable;
import java.util.List;

import com.nari.qrystat.colldataanalyse.GeneralDataBean;

/**
 * 重点用户监测曲线冻结数据之数据值对象
 * @author 姜炜超
 */
public class VipConsStatDataBean implements Serializable{
    private String curveName;//曲线名
    private Boolean curveFlag;//曲线是否漏点标记
    private List<GeneralDataBean> curveList;//曲线列表
    
	public String getCurveName() {
		return curveName;
	}
	public void setCurveName(String curveName) {
		this.curveName = curveName;
	}
	public Boolean getCurveFlag() {
		return curveFlag;
	}
	public void setCurveFlag(Boolean curveFlag) {
		this.curveFlag = curveFlag;
	}
	public List<GeneralDataBean> getCurveList() {
		return curveList;
	}
	public void setCurveList(List<GeneralDataBean> curveList) {
		this.curveList = curveList;
	}
}
