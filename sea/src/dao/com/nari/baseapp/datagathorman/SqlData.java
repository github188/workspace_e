package com.nari.baseapp.datagathorman;

/****
 * 集中纪录要使用的sql 单例 *
 **/
public class SqlData {
	private SqlData() {
	}

	private static SqlData sd = new SqlData();

	public static SqlData getOne() {
		return sd;
	}

	public String dataFetch_base = "select o.org_name,c.cons_no,c.cons_name,r.terminal_addr,nvl(vc.PROTOCOL_name,'未知规约') as protocol_name,nvl(vf.factory_name,'未知厂商') as factory_name,vf.factory_code ,"
			+ " r.tmnl_asset_no,r.protocol_code,r.cis_asset_no from vw_tmnl_run r join"
			+ " c_cons c on (r.cons_no=c.cons_no) join  o_org o  on(r.org_no=o.org_no)"
			+ " left join vw_protocol_code vc on(vc.PROTOCOL_CODE=r.protocol_code)"
			+ " left join vw_tmnl_factory vf on (vf.factory_code=r.factory_code) ";
	/****
	 * 当查询左边树中一个部门的所有的用户时 *
	 **/
	public String dataFetch_org = dataFetch_base + " where r.org_no like ? ";
	/****
	 * 当查询左边树中一个区域下所有的用户时 *
	 * 
	 **/
	public String dataFetch_sub = dataFetch_base + " where c.subs_id=? ";
	/***
	 * 当查询左边树种一个线路下的所有的用户是 *
	 **/
	public String dataFecth_line = dataFetch_base + " where c.line_id=? ";
	/****
	 * 当查询的是某个用户的节点时
	 * **/
	public String dataFecth_usr = dataFetch_base + " where c.cons_no=? ";
	/****
	 * 当查询的条件是以终端资产号为基准时 *
	 **/
	public String dataFetch_tmnl = dataFetch_base
			+ " where r.tmnl_asset_no=? and rownum=1 ";
	public String dataFetch_allClick = dataFetch_base + " where ";
	/***
	 * 日志查询的基本语句 *
	 ****/
	public String logQuery_base = "select l.*,bcp.protocol_name as big_name,bcpi.prot_item_name as small_name,o.org_name,psu.staff_no,psu.name as staff_name,c.cons_no,c.cons_name,vot.op_type_name,voo.op_obj_name from\n"
			+ " L_OP_TMNL_LOG l join o_org o on(l.org_no=o.org_no) join p_sys_user psu on (psu.staff_no=l.emp_no)\n"
			+ "join vw_op_type vot on(vot.op_type=l.op_type) join vw_op_obj voo on(voo.op_obj=l.op_obj)\n"
			+ "join Vw_Tmnl_Run r on(r.TMNL_ASSET_NO=l.TMNL_ASSET_NO ) join c_cons c on(c.cons_no=r.cons_no)\n "
			+ "left join b_comm_protocol bcp on(l.protocol_no=bcp.protocol_no) left join b_comm_protocol_item bcpi on(l.prot_item_no=bcpi.prot_item_no) \n"
			+ "";

	/**
	 * 群组的基本语句 *
	 **/
	public String group_base = "select o.org_name,c.cons_no,c.cons_name,r.terminal_addr,vc.PROTOCOL_name,vf.factory_name,vf.factory_code , r.tmnl_asset_no,r.protocol_code\n"
			+ "from vw_tmnl_run r join c_cons c on (r.cons_no=c.cons_no) join  o_org o  on(r.org_no=o.org_no) join vw_protocol_code vc\n"
			+ "on(vc.PROTOCOL_CODE=r.protocol_code) join vw_tmnl_factory vf on(vf.factory_code=r.factory_code) join R_USER_GROUP_DETAIL rud\n"
			+ "on(rud.tmnl_asset_no=r.tmnl_asset_no) join r_user_group rug on(rug.group_no=rud.group_no)";
	/*** 点击用户群组节点时候的语句 **/
	public String group_click = group_base
			+ " where ((rud.group_no=? and rug.staff_no=?) or (rug.is_share=1  and rud.group_no=?)) ";

	/*****
	 * 查找一个操作人员 *
	 ***/
//	public String logQuery_findStaff =
//
//	"select * from (\n" + "select psu.staff_no,\n" + "       psu.org_no,\n"
//			+ "       psu.name as staff_name,\n" + "       oo.org_name,\n"
//			+ "       od.dept_no,\n" + "       od.name as dept_name\n"
//			+ "  from p_sys_user psu\n"
//			+ "  join o_org oo on (psu.org_no = oo.org_no)\n"
//			+ "  join o_dept od on (od.dept_no = psu.dept_no)\n"
//			+ " where psu.org_no like ?\n" + "    or (? = (select org_no\n"
//			+ "               from o_org\n"
//			+ "              where p_org_no = '00000'\n"
//			+ "                and Rownum = 1)))\n";

