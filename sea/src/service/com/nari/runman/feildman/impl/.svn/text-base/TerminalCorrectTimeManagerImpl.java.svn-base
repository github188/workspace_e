package com.nari.runman.feildman.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nari.action.baseaction.BaseAction;
import com.nari.baseapp.planpowerconsume.ILOpTmnlLogManager;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.define.FrontConstant;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.orderlypower.CtrlParamBean;
import com.nari.orderlypower.TmnlExecStatusBean;
import com.nari.runman.dutylog.TmnlTime;
import com.nari.runman.feildman.ITerminalCorrectTimeManager;
/**
 * 终端对时service接口实现类
 * @author haihui
 *
 */
public class TerminalCorrectTimeManagerImpl extends BaseAction implements
		ITerminalCorrectTimeManager {
	  
	private ILOpTmnlLogManager iLOpTmnlLogManager;
	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}
	
	/**
	 * 对时
	 * @param tmnlList
	 * @return
	 * @throws Exception
	 */
	public List<TmnlExecStatusBean> correctTmnlTime(List<CtrlParamBean> tmnlList,Integer taskSecond)throws Exception{
		try{
			TaskDeal taskDeal=new TaskDeal();
			Date OpTime=new Date();
			//终端资产号集合
			List<String> tmnlAssetNoList=null;
			// 参数项设置
			List<ParamItem> paramList = null;
			ParamItem pitem=null;
			List<Item> items=null; 
			Item item=null; 
			for (int i = 0; i < tmnlList.size(); i++) {
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlList.get(i).getTmnlAssetNo());
			
				paramList = new ArrayList<ParamItem>();
				pitem = new ParamItem();
				pitem.setFn((short) 31);// 设置FN号  
				pitem.setPoint((short) 0);// 设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
				// 编码对象集合
				items = new ArrayList<Item>();
				item = new Item(tmnlList.get(i).getProtocolCode()+"051F001");
				item.setValue(null);
				items.add(item);		
				pitem.setItems((ArrayList<Item>) items);
				paramList.add(pitem);
			    //下发对时命令
				taskDeal.qstTermParamTaskResult(tmnlAssetNoList,FrontConstant.CONTROL_COMMAND, paramList);
			}
			
			List<Response> respList = taskDeal.getAllResponse(taskSecond);
			//前置机返回结果
			List<TmnlExecStatusBean> execStatusList = new ArrayList<TmnlExecStatusBean>();
			
			//返回执行状态
			TmnlExecStatusBean tmnlExecStatusBean = null;
			if (null != respList && respList.size()>0){
				Response res=null;
				LOpTmnlLog log=null;
				for(int i=0;i < respList.size();i++){
					res=(Response)respList.get(i);
					String tAssetNo=res.getTmnlAssetNo();
					//终端返回状态
					short Status = res.getTaskStatus();
					// 写日志
					log = getLOpTmnlLog();
					log.setOpModule("终端对时");
					log.setOpButton("对时");
					log.setOpType((short)2);
					log.setOpObj((short)1);
					log.setTmnlAssetNo(tAssetNo);
					log.setProtItemNo(getProtocolCodeByTmnlAssetNo(tmnlList,tAssetNo)+"051F001");
					log.setOpTime(OpTime);
					log.setCurStatus(String.valueOf(Status));
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
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 时钟召测
	 * @param tmnlList
	 * @return
	 * @throws Exception
	 */
	public List<TmnlTime> fetchTmnlTime(List<String> tmnlList,Integer taskSecond)throws Exception{
		try{
			TaskDeal taskDeal=new TaskDeal();
			
			//参数项设置
			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			RealDataItem ritem = new RealDataItem();
			ArrayList<Item> codes = new ArrayList<Item>();
			Item item = new Item("4E242F");
			codes.add(item);
			ritem.setCodes(codes);
			ritem.setPoint((short)0);
			realDataItems.add(ritem);
			
			// 终端资产号集合
			List<String> tmnlAssetNoList = null;
			for (int i = 0; i < tmnlList.size(); i++){
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(tmnlList.get(i));	
				// 召测
				taskDeal.callRealData(tmnlAssetNoList, realDataItems);
			}
			List<Response> respList  = taskDeal.getAllResponse(taskSecond);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//召测结果
			List<TmnlTime> fetchResultList = new ArrayList<TmnlTime>();
			
			if(null != respList && respList.size()>0){
				Response  res = null;
				TmnlTime  fetchResult = null;
				for (int i = 0; i < respList.size(); i++) {
					res=(Response)respList.get(i);
					if(null!=res.getDbDatas()&& 0 < res.getDbDatas().size() && res.getTaskStatus()==3){
						if(null!=res.getDbDatas().get(0).getDataCodes()&&0<res.getDbDatas().get(0).getDataCodes().size())
						{
							fetchResult=new  TmnlTime();
							fetchResult.setTmnlAssetNo(res.getTmnlAssetNo());
							fetchResult.setTime(sdf.format(res.getDbDatas().get(0).getDataCodes().get(0).getValue()));
							fetchResultList.add(fetchResult);	
						}
					}
				}
			}
			return fetchResultList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
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

