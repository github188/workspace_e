package com.nari.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Random;
public class Database2byte{	
	public Connection getConnection()
	{
		Connection conn = null;
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@10.215.191.19:1521:SEA", "sea", "seats");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	public  ByteArrayInputStream getInputStream(String sql) throws Exception
	{
		Connection conn=getConnection();
		PreparedStatement ps;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] reportData = null;
		try {
			ps = conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();			
			ResultSetMetaData rm=rs.getMetaData();
			int columns=rm.getColumnCount();
			System.out.println(columns);
			bos =new ByteArrayOutputStream();
			int ch;
			while(rs.next())
			{
				
				InputStream stream=rs.getBinaryStream("REPORT_CONTENT");
				String s=rs.getObject(1).toString();
				System.out.println(s);
				Blob b= rs.getBlob("REPORT_CONTENT");
				int i = 0;
                if(stream!=null)
				{
				 while((ch = stream.read()) != -1) {
					bos.write(ch);
                       }
				}
				else{
					System.out.println("报表为空");
				}
			}
				System.out.println(bos.size()+"大小");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		reportData = bos.toByteArray();
		ByteArrayInputStream bis=new ByteArrayInputStream(reportData);
		System.out.println(bis.available()+"最后一个大小");
		return bis;		
	}
	private void saveFileToDb(String reportType,String reportName,String reportfileName,String jsFilename,String description){
		FileInputStream stream1,stream2;
		try {
			File file1= new File(reportfileName);
			long length1 =(int) file1.length();
			byte [] data1 = new byte[(int)length1];
			stream1 = new FileInputStream(file1);
			int rlen1 = stream1.read(data1);
			File file2= new File(jsFilename);
			long length2 =(int) file2.length();
			byte [] data2 = new byte[(int)length2];
			stream2 = new FileInputStream(file2);
			int rlen2 = stream2.read(data2);
			Connection conn=getConnection();
			Random ran=new Random();
			PreparedStatement ps=conn.prepareStatement("insert into b_report_setup values(?,?,?,?,?,?,?,?)");
			InputStream bais1=new ByteArrayInputStream(data1);
			InputStream bais2=new ByteArrayInputStream(data2);
			ps.setObject(2, reportType);
			ps.setObject(1,ran.nextInt(1000));//ps.setObject(1, ran.nextInt(1000));
			ps.setObject(3, reportName);
		    ps.setObject(4, (int)length1);
		    ps.setBinaryStream(5, bais1,(int)length1);
		    ps.setObject(6, description);
		    ps.setObject(7, 1);
			ps.setBinaryStream(8, bais2,(int)length2);
			ps.execute();
			stream1.close();
			stream2.close();
			ps.close();
			conn.close();
		}catch (FileNotFoundException fnf) {
			// TODO Auto-generated catch block
			fnf.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	/**
	 * @param args
	 * @throws Exception 
	 */	
	public static void main(String[] args) throws Exception 
	{  
		Database2byte db=new Database2byte();
//1.电表数据报表
		db.saveFileToDb("电表数据报表","电表数据报表111","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\电表数据报表\\meterDataReport.cpt","D:\\报表文件\\电表数据报表\\meterDataReport1.js","列出用户编号、用户名称、终端地址、CT、PT、正向有功总峰平谷示值及正向无功总示值、反向有功总示值、反向无功总示值");		
//		db.saveFileToDb("电表数据报表","电表电量数据报表t","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\电表数据报表\\meterEnergyReport.cpt","D:\\报表文件\\电表数据报表\\meterEnergyReport.js","可选展示的用户信息、终端信息、电表信息以及电表示值报表内容");
//		db.saveFileToDb("电表数据报表","电表自定义数据报表2","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\电表数据报表\\meterDefinitionReport.cpt","D:\\报表文件\\电表数据报表\\meterDefinitionReport.js","可选展示的用户信息、终端信息、电表信息以及电表示值报表内容");
//		db.saveFileToDb("电表数据报表","电表日时段示数报表2","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\电表数据报表\\meterDayPeriodReport.cpt","D:\\报表文件\\电表数据报表\\meterDayPeriodReport.js","打印所选时间段内每日的电表示值");
//		db.saveFileToDb("电表数据报表","电表数据导出2","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\电表数据报表\\meterDataExport.cpt","D:\\报表文件\\电表数据报表\\meterDefinitionReport.js","进行电压、电流、示值、电量的整点数据报表导出");
//2.采集点数据报表
//		db.saveFileToDb("采集点数据报表","采集点档案报表","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\采集点数据报表\\meterDefinitionReport.cpt","D:\\报表文件\\采集点数据报表\\meterDefinitionReport.js","采集点台账报表，可以选择显示的字段内容");
//		db.saveFileToDb("采集点数据报表","采集点日数据报表","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\采集点数据报表\\meterDataReport.cpt","D:\\报表文件\\采集点数据报表\\meterDataReport.js","单采集点数据综合报表，涵盖采集点总加组数据");		
//3.地区数据报表
//		db.saveFileToDb("地区数据报表","采集终端安装统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\meterDefinitionReport.js","按电压等级打印各下属供电单位的各运行容量段内各终端状态的终端数");
//		db.saveFileToDb("地区数据报表","实采电量情况统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\realFetchMeterEnergy.js","按下级单位统计售电量、采集电量、实采电量比重");		
//		db.saveFileToDb("地区数据报表","终端安装及运行统计7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\tmnlRunStatusStat.js","打印各供电单位按通信类型划分的各类终端安装数、运行终端数、调通终端数、未调通终端数、待装终端数、采集用户数");
//		db.saveFileToDb("地区数据报表","终端运行调试状态统计7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\tmnlRunDebug.js","打印按各下级供电单位各类通信信道的终端数，调通终端数，合计调通比率");		
//		db.saveFileToDb("地区数据报表","行业采集电量报表77","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\industryCollEnergy.js","打印下属各供电单位八大行业电量统计报表");
//		db.saveFileToDb("地区数据报表","电压等级采集电量报表77","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\voltDegreeCollEnergy.js","打印下属各供电单位按电压等级的电量统计报表");		
//		db.saveFileToDb("地区数据报表","无数据终端统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\tmnlWithoutDataStat.js","打印下属各供电单位按终端生产厂家终端数、无数据终端数、无数据终端比率");
//		db.saveFileToDb("地区数据报表","故障统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\failureStat.js","打印各类故障数统计、占比");		
//		db.saveFileToDb("地区数据报表","营销流程统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\salesProcess.js","打印营销流程统计报表");
//		db.saveFileToDb("地区数据报表","采集终端安装分布统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\collTmnlInstallDistribution.js","按运行容量、电压等级、供电单位打印多组合分类统计终端安装量、远采电表量");		
//		db.saveFileToDb("地区数据报表","采集成功率统计报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\collSucessPerReport.js","按供电单位统计采集成功率统计");		
//		db.saveFileToDb("地区数据报表","整点采集电量月汇总表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\sharpPointEnergyofMon.js","按供电单位打印某月每日24点电量统计");
//		db.saveFileToDb("地区数据报表","无数据终端明细报表7","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\地区数据报表\\collSucessPerReport.cpt","D:\\报表文件\\地区数据报表\\meterDataReport.js","打印时间段内无数据终端，信息包含用户编号、用户名称、终端地址、表计条码、发生时间");		
//		
//4.用户数据报表		
//		db.saveFileToDb("用户数据报表","用户日电量明细2","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\用户数据报表\\consDayEnergyDetail.cpt","D:\\报表文件\\用户数据报表\\consDayEnergyDetail.js","打印用户所选月份的每日电量及电量合计");				
//	    db.saveFileToDb("用户数据报表","用户月电量明细2","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\用户数据报表\\consSelectedMonDetail.cpt","D:\\报表文件\\用户数据报表\\consSelectedMonDetail.js","打印用户所选月份的每日整点电量及电量合计");
//	    db.saveFileToDb("用户数据报表","用户月负荷明细2","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\用户数据报表\\consMonLoadDetail.cpt","D:\\报表文件\\用户数据报表\\consMonLoadDetail.js","打印用户月每日整点负荷");

//5.用户数据报表
//		db.saveFileToDb("统计报表","功率报表","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\powerReport.cpt","D:\\报表文件\\统计报表\\powerReport.js","打印批量用户的日整点负荷、日电量、最大功率、变压器容量");
//		db.saveFileToDb("统计报表","电量报表","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\powerReport.cpt","D:\\报表文件\\统计报表\\energyReport.js","打印批量用户的最大负荷、负荷率、正向有功总峰平谷电量、峰谷比、功率因数、正向无功总电量");		
//		db.saveFileToDb("统计报表","系统月电量报表1","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\systemMonthEnergy.cpt","D:\\报表文件\\统计报表\\systemMonthEnergy.js","打印系统各采集点月每日电量及月总电量、合计出系统每日电量及月总电量");
//		db.saveFileToDb("统计报表","抄读失败电表报表1","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\powerReport.cpt","D:\\报表文件\\统计报表\\sendDataFailure.js","打印抄表失败详情：供电单位、采集点编号、采集点名称、用电地址、用户编号、用户名称、终端型号、终端地址、终端生产厂家、计量点编号、抄表段号、抄表员、电压等级、线路号、线路名称、电表资产号、电表状态、电表生产厂家、电表规约类型");		
//		db.saveFileToDb("统计报表","台区线损统计1","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\powerReport.cpt","D:\\报表文件\\统计报表\\tgLineLoss.js","台区编号、终端地址、所属供电单位、抄表块数、成功抄表块数、抄表成功率、供电量、售电量、损失率");
//		db.saveFileToDb("统计报表","预购电工单汇总1","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\powerReport.cpt","D:\\报表文件\\统计报表\\subscribedEleBillStat.js","用户名称、用户编号、终端地址、购电单号、购电时间、购电金额、报警门限、跳闸门限");		
//		db.saveFileToDb("统计报表","变压器利用率统计1","C:\\FineReport6.5\\WebReport\\WEB-INF\\reportlets\\统计报表\\powerReport.cpt","D:\\报表文件\\统计报表\\transformerUsage.js","供电单位、用户名称、变压器编号、视在功率、容量、利用率");
		
//		db.saveFileToDb("电表数据报表","电表电量数据报表测试","C:\\Documents and Settings\\Administrator\\桌面\\2.cpt","D:\\报表文件\\reportParamsSecond.js","选择日期时段、显示起止日期示值，计算差值，计算电量");
//		db.saveFileToDb("电表数据报表","电表自定义数据报表",
//		db.saveFileToDb("电表数据报表","电表日时段示数报表",
//		db.saveFileToDb("电表数据报表","电表数据导出",
//		db.saveFileToDb("电表数据报表","抄表日统计月电量",
//		
//		db.saveFileToDb("采集点数据报表","电表数据报表","C:\\Documents and Settings\\Administrator\\桌面\\reportParamsSecond.js","C:\\Documents and Settings\\Administrator\\桌面\\reportParamsSecond.js","报表1","报表1");
//		db.saveFileToDb("采集点数据报表","采集点日数据报表",
//		
//		db.saveFileToDb("地区数据报表","采集终端安装统计报表",
//		db.saveFileToDb("地区数据报表","实采电量情况统计报表",
//		db.saveFileToDb("地区数据报表","终端安装及运行统计",
//		db.saveFileToDb("地区数据报表","终端运行调试状态统计",
//		db.saveFileToDb("地区数据报表","实采电量情况统计报表",
//		db.saveFileToDb("地区数据报表","行业采集电量报表",
//		db.saveFileToDb("地区数据报表","电压等级采集电量报表",
//		db.saveFileToDb("地区数据报表","无数据终端统计报表",
//		db.saveFileToDb("地区数据报表","故障统计报表",
//		db.saveFileToDb("地区数据报表","营销流程统计报表",
//		db.saveFileToDb("地区数据报表","采集终端安装分布统计报表",
//		db.saveFileToDb("地区数据报表","采集成功率统计报表",
//		db.saveFileToDb("地区数据报表","整点采集电量月汇总表",
//		db.saveFileToDb("地区数据报表","无数据终端明细报表",
//		
//		db.saveFileToDb("用户数据报表","用户日电量明细",
//	    db.saveFileToDb("用户数据报表","用户月电量明细",
//	    db.saveFileToDb("用户数据报表","用户月负荷明细",	
//	    
//	    db.saveFileToDb("统计报表","功率报表",
//	    db.saveFileToDb("统计报表","电量报表",
//	    db.saveFileToDb("统计报表","系统月电量报表",
//	    db.saveFileToDb("统计报表","抄读失败电表报表",
//	    db.saveFileToDb("统计报表","台区线损统计",
//	    db.saveFileToDb("统计报表","预购电工单汇总",
//	    db.saveFileToDb("统计报表","变压器利用率统计",		
//	    						
//		db.getInputStream("select * from b_report_setup");
//		//System.out.println(b);		
	}
	}
