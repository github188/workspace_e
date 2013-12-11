package com.nari.runman.archivesman;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import com.nari.action.baseaction.BaseAction;
import com.nari.action.baseaction.Errors;
import com.nari.customer.CCons;
import com.nari.grid.GTg;
import com.nari.runcontrol.CmeterJdbc;
import com.nari.runcontrol.CmpJdbc;
import com.nari.runcontrol.RCpConsRela;
import com.nari.runcontrol.RcpParaJdbc;
import com.nari.runcontrol.RcpRunJdbc;
import com.nari.runcontrol.RtmnlRunJdbc;
import com.nari.runcontrol.RConsTmnlRela;
import com.nari.util.ParseString;

/**
 * ImportPrivacyAction
 * 
 * @author zhangzhw
 * @describe 导入专变档案Action
 */
public class ImportPrivacyAction extends BaseAction {

	// 返回确认
	public boolean success = true;
	public Errors errors;
	
	public String msg;

	// 文件
	public String uploadFileName;
	public File upload;
	private String uploadContentType;
	private String savePath;

	// 注入类
	IImportExcelManager iImportExcelManager;
	

	// 返回值 同保存值
	private List<CCons> cconsList;
	private List<RcpRunJdbc> rcpList;
	private List<RcpParaJdbc> rcpparaList;
	private List<RtmnlRunJdbc> tmnlList;
	private List<CmpJdbc> cmpList;
	private List<CmeterJdbc> cmeterList;
	private List<RCpConsRela> rCpConsRelaList;
	private List<RConsTmnlRela> rConsTmnlRelaList;
	
	
	/**
	 * 导入专变数据
	 */
	public String importPrivacy() throws Exception{
		getResponse().setContentType("text/html; charset=utf-8");

		String rtn = parseExcel2List();
		if (rtn!=null) {
			errors = new Errors();
			errors.setTitle("错误");
			errors.setMsg(rtn);
			return SUCCESS;
		}

		try{
		this.msg = this.iImportExcelManager.savePrivacyExcel(
				cconsList, rcpList, rcpparaList, tmnlList, cmpList, cmeterList,
				rCpConsRelaList, rConsTmnlRelaList);
		}
		catch(Exception e)
		{
			errors = new Errors();
			errors.setTitle("错误");
			errors.setMsg(e.getMessage());
			return SUCCESS;
		}
		return SUCCESS;
	}

	/**
	 * 测试导入专变数据
	 */
	public String importPrivacyTest() {

		getResponse().setContentType("text/html; charset=utf-8");
		return SUCCESS;
	}

	/**
	 * 从用户sheet中解析出用户
	 */
	private String parseExcel2List() {

		InputStream is = null;
		Workbook rwb = null;

		String rtnmsg = "success";

		try {
			is = new FileInputStream(getUpload());
			rwb = Workbook.getWorkbook(is);

		} catch (Exception e) {
			return "读取EXCEL文件错误！";
		}

		if (rwb.getNumberOfSheets() < 4)
			return "EXCEL 文件并未包含所需要的表单！";

		// 取得三个表单
		//Sheet gtg = rwb.getSheet(0);
		Sheet ccons = rwb.getSheet(0);
		Sheet tmnl = rwb.getSheet(1);
		Sheet cmp = rwb.getSheet(2);
		Sheet meter = rwb.getSheet(3);

		try {
			
			this.cconsList = new ArrayList<CCons>();
			this.rcpList = new ArrayList<RcpRunJdbc>();
			this.rcpparaList = new ArrayList<RcpParaJdbc>();
			this.tmnlList = new ArrayList<RtmnlRunJdbc>();
			this.rCpConsRelaList = new ArrayList<RCpConsRela>();
			this.rConsTmnlRelaList = new ArrayList<RConsTmnlRela>();
			this.cmpList = new ArrayList<CmpJdbc>();
			this.cmeterList = new ArrayList<CmeterJdbc>();
			
			rtnmsg = parseConsSheet(ccons);
			if (rtnmsg != null)
				return rtnmsg;
			rtnmsg = parseTmnlSheet(tmnl);
			if (rtnmsg != null)
				return rtnmsg;

			rtnmsg = parseCmp(cmp);
			if (rtnmsg != null)
				return rtnmsg;

			rtnmsg = parseMeterSheet(meter);
			if (rtnmsg != null)
				return rtnmsg;

		} catch (Exception e) {
			e.printStackTrace();
			rwb.close();

		}
		rwb.close();

		return rtnmsg;
	}

