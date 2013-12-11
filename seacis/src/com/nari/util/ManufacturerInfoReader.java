package com.nari.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import com.nari.exception.ExceptionHandle;

/**
 * 读取厂家编码的工具类
 */
public class ManufacturerInfoReader {

	protected final Log log = LogFactory.getLog(getClass());

	/** 实例 */
	private static ManufacturerInfoReader mir;

	/** 模块名=(命令名=(属性列表))Map */
	private static Map<String, String> anufacturerInfoMap = new HashMap<String, String>();

	/**
	 * 获取实例
	 * 
	 * @return CommandPropertyReader
	 */
	public static ManufacturerInfoReader getInstance() {
		// 单态
		if (mir == null) {
			mir = new ManufacturerInfoReader();
		}
		return mir;
	}

	/**
	 * 构造函数
	 */
	private ManufacturerInfoReader() {
		// 读取XML配置文件
		readXML();
	}

	/**
	 * 读取XML配置文件, 并把内容存储到moduleMap
	 */
	private static void readXML() {

		SAXBuilder sb = new org.jdom.input.SAXBuilder();
		try {
			// 配置文件路径
			URL url = ManufacturerInfoReader.class.getResource("/manufacturer.xml");
			// 创建文档
			Document doc = sb.build(url);
			// 获得这个文档得跟元素
			Element root = doc.getRootElement();
			// 获得这个跟元素，的所有子元素(厂家列表)
			List<?> manufacturerList = root.getChildren();
			for (Iterator<?> manufacturerIt = manufacturerList.iterator(); manufacturerIt.hasNext();) {
				// 模块Element
				Element manufacturer = (Element) manufacturerIt.next();
				String cjbm = manufacturer.getAttributeValue("cjbm");
				String broadcastAddress = manufacturer.getAttributeValue("broadcastAddress");
				anufacturerInfoMap.put(cjbm, broadcastAddress);
			}
		} catch (Exception e) {
			ExceptionHandle.handleUnCheckedException(e);
		}
	}

	public Map<String, String> getAnufacturerInfoMap() {
		return anufacturerInfoMap;
	}
}