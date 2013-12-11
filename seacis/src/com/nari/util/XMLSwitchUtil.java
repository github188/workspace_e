package com.nari.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import com.nari.exception.ExceptionHandle;
import com.nari.global.Constant;
import com.nari.service.MarketTerminalConfigManager;

/**
 * XML与Map互转
 */
public class XMLSwitchUtil {
	
	private MarketTerminalConfigManager marketTerminalConfigManager;

	public void setMarketTerminalConfigManager(MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}

	/**
	 * 将指定格式的XML转换为Map
	 * @param xml	传入的指定格式的XML，格式参见《附件1 电力用户用电信息采集系统主站软件接口设计0208》
	 * @param map 	转换后的map结果(输出)
	 * @return		0  : 正常转换
	 * 				-1 : 传入的xml为空
	 * 				-2 : JDOMException
	 * 				-3 : IOException
	 * 				-4 : xml中根节点不是按约定格式的<DBSET>
	 */
	public int switchXMLToMap(String xml , Map<String, Object> map){
		

		if((xml == null) || (xml.trim().length() == 0)){
			return -1 ;
		}

		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		try {
	        doc = sb.build(new StringReader(xml)) ;	        
	        //临时保存xml文件
	        //String tmpXmlFile = "d:\\xml_log\\" + System.currentTimeMillis() ;
	        //XMLOutputter outputter = new XMLOutputter() ;
	        //outputter.output(doc, new FileOutputStream(tmpXmlFile)) ;      
        } catch (JDOMException e) {
        	ExceptionHandle.handleUnCheckedException(e);
        	return -2 ;
        } catch (IOException e) {
        	ExceptionHandle.handleUnCheckedException(e);
        	return -3 ;
        }
		Element eleRoot = doc.getRootElement() ;
		//取营销厂家
		String yxFactory = this.getYxFactory();
		/**
		 * -------------------------------------------朗新-----------------------------------------
		 * 
		 */
		if("1".equals(yxFactory)||"3".equals(yxFactory)){
			if(eleRoot.getName().compareTo("DBSET") == 0) {
				List<?> list_child = eleRoot.getChildren();
				if(list_child != null && list_child.size()==0) {
					String result = eleRoot.getAttributeValue("RESULT") ;
					result = result == null ? "" : result.trim() ;
					if(result.length() == 0){
						//返回的RESULT属性有误，没有按约定格式传
						return -1;
					}
					int nResult = Integer.parseInt(result) ;
					list_child = null;
					return nResult;
				}
				//取节点ROW
				Element eRow = eleRoot.getChild("R") ;
				List<?> ls = eRow.getChildren() ;
				Iterator<?> it = ls.iterator() ;
				while(it.hasNext()){
					Element e = (Element)it.next() ;
					String key = e.getAttributeValue("N") ;
					if(key == null){
						continue ;
					}
					String value = e.getText() ;
					//将取到的<COL>节点的内容存入map
					map.put(key, value) ;
				}
			}else{//根节点不是按约定格式的<DBSET>
				return -4 ;
			}
		}
		/**
		 * -------------------------------------------东软-----------------------------------------
		 * 
		 */
		if("2".equals(yxFactory)){
			if(eleRoot.getName().compareTo("dataset") == 0) {
				Element dStore = eleRoot.getChild("datastores").getChild("datastore");
				//取节点COLUMN
				Element eMetaData = dStore.getChild("metadata");
				List<?> mls = eMetaData.getChildren() ;
				Iterator<?> mit = mls.iterator() ;
				int i = 0;
				while(mit.hasNext()){
					Element me = (Element)mit.next() ;
					String key = me.getAttributeValue("name") ;
					if(key == null){
						continue ;
					}
					i++;
					//取节点ROW
					Element eRow = dStore.getChild("rowset").getChild("row");
					List<?> ls = eRow.getChildren() ;
					Iterator<?> it = ls.iterator() ;
					int j = 0;
					while(it.hasNext()){
						Element e = (Element)it.next() ;
						String value = e.getText() ;
						//将取到的<COL>节点的内容存入map
						j++;
						if(i == j){
							map.put(key, value) ;
						}
					}
				}
				
			}else{//根节点不是按约定格式的<dataset>
				return -4 ;
			}
		}
			
		return 0 ;
	}
	
