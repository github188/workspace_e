package com.nari.qrystat.colldataanalyse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.ServiceException;

/**
 * 台区线损管理接口
 * @author huangxuan
 *
 */
public interface LosePowerManManager {
	/**
	 * 通过左边树来加载数据
	 * 
	 * @param user
	 * @param nodeStr
	 * @return
	 * @throws ServiceException
	 */
	List<Map> findLeftTreeClick(PSysUser user, String nodeStr)
			throws ServiceException;
	/**
	 * 分页来通过finder实体来找到所有的满足要求的考核单元
	 * @param user 当前的用户
	 * @param start
	 * @param limit
	 * @param finder 一个查询的实体
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findChkUnit(PSysUser user, long start, int limit,
			GChkUnitFinder finder) throws ServiceException;
	/**
	 * 分页版本的通过左边树来查找相应的台区的信息
	 * 
	 * @param user
	 * @param start
	 * @param limit
	 * @param nodeStr
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findPageLeftClick(PSysUser user, long start, int limit,
			String nodeStr, TgFinder finder) throws ServiceException;
	/**
	 * 删除某个考核单元下的所指定的台区
	 * 
	 * @param gId
	 * @param tgs
	 * @throws ServiceException
	 */
	void deleteGChkunitComp(Integer gId, List<Integer> tgs)
			throws ServiceException;
	/**
	 * 修改一个考核单元中的内容
	 * 
	 * @param g
	 * @throws ServiceException
	 */
	void updateGChkunit(Gchkunit g) throws ServiceException;
	/**
	 * 找到某个考核单元下的所有的台区
	 * 
	 * @param user
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	Page findTgsInGChkunit(PSysUser user, Integer gid, long start, int limit)
			throws ServiceException;
	/**
	 * 注册一些台区到相应组合表中
	 * 
	 * @param g
	 * @param tgIds
	 * @throws ServiceException
	 */
	void saveGChkunit(Gchkunit g, List<String> chkCycles, List<Integer> tgIds)
			throws ServiceException;
	/**
	 * 根据一个台区的id和用户来找到某个考核单元的详细信息
	 * @param user
	 * @param gid
	 * @return
	 * @throws ServiceException
	 */
	Map findGchkunitById(PSysUser user, Integer gid) throws ServiceException;
	
	/**
	 * 在考核单元中添加新的台区
	 * 
	 * @param gid
	 *            考核单元的名称
	 * @param tgs
	 *            添加的台区的id
	 */
	void addTgs(Integer gid, List tgs) throws ServiceException;

	/**
	 * 按照finder来查找相应的计量点
	 * @param tmnlAddr
	 * @param finder
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findMeterMp(Integer gid,String type, MeterMpFinder finder, long start,
			int limit) throws ServiceException;
	
	/**
	 * 删除指定的考核单元
	 * @param user 当前的登录用户
	 * @param gids 要删除的考核单元的id
	 * @throws ServiceException 
	 */
	public void deleteGChkunit(PSysUser user, List<Integer> gids) throws ServiceException;
	/**
	 * 向G_IO_MP中加入数据，通过METER_ID和MP_ID两个字段来merge
	 * @param gid 考核单元id
	 * @param isIn 是流入还是流出
	 * @param mpIds 所选的电能表id列表
	 * @param meterIds 所选的表计id列表
	 * @param dataIds e_data_mp中所对应的id
	 * @throws ServiceException 
	 */
	void mergeGiomp(Integer gid, Integer isIn,final Integer isVaild, List<String> mpIds,
			List<String> meterIds,List<String> dataIds,List<String> pqFlags,List<String> objIds) throws ServiceException;
	
	/**
	 *通过终端地址来找采集器资产号
	 * @param tmnlAddr
	 * @return
	 * @throws ServiceException
	 */
	List<Map> findFrm(String tmnlAddr) throws ServiceException;
	