	/**
	 * parseCCons
	 * 
	 * @param sheet
	 * @return 从EXCEL表单中解析出CCons
	 */
	private String parseConsSheet(Sheet s) throws Exception {
		// 从第三行开始循环
		for (int i = 2; i < s.getRows(); i++) {

			String[] str = new String[s.getColumns()];
			for (int j = 0; j < s.getColumns(); j++) {
				try {
					str[j] = s.getCell(j, i).getContents().trim();
				} catch (Exception e) {
					return "用户表第" + (i + 1) + "行,第" + (j + 1) + "列读取错误!";
				}
			}

			if (str[2] == null || "".equals(str[2])) {
				break;
			}
			if (str[3] == null || "".equals(str[3])) {
				break;
			}
			try {
				CCons c = new CCons();
				// 对CCons校验赋值
				c.setConsId(ParseString.str2long(str[2]));
				c.setCustId(-1L);
				c.setOrgNo(str[0]);
				c.setAreaNo(str[1]); // TODO:截掉后两位
				c.setConsNo(str[2]);
				c.setConsName(str[3]);
				c.setCustNo(str[4]);
				c.setSubsId(ParseString.str2long(str[5]));
				c.setTgId(ParseString.str2long(str[6]));
				c.setLineId(ParseString.str2long(str[7]));
				c.setCustQueryNo(str[8]);
				c.setTmpPayRelaNo(str[9]);
				c.setOrgnConsNo(str[10]);
				c.setConsSortCode(ParseString.str2cut(str[11]));
				c.setElecAddr(str[12]);
				c.setTradeCode(str[13]);
				c.setConsType(ParseString
						.str2byte(ParseString.str2cut(str[14])));
				c.setElecTypeCode(str[15]);
				c.setContractCap(ParseString.str2double(str[16]));
				c.setRunCap(ParseString.str2double(str[17]));
				c.setCapGradeNo(str[18]);
				c.setShiftNo(ParseString.str2cut(str[19]));
				c.setLodeAttrCode(ParseString.str2cut(str[20]));
				c.setVoltCode(str[21]); // TODO: EXCEL是否使用下拉
				c.setHecIndustryCode(str[22]);
				c.setHoliday(str[23]);
				c.setBuildDate(ParseString.str2Date(str[24]));
				c.setPsDate(ParseString.str2Date(str[25]));
				c.setCancelDate(ParseString.str2Date(str[26]));
				c.setDueDate(ParseString.str2Date(str[27]));
				c.setNotifyMode(str[28]);
				c.setSettleMode(str[29]);
				c.setStatusCode(str[30]);
				c.setRrioCode(str[31]);
				c.setChkCycle(ParseString.str2int(str[32]));
				c.setLastChkDate(ParseString.str2Date(str[33]));
				c.setCheckerNo(str[34]);
				c.setPoweroffCode(ParseString.str2cut(str[35]));
				c.setTransferCode(ParseString.str2cut(str[36]));
				c.setMrSectNo(str[37]);
				c.setNoteTypeCode(str[38]);
				c.setTmpFlag(ParseString.str2cut(str[39]));
				c.setTmpDate(ParseString.str2Date(str[40]));
				c.setApplyNo(str[41]);
				c.setApplyDate(ParseString.str2Date(str[42]));

				this.cconsList.add(c);

			} catch (Exception e) {
				return "用户表第" + i + "行转换错误!";
			}

		}
		return null;
	}

