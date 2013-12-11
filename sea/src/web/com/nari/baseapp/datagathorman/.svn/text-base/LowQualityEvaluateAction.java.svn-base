package com.nari.baseapp.datagathorman;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.baseapp.datagatherman.LowQualityEvaluate;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.task.HisDataItem;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
import com.nari.privilige.PSysUser;

/**
 * 低压采集质量action
 * @author YuTao
 *
 */
public class LowQualityEvaluateAction extends BaseAction {

	private Logger logger = Logger.getLogger(this.getClass());
	
	private LowQualityEvaluateManager lowQualityEvaluateManager;
	public void setLowQualityEvaluateManager(
			LowQualityEvaluateManager lowQualityEvaluateManager) {
		this.lowQualityEvaluateManager = lowQualityEvaluateManager;
	}
	
	private boolean success = true;
	
	private List<LowQualityEvaluate> lowQualityList;
	private List<LowQualityEvaluate> chartList;
	private String type;
	private String qualityValue;
	private String startDate;
	private String endDate;
	private int myDays;
	private String date;
	private String tmnlNo;
	
	private String regSn;	//注册序号
	private String tmnlAssetNo;		//集中器终端资产号
	private String msg= "";
	private String queryDate;
	
	private List<LowQualityEvaluate> lowqeList;

