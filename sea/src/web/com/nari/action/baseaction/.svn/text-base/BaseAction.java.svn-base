package com.nari.action.baseaction;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.nari.baseapp.planpowerconsume.ILOpTmnlLogManager;
import com.nari.logofsys.LOpTmnlLog;
import com.nari.privilige.PSysUser;
import com.nari.runman.dutylog.IUserLogManager;
import com.nari.runman.dutylog.LUserOpLog;
import com.nari.util.RealIP;
import com.nari.util.exception.DBAccessException;

/**
 * 类 BaseAction
 * 
 * @author zhangzhw
 * @describe 所有Action的基类
 */
public class BaseAction {
	//
	protected static final String SUCCESS = "success";
	protected static final String ERROR = "error";
	protected static Format format = new SimpleDateFormat("yyyy-MM-dd");

	// 注入类
	protected IUserLogManager iUserLogManager;
	protected ILOpTmnlLogManager iLOpTmnlLogManager;

	// 前台参数
	public String rootProperty;
	public String cm;
	public String dataMethod;
	public String cmDataIndex;
	public String cmDataType;

	// 返回值
	public Boolean success = true;
	public String exportContent;

	/**
	 * 
	 * @return 返回Request
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * 
	 * @return 返回Response
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 
	 * @return 返回Session
	 */
	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public PSysUser getPSysUser() {
		return (PSysUser) this.getSession().getAttribute("pSysUser");
	}

	/**
	 * 
	 * @param content
	 *            日志操作内容
	 * @param module
	 *            操作模块名
	 * @param opType
	 *            操作类型 01:查询 ,02：增加,03:删除,04:修改,05:注销
	 */
	public void addUserLog(String content, String module, String opType) {
		LUserOpLog log = new LUserOpLog();
		PSysUser pSysUser = (PSysUser) this.getSession().getAttribute(
				"pSysUser");
		String staffNo = pSysUser.getStaffNo();
		String orgNo = pSysUser.getOrgNo() == null ? "" : pSysUser.getOrgNo();
//		String ipAddr = this.getRequest().getRemoteAddr();
		//获取真实IP
		String ipAddr = RealIP.getIPAddr(this.getRequest());
		log.setOpContent(content == null ? "" : content.trim());
		log.setOpModule(module == null ? "" : module.trim());
		log.setOpType(opType == null ? "01" : opType);
		log.setEmpNo(staffNo);
		log.setOrgNo(orgNo);
		log.setIpAddr(ipAddr);

		this.iUserLogManager.addUserLog(log);
	}
	
	public LOpTmnlLog getLOpTmnlLog() throws UnknownHostException {
		LOpTmnlLog log = new LOpTmnlLog();
		PSysUser pSysUser = (PSysUser) getSession().getAttribute("pSysUser");
		String staffNo = pSysUser.getStaffNo();
		String orgNo = pSysUser.getOrgNo();
		InetAddress addr = InetAddress.getLocalHost();
	    String ipAddr=addr.getHostAddress().toString();//获得本机IP
	    log.setOrgNo(orgNo);
	    log.setEmpNo(staffNo);
	    log.setIpAddr(ipAddr);
	    return log;
	}
	/**
	 * 写日志
	 * 
	 * @param lOpTmnlLog
	 *            日志对象
	 */
	public void addOpTmnlLog(LOpTmnlLog lOpTmnlLog) {
		try {
			lOpTmnlLog.setIpAddr(RealIP.getIPAddr(this.getRequest()));
			this.iLOpTmnlLogManager.saveTmnlLog(lOpTmnlLog);
		} catch (DBAccessException e) {
			e.printStackTrace();
		}
	}

