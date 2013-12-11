package com.nari.orderlypower;

/**
 * 遥测功率结果数据值对象
 * 
 * @author 姜炜超
 */
public class RmtCtrlPowerDto {
	private String runCap;// 运行容量
	private String totalP;// 当前总加有功功率
	private String totalQ;// 当前总加无功功率
	private String keyId;// 主键 唯一性标示
	private String totalNo;//总加组号

	public String getRunCap() {
		return runCap;
	}

	public void setRunCap(String runCap) {
		this.runCap = runCap;
	}

	public String getTotalP() {
		return totalP;
	}

	public void setTotalP(String totalP) {
		this.totalP = totalP;
	}

	public String getTotalQ() {
		return totalQ;
	}

	public void setTotalQ(String totalQ) {
		this.totalQ = totalQ;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(String totalNo) {
		this.totalNo = totalNo;
	}

	@Override
	public String toString() {
		return "keyId=" +keyId + "* totalP=" +totalP + "* totalQ=" +totalQ; 
	}
}
