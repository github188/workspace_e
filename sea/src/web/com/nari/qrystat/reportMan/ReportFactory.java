package com.nari.qrystat.reportMan;

import com.fr.report.ReportPageAttr;
import com.fr.report.ReportTemplate;
import com.fr.report.ReportWebAttr;
import com.fr.report.io.ExcelExportAttr;
import com.fr.report.io.ReportExportAttr;
import com.fr.report.io.TemplateImporter;
import com.fr.report.parameter.Parameter;
import com.fr.report.web.Printer;
import com.fr.web.Reportlet;
import com.fr.web.ReportletException;
import com.fr.web.ReportletRequest;
import com.nari.qrystat.reportQuery.ReportTypeQueryAction;

public class ReportFactory implements Reportlet {

	
	
	@SuppressWarnings("null")
	@Override
	public ReportTemplate createReport(ReportletRequest request)
			throws ReportletException {
		ReportTemplate workBook = null;
		int reportId= Integer.valueOf(request.getParameter("reportId").toString());
		ReportTypeQueryAction rtqa=new ReportTypeQueryAction();
		rtqa.setReportId(reportId);
		rtqa.queryReport();
	    TemplateImporter templateImporter = new TemplateImporter();     
        try {
			workBook = templateImporter.generate(rtqa.bais);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ReportWebAttr arr=workBook.getReportWebAttr();
//		Printer printer=arr.getPrinter();
//		String[] printerNames=printer.getPrinters();
//		String[] printers=null;
//		for(int i=0;i<printerNames.length;i++)
//		{
//			if(i==0)
//			{
//				printers[i]=printerNames[i];
//			}
//			
//		}
//		arr.getPrinter().setPrinters(printers);
//		workBook.setReportWebAttr(arr);
//		workBook.setReportPageAttr(null);
//		ReportPageAttr rpa=new ReportPageAttr();
//		
//		ExcelExportAttr eea=new ExcelExportAttr();
//	    
//		ReportExportAttr rea=new ReportExportAttr();
//		rea.setExcelExportAttr(null);
//		workBook.setReportExportAttr(null);
//		workBook.setReportWebAttr(null);
//		ReportWebAttr arr=new ReportWebAttr();
//		String[] printers=arr.getPrinter().getPrinters();
//		arr.getPrinter().setPrinters(printers);
//		arr.getPrinter().setPrinters(printers[3]);
//		arr.setPrinter(printers[1]);
		Parameter[] params = workBook.getParameters();
		for(Parameter p : params){
			String name = p.getName();
			String pa = (String)request.getParameter(name);
			if(null == pa){
				return null;
			}else{				
				p.setValue(pa);
			}
		}
		
		System.out.println(workBook.toString());
		
		return workBook;
	}

}
