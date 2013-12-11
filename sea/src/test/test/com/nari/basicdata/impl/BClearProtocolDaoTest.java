package test.com.nari.basicdata.impl;

import java.util.List;

import test.com.nari.test.SpringJunitTest;

import com.nari.basicdata.BClearProtocol;
import com.nari.basicdata.BClearProtocolDao;
import com.nari.util.exception.DBAccessException;

public class BClearProtocolDaoTest extends SpringJunitTest {

	public void testFindAll() throws DBAccessException {
		BClearProtocolDao bClearProtocolDao = (BClearProtocolDao)this.applicationContext.getBean("bClearProtocolDao");
		List<BClearProtocol> bClearProtocolList = bClearProtocolDao.findAll("", "asc");
		System.out.println(bClearProtocolList.size());
	}

	public void testFindBy() throws DBAccessException {
		//		BClearProtocolDao bClearProtocolDao = (BClearProtocolDao)this.applicationContext.getBean("bClearProtocolDao");
		//		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		//		PropertyFilter filter1 = new PropertyFilter("dataType", Byte.parseByte(3), MatchType.EQ);
		//		PropertyFilter filter2 = new PropertyFilter("protocolNo", "%FF", MatchType.LIKE);
		//		filters.add(filter1);
		//		filters.add(filter2);
		//		List<BClearProtocol> bClearProtocolList = bClearProtocolDao.findAll();
	}

}