	// 导出Excel的通用类
	@SuppressWarnings("unchecked")
	public String exportExcel() throws Exception {

		// Class ownerClass = this.getClass();
		// 设置最大数据量
		//Method methodl = this.getClass().getMethod("setLimit", Integer.class);
		//Method methodLimit = this.getClass().getMethod("setLimit", null);
		//Method[] m = this.getClass().getMethods();
		
		// methodLimit.invoke(this, new Object[]{32767});

		// 取得前台调用的方法 并执行
		Method method = this.getClass().getMethod(this.dataMethod, null);
		String rtn = (String) method.invoke(this, null);

		// 根据 store指定的属性取得数据
		// Field field = ownerClass.getField(rootProperty);
		// Object property = field.get(this);
		Object property = this.invokeMethod(this, this.rootProperty);

		// 根据 GridPanel 的表头 生成Excel 数据
		String xml = this.genXmlData(property, this.cmDataType,
				this.cmDataIndex);

		String result = this.cm.replace("gridStoreData", xml).replace(
				"rowCountToreplace", ((List) property).size() + 3 + "");

		// getResponse().setContentType("application/vnd.ms-excel");
		// ss:ExpandedRowCount="2"
		// System.out.println("-------------------------------------");
		// System.out.println(result.indexOf("ss:ExpandedRowCount=\"2\""));
		// System.out.println(((List)property).size()+3 );
		// result=result.replaceAll("ss:ExpandedRowCount=\"2\"",
		// "ss:ExpandedRowCount=\""+((List)property).size()+3 +"\"");
		this.getResponse().setHeader("Content-Type",
				"application/force-download");
		this.getResponse()
				.setHeader("Content-Type", "application/vnd.ms-excel");
		this.getResponse().setHeader("Content-Disposition",
				"attachment;filename=export.xls");

		this.getResponse().getOutputStream().write(result.getBytes("UTF-8"));
		// getResponse().getWriter().write(result);
		// getResponse().flushBuffer();
		// exportContent = result;
		return null;
	}

	// 动态组成 Excel 的数据
	public String genXmlData(Object property, String cmDataType,
			String cmDataIndex) throws Exception {
		String[] dataType = cmDataType.split(",");
		String[] dataIndex = cmDataIndex.split(",");
		StringBuffer sb = new StringBuffer();

		List list = (List) property;
		for (int i = 0; i < list.size(); i++) {
			sb.append("<ss:Row>");
			String cellClass = null;
			if (i % 2 == 0)
				cellClass = "even";
			else
				cellClass = "odd";

			for (int j = 0; j < dataType.length; j++) {

				sb.append("<ss:Cell ss:StyleID=\"");
				sb.append(cellClass);
				sb.append("\"><ss:Data ss:Type=\"");
				sb.append(dataType[j] + "\">");
				Object object = null;
				try {
					object = this.invokeMethod(list.get(i), dataIndex[j]);
				} catch (Exception e) {
					object = null;
				}

				if (dataType[j].equals("DateTime")) {
					sb.append(format.format((Date) object));
				} else {
					sb.append(object);
				}
				sb.append("</ss:Data></ss:Cell>");
			}
			sb.append("</ss:Row>");
		}

		return sb.toString();
	}

	// 从Object 中执行 methodName 方法，并返回结果
	@SuppressWarnings("unchecked")
	private Object invokeMethod(Object object, String methodName)
			throws Exception {
		//黄轩修改开始  使这个方法能正常的处理map
		if(object instanceof Map){
			Map m=(Map) object;
			return m.get(methodName);
		}
		
		//黄轩修改结束
		Class ownerClass = object.getClass();

		String mName = "get" + methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);

		Method method = ownerClass.getMethod(mName, null);
		Object rtn = method.invoke(object, null);

		return rtn;
	}

	public void setiUserLogManager(IUserLogManager iUserLogManager) {
		this.iUserLogManager = iUserLogManager;
	}

	public void setiLOpTmnlLogManager(ILOpTmnlLogManager iLOpTmnlLogManager) {
		this.iLOpTmnlLogManager = iLOpTmnlLogManager;
	}

	public String getRootProperty() {
		return this.rootProperty;
	}

	public void setRootProperty(String rootProperty) {
		this.rootProperty = rootProperty;
	}

	public String getCm() {
		return this.cm;
	}

	public void setCm(String cm) {
		this.cm = cm;
	}

	public String getDataMethod() {
		return this.dataMethod;
	}

	public void setDataMethod(String dataMethod) {
		this.dataMethod = dataMethod;
	}

	public String getCmDataIndex() {
		return this.cmDataIndex;
	}

	public void setCmDataIndex(String cmDataIndex) {
		this.cmDataIndex = cmDataIndex;
	}

	public String getCmDataType() {
		return this.cmDataType;
	}

	public void setCmDataType(String cmDataType) {
		this.cmDataType = cmDataType;
	}

	public String getExportContent() {
		return this.exportContent;
	}

	public void setExportContent(String exportContent) {
		this.exportContent = exportContent;
	}

}
