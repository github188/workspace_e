package com.nari.terminalparam;

public class MpMeterInfoBean {
	private String tmnlAssetNo;//终端资产编号
    private String meterId;//电能表标识
    private String meterAssetNo;//电能表资产号
    private String mpId;//计量点标识
    private String mpNo;//计量点编号
    private String mpName;//计量点名称
    private int mpSn;//测量点
	public String getTmnlAssetNo() {
		return tmnlAssetNo;
	}
	public void setTmnlAssetNo(String tmnlAssetNo) {
		this.tmnlAssetNo = tmnlAssetNo;
	}
	public String getMeterId() {
		return meterId;
	}
	public void setMeterId(String meterId) {
		this.meterId = meterId;
	}
	public String getMeterAssetNo() {
		return meterAssetNo;
	}
	public void setMeterAssetNo(String meterAssetNo) {
		this.meterAssetNo = meterAssetNo;
	}
	public String getMpId() {
		return mpId;
	}
	public void setMpId(String mpId) {
		this.mpId = mpId;
	}
	public String getMpNo() {
		return mpNo;
	}
	public void setMpNo(String mpNo) {
		this.mpNo = mpNo;
	}
	public String getMpName() {
		return mpName;
	}
	public void setMpName(String mpName) {
		this.mpName = mpName;
	}
	public int getMpSn() {
		return mpSn;
	}
	public void setMpSn(int mpSn) {
		this.mpSn = mpSn;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((meterAssetNo == null) ? 0 : meterAssetNo.hashCode());
		result = prime * result + ((meterId == null) ? 0 : meterId.hashCode());
		result = prime * result + ((mpId == null) ? 0 : mpId.hashCode());
		result = prime * result + ((mpName == null) ? 0 : mpName.hashCode());
		result = prime * result + ((mpNo == null) ? 0 : mpNo.hashCode());
		result = prime * result + mpSn;
		result = prime * result
				+ ((tmnlAssetNo == null) ? 0 : tmnlAssetNo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MpMeterInfoBean other = (MpMeterInfoBean) obj;
		if (meterAssetNo == null) {
			if (other.meterAssetNo != null)
				return false;
		} else if (!meterAssetNo.equals(other.meterAssetNo))
			return false;
		if (meterId == null) {
			if (other.meterId != null)
				return false;
		} else if (!meterId.equals(other.meterId))
			return false;
		if (mpId == null) {
			if (other.mpId != null)
				return false;
		} else if (!mpId.equals(other.mpId))
			return false;
		if (mpName == null) {
			if (other.mpName != null)
				return false;
		} else if (!mpName.equals(other.mpName))
			return false;
		if (mpNo == null) {
			if (other.mpNo != null)
				return false;
		} else if (!mpNo.equals(other.mpNo))
			return false;
		if (mpSn != other.mpSn)
			return false;
		if (tmnlAssetNo == null) {
			if (other.tmnlAssetNo != null)
				return false;
		} else if (!tmnlAssetNo.equals(other.tmnlAssetNo))
			return false;
		return true;
	}
}