	//因手工建档原因，四川盘龙修改该sql如下，其他地方注释掉即可
	public String logQuery_findStaff =

		"select * from (\n" + "select psu.staff_no,\n" + "       psu.org_no,\n"
				+ "       psu.name as staff_name,\n" + "       oo.org_name\n"				
				+ "  from p_sys_user psu\n"
				+ "  join o_org oo on (psu.org_no = oo.org_no)\n"
				+ " where psu.org_no like ?\n" + "    or (? = (select org_no\n"
				+ "               from o_org\n"
				+ "              where p_org_no = '13401'\n"
				+ "                and Rownum = 1)))\n";

	/***
	 * 查找某人操作日志的主异常 *
	 ***/
	public String logQuery_userOpLog = "select l.*,p.name,o.org_name,ot.op_type_name  from l_user_op_log l join\n"
			+ " p_sys_user p on(p.staff_no=l.emp_no) join o_org o on(o.org_no=l.org_no)\n"
			+ " join  vw_user_op_type ot on (ot.op_type=l.op_type) ";

	/******
	 * 在档案运行里面进行左边树查询的语句 *
	 *****/
	public String archives_run = "select o.org_name,c.cons_id,c.cons_no,c.tg_id,r.cp_no,r.terminal_id,c.cons_name,r.terminal_addr,vc.PROTOCOL_name,vf.factory_name,vf.factory_code ,"
			+ " r.tmnl_asset_no,r.protocol_code from vw_tmnl_run r join"
			+ " c_cons c on (r.cons_no=c.cons_no) join  o_org o  on(r.org_no=o.org_no)"
			+ " join vw_protocol_code vc on(vc.PROTOCOL_CODE=r.protocol_code)"
			+ " join vw_tmnl_factory vf on (vf.factory_code=r.factory_code) ";
	public String archives_run_org = archives_run + " where r.org_no like ?  ";
	public String archives_run_org_06 = archives_run + " where r.org_no = ?  ";
	public String archives_run_sub = archives_run + " where c.subs_id=?    ";
	public String archives_run_line = archives_run + "  where c.line_id=?   ";
	public String archives_run_usr = archives_run + "  where c.cons_no=?   ";
	public String archives_run_tmnl = archives_run
			+ "  where r.tmnl_asset_no=?  ";
	/*** 如果在档案运行里面点击了左边树中的群组,减少了字段了表连接的数目 *****/
	public String archives_group = "select c.cons_no,c.cons_name,c.tg_id  from r_user_group rug join r_user_group_detail rud\n"
			+ "on(rud.group_no=rug.group_no) join c_cons c on(c.cons_no=rud.cons_no)\n"
			+ "join vw_tmnl_run r on(r.cons_no=c.cons_no)\n";

	/***** 召测组合语句 ****/
	public String batchFetch_findCombi = "select t.combi_id,t.combi_name,t.staff_no,b.data_group from t_data_combi  t\n"
			+ " join b_clear_protocol b on(t.clear_prot_no=b.protocol_no) where t.rowid in (\n"
			+ " select rid from(\n"
			+ " select max(tt.rowid ) as rid,min(bb.data_group) as dg from t_data_combi tt\n"
			+ " join b_clear_protocol bb on(tt.clear_prot_no=bb.protocol_no)\n"
			+ " where (trunc(tt.create_date)+tt.valid_days>trunc(sysdate) or tt.valid_days=0)\n"
			+ " and tt.staff_no=?  group by tt.combi_name\n"
			+ " ) tb where (tb.dg=? or tb.dg=3)\n" + " )";