	/**
	 * 将指定格式的XML转换为Map
	 * @param xml	传入的指定格式的XML，格式参见《营销与现场接口方案》
	 * @param map	转换后的map结果(输出)
	 * @param flag	是否嵌套标志: 
	 * 					0 - 否
	 * 					1 - 是
	 * @return		0  : 正常转换
	 * 				-1 : 传入的xml为空
	 * 				-2 : JDOMException
	 * 				-3 : IOException
	 * 				-4 : xml中根节点不是按约定格式的<DBSET>
	 */
	public int switchXMLToMap(String xml , Map<String, Object> map , int flag){
		if((xml == null) || (xml.trim().length() == 0)){
			return -1 ;
		}
		if(flag == 0){
        	return switchXMLToMap(xml , map) ;
        }else{
    		SAXBuilder sb = new SAXBuilder() ;
    		Document doc = null ;
    		try {
    	        doc = sb.build(new StringReader(xml)) ;
            } catch (JDOMException e) {
    	        ExceptionHandle.handleUnCheckedException(e);
            	return -2 ;
            } catch (IOException e) {
    	        ExceptionHandle.handleUnCheckedException(e);
            	return -3 ;
            }
    		Element eleRoot = doc.getRootElement() ;
    		if(eleRoot.getName().compareTo("DBSET") == 0) {
    			//取节点ROW
    			Element eRow = eleRoot.getChild("R") ;
    			List<?> ls = eRow.getChildren() ;
    			Iterator<?> it = ls.iterator() ;
    			while(it.hasNext()){
    				Element e = (Element)it.next() ;
    				String key = e.getAttributeValue("N") ;
    				if(key == null){
    					continue ;
    				}
    				int size = e.getContentSize() ;
    				// 处理嵌套的情况
    				if(size >0) {
    					Element eSubDBSet = e.getChild("DBSET");
    					if(eSubDBSet!=null) {
    						System.out.println("has a DBSET node.");
        					List<Map<String, String>> list = switchXMLToList(eSubDBSet) ;
        					map.put(key, list) ;
        					continue ;
    					}
    				}
    				String value = e.getText() ;
    				map.put(key, value) ;
    			}
    		}
    		else{//根节点不是按约定格式的<DBSET>
    			return -4 ;
    		}
        }
		return 0 ;
	}
	
