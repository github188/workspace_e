package com.nari.action.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.nari.action.BaseAction;
import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.basicdata.ATmnlRealTime;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.ami.front.taskschedule.TaskHandle;
import com.nari.eventbean.BackFeedEvent;
import com.nari.global.Constant;
import com.nari.quartz.QuartzJob;
import com.nari.service.MarketTerminalConfigManager;
import com.nari.util.StringUtil;
import com.nari.util.XMLSwitchUtil;
import com.nari.webservice.IEIGWSForMarketing;

public class TestAction extends BaseAction {

	private MarketTerminalConfigManager marketTerminalConfigManager;

	public MarketTerminalConfigManager getMarketTerminalConfigManager() {
		return marketTerminalConfigManager;
	}

	public void setMarketTerminalConfigManager(
			MarketTerminalConfigManager marketTerminalConfigManager) {
		this.marketTerminalConfigManager = marketTerminalConfigManager;
	}

	// 测试
	public ActionForward test(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		getSynDataForMarketingManager().synData("test");
		return mapping.findForward("test");
	}

	// 负控调试
	public ActionForward FK(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String message = null;

		String appNo = request.getParameter("appNo");

		if (appNo == null || "".equals(appNo)) {
			message = "appNo 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("appNo 为空或为 null");
			return mapping.findForward("test");
		}

		marketTerminalConfigManager = (MarketTerminalConfigManager) Constant
				.getCtx().getBean("marketTerminalConfigManager");
		List<?> iImnlTaskList = marketTerminalConfigManager
				.getITmnlTaskByAppNo(appNo);

		Map<String, Object> map = new HashMap<String, Object>();
		XMLSwitchUtil xmlSwitch = new XMLSwitchUtil();
		if (iImnlTaskList != null && iImnlTaskList.size() > 0) {
			Map<?, ?> iImnlTaskMap = (Map<?, ?>) iImnlTaskList.get(0);

			map.put("APP_NO", appNo);
			map.put("TERMINAL_ID", StringUtil.removeNull(iImnlTaskMap
					.get("TERMINAL_ID")));// 终端Id
			map.put("TMNL_TASK_TYPE", StringUtil.removeNull(iImnlTaskMap
					.get("TMNL_TASK_TYPE")));// 终端新装
			map.put("CONS_CHG_TYPE", StringUtil.removeNull(iImnlTaskMap
					.get("CONS_CHG_TYPE")));
			map.put("METER_FLAG", StringUtil.removeNull(iImnlTaskMap
					.get("METER_FLAG")));// 电能表变更
			map.put("CONS_NO", StringUtil.removeNull(iImnlTaskMap
					.get("CONS_NO")));// 户号
			map.put("TG_ID", StringUtil.removeNull(iImnlTaskMap.get("TG_ID")));
			map.put("MP_NO", StringUtil.removeNull(iImnlTaskMap.get("MP_NO")));
			map.put("TYPE_CODE", StringUtil.removeNull(iImnlTaskMap
					.get("TYPE_CODE")));
			map.put("USAGE_TYPE_CODE", StringUtil.removeNull(iImnlTaskMap
					.get("USAGE_TYPE_CODE")));
			map.put("NEW_TERMINAL_ID", StringUtil.removeNull(iImnlTaskMap
					.get("NEW_TERMINAL_ID")));
			map.put("ELEC_ADDR", StringUtil.removeNull(iImnlTaskMap
					.get("ELEC_ADDR")));
			map.put("CONS_ID", StringUtil.removeNull(iImnlTaskMap
					.get("CONS_ID")));
			map.put("CP_NO", StringUtil.removeNull(iImnlTaskMap.get("CP_NO")));
			map.put("WKST_APP_NO", StringUtil.removeNull(iImnlTaskMap
					.get("WKST_APP_NO")));
		}
		// String xxx = xmlSwitch.switchMapToXMLClient(map,"");

		// System.out.println("【模拟调试开始】" + xxx );
		// 通过WS调用
		IEIGWSForMarketing iEIGWSForMarketing = (IEIGWSForMarketing) Constant
				.getCtx().getBean("iEIGWSForMarketing");
		// iEIGWSForMarketing.WS_TMNL_TASK_SR(xxx);

		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	// 集抄调试
	public ActionForward JC(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String message = null;
		// 本地调试时，请修改下面的值（I_TMNL_TASK表中的APP_NO）
		String appNo = request.getParameter("appNo");

		if (appNo == null || "".equals(appNo)) {
			message = "appNo 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("appNo 为空或为 null");
			return mapping.findForward("test");
		}

		marketTerminalConfigManager = (MarketTerminalConfigManager) Constant
				.getCtx().getBean("marketTerminalConfigManager");
		List<?> iImnlTaskList = marketTerminalConfigManager
				.getITmnlTaskByAppNo(appNo);

		Map<String, Object> map = new HashMap<String, Object>();
		XMLSwitchUtil xmlSwitch = new XMLSwitchUtil();
		if (iImnlTaskList != null && iImnlTaskList.size() > 0) {
			Map<?, ?> iImnlTaskMap = (Map<?, ?>) iImnlTaskList.get(0);

			map.put("APP_NO", appNo);
			map.put("TERMINAL_ID", StringUtil.removeNull(iImnlTaskMap
					.get("TERMINAL_ID")));// 终端Id
			map.put("TMNL_TASK_TYPE", StringUtil.removeNull(iImnlTaskMap
					.get("TMNL_TASK_TYPE")));// 终端新装
			map.put("CONS_CHG_TYPE", StringUtil.removeNull(iImnlTaskMap
					.get("CONS_CHG_TYPE")));
			map.put("METER_FLAG", StringUtil.removeNull(iImnlTaskMap
					.get("METER_FLAG")));// 电能表变更
			map.put("CONS_NO", StringUtil.removeNull(iImnlTaskMap
					.get("CONS_NO")));// 户号
			map.put("TG_ID", StringUtil.removeNull(iImnlTaskMap.get("TG_ID")));
			map.put("MP_NO", StringUtil.removeNull(iImnlTaskMap.get("MP_NO")));
			map.put("TYPE_CODE", StringUtil.removeNull(iImnlTaskMap
					.get("TYPE_CODE")));
			map.put("USAGE_TYPE_CODE", StringUtil.removeNull(iImnlTaskMap
					.get("USAGE_TYPE_CODE")));
			map.put("NEW_TERMINAL_ID", StringUtil.removeNull(iImnlTaskMap
					.get("NEW_TERMINAL_ID")));
			map.put("ELEC_ADDR", StringUtil.removeNull(iImnlTaskMap
					.get("ELEC_ADDR")));
			map.put("CONS_ID", StringUtil.removeNull(iImnlTaskMap
					.get("CONS_ID")));
			map.put("CP_NO", StringUtil.removeNull(iImnlTaskMap.get("CP_NO")));
			map.put("WKST_APP_NO", StringUtil.removeNull(iImnlTaskMap
					.get("WKST_APP_NO")));

		}
		// String xxx = xmlSwitch.switchMapToXMLClient(map,"");

		// System.out.println("【模拟调试开始】" + xxx );
		// 通过WS调用
		IEIGWSForMarketing iEIGWSForMarketing = (IEIGWSForMarketing) Constant
				.getCtx().getBean("iEIGWSForMarketing");
		// iEIGWSForMarketing.WS_TMNL_TASK_SR(xxx);

		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	// 反馈营销
	public ActionForward backfeed(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long debugId = Long.valueOf(1181);
		String appNo = "";
		Constant.sendEvent(new BackFeedEvent(appNo, debugId));

		return mapping.findForward("test");
	}

	// 加载分布式缓存
	public ActionForward coherenceStatement(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String terminalId = request.getParameter("tmnlId");
		String message = null;

		if (terminalId == null || "".equals(terminalId)) {
			message = "tmnlId 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("tmnlId 为空或为 null");
			return mapping.findForward("test");
		}
		System.out.println(terminalId);

		marketTerminalConfigManager = (MarketTerminalConfigManager) Constant
				.getCtx().getBean("marketTerminalConfigManager");
		IStatement statement = CoherenceStatement.getSharedInstance();
		RTmnlRun rTmnlRun = new RTmnlRun();
		List<?> listTmnl = marketTerminalConfigManager
				.getTerminalById(terminalId);
		if (listTmnl != null && listTmnl.size() > 0) {
			Map<?, ?> mapTmnl = (Map<?, ?>) listTmnl.get(0);
			rTmnlRun.setTerminalId(terminalId);
			rTmnlRun.setCpNo(StringUtil.removeNull(mapTmnl.get("CP_NO")));
			rTmnlRun.setTmnlAssetNo(StringUtil.removeNull(mapTmnl
					.get("TMNL_ASSET_NO")));
			rTmnlRun.setTerminalAddr(StringUtil.removeNull(mapTmnl
					.get("TERMINAL_ADDR")));
			rTmnlRun.setCisAssetNo(StringUtil.removeNull(mapTmnl
					.get("CIS_ASSET_NO")));
			rTmnlRun.setSimNo(StringUtil.removeNull(mapTmnl.get("SIM_NO")));
			rTmnlRun.setId(StringUtil.removeNull(mapTmnl.get("ID")));
			rTmnlRun.setFactoryCode(StringUtil.removeNull(mapTmnl
					.get("FACTORY_CODE")));
			rTmnlRun.setAttachMeterFlag(StringUtil.removeNull(mapTmnl
					.get("ATTACH_METER_FLAG")));
			rTmnlRun.setTerminalTypeCode(StringUtil.removeNull(mapTmnl
					.get("TERMINAL_TYPE_CODE")));
			rTmnlRun.setCollMode(StringUtil
					.removeNull(mapTmnl.get("COLL_MODE")));
			rTmnlRun.setProtocolCode(StringUtil.removeNull(mapTmnl
					.get("PROTOCOL_CODE")));
			rTmnlRun.setCommPassword(StringUtil.removeNull(mapTmnl
					.get("COMM_PASSWORD")));
			rTmnlRun.setRunDate((Date) mapTmnl.get("RUN_DATE"));
			rTmnlRun.setStatusCode(StringUtil.removeNull(mapTmnl
					.get("STATUS_CODE")));
			rTmnlRun.setHarmonicDevFlag(StringUtil.removeNull(mapTmnl
					.get("HARMONIC_DEV_FLAG")));
			rTmnlRun.setPsEnsureFlag(StringUtil.removeNull(mapTmnl
					.get("PS_ENSURE_FLAG")));
			rTmnlRun.setAcSamplingFlag(StringUtil.removeNull(mapTmnl
					.get("AC_SAMPLING_FLAG")));
			rTmnlRun.setEliminateFlag(StringUtil.removeNull(mapTmnl
					.get("ELIMINATE_FLAG")));
			rTmnlRun.setGateAttrFlag(StringUtil.removeNull(mapTmnl
					.get("GATE_ATTR_FLAG")));
			rTmnlRun.setPrioPsMode(StringUtil.removeNull(mapTmnl
					.get("PRIO_PS_MODE")));
			rTmnlRun.setFreezeMode(StringUtil.removeNull(mapTmnl
					.get("FREEZE_MODE")));
			rTmnlRun.setFreezeCycleNum(Integer.parseInt(StringUtil
					.removeNull(mapTmnl.get("FREEZE_CYCLE_NUM"))));
			rTmnlRun.setMaxLoadCurveDays(Integer.parseInt(StringUtil
					.removeNull(mapTmnl.get("MAX_LOAD_CURVE_DAYS"))));
			rTmnlRun.setPsLineLen(Integer.parseInt(StringUtil
					.removeNull(mapTmnl.get("PS_LINE_LEN"))));
			rTmnlRun.setWorkPs(StringUtil.removeNull(mapTmnl.get("WORK_PS")));
			rTmnlRun.setSpeakerFlag(StringUtil.removeNull(mapTmnl
					.get("SPEAKER_FLAG")));
			rTmnlRun.setSpeakerDist(Integer.parseInt(String.valueOf(mapTmnl
					.get("SPEAKER_DIST"))));
			if ("1".equals(mapTmnl.get("SEND_UP_MODE"))) {
				rTmnlRun.setSendUpMode(true);
			} else {
				rTmnlRun.setSendUpMode(false);
			}
			rTmnlRun.setCommMode(StringUtil
					.removeNull(mapTmnl.get("COMM_MODE")));
			rTmnlRun.setFrameNumber(Short.valueOf(StringUtil.removeNull(mapTmnl
					.get("FRAME_NUMBER"))));
			rTmnlRun.setPowerCutDate((Date) mapTmnl.get("POWER_CUT_DATE"));
		}
		try {
			// 调用分布式缓存
			statement.executeUpdate(RTmnlRun.class, rTmnlRun.getTerminalId(),
					rTmnlRun);
			System.out.println(terminalId + "加载");
		} catch (Exception e) {
			e.printStackTrace();
		}

		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	public static TaskHandle getTaskHandle() {
		return TaskHandle.getSharedInstance("");
	}

	// 判断是否在线
	public ActionForward isOnline(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String message = null;
		// String terminalId = "63007600";
		String terminalId = request.getParameter("tmnlId");

		if (terminalId == null || "".equals(terminalId)) {
			message = "tmnlId 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("tmnlId 为空或为 null");
			return mapping.findForward("test");
		}

		System.out.println(terminalId);
		ATmnlRealTime tmnlStatus = getTaskHandle().getTmnlStatus(terminalId);
		if (null == tmnlStatus || !tmnlStatus.getIsOnline()) {
			System.out.println(terminalId + "===========不在线");
		} else {
			System.out.println(terminalId + "=============在线");
		}

		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	// 销户
	public ActionForward removeUser(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String consNo = request.getParameter("consNo");
		System.out.println(consNo + "=============销户开始==================");

		String message = null;

		if (consNo == null || "".equals(consNo)) {
			message = "consNo 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("consNo 为空或为 null");
			return mapping.findForward("test");
		}

		List<?> listTmnl = marketTerminalConfigManager.getTmnlByConsNo(consNo);
		if (listTmnl != null && listTmnl.size() > 0) {
			for (int j = 0; j < listTmnl.size(); j++) {
				Map<?, ?> mapTmnl = (Map<?, ?>) listTmnl.get(j);

				String tmnlTypeCode = StringUtil.removeNull(mapTmnl
						.get("TERMINAL_TYPE_CODE"));
				String tmnlId = StringUtil.removeNull(mapTmnl
						.get("TERMINAL_ID"));
				String tmnlAssetNo = StringUtil.removeNull(mapTmnl
						.get("TMNL_ASSET_NO"));
				String cpNo = StringUtil.removeNull(mapTmnl.get("CP_NO"));
				if ("05".equals(tmnlTypeCode)) {
					// 集抄
					marketTerminalConfigManager.cancelUser(consNo);
					// 删除群组明细
					marketTerminalConfigManager.deleteGroupDetail(consNo);
					// 删除用户终端关系表
					marketTerminalConfigManager.deleteRconsTmnlRela(consNo,
							tmnlAssetNo);
					// 删除集中器相关信息
					marketTerminalConfigManager.cancelTmnl(tmnlId, tmnlAssetNo,
							"", "", cpNo);
				} else {
					// 负控
					marketTerminalConfigManager.cancelUser(consNo);
					// 删除群组明细
					marketTerminalConfigManager.deleteGroupDetail(consNo);
					// 删除用户终端关系表
					marketTerminalConfigManager.deleteRconsTmnlRela(consNo,
							tmnlAssetNo);
					// 删除终端相关信息
					marketTerminalConfigManager.cancelTmnl(tmnlId, tmnlAssetNo,
							"", "", cpNo);
				}
			}
		} else {
			// 集抄
			marketTerminalConfigManager.cancelUser(consNo);
			// 删除群组明细
			marketTerminalConfigManager.deleteGroupDetail(consNo);
		}

		System.out.println(consNo + "=============销户结束==================");

		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	// 拆终端
	public ActionForward removeTmnl(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tmnlId = request.getParameter("tmnlId");

		String message = null;
		// String terminalId = "63007600";

		if (tmnlId == null || "".equals(tmnlId)) {
			message = "tmnlId 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("tmnlId 为空或为 null");
			return mapping.findForward("test");
		}

		System.out.println(tmnlId + "=============拆终端开始==================");
		RTmnlRun rTmnlRun = marketTerminalConfigManager
				.getTmnlByAssetNo(tmnlId);
		if (rTmnlRun != null) {
			String cpNo = rTmnlRun.getCpNo();
			String tmnlAssetNo = rTmnlRun.getTmnlAssetNo();
			marketTerminalConfigManager.cancelTmnl(tmnlId, tmnlAssetNo, "", "",
					cpNo);
		}
		System.out.println(tmnlId + "=============拆终端结束==================");
		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	// 删除分布式缓存中的运行终端信息
	public ActionForward removeCoherence(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String tmnlId = request.getParameter("tmnlId");

		String message = null;
		// String terminalId = "63007600";

		if (tmnlId == null || "".equals(tmnlId)) {
			message = "tmnlId 为空或为 null";
			request.setAttribute("message", message);
			System.out.println("tmnlId 为空或为 null");
			return mapping.findForward("test");
		}

		System.out.println(tmnlId
				+ "=============删除分布式缓存中的运行终端信息开始==================");
		// 删除分布式缓存中的运行终端信息
		try {
			IStatement statement = CoherenceStatement.getSharedInstance();
			statement.removeValueByKey(RTmnlRun.class, tmnlId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tmnlId
				+ "=============删除分布式缓存中的运行终端信息结束==================");
		message = " 处理完毕 ！";
		request.setAttribute("message", message);
		return mapping.findForward("test");
	}

	/**
	 * 定时任务
	 */
	public ActionForward task(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuartzJob quartzJob = new QuartzJob();
		System.out.println("===========定时任务开始=============");
		quartzJob.working();
		System.out.println("===========定时任务结束=============");
		return mapping.findForward("test");

	}
}