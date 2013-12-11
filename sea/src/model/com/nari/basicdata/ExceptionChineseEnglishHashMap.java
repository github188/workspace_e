package com.nari.basicdata;

import java.util.HashMap;

public class ExceptionChineseEnglishHashMap{
   
	private static HashMap<String,String> hm = new HashMap<String,String>();
    private static ElecExcepBean eec = new ElecExcepBean();
    public ExceptionChineseEnglishHashMap(){
          hm.put("00100","0");
          hm.put("00101","0");
          hm.put("00102","0");
          hm.put("00103","0");
          hm.put("00104","0");
          hm.put("00106","0");
          hm.put("00107","0");
          hm.put("00108","0");
    }
    
    public static HashMap<String, String> getHm() {
		return hm;
	}
	public ElecExcepBean getEec() {
		eec.setVoltageLost(hm.get("00100"));
		eec.setVoltageCut(hm.get("00101"));
		eec.setVoltageOver(hm.get("00102"));
		eec.setPowerExcep(hm.get("00103"));
		eec.seteCurrentExcep(hm.get("00104"));
		eec.setAmmeterStop(hm.get("00106"));
		eec.setDataExcep(hm.get("00107"));
		eec.setDayUseChange(hm.get("00108"));
		return eec;
	}
	
}