	/**
	 * 从终端sheet解析出采集点、采集点参数、运行终端、采集点用户关系和终端用户关系
	 * 
	 * @param s
	 * @throws Exception
	 */
	private String parseTmnlSheet(Sheet s) throws Exception {
		for (int i = 2; i < s.getRows(); i++) {
			String[] str = new String[s.getColumns()];
			for (int j = 0; j < s.getColumns(); j++) {
				try {
					str[j] = s.getCell(j, i).getContents().trim();
				} catch (Exception e) {
					return "终端表第" + (i + 1) + "行,第" + (j + 1) + "列读取错误!";
				}
			}

			if (str[0] == null || "".equals(str[0]))
				break; // 用户编号
			// if(str[1] == null || "".equals(str[1])) break; //采集点编号
			// if(str[2] == null || "".equals(str[2])) break; //采集点名称
			// if(str[3] == null || "".equals(str[3])) break; //采集点类型
			// if(str[8] == null || "".equals(str[8])) break; //通信参数标识
			if (str[1] == null || "".equals(str[1]))
				break; // 终端地址码
			if (str[2] == null || "".equals(str[2]))
				break; // 通信规约类型
			if (str[21] == null || "".equals(str[21]))
				break; // 算法编号
			if (str[22] == null || "".equals(str[22]))
				break; // 算法密钥
			if (str[23] == null || "".equals(str[23]))
				break; // 终端编号
			if (str[24] == null || "".equals(str[24]))
				break; // 终端资产编号
			// if(str[33] == null || "".equals(str[33])) break; //终端地址码
			if (str[29] == null || "".equals(str[29]))
				break; // 生产厂家
			if (str[31] == null || "".equals(str[31]))
				break; // 终端类型
			if (str[32] == null || "".equals(str[32]))
				break; // 采集方式
			if (str[35] == null || "".equals(str[35]))
				break; // 终端运行状态
			if (str[44] == null || "".equals(str[44]))
				break; // 任务上送方式

			try {
				RcpRunJdbc rcp = new RcpRunJdbc();
				rcp.setCpNo(str[23]);
				rcp.setName("用户采集点:" + str[0]);
				rcp.setCpTypeCode("2");
				rcp.setStatusCode("04");
				rcp.setCpAddr("待更新为用户用电地址");
				rcp.setGpsLongitude("");
				rcp.setGpsLatitude("");
				rcpList.add(rcp);

				RCpConsRela rcpcons = new RCpConsRela();
				rcpcons.setCpNo(str[23]);
				rcpcons.setConsId(ParseString.str2long(str[0]));
				rcpcons.setCpConsId(ParseString.str2long(str[0]));
				this.rCpConsRelaList.add(rcpcons);

				RConsTmnlRela rconstmnl = new RConsTmnlRela();
				rconstmnl.setConsNo(str[0]);
				rconstmnl.setTmnlAssetNo(str[24]);
				this.rConsTmnlRelaList.add(rconstmnl);

				RcpParaJdbc rcpPara = new RcpParaJdbc();
				rcpPara.setCommParaId(ParseString.str2long(str[23]));
				rcpPara.setCpNo(str[23]);
				rcpPara.setTerminalAddr(str[1]);
				rcpPara.setProtocolCode(ParseString.str2cut(str[2]));
				rcpPara.setChannelNo(str[3]);
				rcpPara.setRtsOn(ParseString.str2int(str[4]));
				rcpPara.setRtsOff(ParseString.str2int(str[5]));
				rcpPara.setTransmitDelay(ParseString.str2int(str[6]));
				rcpPara.setRespTimeout(ParseString.str2int(str[7]));
				rcpPara.setMasterIp(str[8]);
				rcpPara.setMasterPort(ParseString.str2int(str[9]));
				rcpPara.setSpareIpAddr(str[10]);
				rcpPara.setSparePort(ParseString.str2int(str[11]));
				rcpPara.setGatewayIp(str[12]);
				rcpPara.setGatewayPort(ParseString.str2int(str[13]));
				rcpPara.setProxyIpAddr(str[14]);
				rcpPara.setProxyPort(ParseString.str2int(str[15]));
				rcpPara.setGprsCode(str[16]);
				rcpPara.setSmsNo(str[17]);
				rcpPara.setApn(str[9]);
				rcpPara.setHeartbeatCycle(ParseString.str2int(str[19]));
				rcpPara.setStartDate(ParseString.str2Date(str[20]));
				rcpPara.setAlgNo(str[21]);
				rcpPara.setAlgKey(str[22]);
				this.rcpparaList.add(rcpPara);

				RtmnlRunJdbc tmnl = new RtmnlRunJdbc();
				tmnl.setTerminalId(str[23]);
				tmnl.setCpNo(str[23]);
				tmnl.setTmnlAssetNo(str[24]);
				tmnl.setTerminalAddr(str[25]);
				tmnl.setCisAssetNo(str[26]);
				tmnl.setSimNo(str[27]);
				tmnl.setId(str[28]);
				tmnl.setFactoryCode(ParseString.str2cut(str[29]));
				tmnl.setAttachMeterFlag(ParseString.str2cut(str[30]));
				tmnl.setTerminalTypeCode(ParseString.str2cut(str[31]));
				tmnl.setCollMode(ParseString.str2cut(str[32]));
				tmnl.setProtocolCode(ParseString.str2cut(str[2]));
				tmnl.setCommPassword(str[33]);
				tmnl.setRunDate(ParseString.str2Date(str[34]));
				tmnl.setStatusCode(ParseString.str2cut(str[35]));
				tmnl.setHarmonicDevFlag(ParseString.str2cut(str[36]));
				tmnl.setPsEnsureFlag(ParseString.str2cut(str[37]));
				tmnl.setAcSamplingFlag(ParseString.str2cut(str[38]));
				tmnl.setEliminateFlag(ParseString.str2cut(str[39]));
				// tmnl.setGateAttrFlag(ParseString.str2cut(str[40]));
				tmnl.setPrioPsMode(ParseString.str2cut(str[40]));
				tmnl.setFreezeMode(str[41]);
				tmnl.setFreezeCycleNum(ParseString.str2int(str[42]));
				tmnl.setMaxLoadCurveDays(ParseString.str2int(str[43]));
				tmnl.setPsLineLen(null);
				tmnl.setWorkPs(null);
				tmnl.setSpeakerFlag(null);
				tmnl.setSpeakerDist(null);
				tmnl.setSendUpMode(ParseString.str2byte(ParseString
						.str2cut(str[44])));
				tmnl.setCommMode(str[45]);
				tmnl.setFrameNumber(null);
				tmnl.setPowerCutDate(null);
				// 数据库有而pojo没有，待添加
				// TODO:tmnl.setWiringMode(str[61]);
				this.tmnlList.add(tmnl);
			} catch (Exception e) {
				return "终端表第" + (i + 1) + "行转换错误!";
			}
		}
		return null;

	}

