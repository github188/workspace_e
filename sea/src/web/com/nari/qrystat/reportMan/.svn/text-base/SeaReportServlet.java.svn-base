package com.nari.qrystat.reportMan;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.web.ReportServlet;

@SuppressWarnings("serial")
public class SeaReportServlet extends ReportServlet {
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException{
		
		Map params = request.getParameterMap();
		Set keys = params.keySet();
		for(Object key : keys){
			Object value = params.get(key);
			System.err.println((String)key + ":" + value.toString());
		}
		
		String reportID = request.getParameter("reportId");
		if(null != reportID)
			request.setAttribute("reportlet", "com.nari.qrystat.reportMan.ReportFactory");
		super.doGet(request, response);
	}
}
