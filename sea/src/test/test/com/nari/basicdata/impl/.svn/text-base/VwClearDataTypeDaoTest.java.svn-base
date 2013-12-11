package test.com.nari.basicdata.impl;

import java.util.List;

import test.com.nari.test.SpringJunitTest;

import com.nari.basicdata.VwClearDataType;
import com.nari.basicdata.VwClearDataTypeDao;
import com.nari.util.exception.DBAccessException;

public class VwClearDataTypeDaoTest extends SpringJunitTest {

	public void testFindAll() throws DBAccessException {
		VwClearDataTypeDao vwClearDataTypeDao = (VwClearDataTypeDao)this.applicationContext.getBean("vwClearDataTypeDao");
		List<VwClearDataType> vwClearDataTypeList = vwClearDataTypeDao.findAll();
		System.out.println(vwClearDataTypeList.size());
	}

}
