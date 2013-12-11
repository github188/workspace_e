package com.nari.coherence;

import org.apache.log4j.Logger;

import com.nari.ami.cache.event.AbstractCacheMapListener;
import com.nari.fe.commdefine.task.Response;
/**
 * @author 陈建国
 *
 * @创建时间:2010-1-20 下午02:18:02
 *
 * @类描述: Coherence事件监听
 *	
 */
public class FrontListern extends AbstractCacheMapListener<Response> {

	private static final Logger log = Logger.getLogger(FrontListern.class);
	
	@Override
	public void EntryDelete(Object key, Response value) {
		log.debug("收到监听Delete ："+value.getTaskId());
	}

	@Override
	public void EntryInsert(Object key, Response value) {
		log.debug("收到监听Insert :======" + value.toString());
		Dispatcher.notify(key, value);
		
	}

	@Override
	public void EntryUpdate(Object key, Response oldValue, Response newValue) {
		log.debug("收到监听:  new:"+newValue.toString());
		Dispatcher.notify(key,newValue);
	}

}
