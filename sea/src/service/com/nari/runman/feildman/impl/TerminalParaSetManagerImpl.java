package com.nari.runman.feildman.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.nari.basicdata.BCommProtItemListId;
import com.nari.basicdata.BCommProtocol;
import com.nari.basicdata.BCommProtocolItem;
import com.nari.basicdata.VwDataTypePBean;
import com.nari.basicdata.VwProtocolCodeBean;
import com.nari.elementsdata.EDataMp;
import com.nari.privilige.PSysUser;
import com.nari.runman.feildman.FetchInfoBean;
import com.nari.runman.feildman.ItemListDao;
import com.nari.runman.feildman.MpInfoDao;
import com.nari.runman.feildman.MpInfoSaveDao;
import com.nari.runman.feildman.ProtocolItemDao;
import com.nari.runman.feildman.ProtocolNameDao;
import com.nari.runman.feildman.TerminalInfoBean;
import com.nari.runman.feildman.TerminalListDao;
import com.nari.runman.feildman.TerminalParaSetManager;
import com.nari.runman.feildman.TerminalSaveDao;
import com.nari.runman.feildman.VwDataTypeDao;
import com.nari.runman.feildman.VwProtocolCodeDao;
import com.nari.support.Page;
import com.nari.terminalparam.CallValueBean;
import com.nari.terminalparam.MpMeterInfoBean;
import com.nari.terminalparam.TTmnlMpParam;
import com.nari.terminalparam.TTmnlParam;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ServiceException;

/**
 * 终端参数设定业务层实现
 * 
 * @author longkc
 */
public class TerminalParaSetManagerImpl implements TerminalParaSetManager {

	// 规约类型DAO
	private VwProtocolCodeDao vwProtocolCodeDao;
	// 规约数据类型DAO
	private VwDataTypeDao vwDataTypeDao;

	//
	private ProtocolItemDao proItemDao;
	// 规约项明细DAO
	private ProtocolNameDao proNameDao;
	//
	private ItemListDao itemListDao;
	// 终端参数DAO
	private TerminalListDao terminalListDao;
	//
	private TerminalSaveDao terminalSaveDao;
	//
	private MpInfoSaveDao mpInfoSaveDao;
	//
	private MpInfoDao mpInfoDao;

	public void setMpInfoSaveDao(MpInfoSaveDao mpInfoSaveDao) {
		this.mpInfoSaveDao = mpInfoSaveDao;
	}

	public void setMpInfoDao(MpInfoDao mpInfoDao) {
		this.mpInfoDao = mpInfoDao;
	}

	public void setTerminalSaveDao(TerminalSaveDao terminalSaveDao) {
		this.terminalSaveDao = terminalSaveDao;
	}

	public void setTerminalListDao(TerminalListDao terminalListDao) {
		this.terminalListDao = terminalListDao;
	}

	public void setVwProtocolCodeDao(VwProtocolCodeDao vwProtocolCodeDao) {
		this.vwProtocolCodeDao = vwProtocolCodeDao;
	}

	public void setVwDataTypeDao(VwDataTypeDao vwDataTypeDao) {
		this.vwDataTypeDao = vwDataTypeDao;
	}

	public void setProItemDao(ProtocolItemDao proItemDao) {
		this.proItemDao = proItemDao;
	}

	public void setProNameDao(ProtocolNameDao proNameDao) {
		this.proNameDao = proNameDao;
	}

	public void setItemListDao(ItemListDao itemListDao) {
		this.itemListDao = itemListDao;
	}

	@Override
	public List<VwDataTypePBean> getDataTypeList(String proCode, String proNO)
			throws Exception {
		try {
		
			return this.vwDataTypeDao.findType(proCode, proNO);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置数据类型查询出错");
		}
	}

	@Override
	public List<VwProtocolCodeBean> getProCodeList() throws Exception {
		try {
			return this.vwProtocolCodeDao.findType();
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置规约类型查询出错");
		}

	}

	@Override
	public List<BCommProtocolItem> getProtocolItemList(String proNO)
			throws Exception {
		try {
			return this.proItemDao.findType(proNO);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置规约项查询出错");
		}
	}

	@Override
	public List<BCommProtocol> getProtocolList(String dataType, String proCode)
			throws Exception {
		try {
			return this.proNameDao.findType(dataType, proCode);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置事件类型查询出错");
		}
	}

	@Override
	public List<BCommProtItemListId> getProtlItemListId(String proItemNO)
			throws Exception {
		try {
			return this.itemListDao.findItemList(proItemNO);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置规约项明细查询出错");
		}
	}