	/****** 通过召测组合的id来找到所有的召测组合的小项 ****/
	public String batchFetch_findCombi_item = "select b.protocol_no,b.protocol_name,b.data_group from t_data_combi  t join b_clear_protocol b on(t.clear_prot_no=b.protocol_no)\n"
			+ "where staff_no =? and combi_name in (select combi_name from t_data_combi where combi_id=?)";
	/*** 找某个用户的所有的任务 ****/
	public String batchFetch_findTask = "select tt.*,o.org_name,vot.obj_type_name,vtt.task_type_name ,stc.STATUS_NAME,\n"
			+ "decode(tt.obj_type,0,'终端',tt.obj_list) as data_list\n"
			+ "from t_bg_task tt join o_org o on(o.org_no=tt.org_no)\n"
			+ "join vw_send_status_code stc on(stc.STATUS_CODE=tt.task_status)\n"
			+ "join vw_obj_type vot on(vot.obj_type=tt.obj_type)\n"
			+ "join vw_task_type vtt on(vtt.task_type=tt.task_type)\n"
			+ "where  %s  tt.staff_no=? and tt.task_type=4 order by task_id desc";
	/***** 找到某个任务id下的所有的任务结果 ***/
	public String batchFetch_taskResult = "select tr.*,b.protocol_name from t_bg_task_result tr join\n"
			+ "b_clear_protocol b on(b.protocol_no=tr.data_item_no) where\n"
			+ " tr.task_id=?";
	/** 统计查询，采集点覆盖情况 *****/
	public String coverageQuery = "select aaaaaa.*,\n"
			+ "       nvl(oo.org_short_name, '合计') as org_short_name,\n"
			+ "       decode(cons_cnt0, 0, 0, inst_cons_cnt0 / cons_cnt0) as inst_cons_rate0,\n"
			+ "       decode(cons_cnt1, 0, 0, inst_cons_cnt1 / cons_cnt1) as inst_cons_rate1,\n"
			+ "       decode(cons_cnt2, 0, 0, inst_cons_cnt2 / cons_cnt2) as inst_cons_rate2,\n"
			+ "       decode(cons_cnt3, 0, 0, inst_cons_cnt3 / cons_cnt3) as inst_cons_rate3\n"
			+ "  from (select o.org_no,\n"
			+ "               sum(decode(a.cap_grade_no, '00', a.demand_cons_cnt, 0)) as cons_cnt0,\n"
			+ "               sum(decode(a.cap_grade_no, '00', a.cons_cnt, 0)) as inst_cons_cnt0,\n"
			+ "               sum(decode(a.cap_grade_no, '01', a.demand_cons_cnt, 0)) as cons_cnt1,\n"
			+ "               sum(decode(a.cap_grade_no, '01', a.cons_cnt, 0)) as inst_cons_cnt1,\n"
			+ "               sum(decode(a.cap_grade_no, '02', a.demand_cons_cnt, 0)) as cons_cnt2,\n"
			+ "               sum(decode(a.cap_grade_no, '02', a.cons_cnt, 0)) as inst_cons_cnt2,\n"
			+ "               sum(decode(a.cap_grade_no,\n"
			+ "                          '03',\n"
			+ "                          a.demand_cons_cnt,\n"
			+ "                          '04',\n"
			+ "                          a.demand_cons_cnt,\n"
			+ "                          0)) as cons_cnt3,\n"
			+ "               sum(decode(a.cap_grade_no,\n"
			+ "                          '03',\n"
			+ "                          a.cons_cnt,\n"
			+ "                          '04',\n"
			+ "                          a.cons_cnt,\n"
			+ "                          0)) as inst_cons_cnt3\n"
			+ "          from a_tmnl_stats_d a\n"
			+ "          join o_org o on (a.org_no = o.org_no)\n"
			+ "         where o.p_org_no = ?\n"
			+ "           and a.stat_date = trunc(sysdate) - 1\n"
			//+ "           and a.cons_type != 5" +
			+		" %s\n"
			+ "         group by o.p_org_no, rollup(o.org_no)) aaaaaa\n"
			+ "  left join (select oo.org_no,oo.org_short_name from o_org oo join p_access_org pao on(pao.org_no=oo.#ORG)"
			+ "" + "where pao.staff_no=?"
			+ ") oo on (oo.org_no = aaaaaa.org_no)";

	public String coverageQueryByArea = "select aaaa.*,\n"
			+ "       nvl(oo.org_short_name, '合计') as org_short_name,\n"
			+ "       decode(demandcnt1, 0, 0, conscnt1 / demandcnt1) as rate1,\n"
			+ "       decode(demandcnt2, 0, 0, conscnt2 / demandcnt2) as rate2,\n"
			+ "       decode(demandcnt5, 0, 0, conscnt5 / demandcnt5) as rate5,\n"
			+ "       decode(demandcntRest, 0, 0, conscntRest / demandcntRest) as rateRest\n"
			+ "  from (select ad.org_no,\n"
			+ "               sum(decode(ad.cons_type, '1', ad.demand_cons_cnt, 0)) as demandcnt1,\n"
			+ "               sum(decode(ad.cons_type, '1', ad.cons_cnt, 0)) as conscnt1,\n"
			+ "               sum(decode(ad.cons_type, '2', ad.demand_cons_cnt, 0)) as demandcnt2,\n"
			+ "               sum(decode(ad.cons_type, '2', ad.cons_cnt, 0)) as conscnt2,\n"
			+ "               sum(decode(ad.cons_type, '5', ad.demand_cons_cnt, 0)) as demandcnt5,\n"
			+ "               sum(decode(ad.cons_type, '5', ad.cons_cnt, 0)) as conscnt5,\n"
			+ "               sum(decode(ad.cons_type,\n"
			+ "                          '4',\n"
			+ "                          ad.demand_cons_cnt,\n"
			+ "                          '3',\n"
			+ "                          ad.demand_cons_cnt,\n"
			+ "                          '6',\n"
			+ "                          ad.demand_cons_cnt,\n"
			+ "                          '7',\n"
			+ "                          ad.demand_cons_cnt,\n"
			+ "                          '10',\n"
			+ "                          ad.demand_cons_cnt,\n"
			+ "                          0)) as demandcntRest,\n"
			+ "               sum(decode(ad.cons_type,\n"
			+ "                          '4',\n"
			+ "                          ad.cons_cnt,\n"
			+ "                          '3',\n"
			+ "                          ad.cons_cnt,\n"
			+ "                          '6',\n"
			+ "                          ad.cons_cnt,\n"
			+ "                          '7',\n"
			+ "                          ad.cons_cnt,\n"
			+ "                          '10',\n"
			+ "                          ad.cons_cnt,\n"
			+ "                          0)) as conscntRest\n"
			+ "          from a_tmnl_stats_d ad\n"
			+ "          join o_org o on (o.org_no = ad.org_no)\n"
			+ "         where ad.stat_date = trunc(sysdate) - 1\n"
			+ "           and o.p_org_no = ?\n"
			// + "          and ad.cons_type != 5 " +
			+ " %s \n"
			+ "         group by o.p_org_no, rollup(ad.org_no)) aaaa\n"
			+ "  left join ( select oo.org_no,oo.org_short_name from o_org oo join p_access_org pao on(pao.org_no=oo.#ORG)"
			+ "" + "where pao.staff_no=?" + ") oo on (oo.org_no = aaaa.org_no)";

