package com.nari.baseapp.planpowerconsume.impl;

import com.nari.baseapp.planpowerconsume.WtmnlCtrlStatusDao;
import com.nari.basedao.impl.JdbcBaseDAOImpl;
import com.nari.orderlypower.WTmnlCtrlStatus;

public class WtmnlCtrlStatusDaoImpl extends JdbcBaseDAOImpl implements WtmnlCtrlStatusDao {

	@Override
	public void saveOrUpdateTmnlCtrlStatusDownCtrlFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.down_ctrl_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, down_ctrl_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getDownCtrlFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getDownCtrlFlag()});
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusBusinessFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.business_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, business_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getBusinessFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getBusinessFlag()});
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusFactctrlFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.factctrl_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, factctrl_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getFactctrlFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getFactctrlFlag()});
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusFeectrlFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.feectrl_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, feectrl_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getFeectrlFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getFeectrlFlag()});
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusMonPctrlFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.mon_pctrl_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, mon_pctrl_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getMonPctrlFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getMonPctrlFlag() });
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusPwrctrlFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.pwrctrl_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, pwrctrl_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getPwrctrlFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getPwrctrlFlag() });
	}

	@Override
	public void saveOrUpdateTmnlCtrlStatusTurnFlag(WTmnlCtrlStatus ctrlStatus) {
		String sql =
			"merge into w_tmnl_ctrl_status wt\n" +
			"using (select ? as tmnl_asset_no, ? as total_no from dual) dat\n" + 
			"on (dat.tmnl_asset_no = wt.tmnl_asset_no and dat.total_no = wt.total_no)\n" + 
			"when matched then\n" + 
			"  update set wt.turn_flag = ?\n" + 
			"when not matched then\n" + 
			"  insert\n" + 
			"    (tmnl_asset_no, total_no, turn_flag)\n" + 
			"  values\n" + 
			"    (?, ?, ?)";
		getJdbcTemplate().update(sql,
				new Object[] { ctrlStatus.getTmnlAssetNo(),
						ctrlStatus.getTotalNo(), ctrlStatus.getTurnFlag(),
						ctrlStatus.getTmnlAssetNo(), ctrlStatus.getTotalNo(),
						ctrlStatus.getTurnFlag() });
	}
}
