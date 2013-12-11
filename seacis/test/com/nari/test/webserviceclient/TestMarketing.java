package com.nari.test.webserviceclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.xfire.client.Client;
import com.nari.util.XMLSwitchUtil;

public class TestMarketing{
	
	/**
	 * 测试终端运行管理
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public static void testIEIGWSForMarketing() throws MalformedURLException, Exception{
		Client client = new Client(new URL("http://10.215.7.50:8080/IEIGInterface/service/IEIGWSForMarketing?wsdl"));
		client.addOutHandler(new ClientAuthenticationHandler("sea","sea"));
	    
	    Map<String, Object> map = new HashMap<String, Object>() ;
		XMLSwitchUtil xmlSwitch = new XMLSwitchUtil() ;
		map.put("APP_NO", "100421046059");
		map.put("TERMINAL_ID", "206445973");
		map.put("TMNL_TASK_TYPE", "01");
		map.put("CONS_CHG_TYPE", "05");
		map.put("METER_FLAG", "1");
		map.put("CONS_NO", "0000003953");
		map.put("TG_ID", "");
		map.put("MP_NO", "00027675529");
		map.put("TYPE_CODE", "04");
		map.put("USAGE_TYPE_CODE", "03");
		map.put("NEW_TERMINAL_ID", "");
		map.put("ELEC_ADDR", "黄河路158#4#楼4-362");
		map.put("CONS_ID", "3350938");
		map.put("CP_NO", ">0000529059");
		map.put("Wkst_app_no", "");
		String xxx = xmlSwitch.switchMapToXML(map);
	    Object[] results = client.invoke("WS_TMNL_TASK_SR",new Object[]{xxx});
	    for(int i=0,len=results.length;i<len;i++){
	    	System.out.println(results[i]);
	    }
	}
	
	/**
	 * 测试终端运行管理通知营销
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public static void testYINXIAO() throws MalformedURLException, Exception{
		Client client = new Client(new URL("http://10.215.25.70:7001/web/services/GenericServer?wsdl"));
		client.addOutHandler(new ClientAuthenticationHandler("epcinf","longshine"));
		
		String path = "epm/epc/inf/service/ArchiveDataService";
		String methodName = "WS_TMNL_DEBUG_INFO";
	    
	    Map<String, Object> map = new HashMap<String, Object>() ;
		XMLSwitchUtil xmlSwitch = new XMLSwitchUtil();
		map.put("TMNL_DEBUG_ID", "100118860061");
		map.put("TERMINAL_ID", "30450668");
		map.put("DEBUG_TIME", "01");
		
		String xxx = xmlSwitch.switchMapToXML(map);
	    Object[] results = client.invoke("invoke",new Object[]{path,methodName,xxx});
	    for(int i=0,len=results.length;i<len;i++){
	    	System.out.println(results[i]);
	    }
	}
	
	public static void main(String[] args) throws MalformedURLException, Exception {
		testIEIGWSForMarketing();
	}
}