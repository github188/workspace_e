package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.PowerCtrlDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.WFloatDownCtrl;
import com.nari.orderlypower.WTmnlFactctrl;
import com.nari.orderlypower.WTmnlPwrctrl;
import com.nari.util.exception.DBAccessException;

public class PowerCtrlDaoImpl extends JdbcBaseDAOImpl implements PowerCtrlDao {

	@Override
	public void deleteBySchemeId(Long ctrlSchemeId) {
		String sql = "delete from w_ctrl_scheme_section w where w.ctrl_scheme_id =?";
		getJdbcTemplate().update(sql, new Object[]{ctrlSchemeId});
	}

	@Override
	public void updateTmnlFactctrl(WTmnlFactctrl tmnlFactctrl)
			throws DBAccessException {
		int ctrlFlag = 0;
		if(null != tmnlFactctrl){
			if(tmnlFactctrl.getCtrlFlag())
				ctrlFlag = 1;
		    String sql = 
		        "UPDATE W_TMNL_FACTCTRL TF\n" +
		        "SET TF.CTRL_FLAG = ?,\n" + 
		        "    TF.SEND_TIME = ?\n" + 
		        "	  TF.SUCCESS_TIME = ?\n" +
		        "	  TF.STAFF_NO = ?\n" +
		        "WHERE TF.TMNL_ASSET_NO = ? AND TF.TOTAL_NO = ?";
            getJdbcTemplate().update(sql,
					new Object[] { ctrlFlag, tmnlFactctrl.getSendTime(),
							tmnlFactctrl.getSuccessTime(),
							tmnlFactctrl.getStaffNo(),
							tmnlFactctrl.getTmnlAssetNo(),
							tmnlFactctrl.getTotalNo() });
		}
	}

	@Override
	public void updateTmnlFloat(WFloatDownCtrl floatDownCtrl)
			throws DBAccessException {
		int ctrlFlag = 0;
		if(null != floatDownCtrl){
			if(floatDownCtrl.getCtrlFlag())
				ctrlFlag = 1;
		    String sql = 
		        "UPDATE W_FLOAT_DOWN_CTRL TF\n" +
		        "SET TF.CTRL_FLAG = ?,\n" + 
		        "    TF.SEND_TIME = ?\n" + 
		        "	  TF.SUCCESS_TIME = ?\n" +
		        "	  TF.STAFF_NO = ?\n" +
		        "WHERE TF.TMNL_ASSET_NO = ? AND TF.TOTAL_NO = ?";
            getJdbcTemplate().update(sql,
					new Object[] { ctrlFlag, floatDownCtrl.getSendTime(),
							floatDownCtrl.getSuccessTime(),
							floatDownCtrl.getStaffNo(),
							floatDownCtrl.getTmnlAssetNo(),
							floatDownCtrl.getTotalNo() });
		}
	}

	@Override
	public void updateTmnlPwrctrl(WTmnlPwrctrl tmnlPwrctrl)
			throws DBAccessException {
		int ctrlFlag = 0;
		if(null != tmnlPwrctrl){
			if(tmnlPwrctrl.getCtrlFlag())
				ctrlFlag = 1;
		    String sql = 
		        "UPDATE W_TMNL_PWRCTRL TF\n" +
		        "SET TF.CTRL_FLAG = ?,\n" + 
		        "    TF.SEND_TIME = ?\n" + 
		        "	  TF.SUCCESS_TIME = ?\n" +
		        "	  TF.STAFF_NO = ?\n" +
		        "WHERE TF.TMNL_ASSET_NO = ? AND TF.TOTAL_NO = ?";
            getJdbcTemplate().update(sql,
					new Object[] { ctrlFlag, tmnlPwrctrl.getSendTime(),
							tmnlPwrctrl.getSuccessTime(),
							tmnlPwrctrl.getStaffNo(),
							tmnlPwrctrl.getTmnlAssetNo(),
							tmnlPwrctrl.getTotalNo() });
		}
	}
}
