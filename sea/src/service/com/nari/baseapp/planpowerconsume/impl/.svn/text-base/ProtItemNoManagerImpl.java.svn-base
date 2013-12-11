package com.nari.baseapp.planpowerconsume.impl;


import com.nari.baseapp.planpowerconsume.IProtItemNoJdbcDao;
import com.nari.baseapp.planpowerconsume.IProtItemNoManager;
import com.nari.util.exception.DBAccessException;

/**
 * 取prot_item_no相关信息接口实现类
 * @author 姜海辉
 *
 */
public class ProtItemNoManagerImpl implements IProtItemNoManager{
	private IProtItemNoJdbcDao iProtItemNoJdbcDao;
	

    public void setiProtItemNoJdbcDao(IProtItemNoJdbcDao iProtItemNoJdbcDao) {
		this.iProtItemNoJdbcDao = iProtItemNoJdbcDao;
	}

	/**
    * 查询终端规约码
    * @param tmnlAssetNo
    * @return
    */
	public String qureyProtocolCode(String tmnlAssetNo) throws DBAccessException{
		return this.iProtItemNoJdbcDao.findProtocolCode(tmnlAssetNo);

	}
	
	/**
	 * 查询规约项编码
	 * @param protocolCode
	 * @return
	 * @throws DBAccessException
	 */
	public String[] qureyProtItemNo(String tmnlAssetNo,String type,Integer fn)throws DBAccessException{
		if(null==tmnlAssetNo||null==type||null==fn)
			return null;
        String protocolCode=this.iProtItemNoJdbcDao.findProtocolCode(tmnlAssetNo);
		String fnH=String.valueOf(Integer.toHexString(fn));
		
        if(fnH.length()==1)
        	fnH="0"+fnH;
        if(fnH.length()!=2)
            return null;
        StringBuffer protocolNo=new StringBuffer();
        protocolNo.append(protocolCode);
        protocolNo.append(type);
        protocolNo.append(fnH.toUpperCase());
        System.out.println("规约号："+protocolNo.toString());
		return this.iProtItemNoJdbcDao.findProtItemNo(protocolNo.toString());
	}
	

	
		
}