	private String parseCmp(Sheet s) throws Exception {
		// 从第三行开始循环
		for (int i = 2; i < s.getRows(); i++) {
			String[] str = new String[s.getColumns()];
			for (int j = 0; j < s.getColumns(); j++) {
				try {
					str[j] = s.getCell(j, i).getContents().trim();
				} catch (Exception e) {
					return "计量点表第" + (i + 1) + "行,第" + (j + 1) + "列读取错误!";
				}
			}

			if (str[2] == null || "".equals(str[2]))
				break; // 计量点编号
			// if(str[4] == null || "".equals(str[4])) break; //计量点名称
			// if(str[15] == null || "".equals(str[15])) break; //电能表流水号
			// if(str[22] == null || "".equals(str[22])) break; //综合倍率
			// if(str[43] == null || "".equals(str[43])) break; //抄表系数

			try {
				CmpJdbc cmp = new CmpJdbc();
				cmp.setMpId(ParseString.str2long(str[2]));
				cmp.setMpSectId(ParseString.str2long(str[2]));
				cmp.setSpId(ParseString.str2long(str[2]));
				cmp.setMpNo(str[2]);
				cmp.setMpName(str[3]);
				cmp.setOrgNo(str[4]);
				cmp.setConsId(ParseString.str2long(str[0]));
				cmp.setConsNo(str[0]);
				cmp.setTgId(ParseString.str2long(str[1]));
				cmp.setLineId(ParseString.str2long(str[5]));
				cmp.setMrSectNo(str[6]);
				cmp.setMpAddr(str[7]);
				cmp.setTypeCode(ParseString.str2cut(str[8]));
				cmp.setMpAttrCode(ParseString.str2cut(str[9]));
				cmp.setUsageTypeCode(ParseString.str2cut(str[10]));
				cmp.setSideCode(ParseString.str2cut(str[11]));
				cmp.setVoltCode(ParseString.str2cut(str[12]));
				cmp.setAppDate(ParseString.str2Date(str[13]));
				cmp.setRunDate(ParseString.str2Date(str[14]));
				cmp.setWiringMode(ParseString.str2cut(str[15]));
				cmp.setMeasMode(ParseString.str2cut(str[16]));
				cmp.setSwitchNo(ParseString.str2cut(str[17]));
				cmp.setExchgTypeCode(ParseString.str2cut(str[18]));
				cmp.setMdTypeCode(ParseString.str2cut(str[19]));
				cmp.setMrSn(ParseString.str2int(str[20]));
				cmp.setMpSn(ParseString.str2int(str[21]));
				cmp.setMeterFlag(ParseString.str2cut(str[22]));
				cmp.setStatusCode(ParseString.str2cut(str[13]));
				cmp.setLcFlag(ParseString.str2cut(str[24]));
				cmp.setEarthMode(ParseString.str2cut(str[25]));

				this.cmpList.add(cmp);
			} catch (Exception e) {
				return "计量点表第" + (i + 1) + "行转换错误!";
			}

		}
		return null;
	}