	/**** 找到档案管理里面的配置 ****/
	public String archives_config = "select b.param_item_val from b_sys_parameter b where b.param_no='INTERFACE' and b.param_item_no=upper(?)";

	/*** 通过一个终端资产号找到相关联的表计的信息 *****/
	public String dataFecth_findCmp =

	"select cmp.mp_no,\n"
			+ "       cmp.mp_name,\n"
			+ "       e.mp_sn,\n"
			+ "       cc.cons_no,\n"
			+ "       e.tmnl_asset_no,\n"
			+ "       e.asset_no,\n"
			+ "      dlc.asset_no fmr_asset_no,\n"
			+ "       cc.cons_name\n"
			+ "  from e_data_mp e\n"
			+ "  left join c_cons cc on (cc.cons_no = e.cons_no)\n"
			+ "  left join c_meter c on (c.meter_id = e.meter_id)\n"
			+ "  left join c_meter_mp_rela mmr on (e.meter_id = mmr.meter_id)\n"
			+ "  left join c_mp cmp on (cmp.mp_id = mmr.mp_id)\n"
			+ "  LEFT join D_LC_EQUIP dlc on (dlc.id = c.fmr_asset_no)\n"
			+ " where cmp.meter_flag = 1\n" + "   and e.tmnl_asset_no = ?\n"
			+ "  and e.is_valid=1 \n" + " order by c.fmr_asset_no";

	/***
	 * 通过一个终端资产号找到相关联的表计的信息 通过一个find来查找 *
	 ****/
	public String dataFecth_edatamp =

	"select cmp.mp_no,\n"
			+ "       cmp.mp_name,\n"
			+ "       e.mp_sn,\n"
			+ "       cc.cons_no,\n"
			+ "       e.tmnl_asset_no,\n"
			+ "       e.asset_no,\n"
			+ "      dlc.asset_no fmr_asset_no,\n"
			+ "       cc.cons_name\n"
			+ "  from e_data_mp e\n"
			+ "  left join c_cons cc on (cc.cons_no = e.cons_no)\n"
			+ "  left join c_meter c on (c.meter_id = e.meter_id)\n"
			+ "  left join c_meter_mp_rela mmr on (e.meter_id = mmr.meter_id)\n"
			+ "  left join c_mp cmp on (cmp.mp_id = mmr.mp_id)\n"
			+ "  LEFT join D_LC_EQUIP dlc on (dlc.id = c.fmr_asset_no)\n"
			+ " where (cmp.meter_flag = 1 or cmp.meter_flag is null)\n"
			+ "  and e.is_valid=1 \n" + " and   %s \n" + " order by e.mp_sn";

	/*** 找到一个终端对应的所有的测量点 ****/
	public String dataFetch_findAllPns = "select e.mp_sn\n"
			+ "  from e_data_mp e\n" + " where \n" + "    e.tmnl_asset_no = ? "
			+ "  and e.is_valid=1 \n";
	/**** 找到一个终端对应的所有的采集器 ******/
	public String dataFetch_findCollect = "select dlc.asset_no as fmr_asset_no from e_data_mp e\n"
			+ "join  c_meter c on(e.meter_id=c.meter_id)\n"
			+ " join D_LC_EQUIP dlc on (dlc.id = c.fmr_asset_no) \n"
			+ " where\n"
			+ "e.tmnl_asset_no=? and c.tmnl_asset_no!=c.fmr_asset_no \n"
			+ "  and e.is_valid=1 \n";;
	/***** 找到一个采集器所对应的所有电能表资产号 ******/
	public String dataFetch_findPns = "select e.asset_no from e_data_mp e\n"
			+ "join  c_meter c on(e.meter_id=c.meter_id)\n"
			+ " join D_LC_EQUIP dlc on (dlc.id = c.fmr_asset_no) \n"
			+ "where dlc.asset_no=?\n" + "  and e.is_valid=1 \n";
	/***
	 * 查找所有的用户操作类型 *
	 **/
	public String log_user_op_type = "select * from vw_user_op_type";
	/***
	 * 查找所有的终端操作类型 *
	 **/
	public String log_tmnl_op_type = "select * from vw_op_type";

