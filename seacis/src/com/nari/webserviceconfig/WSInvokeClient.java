package com.nari.webserviceconfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.log4j.Logger;

import com.nari.global.Constant;
import com.nari.service.MarketTerminalConfigManager;

/**
 * 作为客户端调用营销接口
 * @author HengC
 *
 */
@SuppressWarnings("unused")
public class WSInvokeClient{


	private static Logger logger = Logger.getLogger(WSInvokeClient.class); 
	/** 
	 * 调用营销服务端方法
	 * @param classPath
	 * @param methodName
	 * @param xml
	 * @return
	 */
	public String invoke(String classPath, String methodName, String xml) throws MalformedURLException, Exception {
		logger.info("***************************************调用采集访问方法WSInvokeClient***************************************");
		MarketTerminalConfigManager marketTerminalConfigManager = (MarketTerminalConfigManager) Constant.getCtx().getBean("marketTerminalConfigManager");
		String yxPassword = null;
		String yxuserName =  null;
		String yxUrl = null;
		List<?> list = marketTerminalConfigManager.getWSInfo();
		for (int i = 0; i <list.size(); i++) {
			Map<?, ?> mapTmnl = (Map<?, ?>) list.get(i);
			String itemNo = (String) mapTmnl.get("PARAM_ITEM_NO");
			String itemVal = (String) mapTmnl.get("PARAM_ITEM_VAL");
			if ("YX_URL".equals(itemNo)) {
				yxUrl = itemVal;
			}
			if ("YX_USERNAME".equals(itemNo)) {
				yxuserName = itemVal;
			}
			if ("YX_PASSWORD".equals(itemNo)) {
				yxPassword = itemVal;
			}
		}
		logger.info("***************************************"+yxUrl+"***************************************");
		Service service = new Service();
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(new URL(yxUrl));
		call.setOperationName(new QName("http://server.webservice.core.epm", "invoke") );
		//营销服务端用户名
		call.addHeader(new SOAPHeaderElement("Authorization","username", yxuserName));
		//营销服务端密码
		call.addHeader(new SOAPHeaderElement("Authorization","password", yxPassword));
		
		String result = (String) call.invoke(new Object[] {classPath, methodName, xml});
		
		return result;
	}
}