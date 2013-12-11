package com.nari.test.webserviceclient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.xfire.client.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.nari.util.XMLSwitchUtil;
import com.nari.webservice.IEIGWSForMarketing;



public class TestSayHelloXfire {

	public static void main(String[] args) throws MalformedURLException, Exception {
		test();
//		System.out.println(Long.valueOf("012234"));
//		System.out.println(Long.parseLong("012234"));
//		String protNO ="C0A01";
//		if(protNO.equals("C0A01")||protNO.equals("C0A0B"))
//		{
//			protNO = protNO.replace("0A", "04");
//		}
//		System.out.println(protNO);
	}
//	public static void test() throws MalformedURLException, Exception{
//			
//		Map<String, Object> map = new HashMap<String, Object>() ;
//		XMLSwitchUtil xmlSwitch = new XMLSwitchUtil() ;
//		map.put("CR_ID", "34401");
//		map.put("CP_NO", "1111");
//		map.put("ORG_NO", "1111");
//		map.put("CUST_NO", "1111");
//		map.put("NAME", "1111");
//		map.put("TYPE_CODE", "1111");
//		map.put("PLAN_EXEC_DATE", "");
//			
//		String xxx = xmlSwitch.switchMapToXML(map);
//	    Client client = new Client(new URL("http://localhost:8080/IEIGInterface/service/IEIGWSForMarketing?wsdl"));
//	    client.addOutHandler(new ClientAuthenticationHandler("sea","sea"));
//	    
//		Object[] results = client.invoke("WS_R_CR_CTRL", new Object[] {xxx});
//		System.out.println("第三种调用XFireWS的方法");  
//	}
	
	
	public static void test() throws MalformedURLException, Exception{
		
		//Client client = new Client(new URL("http://localhost:8080/IEIGInterface/service/IEIGWSForMarketing?wsdl"));
		Client client = new Client(new URL("http://192.168.172.206/seacis/service/IEIGWSForMarketing?wsdl"));
		client.addOutHandler(new ClientAuthenticationHandler("sea","sea"));
		String uerid = "1335628";
		//Object[] results  = client.invoke("WS_CONS_OVERDUE",  new Object[]{});
		Object[] results  = client.invoke("WS_CONS_PAY",  new Object[]{uerid});
		for(Object o : results)
		{
			System.out.println(o);
		}
//		String []paths ={"G:/workspace/seacis/WebContent/WEB-INF/config/xFireSpring.xml"};
//		ApplicationContext ctx = new FileSystemXmlApplicationContext(paths);
//		IEIGWSForMarketing ie = (IEIGWSForMarketing) ctx.getBean("ceshiWebService");
//		System.out.println(ie.WS_CONS_OVERDUE());
		
	}
}