	/**
	 * 将Map转换为指定格式的XML
	 * @param map	传入的Map
	 * @return		返回指定格式的XML，格式参见《营销与现场接口方案》（入参）
	 */
	public String switchMapToXML(Map<?, ?> map) {
		String xml = "";
		Document doc = null;
		//int num = -1 ;
		String yxFactory = this.getYxFactory();
		
		/**
		 * -------------------------------------------朗新、普华-----------------------------------------
		 * 
		 */
		if("1".equals(yxFactory)||"3".equals(yxFactory)){
			Element eleRoot = new Element("DBSET");
			doc = new Document(eleRoot);
			if (map == null) { // 如果传入的Map为null，则有异常
				System.out.println("map is null");
			} else {
				if (map.size() == 0) { // 如果没有取到结果
					System.out.println("map is null");
				} else { // 有结果
					Element eleRow = new Element("R");
					Set<?> set = map.keySet();
					Iterator<?> it = set.iterator();
					while (it.hasNext()) {
						// 取map中的一项，取到key和value
						String key = (String) it.next();
						if("zdbjdyMap".equals(key)) {
							continue;
						}
						String value = map.get(key) == null ? "" : (String) map.get(key);
						// 将key和value组合成xml中的节点<COL>
						Element e = new Element("C");
						e.setAttribute(new Attribute("N", key));
						e.setText(value);

						eleRow.addContent(e);
					}
					eleRoot.addContent(eleRow);
				}
			}
		}
		/**
		 * -------------------------------------------东软-----------------------------------------
		 * 
		 */
		if("2".equals(yxFactory)){
			Element eleRoot = new Element("dataset");
			doc = new Document(eleRoot);
			if (map == null) { // 如果传入的Map为null，则有异常
				System.out.println("map is null");
			} else {
				if (map.size() == 0) { // 如果没有取到结果
					System.out.println("map is null");
				} else { // 有结果
					Element eleRow = new Element("parameters");
					Set<?> set = map.keySet();
					Iterator<?> it = set.iterator();
					while (it.hasNext()) {
						// 取map中的一项，取到key和value
						String key = (String) it.next();
						String value = map.get(key) == null ? "" : (String) map.get(key);
						// 将key和value组合成xml中的节点<COL>
						Element e = new Element("parameter");
						e.setAttribute(new Attribute("name", key));
						e.setText(value);

						eleRow.addContent(e);
					}
					eleRoot.addContent(eleRow);
				}
			}
		}
		// 转换为字符串
		XMLOutputter outputter = new XMLOutputter();
		xml = outputter.outputString(doc);
		return xml;
	}
	
	/**
	 * 将Map转换为指定格式的XML(接口作为客户端调用)
	 * @param map	传入的Map
	 * @return		返回指定格式的XML
	 */
	public String switchMapToXMLClient(Map<?, ?> map, String oName) {
		String xml = "";
		Document doc = null;
		//int num = -1 ;
		String yxFactory = this.getYxFactory();
		/**
		 * -------------------------------------------朗新-----------------------------------------
		 * 
		 */
		if("1".equals(yxFactory)||"3".equals(yxFactory)){
			Element eleRoot = new Element("DBSET");
			doc = new Document(eleRoot);
			if (map == null) { // 如果传入的Map为null，则有异常
				System.out.println("map is null");
			} else {
				if (map.size() == 0) { // 如果没有取到结果
					System.out.println("map is null");
				} else { // 有结果
					Element eleRow = new Element("R");
					Set<?> set = map.keySet();
					Iterator<?> it = set.iterator();
					while (it.hasNext()) {
						// 取map中的一项，取到key和value
						String key = (String) it.next();
						if("zdbjdyMap".equals(key)) {
							continue;
						}
						String value = map.get(key) == null ? "" : (String) map.get(key);
						// 将key和value组合成xml中的节点<COL>
						Element e = new Element("C");
						e.setAttribute(new Attribute("N", key));
						e.setText(value);

						eleRow.addContent(e);
					}
					eleRoot.addContent(eleRow);
				}
			}
		}
		/**
		 * -------------------------------------------东软-----------------------------------------
		 * 
		 */
		if("2".equals(yxFactory)){
			Element eleRoot = new Element("dataset");
			doc = new Document(eleRoot);
			Element dataStores = new Element("datastores");
			Element dataStore = new Element("datastore");
			dataStore.setAttribute(new Attribute("name", oName));
			
			if (map == null) { // 如果传入的Map为null，则有异常
				System.out.println("map is null");
			} else {
				if (map.size() == 0) { // 如果没有取到结果
					System.out.println("map is null");
				} else { // 有结果
					//添加metaData
					Element metaData = new Element("metadata");
					Set<?> set = map.keySet();
					Iterator<?> it = set.iterator();
					while (it.hasNext()) {
						// 取map中的一项，取到key
						String key = (String) it.next();
						Element e = new Element("column");
						e.setAttribute(new Attribute("name", key));
						e.setAttribute(new Attribute("type", "VARCHAR"));

						metaData.addContent(e);
						
					}
					//添加rowSet
					Element rowSet = new Element("rowset");
					Element row = new Element("row");
					Set<?> rset = map.keySet();
					Iterator<?> rit = rset.iterator();
					while (rit.hasNext()) {
						// 取map中的一项，取到value
						String key = (String) rit.next();
						Element e = new Element("column");
						String value = map.get(key) == null ? "" : (String) map.get(key);
						e.setText(value);
						row.addContent(e);
					}
					dataStore.addContent(metaData);
					rowSet.addContent(row);
					dataStore.addContent(rowSet);
				}
			}
			dataStores.addContent(dataStore);
			eleRoot.addContent(dataStores);
		}
		// 转换为字符串
		XMLOutputter outputter = new XMLOutputter();
		xml = outputter.outputString(doc);
		return xml;
	}
	
