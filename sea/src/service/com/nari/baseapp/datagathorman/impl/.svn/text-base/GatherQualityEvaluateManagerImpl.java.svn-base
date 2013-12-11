package com.nari.baseapp.datagathorman.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.nari.ami.database.datapursue.ATmnlMissingMark;
import com.nari.ami.database.datapursue.ATmnlMissingMarkId;
import com.nari.ami.datapursue.TmnlTaskFactory;
import com.nari.baseapp.datagatherman.ATmnlCollQuality;
import com.nari.baseapp.datagatherman.CommMode;
import com.nari.baseapp.datagatherman.ConsType;
import com.nari.baseapp.datagatherman.GatherByHandDto;
import com.nari.baseapp.datagatherman.MissingTerminalDTO;
import com.nari.baseapp.datagatherman.TerminalTypeCode;
import com.nari.baseapp.datagatherman.TmnlModeCode;
import com.nari.baseapp.datagathorman.GatherQualityEvaluateJdbcDao;
import com.nari.baseapp.datagathorman.GatherQualityEvaluateManager;
import com.nari.fe.commdefine.task.TermTaskInfo;
import com.nari.oranization.OOrgDao;
import com.nari.orgnization.OOrg;
import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.NodeTypeUtils;
import com.nari.util.exception.BaseException;
import com.nari.util.exception.DBAccessException;
import com.nari.util.exception.ExceptionConstants;
import com.nari.util.exception.ServiceException;

