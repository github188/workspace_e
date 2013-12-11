package com.nari.qrystat.colldataanalyse;

import java.util.List;
import java.util.Map;

import com.nari.privilige.PSysUser;
import com.nari.support.Page;
import com.nari.util.exception.DBAccessException;

public interface LosePowerManJdbcDao {
	/**
	 * 注册一些台区到相应组合表中
	 * @param g
	 * @param tgIds
	 */
	void saveGChkunit(Gchkunit g, List<Integer> tgIds);
	/**
	 * 找到某个考核单元下的所有的台区
	 * @param user
	 * @param gid
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	Page findTgsInGChkunit(PSysUser user, Integer gid, long start, int limit);
	
	/**
	 * 删除某个考核单元下的所指定的台区
	 * @param gId
	 * @param tgs
	 */
	void deleteGChkunitComp(Integer gId, List<Integer> tgs);
	/**
	 * 修改一个考核单元中的内容
	 * @param g
	 */
	void updateGChkunit(Gchkunit g);
	/**
	 * 在考核单元中添加新的台区
	 * @param gid  考核单元的名称
	 * @param tgs  添加的台区的id
	 */
	void addTgs(Integer gid, List tgs);
	/**
	 * 删除指定的考核单元
	 * @param user
	 * @param gids  选择要被删除的考核单元
	 */
	public void deleteGChkunit(final PSysUser user, final List<Integer> gids) ;
	
	/**
	 * 向G_IO_MP中加入数据，通过METER_ID和MP_ID两个字段来merge
	 * @param gid 考核单元id
	 * @param isIn 是流入还是流出
	 * @param mpIds 所选的电能表id列表
	 * @param meterIds 所选的表计id列表
	 * @param dataIds e_data_mp里面的id
	 */
	void mergeGiomp(Integer gid, Integer isIn,final Integer isVaild, List<String> mpIds,
			List<String> meterIds,List<String> dataIds, List<String> pqFlags,List<String> objIds);
	/**
	 * 找到一个考核单元下所有的终端的终端地址
	 * @param gid
	 * @return
	 */
	List findAddrInGChkunit(Integer gid,String type);
	/**
	 * 通过一个id来找到一个参考指标的,如果没有找到返回null
	 * @param gid
	 * @return
	 */
	Map findRefByChkunit(Integer gid);
	/**
	 * 删除一个考核指标，通过chkunit_id
	 * @param gid
	 */
	void deleteRef(Integer gid);
	/**
	 * 通过对chkunit_id的判断来更新或者插入一条参考指标
	 * @param gr
	 */
	void saveOrUpdateRef(GChkunitRefIdx gr);
	
	/**
	 * 在新建台区的时候，在提交的台区中找到已经被注册的，
	 * <br>并且把找的已经被注册的台区的被注册的考核单元的名称和id等信息返回
	 * @param tgIds
	 * @param start
	 * @param limit
	 * @return
	 */
	Page<Map> checkExistsTgs(List<Integer> tgIds, long start, int limit);
	/**
	 * 找到一个考核单元下面的所有的参考指标
	 *	@param chkunitId 考核周期名称
	 */
	List<GChkunitRefIdx> findRefsByChkunitId(Integer chkunitId);
	
	/**
	 * 通过msg的不同更新或者保存一个参考指标的列表 此方法能保证操作后chkunit和周期能唯一确定一条记录 <br>
	 * 对于增加
	 * 
	 * @param refs
	 */
	void updateRefs(List<GChkunitRefIdx> refs);
	
	/**
	 * 通过参考指标的id列表来删除
	 * @param idxIds
	 */
	void deleteRefByIdxIds(List<Integer> idxIds);
	/**
	 * 判断某个用户是不是有某个url的访问权限
	 * @param user
	 * @param url
	 * @return
	 */
	boolean hasMenuPower(PSysUser user, String url);
	

}
