package com.nari.coherence;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.nari.fe.commdefine.param.TaskTermRelations;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.ResponseItem;
/**
 * @author 陈建国
 *
 * @创建时间:2010-1-20 下午02:24:14
 *
 * @类描述:
 *	
 */
public class IFuture {

	private static final Logger log = Logger.getLogger(IFuture.class);
	private LinkedBlockingQueue<Response> blockingQueue = null;
	private LinkedList<Long> taskIDs = new 	LinkedList<Long>();

	public IFuture() {
		blockingQueue = new LinkedBlockingQueue<Response>();		
	}
	
	public void addID(long id){
		taskIDs.add(id);
	}
	
	public LinkedList<Long> getTaskIDs(){
		return taskIDs;
	}

	public void setResult(Response result) {
//		log.debug("set======================\n" + result);
		try {
			blockingQueue.put(result);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public synchronized Response getResponse() {
		Response result = blockingQueue.poll();		
		return result;
	}

	public Response getResponse(long millSecondsTime) {

		Response result = null;
		try {
			result = blockingQueue.poll(millSecondsTime, TimeUnit.MILLISECONDS);
//			log.debug("========get\n" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		

		return result;
	}

	
	public List<Response> getRtnList(int second,List<TaskTermRelations> ttr){
		List <Response> list = new ArrayList<Response>();
		for(int i = 0 ; i < this.getTaskIDs().size(); i++){
			int waitTime = second/this.getTaskIDs().size();
			Response response = null;
			
			response = this.getResponse(waitTime * 1000);
			
			if(null != response){
				list.add(response);
			}
		}
		
		log.debug("从缓存返回Response列表：" + list.toString());
		
		if(ttr.size() > list.size()){
			/**
			 * 将未返回Response对象的任务剔除
			 */
//			for(Iterator<TaskTermRelations> it = ttr.iterator() ; it.hasNext(); ){
//				TaskTermRelations tt = it.next();
//				for(int j = 0 ; j < list.size() ; j++){
//					if(tt.getTmnlAssetNo().equals(list.get(j).getTmnlAssetNo())){
//						it.remove();
//					}
//				}
//			}
			
			for(int i = ttr.size()-1; i>=0 ;i--){
				TaskTermRelations tt = ttr.get(i);
				for(int j = 0 ; j < list.size(); j++){
					if(tt.getTmnlAssetNo().equals(list.get(j).getTmnlAssetNo())){
						ttr.remove(tt);
					}
				}
			}
			
			log.debug("未返回Response对象的终端，任务关系列表：" + ttr.toString());
			
			/**
			 * 处理未返回Response对象的任务
			 */
			for (int i = 0 ; i < ttr.size() ; i++){
				Response response = new Response();
				response.setTaskId(this.getTaskIDs().get(i));
				response.setTmnlAssetNo(ttr.get(i).getTmnlAssetNo());
				response.setTaskStatus((short)0);
				list.add(response);
			}
		}
		
		return list;
	}


	/**
	 * @desc 返回 pn
	 * @param second
	 * @param ttr
	 * @return
	 */
	public List<Response> getAllRtnList(int second,List<TaskTmnlPnRelation> ttpr){
		
		log.debug("下发长度：" + ttpr.size());
		List <Response> list = new ArrayList<Response>();
		try {
			for(int i = 0 ; i < this.getTaskIDs().size(); i++){
				int waitTime = second/this.getTaskIDs().size();
				Response response = null;
				
				response = this.getResponse(waitTime * 1000);
				
				if(null != response){
					//黄轩加，如果是最后一帧了，去掉taskId
					if(response.isContinue()){
						Dispatcher.removeId(response.getTaskId());			
					}
					list.add(response);
				}
			}
		} catch (Exception e) {
		}finally{
			//黄轩加，在召测结束后统一去掉taskId
			Dispatcher.removeTaskIds(this.getTaskIDs());
		}
		
		
		log.debug("从缓存返回Response列表长度：" + list.size());
		log.debug("从缓存返回Response列表：" + list.toString());
		
		if(ttpr.size() > list.size()){
			/**
			 * 将未返回Response对象的任务剔除
			 */
//			for(Iterator<TaskTmnlPnRelation> it = ttpr.iterator() ; it.hasNext() ;){
//				TaskTmnlPnRelation ttp = it.next();
//				for(int j = 0 ; j < list.size(); j++){
//					if(ttp.getTtr().getTmnlAssetNo().equals(list.get(j).getTmnlAssetNo())){
//						it.remove();
//					}
//				}
//			}
			
			for (int i = ttpr.size()-1; i >=0; i--) {
				TaskTmnlPnRelation ttp = ttpr.get(i);
				for(int j = 0 ; j < list.size(); j++){
					if(ttp.getTtr().getTmnlAssetNo().equals(list.get(j).getTmnlAssetNo())){
						ttpr.remove(ttp);
					}
				}
			}
			
			log.debug("未返回Response对象的终端，任务，pn关系列表长度：" + ttpr.size());
			log.debug("未返回Response对象的终端，任务，pn关系列表：" + ttpr.toString());
			
			
			/**
			 * 处理未返回Response对象的任务
			 */
			for(int i = 0 ; i < ttpr.size(); i++){
				Response response = new Response();
				ResponseItem reItem = new ResponseItem();
				List<ResponseItem> responseItems = new ArrayList<ResponseItem>();
				
				reItem.setPn(ttpr.get(i).getPn());
				responseItems.add(reItem);
				response.setResponseItems(responseItems);
				response.setTaskId(this.getTaskIDs().get(i));
				response.setTmnlAssetNo(ttpr.get(i).getTtr().getTmnlAssetNo());
				response.setTaskStatus((short)0);
				list.add(response);
			}
		}
		
		log.debug("返回长度： " + list.size());
		return list;
	}
}