	/**
	 * 批量巡测的统计结果查询
	 * **/
	public String batchFetch_statistics = "select distinct tem.*,tb.task_name, success / cnt as rate\n"
			+ "  from (select t.task_id,\n"
			+ "               count(1) cnt,\n"
			+ "               nvl(max(t.finish_time - t.task_time) * 60 * 60 * 24, 0) as last_Time, --执行的秒数\n"
			+ "               sum(decode(t.task_status, '04', 1, 0)) as success, --成功\n"
			+ "               sum(decode(t.task_status, '01', 1, 0)) as pause, --已经挂起的任务\n"
			+ "               sum(decode(t.task_status, '02', 1, 0)) as waiting, --在线等待\n"
			+ "               sum(decode(t.task_status, '08', 1, 0)) as doing, --正在下发\n"
			+ "               sum(decode(t.task_status, '05', 1, '06', 1, 0)) as failure --失败\n"
			+ "          from t_bg_task t\n"
			+ "         where t.task_type = 4 \n"
			+ "         group by t.task_id) tem,\n"
			+ "       t_bg_task tb\n"
			+ " where tem.task_id = tb.task_id";
	/** 台区线损管理-查找一个考核单元下面的所有的 台区 **/
	public String losePower_findInGCHKUNIT = "select *\n"
			+ "  from (select o.org_name,\n"
			+ "               o.org_no,\n"
			+ "               gt.tg_name,\n"
			+ "               gt.tg_no,\n"
			+ "               gt.tg_cap,\n"
			+ "               gt.pub_priv_flag,\n"
			+ "               gt.run_status_code,\n"
			+ "               r.cis_asset_no,\n"
			// 这里查出来的肯定是注册过的
			+ "               1 as is_reg\n"
			+ "          from g_chkunit_comp gc\n"
			+ "join c_cons c on(c.tg_id=gc.obj_id)\n"
			+ "join g_tg gt on (gt.tg_id = c.tg_id)\n"
			+ "join o_org o on (o.org_no = gt.org_no)\n"
			+ "join vw_tmnl_run r on (r.cons_no = c.cons_no)"
			+ " join g_chkunit gu on(gu.chkunit_id=gc.chkunit_id) "
			+ " where gc.chkunit_id = ? and c.cons_type=2 and gu.chkunit_type_code='02') ";

	/** 台区线损管理-通过供电单位查询 **/
	public String losePower_fingByOrg = "select * from (select c.org_no,\n"
			+ "       o.org_name,\n"
			+ "       r.cis_asset_no,\n"
			+ "       r.tmnl_asset_no,\n"
			+ "       g.tg_no,\n"
			+ "       g.tg_id,\n"
			+ "       g.tg_name,\n"
			+ "       g.tg_cap,\n"
			+ "       g.pub_priv_flag,\n"
			+ "       g.run_status_code,\n"
			+ "       nvl((select 1 from g_chkunit_comp gc where gc.obj_id=g.tg_id  and rownum=1),0) as is_reg\n"
			+ "  from c_cons c\n" + "  join g_tg g on (c.tg_id = g.tg_id)\n"
			+ "  join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
			+ "  join o_org o on (o.org_no = c.org_no)\n"
			+ " where c.cons_type = 2\n" + "   and c.org_no like ?) ";

