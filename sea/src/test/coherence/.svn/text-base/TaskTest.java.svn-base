package coherence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.fe.commdefine.param.TaskTermRelations;
import com.nari.fe.commdefine.task.FuncAggregationImpl;
import com.nari.fe.commdefine.task.HisDataItem;
import com.nari.fe.commdefine.task.IFuncAggregation;
import com.nari.fe.commdefine.task.Item;
import com.nari.fe.commdefine.task.ParamItem;
import com.nari.fe.commdefine.task.Response;
import com.nari.fe.commdefine.task.TaskInfo;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

/**
 * @author 陈建国
 * 
 * @创建时间:2009-12-9 下午02:40:23
 * 
 * @类描述:前置与web的交互接口测试
 * 
 */
public class TaskTest {

	public static void main(String args[]) {
		String dbHost = "localhost";

		try {
			if (args.length > 0) {
				dbHost = args[0];
			}
			IStatement statement = CoherenceStatement.getSharedInstance();
			try {
				// ITaskHandle taskHandle =
				// TaskHandle.getSharedInstance("100000");
				//				
				// Collection tasks = taskHandle.getTasks(5) ;
				// for(Iterator i=tasks.iterator() ;i.hasNext() ;){
				// TermTaskInfo info = (TermTaskInfo)i.next();
				// taskHandle.updateTaskStatus(info.getTaskId(),(short)2) ;
				// System.out.println("ID:"+info.getTaskId() );
				// }

				IFuncAggregation aggreg = new FuncAggregationImpl();
//				int i = 0;
//				while (true) {
//					i++;
//				
//					System.out.println("i:" + i);
					
					//终端资产号集合
					List<String> tmnlAssetNoList = new ArrayList<String>();
					tmnlAssetNoList.add("TMNL000436");
					tmnlAssetNoList.add("TMNL000437");

					//参数项设置
					List<ParamItem> paramList = new ArrayList<ParamItem>();
					
					ParamItem pitem = new ParamItem();
					pitem.setFn((short) 1);//设置FN号
					pitem.setPoint((short) 1);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					//编码对象集合
					List<Item> items = new ArrayList<Item>();
					Item item = new Item("50C2A");
					item.setType("1");
					item.setValue("10");
					items.add(item);

					pitem.setItems((ArrayList<Item>) items);

					paramList.add(pitem);

					//任务终端关联关系
					List<TaskTermRelations> ttr = null;

					// ttr = aggreg.qstTermParamTask(tmnlAssetNoList,
					// FrontConstant.QUERY_PARAMS, paramList) ;

					//历史数据项
					List<HisDataItem> hisItems = new ArrayList<HisDataItem>();
					HisDataItem dataItem = new HisDataItem();

					Calendar cal = Calendar.getInstance();

					//设置透明编码
					dataItem.setCodes((ArrayList<Item>) items);
					dataItem.setPoint((short) 1);//设置point (为测量点号、总加组号、控制轮次、直流模拟量端口号、任务号)
					dataItem.setStartTime(cal.getTime());//数据起始时间
					dataItem.setEndTime(cal.getTime());//数据结束时间
					hisItems.add(dataItem);

					//召唤历史数据
					ttr = aggreg.callHisData(tmnlAssetNoList, hisItems);
					
					System.out.println("历史数据: " + ttr.size());
					for (TaskTermRelations rel : ttr) {
						System.out.println("===========" + rel.getTaskId());
					}
					
					Response response = new Response();
					
					System.out.println("============请求" + response.getTmnlAssetNo());
					/*
					 * Map<Short,List<Item>> t1 = new
					 * HashMap<Short,List<Item>>(); t1.put(new Short((short)1),
					 * null);
					 * 
					 * Map<Short,Map<Short,List<Item>>> t = new
					 * HashMap<Short,Map<Short,List<Item>>>(); t.put(new
					 * Short((short)1),t1); ITaskHandle taskHandle;
					 * 
					 * TaskInfo ti = new TaskInfo(); ti.setPnFuncs(t);
					 * taskHandle = TaskHandle.getSharedInstance("") ; long
					 * taskId =taskHandle.referTask("TMNL000436",FrontConstant.
					 * REQUEST_HIS_DATA, ti);
					 */
//					NamedCache taskInfoCache = CacheFactory
//							.getCache(TaskInfo.class.getName());
//					TaskInfo info = (TaskInfo) taskInfoCache.get(new Long(
//							102000081));
//					if (info != null) {
//						Map<Short, List<Item>> aaa = info.getPnFuncs().get(
//								new Short((short) 1));
//						if (aaa == null)
//							System.out.println("aaa == null");
//						System.out.println("aaa size:" + aaa.size());
//					}
//
//					info = (TaskInfo) taskInfoCache.get(new Long(102000001));
//					if (info != null) {
//						Map<Short, List<Item>> aaa = info.getPnFuncs().get(
//								new Short((short) 1));
//
//						if (aaa == null)
//							System.out.println("aaa == null");
//
//						System.out.println("aaa size:" + aaa.size());
//					}
//
//					info = (TaskInfo) taskInfoCache.get(new Long(102000002));
//					if (info != null) {
//						Map<Short, List<Item>> aaa = info.getPnFuncs().get(
//								new Short((short) 1));
//
//						if (aaa == null)
//							System.out.println("aaa == null");
//
//						System.out.println("aaa size:" + aaa.size());
//					}
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
