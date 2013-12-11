package com.nari.qrystat.colldataanalyse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.nari.action.baseaction.BaseAction;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.securityman.IGeneralTreeManager;
import com.nari.util.TreeNode;
import com.nari.util.exception.ServiceException;
import com.opensymphony.xwork2.ActionContext;

/**
 * 台区线损管理
 * 
 * @author huangxuan
 * 
 */
public class LosePowerManAction extends BaseAction {
	private Logger logger = Logger.getLogger(this.getClass());
	private LosePowerManManager losePowerManManager;
	private String message;
	private List list;
	// 接受前台的正向有功，反向有功
	private List<String> pqFlags;
	// 接受前台考核单元的信息
	private List<String> nodes;
	// 一个参考指标的的接收实体
	private GChkunitRefIdx gr;
	// 接受是否有效
	private Integer isVaild;
	// 接受integer id的列表
	private List<Integer> ids;
	// 接受台区的id
	private List<String> objIds;
	boolean hasPower = false;
	// 接受前台传过来的e_data_mp中的id列表
	private List<String> dataIds;
	// 接受一个整型列表保存计量点列表
	private List<String> mpIds;
	// 接受一个整型列表保存表计列表
	private List<String> meterIds;
	// 是进还是出
	private Integer isIn;
	// 接受终端地址
	private String tmnlAddr;
	// 是不是保存所有的计量点
	private boolean allSave;
	// 记录单独的数据
	private Map map;
	// 用来接受一个表计和电能能对应关系的查找者
	private MeterMpFinder mmFinder;
	private TgFinder tgFinder;
	// 用来接受考核单元的id
	private Integer gid;
	// 接受从前台传过来的整型列表
	private List<Integer> ints;
	// 当前的用户编号
	private PSysUser staff;
	// 树数据
	public Page<TreeNode> pageInfo;
	// 用于接受前台传来的考核周期对象
	private Gchkunit gk;
	// 用于接受前台传过来的台区id列表
	private List<Integer> tgIds;
	// 用来接受前台传过来的考核单元的周期列表
	private List<String> cycles;
	private boolean success = true;
	private List<Map> resultList;
	private Long resultCount;
	private GChkUnitFinder unitFinder = new GChkUnitFinder();
	// spring 注入对象
	public IGeneralTreeManager iGeneralTreeManager;

	/**
		 * 
		 */
	private String node;
	private long start;
	private int limit;
	private LeftTreeLineFinder lineFinder;
	private String typeCode;
	private String orgNo;
	private Date statDate;
	private String url;