	/** 台区线损管理-通过线路查询 **/
	public String losePower_fingByLine = "select * from (select c.org_no,\n"
			+ "       o.org_name,\n"
			+ "       r.cis_asset_no,\n"
			+ "       g.tg_no,\n"
			+ "		g.tg_id,\n"
			+ "       g.tg_name,\n"
			+ "       r.tmnl_asset_no,\n"
			+ "       g.tg_cap,\n"
			+ "       g.pub_priv_flag,\n"
			+ "       g.run_status_code,\n"
			+ "       nvl((select 1 from g_chkunit_comp gc where gc.obj_id=g.tg_id  and rownum=1),0) as is_reg\n"
			+ "  from c_cons c\n"
			+ "  join g_line_tg_rela gr on (c.tg_id = gr.tg_id)\n"
			+ "  join g_tg g on (c.tg_id = g.tg_id)\n"
			+ "  join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
			+ "  join o_org o on (o.org_no = c.org_no)\n"
			+ " where c.cons_type =2\n" + "   and gr.line_id = ? ) ";;
	/** 台区线损管理-通过终端资产号查找 **/
	public String losePower_findByTmnl = "select * from (select c.org_no,\n"
			+ "       o.org_name,\n"
			+ "       r.cis_asset_no,\n"
			+ "       r.tmnl_asset_no,\n"
			+ "		g.tg_id,\n"
			+ "       g.tg_no,\n"
			+ "       g.tg_name,\n"
			+ "       g.tg_cap,\n"
			+ "       g.pub_priv_flag,\n"
			+ "       g.run_status_code,\n"
			+ "       nvl((select 1 from g_chkunit_comp gc where gc.obj_id=g.tg_id  and rownum=1),0) as is_reg\n"
			+ "  from c_cons c\n" + "  join g_tg g on (c.tg_id = g.tg_id)\n"
			+ "  join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
			+ "  join o_org o on (o.org_no = c.org_no)\n"
			+ " where c.cons_type = 2\n" + "   and r.tmnl_asset_no = ?) ";
	/*** 台区线损管理-查找考核单元 **/
	public String losePower_findChkUnit = "select * from (select g.*,decode(g.resp_emp_no,?,1,0) is_self, o.org_name\n"
			+ ",(decode(substr(chk_cycle,1,1),1,'日,','')||\n"
			+ "decode(substr(chk_cycle,2,1),1,'月,','')||\n"
			+ "decode(substr(chk_cycle,3,1),1,'季,','')||\n"
			+ "decode(substr(chk_cycle,4,1),1,'半年,','')||\n"
			+ "decode(substr(chk_cycle,5,1),1,'年,','')\n"
			+ ") as cycle"

			+ "  from g_chkunit g\n"
			+ "  join o_org o on(o.org_no=g.org_no)\n"
			+ "  join p_sys_user psu on (psu.staff_no= g.resp_emp_no)\n"
			+ " where (psu.org_no like ? or ? = (select org_no from o_org where p_org_no='00000' and Rownum=1)) )";
	/**** 台区线损管理-根据id查找考核单元 ****/
	public String losePower_findChkunitById = "select g.*,o.org_name\n"
			+ "  from g_chkunit g\n" + "join o_org o on(o.org_no=g.org_no) \n"
			+ " where \n" + "   g.chkunit_id = ?";
	/**** 台区线损管理-找到表能表，表计，终端之间的关系 ****/
	public String losePower_findMeterMp =

	"select * from ( select cm.mp_no,\n"
			+ "       cm.mp_name,\n"
			+ "       c.cons_type ,\n"
			+ "       cm.type_code,\n"
			+ "       e.id as data_id,\n"
			+ "       cm.MP_ATTR_CODE,\n"
			+ "       cm.USAGE_TYPE_CODE,\n"
			+ "       c.cons_no,\n"
			+ "       e.data_src,\n"
			+ "       cc.meter_id,\n"
			+ "       cc.fmr_asset_no,\n"
			+ "       cm.mp_id,\n"
			+ "       c.cons_name,\n"
			+ "       cc.asset_no,\n"
			+ "      	c.line_id,\n"
			+ "      	c.tg_id,\n"
			+ "       e.tmnl_asset_no,\n"
			+ "       e.terminal_addr,\n"
			+ "nvl((select pq_flag from g_io_mp gm where gm.mp_id=cm.mp_id and gm.meter_id=e.meter_id and gm.chkunit_id=? and rownum=1),-1) as pq_flag,"
			+ "nvl((select is_in from g_io_mp gm where gm.mp_id=cm.mp_id and gm.meter_id=e.meter_id and gm.chkunit_id=? and rownum=1),-1) as is_in,"
			+ "nvl((select IS_VALID from g_io_mp gm where gm.mp_id=cm.mp_id and gm.meter_id=e.meter_id and gm.chkunit_id=? and rownum=1),-1) as io_valid"
			+ "  from e_data_mp e\n"
			+ "  join c_cons c on (c.cons_no = e.cons_no)\n"
			+ "  join c_meter cc on (cc.meter_id = e.meter_id)\n"
			+ "  join c_meter_mp_rela cmm on (e.meter_id = cmm.meter_id)\n"
			+ "  join c_mp cm on (cmm.mp_id = cm.mp_id)\n"
			+ " where e.is_valid = 1\n"
			+ "   and e.tmnl_asset_no in (select distinct r.tmnl_asset_no\n"
			+ "                             from g_chkunit_comp gc\n"
			+ "                             join c_cons c on (c.#table = gc.obj_id)\n"
			+ "                             join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
			+ "                            where gc.chkunit_id = ? ) #type ) ";

	/**** 台区线损管理-找到表能表，表计，终端之间的关系,查询的值没有那么多 ****/
	public String losePower_findMeterMpSimple =

