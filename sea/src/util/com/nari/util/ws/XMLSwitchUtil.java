package com.nari.util.ws;

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

/**
 * XML与Map互转
 */
public class XMLSwitchUtil {

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

		xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		try {
	        doc = sb.build(new StringReader(xml)) ;	        
	        //临时保存xml文件
	        //String tmpXmlFile = "d:\\xml_log\\" + System.currentTimeMillis() ;
	        //XMLOutputter outputter = new XMLOutputter() ;
	        //outputter.output(doc, new FileOutputStream(tmpXmlFile)) ;      
        } catch (JDOMException e) {
        	
        	return -2 ;
        } catch (IOException e) {
        	
        	return -3 ;
        }
		Element eleRoot = doc.getRootElement() ;
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

		xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", " ");
        if(flag == 0){
        	return switchXMLToMap(xml , map) ;
        }else{
    		SAXBuilder sb = new SAXBuilder() ;
    		Document doc = null ;
    		try {
    	        doc = sb.build(new StringReader(xml)) ;
            } catch (JDOMException e) {
    	        
            	return -2 ;
            } catch (IOException e) {
    	        
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
	
	/**
	 * 取xml中的<DBSET>节点的RESULT属性值
	 * 注：这里的返回值<=-1000时，是方法内部的错误，如果与营销系统有冲突的话，再重新修改
	 * @param xml	传入的指定格式的XML，格式参见《营销与现场接口方案》
	 * @return		
	 * 				-1000	： 	应该不会出现
	 * 				-1001	：	传入的xml为空
	 * 				-1002	：	JDOMException
	 * 				-1003	：	IOException
	 * 				-1004	：	传入xml的RESULT属性有误，没有按约定格式传
	 * 				-1005	：	//根节点不是按约定格式的<DBSET>
	 * 				其它		：	xml的<DBSET>节点的RESULT属性值
	 */
	public int getResultCode(String xml){
		int nRet = -1000 ;
		xml = xml == null ? "" : xml.trim() ;
		if(xml.length() == 0){
			nRet = -1001 ;
		}
		else{		
			SAXBuilder sb = new SAXBuilder() ;
			Document doc = null ;

			xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
			try {
		        doc = sb.build(new StringReader(xml)) ;
		    } catch (JDOMException e) {
		    	nRet = -1002 ;
		        
		    } catch (IOException e) {
		    	nRet = -1003 ;
		        
		    }
			Element eleRoot = doc.getRootElement() ;
			if(eleRoot.getName().compareTo("DBSET") == 0) {
				//取根节点属性 RESULT
				String result = eleRoot.getAttributeValue("RESULT") ;
				result = result == null ? "" : result.trim() ;
				
				if(result.length() == 0){	//返回的RESULT属性有误，没有按约定格式传
					nRet = -1004 ;
				}
				nRet = Integer.parseInt(result) ;
			}else{//根节点不是按约定格式的<DBSET>
				nRet = -1005 ;
			}
		}
		return nRet ;
	}
	/**
	 * 将得到的xml生成List
	 * @param xml	调用接口后得到的xml，符合《营销与现场接口方案》的要求
	 * @return
	 */
	public List<Map<String, String>> switchXMLToList(String xml,int flag){
		List<Map<String, String>> lstRtn = null ;
		if((xml == null) || (xml.trim().length() == 0)){
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		// 取相关map
		Map<?, ?> params = getParamFieldMap(flag) ;
		xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return null ;
        } catch (IOException e) {
	        
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
					String key = e.getAttributeValue("NAME") ;
					if(key == null || !params.containsValue(key) 
							&&  (flag ==  Globals.MARKET_INTERFACE_FLAG_GETDIC
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETPOWERSUPPLY
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETLINE
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETDEPT
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETPOWER
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETCONTACT
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETRELATECUST
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETAMETER
									|| flag == Globals.MARKET_INTERFACE_FLAG_GETTRANSFORMER
									|| flag ==  Globals.MARKET_INTERFACE_FLAG_GETSIM
									|| flag ==  Globals.MARKET_INTERFACE_FLAG_GETSWITCH
									|| flag ==  Globals.MARKET_INTERFACE_FLAG_GETTERMINAL
									|| flag ==  Globals.MARKET_INTERFACE_FLAG_COMMINFO
									|| flag ==  Globals.MARKET_INTERFACE_FLAG_MODIFY_NOTICE_INFO
								)
							){
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
		xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return null ;
        } catch (IOException e) {
	        
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
	 * 根据标志取参数与字段的对应关系
	 * @param flag	调用的接口标志
	 */
	public Map<?, ?> getParamFieldMap(int flag){

		Map<?, ?> map = new HashMap<String,String>() ;
		//根据标志取相关map
		switch (flag) {
		case Globals.MARKET_INTERFACE_FLAG_GETDIC:
			//取数据字典信息
			map = ParamFieldMapUtil.getDicMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETPOWERSUPPLY:	
			//取供电所信息
			map = ParamFieldMapUtil.getPowerSupplyMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETLINE:
			//取供电线路信息
			map = ParamFieldMapUtil.getLineInfoMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETDEPT:	
			//取单位
			map = ParamFieldMapUtil.getDeptInfoMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETCUSTINFO:	
			//取客户档案
			map = ParamFieldMapUtil.getCustomerInfoMap() ;
			break; 
		case Globals.MARKET_INTERFACE_FLAG_GETPOWER:	
			//取客户供电电源信息
			map = ParamFieldMapUtil.getCustomerPowerInfoMap() ;
			break;	
		case Globals.MARKET_INTERFACE_FLAG_GETCONTACT:	
			//取客户联系人信息
			map = ParamFieldMapUtil.getCustomerContactMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETRELATECUST:
			//取客户关联户号信息
			//map = ParamFieldMapUtil ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETAMETER:	
			//取客户表计档案
			map = ParamFieldMapUtil.getAmeterInfoMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETCUSTMANAGER:
			//取客户经理信息
			//map = ParamFieldMapUtil ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETTRANSFORMER:	
			//取客户变压器档案
			map = ParamFieldMapUtil.getTransformerInfoMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETPOWERRATE:	
			//取客户某月电费信息
			//map = ParamFieldMapUtil ;
			break;	
		case Globals.MARKET_INTERFACE_FLAG_GETSIM:	
			//取客户SIM卡档案
			map = ParamFieldMapUtil.getSIMInfoMap() ;
			break;	
		case Globals.MARKET_INTERFACE_FLAG_GETSWITCH:	
			//取开关信息
			map = ParamFieldMapUtil.getSwitchInfoMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_GETTERMINAL: 
			//取客户终端档案
			map = ParamFieldMapUtil.getCustomerTermInfoMap() ;
			break;
		case Globals.MARKET_INTERFACE_FLAG_COMMINFO:
			// 取终端通讯参数方案
			map = ParamFieldMapUtil.getTermParamSchemeMap() ;
			break ;
		case Globals.MARKET_INTERFACE_FLAG_MODIFY_NOTICE_INFO:
			map = ParamFieldMapUtil.getNoticeInfoChanged();
			break;
		case Globals.MARKET_INTERFACE_FLAG_AMETERDATA:
			// 取抄表数据
			map = ParamFieldMapUtil.getAmeterDataMap() ;
			break ;
		}
		
		return map ;
	}
	
	/**
	 * 将现场系统中传的入参字段（为现场系统中相关表的字段）转换为接口中要求的入参字段的xml
	 * @param xml		现场系统中传的入参字段组合的xml
	 * @param flag		调用的接口标志
	 * @return
	 */
	public String switchFieldToParam(String xml , int flag){
		String resultXML = null ;
		// 取相关map
		Map<?, ?> map = getParamFieldMap(flag);
		// 如果map中没有值，就不替换，直接返回原来的xml
		resultXML = xml ;
		if(map == null)
			return resultXML ;
		// 从相关map中取值进行循环替换
		Set<?> set = map.keySet() ;
		Iterator<?> iter = set.iterator() ;
		while(iter.hasNext()){
			// 取map中的一条记录
			String key = (String) iter.next() ;
			key = key == null ? "" : key.trim() ;
			if(key.length() == 0)
				continue ;
			String value = map.get(key) + "" ;
			value = value == null ? "" : value.trim() ;
			if(value.length() == 0)
				continue ;
			// 进行替换，为了确保替换没有问题，加上这样的字符"=<\""
			key = "=\"" + key + "\">" ;
			value = "=\"" + value + "\">" ;
			resultXML = resultXML.replaceAll(value, key) ;
		}
		// 返回
		return resultXML ;
	}

	/**
	 * 将接口中要求的入参字段转换为现场系统中传的入参字段（为现场系统中相关表的字段）的xml
	 * @param xml		现场系统中传的入参字段组合的xml
	 * @param flag		调用的接口标志
	 * @return
	 */
	public String switchParamToField(String xml , int flag){
		String resultXML = null ;

		// 取相关map
		Map<?, ?> map = getParamFieldMap(flag) ;
		// 如果map中没有值，就不替换，直接返回原来的xml
		resultXML = xml ;
		if(map == null || resultXML == null || resultXML.length() == 0)
			return resultXML ;		
		// 从相关map中取值进行循环替换
		Set<?> set = map.keySet() ;
		Iterator<?> iter = set.iterator() ;
		while(iter.hasNext()){
			// 取map中的一条记录
			String key = (String) iter.next() ;
			key = key == null ? "" : key.trim() ;
			if(key.length() == 0)
				continue ;
			String value = map.get(key) + "" ;
			value = value == null ? "" : value.trim() ;
			if(value.length() == 0)
				continue ;
			// 进行替换，为了确保替换没有问题，加上这样的字符"=<\""
			key = "=\"" + key + "\">" ;
			value = "=\"" + value + "\">" ;
			resultXML = resultXML.replaceAll(key, value) ;
		}
		
		// 返回
		return resultXML ;
	}
	
	/**
	 * 取XML中的RESULT的值
	 * @param xml
	 * @return	-1001：根节点不是按约定格式的<DBSET>
	 * 			-1002：返回的RESULT属性有误，没有按约定格式传
	 * 			-1003：Integer.parseInt异常
	 * 			-9001：xml空
	 * 			-9002：JDOMException异常
	 * 			-9003：IOException异常
	 * 			其它：xml中的RESULT属性值（转换后的int值）
	 */
	public int getXmlResult(String xml){
		if((xml == null) || (xml.trim().length() == 0)){
			//xml空
			return -9001 ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;

		xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return -9002 ;
        } catch (IOException e) {
	        
        	return -9003 ;
        }
		Element eleRoot = doc.getRootElement() ;
		if(eleRoot.getName().compareTo("DBSET") == 0) {
			//取根节点属性 RESULT
			String result = eleRoot.getAttributeValue("RESULT") ;
			result = result == null ? "" : result.trim() ;
			if(result.length() == 0){
				//返回的RESULT属性有误，没有按约定格式传
				return -1002 ;
			}
			int nRet = 0 ;
			try{
				nRet = Integer.parseInt(result) ;
				return nRet ;
			}
			catch(Exception e){
				return -1003 ;
			}
		}
		else{//根节点不是按约定格式的<DBSET>
			return -1001 ;
		}
		
	}
	
	/**
	 * 取XML中的RLT_FLAG的值
	 * @param xml
	 * @return 返回调用营销结果
	 */
	public int getXmlResultRltFlag(String xml){
		int nRet = 0;
		if((xml == null) || (xml.trim().length() == 0)){
			//xml空
			return -9001 ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;

		xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return -9002 ;
        } catch (IOException e) {
	        
        	return -9003 ;
        }
		Element eleRoot = doc.getRootElement() ;
		if(eleRoot.getName().compareTo("DBSET") == 0) {
			List<?> list_child = eleRoot.getChildren();
			if(list_child != null && list_child.size()==0) {
				String result = eleRoot.getAttributeValue("RESULT") ;
				result = result == null ? "" : result.trim() ;
				if(result.length() == 0){
					//返回的RESULT属性有误，没有按约定格式传
					return 0;
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
				if ("RLT_FLAG".equals(key)){
					nRet = Integer.parseInt(e.getText()) ;
				}
			}
			return nRet;
		}else{
			return 0;
		}
	}
	
	/**
	 * 特殊处理（变更用）：将取到的终端通讯参数方案加上单位代码转换为xml，以便后面可以发给营销系统
	 * @param dwdm	单位代码
	 * @param list	终端通讯参数方案结果集合
	 * @return
	 */
	public static String getTermInfoXML(String dwdm , List<?> list){
		String xml = "" ;
		Document doc = null ;
		int num = -1 ;
		Element eleRoot = new Element("DBSET") ;
		doc = new Document(eleRoot) ;
		if(list == null){
			num = -1 ;
		}
		else{
			//取得结果数量
			num = list.size() ;
			//循环取向量中的Map，然后生成xml
			for(int i = 0 ; i < list.size() ; i ++){
				Element eleRow = new Element("R") ;
				//向量中的每个对象是一个Map
				Map<?, ?> map = (Map<?, ?>)list.get(i) ;
				
				// 特殊的地方就在这里，每条记录还需要增加单位代码
				Element e_ = new Element("C") ;
				e_.setAttribute(new Attribute("N" , "DW")) ;
				e_.setText(dwdm) ;
				eleRow.addContent(e_) ;
				
				Set<?> set = map.keySet() ;
				Iterator<?> it = set.iterator() ;
				while(it.hasNext()){
					//取map中的一项，取到key和value
					String key = (String)it.next() ;
					String value = map.get(key) == null ? "" : map.get(key) + "" ;

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
		
		return xml ;
	}
	/**
	 * 将单点登陆系统传过来的XML转换为LIST（操作员）
	 * @param xml
	 * @return
	 */
	public static List<Map<String, String>> switchSSOXMLToUserList(String xml){
		List<Map<String, String>> lstRtn = null ;
		if((xml == null) || (xml.trim().length() == 0)){
			//xml空
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		//xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return null ;
        } catch (IOException e) {
	        
        	return null ;
        }
		Element eleRoot = doc.getRootElement() ;
		if(eleRoot.getName().equals("organizationItems")){
			List<?> elements = eleRoot.getChildren() ;
			lstRtn = new ArrayList<Map<String, String>>() ; 
			for(int i = 0 ; i < elements.size() ; i ++){
				Element element = (Element)elements.get(i) ;
				String eleName = element.getName() ;
				if(eleName.equals("userItem")){	// 操作员数据
					String operation = element.getAttributeValue("operation") ;
					String id = element.getAttributeValue("id") ;
					String parent = element.getAttributeValue("parent") ;
					String name = element.getAttributeValue("name") ;
					String title = element.getAttributeValue("title") ;
					String mobilePhone = element.getAttributeValue("mobile") ;
					String virtualNumber = element.getAttributeValue("virtualNumber") ;
					String office1 = element.getAttributeValue("office1") ;
					String office2 = element.getAttributeValue("office2") ;
					String email = element.getAttributeValue("email") ;
					String email2 = element.getAttributeValue("email2") ;
					String index = element.getAttributeValue("index") ;
					
					Map<String, String> map = new HashMap<String, String>() ;
					map.put("operation", operation) ;
					map.put("ID", id) ;
					map.put("DWDM", parent) ;
					map.put("CZYID", name) ;
					map.put("XM", title) ;
					map.put("SJ", mobilePhone) ;
					map.put("VIRTUALNUM", virtualNumber) ;
					map.put("DH", office1) ;
					map.put("DH2", office2) ;
					map.put("EMAIL", email) ;
					map.put("EMAIL2", email2) ;
					map.put("index", index) ;
					
					lstRtn.add(map) ;
				} //else if(eleName.equals("institutionItem")){	// 机构数据(暂时不用机构数据)
//					String operation = element.getAttributeValue("operation") ;
//					String id = element.getAttributeValue("id") ;
//					String parent = element.getAttributeValue("parent") ;
//					String name = element.getAttributeValue("name") ;
//					String title = element.getAttributeValue("title") ;
//					String abbreviate = element.getAttributeValue("abbreviate") ;
//					String index = element.getAttributeValue("index") ;				
//					Map map = new HashMap() ;
//					map.put("operation", operation) ;
//					map.put("id", id) ;
//					map.put("parent", parent) ;
//					map.put("name", name) ;
//					map.put("title", title) ;
//					map.put("abbreviate", abbreviate) ;
//					map.put("index", index) ;		
//					lstRtn.add(map) ;
//				}
			}
		}
		return lstRtn ;
	}

	/**
	 * 将单点登陆系统传过来的XML转换为LIST（机构）
	 * @param xml
	 * @return
	 */
	public static List<Map<String, String>> switchSSOXMLToInstitutionList(String xml){
		List<Map<String, String>> lstRtn = null ;
		if((xml == null) || (xml.trim().length() == 0)){
			//xml空
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		//xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return null ;
        } catch (IOException e) {
	        
        	return null ;
        }
		Element eleRoot = doc.getRootElement() ;
		if(eleRoot.getName().equals("organizationItems")){
			List<?> elements = eleRoot.getChildren() ;
			lstRtn = new ArrayList<Map<String, String>>() ; 
			for(int i = 0 ; i < elements.size() ; i ++){
				Element element = (Element)elements.get(i) ;
				String eleName = element.getName() ;
				if(eleName.equals("institutionItem")){	// 机构数据(暂时不用机构数据)
					String operation = element.getAttributeValue("operation") ;
					String id = element.getAttributeValue("id") ;
					String parent = element.getAttributeValue("parent") ;
					String name = element.getAttributeValue("name") ;
					String title = element.getAttributeValue("title") ;
					String abbreviate = element.getAttributeValue("abbreviate") ;
					String institutionPath = element.getAttributeValue("institutionPath") ;
					String index = element.getAttributeValue("index") ;
					// 处理取到值为null或字符串"null"的情况
					operation = operation == null ? "" : operation.trim() ;
					operation = operation == "null" ? "" : operation.trim() ;
					id = id == null ? "" : id.trim() ;
					id = id == "null" ? "" : id.trim() ;
					parent = parent == null ? "" : parent.trim() ;
					parent = parent == "null" ? "" : parent.trim() ;
					name = name == null ? "" : name.trim() ;
					name = name == "null" ? "" : name.trim() ;
					title = title == null ? "" : title.trim() ;
					title = title == "null" ? "" : title.trim() ;
					abbreviate = abbreviate == null ? "" : abbreviate.trim() ;
					abbreviate = abbreviate == "null" ? "" : abbreviate.trim() ;
					institutionPath = institutionPath == null ? "" : institutionPath.trim() ;
					institutionPath = institutionPath == "null" ? "" : institutionPath.trim() ;
					index = index == null ? "" : index.trim() ;
					index = index == "null" ? "" : index.trim() ;
					
					Map<String, String> map = new HashMap<String, String>() ;
					map.put("operation", operation) ;
					map.put("JGID", id) ;
					map.put("SJJGID", parent) ;
					map.put("JGBM", name) ;
					map.put("JGMC", title) ;
					map.put("JGZWJC", abbreviate) ;
					map.put("JGLJ", institutionPath) ;
					map.put("SXH", index) ;
					
					lstRtn.add(map) ;
				}
			}
		}
		return lstRtn ;
	}
	
	/**
	 * 从单点登陆的接口中过来的XML文件中取更新包信息
	 * @param xml
	 * @return
	 */
	public static Map<String, String> getPackageInfoFromSSOXml(String xml){
		Map<String, String> map = null ;
		
		if((xml == null) || (xml.trim().length() == 0)){
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		//xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return null ;
        } catch (IOException e) {
	        
        	return null ;
        }
		Element eleRoot = doc.getRootElement() ;
		String id = eleRoot.getAttributeValue("id") ;
		String time = eleRoot.getAttributeValue("time") ;
		
		map = new HashMap<String, String>() ;
		map.put("id", id) ;
		map.put("time", time) ;
		
		return map ;
	}
	
	/**
	 * 取从“取得丢失同步的组织信息列表”接口中返回过来的XML中的ID列表
	 * @param xml
	 * @return
	 */
	public static List<String> getIdList(String xml){
		List<String> list =  new ArrayList<String>() ;
		
		if((xml == null) || (xml.trim().length() == 0)){
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		//xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
	        
        	return null ;
        } catch (IOException e) {
	        
        	return null ;
        }
		Element eleRoot = doc.getRootElement() ;
		List<?> elements = eleRoot.getChildren() ;
		for(int i = 0 ; i < elements.size() ; i ++){
			Element element = (Element)elements.get(i) ;
			String id = element.getAttributeValue("id") ;
			list.add(id) ;
		}
		return list ;
	}
	
	/**
	 * 取从“取得组织信息”接口中返回的XML中的用户列表
	 * @param xml
	 * @return
	 */
	public static List<Map<String, String>> getUserItemList(String xml){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>() ;
		
		if((xml == null) || (xml.trim().length() == 0)){
			return null ;
		}
		
		SAXBuilder sb = new SAXBuilder() ;
		Document doc = null ;
		
		//xml = xml.replaceAll("<!DOCTYPE DBSET PUBLIC \"-//Longshine//DTD Zepcis Interface Parameters Rules Configuration 1.0//EN\" \"zepcis_interface_1_0.dtd\">", "");
		try {
	        doc = sb.build(new StringReader(xml)) ;
        } catch (JDOMException e) {
        	return null ;
        } catch (IOException e) {
        	return null ;
        }
		Element eleRoot = doc.getRootElement() ;
		List<?> elements = eleRoot.getChildren() ;
		for(int i = 0 ; i < elements.size() ; i ++){
			Element element = (Element)elements.get(i) ;
			String eleName = element.getName() ;
			if(eleName.equals("userItem")){	// 操作员数据
				String id = element.getAttributeValue("id") ;
				String parent = element.getAttributeValue("parent") ;
				String name = element.getAttributeValue("name") ;
				String title = element.getAttributeValue("title") ;
				String mobilePhone = element.getAttributeValue("mobilePhone") ;
				String virtualNumber = element.getAttributeValue("virtualNumber") ;
				String office1 = element.getAttributeValue("office1") ;
				String office2 = element.getAttributeValue("office2") ;
				String email = element.getAttributeValue("email") ;
				String email2 = element.getAttributeValue("email2") ;
				String index = element.getAttributeValue("index") ;
				
				Map<String, String> map = new HashMap<String, String>() ;
				map.put("id", id) ;
				map.put("parent", parent) ;
				map.put("name", name) ;
				map.put("title", title) ;
				map.put("mobilePhone", mobilePhone) ;
				map.put("virtualNumber", virtualNumber) ;
				map.put("office1", office1) ;
				map.put("office2", office2) ;
				map.put("email", email) ;
				map.put("email2", email2) ;
				map.put("index", index) ;
				
				list.add(map) ;
			// 机构数据(暂时不用)
			}else if(eleName.equals("institutionItem")){	
//				String id = element.getAttributeValue("id") ;
//				String parent = element.getAttributeValue("parent") ;
//				String name = element.getAttributeValue("name") ;
//				String title = element.getAttributeValue("title") ;
//				String abbreviate = element.getAttributeValue("abbreviate") ;
//				String index = element.getAttributeValue("index") ;			
//				Map map = new HashMap() ;
//				map.put("id", id) ;
//				map.put("parent", parent) ;
//				map.put("name", name) ;
//				map.put("title", title) ;
//				map.put("abbreviate", abbreviate) ;
//				map.put("index", index) ;
//				list.add(map) ;
			}
		}
		return list ;
	}
	
	/**
	 * 根据错误码生成符合《营销与现场接口方案》要求的xml
	 * @param errCode	错误码
	 * @return
	 */
	public String getErrCodeToXML(int errCode){
		String xml = "" ;
		Document doc = null ;
		Element eleRoot = new Element("DBSET") ;
		doc = new Document(eleRoot) ;
		if(errCode >= 0 ) {//表示返回成功
			eleRoot.setAttribute(new Attribute("RESULT" , errCode + "")) ;
			XMLOutputter outputter = new XMLOutputter();
			xml = outputter.outputString(doc) ;
			return xml ;
		}
		Map<String, String> map = getErrorCodeMap(errCode);

		Element eleRow = new Element("R");
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			// 取map中的一项，取到key和value
			String key = it.next();
			String value = map.get(key) == null ? "" : map.get(key);
			// 将key和value组合成xml中的节点<COL>
			Element e = new Element("C");
			e.setAttribute(new Attribute("N", key));
			e.setText(value);

			eleRow.addContent(e);
		}
		eleRoot.addContent(eleRow);
		eleRoot.setAttribute(new Attribute("RESULT" ,"-1")) ;
		// 转换为字符串
		XMLOutputter outputter = new XMLOutputter();
		xml = outputter.outputString(doc) ;
		return xml ;
	}
	/**
	 * 根据标志取参数与字段的对应关系
	 * @param flag		调用的接口标志
	 * @return
	 */
	public Map<String, String> getErrorCodeMap(int flag){

		Map<String, String> map = null ;
		//根据标志生成相关map
		switch (flag) {
		case ErrorCode.MARKET_INTERFACE_FLAG_ROOT_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_ROOT_ERROR));
			map.put("BZ", "不是按约定格式的XML!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_CDBH_EXIST:
			//没有取到数据字典信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_CDBH_EXIST));
			map.put("BZ", "该传单在现场管理系统已存在且建档成功，请检查后再处理!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_DIC:
			//没有取到数据字典信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_DIC));
			map.put("BZ", "取字典数据失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_POWERSUPPLY:	
			//取供电所信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_POWERSUPPLY));
			map.put("BZ", "取供电所信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_LINE:
			//取供电线路信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_LINE));
			map.put("BZ", "取供电线路信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_DEPT:	
			//取单位
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_DEPT));
			map.put("BZ", "取单位信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CUSTINFO:	
			//取客户档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CUSTINFO));
			map.put("BZ", "取客户档案信息失败!");
			break; 
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CUSTINFO_TIMEOUT:	
			//取客户档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CUSTINFO_TIMEOUT));
			map.put("BZ", "取客户档案信息超时!");
			break; 
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_POWER:	
			//取客户供电电源信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_POWER));
			map.put("BZ", "取客户供电电源信息失败!");
			break;	
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CONTACT:	
			//取客户联系人信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CONTACT));
			map.put("BZ", "取客户联系人信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_RELATECUST:
			//取客户关联户号信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_RELATECUST));
			map.put("BZ", "取客户关联户号信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_AMETER:	
			//取客户表计档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_AMETER));
			map.put("BZ", "取客户表计档案信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_AMETER_TIMEOUT:	
			//取客户表计档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_AMETER_TIMEOUT));
			map.put("BZ", "取客户表计档案信息超时!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CUSTMANAGER:
			//取客户经理信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_CUSTMANAGER));
			map.put("BZ", "取客户经理信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TRANSFORMER:	
			//取客户变压器档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TRANSFORMER));
			map.put("BZ", "取客户变压器档案失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_POWERRATE:	
			//取客户某月电费信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_POWERRATE));
			map.put("BZ", "取客户某月电费信息失败!");
			break;	
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_SIM:	
			//取客户SIM卡档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_SIM));
			map.put("BZ", "取客户客户SIM卡档案信息失败!");
			break;	
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_SWITCH:	
			//取开关信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_SWITCH));
			map.put("BZ", "取客户开关信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINAL: 
			//取客户终端档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINAL));
			map.put("BZ", "取终端档案失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINAL_TIMEOUT: 
			//取客户终端档案
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINAL_TIMEOUT));
			map.put("BZ", "取终端档案超时!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_INDUSTRYCLASSIFICATION: 
			//取行业分类信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_INDUSTRYCLASSIFICATION));
			map.put("BZ", "取行业分类信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_FACTORY: 
			//取终端厂家信息
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_FACTORY));
			map.put("BZ", "取终端厂家信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NORELATECUSTOMER: 
			//无此对应的用户
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NORELATECUSTOMER));
			map.put("BZ", "无此对应的用户!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_MODIFY_NOTICE_INFO_FAIL: 
			//取变更通知信息失败
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_MODIFY_NOTICE_INFO_FAIL));
			map.put("BZ", "取变更通知信息失败!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_RELATION: 
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_RELATION));
			map.put("BZ", "终端与表计没有对应关系!");
			break;
		case ErrorCode.MARKET_INTERFACE_FLAG_EXIST_SIMKH:
			//SIM卡号已经存在
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_EXIST_SIMKH));
			map.put("BZ", "SIM卡号已经存在!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_EXIST_TERMINALLOGIC_ADDRESS:
			//终端逻辑地址已经存在
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_EXIST_TERMINALLOGIC_ADDRESS));
			map.put("BZ", "终端逻辑地址已经存在!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_EXIST_AMETER:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_EXIST_AMETER));
			map.put("BZ", "表计局号已经存在且属于另一个用户!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGETTERMINAL_RELATION:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGETTERMINAL_RELATION));
			map.put("BZ", "终端与表计对应关系中的终端局号为空!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINAL_ZDSYM:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINAL_ZDSYM));
			map.put("BZ", "终端索引码或终端类型为空!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_AMETER_ADDRESS:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_AMETER_ADDRESS));
			map.put("BZ", "表地址、表规约或CT、PT为空!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_EXIST_TERMINAL_JH:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_EXIST_TERMINAL_JH));
			map.put("BZ", "终端局号已经存在，且属于另一个用户!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINALLOGIC_ADDRESS:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGET_TERMINALLOGIC_ADDRESS));
			map.put("BZ", "终端逻辑地址为空!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_TERMINALPASS_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_TERMINALPASS_ERROR));
			map.put("BZ", "终端密码超长!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTGETAMETER_RELATION:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTGETAMETER_RELATION));
			map.put("BZ", "终端与表计对应关系中的表计局号为空!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_MERGE_USER_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_MERGE_USER_ERROR));
			map.put("BZ", "并户过程中销户异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_DIC_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_DIC_ERROR));
			map.put("BZ", "存储字典数据异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_POWERSUPPLY_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_POWERSUPPLY_ERROR));
			map.put("BZ", "存储供电所信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_LINE_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_LINE_ERROR));
			map.put("BZ", "存储供电线路信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_DEPT_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_DEPT_ERROR));
			map.put("BZ", "存储单位信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_CUSTINFO_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_CUSTINFO_ERROR));
			map.put("BZ", "存储客户档案信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_POWER_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_POWER_ERROR));
			map.put("BZ", "存储客户供电电源信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_CONTACT_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_CONTACT_ERROR));
			map.put("BZ", "存储客户联系人信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_TRANSFORMER_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_TRANSFORMER_ERROR));
			map.put("BZ", "存储客户变压器档案异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_SIM_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_SIM_ERROR));
			map.put("BZ", "存储客户SIM卡档案异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_SWITCH_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_SWITCH_ERROR));
			map.put("BZ", "存储开关信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_INDUSTRYCLASSIFICATION_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_INDUSTRYCLASSIFICATION_ERROR));
			map.put("BZ", "存储行业分类信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SAVE_FACTORY_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SAVE_FACTORY_ERROR));
			map.put("BZ", "存储终端厂家信息异常!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_CDBH:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_CDBH));
			map.put("BZ", "传单号为空,撤单失败!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_NOTEXIST_CDBH:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_NOTEXIST_CDBH));
			map.put("BZ", "现场系统不存在此传单,无需撤单!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_SEND_PARAMS:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_SEND_PARAMS));
			map.put("BZ", "已进入下发测量点参数或下发任务流程,无法撤单!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_FLOWOVER:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_FLOWOVER));
			map.put("BZ", "流程已经结束,无法撤单!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_CONFIRM_PROGRAM:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_CONFIRM_PROGRAM));
			map.put("BZ", "装接方案已确认,无法撤单!");
			break ;
		case ErrorCode.MARKET_INTERFACE_FLAG_UPDATE_ERROR:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_UPDATE_ERROR));
			map.put("BZ", "数据更新异常,撤单失败!");
			break ;
		default:
			map = new HashMap<String, String>();
			map.put("ERRORCODE", String.valueOf(ErrorCode.MARKET_INTERFACE_FLAG_OTHER_ERROR));
			map.put("BZ", "未知错误");
			break ;
		}	
		return map ;
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
}