	/**
	 * 通过一个考核单元的ip来找到这个考核单元中包含的所有的
	 * 台区的终端资产号
	 * @param gid
	 * @return
	 * @throws ServiceException
	 */
	List findTmnlAddr(Integer gid,String type) throws ServiceException;
	/**
	 * 按照finder来查找相应的计量点的未分页版本
	 * @param gid
	 * @param finder  一个findr用来找到所有的数据
	 * @param type 根据不同的类型选择是台区还是线路等考核单元
	 * @return
	 * @throws ServiceException
	 */
	List<Map> findMeterMp(Integer gid,String type, MeterMpFinder finder)
			throws ServiceException;
	
	/**
	 * 通过考核单元id来查找一个考核单元下面的所有的台区
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findPageTgsInGchkunit(Integer gid, long start, int limit)
			throws ServiceException;
	/**
	 * 通过一个id来找到一个参考指标的,如果没有找到返回null
	 * @param gid
	 * @return
	 * @throws ServiceException 
	 */
	Map findRefByChkunit(Integer gid) throws ServiceException;
	/**
	 * 删除一个考核指标，通过chkunit_id
	 * @param gid
	 * @throws ServiceException 
	 */
	void deleteRef(Integer gid) throws ServiceException;
	/**
	 * 通过对chkunit_id的判断来更新或者插入一条参考指标
	 * @param gr
	 * @throws ServiceException 
	 */
	void saveOrUpdateRef(GChkunitRefIdx gr) throws ServiceException;
	
	/**
	 * 在新建台区的时候，在提交的台区中找到已经被注册的，
	 * <br>并且把找的已经被注册的台区的被注册的考核单元的名称和id等信息返回
	 * @param tgIds
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException 
	 */
	Page<Map> checkExistsTgs(List<Integer> tgIds, long start, int limit) throws ServiceException;
	//修改参考指标的实现
	/**
	 * 找到一个考核单元下面的所有的参考指标
	 *	@param chkunitId 考核周期名称
	 * @throws ServiceException 
	 */
	List<GChkunitRefIdx> findRefsByChkunitId(Integer chkunitId) throws ServiceException;
	
	/**
	 * 通过msg的不同更新或者保存一个参考指标的列表 此方法能保证操作后chkunit和周期能唯一确定一条记录 <br>
	 * 对于增加
	 * 
	 * @param refs
	 * @throws ServiceException 
	 */
	void updateRefs(List<GChkunitRefIdx> refs) throws ServiceException;
	
	/**
	 * 通过参考指标的id列表来删除
	 * @param idxIds
	 * @throws ServiceException 
	 */
	void deleteRefByIdxIds(List<Integer> idxIds) throws ServiceException;
	
	/**
	 * 通过左边树来查找线路,进行分页查询
	 * @param user
	 * @param nodeStr
	 * @param start
	 * @param limit
	 * @param finder
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findLeftByLine(PSysUser user, String nodeStr, long start,
			int limit, LeftTreeLineFinder finder) throws ServiceException;
	/**
	 * 查找一个考核单元中已经注册的过的线路
	 * @param user
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	Page<Map> findExistLine(PSysUser user, Integer gid, long start, int limit)
			throws ServiceException;
	/**
	 * 找到在注册的时候已经注册过的线路
	 * @param ids
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findCheckLine(List<Integer> ids, long start, int limit)
			throws ServiceException;
	/**
	 * 查找分压等级
	 * @return
	 * @throws ServiceException
	 */
	List<Map> findVoltType() throws ServiceException;
	/**
	 * 统计全省的当天的电量 
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findDayPqStat(PSysUser user, Date statDate, long start, int limit) throws ServiceException;
	/**
	 * 通过不同的类型查询电量的统计
	 * @param user
	 * @param type
	 * @return
	 * @throws ServiceException
	 */
	Page<Map> findDayPqStatByType(PSysUser user, String orgNo, String type,Date statDate, long start, int limit)
			throws ServiceException;
	/**
	 * 判断某个用户是不是有某个url的访问权限
	 * @param user
	 * @param url
	 * @return
	 * @throws ServiceException 
	 */
	boolean findMenuPower(PSysUser user, String url) throws ServiceException;
	
}