	"select * from (select "
			+ "       cc.meter_id,\n"
			+ "       cm.mp_id,\n"
			+ "       e.id as data_id,\n"
			+ "       c.cons_type ,\n"
			+ "       e.data_src\n,"
			+ "       cc.asset_no,\n"
			+ "       cm.type_code,\n"
			+ "       cm.mp_attr_code,\n"
			+ "       e.tmnl_asset_no,\n"
			+ "       e.terminal_addr,\n"
			+ "       cc.fmr_asset_no,\n"
			+ "      	c.line_id,\n"
			+ "      	c.tg_id,\n"
			+ "nvl((select pq_flag from g_io_mp gm where gm.mp_id=cm.mp_id and gm.meter_id=e.meter_id and gm.chkunit_id=? and rownum=1),-1) as pq_flag,"
			+ "nvl((select is_in from g_io_mp gm where gm.mp_id=cm.mp_id and gm.meter_id=e.meter_id and gm.chkunit_id=? and rownum=1),-1) as is_in,"
			+ "nvl((select IS_VALID from g_io_mp gm where gm.mp_id=cm.mp_id and gm.meter_id=e.meter_id and gm.chkunit_id=? and rownum=1),-1) as io_valid"
			+ "  from e_data_mp e\n"
			+ "  join c_cons c on (c.cons_no = e.cons_no)\n"
			+ "  join c_meter cc on (cc.meter_id = e.meter_id)\n"
			+ "  join c_meter_mp_rela cmm on (e.meter_id = cmm.meter_id)\n"
			+ "  join c_mp cm on (cmm.mp_id = cm.mp_id)\n"
			+ " where e.is_valid = 1\n"
			+ "   and e.tmnl_asset_no in (select distinct r.tmnl_asset_no\n"
			+ "                             from g_chkunit_comp gc\n"
			+ "                             join c_cons c on (c.#table = gc.obj_id)\n"
			+ "                             join vw_tmnl_run r on (r.cons_no = c.cons_no)\n"
			+ "                            where gc.chkunit_id = ? )  #type) ";
	public String losePower_linelose = "select stat_date,\n"
			+ "       decode(ppq, null, 0, 0, 0, case when ppq < tg_spq then 0 else (ppq - tg_spq) / ppq end) as ll_per,\n"
			+ "       ll_idx_value\n"
			+ "  from a_tg_pq\n"
			+ " where tg_id = 14231\n"
			+ "   and to_date(stat_date, 'yyyymmdd') between trunc(sysdate - 10) and\n"
			+ "       trunc(sysdate)   order by stat_date";

	public String losePower_findFrm = "select distinct c.fmr_asset_no\n"
			+ "  from e_data_mp e\n"
			+ "  join c_meter c on (c.meter_id = e.meter_id)\n"
			+ " where e.terminal_addr = ?\n"
			+ "   and e.is_valid = 1 and c.fmr_asset_no is not null";
	// 通了线路在左边树中查询
	public String losePower_treeFindLineInOrg = "select *\n"
			+ "  from (select o.org_no,\n"
			+ "               g.line_name,\n"
			+ "               g.line_no,\n"
			+ "               g.volt_code,\n"
			+ "               g.run_status_code,\n"
			+ "               vc.VOLT,\n"
			+ "               g.line_id,\n"
			+ "               (select a.line_name from g_line a,g_line_rela b where a.line_id=b.link_line_id and b.line_id=g.line_id and rownum=1) as father,\n"
			+ "               o.org_name,\n"
			+ "               nvl((select 1\n"
			+ "                     from g_chkunit_comp gc\n"
			+ "                    where gc.obj_id = g.line_id\n"
			+ "                      and rownum = 1),\n"
			+ "                   0) as is_reg\n"
			+ "          from g_line g\n"
			+ "          join o_org o on (o.org_no = g.org_no)\n"
			+ "          join vw_volt_code vc on (vc.VOLT_CODE = g.volt_code)\n"
			+ "         where g.line_id in\n"
			+ "               (select distinct line_id from c_cons where org_no like ?)) ";

	/*** 台区线损-通过变电站找线路 ****/
	public String losePower_treeFindLineInSubs =

	"select *\n"
			+ "  from (select o.org_no,\n"
			+ "               g.line_id,\n"
			+ "               g.line_name,\n"
			+ "               g.line_no,\n"
			+ "               g.volt_code,\n"
			+ "               g.run_status_code,\n"
			+ "               vc.VOLT,\n"
			+ "               o.org_name,\n"
			+ "                (select a.line_name from g_line a,g_line_rela b where a.line_id=b.link_line_id and b.line_id=g.line_id and rownum=1) as father,\n"
			+ "               nvl((select 1\n"
			+ "                     from g_chkunit_comp gc\n"
			+ "                    where gc.obj_id = g.line_id\n"
			+ "                      and rownum = 1),\n"
			+ "                   0) as is_reg\n"
			+ "          from g_line g\n"
			+ "          join o_org o on (o.org_no = g.org_no)\n"
			+ "          join vw_volt_code vc on (vc.VOLT_CODE = g.volt_code)\n"
			+ "         where g.line_id in (select distinct line_id\n"
			+ "                               from g_subs_line_rela\n"
			+ "                              where subs_id = ?\n"
			+ "                                and rela_flag = 1)) ";

