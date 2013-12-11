package com.nari.runman.feildman.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.baseapp.planpowerconsume.ILOpTmnlLogManager;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.runman.feildman.ITerminalResetManager;

public class TerminalResetManagerImpl extends BaseAction implements ITerminalResetManager {
	
	private ILOpTmnlLogManager iLOpTmnlLogManager;
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}
	/**
	 * 终端复位
	 * @param tmnlList
	 * @return
	 * @throws Exception
	 */
	public List<TmnlExecStatusBean> tmnlReset(List<CtrlParamBean> tmnlList,Short resetType,Integer taskSecond)throws Exception{
		TaskDeal taskDeal=new TaskDeal();
		Date OpTime=new Date();
		
		// 参数项设置
		List<ParamItem> paramList = new ArrayList<ParamItem>();
		ParamItem pitem = new ParamItem();
		pitem.setFn((short)resetType);// 设置FN号  
		pitem.setPoint((short) 0);// 设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
		// 编码对象集合		
		pitem.setItems(null);
		paramList.add(pitem);
		//终端资产号集合
		List<String> tmnlAssetNoList = null;
		for (int i = 0; i < tmnlList.size(); i++){
			tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(tmnlList.get(i).getTmnlAssetNo());
			 //下发复位命令
			taskDeal.qstTermParamTaskResult(tmnlAssetNoList,FrontConstant.RESET_COMMAND, paramList);
		}
		List<Response> respList = taskDeal.getAllResponse(taskSecond);
		//前置机返回结果
		List<TmnlExecStatusBean> execStatusList = new ArrayList<TmnlExecStatusBean>();
		
		TmnlExecStatusBean tmnlExecStatusBean = null;
		if (null != respList && respList.size()>0){
			Response res=null;
			LOpTmnlLog log = null;
			for(int i=0;i < respList.size();i++){
				res=(Response)respList.get(i);
				String tAssetNo=res.getTmnlAssetNo();
				//终端返回状态
				short Status = res.getTaskStatus();
				// 写日志
				log = getLOpTmnlLog();
				log.setOpModule("终端复位");
				log.setOpButton("复位");
				log.setOpType((short)2);
				log.setOpObj((short)1);
				log.setTmnlAssetNo(tAssetNo);
				log.setProtocolNo(getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo)+"010"+String.valueOf(resetType));
				log.setCurStatus(String.valueOf(Status));
				log.setOpTime(OpTime);
				this.iLOpTmnlLogManager.saveTmnlLog(log);
				
				tmnlExecStatusBean = new TmnlExecStatusBean();
				tmnlExecStatusBean.setKeyId(tAssetNo);
				if (Status == 3){
					tmnlExecStatusBean.setExecFlag("1");
				}
				else
					tmnlExecStatusBean.setExecFlag("0");
				execStatusList.add(tmnlExecStatusBean);
			}
		}
		else{
			for(int i=0;i < tmnlList.size();i++){
				tmnlExecStatusBean = new TmnlExecStatusBean();
				tmnlExecStatusBean.setKeyId(tmnlList.get(i).getTmnlAssetNo());
				tmnlExecStatusBean.setExecFlag("0");
				execStatusList.add(tmnlExecStatusBean);
			}
		}
        return execStatusList;
	}
	
	/**
	 * 根据终端资产编号获取终端规约编码
	 * @param tmnlList
	 * @param tmnlAssetNo
	 * @return
	 */
	public String getProtocolCodeByTmnlAssetNo(List<CtrlParamBean> tmnlList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) return "";
		for (int i = 0; i < tmnlList.size(); i++) {
			CtrlParamBean bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean.getProtocolCode();
			}
		}
		return "";
	}

}
