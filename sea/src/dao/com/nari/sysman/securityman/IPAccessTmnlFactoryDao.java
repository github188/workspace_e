package com.nari.sysman.securityman;

import java.util.List;

import com.nari.privilige.PAccessTmnlFactory;
import com.nari.util.exception.DBAccessException;

public interface IPAccessTmnlFactoryDao {

	List<PAccessTmnlFactory> findByStaffNo(String staffNo) throws DBAccessException;

}