	/** 台区线损-通过线路id找线路 **/
	public String losePower_treeFindLineInLine =

	"select *\n"
			+ "  from (select o.org_no,\n"
			+ "               g.line_id,\n"
			+ "               g.line_name,\n"
			+ "               g.line_no,\n"
			+ "               g.volt_code,\n"
			+ "               g.run_status_code,\n"
			+ "               vc.VOLT,\n"
			+ "               o.org_name,\n"
			+ "               (select a.line_name\n"
			+ "                  from g_line a, g_line_rela b\n"
			+ "                 where a.line_id = b.link_line_id\n"
			+ "                   and b.line_id = g.line_id\n"
			+ "                   and rownum = 1) as father,\n"
			+ "               nvl((select 1\n"
			+ "                     from g_chkunit_comp gc\n"
			+ "                    where gc.obj_id = g.line_id\n"
			+ "                      and rownum = 1),\n"
			+ "                   0) as is_reg\n"
			+ "          from g_line g\n"
			+ "          join o_org o on (o.org_no = g.org_no)\n"
			+ "          join vw_volt_code vc on (vc.VOLT_CODE = g.volt_code)\n"
			+ "         where g.line_id = ?) ";

	/** 台区线损查询-查找当前的线路的考核单元里面的所有exist线路 ***/
	public String losePower_existLine = "select *\n"
			+ "  from (select o.org_no,\n"
			+ "               g.line_id,\n"
			+ "               g.line_name,\n"
			+ "               g.line_no,\n"
			+ "               gc.obj_id,\n"
			+ "               g.volt_code,\n"
			+ "               g.run_status_code,\n"
			+ "               vc.VOLT,\n"
			+ "               o.org_name,\n"
			+ "               (select a.line_name\n"
			+ "                  from g_line a, g_line_rela b\n"
			+ "                 where a.line_id = b.link_line_id\n"
			+ "                   and b.line_id = g.line_id\n"
			+ "                   and rownum = 1) as father,\n"
			+ "               1 as is_reg\n"
			+ "          from g_line g\n"
			+ "          join g_chkunit_comp gc on (g.line_id = gc.obj_id)\n"
			+ "          join o_org o on (o.org_no = g.org_no)\n"
			+ "			 join g_chkunit gu on(gu.chkunit_id=gc.chkunit_id)"
			+ "          join vw_volt_code vc on (vc.VOLT_CODE = g.volt_code)\n"
			+ "         where gc.chkunit_id = ? and gu.chkunit_type_code='01') ";

	public String losePower_checkExistLine = "select o.org_name,gl.line_id,gl.RUN_STATUS_CODE,gl.line_no,gl.line_name,g.* from g_chkunit_comp gc\n"
			+ "        join g_line gl on(gl.line_id=gc.obj_id)\n"
			+ "        join o_org o on(o.org_no=gl.org_no)\n"
			+ "        join g_chkunit g on(g.chkunit_id=gc.chkunit_id)\n"
			+ "        where g.chkunit_type_code='01' and ";
	/*** 查找分压等级 ***/
	public String losePower_voltType = "select * from vw_volt_code where volt_code in (select distinct volt_code from G_LINE)";
	public String archives_ugp = group_base
			+ " where ((rud.group_no=? and rug.staff_no=?) or (rug.is_share=1  and rud.group_no=?))    ";
	/*** 每日电量统计,在主表中查找每天的电量 ***/
	public String everyDate_pq = "select ad.*, o.org_name, o.org_short_name\n"
			+ "  from a_org_energy_d ad\n"
			+ "  join o_org o on (o.org_no = ad.org_no)\n"
			+ " where ad.stat_date = trunc(?) order by ad.org_no ";
	/**** 每日电量统计，在从表中找相关联的电量 ******/
	public String refDate_pq = "select ad.*, o.org_name, o.org_short_name,\n"
			+ "decode(oo.org_name,null,dtype.cons_type_name,oo.org_name) as det_type_name"
			+ "  from a_org_energy_det_d ad \n"
			+ "  join o_org o on (o.org_no = ad.org_no)\n"
			+ "left join vw_energy_det_type dtype on (dtype.cons_type=ad.energy_det_type)"
			+ "left  join o_org oo on (oo.org_no = ad.energy_det_type)\n"
			+ " where ad.org_no = ?\n" + "   and stat_date = trunc(?)\n"
			+ "   and energy_type = ? ";

	public static void main(String[] args) {
		SqlData sql = new SqlData();
		System.out.println(sql.dataFetch_org);
	}
}
