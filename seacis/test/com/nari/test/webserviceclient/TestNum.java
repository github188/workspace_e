package com.nari.test.webserviceclient;

import java.net.MalformedURLException;
import java.util.Date;

import com.nari.ami.cache.CoherenceStatement;
import com.nari.ami.cache.IStatement;
import com.nari.ami.database.map.runcontrol.RTmnlRun;
import com.nari.util.StringUtil;

public class TestNum {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, Exception {
		IStatement statement = CoherenceStatement.getSharedInstance();
		RTmnlRun rTmnlRun = new RTmnlRun();
		
		rTmnlRun.setTerminalId("918918");
		rTmnlRun.setCpNo(StringUtil.removeNull("1"));
		rTmnlRun.setTmnlAssetNo(StringUtil.removeNull("918918"));
		rTmnlRun.setTerminalAddr(StringUtil.removeNull("1"));
		rTmnlRun.setCisAssetNo(StringUtil.removeNull("1"));
		rTmnlRun.setSimNo(StringUtil.removeNull("1"));
		rTmnlRun.setId(StringUtil.removeNull("1"));
		rTmnlRun.setFactoryCode(StringUtil.removeNull("1"));
		rTmnlRun.setAttachMeterFlag(StringUtil.removeNull("1"));
		rTmnlRun.setTerminalTypeCode(StringUtil.removeNull("1"));
		rTmnlRun.setCollMode(StringUtil.removeNull("1"));
		rTmnlRun.setProtocolCode(StringUtil.removeNull("1"));
		rTmnlRun.setCommPassword(StringUtil.removeNull("1"));
		rTmnlRun.setRunDate(new Date());
		rTmnlRun.setStatusCode(StringUtil.removeNull("1"));
		rTmnlRun.setHarmonicDevFlag(StringUtil.removeNull("1"));
		rTmnlRun.setPsEnsureFlag(StringUtil.removeNull("1"));
		rTmnlRun.setAcSamplingFlag(StringUtil.removeNull("1"));
		rTmnlRun.setEliminateFlag(StringUtil.removeNull("1"));
		rTmnlRun.setGateAttrFlag(StringUtil.removeNull("1"));
		rTmnlRun.setPrioPsMode(StringUtil.removeNull("1"));
		rTmnlRun.setFreezeMode(StringUtil.removeNull("1"));
		rTmnlRun.setFreezeCycleNum(Integer.parseInt(StringUtil.removeNull("1")));
//			rTmnlRun.setMaxLoadCurveDays(Integer.parseInt("".equals(StringUtil.removeNull(mapTmnl.get("MAX_LOAD_CURVE_DAYS")))? "0" : StringUtil.removeNull(mapTmnl.get("MAX_LOAD_CURVE_DAYS"))));
//			rTmnlRun.setPsLineLen(Integer.parseInt(StringUtil.removeNull(mapTmnl.get("PS_LINE_LEN"))));
		rTmnlRun.setWorkPs(StringUtil.removeNull("1"));
		rTmnlRun.setSpeakerFlag(StringUtil.removeNull("1"));
//			rTmnlRun.setSpeakerDist(Integer.parseInt(String.valueOf(mapTmnl.get("SPEAKER_DIST"))));
		if("1".equals(StringUtil.removeNull("1"))){
			rTmnlRun.setSendUpMode(true);
		}else{
			rTmnlRun.setSendUpMode(false);
		}	
		rTmnlRun.setCommMode(StringUtil.removeNull("1"));
		rTmnlRun.setFrameNumber(Short.valueOf(StringUtil.removeNull("1")));
		rTmnlRun.setPowerCutDate(new Date());

		System.out.println("*****************************************************************************");
		try {
			//调用分布式缓存
			statement.executeUpdate(RTmnlRun.class, rTmnlRun.getTerminalId(), rTmnlRun);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
