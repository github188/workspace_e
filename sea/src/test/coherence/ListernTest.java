package coherence;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.cache.event.EventType;

import com.nari.fe.commdefine.task.Response;
import com.nari.fe.main.Listern;

/**
 * @author 陈建国
 *
 * @创建时间:2009-12-8 下午07:11:01
 *
 * @类描述: 王总发的前置与web的交互演示 (Listern.java,ListernTest.java,Main.java)
 *	
 */
public class ListernTest {
	
	
	public static void main(String args[]) {
	    String dbHost = "localhost";

	
	    try {
	      if (args.length > 0) {
	        dbHost = args[0];
	      }
	      IStatement statement = CoherenceStatement.getSharedInstance();
	      statement.addCacheMapListerener(Response.class, new Listern(),EventType.ALL);
	      while(true){
	    	  try{
	    		Thread.sleep(1000);  
	    	  }
	    	  catch(Exception e){
	    		  
	    	  }
	      }
	    }
	    catch(Exception e){
	    	
	    }
	}
}
