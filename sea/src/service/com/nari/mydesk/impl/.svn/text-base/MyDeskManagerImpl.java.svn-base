package com.nari.mydesk.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nari.mydesk.CommFlowDTO;
import com.nari.mydesk.ElecOrderDTO;
import com.nari.mydesk.IMyDeskDao;
import com.nari.mydesk.IMyDeskManager;
import com.nari.mydesk.Model;

public class MyDeskManagerImpl implements IMyDeskManager {
	private IMyDeskDao iMyDeskDao ;
	
	
	
	public void setiMyDeskDao(IMyDeskDao iMyDeskDao) {
		this.iMyDeskDao = iMyDeskDao;
	}



	public List<ElecOrderDTO> getElecOrder(String staff_no,String date) throws Exception{
		return this.iMyDeskDao.findAllOrder(staff_no, date);
	}
	
	public List<CommFlowDTO> getCommFlow(String staffNo) throws Exception{
		return iMyDeskDao.findAllCommFlow(staffNo);
	}

	/**
	 * @desc 读取配置文件，获取 portlet
	 * @return
	 * @throws Exception
	 */
	public List<Model> getAllModel() throws Exception{
		SAXReader reader = new SAXReader();
		Document doc = reader.read(this.getClass().getClassLoader().getResourceAsStream("config.xml"));
		Element root = doc.getRootElement();
		
		Element el ;
		
		List<Model> ls = new ArrayList<Model>(); 
		
		for(Iterator i = root.elementIterator("model");i.hasNext();){
			el = (Element)i.next();
			Model model = new Model ();
			model.setId(el.attributeValue("id"));
			model.setName(el.attributeValue("name"));
			model.setImage(el.attributeValue("image"));
			model.setPath(el.attributeValue("path"));
			ls.add(model);
		}
		
		return ls;
	}
}
