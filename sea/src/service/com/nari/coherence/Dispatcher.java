package com.nari.coherence;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.nari.fe.commdefine.task.Response;

/**
 * @author 陈建国
 *
 * @创建时间:2010-1-20 下午02:22:18
 *
 * @类描述:
 *	
 */
public class Dispatcher {

private static ConcurrentHashMap<Long,IFuture> response = new ConcurrentHashMap<Long,IFuture>();
	
	public static void notify(Object key , Response value){
		System.out.println("***************"+value.getTaskId()+"*****notify时间*******************");
		Long id = (Long)key;
		IFuture future = response.get(id);
		if(null != future){
			future.setResult(value);
		}
	}
	/**黄轩加，清除列表中的所有taskId**/
	public static void removeTaskIds(List<Long> taskIds){
		for (Long id : taskIds) {
			response.remove(id);
		}
		
	}
	/***黄轩加，清除某个taskId**/
	public static void removeId(Long id){
		response.remove(id);
	}
	
	public static void sendMessage(IFuture future){
		
		//将数据发送出去，得到对应的ID
		LinkedList<Long> taskIDs = future.getTaskIDs();
		for(Long id:taskIDs){
			response.put(id , future);
		}
	}
}