public class GatherQualityEvaluateManagerImpl implements
		GatherQualityEvaluateManager {

	private GatherQualityEvaluateJdbcDao gatherQualityEvaluateJdbcDao;
	private OOrgDao oOrgDao;

	public void setGatherQualityEvaluateJdbcDao(
			GatherQualityEvaluateJdbcDao gatherQualityEvaluateJdbcDao) {
		this.gatherQualityEvaluateJdbcDao = gatherQualityEvaluateJdbcDao;
	}
	public void setoOrgDao(OOrgDao oOrgDao) {
		this.oOrgDao = oOrgDao;
	}

	/**
	 * 查询采集质量
	 * @param orgType 操作员单位类型
	 * @param orgNo 操作员单位编码
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return
	 * @throws DBAccessException
	 */
	public List<ATmnlCollQuality> queryGatherQuality(PSysUser pSysUser,String orgNo,
										String startDate,String endDate) throws Exception{
		List<ATmnlCollQuality> list = new ArrayList<ATmnlCollQuality>();
		try{
			OOrg oOrg = this.oOrgDao.findById(orgNo);
			String orgType = oOrg==null?"":oOrg.getOrgType();
			list = this.gatherQualityEvaluateJdbcDao.findGatherQuality(pSysUser, orgType, orgNo, startDate, endDate);
			return list;
		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.BAE_QUALITYEXCEPT);
		}
	}
	
	/**
	 * 漏点补招
	 * @param gatherByHandDto 漏点信息
	 * @return
	 * @throws Exception
	 */
	public void recallByHand(GatherByHandDto[] gatherByHandDto) throws Exception{
		try{
			for (int i = 0; i < gatherByHandDto.length; i++) {
				//生成漏点信息
				ATmnlMissingMark aTmnlMissingMark = new ATmnlMissingMark();
				ATmnlMissingMarkId aTmnlMissingMarkId = new ATmnlMissingMarkId();
				aTmnlMissingMarkId.setMpSn(gatherByHandDto[i].getMpSn());
				aTmnlMissingMarkId.setProtNoList(gatherByHandDto[i].getProtNoList());
				aTmnlMissingMarkId.setStatDate(gatherByHandDto[i].getStatDate());
				aTmnlMissingMarkId.setTmnlAssetNo(gatherByHandDto[i].getTmnlAssetNo());
				aTmnlMissingMark.setMarkId(aTmnlMissingMarkId);
				aTmnlMissingMark.setApplyCnt(gatherByHandDto[i].getApplyCnt());
				aTmnlMissingMark.setDataGroup(gatherByHandDto[i].getDataGroup());
				aTmnlMissingMark.setId(gatherByHandDto[i].getDataId());
				aTmnlMissingMark.setDataSrc(gatherByHandDto[i].getDataSrc());
				aTmnlMissingMark.setDenizenMp(gatherByHandDto[i].getDenizenMp());
				aTmnlMissingMark.setFirstCollCnt(gatherByHandDto[i].getFirstCollCnt());
				aTmnlMissingMark.setIsDenizen(gatherByHandDto[i].getIsDenizen());
				aTmnlMissingMark.setMissingCnt(gatherByHandDto[i].getMissingCnt());
				aTmnlMissingMark.setPowerCutCnt(gatherByHandDto[i].getPowerCutCnt());
				aTmnlMissingMark.setTemplateId(gatherByHandDto[i].getTemplateId());
				//调用补招接口进行补招
				List<TermTaskInfo> rtList = 
				TmnlTaskFactory.createManualRecallTask(aTmnlMissingMark);
				System.out.println(rtList.size());
			}

		} catch (DataAccessException de) {
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("手工补召任务下发出错");
		}
	}
	
	/**
	 * 查询漏召信息
	 * @param nodeValue 单位编码/线路Id/变电站Id/用户编号/群组编号(树节点值)
	 * @param nodeType	org/line/sub/usr/cgp、ugp(树节点类型)
	 * @param dataType	数据类型（抄表、负荷、电能质量）
	 * @param startDate	起始时间
	 * @param endDate	结束时间
	 * @param orgType	供电单位级别
	 * @return
	 * @throws Exception
	 */
	public Page<GatherByHandDto> queryUnGatherInfo(PSysUser pSysUser,long start,int limit,String nodeValue,String nodeType,String dataType,String startDate,String endDate,String orgType) throws Exception{
		Page<GatherByHandDto> pgb = new Page<GatherByHandDto>();
		try{
			pgb = this.gatherQualityEvaluateJdbcDao.findUnGatherInfo(pSysUser, start, limit, nodeValue, nodeType, startDate, endDate, dataType);
//			if(NodeTypeUtils.NODETYPE_ORG.equals(nodeType)){
//				if(orgType==null || "".equals(orgType)){
//					OOrg oOrg = this.oOrgDao.findById(nodeValue);
//					orgType = oOrg==null?"":oOrg.getOrgType();
//				}
//				if("06".equals(orgType)||"04".equals(orgType)||"03".equals(orgType)){
//					pgb = this.gatherQualityEvaluateJdbcDao.findUnGatherInfoByOrg(pSysUser, start, limit, nodeValue, orgType, startDate, endDate, dataType);
//				}else{
//					return pgb;
//				}
//			}else if(NodeTypeUtils.NODETYPE_LINE.equals(nodeType)||NodeTypeUtils.NODETYPE_SUB.equals(nodeType)){
//				pgb = this.gatherQualityEvaluateJdbcDao.findUnGatherInfo(pSysUser, start, limit, nodeValue, nodeType, startDate, endDate, dataType);
//			}else if(NodeTypeUtils.NODETYPE_USER.equals(nodeType)){
//				pgb = this.gatherQualityEvaluateJdbcDao.findUnGatherInfoByUsr(start, limit, nodeValue, startDate, endDate, dataType);
//			}else if(NodeTypeUtils.NODETYPE_CGP.equals(nodeType)||NodeTypeUtils.NODETYPE_UGP.equals(nodeType)){
//				pgb = this.gatherQualityEvaluateJdbcDao.findUnGatherInfoByGrp(start, limit, nodeValue, startDate, endDate, nodeType, dataType);
//			}
			return pgb;
		}catch (DataAccessException de) {
			de.printStackTrace();
			throw new DBAccessException(BaseException.processDBException(de));
		} catch (Exception e) {
			throw new ServiceException("查询漏召信息出错");
		}
	}
	
	/**
	 * 查询全部用户类型
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<ConsType> getAllConsType() throws Exception{
		return gatherQualityEvaluateJdbcDao.findAllConsType();
	}
	
	/**
	 * 查询全部终端型号
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<TmnlModeCode> getAllTmnlModeCode() throws Exception{
		return gatherQualityEvaluateJdbcDao.findAllTmnlModeCode();
	}
	
	/**
	 * 查询全部终端类型
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<TerminalTypeCode> getAllTmnlType() throws Exception{
		return gatherQualityEvaluateJdbcDao.findAllTmnlType();
	}
	
	/**
	 * 查询全部通信方式
	 * chenjg
	 * @return
	 * @throws Exception
	 */
	public List<CommMode> getAllCommMode() throws Exception{
		try{
			return gatherQualityEvaluateJdbcDao.findAllCommMode();
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException(ExceptionConstants.QYE_LOSEPOWERSTATREADINFO);
		}

	}
	
	/**
	 * chenjg
	 * @param staffNo 工号
	 * @param orgNo
	 * @param consType 用户类型
	 * @param tmnlModeCode 终端型号
	 * @param commMode 通信方式
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 * @throws Exception
	 */
	public List<ATmnlCollQuality> queryGatherQuality(String staffNo,String orgType, String orgNo,String consType,String tmnlModeCode,String commMode,
			String startDate,String endDate) throws Exception{
		try{
			return gatherQualityEvaluateJdbcDao.queryGatherQuality(staffNo, orgType, orgNo, consType, tmnlModeCode, commMode, startDate, endDate);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("查询采集质量情况出错");
		}

	}
	
	
	/**
	 * chenjg
	 * 查询采集失败终端
	 * @param start
	 * @param limit
	 * @param staffNo
	 * @param consType
	 * @param orgNo
	 * @param tmnlModeCode
	 * @param commMode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public Page<MissingTerminalDTO> getMissingTerminal(long start,int limit,String staffNo,String consType,String orgNo,String tmnlModeCode,String commMode,String startDate,String endDate) throws Exception{
		try{
			return gatherQualityEvaluateJdbcDao.findMissingTermianl(start, limit, staffNo, consType, orgNo, tmnlModeCode, commMode, startDate, endDate);
		}catch(DataAccessException e) {
			throw new DBAccessException(BaseException.processDBException(e));
		} catch (Exception e) {
			throw new ServiceException("查询采集失败终端出错");
		}

	}
}
