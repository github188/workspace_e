package com.nari.orderlypower;

/**
 * 遥测电压电流结果数据值对象
 * @author 姜炜超
 */
public class RmtCtrlPCDto {
	private String ua;// A相电压
	private String ub;// B相电压
	private String uc;// C相电压
	private String ia;// A相电流
	private String ib;// B相电流
	private String ic;// C相电流
	private String keyId;// 主键 唯一性标示

	public String getUa() {
		return ua;
	}

	public void setUa(String ua) {
		this.ua = ua;
	}

	public String getUb() {
		return ub;
	}

	public void setUb(String ub) {
		this.ub = ub;
	}

	public String getUc() {
		return uc;
	}

	public void setUc(String uc) {
		this.uc = uc;
	}

	public String getIa() {
		return ia;
	}

	public void setIa(String ia) {
		this.ia = ia;
	}

	public String getIb() {
		return ib;
	}

	public void setIb(String ib) {
		this.ib = ib;
	}

	public String getIc() {
		return ic;
	}

	public void setIc(String ic) {
		this.ic = ic;
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	@Override
	public String toString() {
		return "keyId=" + keyId +"* ua=" +ua + "* ub=" +ub +"* uc=" +uc +"* ia=" +ia +"* ib=" +ib + "* ic=" +ic;
	}
}