	/**
	 * 通过左边树来加载数据
	 * 
	 * @return
	 */
	public String leftTreeClick() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			resultList = losePowerManManager.findLeftTreeClick(user, node);
			// System.out.println(losePowerManManager);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/** 查找考核单元 ***/
	@SuppressWarnings("unchecked")
	public String findChkUnit() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findChkUnit(user, start,
					limit, unitFinder);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.debug(e.getMessage());
		}
		return SUCCESS;
	}

	public GChkUnitFinder getUnitFinder() {
		return unitFinder;
	}

	/*** 分页版本的根据左边树来查询相对应的台区 ***/
	public String findPageTg() {
		try {
			Map session = ActionContext.getContext().getSession();
			// 得到操作人员
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findPageLeftClick(user, start,
					limit, node, tgFinder);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.debug("台区线损管理:" + e.getMessage());
		}
		return SUCCESS;
	}

	/*** 查找当前的用户 ***/
	public String findCurrentStaff() {
		Map session = ActionContext.getContext().getSession();
		// 得到操作人员
		PSysUser user = (PSysUser) session.get("pSysUser");
		staff = user;
		return SUCCESS;
	}

	/**** 电网树查询 *****/
	public String netTreeClick() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			// 既不是线路又不是区域时直接返回
			// !node.startsWith("line")&&
			if (null != node
					&& ((!node.startsWith("org") && !node
							.startsWith("area_root")) || node.endsWith("06"))) {
				return SUCCESS;
			}
			// 得到操作人员
			pageInfo = iGeneralTreeManager.findAreaTree(user, node, 0, 100);

		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 注册一个新的考核单元
	 * **/
	public String register() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			gk.setRespEmpNo(user.getStaffNo());
			losePowerManManager.saveGChkunit(gk, cycles, tgIds);
		} catch (Exception e) {
			this.message = e.getMessage();
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 查找一个考核单元下所有的台区
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String findTgs() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findTgsInGChkunit(user, gid,
					start, limit);
			resultCount = page.getTotalCount();
			resultList = page.getResult();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 删除某个考核单元下的所有的选择的台区 **/
	@SuppressWarnings("unchecked")
	public String deleteGchkunit() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			losePowerManManager.deleteGChkunitComp(gid, tgIds);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 通过id来查找一个考核单元的详情
	 * **/
	public String findDetailChkunit() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			map = losePowerManManager.findGchkunitById(user, gid);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;

	}

	/**
	 * 修改一个考核单元的内容
	 * **/
	public String updateChkunit() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			gk.setRespEmpNo(user.getStaffNo());
			gk.setChkCycle(convertBs(cycles));
			// gk.setChkunitTypeCode("02");
			losePowerManManager.updateGChkunit(gk);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	// 将日月季半年年转化为bs时间
	private String convertBs(Collection<String> cs) {
		StringBuilder sb = new StringBuilder("00000");
		for (String str : cs) {
			if (str.equals("日")) {
				sb.replace(0, 1, "1");
			} else if (str.equals("月")) {
				sb.replace(1, 2, "1");
			} else if (str.equals("季")) {
				sb.replace(2, 3, "1");
			} else if (str.equals("半年")) {
				sb.replace(3, 4, "1");
			} else if (str.equals("年")) {
				sb.replace(4, 5, "1");
			}
		}
		return sb.toString();
	}

	/*** 向一个考核单元里面添加台区 **/
	public String addTgs() {
		try {
			losePowerManManager.addTgs(gid, tgIds);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 查找表计和电能表的对应关系 ***/
	@SuppressWarnings("unchecked")
	public String findMeterMp() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findMeterMp(gid, typeCode,
					mmFinder, start, limit);
			resultCount = page.getTotalCount();
			resultList = page.getResult();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 查找一个考核单元下面的所有的台区 ***/
	@SuppressWarnings("unchecked")
	public String findPageTgsInGchkunit() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findPageTgsInGchkunit(gid,
					start, limit);
			resultCount = page.getTotalCount();
			resultList = page.getResult();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**** 删除没有考核单元和他所有相关联的表 ****/
	public String deleteGChkunitRef() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			losePowerManManager.deleteGChkunit(user, ints);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;

	}

	/*** merge定义入口计量点 ***/

	public String mergeInMeter() {
		try {
			if (allSave) {
				initMpMeter();
			}
			losePowerManManager.mergeGiomp(gid, 1, isVaild, mpIds, meterIds,
					dataIds, pqFlags, objIds);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/*** merge定义出口计量点 ***/
	public String mergeOutMeter() {
		try {
			if (allSave) {
				initMpMeter();
			}
			losePowerManManager.mergeGiomp(gid, 0, isVaild, mpIds, meterIds,
					dataIds, pqFlags, objIds);
		} catch (Exception e) {
			e.printStackTrace();
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	// 通过finder初始化相应的表计和计量点
	private void initMpMeter() throws ServiceException {
		// losePowerManManager.findMeterMp(gid, mmFinder, start, limit)
		mpIds = new ArrayList<String>();
		meterIds = new ArrayList<String>();
		dataIds = new ArrayList<String>();
		objIds = new ArrayList<String>();
		pqFlags = new ArrayList<String>();
		String name = null;
		if (typeCode.equals("01")) {
			name = "lineId";
		} else if (typeCode.equals("02")) {
			name = "tgId";
		}
		List<Map> list = losePowerManManager.findMeterMp(gid, typeCode,
				mmFinder);
		for (Map map : list) {
			Object mp = map.get("mpId");
			Object dataId = map.get("dataId");
			Object meterId = map.get("meterId");
			Object objId = map.get(name);
			Object pqFlag = map.get("pqFlag");
			if (null == mp || null == meterId || null == dataId
					|| null == objId) {
				continue;
			}
			objIds.add(objId.toString());
			mpIds.add(mp.toString());
			dataIds.add(dataId.toString());
			meterIds.add(meterId.toString());
			pqFlags.add(pqFlag == null ? "-1" : pqFlag.toString());
		}
	}

	public String findFrmAsset() {
		try {
			resultList = losePowerManManager.findFrm(tmnlAddr);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 找到一个考核单元下面的所有的终端 ****/
	public String findTmnlAddr() {
		try {
			resultList = losePowerManManager.findTmnlAddr(gid, typeCode);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 找到一个考核单元对应的参考指标 ***/
	public String findRefByGid() {
		try {
			map = losePowerManManager.findRefByChkunit(gid);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**** 删除一个考核单元参考指标 ***/
	public String delRefByGid() {
		try {
			losePowerManManager.deleteRef(gid);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**** 更新或者新建一个参考指标 ***/
	public String saveOrUpdateRefByGid() {
		try {
			losePowerManManager.saveOrUpdateRef(gr);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**** 对添加的台区做验证 *****/
	public String checkNewTgs() {
		try {
			Page<Map> page = losePowerManManager.checkExistsTgs(tgIds, start,
					limit);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	// ***修改后的参考指标
	// 修改召测结果的列表
	public String updateRefs() {
		logger.debug("修改参考指标的列表开始");
		try {
			losePowerManManager.updateRefs(parseRefData());
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		logger.debug("修改参考指标的列表结束");
		return SUCCESS;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}

	/*** 找到一个考核单元下面的所有参考指标 ***/
	public String findRefs() {
		logger.debug("找到一个考核单元下面的所有参考指标开始");
		try {
			list = losePowerManManager.findRefsByChkunitId(gid);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		logger.debug("找到一个考核单元下面的所有参考指标结束");
		return SUCCESS;
	}

	/*** 删除所有的在列表的id的参考指标 **/
	/*** 找到一个考核单元下面的所有参考指标 ***/
	public String deleteRefs() {
		logger.debug("修改参考指标的列表开始");
		try {
			losePowerManManager.deleteRefByIdxIds(ids);
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		logger.debug("修改参考指标的列表结束");
		return SUCCESS;
	}

	// 对前台的数据进行处理，转化成参考指标的列表
	private List<GChkunitRefIdx> parseRefData() {
		List<GChkunitRefIdx> list = new ArrayList<GChkunitRefIdx>();
		for (String str : nodes) {
			GChkunitRefIdx gf = new GChkunitRefIdx();
			String[] strs = str.split("~");
			if (strs.length != 6) {
				throw new RuntimeException(str);
			}
			gf.setMsg(strs[0]);
			gf.setLlIdxId(Integer.valueOf(strs[1].equals("") ? "0" : strs[1]));
			gf
					.setChkunitId(Integer.valueOf(strs[2].equals("") ? "0"
							: strs[2]));
			if (!strs[0].equals("del") && strs[3].trim().equals("")) {
				throw new RuntimeException("参考指标名称不能为空");
			}
			gf.setLlIdxName(strs[3]);
			if (!strs[0].equals("del") && strs[4].trim().equals("")) {
				throw new RuntimeException("参考指标值不能为空");
			}
			gf.setLlIdxValue(Double.valueOf(strs[4]));
			if (strs[5].trim().equals("")) {
				throw new RuntimeException("参考指标周期不能为空");
			}
			gf.setChkCycle(strs[5]);
			list.add(gf);
		}
		return list;
	}

	/** 通过左边树来查询线路 **/
	public String findLineByLeft() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findLeftByLine(user, node,
					start, limit, lineFinder);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 查找考核单元已经存在的线路 **/
	public String findExistLine() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findExistLine(user, gid,
					start, limit);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/** 查找已经存在的线路 **/
	public String checkExistLine() {
		try {
			// 历史问题tgIds表示线路很多台区的id，指g_chkunit_comp里面的obj_id
			Page<Map> page = losePowerManManager.findCheckLine(tgIds, start,
					limit);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/***** 查找台区线损的分压等级 ******/
	public String findVoltType() {
		try {
			// 历史问题tgIds表示线路很多台区的id，指g_chkunit_comp里面的obj_id
			resultList = losePowerManManager.findVoltType();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	/**** 统计的选定天的电量 ****/
	public String queryDayPqStat() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Calendar c = Calendar.getInstance();
			if (null == statDate) {
				statDate = new Date();
				c.setTime(statDate);
				c.add(Calendar.DATE, -1);
			}else{
				c.setTime(statDate);
			}
			Page<Map> page = losePowerManManager.findDayPqStat(user, c
					.getTime(), start, limit);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;

	}

	/**** 按类别来统计电量 ****/
	public String queryDayPqStatByType() {
		try {
			Map session = ActionContext.getContext().getSession();
			PSysUser user = (PSysUser) session.get("pSysUser");
			Page<Map> page = losePowerManManager.findDayPqStatByType(user,
					orgNo, typeCode, statDate, start, limit);
			resultList = page.getResult();
			resultCount = page.getTotalCount();
		} catch (Exception e) {
			this.message = e.getMessage();
			logger.error(e.getMessage());
		}
		return SUCCESS;
	}

	public String findHasPower() {
		try {
			hasPower = losePowerManManager.findMenuPower(getPSysUser(), url);
		} catch (Exception e) {
			this.message = e.getMessage();
		}
		return SUCCESS;
	}

	

	public void setStart(long start) {
		this.start = start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getMessage() {
		return message;
	}

	public List<Map> getResultList() {
		return resultList;
	}

	public Long getResultCount() {
		return resultCount;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public void setLosePowerManManager(LosePowerManManager losePowerManManager) {
		this.losePowerManManager = losePowerManManager;
	}

	public void setUnitFinder(GChkUnitFinder unitFinder) {
		this.unitFinder = unitFinder;
	}

	public void setStaff(PSysUser staff) {
		this.staff = staff;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setiGeneralTreeManager(IGeneralTreeManager iGeneralTreeManager) {
		this.iGeneralTreeManager = iGeneralTreeManager;
	}

	public TgFinder getTgFinder() {
		return tgFinder;
	}

	public void setTgFinder(TgFinder tgFinder) {
		this.tgFinder = tgFinder;
	}

	public Page<TreeNode> getPageInfo() {
		return pageInfo;
	}

	public Gchkunit getGk() {
		return gk;
	}

	public void setGk(Gchkunit gk) {
		this.gk = gk;
	}

	public void setTgIds(List<Integer> tgIds) {
		this.tgIds = tgIds;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

	public void setCycles(List<String> cycles) {
		this.cycles = cycles;
	}

	public PSysUser getStaff() {
		return staff;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public MeterMpFinder getMmFinder() {
		return mmFinder;
	}

	public void setMmFinder(MeterMpFinder mmFinder) {
		this.mmFinder = mmFinder;
	}

	public void setTmnlAddr(String tmnlAddr) {
		this.tmnlAddr = tmnlAddr;
	}

	public void setInts(List<Integer> ints) {
		this.ints = ints;
	}

	public void setMpIds(List<String> mpIds) {
		this.mpIds = mpIds;
	}

	public void setMeterIds(List<String> meterIds) {
		this.meterIds = meterIds;
	}

	public void setIsIn(Integer isIn) {
		this.isIn = isIn;
	}

	public void setAllSave(boolean allSave) {
		this.allSave = allSave;
	}

	public void setDataIds(List<String> dataIds) {
		this.dataIds = dataIds;
	}

	public void setObjIds(List<String> objIds) {
		this.objIds = objIds;
	}

	public void setIsVaild(Integer isVaild) {
		this.isVaild = isVaild;
	}

	public void setGr(GChkunitRefIdx gr) {
		this.gr = gr;
	}

	public GChkunitRefIdx getGr() {
		return gr;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List getList() {
		return list;
	}

	public void setPqFlags(List<String> pqFlags) {
		this.pqFlags = pqFlags;
	}

	public void setLineFinder(LeftTreeLineFinder lineFinder) {
		this.lineFinder = lineFinder;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public LeftTreeLineFinder getLineFinder() {
		return lineFinder;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

}
