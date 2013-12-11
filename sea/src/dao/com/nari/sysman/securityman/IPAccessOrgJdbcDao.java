package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PAccessOrg;

public interface IPAccessOrgJdbcDao {

	List<PAccessOrg> findByStaffNo(String staffNo);

	void delOrg(String staffNo);

	void saveOrg(PAccessOrg pAccessOrg);

}
