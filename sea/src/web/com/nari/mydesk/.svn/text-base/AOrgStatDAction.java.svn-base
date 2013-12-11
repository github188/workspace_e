package com.nari.mydesk;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;

public class AOrgStatDAction extends BaseAction {
	private AOrgStatDManager aOrgStatDManager;
	private AOrgStatD aOrgStatD;
	private MainCurve mainCurve;
	private List<MainCurve> mainCurveList;
	private List<AOrgStatD> aOrgStatDList;
	private String orgNo;
	private String orgType;
	private String staffNo;
	private PSysUser pSysUser; 
	private boolean success = true;
	
	
	public MainCurve getMainCurve() {
		return mainCurve;
	}
	public void setMainCurve(MainCurve mainCurve) {
		this.mainCurve = mainCurve;
	}
	public List<MainCurve> getMainCurveList() {
		return mainCurveList;
	}
	public void setMainCurveList(List<MainCurve> mainCurveList) {
		this.mainCurveList = mainCurveList;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public PSysUser getpSysUser() {
		return pSysUser;
	}
	public void setpSysUser(PSysUser pSysUser) {
		this.pSysUser = pSysUser;
	}
	public void setaOrgStatDManager(AOrgStatDManager aOrgStatDManager) {
		this.aOrgStatDManager = aOrgStatDManager;
	}
	public AOrgStatD getaOrgStatD() {
		return aOrgStatD;
	}
	public void setaOrgStatD(AOrgStatD aOrgStatD) {
		this.aOrgStatD = aOrgStatD;
	}
	public List<AOrgStatD> getaOrgStatDList() {
		return aOrgStatDList;
	}
	public void setaOrgStatDList(List<AOrgStatD> aOrgStatDList) {
		this.aOrgStatDList = aOrgStatDList;
	}
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	
	public String queryAOrgStatD(){
		try{
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			staffNo = pSysUser.getStaffNo();
			orgNo = pSysUser.getOrgNo();
			orgType = this.aOrgStatDManager.getAOrgStatDOrgType(orgNo).get(0).getOrgType();
			if(orgType.equals("02")){
				aOrgStatDList = this.aOrgStatDManager.getAOrgStatDByOrgNo(orgNo, orgType);			
			}else{
				aOrgStatDList = this.aOrgStatDManager.getAOrgStatDBystaffNo(staffNo,orgType);
			}
//			aOrgStatDList = this.aOrgStatDManager.getAOrgStatDBystaffNo(staffNo);
//			if(aOrgStatDList.size()>0){
//				orgNo =aOrgStatDList.get(0).getOrgNo();
//				//this.setOrgNo(orgNo);
//				orgType = aOrgStatDList.get(0).getOrgType();
//				//this.setOrgType(orgType);
//				if(orgNo != null && orgType != null && orgType.equals("02")){
//					aOrgStatDList = this.aOrgStatDManager.getAOrgStatDByOrgNo(orgNo, orgType);
//				}				
//			}		
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String queryMainCurve(){
		try{
			pSysUser =(PSysUser)super.getSession().getAttribute("pSysUser");
			staffNo = pSysUser.getStaffNo();
			aOrgStatDList = this.aOrgStatDManager.getAOrgStatDBystaffNo(staffNo,orgType);
			if(aOrgStatDList.size()>0){
				orgNo =aOrgStatDList.get(0).getOrgNo();
				//this.setOrgNo(orgNo);
				orgType = aOrgStatDList.get(0).getOrgType();
				System.out.println("orgType：" + orgType);
				//this.setOrgType(orgType);
				if(orgNo != null && orgType != null){
					mainCurveList = this.aOrgStatDManager.getMainCurve(orgNo,orgType);
				}
				
			}
			
		}catch(Exception e){
		e.printStackTrace();
		}
		return SUCCESS;
	}
}