	/**
	 * 将Map转换为指定格式的XML
	 * @param vct
	 * 传入的Vector
	 * @return 返回结果集（指定格式的XML，格式参见《营销与现场接口方案》）
	 */
	public String switchListToXML(List<?> lst){
		String xml = "" ;
		Document doc = null ;
		int num = -1 ;
		Element eleRoot = new Element("DBSET") ;
		doc = new Document(eleRoot) ;
		if(lst == null){
			num = -1 ;
		}else{
			//取得结果数量
			num = lst.size() ;
			//循环取向量中的Map，然后生成xml
			for(int i = 0 ; i < lst.size() ; i ++){
				Element eleRow = new Element("R") ;
				//向量中的每个对象是一个Map
				Map<?, ?> map = (Map<?, ?>)lst.get(i) ;				
				Set<?> set = map.keySet() ;
				Iterator<?> it = set.iterator() ;
				while(it.hasNext()){
					//取map中的一项，取到key和value
					String key = (String)it.next() ;
					String value = map.get(key) + "" ;
					//将key和value组合成xml中的节点<COL>
					Element e = new Element("C") ;
					e.setAttribute(new Attribute("N" , key)) ;
					e.setText(value) ;					
					eleRow.addContent(e) ;
				}
				eleRoot.addContent(eleRow) ;
			}
		}		
		eleRoot.setAttribute(new Attribute("RESULT" , num + "")) ;
		//生成xml字符串
		XMLOutputter outputter = new XMLOutputter();
		xml = outputter.outputString(doc) ;
		//返回
		return xml ;
	}
	
	/**
	 * 将得到的xml生成List
	 * @param xml	调用接口后得到的xml，符合《营销与现场接口方案》的要求
	 * @return
	 */
	public List<Map<String, String>> switchXMLToList(String xml){
		List<Map<String, String>> lstRtn = null ;
		if((xml == null) || (xml.trim().length() == 0)){
			//xml空
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        ExceptionHandle.handleUnCheckedException(e);
        	return null ;
        } catch (IOException e) {
	        ExceptionHandle.handleUnCheckedException(e);
        	return null ;
        }
		Element eleRoot = doc.getRootElement() ;
		if(eleRoot.getName().compareTo("DBSET") == 0) {
			//取根节点属性 RESULT
			String result = eleRoot.getAttributeValue("RESULT") ;
			result = result == null ? "" : result.trim() ;
			if(result.length() == 0){
				//返回的RESULT属性有误，没有按约定格式传
				//...暂时不管
			}
			int nResult = Integer.parseInt(result) ;
			if(nResult <= 0)
				return null ;

			lstRtn = new ArrayList<Map<String, String>>() ;
			List<?> lsOfRow = eleRoot.getChildren() ;
			Iterator<?> itOfRow = lsOfRow.iterator() ;
			while(itOfRow.hasNext()){
				//取一个ROW节点
				Element eRow = (Element) itOfRow.next() ;
				//再去其子节点迭代去将信息存入map
				List<?> ls = eRow.getChildren() ;
				Iterator<?> it = ls.iterator() ;
				Map<String, String> map = new HashMap<String, String>() ;
				while(it.hasNext()){
					Element e = (Element)it.next() ;
					String key = e.getAttributeValue("N") ;
					if(key == null ){
						continue ;
					}
					String value = e.getText() ;
					map.put(key, value) ;
				}
				//将一个ROW节点对应的map存入List
				lstRtn.add(map) ;
			}
		}
		else{//根节点不是按约定格式的<DBSET>
			return null ;
		}
		return lstRtn ;
	}

