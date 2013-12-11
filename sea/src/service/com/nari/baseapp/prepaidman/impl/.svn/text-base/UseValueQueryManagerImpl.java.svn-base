package com.nari.baseapp.prepaidman.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nari.baseapp.prepaidman.IUseValueQueryDao;
import com.nari.baseapp.prepaidman.IUseValueQueryManager;
import com.nari.baseapp.prepaidman.UseValueResult;
import com.nari.coherence.TaskDeal;
import com.nari.fe.commdefine.task.DataCode;
import com.nari.fe.commdefine.task.DbData;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.RealDataItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public class UseValueQueryManagerImpl implements IUseValueQueryManager {
	
	private IUseValueQueryDao iUseValueQueryDao;
	
	public void setiUseValueQueryDao(IUseValueQueryDao iUseValueQueryDao) {
		this.iUseValueQueryDao = iUseValueQueryDao;
	}

	/**
     * 查询用户余额
     * @param orgName
     * @param consNo
     * @param appNo
     * @param terminalAddr
     * @param startDate
     * @param endDate
     * @param start
     * @param limit
     * @return
     * @throws DBAccessException
     */
	 public Page<UseValueResult> valueQuery(
			 String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException{
		 try{
			  return this.iUseValueQueryDao.valueQueryBy(orgName, consNo, appNo, terminalAddr, startDate, endDate, start, limit);
		 }catch(Exception e) {
				throw new DBAccessException("查询用户余额出错！");
		 } 
	 }
	 
	/**
	 *  查询剩余值明细
	 * @param tmnlAssetNo
	 * @param totalNo
	 * @param orgName
	 * @param consNo
	 * @param appNo
	 * @param terminalAddr
	 * @param startDate
	 * @param endDate
	 * @param start
	 * @param limit
	 * @return
	 * @throws DBAccessException
	 */
	 public Page<UseValueResult> valueDetailQuery(
			 String tmnlAssetNo,Short totalNo,
			 String orgName,String consNo,String appNo,
			 String terminalAddr,String startDate,String endDate, long start, long limit)throws DBAccessException{
		 try{
			  return this.iUseValueQueryDao.valueDetailQueryBy(tmnlAssetNo, totalNo, orgName, consNo, appNo, terminalAddr, startDate, endDate, start, limit);
		 }catch(Exception e) {
				throw new DBAccessException("查询剩余值明细出错！");
		 } 
	 }
	 
	 /**
	  * 召测剩余值
	  * @param tmnlList
	  * @param taskSecond
	  * @return
	  * @throws DBAccessException
	  */
	public List<UseValueResult> fetchUseValue(List<UseValueResult> tmnlList,
			Integer taskSecond) throws Exception {
		
		try{
			//操作时间
			Date opTime =new Date();
			
			TaskDeal taskDeal=new TaskDeal();
			
			UseValueResult flagBean = tmnlList.get(0);
			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			List<String> tmnlAssetNoList = new ArrayList<String>();
			
			for (int n = 0; n < tmnlList.size(); n++) {
				UseValueResult bean = tmnlList.get(n);
				if(n==0) {
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
					//设置参数
					realDataItems.add(setUiRealDataItem(bean));
					continue;
				}
				
				//参数项设置
				if(!bean.getTmnlAssetNo().equals(flagBean.getTmnlAssetNo())){
					taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
					
					realDataItems = new ArrayList<RealDataItem>();
					tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add(bean.getTmnlAssetNo());
				}
				
				realDataItems.add(setUiRealDataItem(bean));
				//召测最后一个终端
				if(n== tmnlList.size()-1) {
					taskDeal.callRealData(tmnlAssetNoList, realDataItems);//召测
				}
			}		
			
			List<Response> list  = taskDeal.getAllResponse(taskSecond);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//召测结果
			List<UseValueResult> fetchResultList = new ArrayList<UseValueResult>();
			
			if (null != list && 0 < list.size()) {
				for (int i = 0; i < list.size(); i++) {
					Response res  = (Response) list.get(i);
					Short Status = res.getTaskStatus();
					if (Status == 3&& res.getErrorCode()==null){//返回状态为成功时，设置值返回前台
						List<DbData> datas = res.getDbDatas();
						if(datas!= null && datas.size()>0){							
							for (int j = 0; j < datas.size(); j++) {
								List<DataCode> dataCodes = datas.get(j).getDataCodes();
								if(dataCodes!= null && dataCodes.size()>0) {
									UseValueResult	useValue = new UseValueResult();
									useValue.setKeyId(res.getTmnlAssetNo()+String.valueOf(datas.get(j).getPn()));
									useValue.setTmnlAssetNo(res.getTmnlAssetNo());
									useValue.setTotalNo((short)datas.get(j).getPn());
									useValue.setOpTime(sdf.format(opTime));
									for (int k = 0; k < dataCodes.size();k++) {
										DataCode dataCode = dataCodes.get(k);
											useValue.setUseValue("-9999.0".equals(dataCode.getValue().toString())?"":dataCode.getValue().toString());
											continue;
									}
									fetchResultList.add(useValue);
									UseValueResult fetchTmnl = getTmnlFromList(tmnlList, res.getTmnlAssetNo());
									this.iUseValueQueryDao.addUseValue(fetchTmnl.getOrgNo(), fetchTmnl.getAppNo(), fetchTmnl.getConsNo(), 
											fetchTmnl.getTerminalId(),fetchTmnl.getTotalNo(), fetchTmnl.getMeterId(), useValue.getUseValue(), Status, opTime, "");
								}
							}
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
	
    protected RealDataItem setUiRealDataItem(UseValueResult bean)  throws Exception{
    	try{
	    	RealDataItem ritem = new RealDataItem();
			ArrayList<Item> codes = new ArrayList<Item>();
			
			Item item = null;
			item = new Item("32111F");
			codes.add(item);
			
			ritem.setCodes(codes);
			ritem.setPoint(bean.getTotalNo());
			return ritem;
    	}catch (Exception e) {
			e.printStackTrace();
		}	
    	return null;
    }
	
	/**
	  * 召测剩余值（全选）
	  * @param tmnlList
	  * @param taskSecond
	  * @return
	  * @throws DBAccessException
	  *//*
	public void fetchAllUseValue(String orgName, String consNo, String appNo,
			String terminalAddr, String startDate, String endDate,
			Integer taskSecond) throws Exception {
		try{
			//按照条件查询出全选终端
			List<UseValueResult> useValueResultList = this.iUseValueQueryDao
					.valueListQueryBy(orgName, consNo, appNo, terminalAddr,
							startDate, endDate);
			//操作时间
			Date opTime =new Date();
			
			TaskDeal taskDeal=new TaskDeal();
			
			//参数项设置
			List<RealDataItem> realDataItems = new ArrayList<RealDataItem>();
			RealDataItem ritem = new RealDataItem();
			ArrayList<Item> codes = new ArrayList<Item>();
			Item item = new Item("32111F");
			codes.add(item);
			ritem.setCodes(codes);
			ritem.setPoint((short)0);
			realDataItems.add(ritem);
			
			// 终端资产号集合
			List<String> tmnlAssetNoList = null;
			for (int i = 0; i < useValueResultList.size(); i++){
				tmnlAssetNoList = new ArrayList<String>();
				tmnlAssetNoList.add(useValueResultList.get(i).getTmnlAssetNo());	
				// 召测
				taskDeal.callRealData(tmnlAssetNoList, realDataItems);
			}
			List<Response> respList  = taskDeal.getAllResponse(taskSecond);
			if(null != respList && respList.size()>0){
				for (Response res:respList) {
					short statusCode=res.getTaskStatus();
					String useValue="";
					if (3 == statusCode){
						if(null != res.getDbDatas()
							&& 0 < res.getDbDatas().size()
							&& null != res.getDbDatas().get(0).getDataCodes()
							&& 0 < res.getDbDatas().get(0).getDataCodes()
									.size()) {
							useValue = String.valueOf(res.getDbDatas()
									.get(0).getDataCodes().get(0).getValue());
							}
					}
					UseValueResult fetchTmnl = getTmnlFromList(useValueResultList,
							res.getTmnlAssetNo());
					if(null!=fetchTmnl){
//						this.iUseValueQueryDao.updateUseValue(fetchTmnl
//								.getOrgNo(), fetchTmnl.getAppNo(), fetchTmnl.getConsNo(), fetchTmnl.getMeterId(), useValue,
//								statusCode, opTime, "", fetchTmnl.getTerminalId());
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}*/

	/**
	 * 获取终端信息
	 * @param tmnlTotalList
	 * @param tmnlAssetNo
	 * @return
	 */
	public UseValueResult getTmnlFromList(List<UseValueResult> tmnlList, String tmnlAssetNo) {
		if(tmnlAssetNo==null ||"".equals(tmnlAssetNo)) 
			return null;
		for (int i = 0; i < tmnlList.size(); i++) {
			UseValueResult bean = tmnlList.get(i);
			if(tmnlAssetNo.equals(bean.getTmnlAssetNo())) {
				return bean;
			}
		}
		return null;
	}
		 
}