	/**
	 * 查询失败的用电客户信息
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public String queryFailList() throws Exception {
		this.logger.debug("查询集抄用户失败记录");	
		this.lowQualityList = this.lowQualityEvaluateManager.findFail(date, tmnlNo);
		return SUCCESS;
	}
	
	/**
	 * 补抄电表数据
	 * @return
	 * @throws Exception
	 */
	public String queryMeterDataByDate() throws Exception{
		
		lowqeList = new ArrayList<LowQualityEvaluate>();
		
		// 是否选中召测终端判断
		if (this.regSn == null || "".equals(this.regSn)) {
			this.msg = "请选择用户";
			return SUCCESS;
		}
		// 前置集群服务通信状态
		if (!TaskDeal.isFrontAlive()) {
			this.msg = "前置集群服务中断";
			return SUCCESS;
		}
		try{
			TaskDeal taskDeal = new TaskDeal();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String currTime = "";
			
			List<String> tmnlAssetNoList = new ArrayList<String>();
			tmnlAssetNoList.add(tmnlAssetNo);

			List<HisDataItem> hisDataItems = new ArrayList<HisDataItem>();
			HisDataItem rdi = new HisDataItem();
			rdi.setPoint(Short.parseShort(regSn));
			
			List<Item> items = new ArrayList<Item>();
			Item it = new Item("1131FF");
			items.add(it);
			rdi.setCodes((ArrayList<Item>) items);
			rdi.setFreezingType((short)4);
			rdi.setStartTime(sdf2.parse(this.queryDate));
			rdi.setEndTime(sdf2.parse(this.queryDate));
			hisDataItems.add(rdi);
			
			taskDeal.callHisData(tmnlAssetNoList, hisDataItems);
			List<Response> ttr = taskDeal.getAllResponse(30);
			
			
			
			Map<Short, String> statusMaps = getStatusMaps();
			
			if(ttr!=null && ttr.size()>0){
				for(Response re : ttr){
					if(re == null) continue;
					List<ResponseItem> list  = re.getResponseItems();
					if(re.getTaskStatus() == 3 && list != null && list.size()>0){
						String value = list.get(0).getValue();
						String[] values = value.split(",");
						if(values == null) return null;
						
						LowQualityEvaluate lowqeDto = new LowQualityEvaluate();
						lowqeDto.setRegSn(Integer.valueOf(regSn));
						lowqeDto.setCommAddr1(values[0]);
						if(values.length>1)
							lowqeDto.setMeterData(values[1]);
						if(values.length>2){
							String date1 = "20"+values[2];
							currTime = sdf1.format(sdf.parse(date1));
						}
						lowqeDto.setDataDate(currTime);
						lowqeList.add(lowqeDto);
					}else{
						LowQualityEvaluate lowqeDto = new LowQualityEvaluate();
						lowqeDto.setRegSn(Integer.valueOf(regSn));
						lowqeDto.setMeterData(statusMaps.get(re.getTaskStatus()));
						lowqeDto.setDataDate(currTime);
						lowqeList.add(lowqeDto);
					}
				}
			}else{
				LowQualityEvaluate lowqeDto = new LowQualityEvaluate();
				lowqeDto.setRegSn(Integer.valueOf(regSn));
				lowqeDto.setMeterData(statusMaps.get(5));
				lowqeDto.setDataDate(currTime);
				lowqeList.add(lowqeDto);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 查询集抄用户采集成功率
	 * @param startDate
	 * @param endDate
	 * @param type 节点类型
	 * @param qualityValue 节点值
	 * @return
	 * @throws Exception
	 */
	public String querySuccRate() throws Exception {
		this.logger.debug("查询集抄用户采集成功率");
		
		PSysUser pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		if(pSysUser == null){
			return SUCCESS;
		}
		this.lowQualityList = this.lowQualityEvaluateManager.getRateByNode(pSysUser, startDate,
					endDate, qualityValue, type, myDays);
		
//		Map<String, LowQualityEvaluate> succMap = new HashMap<String, LowQualityEvaluate>();
//		for(LowQualityEvaluate l:lowQualityList){
//			LowQualityEvaluate ll = succMap.get(l.getConsNo());
//			if(ll != null){
//				Double scount = Double.valueOf(l.getScount()) + ll.getScount();
//				Double tcount = Double.valueOf(l.getTcount()) + ll.getTcount();
//				String rate = String.valueOf((scount/tcount*100));
//				ll.setSuccRate(rate);
//				succMap.put(ll.getConsNo(), ll);
//			}else{
//				succMap.put(l.getConsNo(), l);
//			}
//		}
//		if(succMap.values()!=null)
//			chartList = new ArrayList<LowQualityEvaluate>();
//			chartList.addAll(succMap.values());
		
		if(this.lowQualityList.size()>0){
			String tmnlAssetNo = lowQualityList.get(0).getTmnlAssetNo();
			String consNo = lowQualityList.get(0).getConsNo();
			String consName = lowQualityList.get(0).getConsName();
			Long totalNum = lowQualityList.get(0).getTcount();
			
			chartList = this.lowQualityEvaluateManager.getRateByTmnl(pSysUser, startDate, 
					endDate, tmnlAssetNo, consNo, consName, totalNum/myDays);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查询终端集抄用户采集成功率
	 * @param startDate
	 * @param endDate
	 * @param type 节点类型
	 * @param qualityValue 节点值
	 * @return
	 * @throws Exception
	 */
	public String queryTmnlSuccRate() throws Exception {
		this.logger.debug("查询终端集抄用户采集成功率");
		
		PSysUser pSysUser =(PSysUser) getSession().getAttribute("pSysUser");
		if(pSysUser == null){
			return SUCCESS;
		}
		
		String[] values = this.qualityValue.split(",");
		if(values==null || values.length<4) return SUCCESS;
		
		String tmnlAssetNo = values[0];
		String consNo = values[1];
		String consName = values[2];
		Long totalNum = Long.valueOf(values[3]);
		
		chartList = this.lowQualityEvaluateManager.getRateByTmnl(pSysUser, startDate, 
				endDate, tmnlAssetNo, consNo, consName, totalNum/myDays);
		
		return SUCCESS;
	}
	
	/**
	 * 获取任务执行后返回的状态码表
	 * @return
	 */
	private Map<Short,String> getStatusMaps(){
		Map<Short, String> statusMaps = new HashMap<Short, String>();
		statusMaps.put((short) 0, "终端响应超时");
		statusMaps.put((short) 1, "终端不在线");
		statusMaps.put((short) 2, "终端无应答");
		statusMaps.put((short) 3, "成功");
		statusMaps.put((short) 4, "失败");
		statusMaps.put((short) 5, "前置集群服务中断");
		
		return statusMaps;
	}
	
	public List<LowQualityEvaluate> getLowqeList() {
		return lowqeList;
	}

	public void setLowqeList(List<LowQualityEvaluate> lowqeList) {
		this.lowqeList = lowqeList;
	}

	public List<LowQualityEvaluate> getLowQualityList() {
		return lowQualityList;
	}
	public void setLowQualityList(List<LowQualityEvaluate> lowQualityList) {
		this.lowQualityList = lowQualityList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getQualityValue() {
		return qualityValue;
	}
	public void setQualityValue(String qualityValue) {
		this.qualityValue = qualityValue;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public List<LowQualityEvaluate> getChartList() {
		return chartList;
	}
	public void setChartList(List<LowQualityEvaluate> chartList) {
		this.chartList = chartList;
	}
	public int getMyDays() {
		return myDays;
	}
	public void setMyDays(int myDays) {
		this.myDays = myDays;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTmnlNo() {
		return tmnlNo;
	}

	public void setTmnlNo(String tmnlNo) {
		this.tmnlNo = tmnlNo;
	}

	public String getRegSn() {
		return regSn;
	}

	public void setRegSn(String regSn) {
		this.regSn = regSn;
	}

	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}

	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getQueryDate() {
		return queryDate;
	}

	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	
}
