package com.nari.qrystat.colldataanalyse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.util.DateUtil;

/**
 * 日用电负荷监测Action
 * @author 姜炜超
 */
public class CurrLoadMonitorAction extends BaseAction{
	//定义日志
	private static final Logger logger = Logger.getLogger(CurrLoadMonitorAction.class);
	
	private CurrLoadMonitorManager currLoadMonitorManager;
	
	//成功返回标记
	public boolean success = true;
	
	private Date queryDate;//当前日期
	private String orgNo;//供电单位编号
	private String orgType;//供电单位类型
	private List<String>  orgList; //供电单位列表
	private List<CurrLoadMonitorDto> clmList;//日用电负荷监测Grid数据
	private List<CurrLoadMonitorCurveListBean> curveRealList = new ArrayList<CurrLoadMonitorCurveListBean>();//实时曲线列表
	private List<CurrLoadMonitorCurveListBean> curveStatList = new ArrayList<CurrLoadMonitorCurveListBean>();;//冻结曲线列表
	
	private List<CurrLoadMonitorDto> clmTimeModelList;//时标，用于显示
	private boolean statCheckValue;//是否选择冻结数据
	private boolean realCheckValue;//是否显示实时数据

	/**
	 * 查询日用电负荷监测数据
	 * @return String 
	 * @throws Exception
	 */
    public String loadGridData() throws Exception{
    	logger.debug("当日用电负荷监测开始");
    	
    	if(null==this.orgList||0==this.orgList.size()){
			return SUCCESS;	
    	}
    	
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
    	
		//循环解析数据，放入list，进行数据
    	List<CurrLoadMonitorDto> list = new ArrayList<CurrLoadMonitorDto>();
    	for (int i = 0; i < this.orgList.size(); i++) {
			String[] orgs = this.orgList.get(i).split("!");
			if(null == orgs[0] || null == orgs[1] || "".equals(orgs[0])||"".equals(orgs[1])){
				continue;
			}
			CurrLoadMonitorDto dto = new CurrLoadMonitorDto();
			dto.setOrgNo(orgs[0]);
			dto.setOrgType(orgs[1]);
			dto.setOrgName(orgs[2]);
			list.add(dto);
    	}
    	    	
    	clmList = currLoadMonitorManager.findCLMGridInfoByCond(list, DateUtil.dateToString(queryDate), pSysUser);
    	
    	clmTimeModelList = currLoadMonitorManager.getTimeModelList();
    	CurrLoadMonitorCurveListBean realBean = null;
    	CurrLoadMonitorCurveListBean statBean = null;
    	//循环查询曲线，按照设计只能查询4个单位的实时和冻结曲线
    	for(int j = 0; j < list.size(); j++){
    		CurrLoadMonitorDto bean = (CurrLoadMonitorDto)list.get(j);
    		
    		if(realCheckValue){//是否显示实时数据
	    		realBean = new CurrLoadMonitorCurveListBean();
	    		List<CurrLoadMonitorCurveBean> realList = currLoadMonitorManager.findCLMRealCurInfoByCond(bean, DateUtil.dateToString(queryDate), pSysUser);
	    		realBean.setList(realList);
	    		if(null != realList && 0 < realList.size()){
	    			realBean.setName(bean.getOrgName()+"实时曲线");
	    			realBean.setOrgName(bean.getOrgName());
	    			realBean.setOrgNo(bean.getOrgNo());
				}	
	    		curveRealList.add(realBean);
    		}
    		
    		if(statCheckValue){//是否显示冻结数据
	    		List<CurrLoadMonitorCurveBean> statList = currLoadMonitorManager.findCLMVSTATCurInfoByCond(bean, DateUtil.dateToString(queryDate), pSysUser);
	    		statBean = new CurrLoadMonitorCurveListBean();
	    		statBean.setList(statList);
	    		if(null != statList && 0 < statList.size()){
	    			statBean.setName(bean.getOrgName()+"冻结曲线");
	    			statBean.setOrgName(bean.getOrgName());
	    			statBean.setOrgNo(bean.getOrgNo());
				}
	    		curveStatList.add(statBean);
    		}
    	}
    	logger.debug("当日用电负荷监测结束");
    	return SUCCESS;
    }
    
    /**
	 * 左边树点击加载供电单位信息
	 * @return String 
	 * @throws Exception
	 */
    public String loadOrgInfo() throws Exception{
    	if(null == orgNo || "".equals(orgNo)){
    		return SUCCESS;
    	}
    	PSysUser pSysUser = getPSysUser();
		if(null == pSysUser){
			return SUCCESS;
		}
    	clmList = currLoadMonitorManager.findOrgInfo(orgNo, orgType,pSysUser);
    	return SUCCESS;
    }

	public void setCurrLoadMonitorManager(
			CurrLoadMonitorManager currLoadMonitorManager) {
		this.currLoadMonitorManager = currLoadMonitorManager;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Date getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public List<CurrLoadMonitorDto> getClmList() {
		return clmList;
	}
	public void setClmList(List<CurrLoadMonitorDto> clmList) {
		this.clmList = clmList;
	}

	public List<String> getOrgList() {
		return orgList;
	}

	public void setOrgList(List<String> orgList) {
		this.orgList = orgList;
	}

	public List<CurrLoadMonitorDto> getClmTimeModelList() {
		return clmTimeModelList;
	}

	public void setClmTimeModelList(List<CurrLoadMonitorDto> clmTimeModelList) {
		this.clmTimeModelList = clmTimeModelList;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public boolean isStatCheckValue() {
		return statCheckValue;
	}

	public void setStatCheckValue(boolean statCheckValue) {
		this.statCheckValue = statCheckValue;
	}

	public boolean isRealCheckValue() {
		return realCheckValue;
	}

	public void setRealCheckValue(boolean realCheckValue) {
		this.realCheckValue = realCheckValue;
	}

	public List<CurrLoadMonitorCurveListBean> getCurveRealList() {
		return curveRealList;
	}

	public void setCurveRealList(List<CurrLoadMonitorCurveListBean> curveRealList) {
		this.curveRealList = curveRealList;
	}

	public List<CurrLoadMonitorCurveListBean> getCurveStatList() {
		return curveStatList;
	}

	public void setCurveStatList(List<CurrLoadMonitorCurveListBean> curveStatList) {
		this.curveStatList = curveStatList;
	}
}
