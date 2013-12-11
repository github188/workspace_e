package com.nari.sysman.securityman.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.basicdata.BTrade;
import com.nari.customer.CConsRtmnl;
import com.nari.grid.GLine;
import com.nari.grid.GSubs;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.sysman.alertevent.CpTypeCode;
import com.nari.sysman.securityman.IGeneralTreeManager;
import com.nari.sysman.securityman.IGenralTreeDao;
import com.nari.sysman.securityman.UserGridBean;
import com.nari.sysman.templateman.VwProtocolCode;
import com.nari.terminalparam.RUserGroup;
import com.nari.terminalparam.RUserGroupDetailId;
import com.nari.terminalparam.TTmnlGroup;
import com.nari.terminalparam.TTmnlGroupDetailId;
import com.nari.util.TreeNode;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

/**
 * 类 GeneralTreeManagerImpl
 * 
 * @author zhangzhw zhongweizhang@163.com
 * @describe 电网 行业 用户 树构造
 */
public class GeneralTreeManagerImpl implements IGeneralTreeManager {

	public IGenralTreeDao iGenralTreeDao;

	/**
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 子节点
	 * @throws Exception
	 * @describe 查询行业树中某节点的子节点
	 */
	@Override
	public Page<TreeNode> findTradeTree(PSysUser username, String node,
			long start, int limit) throws Exception {
		try {
			Page<TreeNode> pageInfo = null;

			if (node.equals("trade_root")) {
				pageInfo = convertPageFromTrade(iGenralTreeDao
						.findTradeByUpTrade("", start, limit));
			} else {
				// 拆分 节点 ID
				String[] nodeArray = node.split("_");

				String nodeType = nodeArray[0];
				String nodeId = nodeArray[1];

				if (nodeType.equals("trade")) {// 类型为单位
					// pageInfo = convertPageFromTrade(iGenralTreeDao
					// .findTradeByUpTrade(nodeId, start, limit));
					pageInfo = generateTradePage(username, nodeId, start, limit);

				}
			}
			return pageInfo;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_TRADETREEMANAGER);
		}
	}

	/**
	 * 
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 该节点的子节点分页信息
	 * @throws Exception
	 */
	@Override
	public Page<TreeNode> findGridTree(PSysUser username, String node,
			long start, int limit) throws Exception {
		try {
			Page<TreeNode> pageInfo = null;

			if (node.equals("net_root")) {
				// 单位
				pageInfo = convertPageFromOrg(iGenralTreeDao.findOrgByUsername(
						username, start, limit));
			} else {
				// 拆分 节点 ID
				String[] nodeArray = node.split("_");

				String nodeType = nodeArray[0];
				String nodeId = nodeArray[1];
				String unitType = "00";
				if (nodeArray.length > 2)
					unitType = nodeArray[2];

				// 根据单位节点类别分别查询下级节点
				if (nodeType.equals("org")) {
					if (Integer.valueOf(unitType) < 3) {
						pageInfo = generateAreaPageReverse(username, unitType, nodeId,
										start, limit);
					} else if (Integer.valueOf(unitType) == 3) {
						// 组合地市级供电局下属 （存在县级供电局和市级变电站）
						pageInfo = generateAreaPageReverse(username, unitType, nodeId,
								start, limit);
					} else {

						pageInfo = convertPageFromSub(iGenralTreeDao
								.findSubByOrg(nodeId, start, limit));
					}
				} else if (nodeType.equals("sub")) {
					pageInfo = convertPageFromLine(iGenralTreeDao
							.findLineBySub(nodeId, start, limit));

				} else if (nodeType.equals("line")) {
					pageInfo = convertPageFromCustomer(iGenralTreeDao
							.findCustmerByLine(nodeId, start, limit, username));
				}

			}
			return pageInfo;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}

	/**
	 * 方法 generateTradePage
	 * 
	 * @param username
	 * @param nodeId
	 * @param start
	 * @param limit
	 * @return 对行业的下级行业节点和用户节点进行组合
	 * @throws Exception
	 */
	public Page<TreeNode> generateTradePage(PSysUser username, String nodeId,
			long start, int limit) throws Exception {
		try {
			// pageInfo = convertPageFromTrade(iGenralTreeDao
			// .findTradeByUpTrade(nodeId, start, limit));
			Page<TreeNode> pt = null;
			Page<BTrade> pb = iGenralTreeDao.findTradeByUpTrade(nodeId, start,
					limit);
			Page<CConsRtmnl> pc = null;

			if (start + limit <= pb.getTotalCount()) {
				pc = iGenralTreeDao.findCustmerByTrade(nodeId, 0, 2, username); // 取巧取得总数
				pb.setTotalCount(pc.getTotalCount() + pc.getTotalCount());
				pt = convertPageFromTrade(pb);
			} else if (start > pb.getTotalCount()) {
				pc = iGenralTreeDao.findCustmerByTrade(nodeId, (start - pb
						.getTotalCount()), limit, username);
				pt = convertPageFromCustomer(pc);
			} else {
				pt = new Page<TreeNode>();
				List<TreeNode> list = new ArrayList<TreeNode>();
				list.addAll(convertPageFromTrade(pb).getResult());
				pc = iGenralTreeDao.findCustmerByTrade(nodeId, 0, (int) (start
						+ limit - pb.getTotalCount()), username);
				list.addAll(convertPageFromCustomer(pc).getResult());

				pt.setResult(list);
				pt.setTotalCount(pb.getTotalCount() + pc.getTotalCount());

			}

			return pt;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}

	/**
	 * 方法　generateAreaPage
	 * 
	 * @param username
	 * @param nodeId
	 * @param start
	 * @param limit
	 * @return　　针对市级单位下存在变电站的情况，进行　县级单位和　变电站组合　
	 * @throws Exception
	 */
	public Page<TreeNode> generateAreaPage(PSysUser username, String unitType,
			String nodeId, long start, int limit) throws Exception {
		try {
			Page<TreeNode> pt = null;
			Page<OOrg> po = iGenralTreeDao.findOrgByUpNode(username, unitType,
					nodeId, start, limit);
			Page<GSubs> pg = null;

			if (start + limit <= po.getTotalCount()) {//针对供电单位超过50条的情况
				pg = iGenralTreeDao.findSubByOrg(nodeId, 0, 2); // 取巧取得总数
				po.setTotalCount(po.getTotalCount() + pg.getTotalCount());
				pt = convertPageFromOrg(po);
			} else if (start > po.getTotalCount()) {
				pg = iGenralTreeDao.findSubByOrg(nodeId, (int) (start - po
						.getTotalCount()), limit);
				pt = convertPageFromSub(pg);
			} else {
				pt = new Page<TreeNode>();
				List<TreeNode> list = new ArrayList<TreeNode>();
				list.addAll(convertPageFromOrg(po).getResult());
				pg = iGenralTreeDao.findSubByOrg(nodeId, 0, start + limit
						- (int) po.getTotalCount());
				list.addAll(convertPageFromSub(pg).getResult());

				pt.setResult(list);
				pt.setTotalCount(po.getTotalCount() + pg.getTotalCount());

			}

			return pt;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}
	
	/**
	 * 方法　generateAreaPage，应青海现场以前，把变电所放在供电单位前面
	 * @author jiangyike modify on 2010-8-20
	 * @param username
	 * @param nodeId
	 * @param start
	 * @param limit
	 * @return　　针对市级单位下存在变电站的情况，进行　县级单位和　变电站组合　
	 * @throws Exception
	 */
	public Page<TreeNode> generateAreaPageReverse(PSysUser username, String unitType,
			String nodeId, long start, int limit) throws Exception {
		try {
			Page<TreeNode> pt = null;
			Page<GSubs> pg = iGenralTreeDao.findSubByOrg(nodeId, start, limit);;
			Page<OOrg> po = null;

			if (start + limit <= pg.getTotalCount()) {//针对变电所超过50条的情况
				po = iGenralTreeDao.findOrgByUpNode(username, unitType,
						nodeId, 0, 2); // 取巧取得总数
				pg.setTotalCount(pg.getTotalCount() + po.getTotalCount());
				pt = convertPageFromSub(pg);
			} else if (start > pg.getTotalCount()) {
				po = iGenralTreeDao.findOrgByUpNode(username, unitType,
						nodeId, (int) (start - pg.getTotalCount()), limit);
				pt = convertPageFromOrg(po);
			} else {
				pt = new Page<TreeNode>();
				List<TreeNode> list = new ArrayList<TreeNode>();
				list.addAll(convertPageFromSub(pg).getResult());
				po = iGenralTreeDao.findOrgByUpNode(username, unitType,
						nodeId, 0, (int) (start + limit - pg.getTotalCount()));
				list.addAll(convertPageFromOrg(po).getResult());

				pt.setResult(list);
				pt.setTotalCount(pg.getTotalCount() + po.getTotalCount());
			}

			return pt;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_GRIDTREEMANAGER);
		}
	}

	/**
	 * 方法 findAreaTree
	 * 
	 * @param username
	 * @param node
	 * @param start
	 * @param limit
	 * @return 该节点的子节点分页信息
	 * @throws Exception
	 */
	@Override
	public Page<TreeNode> findAreaTree(PSysUser username, String node,
			long start, int limit) throws Exception {
		try {
			Page<TreeNode> pageInfo = null;

			if (node.equals("area_root")) {
				// 单位
				pageInfo = convertPageFromOrg(iGenralTreeDao.findOrgByUsername(
						username, start, limit));
			
			} else {
				// 拆分 节点 ID
				String[] nodeArray = node.split("_");

				String nodeType = nodeArray[0];
				String nodeId = nodeArray[1];
				String unitType = "00";
				if (nodeArray.length > 2)
					unitType = nodeArray[2];

				// 根据单位节点类别分别查询下级节点
				if (nodeType.equals("org")) {
					if (Integer.valueOf(unitType) < 4) {
						pageInfo = convertPageFromOrg(iGenralTreeDao
								.findOrgByUpNode(username, unitType, nodeId,
										start, limit));
					} else if (Integer.valueOf(unitType) == 4) {
						pageInfo = generateAreaPage1(username, unitType,
								nodeId, start, limit);
					} else {
						pageInfo = convertPageFromCustomer(iGenralTreeDao
								.findCustmerByOrg(nodeId, start, limit,
										username));
					}
				}

			}
			return pageInfo;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_AREATREEMANAGER);
		}
	}

	/**
	 * 
	 * @param username
	 * @param unitType
	 * @param nodeId
	 * @param start
	 * @param limit
	 * @return 对于区县级单位下没有供电所的情况，区县下直接挂用户
	 * @throws Exception
	 */
	public Page<TreeNode> generateAreaPage1(PSysUser username, String unitType,
			String nodeId, long start, int limit) throws Exception {
		try {
			Page<TreeNode> pt = null;
			Page<OOrg> po = iGenralTreeDao.findOrgByUpNode(username, unitType,
					nodeId, start, limit);
			Page<CConsRtmnl> pc = null;

			if (start + limit <= po.getTotalCount()) {
				pc = iGenralTreeDao.findCustmerByOrg(nodeId, 0, 2, username); // 取巧取得总数
				po.setTotalCount(po.getTotalCount() + pc.getTotalCount());
				pt = convertPageFromOrg(po);
			} else if (start > po.getTotalCount()) {
				
				long poCount = 0; 
				if(po != null){
					poCount = po.getTotalCount();
				}
				pc = iGenralTreeDao.findCustmerByOrg(nodeId, (int) (start - poCount), limit, username);
				pt = convertPageFromCustomer(pc);
			} else {
				pt = new Page<TreeNode>();
				List<TreeNode> list = new ArrayList<TreeNode>();
				list.addAll(convertPageFromOrg(po).getResult());
				pc=iGenralTreeDao.findCustmerByOrg(nodeId, 0, (int) start + limit
						- (int) po.getTotalCount(), username);
				list.addAll(convertPageFromCustomer(pc).getResult());

				pt.setResult(list);
				
				long poCount = 0; 
				if(po != null){
					poCount = po.getTotalCount();
				}
				pt.setTotalCount(poCount + pc.getTotalCount());

			}

			return pt;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_AREATREEMANAGER);
		}
	}

	/**
	 * 备选用户群组数据
	 */
	@Override
	public Page<TreeNode> findBackTree(PSysUser username, String node,
			long start, int limit) throws Exception {
		try {
			Page<TreeNode> pageInfo = null;

			if (node.equals("backuser_root")) {
				// 单位
				pageInfo = convertPageFromBackUserGroup(iGenralTreeDao
						.findBackUserByName(username.getStaffNo(), start, limit));
			} else {
				// 拆分 节点 ID
				String[] nodeArray = node.split("_");
				// String nodeType = nodeArray[0];
				String nodeId = nodeArray[1];

				// 根据单位节点类别分别查询下级节点

				pageInfo = convertPageFromCustomer(iGenralTreeDao
						.findBackUserDetail(nodeId, start, limit, username));

			}
			return pageInfo;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_BACKUSERGROUPTREEMANAGER);
		}
	}

	/**
	 * 控制群组数据
	 */
	@Override
	public Page<TreeNode> findControlTree(PSysUser username, String node,
			long start, int limit) throws Exception {
		try {
			Page<TreeNode> pageInfo = null;

			if (node.equals("controluser_root")) {
				// 单位
				pageInfo = convertPageFromControlUserGroup(iGenralTreeDao
						.findControlGroupByName(username.getStaffNo(), start,
								limit));
			} else {
				// 拆分 节点 ID
				String[] nodeArray = node.split("_");

				// String nodeType = nodeArray[0];
				String nodeId = nodeArray[1];

				// 根据单位节点类别分别查询下级节点
				pageInfo = convertPageFromCustomer(iGenralTreeDao
						.findControlGroupDetail(nodeId, start, limit, username));

			}
			return pageInfo;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_CONTROLGROUPTREEMANAGER);
		}

	}

	/**
	 * 方法 findUserGrid
	 * 
	 * @param username
	 * @param start
	 * @param limit
	 * @return 用户列表
	 * @throws Exception
	 */
	@Override
	public Page<CConsRtmnl> findUserGrid(PSysUser pSysUser, long start,
			int limit, UserGridBean ugb) throws Exception {
		try {
			if ("1".equals(ugb.getConsType()))
				return iGenralTreeDao.findCustmerByOrg(ugb, start, limit,
						pSysUser);
			else if ("5".equals(ugb.getConsType()))
				return iGenralTreeDao.findCustmerBycitizen(ugb, start, limit,
						pSysUser);
			else if ("2".equals(ugb.getConsType()))
				return iGenralTreeDao.findCustmerByTg(ugb, start, limit,
						pSysUser);
			else if("7".equals(ugb.getConsType()))//变电站查询
				return iGenralTreeDao.findCustmerBySubs(ugb, start, limit, pSysUser);
			else
				return iGenralTreeDao.findCustmerByOrg(ugb, start, limit,
						pSysUser);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_CONSRTMNLEXCEPT);
		}
	}

	@Override
	public List<OOrg> findBureauList(PSysUser username) throws Exception {
		try {
			return iGenralTreeDao.findBureauList(username);
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.SYE_BUREAUEXCEPT);
		}
	}

	@Override
	public List<VwProtocolCode> findProtocolList() throws Exception {
		try {
			return iGenralTreeDao.findProtocolList();
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(
					ExceptionConstants.SYE_PROTOCOLlISTEXCEPT);
		}
	}

	/**
	 * 方法 convertPageFromOrg
	 * 
	 * @param page
	 * @return 从单位转化为 TreeNode 转化后的 Page 对象
	 */
	private Page<TreeNode> convertPageFromOrg(Page<OOrg> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());

		for (OOrg o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setName(o.getOrgName());
			tn.setText(o.getOrgName());
			tn.setType("org");
			tn.setId("org_" + o.getOrgNo() + "_" + o.getOrgType());
			tn.setQtip(tn.getText());
			tn.setLeaf(false);
			tn.setIconCls("net-" + o.getOrgType());
			retList.add(tn);
		}

		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromSub
	 * 
	 * @param page
	 * @return 从变电站转化为TreeNode 转化后的Page 对象
	 */
	private Page<TreeNode> convertPageFromSub(Page<GSubs> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (GSubs o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("net-subs");
			tn.setType("sub");
			StringBuffer sb = new StringBuffer();
			if (!(null == o.getSubsName() || "".equals(o.getSubsName()))) {
				sb.append("<font color='blue';>变电站名：</font>" + o.getSubsName() + "<br>");
			}
			if (!(null == o.getVoltCode() || "".equals(o.getVoltCode()))) {
				sb.append("<font color='blue';>电压等级：</font>" + o.getVoltCode() + "<br>");
			}
			if (!(null == o.getMtNum() || "".equals(o.getMtNum()))) {
				sb.append("<font color='blue';>主变台数：</font>" + o.getMtNum() + "<br>");
			}
			if (!(null == o.getMtCap() || "".equals(o.getMtCap()))) {
				sb.append("<font color='blue';>主变容量：</font>" + o.getMtCap() + "<br>");
			}
			if (!(null == o.getSubsAddr() || "".equals(o.getSubsAddr()))) {
				sb.append("<font color='blue';>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp地址：</font>" + o.getSubsAddr() + "<br>");
			}
			if (!(null == o.getRunStatusCode() || "".equals(o.getRunStatusCode()))) {
				sb.append("<font color='blue';>运行状态：</font>" + o.getRunStatusCode() + "<br>");
			}
			if (!(null == o.getChgDate() || "".equals(o.getChgDate()))) {
				sb.append("<font color='blue';>变更时间：</font>" + o.getChgDate() + "<br>");
			}
			tn.setText(o.getSubsName() + "(" + o.getVoltCode() + ")");
			tn.setQtip(sb.toString());
			tn.setId("sub_" + o.getSubsId() + "_" + o.getSubsId());
			retList.add(tn);
		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromLine
	 * 
	 * @param page
	 * @return 从线路转化为TreeNode 转化后的 Page 对象
	 */
	private Page<TreeNode> convertPageFromLine(Page<GLine> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (GLine o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("net-line");
			tn.setType("line");
			tn.setText(o.getLineName() + "(" + o.getVoltCode() + ")");
			tn.setQtip(tn.getText());
			tn.setId("line_" + o.getLineId() + "_" + o.getLineId());
			retList.add(tn);
		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromCustomer
	 * 
	 * @param page
	 * @return 从客户转化为树节点 转化后的对象
	 */
	private Page<TreeNode> convertPageFromCustomer(Page<CConsRtmnl> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		if(page == null)
			return null;
		retPage.setTotalCount(page.getTotalCount());
		for (CConsRtmnl o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			if (o.getConsType().equals("1"))
				tn.setIconCls("SpecialUser");
			else if (o.getConsType().equals("2"))
				tn.setIconCls("PublicUser");
			else if (o.getConsType().equals("3"))
				tn.setIconCls("TransFormUser");
			tn.setLeaf(true);
			tn.setType("usr");
			tn.setText(o.getConsName());
			tn.setQtip("用户编号:" + o.getConsNo() + "<br> 逻辑地址:"
					+ o.getTerminalId());
			// 树节点 ID 重复的情况，会造成节点点不中
			tn.setId("usr_" + o.getConsId() + "_" + o.getTmnlAssetNo() + "_"
					+ o.getCpNo());
			retList.add(tn);

		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromCustomer
	 * 
	 * @param page
	 * @return 从行业转化为树节点 转化后的对象
	 */
	private Page<TreeNode> convertPageFromTrade(Page<BTrade> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (BTrade o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("");
			tn.setType("trade");
			//tn.setText(o.getTradeName() + o.getTradeNo());
			tn.setText(o.getTradeName());
			tn.setQtip(tn.getText());
			tn.setId("trade_" + o.getTradeNo() + "_" + o.getTradeName());
			retList.add(tn);

		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromBackUserGroup
	 * 
	 * @param page
	 * @return 从用户群组转化为树节点 转化后的对象
	 */
	private Page<TreeNode> convertPageFromBackUserGroup(Page<RUserGroup> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (RUserGroup o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("net-02");
			tn.setType("ugp");
			tn.setText(o.getGroupName());
			tn.setQtip(tn.getText());
			tn.setId("ugp_" + o.getGroupNo() + "_" + o.getGroupNo());
			retList.add(tn);

		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromBackUserGroupDetail
	 * 
	 * @param page
	 * @return 从用户群组转化为树节点 转化后的对象
	 * @deprecated
	 */
	private Page<TreeNode> convertPageFromBackUserGroupDetail(
			Page<RUserGroupDetailId> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (RUserGroupDetailId o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("SpecialUser");
			tn.setType("usr");
			tn.setText("用户" + o.getConsNo());
			tn.setQtip("用户编号:" + o.getConsNo() + "<br> 逻辑地址:"
					+ o.getTmnlAssetNo());
			tn.setId("usr_" + o.getConsNo() + "_" + o.getTmnlAssetNo());
			tn.setLeaf(true);
			retList.add(tn);

		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromControlUserGroup
	 * 
	 * @param page
	 * @return 从控制群组转化为树节点 转化后的对象
	 * @deprecated
	 */
	private Page<TreeNode> convertPageFromControlUserGroup(Page<TTmnlGroup> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (TTmnlGroup o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("net-02");
			tn.setType("cgp");
			tn.setText(o.getGroupName());
			tn.setQtip(tn.getText());
			tn.setId("cgp_" + o.getGroupNo() + "_" + o.getGroupNo());
			retList.add(tn);

		}
		retPage.setResult(retList);
		return retPage;
	}

	/**
	 * 方法 convertPageFromControlUserGroup
	 * 
	 * @param page
	 * @return 从控制群组明细转化为树节点 转化后的对象
	 */
	private Page<TreeNode> convertPageFromControlUserGroupDetail(
			Page<TTmnlGroupDetailId> page) {
		Page<TreeNode> retPage = new Page<TreeNode>();
		List<TreeNode> retList = new ArrayList<TreeNode>();
		retPage.setTotalCount(page.getTotalCount());
		for (TTmnlGroupDetailId o : page.getResult()) {
			TreeNode tn = new TreeNode();
			tn.setAttributes(o);
			tn.setIconCls("SpecialUser");
			tn.setType("usr");
			tn.setLeaf(true);
			tn.setText("终端" + o.getConsNo());
			tn.setQtip("用户编号:" + o.getConsNo() + "<br> 逻辑地址:"
					+ o.getTmnlAssetNo());
			tn.setId("usr_" + o.getConsNo() + "_" + o.getTmnlAssetNo());
			retList.add(tn);

		}
		retPage.setResult(retList);
		return retPage;
	}

	// getters and setters

	public IGenralTreeDao getiGenralTreeDao() {
		return iGenralTreeDao;
	}

	public void setiGenralTreeDao(IGenralTreeDao iGenralTreeDao) {
		this.iGenralTreeDao = iGenralTreeDao;
	}

	/**
	 * 查询所有采集点类型
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List <CpTypeCode> getAllCpTypeCode() throws Exception{
		return iGenralTreeDao.findAllCpTypeCode();
	}
}