	/**
	 * 从电表sheet里解析c_meter
	 */
	private String parseMeterSheet(Sheet s) throws Exception {
		// 从第三行开始循环
		for (int i = 2; i < s.getRows(); i++) {
			String[] str = new String[s.getColumns()];
			for (int j = 0; j < s.getColumns(); j++) {
				try {
					str[j] = s.getCell(j, i).getContents().trim();
				} catch (Exception e) {
					return "电能表表第" + (i + 1) + "行,第" + (j + 1) + "列读取错误!";
				}
			}

			if (str[0] == null || "".equals(str[0]))
				break; // 计量点编号
			// if(str[4] == null || "".equals(str[4])) break; //计量点名称
			if (str[15] == null || "".equals(str[1]))
				break; // 电能表流水号
			if (str[22] == null || "".equals(str[10]))
				break; // 综合倍率
			// if(str[43] == null || "".equals(str[43])) break; //抄表系数
			try {
				CmeterJdbc cmeter = new CmeterJdbc();
				cmeter.setMeterId(ParseString.str2long(str[1]));
				cmeter.setAssetNo(str[2]);
				cmeter.setMpId(ParseString.str2long(str[0]));
				cmeter.setOrgNo("1");
				// if(str[2]!=null && str[2].length()>3)
				cmeter.setAreaNo("1");
				cmeter.setConsNo("1");
				cmeter.setBaudrate(str[3]);
				cmeter.setCommNo(ParseString.str2cut(str[4]));
				cmeter.setCommAddr1(str[5]);
				cmeter.setCommAddr2(str[6]);
				cmeter.setCommMode(ParseString.str2cut(str[7]));
				cmeter.setInstLoc(str[8]);
				cmeter.setInstDate(ParseString.str2Date(str[9]));
				cmeter.settFactor(ParseString.str2double(str[10]));
				cmeter.setRefMeterFlag(str[11]);
				cmeter.setRefMeterId(ParseString.str2long(str[12]));
				cmeter.setValidateCode(str[13]);
				cmeter.setModuleNo(str[14]);
				cmeter.setMrFactor(str[15]);
				cmeter.setLastChkDate(ParseString.str2Date(str[16]));
				cmeter.setRotateCycle(ParseString.str2int(str[17]));
				cmeter.setRotateValidDate(ParseString.str2Date(str[18]));
				cmeter.setChkCycle(ParseString.str2int(str[19]));
				cmeter.setTmnlAssetNo(str[20]);
				// cmeter.setFmrAssetNo(str[50]);
				cmeter.setRegStatus(ParseString.str2byte(str[21]));
				cmeter.setRegSn(ParseString.str2int(str[22]));
				this.cmeterList.add(cmeter);
			} catch (Exception e) {
				return "电能表表第" + (i + 1) + "行,转换错误!";
			}

		}
		return null;
	}

	// getters and setters

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Errors getErrors() {
		return errors;
	}

	public void setErrors(Errors errors) {
		this.errors = errors;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public List<CCons> getCconsList() {
		return cconsList;
	}

	public void setCconsList(List<CCons> cconsList) {
		this.cconsList = cconsList;
	}

	public List<RcpRunJdbc> getRcpList() {
		return rcpList;
	}

	public void setRcpList(List<RcpRunJdbc> rcpList) {
		this.rcpList = rcpList;
	}

	public List<RcpParaJdbc> getRcpparaList() {
		return rcpparaList;
	}

	public void setRcpparaList(List<RcpParaJdbc> rcpparaList) {
		this.rcpparaList = rcpparaList;
	}

	public List<RtmnlRunJdbc> getTmnlList() {
		return tmnlList;
	}

	public void setTmnlList(List<RtmnlRunJdbc> tmnlList) {
		this.tmnlList = tmnlList;
	}

	public List<CmpJdbc> getCmpList() {
		return cmpList;
	}

	public void setCmpList(List<CmpJdbc> cmpList) {
		this.cmpList = cmpList;
	}

	public List<CmeterJdbc> getCmeterList() {
		return cmeterList;
	}

	public void setCmeterList(List<CmeterJdbc> cmeterList) {
		this.cmeterList = cmeterList;
	}

	public List<RCpConsRela> getrCpConsRelaList() {
		return rCpConsRelaList;
	}

	public void setrCpConsRelaList(List<RCpConsRela> rCpConsRelaList) {
		this.rCpConsRelaList = rCpConsRelaList;
	}

	public List<RConsTmnlRela> getrConsTmnlRelaList() {
		return rConsTmnlRelaList;
	}

	public void setrConsTmnlRelaList(List<RConsTmnlRela> rConsTmnlRelaList) {
		this.rConsTmnlRelaList = rConsTmnlRelaList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setiImportExcelManager(IImportExcelManager iImportExcelManager) {
		this.iImportExcelManager = iImportExcelManager;
	}
}
