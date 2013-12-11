package com.nari.baseapp.planpowerconsume;

import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.orderlypower.WTmnlDto;
import com.nari.privilige.PSysUser;

/** 
 * 作者: 姜海辉
 * 创建时间：2009-12-29 上午09:53:06 
 * 描述：
 */


public class GeneralGridByTreeAction extends BaseAction {
	
	public boolean success = true;
	private String tmnlAssetNo;   //终端资产号
	private String orgNo;        //供电单位编号
	private String orgType;      //供电单位类型
	private String consNo;       //用户编号
    private String nodeType;     //节点类型
    private String lineId;       //线路Id
    private Long schemeId;       //方案ID
    private String groupNo;      //群组编号
    private String subsId;       //变电站标识 
    private List<WTmnlDto> wTmnlDtoList;
    
	private IWTmnlManager iWTmnlManager;
	public void setiWTmnlManager(IWTmnlManager iWTmnlManager) {
		this.iWTmnlManager = iWTmnlManager;
	}

	/**
	 * 取终端信息
	 */
	public String generalGridByTree() throws Exception {
		if(null == nodeType || "".equals(nodeType)){
			return SUCCESS;
   	    }
		try{
			PSysUser pSysUser=(PSysUser) getSession().getAttribute("pSysUser");
			if("usr".equals(this.nodeType)){
				this.wTmnlDtoList =this.iWTmnlManager.queryTmnlByTmnlAssetNo(this.tmnlAssetNo);
	    	}
			else if("org".equals(this.nodeType)){
				this.wTmnlDtoList =this.iWTmnlManager.queryTmnlByOrgNo(this.orgNo,this.orgType,pSysUser);
			}
			else if("line".equals(this.nodeType)){
				this.wTmnlDtoList =this.iWTmnlManager.queryTmnlByLineId(this.lineId,pSysUser);
			}
			else if("cgp".equals(this.nodeType) || "ugp".equals(this.nodeType)){
				this.wTmnlDtoList =this.iWTmnlManager.queryTmnlByGroupNo(this.nodeType,this.groupNo);
			}
			else if("sub".equals(this.nodeType)){
				this.wTmnlDtoList =this.iWTmnlManager.queryTmnlBySubsId(this.subsId,pSysUser);
			}
	//		else if("ctrlScheme".equals(this.nodeType)){
	//		this.wTmnlDtoList = this.iWTmnlManager.queryTmnlBySchemeId(this.schemeType,this.schemeId);
	//		}
			return SUCCESS;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

//	/**
//	 * 取终端总加组信息
//	 */
//	public String generalGridByTreeWithT() throws Exception {
//		if(null == nodeType || "".equals(nodeType)){
//			return ERROR;
//   	    }
//		Page<WTmnlDto> page=new Page<WTmnlDto>();
//		page=this.iWTmnlManager.queryWTmnlByWithT(this.consNo,this.orgNo,this.schemeId,this.nodeType,this.start,this.limit);
//		this.wTmnlDtoList=page.getResult();
//		this.totalCount= page.getTotalCount();
//		return SUCCESS;
//	}
	
	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getConsNo() {
		return consNo;
	}

	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public List<WTmnlDto> getwTmnlDtoList() {
		return wTmnlDtoList;
	}

	public void setwTmnlDtoList(List<WTmnlDto> wTmnlDtoList) {
		this.wTmnlDtoList = wTmnlDtoList;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Long getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
    
}