	@Override
	public List<FetchInfoBean> getTerminalList(String proNo, String terminalNo)
			throws Exception {
		try {
			return this.terminalListDao.FindTerminalList(proNo, terminalNo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端事件数据查询出错");
		}
	}

	@Override
	public void saveTerminal(List<TTmnlParam> tTmnlParams) throws Exception {
		try {
//			String terminalNO = tTmnlParams.get(0).getId().getTmnlAssetNo();
//			String proNo = tTmnlParams.get(0).getId().getProtItemNo()
//					.substring(0, 5);
//			this.terminalListDao.deleteTerminalInfo(proNo, terminalNO);
			this.terminalSaveDao.saveTerminalInfo(tTmnlParams);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端事件数据保存/更新出错");
		}
	}

	@Override
	public void updateTerminal(List<TTmnlParam> ttmInfo) throws Exception {
		// TODO Auto-generated method stub
		try {
			this.terminalSaveDao.saveTerminalInfo(ttmInfo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端事件数据保存出错");
		}
	}

	@Override
	public List<EDataMp> getMpInfoList(String tmnlAssetNo, short pn)
			throws Exception {
		try {
			return this.mpInfoDao.findMpInfo(tmnlAssetNo, pn);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端测量点数据查询出错");
		}
	}

	@Override
	public void saveMpInfo(List<TTmnlMpParam> mpInfo) throws Exception {
		try {
			String dataId = String.valueOf(mpInfo.get(0).getId().getDataId());
			String proNo = mpInfo.get(0).getId().getProtItemNo()
					.substring(0, 5);
			this.mpInfoDao.deleteTmnlMpParam(dataId, proNo);
			this.mpInfoSaveDao.saveMpInfo(mpInfo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端测量点数据更新出错");
		}
	}

	@Override
	public void updateMpInfo(List<TTmnlMpParam> mpInfo) throws Exception {
		try {
			this.mpInfoDao.updateMpInfo(mpInfo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端测量点数据更新出错");
		}
	}
	
	@Override
	public void updateMertInfo(List<FetchInfoBean> fiBean) throws Exception {
		try {
			this.terminalListDao.updateTmnlTermInfo(fiBean);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端表计数据更新出错");
		}
	}

	@Override
	public List<FetchInfoBean> getTmnlMpList(String proNo, String tmnlAssetNo,
			short[] pn) throws Exception {
		try {
			return this.mpInfoDao.findTmnlMpParam(proNo, tmnlAssetNo, pn);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置终端测量点数据查询出错");
		}
	}

	@Override
	public List<TerminalInfoBean> getTermList(String nodeType,
			String nodeValue, PSysUser userInfo, String protCode)
			throws Exception {
		try {
			return this.terminalListDao.searchTerminalList(nodeType, nodeValue,
					userInfo, protCode);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置左边数终端用户查询出错");
		}
	}
	
	public List<TerminalInfoBean> getTmnlArrList(String[] tmnlAssetNoArr, PSysUser userInfo, String protCode) throws Exception {
		try {
			return this.terminalListDao.searchTerminaArrlList(tmnlAssetNoArr, userInfo, protCode);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("参数设置左边数终端用户查询出错");
		}
	}

	@Override
	public Map<String, List<FetchInfoBean>> getTermMeterInfo(
			String tmnlAssetNo, String protocolNo) throws Exception {
		try {
			List<FetchInfoBean> rs = this.terminalListDao.FindTerminalList(
					protocolNo, tmnlAssetNo);

			// 按块序号拆分数据
			Map<String, List<FetchInfoBean>> listMap = new HashMap<String, List<FetchInfoBean>>();
			String compareStr = "0";
			String mpSn = "0";
			List<FetchInfoBean> rsList = new ArrayList<FetchInfoBean>();
			for (FetchInfoBean fiBean : rs) {
				String blockSn = fiBean.getBlockSn();
				// 块序号为0的单独处理，其他的块序号相同的数据组合放在同一个map中
				if ("0".equals(blockSn)) {
					rsList.add(fiBean);
				} else if (compareStr.equals(blockSn)) {
					// 块序号不发生变更时将块序号对应的信息存入list之中
					rsList.add(fiBean);
					//创建测量点->数据的（key->value）
					//取出块所对应的测量点号
					if (("003").equals(fiBean.getProtItemNo().substring(5, 8))) {
						mpSn = fiBean.getCurrentValue();
					}
					if (!"0".equals(mpSn)) {
						listMap.put(mpSn, rsList);
					}
				} else {
					// 块序号变更时，然后初始化list
					compareStr = fiBean.getBlockSn();
					mpSn = "0";
					rsList = new ArrayList<FetchInfoBean>();
					rsList.add(fiBean);
				}
			}
			return listMap;
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("终端表记数据查询出错");
		}
	}

	@Override
	public List<MpMeterInfoBean> getTermMeterInfo(String tmnlAssetNo)
			throws Exception {
		try {
			return this.terminalListDao.getTermMeterInfo(tmnlAssetNo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("终端表记信息查询出错");
		}
	}

	@Override
	public List<TerminalInfoBean> getTermInfo(String tmnlAssetNo)
			throws Exception {
		try {
			return this.terminalListDao.searchTerminalInfo(tmnlAssetNo);
		} catch (DataAccessException dbe) {
			throw new DBAccessException(BaseException.processDBException(dbe));
		} catch (Exception e) {
			throw new ServiceException("终端信息查询出错");
		}
	}

	@Override
	public void saveOrUpdateFetchInfoBeanForMP(List<FetchInfoBean> pubDetailList) {
		try{
			 this.terminalListDao.saveOrUpdateFetchInfoBeanForMP(pubDetailList);
		} catch (DataAccessException e) {
			e.printStackTrace();		
		}
	}

	@Override
	public void saveOrUpdateFetchInfoBeanForTP(List<FetchInfoBean> pubDetailList) {
		
		try{
			 this.terminalListDao.saveOrUpdateFetchInfoBeanForTP(pubDetailList);
		} catch (DataAccessException e) {
			e.printStackTrace();		
		}
		
	}

	@Override
	public Page<CallValueBean> getCallValueBean(String tmnlAssetNo,
			String protNO, List blockSn, long start, int limit) throws DBAccessException{
		try {
			return this.terminalListDao.getCallValueBean(tmnlAssetNo, protNO, blockSn, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}

	@Override
	public Page<CallValueBean> getCallValueBeanMpSn(String tmnlAssetNo,
			String protNO, List mpSn, List blockSn, long start, int limit) throws DBAccessException {
		try {
			return this.terminalListDao.getCallValueBeanMpSn(tmnlAssetNo, protNO, mpSn, blockSn, start, limit);
		} catch (DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));			
		}
	}
}
