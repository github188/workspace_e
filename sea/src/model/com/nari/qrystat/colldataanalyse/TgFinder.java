package com.nari.qrystat.colldataanalyse;

import com.nari.util.SelectConfig;
import com.nari.util.SelectConfig.DealValueType;
import com.nari.util.SelectConfig.LikeMode;
import com.nari.util.SelectConfig.LimitType;

/**
 * 从左边树选择节点后通过这个finder进行查询
 * @author huangxuan
 *
 */
public class TgFinder {
	//是公变还是专变
	@SelectConfig(column="pub_priv_flag",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String pubPrivFlag;
	@SelectConfig(column="is_reg",dealNull=DealValueType.PARSEEMPTY,limit=LimitType.EQ)
	private String isReg;
	private String linkFlag;
	
	
	public String getPubPrivFlag() {
		return pubPrivFlag;
	}
	public void setPubPrivFlag(String pubPrivFlag) {
		this.pubPrivFlag = pubPrivFlag;
	}
	public String getLinkFlag() {
		return linkFlag;
	}
	public void setLinkFlag(String linkFlag) {
		this.linkFlag = linkFlag;
	}
	public String getIsReg() {
		return isReg;
	}
	public void setIsReg(String isReg) {
		this.isReg = isReg;
	}
}