	/**
	 * 将符合要求的节点生成List
	 * @param ele	xml的一个节点，为《营销与现场接口方案》中要求的<DBSET>节点
	 * @return
	 */
	public List<Map<String, String>> switchXMLToList(Element ele){
		List<Map<String, String>> lstRtn = null ;
		if((ele == null)){
			//节点空
			return null ;
		}
		
		if(ele.getName().compareTo("DBSET") == 0) {
			lstRtn = new ArrayList<Map<String, String>>() ;
			List<?> lsOfRow = ele.getChildren() ;
			Iterator<?> itOfRow = lsOfRow.iterator() ;
			while(itOfRow.hasNext()){
				//取一个ROW节点
				Element eRow = (Element) itOfRow.next() ;
				//再去其子节点迭代去将信息存入map
				List<?> ls = eRow.getChildren() ;
				Iterator<?> it = ls.iterator() ;
				Map<String, String> map = new HashMap<String, String>() ;
				while(it.hasNext()){
					Element e = (Element)it.next() ;
					String key = e.getAttributeValue("N") ;
					if(key == null){
						continue ;
					}
					String value = e.getText() ;
					map.put(key, value) ;
				}
				//将一个ROW节点对应的map存入List
				lstRtn.add(map) ;
			}
		}
		else{//根节点不是按约定格式的<DBSET>
			return null ;
		}
		return lstRtn ;
	}
	
	/**
	 * 根据错误码生成符合《营销与现场接口方案》要求的xml
	 * @param errCode	错误码
	 * @return
	 */
	public String genXMLFromErrCode(int errCode){
		String xml = "" ;
		Document doc = null ;
		//生成根节点
		Element eleRoot = new Element("DBSET") ;
		doc = new Document(eleRoot) ;
		//设置RESULT属性
		eleRoot.setAttribute(new Attribute("RESULT" , errCode + "")) ;
		//下面这句话主要看接口是否要求一定要这样的格式：
		//<DBSET RESULT=”-1”>
		//</DBSET>
		//如果也允许用<DBSET RESULT=”-1”/>这样的格式，则可以去掉下面这句话
		eleRoot.setText("") ;
		//输出为字符
		XMLOutputter outputter = new XMLOutputter();
		xml = outputter.outputString(doc) ;
		//返回
		return xml ;
	}
	
	public int checkXMLNode(Map<?, ?> map,String [] nodeNames) {
		for(int i=0;i<nodeNames.length;i++) {
			if(!map.containsKey(nodeNames[i])) {		//缺节点
				return -4;
			}
		}
		if(map.keySet().size() > nodeNames.length) {	//节点过多
			return -4;
		}
		return 0;
	}
	
	public String getYxFactory(){
		marketTerminalConfigManager=(MarketTerminalConfigManager)Constant.getCtx().getBean("marketTerminalConfigManager");
		String yxFactory = "";
		List<?> list = marketTerminalConfigManager.getWSInfo();
		for (int i = 0; i <list.size(); i++) {
			Map<?, ?> mapTmnl = (Map<?, ?>) list.get(i);
			String itemNo = (String) mapTmnl.get("PARAM_ITEM_NO");
			String itemVal = (String) mapTmnl.get("PARAM_ITEM_VAL");
			if ("YX_FACTORY".equals(itemNo)) {
				yxFactory = itemVal;
			}
		}
		return yxFactory;
	}
}