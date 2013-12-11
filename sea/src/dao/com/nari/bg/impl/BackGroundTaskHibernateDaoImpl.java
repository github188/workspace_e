package com.nari.bg.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.nari.bg.BackGroundTaskHibernateDao;
import com.nari.bg.TBgTask;
import com.nari.bg.TBgTaskDetail;

public class BackGroundTaskHibernateDaoImpl extends HibernateDaoSupport
		implements BackGroundTaskHibernateDao {

	@Override
	public void saveTBgTaskDetail(List<TBgTaskDetail> tBgTaskDetail) {
		for (int i = 0; i < tBgTaskDetail.size(); i++) {
			this.getHibernateTemplate().save(tBgTaskDetail.get(i));
		}
	}

	@Override
	public void saveTBgTaskInfo(List<TBgTask> tBgTask) {
		for (int i = 0; i < tBgTask.size(); i++) {
			this.getHibernateTemplate().save(tBgTask.get(i));
		}
	}

}
